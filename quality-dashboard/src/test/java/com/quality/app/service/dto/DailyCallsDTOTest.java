package com.quality.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.quality.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyCallsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyCallsDTO.class);
        DailyCallsDTO dailyCallsDTO1 = new DailyCallsDTO();
        dailyCallsDTO1.setId(1L);
        DailyCallsDTO dailyCallsDTO2 = new DailyCallsDTO();
        assertThat(dailyCallsDTO1).isNotEqualTo(dailyCallsDTO2);
        dailyCallsDTO2.setId(dailyCallsDTO1.getId());
        assertThat(dailyCallsDTO1).isEqualTo(dailyCallsDTO2);
        dailyCallsDTO2.setId(2L);
        assertThat(dailyCallsDTO1).isNotEqualTo(dailyCallsDTO2);
        dailyCallsDTO1.setId(null);
        assertThat(dailyCallsDTO1).isNotEqualTo(dailyCallsDTO2);
    }
}
