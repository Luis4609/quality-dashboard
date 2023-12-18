package com.quality.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.quality.app.domain.DailyChats} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyChatsDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate day;

    @NotNull
    private Integer totalDailyReceivedChats;

    @NotNull
    private Integer totalDailyConversationChatsTimeInMin;

    @NotNull
    private Integer totalDailyAttendedChats;

    @NotNull
    private Integer totalDailyMissedChats;

    @NotNull
    private Float avgDailyConversationChatsTimeInMin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getTotalDailyReceivedChats() {
        return totalDailyReceivedChats;
    }

    public void setTotalDailyReceivedChats(Integer totalDailyReceivedChats) {
        this.totalDailyReceivedChats = totalDailyReceivedChats;
    }

    public Integer getTotalDailyConversationChatsTimeInMin() {
        return totalDailyConversationChatsTimeInMin;
    }

    public void setTotalDailyConversationChatsTimeInMin(Integer totalDailyConversationChatsTimeInMin) {
        this.totalDailyConversationChatsTimeInMin = totalDailyConversationChatsTimeInMin;
    }

    public Integer getTotalDailyAttendedChats() {
        return totalDailyAttendedChats;
    }

    public void setTotalDailyAttendedChats(Integer totalDailyAttendedChats) {
        this.totalDailyAttendedChats = totalDailyAttendedChats;
    }

    public Integer getTotalDailyMissedChats() {
        return totalDailyMissedChats;
    }

    public void setTotalDailyMissedChats(Integer totalDailyMissedChats) {
        this.totalDailyMissedChats = totalDailyMissedChats;
    }

    public Float getAvgDailyConversationChatsTimeInMin() {
        return avgDailyConversationChatsTimeInMin;
    }

    public void setAvgDailyConversationChatsTimeInMin(Float avgDailyConversationChatsTimeInMin) {
        this.avgDailyConversationChatsTimeInMin = avgDailyConversationChatsTimeInMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyChatsDTO)) {
            return false;
        }

        DailyChatsDTO dailyChatsDTO = (DailyChatsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dailyChatsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyChatsDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", totalDailyReceivedChats=" + getTotalDailyReceivedChats() +
            ", totalDailyConversationChatsTimeInMin=" + getTotalDailyConversationChatsTimeInMin() +
            ", totalDailyAttendedChats=" + getTotalDailyAttendedChats() +
            ", totalDailyMissedChats=" + getTotalDailyMissedChats() +
            ", avgDailyConversationChatsTimeInMin=" + getAvgDailyConversationChatsTimeInMin() +
            "}";
    }
}
