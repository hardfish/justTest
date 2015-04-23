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
package org.jclouds.snia.cdmi.v1.features;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jclouds.Fallbacks.NullOnNotFoundOr404;
import static org.jclouds.snia.cdmi.v1.ObjectTypes.CONTAINER;

import java.io.Closeable;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.snia.cdmi.v1.binders.BindQueryParmsToSuffix;
import org.jclouds.snia.cdmi.v1.domain.Container;
import org.jclouds.snia.cdmi.v1.filters.BasicAuthenticationAndTenantId;
import org.jclouds.snia.cdmi.v1.filters.StripExtraAcceptHeader;
import org.jclouds.snia.cdmi.v1.options.CreateContainerOptions;
import org.jclouds.snia.cdmi.v1.queryparams.ContainerQueryParams;

/** CDMI Container Object Resource Operations */
@RequestFilters({ BasicAuthenticationAndTenantId.class, StripExtraAcceptHeader.class })
@Headers(keys = "X-CDMI-Specification-Version", values = "{jclouds.api-version}")
public interface ContainerApi extends Closeable {

   /**
    * get CDMI Container
    * 
    * @param containerName
    *           containerName must end with a forward slash, /.
    * @return Container
    * 
    *         <pre>
    *  Examples: 
    *  {@code
    *  container = get("myContainer/");
    *  container = get("parentContainer/childContainer/");
    * }
    * 
    *         <pre>
    */
   @GET
   @Consumes({ CONTAINER, APPLICATION_JSON })
   @Fallback(NullOnNotFoundOr404.class)
   @Path("/{containerName}")
   Container get(@PathParam("containerName") String containerName);

   /**
    * get CDMI Container
    * 
    * @param containerName
    * @param queryParams
    *           enables getting only certain fields, metadata, children range
    * @return Container
    * 
    *         <pre>
    * Examples: 
    * {@code
    * container = get("myContainer/",ContainerQueryParams.Builder.mimetype("text/plain").field("objectName"))
    * container = get("myContainer/",ContainerQueryParams.Builder.metadata().field("objectName"))
    * }
    * </pre>
    * @see ContainerQueryParams
    */
   @GET
   @Consumes({ CONTAINER, APPLICATION_JSON })
   @Fallback(NullOnNotFoundOr404.class)
   @Path("/{containerName}")
   Container get(@PathParam("containerName") String containerName,
         @BinderParam(BindQueryParmsToSuffix.class) ContainerQueryParams queryParams);

   /**
    * Create CDMI Container
    * 
    * @param containerName
    *           containerName must end with a forward slash, /.
    * @return Container
    * 
    *         <pre>
    *  Examples: 
    *  {@code
    *  container = create("myContainer/");
    *  container = create("parentContainer/childContainer/");
    *  }
    * </pre>
    */
   @PUT
   @Consumes({ CONTAINER, APPLICATION_JSON })
   @Produces(CONTAINER)
   @Fallback(NullOnNotFoundOr404.class)
   @Path("/{containerName}")
   Container create(@PathParam("containerName") String containerName);

   /**
    * Create CDMI Container
    * 
    * @param containerName
    * @param options
    *           enables adding metadata
    * @return Container
    * 
    *         <pre>
    *  Examples: 
    *  {@code
    *  container = create("myContainer/",CreateContainerOptions.Builder..metadata(metaDataIn));
    *  }
    * </pre>
    * @see CreateContainerOptions
    */
   @PUT
   @Consumes({ CONTAINER, APPLICATION_JSON })
   @Produces(CONTAINER)
   @Fallback(NullOnNotFoundOr404.class)
   @Path("/{containerName}")
   Container create(@PathParam("containerName") String containerName, CreateContainerOptions... options);

   /**
    * Delete CDMI Container
    * 
    * @param containerName
    */
   @DELETE
   @Consumes(APPLICATION_JSON)
   @Fallback(NullOnNotFoundOr404.class)
   @Path("/{containerName}")
   void delete(@PathParam("containerName") String containerName);
}
