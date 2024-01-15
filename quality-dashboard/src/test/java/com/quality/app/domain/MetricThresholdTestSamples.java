package com.quality.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MetricThresholdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MetricThreshold getMetricThresholdSample1() {
        return new MetricThreshold().id(1L).entityName("entityName1").metricName("metricName1");
    }

    public static MetricThreshold getMetricThresholdSample2() {
        return new MetricThreshold().id(2L).entityName("entityName2").metricName("metricName2");
    }

    public static MetricThreshold getMetricThresholdRandomSampleGenerator() {
        return new MetricThreshold()
            .id(longCount.incrementAndGet())
            .entityName(UUID.randomUUID().toString())
            .metricName(UUID.randomUUID().toString());
    }
}
