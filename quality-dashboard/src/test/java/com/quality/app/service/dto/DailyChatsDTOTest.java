package com.quality.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.quality.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyChatsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyChatsDTO.class);
        DailyChatsDTO dailyChatsDTO1 = new DailyChatsDTO();
        dailyChatsDTO1.setId(1L);
        DailyChatsDTO dailyChatsDTO2 = new DailyChatsDTO();
        assertThat(dailyChatsDTO1).isNotEqualTo(dailyChatsDTO2);
        dailyChatsDTO2.setId(dailyChatsDTO1.getId());
        assertThat(dailyChatsDTO1).isEqualTo(dailyChatsDTO2);
        dailyChatsDTO2.setId(2L);
        assertThat(dailyChatsDTO1).isNotEqualTo(dailyChatsDTO2);
        dailyChatsDTO1.setId(null);
        assertThat(dailyChatsDTO1).isNotEqualTo(dailyChatsDTO2);
    }
}
