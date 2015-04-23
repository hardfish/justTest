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
package org.jclouds.aliyun;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.jclouds.View;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.apis.Apis;
import org.jclouds.apis.internal.BaseApiMetadataTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;

/**
 * Unit tests for the {@link AliyunApiMetadata} class.
 */
@Test(groups = "unit", testName = "AliyunApiMetadataTest")
// TODO: Change inheritance to BaseComputeApiMetadataTest once the
// ComputeService abstraction is implemented
public class AliyunApiMetadataTest extends BaseApiMetadataTest {

   public AliyunApiMetadataTest() {
      super(new AliyunApiMetadata(), ImmutableSet.<TypeToken<? extends View>> of());
   }

   public void testAliyunApiRegistered() {
      ApiMetadata api = Apis.withId("aliyun");

      assertNotNull(api);
      assertTrue(api instanceof AliyunApiMetadata);
      assertEquals(api.getId(), "aliyun");
   }

}
