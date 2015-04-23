package org.jclouds.aliyun.features;

/**
 * Created by X0269 on 2015/4/23.
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

