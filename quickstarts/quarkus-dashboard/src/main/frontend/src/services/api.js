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
 */
import {getApiURL} from '../env';
import {fixKind, toJson} from '../fetch';
import metadata from '../metadata';

const api = {};

api.list = async () => {
  const response = await fetch(
    `${getApiURL()}/services`
  );
  const rawList =  await toJson(response);
  return fixKind('Service')(rawList);
};

api.requestDelete = async service => {
  await fetch(
    `${getApiURL()}/services/${metadata.selectors.namespace(service)}/${metadata.selectors.name(service)}`,
    {method: 'DELETE'}
  );
};

api.update = async service => {
  const headers = new Headers();
  headers.set('Content-Type', 'application/json');
  const response = await fetch(
    `${getApiURL()}/services/${metadata.selectors.namespace(service)}/${metadata.selectors.name(service)}`,
    {
      method: 'PUT',
      headers,
      body: JSON.stringify(service)
    }
  );
  return await toJson(response);
};

export default api;
