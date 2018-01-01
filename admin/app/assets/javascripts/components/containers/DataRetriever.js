import React from 'react'
import Header from '../Header'
import WorldBank from '../containers/WorldBank'

import {
  Container,
  Row,
  Col
} from 'reactstrap';

const DataRetriever = () => (
  <div className={"app"}>
    <Header/>
    <Container fluid>
      <main className={"main"}>
        <div className={"animated fadeIn"}>
          <Row>
            <Col md={"1"}>
            </Col>
            <Col md={"10"}>
              <WorldBank/>
            </Col>
            <Col md={"1"}>
            </Col>
          </Row>
        </div>
      </main>
    </Container>
  </div>
);

export default DataRetriever