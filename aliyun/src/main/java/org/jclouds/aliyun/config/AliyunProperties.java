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
package org.jclouds.aliyun.config;

/**
 * Configuration properties and constants used in Aliyun connections.
 */
public final class AliyunProperties {
   /**
    * Credential type to be used to authenticate against the Aliyun Api.
    * <p>
    * Valid values: password, token (deprecated).
    */
   public static final String CREDENTIAL_TYPE = "aliyun.credential-type";

   /**
    * The name of the Aliyun logger.
    */
   public static final String ABIQUO_LOGGER = "jclouds.aliyun";

   private AliyunProperties() {
      throw new AssertionError("intentionally unimplemented");
   }
}
