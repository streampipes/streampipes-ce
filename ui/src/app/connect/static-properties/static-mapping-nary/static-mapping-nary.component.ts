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

import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {StaticPropertyUtilService} from '../static-property-util.service';
import {MappingPropertyNary} from "../../model/MappingPropertyNary";
import {StaticMappingComponent} from "../static-mapping/static-mapping";
import {PropertySelectorService} from "../../../services/property-selector.service";
import {EventProperty} from "../../schema-editor/model/EventProperty";
import {EventSchema} from "../../schema-editor/model/EventSchema";


@Component({
    selector: 'app-static-mapping-nary',
    templateUrl: './static-mapping-nary.component.html',
    styleUrls: ['./static-mapping-nary.component.scss']
})
export class StaticMappingNaryComponent extends StaticMappingComponent implements OnInit {


    @Input() staticProperty: MappingPropertyNary;
    @Input() eventSchema: EventSchema;
    @Output() inputEmitter: EventEmitter<Boolean> = new EventEmitter<Boolean>();

    private inputValue: String;
    private hasInput: Boolean;
    availableProperties: Array<EventProperty>;

    constructor(staticPropertyUtil: StaticPropertyUtilService,
                PropertySelectorService: PropertySelectorService){
        super(staticPropertyUtil, PropertySelectorService);
    }

    ngOnInit() {
        this.availableProperties = this.extractPossibleSelections();
        this.availableProperties.forEach(ep => ep.propertySelector = this.firstStreamPropertySelector + ep.runtimeName);
        if (!this.staticProperty.selectedProperties) {
            this.selectNone();
        } else {
            this.availableProperties.forEach(ep => {
                if (this.staticProperty.selectedProperties.indexOf(ep.propertySelector) > -1) {
                    ep["checked"] = true;
                }
            })
        }
        this.inputEmitter.emit(true);
    }

    selectOption(property: EventProperty, $event) {
        if (property["checked"]) {
            this.addProperty(property);
        } else {
            this.staticProperty.selectedProperties.splice(this.staticProperty.selectedProperties.indexOf(this.makeSelector(property)), 1);
            property["checked"] = false;
        }
    }

    addProperty(property: EventProperty) {
        if (this.staticProperty.selectedProperties.indexOf(property.propertySelector) < 0) {
            this.staticProperty.selectedProperties.push(this.makeSelector(property));
        }
    }

    makeSelector(property: EventProperty) {
        return this.firstStreamPropertySelector + property.runtimeName;
    }

    valueChange(inputValue) {
        this.inputValue = inputValue;
        if (inputValue == "" || !inputValue) {
            this.hasInput = false;
        }
        else {
            this.hasInput = true;
        }

        this.inputEmitter.emit(this.hasInput);
    }

    extractPossibleSelections(): Array<EventProperty> {
        return this.eventSchema.eventProperties.filter(ep => this.isInSelection(ep));
    }

    isInSelection(ep: EventProperty): boolean {
        return this.staticProperty.mapsFromOptions.some(maps => maps === this.firstStreamPropertySelector + ep.runtimeName);
    }

    selectAll() {
        this.selectNone();
        this.availableProperties.forEach(ep => {
            ep["checked"] = true;
            this.addProperty(ep);
        })
    }

    selectNone() {
        this.staticProperty.selectedProperties = [];
        this.availableProperties.forEach(ep => {
            ep["checked"] = false;
        });
    }

}