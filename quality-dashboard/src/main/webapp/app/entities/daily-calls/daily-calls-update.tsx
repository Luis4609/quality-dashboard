import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './daily-calls.reducer';

export const DailyCallsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dailyCallsEntity = useAppSelector(state => state.dailyCalls.entity);
  const loading = useAppSelector(state => state.dailyCalls.loading);
  const updating = useAppSelector(state => state.dailyCalls.updating);
  const updateSuccess = useAppSelector(state => state.dailyCalls.updateSuccess);

  const handleClose = () => {
    navigate('/daily-calls' + location.search);
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
    if (values.totalDailyReceivedCalls !== undefined && typeof values.totalDailyReceivedCalls !== 'number') {
      values.totalDailyReceivedCalls = Number(values.totalDailyReceivedCalls);
    }
    if (values.totalDailyAttendedCalls !== undefined && typeof values.totalDailyAttendedCalls !== 'number') {
      values.totalDailyAttendedCalls = Number(values.totalDailyAttendedCalls);
    }
    if (values.totalDailyMissedCalls !== undefined && typeof values.totalDailyMissedCalls !== 'number') {
      values.totalDailyMissedCalls = Number(values.totalDailyMissedCalls);
    }
    if (values.totalDailyReceivedCallsExternalAgent !== undefined && typeof values.totalDailyReceivedCallsExternalAgent !== 'number') {
      values.totalDailyReceivedCallsExternalAgent = Number(values.totalDailyReceivedCallsExternalAgent);
    }
    if (values.totalDailyAttendedCallsExternalAgent !== undefined && typeof values.totalDailyAttendedCallsExternalAgent !== 'number') {
      values.totalDailyAttendedCallsExternalAgent = Number(values.totalDailyAttendedCallsExternalAgent);
    }
    if (values.totalDailyReceivedCallsInternalAgent !== undefined && typeof values.totalDailyReceivedCallsInternalAgent !== 'number') {
      values.totalDailyReceivedCallsInternalAgent = Number(values.totalDailyReceivedCallsInternalAgent);
    }
    if (values.totalDailyAttendedCallsInternalAgent !== undefined && typeof values.totalDailyAttendedCallsInternalAgent !== 'number') {
      values.totalDailyAttendedCallsInternalAgent = Number(values.totalDailyAttendedCallsInternalAgent);
    }
    if (values.totalDailyCallsTimeInMin !== undefined && typeof values.totalDailyCallsTimeInMin !== 'number') {
      values.totalDailyCallsTimeInMin = Number(values.totalDailyCallsTimeInMin);
    }
    if (values.totalDailyCallsTimeExternalAgentInMin !== undefined && typeof values.totalDailyCallsTimeExternalAgentInMin !== 'number') {
      values.totalDailyCallsTimeExternalAgentInMin = Number(values.totalDailyCallsTimeExternalAgentInMin);
    }
    if (values.totalDailyCallsTimeInternalAgentInMin !== undefined && typeof values.totalDailyCallsTimeInternalAgentInMin !== 'number') {
      values.totalDailyCallsTimeInternalAgentInMin = Number(values.totalDailyCallsTimeInternalAgentInMin);
    }
    if (
      values.avgDailyOperationTimeExternalAgentInMin !== undefined &&
      typeof values.avgDailyOperationTimeExternalAgentInMin !== 'number'
    ) {
      values.avgDailyOperationTimeExternalAgentInMin = Number(values.avgDailyOperationTimeExternalAgentInMin);
    }
    if (
      values.avgDailyOperationTimeInternalAgentInMin !== undefined &&
      typeof values.avgDailyOperationTimeInternalAgentInMin !== 'number'
    ) {
      values.avgDailyOperationTimeInternalAgentInMin = Number(values.avgDailyOperationTimeInternalAgentInMin);
    }

    const entity = {
      ...dailyCallsEntity,
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
          ...dailyCallsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="qualitydashboardApp.dailyCalls.home.createOrEditLabel" data-cy="DailyCallsCreateUpdateHeading">
            <Translate contentKey="qualitydashboardApp.dailyCalls.home.createOrEditLabel">Create or edit a DailyCalls</Translate>
          </h2>
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
                  id="daily-calls-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.day')}
                id="daily-calls-day"
                name="day"
                data-cy="day"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyReceivedCalls')}
                id="daily-calls-totalDailyReceivedCalls"
                name="totalDailyReceivedCalls"
                data-cy="totalDailyReceivedCalls"
                type="text"
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyAttendedCalls')}
                id="daily-calls-totalDailyAttendedCalls"
                name="totalDailyAttendedCalls"
                data-cy="totalDailyAttendedCalls"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyMissedCalls')}
                id="daily-calls-totalDailyMissedCalls"
                name="totalDailyMissedCalls"
                data-cy="totalDailyMissedCalls"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyReceivedCallsExternalAgent')}
                id="daily-calls-totalDailyReceivedCallsExternalAgent"
                name="totalDailyReceivedCallsExternalAgent"
                data-cy="totalDailyReceivedCallsExternalAgent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyAttendedCallsExternalAgent')}
                id="daily-calls-totalDailyAttendedCallsExternalAgent"
                name="totalDailyAttendedCallsExternalAgent"
                data-cy="totalDailyAttendedCallsExternalAgent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyReceivedCallsInternalAgent')}
                id="daily-calls-totalDailyReceivedCallsInternalAgent"
                name="totalDailyReceivedCallsInternalAgent"
                data-cy="totalDailyReceivedCallsInternalAgent"
                type="text"
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyAttendedCallsInternalAgent')}
                id="daily-calls-totalDailyAttendedCallsInternalAgent"
                name="totalDailyAttendedCallsInternalAgent"
                data-cy="totalDailyAttendedCallsInternalAgent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyCallsTimeInMin')}
                id="daily-calls-totalDailyCallsTimeInMin"
                name="totalDailyCallsTimeInMin"
                data-cy="totalDailyCallsTimeInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyCallsTimeExternalAgentInMin')}
                id="daily-calls-totalDailyCallsTimeExternalAgentInMin"
                name="totalDailyCallsTimeExternalAgentInMin"
                data-cy="totalDailyCallsTimeExternalAgentInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.totalDailyCallsTimeInternalAgentInMin')}
                id="daily-calls-totalDailyCallsTimeInternalAgentInMin"
                name="totalDailyCallsTimeInternalAgentInMin"
                data-cy="totalDailyCallsTimeInternalAgentInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.avgDailyOperationTimeExternalAgentInMin')}
                id="daily-calls-avgDailyOperationTimeExternalAgentInMin"
                name="avgDailyOperationTimeExternalAgentInMin"
                data-cy="avgDailyOperationTimeExternalAgentInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.dailyCalls.avgDailyOperationTimeInternalAgentInMin')}
                id="daily-calls-avgDailyOperationTimeInternalAgentInMin"
                name="avgDailyOperationTimeInternalAgentInMin"
                data-cy="avgDailyOperationTimeInternalAgentInMin"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/daily-calls" replace color="info">
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

export default DailyCallsUpdate;
