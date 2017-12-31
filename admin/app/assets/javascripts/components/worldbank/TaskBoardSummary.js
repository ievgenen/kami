import React, {Component} from 'react';
import {
  Row,
  Col,
  Label,
  Button,
} from 'reactstrap';

//https://reactjs.org/docs/typechecking-with-proptypes.html

class TaskBoardSummary extends Component {

  render() {

  const body = () => {
    return (<Row>
      <Col>

      </Col>
      <Col>

      </Col>
    </Row>
    )
  };


    return (
      <div>
        {body()}
      </div>
    )
  }
}

export default TaskBoardSummary