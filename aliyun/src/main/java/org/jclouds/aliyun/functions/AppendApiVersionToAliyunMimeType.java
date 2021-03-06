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
package org.jclouds.aliyun.functions;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.rest.annotations.ApiVersion;

import com.google.common.base.Function;

/**
 * Appends the Api version to the given mime type.
 */
@Singleton
public class AppendApiVersionToAliyunMimeType implements Function<String, String> {
   /** The prefix for Aliyun custom media types. */
   private static final String ABIQUO_MIME_TYPE_PREFIX = "application/vnd.aliyun.";

   /** The version to append to media types without version. */
   protected String apiVersion;

   @Inject
   AppendApiVersionToAliyunMimeType(@ApiVersion final String apiVersion) {
      this.apiVersion = checkNotNull(apiVersion, "apiVersion");
   }

   @Override
   public String apply(final String input) {
      checkNotNull(input, "input");
      if (input.startsWith(ABIQUO_MIME_TYPE_PREFIX) && !input.contains("version")) {
         return input + ";version=" + apiVersion;
      } else {
         return input;
      }
   }

}
