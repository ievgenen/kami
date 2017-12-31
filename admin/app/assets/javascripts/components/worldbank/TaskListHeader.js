import React, {Component} from 'react';
import PropTypes from 'prop-types'

import {
  Row,
  Col,
  Label,
  Button,
} from 'reactstrap';

const TaskListHeader = ({caption}) => (
  <Row>
    <Col className={'task-list-name'}>
      <Label><strong>{caption}</strong></Label>
    </Col>
    <Col className={'load-all'}>
      <Button color={'link'}>Load all</Button>
    </Col>
  </Row>
);

TaskListHeader.propTypes = {
  caption: PropTypes.string.isRequired
};

export default TaskListHeader