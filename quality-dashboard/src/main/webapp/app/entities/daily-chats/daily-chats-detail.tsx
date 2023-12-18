import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './daily-chats.reducer';

export const DailyChatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dailyChatsEntity = useAppSelector(state => state.dailyChats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dailyChatsDetailsHeading">
          <Translate contentKey="qualitydashboardApp.dailyChats.detail.title">DailyChats</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.id}</dd>
          <dt>
            <span id="day">
              <Translate contentKey="qualitydashboardApp.dailyChats.day">Day</Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.day ? <TextFormat value={dailyChatsEntity.day} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="totalDailyReceivedChats">
              <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyReceivedChats">Total Daily Received Chats</Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.totalDailyReceivedChats}</dd>
          <dt>
            <span id="totalDailyConversationChatsTimeInMin">
              <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyConversationChatsTimeInMin">
                Total Daily Conversation Chats Time In Min
              </Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.totalDailyConversationChatsTimeInMin}</dd>
          <dt>
            <span id="totalDailyAttendedChats">
              <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyAttendedChats">Total Daily Attended Chats</Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.totalDailyAttendedChats}</dd>
          <dt>
            <span id="totalDailyMissedChats">
              <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyMissedChats">Total Daily Missed Chats</Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.totalDailyMissedChats}</dd>
          <dt>
            <span id="avgDailyConversationChatsTimeInMin">
              <Translate contentKey="qualitydashboardApp.dailyChats.avgDailyConversationChatsTimeInMin">
                Avg Daily Conversation Chats Time In Min
              </Translate>
            </span>
          </dt>
          <dd>{dailyChatsEntity.avgDailyConversationChatsTimeInMin}</dd>
        </dl>
        <Button tag={Link} to="/daily-chats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/daily-chats/${dailyChatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DailyChatsDetail;
