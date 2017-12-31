import React, {Component} from 'react';
import TaskBoard from './TaskBoard'

import {
  Row,
  Col,
  Card,
  CardHeader,
  CardBody,
} from 'reactstrap';


class TaskForm extends Component {

  render() {
    return(
      <Card className={"wb-form"}>
        <CardHeader className={'cardHeader'}>
          <strong>World Bank Data Retrieval</strong>
        </CardHeader>
        <CardBody>
          <Row>
            <Col>
              <div className={"mt-3"}>
                <TaskBoard/>
              </div>
            </Col>
          </Row>
        </CardBody>
      </Card>
    )
  }
}

export default TaskForm