import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MetricThreshold from './metric-threshold';
import MetricThresholdDetail from './metric-threshold-detail';
import MetricThresholdUpdate from './metric-threshold-update';
import MetricThresholdDeleteDialog from './metric-threshold-delete-dialog';

const MetricThresholdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MetricThreshold />} />
    <Route path="new" element={<MetricThresholdUpdate />} />
    <Route path=":id">
      <Route index element={<MetricThresholdDetail />} />
      <Route path="edit" element={<MetricThresholdUpdate />} />
      <Route path="delete" element={<MetricThresholdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MetricThresholdRoutes;
