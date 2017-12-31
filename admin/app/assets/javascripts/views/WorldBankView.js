import React, {Component} from 'react';

import {
  Row,
  Col,
} from 'reactstrap';

import TaskForm from '../components/worldbank/TaskForm';


class WorldBankView extends Component {
  render() {
    return (
      <div className={'mt-3'}>
        <Row>
          <Col>
            <TaskForm/>
          </Col>
        </Row>
      </div>
    );
  }
}

export default WorldBankView;