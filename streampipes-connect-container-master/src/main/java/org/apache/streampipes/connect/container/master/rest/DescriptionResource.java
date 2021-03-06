/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.streampipes.connect.container.master.rest;

import org.apache.streampipes.connect.adapter.exception.AdapterException;
import org.apache.streampipes.connect.container.master.management.DescriptionManagement;
import org.apache.streampipes.connect.container.master.management.Utils;
import org.apache.streampipes.model.connect.adapter.AdapterDescription;
import org.apache.streampipes.model.connect.adapter.AdapterDescriptionList;
import org.apache.streampipes.model.connect.grounding.FormatDescriptionList;
import org.apache.streampipes.model.connect.grounding.ProtocolDescription;
import org.apache.streampipes.model.connect.grounding.ProtocolDescriptionList;
import org.apache.streampipes.rest.shared.annotation.JacksonSerialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/v2/connect/{username}/master/description")
public class DescriptionResource extends AbstractAdapterResource<DescriptionManagement> {

    private static final Logger LOG = LoggerFactory.getLogger(DescriptionResource.class);

    public DescriptionResource() {
        super(DescriptionManagement::new);
    }

    @GET
    @JacksonSerialized
    @Path("/formats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFormats() {
        FormatDescriptionList result = managementService.getFormats();

        return ok(result);
    }

    @GET
    @JacksonSerialized
    @Path("/protocols")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProtocols() {
        ProtocolDescriptionList result = managementService.getProtocols();

        return ok(result);
    }

    @GET
    @JacksonSerialized
    @Path("/adapters")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdapters() {
        AdapterDescriptionList result = managementService.getAdapters();

        return ok(result);
    }

    @GET
    @Path("/{id}/assets")
    @Produces("application/zip")
    public Response getAdapterAssets(@PathParam("id") String id, @PathParam("username") String userName) {
        try {
            String result = null;

            Optional<AdapterDescription> adapterDescriptionOptional = managementService.getAdapter(id);
            if (adapterDescriptionOptional.isPresent()) {
                AdapterDescription adapterDescription = adapterDescriptionOptional.get();
                String workerUrl = new Utils().getWorkerUrl(adapterDescription);
                String newUrl = Utils.addUserNameToApi(workerUrl, userName);

                result = managementService.getAdapterAssets(adapterDescription, newUrl);
            }

            Optional<ProtocolDescription> protocolDescriptionOptional  = managementService.getProtocol(id);
            if (protocolDescriptionOptional.isPresent()) {
                ProtocolDescription protocolDescription = protocolDescriptionOptional.get();
                String workerUrl = new Utils().getWorkerUrl(protocolDescription);
                String newUrl = Utils.addUserNameToApi(workerUrl, userName);

                result = managementService.getProtocolAssets(protocolDescription, newUrl);
            }

            if (result == null) {
                LOG.error("Not found adapter with id " + id);
                return fail();
            } else {
                return ok(result);
            }
        } catch (AdapterException e) {
            LOG.error("Not found adapter with id " + id, e);
            return fail();
        }
    }

    @GET
    @Path("/{id}/assets/icon")
    @Produces("image/png")
    public Response getAdapterIconAsset(@PathParam("id") String id, @PathParam("username") String userName) {
        try {

            byte[] result = null;

            Optional<AdapterDescription> adapterDescriptionOptional = managementService.getAdapter(id);
            if (adapterDescriptionOptional.isPresent()) {
                AdapterDescription adapterDescription = adapterDescriptionOptional.get();
                String workerUrl = new Utils().getWorkerUrl(adapterDescription);
                String newUrl = Utils.addUserNameToApi(workerUrl, userName);

                result = managementService.getAdapterIconAsset(adapterDescription, newUrl);
            }

            Optional<ProtocolDescription> protocolDescriptionOptional  = managementService.getProtocol(id);
            if (protocolDescriptionOptional.isPresent()) {
                ProtocolDescription protocolDescription = protocolDescriptionOptional.get();
                String workerUrl = new Utils().getWorkerUrl(protocolDescription);
                String newUrl = Utils.addUserNameToApi(workerUrl, userName);

                result = managementService.getProtocolIconAsset(protocolDescription, newUrl);
            }

            if (result == null) {
                LOG.error("Not found adapter with id " + id);
                return fail();
            } else {
                return ok(result);
            }
        } catch (AdapterException e) {
            LOG.error("Not found adapter with id " + id);
            return fail();
        }
    }

    @GET
    @Path("/{id}/assets/documentation")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAdapterDocumentationAsset(@PathParam("id") String id, @PathParam("username") String userName) {
        try {
            String result = null;

            Optional<AdapterDescription> adapterDescriptionOptional = managementService.getAdapter(id);
            if (adapterDescriptionOptional.isPresent()) {
                AdapterDescription adapterDescription = adapterDescriptionOptional.get();
                String workerUrl = new Utils().getWorkerUrl(adapterDescription);
                String newUrl = Utils.addUserNameToApi(workerUrl, userName);

                result =  managementService.getAdapterDocumentationAsset(adapterDescription, newUrl);
            }

            Optional<ProtocolDescription> protocolDescriptionOptional  = managementService.getProtocol(id);
            if (protocolDescriptionOptional.isPresent()) {
                ProtocolDescription protocolDescription = protocolDescriptionOptional.get();
                String workerUrl = new Utils().getWorkerUrl(protocolDescription);
                String newUrl = Utils.addUserNameToApi(workerUrl, userName);

                result =  managementService.getProtocolDocumentationAsset(protocolDescription, newUrl);
            }

            if (result == null) {
                LOG.error("Not found adapter with id " + id);
                return fail();
            } else {
                return ok(result);
            }
        } catch (AdapterException e) {
            LOG.error("Not found adapter with id " + id, e);
            return fail();
        }
    }
}
