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
package org.jclouds.aliyun.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jclouds.aliyun.domain.Address;
import org.jclouds.aliyun.domain.Instance;
import org.jclouds.aliyun.domain.Link;
import org.jclouds.aliyun.domain.Resource;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.config.GsonModule;
import org.jclouds.json.config.GsonModule.DateAdapter;

import javax.inject.Singleton;
import java.beans.ConstructorProperties;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class AliyunParserModule extends AbstractModule {

   @Provides
   @Singleton
   public Map<Type, Object> provideCustomAdapterBindings() {
      return ImmutableMap.<Type, Object>of(
//              HostResourceUsage.class, new HostResourceUsageAdapter(),
//              InstanceWithSecurityGroups.class, new InstanceWithSecurityGroupsAdapter(),
              Instance.class, new InstanceAdapter()//,
//              Image.class, new ImageAdapter()
      );
   }

   @Override
   protected void configure() {
      bind(DateAdapter.class).to(GsonModule.Iso8601DateAdapter.class);
   }


   @Singleton
   public static class InstanceAdapter implements JsonDeserializer<Instance> {
      @Override
      public Instance deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
              throws JsonParseException {
         Instance serverBase;

         // Instances can be created without an image so test if an image object is returned
         if (jsonElement.getAsJsonObject().get("image").isJsonObject()) {
            serverBase = apply((InstanceInternal) context.deserialize(jsonElement, InstanceInternal.class));
         } else {
            serverBase = apply((InstanceInternalWithoutImage) context.deserialize(jsonElement, InstanceInternalWithoutImage.class));
         }

         Instance.Builder<?> result = Instance.builder().fromInstance(serverBase);
//         InstanceExtendedStatus extendedStatus = context.deserialize(jsonElement, InstanceExtendedStatus.class);
//         if (!Objects.equal(extendedStatus, InstanceExtendedStatus.builder().build())) {
//            result.extendedStatus(extendedStatus);
//         }
//         InstanceExtendedAttributes extraAttributes = context.deserialize(jsonElement, InstanceExtendedAttributes.class);
//         if (!Objects.equal(extraAttributes, InstanceExtendedAttributes.builder().build())) {
//            result.extendedAttributes(extraAttributes);
//         }
         return result.build();
      }

      public Instance apply(Instance in) {
         return in.toBuilder().build();
      }

      private static class InstanceInternal extends Instance {
         @ConstructorProperties({"id", "name", "links", "uuid", "tenant_id", "user_id", "updated", "created", "hostId", "accessIPv4", "accessIPv6", "status", "image", "flavor", "key_name", "config_drive", "addresses", "metadata", "extendedStatus", "extendedAttributes", "OS-DCF:diskConfig", "OS-EXT-AZ:availability_zone"})
         protected InstanceInternal(String id, @Nullable String name, Set<Link> links, @Nullable String uuid, String tenantId,
                                  String userId, Date updated, Date created, @Nullable String hostId, @Nullable String accessIPv4,
                                  @Nullable String accessIPv6, Status status, Resource image, Resource flavor, @Nullable String keyName,
                                  @Nullable String configDrive, Multimap<String, Address> addresses, Map<String, String> metadata,
//                                  @Nullable InstanceExtendedStatus extendedStatus, @Nullable InstanceExtendedAttributes extendedAttributes,
                                  @Nullable String diskConfig, @Nullable String availabilityZone) {
            super(id, name, links, uuid, tenantId, userId, updated, created, hostId, accessIPv4, accessIPv6, status, image, flavor, keyName, configDrive, addresses, metadata,
//                    extendedStatus, extendedAttributes,
                    diskConfig, availabilityZone);
         }
      }

      private static class InstanceInternalWithoutImage extends Instance {
         @ConstructorProperties({"id", "name", "links", "uuid", "tenant_id", "user_id", "updated", "created", "hostId", "accessIPv4", "accessIPv6", "status", "flavor", "key_name", "config_drive", "addresses", "metadata", "extendedStatus", "extendedAttributes", "OS-DCF:diskConfig", "OS-EXT-AZ:availability_zone"})
         protected InstanceInternalWithoutImage(String id, @Nullable String name, Set<Link> links, @Nullable String uuid, String tenantId,
                                  String userId, Date updated, Date created, @Nullable String hostId, @Nullable String accessIPv4,
                                  @Nullable String accessIPv6, Status status, Resource flavor, @Nullable String keyName,
                                  @Nullable String configDrive, Multimap<String, Address> addresses, Map<String, String> metadata,
//                                  @Nullable InstanceExtendedStatus extendedStatus, @Nullable InstanceExtendedAttributes extendedAttributes,
                                  @Nullable String diskConfig, @Nullable String availabilityZone) {
            super(id, name, links, uuid, tenantId, userId, updated, created, hostId, accessIPv4, accessIPv6, status, null, flavor, keyName, configDrive, addresses, metadata,
//                    extendedStatus, extendedAttributes,
                    diskConfig, availabilityZone);
         }
      }
   }

}
