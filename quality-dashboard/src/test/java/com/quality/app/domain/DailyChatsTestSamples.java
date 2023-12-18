package com.quality.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DailyChatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DailyChats getDailyChatsSample1() {
        return new DailyChats()
            .id(1L)
            .totalDailyReceivedChats(1)
            .totalDailyConversationChatsTimeInMin(1)
            .totalDailyAttendedChats(1)
            .totalDailyMissedChats(1);
    }

    public static DailyChats getDailyChatsSample2() {
        return new DailyChats()
            .id(2L)
            .totalDailyReceivedChats(2)
            .totalDailyConversationChatsTimeInMin(2)
            .totalDailyAttendedChats(2)
            .totalDailyMissedChats(2);
    }

    public static DailyChats getDailyChatsRandomSampleGenerator() {
        return new DailyChats()
            .id(longCount.incrementAndGet())
            .totalDailyReceivedChats(intCount.incrementAndGet())
            .totalDailyConversationChatsTimeInMin(intCount.incrementAndGet())
            .totalDailyAttendedChats(intCount.incrementAndGet())
            .totalDailyMissedChats(intCount.incrementAndGet());
    }
}
