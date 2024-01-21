import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IChatsMetricThreshold, defaultValue } from 'app/shared/model/metric-threshold.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState: EntityState<IChatsMetricThreshold> = {
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

export const getChatsThresholds = createAsyncThunk(
  'metricThresholdChats/fetch_by_entity_name',
  async (entityName: string) => {
    const requestUrl = `${apiUrl}/by-entity-name/${entityName}`;
    return axios.get<IChatsMetricThreshold[]>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

// slice

export const CallsMetricsThresholdSlice = createEntitySlice({
  name: 'metricThresholdChats',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getChatsThresholds.fulfilled, (state, action) => {
        state.loading = false;
        state.entities = action.payload.data;
      })
      .addMatcher(isPending(getChatsThresholds), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getChatsThresholds), (state, action) => {
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
