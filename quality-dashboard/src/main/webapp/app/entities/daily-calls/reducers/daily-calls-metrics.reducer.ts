import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import {
  IDailyCallsMetricsWithPrevious,
  defaultValueMetricsWithPrevious
} from 'app/shared/model/daily-calls.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';
import { IDateRangeParams } from './daily-calls-types';

const initialState: EntityState<IDailyCallsMetricsWithPrevious> = {
  loading: false,
  errorMessage: null,
  entities: null,
  entity: defaultValueMetricsWithPrevious,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/daily-calls';

// Actions

export const getMetrics = createAsyncThunk(
  'dailyCallsMetrics/fetch_metrics',
  async ({ startDate, endDate }: IDateRangeParams) => {
    const requestUrl = `${apiUrl}/metrics/summary?start=${startDate}&finish=${endDate}`;
    const result = axios.get<IDailyCallsMetricsWithPrevious>(requestUrl);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyCallsMetricsSlice = createEntitySlice({
  name: 'dailyCallsMetrics',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getMetrics.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getMetrics), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getMetrics), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entity: data,
        };
      });
  },
});

export const { reset } = DailyCallsMetricsSlice.actions;

// Reducer
export default DailyCallsMetricsSlice.reducer;
