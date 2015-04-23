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
package org.jclouds.shipyard;

import org.jclouds.rest.annotations.Delegate;
import org.jclouds.shipyard.features.ContainersApi;
import org.jclouds.shipyard.features.EnginesApi;
import org.jclouds.shipyard.features.ImagesApi;
import org.jclouds.shipyard.features.ServiceKeysApi;

import java.io.Closeable;

public interface ShipyardApi extends Closeable {

   @Delegate
   ContainersApi containersApi();

   @Delegate
   ImagesApi imagesApi();
   
   @Delegate
   EnginesApi enginesApi();
   
   @Delegate
   ServiceKeysApi serviceKeysApi();
}
