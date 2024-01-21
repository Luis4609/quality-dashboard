package com.quality.app.domain;

import java.util.Date;

public interface IChatsMetrics {

    Integer getTotalReceivedChats();

    Integer getTotalDailyConversationChatsTime();

    Integer getTotalAttendedChats();

    Integer getTotalMissedChats();

    Integer getAvgConversationChats();

    Date getMetricDate();
}
