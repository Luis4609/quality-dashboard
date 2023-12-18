package com.quality.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.quality.app.domain.DailyChats} entity. This class is used
 * in {@link com.quality.app.web.rest.DailyChatsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /daily-chats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyChatsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter day;

    private IntegerFilter totalDailyReceivedChats;

    private IntegerFilter totalDailyConversationChatsTimeInMin;

    private IntegerFilter totalDailyAttendedChats;

    private IntegerFilter totalDailyMissedChats;

    private FloatFilter avgDailyConversationChatsTimeInMin;

    private Boolean distinct;

    public DailyChatsCriteria() {}

    public DailyChatsCriteria(DailyChatsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.totalDailyReceivedChats = other.totalDailyReceivedChats == null ? null : other.totalDailyReceivedChats.copy();
        this.totalDailyConversationChatsTimeInMin =
            other.totalDailyConversationChatsTimeInMin == null ? null : other.totalDailyConversationChatsTimeInMin.copy();
        this.totalDailyAttendedChats = other.totalDailyAttendedChats == null ? null : other.totalDailyAttendedChats.copy();
        this.totalDailyMissedChats = other.totalDailyMissedChats == null ? null : other.totalDailyMissedChats.copy();
        this.avgDailyConversationChatsTimeInMin =
            other.avgDailyConversationChatsTimeInMin == null ? null : other.avgDailyConversationChatsTimeInMin.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DailyChatsCriteria copy() {
        return new DailyChatsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDay() {
        return day;
    }

    public LocalDateFilter day() {
        if (day == null) {
            day = new LocalDateFilter();
        }
        return day;
    }

    public void setDay(LocalDateFilter day) {
        this.day = day;
    }

    public IntegerFilter getTotalDailyReceivedChats() {
        return totalDailyReceivedChats;
    }

    public IntegerFilter totalDailyReceivedChats() {
        if (totalDailyReceivedChats == null) {
            totalDailyReceivedChats = new IntegerFilter();
        }
        return totalDailyReceivedChats;
    }

    public void setTotalDailyReceivedChats(IntegerFilter totalDailyReceivedChats) {
        this.totalDailyReceivedChats = totalDailyReceivedChats;
    }

    public IntegerFilter getTotalDailyConversationChatsTimeInMin() {
        return totalDailyConversationChatsTimeInMin;
    }

    public IntegerFilter totalDailyConversationChatsTimeInMin() {
        if (totalDailyConversationChatsTimeInMin == null) {
            totalDailyConversationChatsTimeInMin = new IntegerFilter();
        }
        return totalDailyConversationChatsTimeInMin;
    }

    public void setTotalDailyConversationChatsTimeInMin(IntegerFilter totalDailyConversationChatsTimeInMin) {
        this.totalDailyConversationChatsTimeInMin = totalDailyConversationChatsTimeInMin;
    }

    public IntegerFilter getTotalDailyAttendedChats() {
        return totalDailyAttendedChats;
    }

    public IntegerFilter totalDailyAttendedChats() {
        if (totalDailyAttendedChats == null) {
            totalDailyAttendedChats = new IntegerFilter();
        }
        return totalDailyAttendedChats;
    }

    public void setTotalDailyAttendedChats(IntegerFilter totalDailyAttendedChats) {
        this.totalDailyAttendedChats = totalDailyAttendedChats;
    }

    public IntegerFilter getTotalDailyMissedChats() {
        return totalDailyMissedChats;
    }

    public IntegerFilter totalDailyMissedChats() {
        if (totalDailyMissedChats == null) {
            totalDailyMissedChats = new IntegerFilter();
        }
        return totalDailyMissedChats;
    }

    public void setTotalDailyMissedChats(IntegerFilter totalDailyMissedChats) {
        this.totalDailyMissedChats = totalDailyMissedChats;
    }

    public FloatFilter getAvgDailyConversationChatsTimeInMin() {
        return avgDailyConversationChatsTimeInMin;
    }

    public FloatFilter avgDailyConversationChatsTimeInMin() {
        if (avgDailyConversationChatsTimeInMin == null) {
            avgDailyConversationChatsTimeInMin = new FloatFilter();
        }
        return avgDailyConversationChatsTimeInMin;
    }

    public void setAvgDailyConversationChatsTimeInMin(FloatFilter avgDailyConversationChatsTimeInMin) {
        this.avgDailyConversationChatsTimeInMin = avgDailyConversationChatsTimeInMin;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DailyChatsCriteria that = (DailyChatsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(totalDailyReceivedChats, that.totalDailyReceivedChats) &&
            Objects.equals(totalDailyConversationChatsTimeInMin, that.totalDailyConversationChatsTimeInMin) &&
            Objects.equals(totalDailyAttendedChats, that.totalDailyAttendedChats) &&
            Objects.equals(totalDailyMissedChats, that.totalDailyMissedChats) &&
            Objects.equals(avgDailyConversationChatsTimeInMin, that.avgDailyConversationChatsTimeInMin) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            day,
            totalDailyReceivedChats,
            totalDailyConversationChatsTimeInMin,
            totalDailyAttendedChats,
            totalDailyMissedChats,
            avgDailyConversationChatsTimeInMin,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyChatsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (totalDailyReceivedChats != null ? "totalDailyReceivedChats=" + totalDailyReceivedChats + ", " : "") +
            (totalDailyConversationChatsTimeInMin != null ? "totalDailyConversationChatsTimeInMin=" + totalDailyConversationChatsTimeInMin + ", " : "") +
            (totalDailyAttendedChats != null ? "totalDailyAttendedChats=" + totalDailyAttendedChats + ", " : "") +
            (totalDailyMissedChats != null ? "totalDailyMissedChats=" + totalDailyMissedChats + ", " : "") +
            (avgDailyConversationChatsTimeInMin != null ? "avgDailyConversationChatsTimeInMin=" + avgDailyConversationChatsTimeInMin + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
