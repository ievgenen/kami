import React from 'react';

import {
  Row,
  Col,
  Card,
  CardHeader,
  CardBody,
} from 'reactstrap';

import TaskBoard from '../containers/TaskBoard'


const WorldBank = () => (
  <div className={'mt-3'}>
    <Row>
      <Col>
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
      </Col>
    </Row>
  </div>
);

export default WorldBank;