/*
 * Copyright 2020 Marc Nuri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created on 2020-09-23, 19:20
 */
package com.marcnuri.yakc.quickstarts.dashboard.service;

import com.marcnuri.yakc.model.io.k8s.api.core.v1.Service;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.List;

@Singleton
public class ServiceResource {

  private final ServiceService serviceService;

  @Inject
  public ServiceResource(
    ServiceService serviceService) {
    this.serviceService = serviceService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Service> get() throws IOException {
    return serviceService.get();
  }
}