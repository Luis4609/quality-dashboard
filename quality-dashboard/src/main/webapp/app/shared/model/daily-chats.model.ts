export interface IDailyChats {
  id?: number;
  day?: string;
  totalDailyReceivedChats?: number;
  totalDailyConversationChatsTimeInMin?: number;
  totalDailyAttendedChats?: number;
  totalDailyMissedChats?: number;
  avgDailyConversationChatsTimeInMin?: number;
}

export const defaultValue: Readonly<IDailyChats> = {};

export interface IDailyChatsMetrics {
  totalReceivedChats?: number | null;
  totalDailyConversationChatsTime?: number;
  totalAttendedChats?: number;
  totalMissedChats?: number;
  avgConversationChats?: number;
  metricDate?: Date;
}

export const defaultValueMetrics: Readonly<IDailyChats> = {};

export interface IDailyChatsMetricsByMonth {
  totalReceivedChats?: number | null;
  totalDailyConversationChatsTime?: number;
  totalAttendedChats?: number;
  totalMissedChats?: number;
  avgConversationChats?: number;
  metricDate?: Date;
}

export const defaultValueMetricsByMonth: Readonly<IDailyChatsMetricsByMonth> = {};


export interface IDailyChatsMetricsSummary {
  totalReceivedChats?: number | null;
  totalDailyConversationChatsTime?: number;
  totalAttendedChats?: number;
  totalMissedChats?: number;
  avgConversationChats?: number;
}

export const defaultValueMetricsSummary: Readonly<IDailyChatsMetricsSummary> = {};

export interface IDailyChatsMetricsWithPrevious {
  current?: IDailyChatsMetricsSummary;
  previous?: IDailyChatsMetricsSummary;
}

export const defaultValueMetricsWithPrevious: Readonly<IDailyChatsMetricsWithPrevious> = {
  current: {
    totalReceivedChats: 0,
    totalDailyConversationChatsTime: 0,
    totalAttendedChats: 0,
    totalMissedChats: 0,
    avgConversationChats: 0,
  },
  previous: {
    totalReceivedChats: 0,
    totalDailyConversationChatsTime: 0,
    totalAttendedChats: 0,
    totalMissedChats: 0,
    avgConversationChats: 0,
  },
};
