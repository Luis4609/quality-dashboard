import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import DailyCallsDashboard from './components/DailyCallsDashboard';

export const DailyCallsMetrics = () => {
  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="dailyCallsMetricsExcel">
            <Translate contentKey="qualitydashboardApp.dailyCalls.metrics.title">Calls Metrics</Translate>
          </h2>
        </Col>
      </Row>
      <DailyCallsDashboard></DailyCallsDashboard>
    </>
  );
};

export default DailyCallsMetrics;
