import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DailyChats from './daily-chats';
import DailyChatsDetail from './daily-chats-detail';
import DailyChatsUpdate from './daily-chats-update';
import DailyChatsDeleteDialog from './daily-chats-delete-dialog';
import DailyChatsMetrics from './daily-chats-metrics';

const DailyChatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DailyChats />} />
    <Route path="new" element={<DailyChatsUpdate />} />
    <Route path='metrics' element={<DailyChatsMetrics />} />
    <Route path=":id">
      <Route index element={<DailyChatsDetail />} />
      <Route path="edit" element={<DailyChatsUpdate />} />
      <Route path="delete" element={<DailyChatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DailyChatsRoutes;
