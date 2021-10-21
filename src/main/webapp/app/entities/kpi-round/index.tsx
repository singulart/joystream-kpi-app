import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KpiRound from './kpi-round';
import KpiRoundDetail from './kpi-round-detail';
import KpiRoundUpdate from './kpi-round-update';
import KpiRoundDeleteDialog from './kpi-round-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KpiRoundUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KpiRoundUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KpiRoundDetail} />
      <ErrorBoundaryRoute path={match.url} component={KpiRound} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KpiRoundDeleteDialog} />
  </>
);

export default Routes;
