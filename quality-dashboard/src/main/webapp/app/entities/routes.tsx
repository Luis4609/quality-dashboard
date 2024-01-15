import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DailyCalls from './daily-calls';
import DailyChats from './daily-chats';
import MetricThreshold from './metric-threshold';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="daily-calls/*" element={<DailyCalls />} />
        <Route path="daily-chats/*" element={<DailyChats />} />
        <Route path="metric-threshold/*" element={<MetricThreshold />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
