package com.quality.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DailyChats.
 */
@Entity
@Table(name = "daily_chats")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyChats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "day", nullable = false, unique = true)
    private LocalDate day;

    @NotNull
    @Column(name = "total_daily_received_chats", nullable = false)
    private Integer totalDailyReceivedChats;

    @NotNull
    @Column(name = "total_daily_conversation_chats_time_in_min", nullable = false)
    private Integer totalDailyConversationChatsTimeInMin;

    @NotNull
    @Column(name = "total_daily_attended_chats", nullable = false)
    private Integer totalDailyAttendedChats;

    @NotNull
    @Column(name = "total_daily_missed_chats", nullable = false)
    private Integer totalDailyMissedChats;

    @NotNull
    @Column(name = "avg_daily_conversation_chats_time_in_min", nullable = false)
    private Float avgDailyConversationChatsTimeInMin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DailyChats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public DailyChats day(LocalDate day) {
        this.setDay(day);
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getTotalDailyReceivedChats() {
        return this.totalDailyReceivedChats;
    }

    public DailyChats totalDailyReceivedChats(Integer totalDailyReceivedChats) {
        this.setTotalDailyReceivedChats(totalDailyReceivedChats);
        return this;
    }

    public void setTotalDailyReceivedChats(Integer totalDailyReceivedChats) {
        this.totalDailyReceivedChats = totalDailyReceivedChats;
    }

    public Integer getTotalDailyConversationChatsTimeInMin() {
        return this.totalDailyConversationChatsTimeInMin;
    }

    public DailyChats totalDailyConversationChatsTimeInMin(Integer totalDailyConversationChatsTimeInMin) {
        this.setTotalDailyConversationChatsTimeInMin(totalDailyConversationChatsTimeInMin);
        return this;
    }

    public void setTotalDailyConversationChatsTimeInMin(Integer totalDailyConversationChatsTimeInMin) {
        this.totalDailyConversationChatsTimeInMin = totalDailyConversationChatsTimeInMin;
    }

    public Integer getTotalDailyAttendedChats() {
        return this.totalDailyAttendedChats;
    }

    public DailyChats totalDailyAttendedChats(Integer totalDailyAttendedChats) {
        this.setTotalDailyAttendedChats(totalDailyAttendedChats);
        return this;
    }

    public void setTotalDailyAttendedChats(Integer totalDailyAttendedChats) {
        this.totalDailyAttendedChats = totalDailyAttendedChats;
    }

    public Integer getTotalDailyMissedChats() {
        return this.totalDailyMissedChats;
    }

    public DailyChats totalDailyMissedChats(Integer totalDailyMissedChats) {
        this.setTotalDailyMissedChats(totalDailyMissedChats);
        return this;
    }

    public void setTotalDailyMissedChats(Integer totalDailyMissedChats) {
        this.totalDailyMissedChats = totalDailyMissedChats;
    }

    public Float getAvgDailyConversationChatsTimeInMin() {
        return this.avgDailyConversationChatsTimeInMin;
    }

    public DailyChats avgDailyConversationChatsTimeInMin(Float avgDailyConversationChatsTimeInMin) {
        this.setAvgDailyConversationChatsTimeInMin(avgDailyConversationChatsTimeInMin);
        return this;
    }

    public void setAvgDailyConversationChatsTimeInMin(Float avgDailyConversationChatsTimeInMin) {
        this.avgDailyConversationChatsTimeInMin = avgDailyConversationChatsTimeInMin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyChats)) {
            return false;
        }
        return getId() != null && getId().equals(((DailyChats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyChats{" +
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
