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

package org.apache.streampipes.model.output;

import io.fogsy.empire.annotations.RdfProperty;
import io.fogsy.empire.annotations.RdfsClass;
import org.apache.streampipes.model.schema.EventProperty;
import org.apache.streampipes.model.util.Cloner;
import org.apache.streampipes.vocabulary.StreamPipes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@RdfsClass(StreamPipes.APPEND_OUTPUT_STRATEGY)
@Entity
public class AppendOutputStrategy extends OutputStrategy {

  private static final long serialVersionUID = 7202888911899551012L;

  @OneToMany(fetch = FetchType.EAGER,
          cascade = {CascadeType.ALL})
  @RdfProperty(StreamPipes.APPENDS_PROPERTY)
  private List<EventProperty> eventProperties;

  public AppendOutputStrategy() {
    super();
    eventProperties = new ArrayList<>();
  }

  public AppendOutputStrategy(AppendOutputStrategy other) {
    super(other);
    this.setEventProperties(new Cloner().properties(other.getEventProperties()));
  }

  public AppendOutputStrategy(List<EventProperty> eventProperties) {
    super();
    this.eventProperties = eventProperties;
  }

  public List<EventProperty> getEventProperties() {
    return eventProperties;
  }

  public void setEventProperties(List<EventProperty> eventProperties) {
    this.eventProperties = eventProperties;
  }


}
