package com.quality.app.domain;

import static com.quality.app.domain.DailyCallsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.quality.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyCallsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyCalls.class);
        DailyCalls dailyCalls1 = getDailyCallsSample1();
        DailyCalls dailyCalls2 = new DailyCalls();
        assertThat(dailyCalls1).isNotEqualTo(dailyCalls2);

        dailyCalls2.setId(dailyCalls1.getId());
        assertThat(dailyCalls1).isEqualTo(dailyCalls2);

        dailyCalls2 = getDailyCallsSample2();
        assertThat(dailyCalls1).isNotEqualTo(dailyCalls2);
    }
}
