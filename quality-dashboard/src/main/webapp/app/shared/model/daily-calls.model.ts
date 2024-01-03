export interface IDailyCalls {
  id?: number;
  day?: string;
  totalDailyReceivedCalls?: number | null;
  totalDailyAttendedCalls?: number;
  totalDailyMissedCalls?: number;
  totalDailyReceivedCallsExternalAgent?: number;
  totalDailyAttendedCallsExternalAgent?: number;
  totalDailyReceivedCallsInternalAgent?: number | null;
  totalDailyAttendedCallsInternalAgent?: number;
  totalDailyCallsTimeInMin?: number;
  totalDailyCallsTimeExternalAgentInMin?: number;
  totalDailyCallsTimeInternalAgentInMin?: number;
  avgDailyOperationTimeExternalAgentInMin?: number;
  avgDailyOperationTimeInternalAgentInMin?: number | null;
}

export const defaultValue: Readonly<IDailyCalls> = {};

export interface IDailyCallsMetrics {
  totalReceivedCalls?: number | null;
  totalAttendedCalls?: number;
  totalLostCalls?: number;
  totalAttendedCallsExternalAgent?: number;
  totalAttendedCallsInternalAgent?: number
}

export const defaultValueMetrics: Readonly<IDailyCallsMetrics> = {};

export interface IDailyCallsMetricsByDate {
  totalReceivedCalls?: number | null;
  totalAttendedCalls?: number;
  totalLostCalls?: number;
  totalAttendedCallsExternalAgent?: number;
  totalAttendedCallsInternalAgent?: number;
  metricDate?: number
}

export const defaultValueMetricsByDate: Readonly<IDailyCallsMetricsByDate> = {};