import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Kpi from './kpi';
import KpiDetail from './kpi-detail';
import KpiUpdate from './kpi-update';
import KpiDeleteDialog from './kpi-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KpiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KpiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KpiDetail} />
      <ErrorBoundaryRoute path={match.url} component={Kpi} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KpiDeleteDialog} />
  </>
);

export default Routes;
