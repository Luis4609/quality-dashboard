package com.quality.app.service.dto.metrics.chats;

public interface IChatsMetricsSummary {

    Integer getTotalReceivedChats();

    Integer getTotalDailyConversationChatsTime();

    Integer getTotalAttendedChats();

    Integer getTotalMissedChats();

    Integer getAvgConversationChats();

}
