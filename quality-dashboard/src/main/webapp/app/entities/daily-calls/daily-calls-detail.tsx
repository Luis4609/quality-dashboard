import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './daily-calls.reducer';

export const DailyCallsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dailyCallsEntity = useAppSelector(state => state.dailyCalls.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dailyCallsDetailsHeading">
          <Translate contentKey="qualitydashboardApp.dailyCalls.detail.title">DailyCalls</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.id}</dd>
          <dt>
            <span id="day">
              <Translate contentKey="qualitydashboardApp.dailyCalls.day">Day</Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.day ? <TextFormat value={dailyCallsEntity.day} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="totalDailyReceivedCalls">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyReceivedCalls">Total Daily Received Calls</Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyReceivedCalls}</dd>
          <dt>
            <span id="totalDailyAttendedCalls">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyAttendedCalls">Total Daily Attended Calls</Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyAttendedCalls}</dd>
          <dt>
            <span id="totalDailyMissedCalls">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyMissedCalls">Total Daily Missed Calls</Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyMissedCalls}</dd>
          <dt>
            <span id="totalDailyReceivedCallsExternalAgent">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyReceivedCallsExternalAgent">
                Total Daily Received Calls External Agent
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyReceivedCallsExternalAgent}</dd>
          <dt>
            <span id="totalDailyAttendedCallsExternalAgent">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyAttendedCallsExternalAgent">
                Total Daily Attended Calls External Agent
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyAttendedCallsExternalAgent}</dd>
          <dt>
            <span id="totalDailyReceivedCallsInternalAgent">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyReceivedCallsInternalAgent">
                Total Daily Received Calls Internal Agent
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyReceivedCallsInternalAgent}</dd>
          <dt>
            <span id="totalDailyAttendedCallsInternalAgent">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyAttendedCallsInternalAgent">
                Total Daily Attended Calls Internal Agent
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyAttendedCallsInternalAgent}</dd>
          <dt>
            <span id="totalDailyCallsTimeInMin">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyCallsTimeInMin">Total Daily Calls Time In Min</Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyCallsTimeInMin}</dd>
          <dt>
            <span id="totalDailyCallsTimeExternalAgentInMin">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyCallsTimeExternalAgentInMin">
                Total Daily Calls Time External Agent In Min
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyCallsTimeExternalAgentInMin}</dd>
          <dt>
            <span id="totalDailyCallsTimeInternalAgentInMin">
              <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyCallsTimeInternalAgentInMin">
                Total Daily Calls Time Internal Agent In Min
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.totalDailyCallsTimeInternalAgentInMin}</dd>
          <dt>
            <span id="avgDailyOperationTimeExternalAgentInMin">
              <Translate contentKey="qualitydashboardApp.dailyCalls.avgDailyOperationTimeExternalAgentInMin">
                Avg Daily Operation Time External Agent In Min
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.avgDailyOperationTimeExternalAgentInMin}</dd>
          <dt>
            <span id="avgDailyOperationTimeInternalAgentInMin">
              <Translate contentKey="qualitydashboardApp.dailyCalls.avgDailyOperationTimeInternalAgentInMin">
                Avg Daily Operation Time Internal Agent In Min
              </Translate>
            </span>
          </dt>
          <dd>{dailyCallsEntity.avgDailyOperationTimeInternalAgentInMin}</dd>
        </dl>
        <Button tag={Link} to="/daily-calls" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/daily-calls/${dailyCallsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DailyCallsDetail;
