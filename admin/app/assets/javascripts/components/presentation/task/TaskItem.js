import React from 'react';
import PropTypes from 'prop-types';

import {
  Badge,
  Button,
} from 'reactstrap';

const mapStatus = (status) => {
  let value;
  switch (status) {
    case 'success':
      value = 'success';
      break;
    case 'failure':
      value = 'danger';
      break;
    default:
      value = 'secondary';
  }
  return value;
};

const TaskItem = ({onLoad, id, name, count, updated, status}) => (
  <tr key={id}>
    <td className={'aspect'}>
      <div className="desc">
        <div className="title">{name}</div>
        <small>Items: {count}</small>
      </div>
    </td>
    <td className={'date'}>
      <div className="desc">
        <small>Updated:</small>
        <div className="title">{updated}</div>
      </div>
    </td>
    <td className={'Status'}>
      <Badge color={mapStatus(status)}>{status}</Badge>
    </td>
    <td className={'action'}>
      <Button color="link" className="text-muted" onClick={onLoad}><i className="fa icon-reload"/></Button>
    </td>
  </tr>
);

TaskItem.propTypes = {
  id: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
  count: PropTypes.number.isRequired,
  updated: PropTypes.string.isRequired,
  status: PropTypes.string.isRequired,
  onLoad: PropTypes.func.isRequired
};


export default TaskItem