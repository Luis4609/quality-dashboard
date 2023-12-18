package com.quality.app.domain;

import static com.quality.app.domain.DailyChatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.quality.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyChatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyChats.class);
        DailyChats dailyChats1 = getDailyChatsSample1();
        DailyChats dailyChats2 = new DailyChats();
        assertThat(dailyChats1).isNotEqualTo(dailyChats2);

        dailyChats2.setId(dailyChats1.getId());
        assertThat(dailyChats1).isEqualTo(dailyChats2);

        dailyChats2 = getDailyChatsSample2();
        assertThat(dailyChats1).isNotEqualTo(dailyChats2);
    }
}
