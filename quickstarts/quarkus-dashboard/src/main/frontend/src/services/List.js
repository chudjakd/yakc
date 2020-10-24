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
import React from 'react';
import {connect} from 'react-redux'
import {bindActionCreators} from 'redux';
import metadata from '../metadata';
import redux from '../redux';
import svc from './';
import Icon from '../components/Icon';
import Link from '../components/Link';
import ResourceList from '../components/ResourceList';
import Table from '../components/Table';

const headers = [
  <span><Icon className='fa-id-card' /> Name</span>,
  'Namespace',
  'Type',
  'Cluster IP',
  ''
];

const sort = (p1, p2) =>
  metadata.selectors.creationTimestamp(p2) - metadata.selectors.creationTimestamp(p1);

const Rows = ({services, loadedResources, deleteServiceAction}) => {
  if (!loadedResources['Service']) {
    return <Table.Loading colSpan={headers.length} />;
  }
  const allServices = Object.values(services);
  if (allServices.length === 0) {
    return <Table.NoResultsRow colSpan={headers.length} />;
  }
  const deleteService = service => async () => {
    await svc.api.requestDelete(service);
    deleteServiceAction(service);
  };
  return allServices
    .sort(sort)
    .map(service => (
        <Table.Row key={metadata.selectors.uid(service)}>
          <Table.Cell>
            <Link.Service to={`/services/${metadata.selectors.uid(service)}`}>
              {metadata.selectors.name(service)}
            </Link.Service>
          </Table.Cell>
          <Table.Cell className='whitespace-no-wrap'>
            <Link.Namespace to={`/namespaces/${metadata.selectors.namespace(service)}`}>
              {metadata.selectors.namespace(service)}
            </Link.Namespace>
          </Table.Cell>
          <Table.Cell>
            <svc.Type service={service} />
          </Table.Cell>
          <Table.Cell>
            {svc.selectors.specClusterIP(service)}
          </Table.Cell>
          <Table.Cell>
            <Table.DeleteButton onClick={deleteService(service)} />
          </Table.Cell>
        </Table.Row>
    ));
};

const List = ({services, loadedResources, deleteServiceAction, ...properties}) => (
  <ResourceList headers={headers} {...properties}>
    <Rows services={services} loadedResources={loadedResources} deleteServiceAction={deleteServiceAction} />
  </ResourceList>
);

const mapStateToProps = ({services, ui: {loadedResources}}) => ({
  services,
  loadedResources
});

const mapDispatchToProps = dispatch =>  bindActionCreators({
  deleteServiceAction: redux.actions.crudDelete
}, dispatch);

const mergeProps = (stateProps, dispatchProps, ownProps) => ({
  ...stateProps,
  ...dispatchProps,
  ...ownProps,
  services: redux.selectors.resourcesBy(stateProps.services, ownProps)
});

export default connect(mapStateToProps, mapDispatchToProps, mergeProps)(List);

