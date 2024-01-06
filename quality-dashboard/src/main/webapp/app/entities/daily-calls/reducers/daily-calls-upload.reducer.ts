import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState: EntityState<File> = {
  loading: false,
  errorMessage: null,
  entity: null,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  entities: [],
};

const apiUrl = 'api/daily-calls';

// Actions

export const uploadExcelEntity = createAsyncThunk(
  'dailyCallsUploadExcel/update_excel',
  async (entity: File | FormData) => {
    const result = await axios.post<File>(`${apiUrl}/upload-file`, entity);
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const DailyCallsUploadExcelSlice = createEntitySlice({
  name: 'dailyCallsUploadExcel',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(uploadExcelEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.updateSuccess = true;
      })
      .addMatcher(isPending(uploadExcelEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isFulfilled(uploadExcelEntity), (state, action) => {
        state.updateSuccess = true;
      });
  },
});

export const { reset } = DailyCallsUploadExcelSlice.actions;

// Reducer
export default DailyCallsUploadExcelSlice.reducer;
