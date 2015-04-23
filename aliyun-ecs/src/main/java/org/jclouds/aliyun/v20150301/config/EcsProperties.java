package org.jclouds.aliyun.v20150301.config;

/**
 * Created by Administrator on 2015/4/23.
 */

/**
 * Configuration properties and constants used in openstack Nova connections.
 */
public class EcsProperties {

    /**
     * Eventual consistency delay for retrieving a security group after it is created (in ms)
     */
    public static final String TIMEOUT_SECURITYGROUP_PRESENT = "jclouds.aliyun-ecs.timeout.securitygroup-present";

    /**
     * Whenever a node is created, automatically create and assign a floating ip address, also
     * delete when the node is destroyed.
     */
    public static final String AUTO_ALLOCATE_FLOATING_IPS = "jclouds.aliyun-ecs.auto-create-floating-ips";

    /**
     * Whenever a node is created, automatically generate keypairs for groups, as needed, also
     * delete the keypair(s) when the last node in the group is destroyed.
     */
    public static final String AUTO_GENERATE_KEYPAIRS = "jclouds.aliyun-ecs.auto-generate-keypairs";

}
