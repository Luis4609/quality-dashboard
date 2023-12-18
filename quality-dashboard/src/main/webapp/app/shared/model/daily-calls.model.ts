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
  totalDailyReceivedCalls?: number | null;
  totalDailyAttendedCalls?: number;
  totalDailyMissedCalls?: number;
  totalDailyAttendedCallsExternalAgent?: number;
  totalDailyAttendedCallsInternalAgent?: number
}