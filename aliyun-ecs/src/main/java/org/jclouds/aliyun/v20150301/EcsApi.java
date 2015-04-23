package org.jclouds.aliyun.v20150301;

import org.jclouds.aliyun.v20150301.features.InstanceApi;
import org.jclouds.rest.annotations.Delegate;

import java.io.Closeable;

/**
 * Created by Administrator on 2015/4/23.
 */
public interface EcsApi extends Closeable {

    @Delegate
    InstanceApi getInstanceApi();

}
