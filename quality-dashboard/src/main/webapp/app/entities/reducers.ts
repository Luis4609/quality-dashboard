import dailyCalls from 'app/entities/daily-calls/daily-calls.reducer';
import dailyChats from 'app/entities/daily-chats/daily-chats.reducer';
import dailyCallsMetrics from './daily-calls/daily-calls-metrics.reducer';
import dailyCallsMetricsByDate from './daily-calls/daily-calls-metrics-date.reducer';

/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  dailyCalls,
  dailyChats,
  dailyCallsMetrics,
  dailyCallsMetricsByDate,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
