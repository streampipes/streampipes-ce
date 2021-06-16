/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.streampipes.manager.execution.http;

import org.apache.streampipes.commons.constants.PipelineElementUrl;
import org.apache.streampipes.svcdiscovery.SpServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class PipelineElementEndpointGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(PipelineElementEndpointGenerator.class);

  private String appId;
  private String elementId;
  private PipelineElementUrl pipelineElementUrl;

  public PipelineElementEndpointGenerator(String elementId,
                                          String appId,
                                          PipelineElementUrl pipelineElementUrl) {
    this.elementId = elementId;
    this.appId = appId;
    this.pipelineElementUrl = pipelineElementUrl;
  }

  public String getEndpointResourceUrl() {
    List<String> endpoints = getServiceEndpoints();
    return pipelineElementUrl.getInvocationUrl(selectService(endpoints), appId);
  }

  private List<String> getServiceEndpoints() {
    return SpServiceDiscovery.getServiceDiscovery().getServiceEndpoints("pe", true, Collections.singletonList(this.appId));
  }

  private String selectService(List<String> availableServices) {
    return availableServices.get(0);
  }
}