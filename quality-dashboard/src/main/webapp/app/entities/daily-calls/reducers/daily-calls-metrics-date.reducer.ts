import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IDailyCallsMetricsByDate, defaultValueMetrics } from 'app/shared/model/daily-calls.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';
import { IDateRangeParams } from './daily-calls-types';

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

export const getMetricsWithDate = createAsyncThunk(
  'dailyCallsMetricsByDate/fetch_metrics_date',
  async ({ startDate, endDate }: IDateRangeParams) => {
    const requestUrl = `${apiUrl}/metrics/year?start=${startDate}&finish=${endDate}`;
    const result = axios.get<IDailyCallsMetricsByDate[]>(requestUrl);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyCallsMetricsByDateSlice = createEntitySlice({
  name: 'dailyCallsMetricsByDate',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getMetricsWithDate.fulfilled, (state, action) => {
        state.loading = false;
        state.entities = action.payload.data;
      })
      .addMatcher(isPending(getMetricsWithDate), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getMetricsWithDate), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      });
  },
});

export const { reset } = DailyCallsMetricsByDateSlice.actions;

// Reducer
export default DailyCallsMetricsByDateSlice.reducer;
