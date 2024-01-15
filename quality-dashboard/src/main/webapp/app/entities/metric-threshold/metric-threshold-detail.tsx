import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './metric-threshold.reducer';

export const MetricThresholdDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const metricThresholdEntity = useAppSelector(state => state.metricThreshold.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="metricThresholdDetailsHeading">
          <Translate contentKey="qualitydashboardApp.metricThreshold.detail.title">MetricThreshold</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{metricThresholdEntity.id}</dd>
          <dt>
            <span id="entityName">
              <Translate contentKey="qualitydashboardApp.metricThreshold.entityName">Entity Name</Translate>
            </span>
          </dt>
          <dd>{metricThresholdEntity.entityName}</dd>
          <dt>
            <span id="metricName">
              <Translate contentKey="qualitydashboardApp.metricThreshold.metricName">Metric Name</Translate>
            </span>
          </dt>
          <dd>{metricThresholdEntity.metricName}</dd>
          <dt>
            <span id="successPercentage">
              <Translate contentKey="qualitydashboardApp.metricThreshold.successPercentage">Success Percentage</Translate>
            </span>
          </dt>
          <dd>{metricThresholdEntity.successPercentage}</dd>
          <dt>
            <span id="dangerPercentage">
              <Translate contentKey="qualitydashboardApp.metricThreshold.dangerPercentage">Danger Percentage</Translate>
            </span>
          </dt>
          <dd>{metricThresholdEntity.dangerPercentage}</dd>
        </dl>
        <Button tag={Link} to="/metric-threshold" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/metric-threshold/${metricThresholdEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MetricThresholdDetail;
