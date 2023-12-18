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
