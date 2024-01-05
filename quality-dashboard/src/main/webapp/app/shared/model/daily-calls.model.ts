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
  totalAttendedCallsInternalAgent?: number;
}

export const defaultValueMetrics: Readonly<IDailyCallsMetrics> = {};

export interface IDailyCallsMetricsByDate {
  totalReceivedCalls?: number | null;
  totalAttendedCalls?: number;
  totalLostCalls?: number;
  totalAttendedCallsExternalAgent?: number;
  totalAttendedCallsInternalAgent?: number;
  metricDate?: number;
}

export const defaultValueMetricsByDate: Readonly<IDailyCallsMetricsByDate> = {};

export interface IDailyCallsMetricsWithPrevious {
  current?: IDailyCallsMetrics;
  previous?: IDailyCallsMetrics;
}

export const defaultValueMetricsWithPrevious: Readonly<IDailyCallsMetricsWithPrevious> = {
  current: {
    totalReceivedCalls: 0,
    totalAttendedCalls: 0,
    totalLostCalls: 0,
    totalAttendedCallsExternalAgent: 0,
    totalAttendedCallsInternalAgent: 0,
  },
  previous: {
    totalReceivedCalls: 0,
    totalAttendedCalls: 0,
    totalLostCalls: 0,
    totalAttendedCallsExternalAgent: 0,
    totalAttendedCallsInternalAgent: 0,
  },
};
