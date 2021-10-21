import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IKpiRound } from 'app/shared/model/kpi-round.model';
import { getEntities as getKpiRounds } from 'app/entities/kpi-round/kpi-round.reducer';
import { getEntity, updateEntity, createEntity, reset } from './kpi.reducer';
import { IKpi } from 'app/shared/model/kpi.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const KpiUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const kpiRounds = useAppSelector(state => state.kpiRound.entities);
  const kpiEntity = useAppSelector(state => state.kpi.entity);
  const loading = useAppSelector(state => state.kpi.loading);
  const updating = useAppSelector(state => state.kpi.updating);
  const updateSuccess = useAppSelector(state => state.kpi.updateSuccess);
  const handleClose = () => {
    props.history.push('/kpi');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getKpiRounds({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...kpiEntity,
      ...values,
      kpiRound: kpiRounds.find(it => it.id.toString() === values.kpiRound.toString()),
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
          ...kpiEntity,
          kpiRound: kpiEntity?.kpiRound?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="joystreamKpiApp.kpi.home.createOrEditLabel" data-cy="KpiCreateUpdateHeading">
            Create or edit a Kpi
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kpi-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Title"
                id="kpi-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 10, message: 'This field is required to be at least 10 characters.' },
                  maxLength: { value: 128, message: 'This field cannot be longer than 128 characters.' },
                }}
              />
              <ValidatedField
                label="Reward"
                id="kpi-reward"
                name="reward"
                data-cy="reward"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="Reward Distribution"
                id="kpi-rewardDistribution"
                name="rewardDistribution"
                data-cy="rewardDistribution"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Grading Process"
                id="kpi-gradingProcess"
                name="gradingProcess"
                data-cy="gradingProcess"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Active"
                id="kpi-active"
                name="active"
                data-cy="active"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Purpose"
                id="kpi-purpose"
                name="purpose"
                data-cy="purpose"
                type="text"
                validate={{
                  maxLength: { value: 2048, message: 'This field cannot be longer than 2048 characters.' },
                }}
              />
              <ValidatedField
                label="Scope Of Work"
                id="kpi-scopeOfWork"
                name="scopeOfWork"
                data-cy="scopeOfWork"
                type="text"
                validate={{
                  maxLength: { value: 2048, message: 'This field cannot be longer than 2048 characters.' },
                }}
              />
              <ValidatedField
                label="Reward Distribution Info"
                id="kpi-rewardDistributionInfo"
                name="rewardDistributionInfo"
                data-cy="rewardDistributionInfo"
                type="text"
                validate={{
                  maxLength: { value: 2048, message: 'This field cannot be longer than 2048 characters.' },
                }}
              />
              <ValidatedField
                label="Reporting"
                id="kpi-reporting"
                name="reporting"
                data-cy="reporting"
                type="text"
                validate={{
                  maxLength: { value: 2048, message: 'This field cannot be longer than 2048 characters.' },
                }}
              />
              <ValidatedField label="Fiat Pool Factor" id="kpi-fiatPoolFactor" name="fiatPoolFactor" data-cy="fiatPoolFactor" type="text" />
              <ValidatedField
                label="Grading"
                id="kpi-grading"
                name="grading"
                data-cy="grading"
                type="text"
                validate={{
                  maxLength: { value: 2048, message: 'This field cannot be longer than 2048 characters.' },
                }}
              />
              <ValidatedField id="kpi-kpiRound" name="kpiRound" data-cy="kpiRound" label="Kpi Round" type="select">
                <option value="" key="0" />
                {kpiRounds
                  ? kpiRounds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.displayName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kpi" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default KpiUpdate;
