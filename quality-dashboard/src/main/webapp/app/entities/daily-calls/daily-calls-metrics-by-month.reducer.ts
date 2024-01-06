import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IDailyCallsMetrics, IDailyCallsMetricsByDate, defaultValueMetrics } from 'app/shared/model/daily-calls.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';
import { IDateRangeParams } from './daily-calls-metrics.reducer';

const initialState: EntityState<IDailyCallsMetricsByDate> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValueMetrics,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/daily-calls';

// Actions

export const getMetricsByMonth = createAsyncThunk(
  'dailyCallsMetricsByMonth/fetch_metrics_by_month',
  async ({startDate, endDate}: IDateRangeParams) => {
    const requestUrl = `${apiUrl}/metrics?start=${startDate}&finish=${endDate}`;
    const result = axios.get<IDailyCallsMetricsByDate[]>(requestUrl);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyCallsMetricsSlice = createEntitySlice({
  name: 'dailyCallsMetricsByMonth',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getMetricsByMonth.fulfilled, (state, action) => {
        state.loading = false;
        state.entities = action.payload.data;
      })
      .addMatcher(isPending(getMetricsByMonth), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getMetricsByMonth), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      });
  },
});

export const { reset } = DailyCallsMetricsSlice.actions;

// Reducer
export default DailyCallsMetricsSlice.reducer;
