import React from 'react';
import PropTypes from 'prop-types';

import {
  Row,
  Col,
  Label,
  Button,
} from 'reactstrap';

const ListHeader = ({caption, onLoadAll}) => (
  <Row className={"board-header"}>
    <Col>
      <Label><strong>{caption}</strong></Label>
    </Col>
    <Col className={'load-all'}>
      <Button color={'link'} onClick={onLoadAll}>Load all</Button>
    </Col>
  </Row>
);

ListHeader.propTypes = {
  caption: PropTypes.string.isRequired,
  onLoadAll: PropTypes.func.isRequired

};

export default ListHeader