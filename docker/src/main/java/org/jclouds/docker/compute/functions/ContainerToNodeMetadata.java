/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.docker.compute.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.getOnlyElement;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadataBuilder;
import org.jclouds.compute.domain.Processor;
import org.jclouds.compute.functions.GroupNamingConvention;
import org.jclouds.docker.domain.Container;
import org.jclouds.docker.domain.Port;
import org.jclouds.docker.domain.State;
import org.jclouds.domain.Location;
import org.jclouds.providers.ProviderMetadata;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.inject.Singleton;

@Singleton
public class ContainerToNodeMetadata implements Function<Container, NodeMetadata> {

   /**
    * This value is used when a container does not have an accessible
    * login port (i.e. the SSH daemon is not running) due to being
    * started outside jclouds. Client code should check for this value
    * when accessing NodeMetadata from Docker.
    */
   public static final Integer NO_LOGIN_PORT = Integer.valueOf(-1);

   private final ProviderMetadata providerMetadata;
   private final Function<State, NodeMetadata.Status> toPortableStatus;
   private final GroupNamingConvention nodeNamingConvention;
   private final Supplier<Map<String, ? extends Image>> images;
   private final Supplier<Set<? extends Location>> locations;

   @Inject
   public ContainerToNodeMetadata(ProviderMetadata providerMetadata, Function<State,
           NodeMetadata.Status> toPortableStatus, GroupNamingConvention.Factory namingConvention,
                                  Supplier<Map<String, ? extends Image>> images,
                                  @Memoized Supplier<Set<? extends Location>> locations) {
      this.providerMetadata = checkNotNull(providerMetadata, "providerMetadata");
      this.toPortableStatus = checkNotNull(toPortableStatus, "toPortableStatus cannot be null");
      this.nodeNamingConvention = checkNotNull(namingConvention, "namingConvention").createWithoutPrefix();
      this.images = checkNotNull(images, "images cannot be null");
      this.locations = checkNotNull(locations, "locations");
   }

   @Override
   public NodeMetadata apply(Container container) {
      String name = cleanUpName(container.name());
      String group = nodeNamingConvention.extractGroup(name);
      NodeMetadataBuilder builder = new NodeMetadataBuilder();
      builder.ids(container.id())
              .name(name)
              .group(group)
              .hostname(container.config().hostname())
               // TODO Set up hardware
              .hardware(new HardwareBuilder()
                      .id("")
                      .ram(container.config().memory())
                      .processor(new Processor(container.config().cpuShares(), container.config().cpuShares()))
                      .build());
      builder.status(toPortableStatus.apply(container.state()));
      builder.loginPort(getLoginPort(container));
      builder.publicAddresses(getPublicIpAddresses());
      builder.privateAddresses(getPrivateIpAddresses(container));
      builder.location(Iterables.getOnlyElement(locations.get()));
      String imageId = container.image();
      builder.imageId(imageId);
      if (images.get().containsKey(imageId)) {
          Image image = images.get().get(imageId);
          builder.operatingSystem(image.getOperatingSystem());
      }
      return builder.build();
   }

   private String cleanUpName(String name) {
      return name.startsWith("/") ? name.substring(1) : name;
   }

   private Iterable<String> getPrivateIpAddresses(Container container) {
      if (container.networkSettings() == null) return ImmutableList.of();
      return ImmutableList.of(container.networkSettings().ipAddress());
   }

   private List<String> getPublicIpAddresses() {
      String dockerIpAddress = URI.create(providerMetadata.getEndpoint()).getHost();
      return ImmutableList.of(dockerIpAddress);
   }

   protected static int getLoginPort(Container container) {
      if (container.networkSettings() != null) {
          Map<String, List<Map<String, String>>> ports = container.networkSettings().ports();
          if (ports != null && ports.containsKey("22/tcp")) {
            return Integer.parseInt(getOnlyElement(ports.get("22/tcp")).get("HostPort"));
          }
      // this is needed in case the container list is coming from listContainers
      } else if (container.ports() != null) {
         for (Port port : container.ports()) {
            if (port.privatePort() == 22) {
               return port.publicPort();
            }
         }
      }
      return NO_LOGIN_PORT;
   }
}
