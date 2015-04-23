package org.jclouds.aliyun.features;

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

import org.jclouds.Fallbacks;
import org.jclouds.aliyun.domain.PaginatedCollection;
import org.jclouds.aliyun.domain.Resource;
import org.jclouds.aliyun.internal.ParseInstances;
import org.jclouds.aliyun.options.PaginationOptions;
import org.jclouds.collect.PagedIterable;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.Transform;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2015/4/23.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v{jclouds.api-version}")
public interface InstanceApi {

    /**
     * @return a set of containers
     */
    @Named("instance:list")
    @GET
    @ResponseParser(ParseInstances.class)
    @Transform(ParseInstances.ToPagedIterable.class)
    @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
    PagedIterable<Resource> list();

    @Named("instance:list")
    @GET
    @ResponseParser(ParseInstances.class)
    @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
    PaginatedCollection<Resource> list(PaginationOptions options);

}

