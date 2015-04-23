package org.jclouds.aliyun.ecs;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.aliyun.v20150301.EcsApi;
import org.jclouds.aliyun.v20150301.domain.Resource;
import org.jclouds.aliyun.v20150301.features.InstanceApi;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

import java.io.IOException;

/**
 * Created by Administrator on 2015/4/23.
 */
public class testEcsInstanceCreate {
    private final EcsApi ecsApi;

    public static void main(String[] args) throws IOException {
        testEcsInstanceCreate test = new testEcsInstanceCreate();

        try {
            test.listInstances();
            test.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            test.close();
        }
    }
    public testEcsInstanceCreate() {
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());

        String provider = "openstack-nova";
        String identity = "admin:admin"; // tenantName:userName
        String credential = "123456";

        ecsApi = ContextBuilder.newBuilder(provider)
                .endpoint("http://121.41.120.176:5000/v2.0/")
                .credentials(identity, credential)
                .modules(modules)
                .buildApi(EcsApi.class);

    }

    private void listInstances() {
        InstanceApi instanceApi = ecsApi.getInstanceApi();

        for (Resource instance : instanceApi.list().concat()) {
            System.out.println("  " + instance);
        }

    }

    public void close() throws IOException {
        Closeables.close(ecsApi, true);
    }
}
