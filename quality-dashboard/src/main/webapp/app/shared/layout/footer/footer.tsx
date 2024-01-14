import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

const Footer = () => (
  <Container>
    <div className="footer page-content">
      <Row>
        <Col md="12">
          <p>
          ©<Translate contentKey="footer">Your footer</Translate>
          </p>
        </Col>
      </Row>
    </div>
  </Container>
);

export default Footer;
