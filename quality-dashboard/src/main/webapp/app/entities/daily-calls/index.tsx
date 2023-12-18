import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DailyCalls from './daily-calls';
import DailyCallsDetail from './daily-calls-detail';
import DailyCallsUpdate from './daily-calls-update';
import DailyCallsDeleteDialog from './daily-calls-delete-dialog';
import DailyCallsUpload from './daily-calls-upload';
import DailyCallsMetrics from './daily-calls-metrics';

const DailyCallsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DailyCalls />} />
    <Route path="new" element={<DailyCallsUpdate />} />
    <Route path="upload" element={<DailyCallsUpload />}/>
    <Route path="metrics" element={<DailyCallsMetrics />} />
    <Route path=":id">
      <Route index element={<DailyCallsDetail />} />
      <Route path="edit" element={<DailyCallsUpdate />} />
      <Route path="delete" element={<DailyCallsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DailyCallsRoutes;
