import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IDateRangeParams } from 'app/entities/daily-calls/reducers/daily-calls-types';
import { IDailyChatsMetrics, IDailyChatsMetricsByMonth, defaultValueMetricsByMonth } from 'app/shared/model/daily-chats.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState: EntityState<IDailyChatsMetricsByMonth> = {
  loading: false,
  errorMessage: null,
  entity: defaultValueMetricsByMonth,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  entities: [],
};

const apiUrl = 'api/daily-chats/metrics';

// Actions

export const getChatsMetricsByMonth = createAsyncThunk(
  'dailyChatsMetricsGroupByMonth/fetch_metrics',
  async ({ startDate, endDate }: IDateRangeParams) => {
    const requestUrl = `${apiUrl}/group-by-month?start=${startDate}&finish=${endDate}`;
    const result = axios.get<IDailyChatsMetricsByMonth[]>(requestUrl);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyChatsUploadExcelSlice = createEntitySlice({
  name: 'dailyChatsMetricsGroupByMonth',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getChatsMetricsByMonth.fulfilled, (state, action) => {
        state.loading = false;
        state.entities = action.payload.data;
      })
      .addMatcher(isPending(getChatsMetricsByMonth), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(getChatsMetricsByMonth), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      });
  },
});

export const { reset } = DailyChatsUploadExcelSlice.actions;

// Reducer
export default DailyChatsUploadExcelSlice.reducer;
