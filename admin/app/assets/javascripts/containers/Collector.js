import React, {Component} from 'react'
import Header from '../../javascripts/components/Header'
import WorldBankView from '../views/WorldBankView'

import {
  Container,
  Row,
  Col
} from 'reactstrap';


class Collector extends Component {
  render() {
    return (
      <div className={"app"}>
        <Header/>
        <Container fluid>
          <main className={"main"}>
              <div className={"animated fadeIn"}>
                <Row>
                  <Col md={"1"}>
                  </Col>
                  <Col md={"10"} >
                    <WorldBankView/>
                  </Col>
                  <Col md={"1"}>
                  </Col>
                </Row>
              </div>
          </main>
        </Container>
      </div>
    )
  }
}

export default Collector