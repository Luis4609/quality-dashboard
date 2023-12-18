package com.quality.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DailyCallsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DailyCalls getDailyCallsSample1() {
        return new DailyCalls()
            .id(1L)
            .totalDailyReceivedCalls(1)
            .totalDailyAttendedCalls(1)
            .totalDailyMissedCalls(1)
            .totalDailyReceivedCallsExternalAgent(1)
            .totalDailyAttendedCallsExternalAgent(1)
            .totalDailyReceivedCallsInternalAgent(1)
            .totalDailyAttendedCallsInternalAgent(1)
            .totalDailyCallsTimeInMin(1)
            .totalDailyCallsTimeExternalAgentInMin(1)
            .totalDailyCallsTimeInternalAgentInMin(1);
    }

    public static DailyCalls getDailyCallsSample2() {
        return new DailyCalls()
            .id(2L)
            .totalDailyReceivedCalls(2)
            .totalDailyAttendedCalls(2)
            .totalDailyMissedCalls(2)
            .totalDailyReceivedCallsExternalAgent(2)
            .totalDailyAttendedCallsExternalAgent(2)
            .totalDailyReceivedCallsInternalAgent(2)
            .totalDailyAttendedCallsInternalAgent(2)
            .totalDailyCallsTimeInMin(2)
            .totalDailyCallsTimeExternalAgentInMin(2)
            .totalDailyCallsTimeInternalAgentInMin(2);
    }

    public static DailyCalls getDailyCallsRandomSampleGenerator() {
        return new DailyCalls()
            .id(longCount.incrementAndGet())
            .totalDailyReceivedCalls(intCount.incrementAndGet())
            .totalDailyAttendedCalls(intCount.incrementAndGet())
            .totalDailyMissedCalls(intCount.incrementAndGet())
            .totalDailyReceivedCallsExternalAgent(intCount.incrementAndGet())
            .totalDailyAttendedCallsExternalAgent(intCount.incrementAndGet())
            .totalDailyReceivedCallsInternalAgent(intCount.incrementAndGet())
            .totalDailyAttendedCallsInternalAgent(intCount.incrementAndGet())
            .totalDailyCallsTimeInMin(intCount.incrementAndGet())
            .totalDailyCallsTimeExternalAgentInMin(intCount.incrementAndGet())
            .totalDailyCallsTimeInternalAgentInMin(intCount.incrementAndGet());
    }
}
