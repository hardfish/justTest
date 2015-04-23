package org.jclouds.aliyun.v20150301.internal;

/**
 * Created by Administrator on 2015/4/23.
 */

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.internal.Arg0ToPagedIterable;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.json.Json;
import org.jclouds.aliyun.v20150301.features.InstanceApi;
import org.jclouds.aliyun.v20150301.internal.ParseInstances.Instances;
import org.jclouds.aliyun.v20150301.domain.Link;
import org.jclouds.aliyun.v20150301.domain.PaginatedCollection;
import org.jclouds.aliyun.v20150301.domain.Resource;
import org.jclouds.aliyun.v20150301.options.PaginationOptions;
import org.jclouds.aliyun.v20150301.EcsApi;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.inject.TypeLiteral;

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

        private final EcsApi api;

        @Inject
        protected ToPagedIterable(EcsApi api) {
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