import React from "react";
import PropTypes from "prop-types";
import TaskItem from "../task/TaskItem"

import {
  Row,
  Col,
  Table
} from 'reactstrap';

const TaskList = ({tasks}) => (
  <Row>
    <Col>
      <Table responsive size="sm" className={'task-table'}>
        <tbody>
        {tasks.map((item) =>
          <TaskItem key={item.id} onLoad={() => console.log("onLoad Clicked!" + item.id)} {...item} />
        )}
        </tbody>
      </Table>
    </Col>
  </Row>
);

TaskList.propTypes = {
  tasks: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    count: PropTypes.number.isRequired,
    updated: PropTypes.string.isRequired,
    status: PropTypes.string.isRequired
  }).isRequired).isRequired
};


export default TaskList