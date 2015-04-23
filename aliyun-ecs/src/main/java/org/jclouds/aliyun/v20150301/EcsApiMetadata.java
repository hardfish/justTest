package org.jclouds.aliyun.v20150301;

/**
 * Created by Administrator on 2015/4/23.
 */
import static org.jclouds.Constants.PROPERTY_SESSION_INTERVAL;
//import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.CREDENTIAL_TYPE;
//import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.SERVICE_TYPE;
import static org.jclouds.aliyun.v20150301.config.EcsProperties.AUTO_ALLOCATE_FLOATING_IPS;
import static org.jclouds.aliyun.v20150301.config.EcsProperties.AUTO_GENERATE_KEYPAIRS;
import static org.jclouds.aliyun.v20150301.config.EcsProperties.TIMEOUT_SECURITYGROUP_PRESENT;
import static org.jclouds.reflect.Reflection2.typeToken;

import java.net.URI;
import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.compute.ComputeServiceContext;
//import org.jclouds.openstack.keystone.v2_0.config.AuthenticationApiModule;
//import org.jclouds.openstack.keystone.v2_0.config.CredentialTypes;
//import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule;
//import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule.RegionModule;
//import org.jclouds.openstack.nova.v2_0.compute.config.EcsComputeServiceContextModule;
//import org.jclouds.openstack.nova.v2_0.config.EcsHttpApiModule;
//import org.jclouds.openstack.nova.v2_0.config.EcsParserModule;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * Implementation of {@link ApiMetadata} for Ecs 2.0 API
 */
@AutoService(ApiMetadata.class)
public class EcsApiMetadata extends BaseHttpApiMetadata<EcsApi>  {

    @Override
    public Builder toBuilder() {
        return new Builder().fromApiMetadata(this);
    }

    public EcsApiMetadata() {
        this(new Builder());
    }

    protected EcsApiMetadata(Builder builder) {
        super(builder);
    }

    public static Properties defaultProperties() {
        Properties properties = BaseHttpApiMetadata.defaultProperties();
        // auth fail can happen while cloud-init applies keypair updates
        properties.setProperty("jclouds.ssh.max-retries", "7");
        properties.setProperty("jclouds.ssh.retry-auth", "true");
        properties.setProperty(SERVICE_TYPE, ServiceType.COMPUTE);
        properties.setProperty(CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);
        properties.setProperty(AUTO_ALLOCATE_FLOATING_IPS, "false");
        properties.setProperty(AUTO_GENERATE_KEYPAIRS, "false");
        properties.setProperty(TIMEOUT_SECURITYGROUP_PRESENT, "500");
        // Keystone 1.1 expires tokens after 24 hours and allows renewal 1 hour
        // before expiry by default.  We choose a value less than the latter
        // since the former persists between jclouds invocations.
        properties.setProperty(PROPERTY_SESSION_INTERVAL, 30 * 60 + "");
        return properties;
    }

    public static class Builder extends BaseHttpApiMetadata.Builder<EcsApi, Builder> {

        protected Builder() {
            id("aliyun-ecs")
                    .name("Aliyun Ecs Diablo+ API")
                    .identityName("${tenantName}:${userName} or ${userName}, if your keystone supports a default tenant")
                    .credentialName("${password}")
                    .endpointName("Keystone base url ending in /v2.0/")
                    .documentation(URI.create("http://api.openstack.org/"))
                    .version("2")
                    .defaultEndpoint("http://localhost:5000/v2.0/")
                    .defaultProperties(EcsApiMetadata.defaultProperties())
                    .view(typeToken(ComputeServiceContext.class))
                    .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
                            .add(AuthenticationApiModule.class)
                            .add(KeystoneAuthenticationModule.class)
                            .add(RegionModule.class)
                            .add(EcsParserModule.class)
                            .add(EcsHttpApiModule.class)
                            .add(EcsComputeServiceContextModule.class).build());
        }

        @Override
        public EcsApiMetadata build() {
            return new EcsApiMetadata(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

