import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './kpi-round.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import ReactMarkdown from 'react-markdown';

export const KpiRoundDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const kpiRoundEntity = useAppSelector(state => state.kpiRound.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kpiRoundDetailsHeading">KPI Round</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kpiRoundEntity.id}</dd>
          <dt>
            <span id="displayName">Display Name</span>
          </dt>
          <dd>{kpiRoundEntity.displayName}</dd>
          <dt>
            <span id="totalPossibleRewards">Total Possible Rewards</span>
          </dt>
          <dd>{kpiRoundEntity.totalPossibleRewards}</dd>
          <dt>
            <span id="councilElectedInRound">Council Elected In Round</span>
          </dt>
          <dd>{kpiRoundEntity.councilElectedInRound}</dd>
          <dt>
            <span id="councilMembers">Council Members</span>
          </dt>
          <dd>{kpiRoundEntity.councilMembers}</dd>
          <dt>
            <span id="termLength">Term Length</span>
          </dt>
          <dd>{kpiRoundEntity.termLength}</dd>
          <dt>
            <span id="startBlock">Start Block</span>
          </dt>
          <dd>{kpiRoundEntity.startBlock}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {kpiRoundEntity.startDate ? <TextFormat value={kpiRoundEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endBlock">End Block</span>
          </dt>
          <dd>{kpiRoundEntity.endBlock}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {kpiRoundEntity.endDate ? <TextFormat value={kpiRoundEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="termSummary">Term Summary</span>
          </dt>
          <dd>{kpiRoundEntity.termSummary}</dd>
          <dt>
            <span id="summarySubmissionDeadline">Summary Submission Deadline</span>
          </dt>
          <dd>{kpiRoundEntity.summarySubmissionDeadline}</dd>
          <dt>
            <span id="maxFiatPoolDifference">Max Fiat Pool Difference</span>
          </dt>
          <dd>{kpiRoundEntity.maxFiatPoolDifference}</dd>
          <dt>
            <span id="numberOfKpis">Number Of KPIs</span>
          </dt>
          <dd>{kpiRoundEntity.numberOfKpis}</dd>
          <dt>
            <span id="notes">Notes</span>
          </dt>
          <dd>
            <ReactMarkdown>{kpiRoundEntity.notes}</ReactMarkdown>
          </dd>
        </dl>
        <Button tag={Link} to="/kpi-round" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kpi-round/${kpiRoundEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KpiRoundDetail;
