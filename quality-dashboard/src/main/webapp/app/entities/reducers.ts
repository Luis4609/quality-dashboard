import dailyCalls from 'app/entities/daily-calls/daily-calls.reducer';
import dailyChats from 'app/entities/daily-chats/daily-chats.reducer';
import dailyCallsMetrics from './daily-calls/reducer/daily-calls-metrics.reducer';
import dailyCallsMetricsByDate from './daily-calls/reducer/daily-calls-metrics-date.reducer';
import dailyCallsUploadExcel from './daily-calls/reducer/daily-calls-upload.reducer';
import dailyCallsMetricsByMonth from './daily-calls/reducer/daily-calls-metrics-by-month.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  dailyCalls,
  dailyChats,
  dailyCallsMetrics,
  dailyCallsMetricsByDate,
  dailyCallsMetricsByMonth,
  dailyCallsUploadExcel,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
