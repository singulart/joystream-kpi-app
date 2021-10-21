import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './kpi.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const KpiDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const kpiEntity = useAppSelector(state => state.kpi.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kpiDetailsHeading">Kpi</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kpiEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{kpiEntity.title}</dd>
          <dt>
            <span id="reward">Reward</span>
          </dt>
          <dd>{kpiEntity.reward}</dd>
          <dt>
            <span id="rewardDistribution">Reward Distribution</span>
          </dt>
          <dd>{kpiEntity.rewardDistribution}</dd>
          <dt>
            <span id="gradingProcess">Grading Process</span>
          </dt>
          <dd>{kpiEntity.gradingProcess}</dd>
          <dt>
            <span id="active">Active</span>
          </dt>
          <dd>{kpiEntity.active}</dd>
          <dt>
            <span id="purpose">Purpose</span>
          </dt>
          <dd>{kpiEntity.purpose}</dd>
          <dt>
            <span id="scopeOfWork">Scope Of Work</span>
          </dt>
          <dd>{kpiEntity.scopeOfWork}</dd>
          <dt>
            <span id="rewardDistributionInfo">Reward Distribution Info</span>
          </dt>
          <dd>{kpiEntity.rewardDistributionInfo}</dd>
          <dt>
            <span id="reporting">Reporting</span>
          </dt>
          <dd>{kpiEntity.reporting}</dd>
          <dt>
            <span id="fiatPoolFactor">Fiat Pool Factor</span>
          </dt>
          <dd>{kpiEntity.fiatPoolFactor}</dd>
          <dt>
            <span id="grading">Grading</span>
          </dt>
          <dd>{kpiEntity.grading}</dd>
          <dt>Kpi Round</dt>
          <dd>{kpiEntity.kpiRound ? kpiEntity.kpiRound.displayName : ''}</dd>
        </dl>
        <Button tag={Link} to="/kpi" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kpi/${kpiEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KpiDetail;
