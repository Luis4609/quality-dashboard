import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMetricThreshold } from 'app/shared/model/metric-threshold.model';
import { getEntity, updateEntity, createEntity, reset } from './metric-threshold.reducer';

export const MetricThresholdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const metricThresholdEntity = useAppSelector(state => state.metricThreshold.entity);
  const loading = useAppSelector(state => state.metricThreshold.loading);
  const updating = useAppSelector(state => state.metricThreshold.updating);
  const updateSuccess = useAppSelector(state => state.metricThreshold.updateSuccess);

  const handleClose = () => {
    navigate('/metric-threshold' + location.search);
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
    if (values.successPercentage !== undefined && typeof values.successPercentage !== 'number') {
      values.successPercentage = Number(values.successPercentage);
    }
    if (values.dangerPercentage !== undefined && typeof values.dangerPercentage !== 'number') {
      values.dangerPercentage = Number(values.dangerPercentage);
    }

    const entity = {
      ...metricThresholdEntity,
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
          ...metricThresholdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="qualitydashboardApp.metricThreshold.home.createOrEditLabel" data-cy="MetricThresholdCreateUpdateHeading">
            <Translate contentKey="qualitydashboardApp.metricThreshold.home.createOrEditLabel">Create or edit a MetricThreshold</Translate>
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
                  id="metric-threshold-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('qualitydashboardApp.metricThreshold.entityName')}
                id="metric-threshold-entityName"
                name="entityName"
                data-cy="entityName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.metricThreshold.metricName')}
                id="metric-threshold-metricName"
                name="metricName"
                data-cy="metricName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('qualitydashboardApp.metricThreshold.successPercentage')}
                id="metric-threshold-successPercentage"
                name="successPercentage"
                data-cy="successPercentage"
                type="text"
              />
              <ValidatedField
                label={translate('qualitydashboardApp.metricThreshold.dangerPercentage')}
                id="metric-threshold-dangerPercentage"
                name="dangerPercentage"
                data-cy="dangerPercentage"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/metric-threshold" replace color="info">
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

export default MetricThresholdUpdate;
