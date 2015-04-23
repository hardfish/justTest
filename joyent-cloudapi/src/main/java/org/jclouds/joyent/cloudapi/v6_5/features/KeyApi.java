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
package org.jclouds.joyent.cloudapi.v6_5.features;

import static org.jclouds.Fallbacks.EmptySetOnNotFoundOr404;
import static org.jclouds.Fallbacks.NullOnNotFoundOr404;
import static org.jclouds.Fallbacks.VoidOnNotFoundOr404;

import java.util.Set;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.joyent.cloudapi.v6_5.domain.Key;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.binders.BindToJsonPayload;


/**
 * Keys are the means by which you operate on your SSH/signing keys. Currently
 * CloudAPI supports uploads of public keys in the OpenSSH format.
 */
@Headers(keys = "X-Api-Version", values = "{jclouds.api-version}")
@RequestFilters(BasicAuthentication.class)
@Path("/my/keys")
@Consumes(MediaType.APPLICATION_JSON)
public interface KeyApi {

   /**
    * Lists all public keys we have on record for the specified account.
    */
   @Named("ListKeys")
   @GET
   @Fallback(EmptySetOnNotFoundOr404.class)
   Set<Key> list();

   /**
    * Retrieves an individual key record.
    */
   @Named("GetKey")
   @GET
   @Path("/{name}")
   @Fallback(NullOnNotFoundOr404.class)
   Key get(@PathParam("name") String name);

   /**
    * Uploads a new OpenSSH key to SmartDataCenter for use in SSH and HTTP
    * signing.
    */
   @Named("CreateKey")
   @POST
   Key create(@BinderParam(BindToJsonPayload.class) Key key);

   /**
    * Deletes an SSH key by name.
    */
   @Named("DeleteKey")
   @DELETE
   @Path("/{name}")
   @Fallback(VoidOnNotFoundOr404.class)
   void delete(@PathParam("name") String name);
}
