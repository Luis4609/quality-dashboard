package com.quality.app.service.dto.metrics.chats;

import java.util.Date;

public interface IChatsMetrics {

    Integer getTotalReceivedChats();

    Integer getTotalDailyConversationChatsTime();

    Integer getTotalAttendedChats();

    Integer getTotalMissedChats();

    Integer getAvgConversationChats();

    Date getMetricDate();
}
