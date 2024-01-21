import dailyCalls from 'app/entities/daily-calls/daily-calls.reducer';
import dailyChats from 'app/entities/daily-chats/daily-chats.reducer';
import metricThreshold from 'app/entities/metric-threshold/metric-threshold.reducer';
import dailyCallsMetrics from './daily-calls/reducers/daily-calls-metrics.reducer';
import dailyCallsMetricsByDate from './daily-calls/reducers/daily-calls-metrics-date.reducer';
import dailyCallsUploadExcel from './daily-calls/reducers/daily-calls-upload.reducer';
import dailyCallsMetricsByMonth from './daily-calls/reducers/daily-calls-metrics-by-month.reducer';
import dailyChatsMetrics from 'app/entities/daily-chats/reducers/daily-chats-metrics.reducer';
import dailyChatsMetricsSummary from 'app/entities/daily-chats/reducers/daily-chats-metrics.reducer';
import dailyChatsMetricsWithPrevious from 'app/entities/daily-chats/reducers/daily-chats-metrics-with-previous.reducer';
import dailyChatsMetricsGroupByMonth from 'app/entities/daily-chats/reducers/daily-chats-metrics-group-by-month.reducer';
import metricThresholdChats from 'app/entities/metric-threshold/metric-threshold-chats.reducer'
import metricThresholdCalls from 'app/entities/metric-threshold/metric-threshold-calls.reducer'
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  dailyCalls,
  dailyChats,
  dailyCallsMetrics,
  dailyCallsMetricsByDate,
  dailyCallsMetricsByMonth,
  dailyCallsUploadExcel,
  metricThreshold,
  metricThresholdCalls,
  metricThresholdChats,
  dailyChatsMetrics,
  dailyChatsMetricsSummary,
  dailyChatsMetricsWithPrevious,
  dailyChatsMetricsGroupByMonth,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
