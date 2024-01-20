import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IDateRangeParams } from 'app/entities/daily-calls/reducers/daily-calls-types';
import { IDailyChatsMetrics } from 'app/shared/model/daily-chats.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState: EntityState<IDailyChatsMetrics> = {
  loading: false,
  errorMessage: null,
  entity: null,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  entities: [],
};

const apiUrl = 'api/daily-chats/metrics';

// Actions

export const getChatsMetrics = createAsyncThunk(
  'dailyChatsMetrics/fetch_metrics',
  async ({ startDate, endDate }: IDateRangeParams) => {
    const requestUrl = `${apiUrl}/summary?start=${startDate}&finish=${endDate}`;
    const result = axios.get<IDailyChatsMetrics>(requestUrl);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyChatsUploadExcelSlice = createEntitySlice({
  name: 'dailyChatsMetrics',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getChatsMetrics.fulfilled, (state, action) => {
        state.loading = false;
        state.updateSuccess = true;
      })
      .addMatcher(isPending(getChatsMetrics), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getChatsMetrics), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entity: data,
        };
      });
  },
});

export const { reset } = DailyChatsUploadExcelSlice.actions;

// Reducer
export default DailyChatsUploadExcelSlice.reducer;
