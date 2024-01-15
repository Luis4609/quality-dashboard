package com.quality.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.quality.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetricThresholdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetricThresholdDTO.class);
        MetricThresholdDTO metricThresholdDTO1 = new MetricThresholdDTO();
        metricThresholdDTO1.setId(1L);
        MetricThresholdDTO metricThresholdDTO2 = new MetricThresholdDTO();
        assertThat(metricThresholdDTO1).isNotEqualTo(metricThresholdDTO2);
        metricThresholdDTO2.setId(metricThresholdDTO1.getId());
        assertThat(metricThresholdDTO1).isEqualTo(metricThresholdDTO2);
        metricThresholdDTO2.setId(2L);
        assertThat(metricThresholdDTO1).isNotEqualTo(metricThresholdDTO2);
        metricThresholdDTO1.setId(null);
        assertThat(metricThresholdDTO1).isNotEqualTo(metricThresholdDTO2);
    }
}
