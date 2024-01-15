package com.quality.app.domain;

import static com.quality.app.domain.MetricThresholdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.quality.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetricThresholdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetricThreshold.class);
        MetricThreshold metricThreshold1 = getMetricThresholdSample1();
        MetricThreshold metricThreshold2 = new MetricThreshold();
        assertThat(metricThreshold1).isNotEqualTo(metricThreshold2);

        metricThreshold2.setId(metricThreshold1.getId());
        assertThat(metricThreshold1).isEqualTo(metricThreshold2);

        metricThreshold2 = getMetricThresholdSample2();
        assertThat(metricThreshold1).isNotEqualTo(metricThreshold2);
    }
}
