import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IDailyCallsMetrics, IDailyCallsMetricsByDate, defaultValueMetrics } from 'app/shared/model/daily-calls.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState: EntityState<IDailyCallsMetrics> = {
  loading: false,
  errorMessage: null,
  entities: null,
  entity: defaultValueMetrics,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/daily-calls';

// Actions

export const getMetrics = createAsyncThunk(
  'dailyCallsMetrics/fetch_metrics',
  async (start: string) => {
    const requestUrl = `${apiUrl}/metrics?start=${start}`;
    const result = axios.get<IDailyCallsMetrics>(requestUrl);
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

        // eslint-disable-next-line no-console
        console.log(`Metrics FULLFILED ${data.totalAttendedCallsInternalAgent}`);

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
