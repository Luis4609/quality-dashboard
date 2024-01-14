import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './daily-chats.reducer';

export const DailyChatsUpdate = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const isEdit = location.pathname.includes('edit');

  const dailyChatsEntity = useAppSelector(state => state.dailyChats.entity);
  const loading = useAppSelector(state => state.dailyChats.loading);
  const updating = useAppSelector(state => state.dailyChats.updating);
  const updateSuccess = useAppSelector(state => state.dailyChats.updateSuccess);

  const handleClose = () => {
    navigate('/daily-chats' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.totalDailyReceivedChats !== undefined && typeof values.totalDailyReceivedChats !== 'number') {
      values.totalDailyReceivedChats = Number(values.totalDailyReceivedChats);
    }
    if (values.totalDailyConversationChatsTimeInMin !== undefined && typeof values.totalDailyConversationChatsTimeInMin !== 'number') {
      values.totalDailyConversationChatsTimeInMin = Number(values.totalDailyConversationChatsTimeInMin);
    }
    if (values.totalDailyAttendedChats !== undefined && typeof values.totalDailyAttendedChats !== 'number') {
      values.totalDailyAttendedChats = Number(values.totalDailyAttendedChats);
    }
    if (values.totalDailyMissedChats !== undefined && typeof values.totalDailyMissedChats !== 'number') {
      values.totalDailyMissedChats = Number(values.totalDailyMissedChats);
    }
    if (values.avgDailyConversationChatsTimeInMin !== undefined && typeof values.avgDailyConversationChatsTimeInMin !== 'number') {
      values.avgDailyConversationChatsTimeInMin = Number(values.avgDailyConversationChatsTimeInMin);
    }

    const entity = {
      ...dailyChatsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...dailyChatsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          {isEdit ? (
            <>
              <h2 id="qualitydashboardApp.dailyChats.home.editLabel" data-cy="DailyChatsCreateUpdateHeading">
                <Translate contentKey="qualitydashboardApp.dailyChats.home.editLabel">Edit a DailyChats</Translate>
              </h2>
            </>
          ) : (
            <>
              <h2 id="qualitydashboardApp.dailyChats.home.createLabel" data-cy="DailyChatsCreateUpdateHeading">
                <Translate contentKey="qualitydashboardApp.dailyChats.home.createLabel">Create a DailyChats</Translate>
              </h2>
            </>
          )}
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="daily-chats-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('qualitydashboardApp.dailyChats.day')}
                id="daily-chats-day"
                name="day"
                data-cy="day"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyChats.totalDailyReceivedChats')}
                id="daily-chats-totalDailyReceivedChats"
                name="totalDailyReceivedChats"
                data-cy="totalDailyReceivedChats"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyChats.totalDailyConversationChatsTimeInMin')}
                id="daily-chats-totalDailyConversationChatsTimeInMin"
                name="totalDailyConversationChatsTimeInMin"
                data-cy="totalDailyConversationChatsTimeInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyChats.totalDailyAttendedChats')}
                id="daily-chats-totalDailyAttendedChats"
                name="totalDailyAttendedChats"
                data-cy="totalDailyAttendedChats"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyChats.totalDailyMissedChats')}
                id="daily-chats-totalDailyMissedChats"
                name="totalDailyMissedChats"
                data-cy="totalDailyMissedChats"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyChats.avgDailyConversationChatsTimeInMin')}
                id="daily-chats-avgDailyConversationChatsTimeInMin"
                name="avgDailyConversationChatsTimeInMin"
                data-cy="avgDailyConversationChatsTimeInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/daily-chats" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DailyChatsUpdate;
