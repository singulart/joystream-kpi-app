import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './kpi-round.reducer';
import { IKpiRound } from 'app/shared/model/kpi-round.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const KpiRoundUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const kpiRoundEntity = useAppSelector(state => state.kpiRound.entity);
  const loading = useAppSelector(state => state.kpiRound.loading);
  const updating = useAppSelector(state => state.kpiRound.updating);
  const updateSuccess = useAppSelector(state => state.kpiRound.updateSuccess);
  const handleClose = () => {
    props.history.push('/kpi-round');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...kpiRoundEntity,
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
          ...kpiRoundEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="joystreamKpiApp.kpiRound.home.createOrEditLabel" data-cy="KpiRoundCreateUpdateHeading">
            Create or edit a KpiRound
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kpi-round-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Display Name"
                id="kpi-round-displayName"
                name="displayName"
                data-cy="displayName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 2, message: 'This field is required to be at least 2 characters.' },
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <ValidatedField
                label="Total Possible Rewards"
                id="kpi-round-totalPossibleRewards"
                name="totalPossibleRewards"
                data-cy="totalPossibleRewards"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 4, message: 'This field is required to be at least 4 characters.' },
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <ValidatedField
                label="Council Elected In Round"
                id="kpi-round-councilElectedInRound"
                name="councilElectedInRound"
                data-cy="councilElectedInRound"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 1, message: 'This field should be at least 1.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Council Members"
                id="kpi-round-councilMembers"
                name="councilMembers"
                data-cy="councilMembers"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 2, message: 'This field should be at least 2.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Term Length"
                id="kpi-round-termLength"
                name="termLength"
                data-cy="termLength"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 64, message: 'This field cannot be longer than 64 characters.' },
                }}
              />
              <ValidatedField
                label="Start Block"
                id="kpi-round-startBlock"
                name="startBlock"
                data-cy="startBlock"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 1, message: 'This field should be at least 1.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Start Date"
                id="kpi-round-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="End Block"
                id="kpi-round-endBlock"
                name="endBlock"
                data-cy="endBlock"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                }}
              />
              <ValidatedField
                label="End Date"
                id="kpi-round-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Term Summary" id="kpi-round-termSummary" name="termSummary" data-cy="termSummary" type="text" />
              <ValidatedField
                label="Summary Submission Deadline"
                id="kpi-round-summarySubmissionDeadline"
                name="summarySubmissionDeadline"
                data-cy="summarySubmissionDeadline"
                type="text"
              />
              <ValidatedField
                label="Max Fiat Pool Difference"
                id="kpi-round-maxFiatPoolDifference"
                name="maxFiatPoolDifference"
                data-cy="maxFiatPoolDifference"
                type="text"
              />
              <ValidatedField label="Number Of Kpis" id="kpi-round-numberOfKpis" name="numberOfKpis" data-cy="numberOfKpis" type="text" />
              <ValidatedField label="Notes" id="kpi-round-notes" name="notes" data-cy="notes" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kpi-round" replace color="info">
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

export default KpiRoundUpdate;
