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
import PropTypes from 'prop-types';
import {connect} from 'react-redux'
import metadata from '../metadata';
import p from './'
import Icon from '../components/Icon';
import Link from '../components/Link';
import ResourceList from '../components/ResourceList';
import Table from '../components/Table';

const headers = [
  '',
  <span><Icon icon='fa-id-card' /> Name</span>,
  'Namespace',
  'Status',
  'Restarts',
  ''
];

const Rows = ({pods}) => {
  const allPods = Object.values(pods);
  if (allPods.length === 0) {
    return <Table.NoResultsRow colSpan={headers.length} />;
  }
  const deletePod = pod => async () => await p.api.requestDelete(pod);
  return allPods
    .sort(metadata.selectors.sortByCreationTimeStamp)
    .map(pod => (
      <Table.Row key={metadata.selectors.uid(pod)}>
        <Table.Cell className='whitespace-no-wrap w-3 text-center'>
          <Icon
            className={p.selectors.succeededOrContainersReady(pod) ? 'text-green-500' : 'text-red-500'}
            icon={p.selectors.succeededOrContainersReady(pod) ? 'fa-check' : 'fa-exclamation-circle'}
          />
        </Table.Cell>
        <Table.Cell>
          <Link.Pod to={`/pods/${metadata.selectors.uid(pod)}`}>
            {metadata.selectors.name(pod)}
          </Link.Pod>
        </Table.Cell>
        <Table.Cell className='whitespace-no-wrap'>
          <Link.Namespace to={`/namespaces/${metadata.selectors.namespace(pod)}`}>
            {metadata.selectors.namespace(pod)}
          </Link.Namespace>
        </Table.Cell>
        <Table.Cell className='whitespace-no-wrap'>
          <p.StatusIcon
            className='mr-1'
            statusPhase={p.selectors.statusPhase(pod)}
          />
          {p.selectors.statusPhase(pod)}
        </Table.Cell>
        <Table.Cell >
          {p.selectors.restartCount(pod)}
        </Table.Cell>
        <Table.Cell className='whitespace-no-wrap text-center'>
          <Link.RouterLink
            variant={Link.variants.outline}
            to={`/pods/${metadata.selectors.uid(pod)}/logs`}
            title='Logs'
          ><Icon stylePrefix='far' icon='fa-file-alt' /></Link.RouterLink>
          <Table.DeleteButton
            className='ml-1' onClick={deletePod(pod)} />
        </Table.Cell>
      </Table.Row>
    ));
}

const List = ({pods, nodeName, ownerUids, ...properties}) => (
  <ResourceList headers={headers} {...properties}>
    <Rows pods={pods} />
  </ResourceList>
);

const mapStateToProps = ({pods}) => ({
  pods
});

const mergeProps = (stateProps, dispatchProps, ownProps) => ({
  ...stateProps,
  ...dispatchProps,
  ...ownProps,
  pods: p.selectors.podsBy(stateProps.pods, ownProps)
});

List.propTypes = {
  nodeName: PropTypes.string,
  ownerUids: PropTypes.arrayOf(PropTypes.string),
  namespace: PropTypes.string
};

export default connect(mapStateToProps, null, mergeProps)(List);

