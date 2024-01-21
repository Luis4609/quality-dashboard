import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ICallsMetricThreshold, defaultValue } from 'app/shared/model/metric-threshold.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState: EntityState<ICallsMetricThreshold> = {
  loading: false,
  errorMessage: null,
  entities: [],
  thresholds: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/metric-thresholds';

// Actions

export const getCallsThresholds = createAsyncThunk(
  'metricThresholdCalls/fetch_by_entity_name',
  async (entityName: string) => {
    const requestUrl = `${apiUrl}/by-entity-name/${entityName}`;
    return axios.get<ICallsMetricThreshold[]>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

// slice

export const CallsMetricsThresholdSlice = createEntitySlice({
  name: 'metricThresholdCalls',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getCallsThresholds.fulfilled, (state, action) => {
        state.loading = false;
        state.entities = action.payload.data;
      })
      .addMatcher(isPending(getCallsThresholds), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getCallsThresholds), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      });
  },
});

export const { reset } = CallsMetricsThresholdSlice.actions;

// Reducer
export default CallsMetricsThresholdSlice.reducer;