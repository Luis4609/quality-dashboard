import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Card, CardBody, CardTitle, Col, Row } from 'reactstrap';

function DailyCallsCards() {
  return (
    <>
      <div style={{ width: '18rem' }}>
        <Card className="card-stats mb-4">
          <CardBody>
            <Row>
              <div className="col">
                <CardTitle className="text-uppercase text-muted mb-0">Total traffic</CardTitle>
                <span className="h2 font-weight-bold mb-0">350,897</span>
              </div>
              <Col className="col-auto">
                <div className="icon icon-shape rounded-circle">
                  <FontAwesomeIcon icon="phone" size='xl' />
                </div>
              </Col>
            </Row>
            <p className="mt-3 mb-0 mr text-muted text-sm">
              <span className="text-success mr-2">
                <FontAwesomeIcon icon="arrow-up" />
                3.48%
              </span>
              <span className="text-nowrap">Since last month</span>
            </p>
          </CardBody>
        </Card>
      </div>
    </>
  );
}

export default DailyCallsCards;
