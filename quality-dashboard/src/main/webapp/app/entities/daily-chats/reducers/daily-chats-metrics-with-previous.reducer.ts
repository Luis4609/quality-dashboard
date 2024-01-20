import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IDailyChatsMetricsWithPrevious, defaultValueMetricsWithPrevious } from 'app/shared/model/daily-chats.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';
import { IDateRangeParams } from './daily-chats-types';

const initialState: EntityState<IDailyChatsMetricsWithPrevious> = {
  loading: false,
  errorMessage: null,
  entity: defaultValueMetricsWithPrevious,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  entities: null,
};

const apiUrl = 'api/daily-chats/metrics';

// Actions

export const getChatsMetricsWithPrevious = createAsyncThunk(
  'dailyChatsMetricsWithPrevious/fetch_metrics',
  async ({ startDate, endDate }: IDateRangeParams) => {
    const requestUrl = `${apiUrl}/summary?start=${startDate}&finish=${endDate}`;
    const result = axios.get<IDailyChatsMetricsWithPrevious>(requestUrl);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyChatsMetricsWithPreviousSlice = createEntitySlice({
  name: 'dailyChatsMetricsWithPrevious',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getChatsMetricsWithPrevious.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getChatsMetricsWithPrevious), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getChatsMetricsWithPrevious), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entity: data,
        };
      });
  },
});

export const { reset } = DailyChatsMetricsWithPreviousSlice.actions;

// Reducer
export default DailyChatsMetricsWithPreviousSlice.reducer;
