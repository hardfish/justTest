package org.jclouds.aliyun.internal;

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

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.inject.TypeLiteral;
import org.jclouds.aliyun.AliyunApi;
import org.jclouds.aliyun.domain.Link;
import org.jclouds.aliyun.domain.PaginatedCollection;
import org.jclouds.aliyun.domain.Resource;
import org.jclouds.aliyun.features.InstanceApi;
import org.jclouds.aliyun.internal.ParseInstances.Instances;
import org.jclouds.aliyun.options.PaginationOptions;
import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.internal.Arg0ToPagedIterable;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.json.Json;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * boiler plate until we determine a better way
 */
@Beta
@Singleton
public class ParseInstances extends ParseJson<Instances> {
    static class Instances extends PaginatedCollection<Resource> {

        @ConstructorProperties({ "instances", "instances_links" })
        protected Instances(Iterable<Resource> instances, Iterable<Link> instances_links) {
            super(instances, instances_links);
        }

    }

    @Inject
    public ParseInstances(Json json) {
        super(json, TypeLiteral.get(Instances.class));
    }

    public static class ToPagedIterable extends Arg0ToPagedIterable.FromCaller<Resource, ToPagedIterable> {

        private final AliyunApi api;

        @Inject
        protected ToPagedIterable(AliyunApi api) {
            this.api = checkNotNull(api, "api");
        }

        @Override
        protected Function<Object, IterableWithMarker<Resource>> markerToNextForArg0(Optional<Object> arg0) {
            String region = arg0.get().toString();
            final InstanceApi instApi = api.getInstanceApi();
            return new Function<Object, IterableWithMarker<Resource>>() {

                @SuppressWarnings("unchecked")
                @Override
                public IterableWithMarker<Resource> apply(Object input) {
                    PaginationOptions paginationOptions = PaginationOptions.class.cast(input);
                    return IterableWithMarker.class.cast(instApi.list(paginationOptions));
                }

                @Override
                public String toString() {
                    return "list()";
                }
            };
        }

    }

}