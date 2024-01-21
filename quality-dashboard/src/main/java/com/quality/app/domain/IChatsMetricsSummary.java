package com.quality.app.domain;

public interface IChatsMetricsSummary {

    Integer getTotalReceivedChats();

    Integer getTotalDailyConversationChatsTime();

    Integer getTotalAttendedChats();

    Integer getTotalMissedChats();

    Integer getAvgConversationChats();

}
