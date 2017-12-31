import React, {Component} from 'react';
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import TaskListHeader from "../worldbank/TaskListHeader";
import getAspects from "../../reducers/index"


import {
  Row,
  Col,
  Label,
  Button,
  Badge,
  Table
} from 'reactstrap';


const TaskList = ({tasks}) => (
  <Row>
    <Col>
      <Table responsive size="sm" className={'task-table'}>
        <tbody>
          {tasks.map((item) => TaskItem(item.id, item.name, item.count, item.updated, item.status))}
        </tbody>
      </Table>
    </Col>
  </Row>
);

const TaskItem = (id, name, count, updated, status) => (
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
    <td className={'action'}>
      <Button color="link" className="text-muted"><i className="fa icon-reload"/></Button>
    </td>
    <td className={'Status'}>
      <Badge color={mapStatus(status)}>{status}</Badge>
    </td>
  </tr>
);

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

const TaskBoard = ({tasks}) => (
  <div className={'mb-3'}>
    <TaskListHeader caption={"Commons"}/>
    <TaskList tasks={tasks}/>
  </div>
);

TaskBoard.propTypes = {
  tasks: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    count: PropTypes.number,
    updated: PropTypes.string,
    status: PropTypes.string
  })).isRequired
};

const getCommonAspects = (state) => {
  return state.commonAspects
};

const mapStateToProps = (state) => ({
  tasks: getCommonAspects(state)
});

export default connect(mapStateToProps)(TaskBoard)
