package com.quality.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.quality.app.domain.DailyCalls} entity. This class is used
 * in {@link com.quality.app.web.rest.DailyCallsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /daily-calls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyCallsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter day;

    private IntegerFilter totalDailyReceivedCalls;

    private IntegerFilter totalDailyAttendedCalls;

    private IntegerFilter totalDailyMissedCalls;

    private IntegerFilter totalDailyReceivedCallsExternalAgent;

    private IntegerFilter totalDailyAttendedCallsExternalAgent;

    private IntegerFilter totalDailyReceivedCallsInternalAgent;

    private IntegerFilter totalDailyAttendedCallsInternalAgent;

    private IntegerFilter totalDailyCallsTimeInMin;

    private IntegerFilter totalDailyCallsTimeExternalAgentInMin;

    private IntegerFilter totalDailyCallsTimeInternalAgentInMin;

    private FloatFilter avgDailyOperationTimeExternalAgentInMin;

    private FloatFilter avgDailyOperationTimeInternalAgentInMin;

    private Boolean distinct;

    public DailyCallsCriteria() {}

    public DailyCallsCriteria(DailyCallsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.totalDailyReceivedCalls = other.totalDailyReceivedCalls == null ? null : other.totalDailyReceivedCalls.copy();
        this.totalDailyAttendedCalls = other.totalDailyAttendedCalls == null ? null : other.totalDailyAttendedCalls.copy();
        this.totalDailyMissedCalls = other.totalDailyMissedCalls == null ? null : other.totalDailyMissedCalls.copy();
        this.totalDailyReceivedCallsExternalAgent =
            other.totalDailyReceivedCallsExternalAgent == null ? null : other.totalDailyReceivedCallsExternalAgent.copy();
        this.totalDailyAttendedCallsExternalAgent =
            other.totalDailyAttendedCallsExternalAgent == null ? null : other.totalDailyAttendedCallsExternalAgent.copy();
        this.totalDailyReceivedCallsInternalAgent =
            other.totalDailyReceivedCallsInternalAgent == null ? null : other.totalDailyReceivedCallsInternalAgent.copy();
        this.totalDailyAttendedCallsInternalAgent =
            other.totalDailyAttendedCallsInternalAgent == null ? null : other.totalDailyAttendedCallsInternalAgent.copy();
        this.totalDailyCallsTimeInMin = other.totalDailyCallsTimeInMin == null ? null : other.totalDailyCallsTimeInMin.copy();
        this.totalDailyCallsTimeExternalAgentInMin =
            other.totalDailyCallsTimeExternalAgentInMin == null ? null : other.totalDailyCallsTimeExternalAgentInMin.copy();
        this.totalDailyCallsTimeInternalAgentInMin =
            other.totalDailyCallsTimeInternalAgentInMin == null ? null : other.totalDailyCallsTimeInternalAgentInMin.copy();
        this.avgDailyOperationTimeExternalAgentInMin =
            other.avgDailyOperationTimeExternalAgentInMin == null ? null : other.avgDailyOperationTimeExternalAgentInMin.copy();
        this.avgDailyOperationTimeInternalAgentInMin =
            other.avgDailyOperationTimeInternalAgentInMin == null ? null : other.avgDailyOperationTimeInternalAgentInMin.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DailyCallsCriteria copy() {
        return new DailyCallsCriteria(this);
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

    public IntegerFilter getTotalDailyReceivedCalls() {
        return totalDailyReceivedCalls;
    }

    public IntegerFilter totalDailyReceivedCalls() {
        if (totalDailyReceivedCalls == null) {
            totalDailyReceivedCalls = new IntegerFilter();
        }
        return totalDailyReceivedCalls;
    }

    public void setTotalDailyReceivedCalls(IntegerFilter totalDailyReceivedCalls) {
        this.totalDailyReceivedCalls = totalDailyReceivedCalls;
    }

    public IntegerFilter getTotalDailyAttendedCalls() {
        return totalDailyAttendedCalls;
    }

    public IntegerFilter totalDailyAttendedCalls() {
        if (totalDailyAttendedCalls == null) {
            totalDailyAttendedCalls = new IntegerFilter();
        }
        return totalDailyAttendedCalls;
    }

    public void setTotalDailyAttendedCalls(IntegerFilter totalDailyAttendedCalls) {
        this.totalDailyAttendedCalls = totalDailyAttendedCalls;
    }

    public IntegerFilter getTotalDailyMissedCalls() {
        return totalDailyMissedCalls;
    }

    public IntegerFilter totalDailyMissedCalls() {
        if (totalDailyMissedCalls == null) {
            totalDailyMissedCalls = new IntegerFilter();
        }
        return totalDailyMissedCalls;
    }

    public void setTotalDailyMissedCalls(IntegerFilter totalDailyMissedCalls) {
        this.totalDailyMissedCalls = totalDailyMissedCalls;
    }

    public IntegerFilter getTotalDailyReceivedCallsExternalAgent() {
        return totalDailyReceivedCallsExternalAgent;
    }

    public IntegerFilter totalDailyReceivedCallsExternalAgent() {
        if (totalDailyReceivedCallsExternalAgent == null) {
            totalDailyReceivedCallsExternalAgent = new IntegerFilter();
        }
        return totalDailyReceivedCallsExternalAgent;
    }

    public void setTotalDailyReceivedCallsExternalAgent(IntegerFilter totalDailyReceivedCallsExternalAgent) {
        this.totalDailyReceivedCallsExternalAgent = totalDailyReceivedCallsExternalAgent;
    }

    public IntegerFilter getTotalDailyAttendedCallsExternalAgent() {
        return totalDailyAttendedCallsExternalAgent;
    }

    public IntegerFilter totalDailyAttendedCallsExternalAgent() {
        if (totalDailyAttendedCallsExternalAgent == null) {
            totalDailyAttendedCallsExternalAgent = new IntegerFilter();
        }
        return totalDailyAttendedCallsExternalAgent;
    }

    public void setTotalDailyAttendedCallsExternalAgent(IntegerFilter totalDailyAttendedCallsExternalAgent) {
        this.totalDailyAttendedCallsExternalAgent = totalDailyAttendedCallsExternalAgent;
    }

    public IntegerFilter getTotalDailyReceivedCallsInternalAgent() {
        return totalDailyReceivedCallsInternalAgent;
    }

    public IntegerFilter totalDailyReceivedCallsInternalAgent() {
        if (totalDailyReceivedCallsInternalAgent == null) {
            totalDailyReceivedCallsInternalAgent = new IntegerFilter();
        }
        return totalDailyReceivedCallsInternalAgent;
    }

    public void setTotalDailyReceivedCallsInternalAgent(IntegerFilter totalDailyReceivedCallsInternalAgent) {
        this.totalDailyReceivedCallsInternalAgent = totalDailyReceivedCallsInternalAgent;
    }

    public IntegerFilter getTotalDailyAttendedCallsInternalAgent() {
        return totalDailyAttendedCallsInternalAgent;
    }

    public IntegerFilter totalDailyAttendedCallsInternalAgent() {
        if (totalDailyAttendedCallsInternalAgent == null) {
            totalDailyAttendedCallsInternalAgent = new IntegerFilter();
        }
        return totalDailyAttendedCallsInternalAgent;
    }

    public void setTotalDailyAttendedCallsInternalAgent(IntegerFilter totalDailyAttendedCallsInternalAgent) {
        this.totalDailyAttendedCallsInternalAgent = totalDailyAttendedCallsInternalAgent;
    }

    public IntegerFilter getTotalDailyCallsTimeInMin() {
        return totalDailyCallsTimeInMin;
    }

    public IntegerFilter totalDailyCallsTimeInMin() {
        if (totalDailyCallsTimeInMin == null) {
            totalDailyCallsTimeInMin = new IntegerFilter();
        }
        return totalDailyCallsTimeInMin;
    }

    public void setTotalDailyCallsTimeInMin(IntegerFilter totalDailyCallsTimeInMin) {
        this.totalDailyCallsTimeInMin = totalDailyCallsTimeInMin;
    }

    public IntegerFilter getTotalDailyCallsTimeExternalAgentInMin() {
        return totalDailyCallsTimeExternalAgentInMin;
    }

    public IntegerFilter totalDailyCallsTimeExternalAgentInMin() {
        if (totalDailyCallsTimeExternalAgentInMin == null) {
            totalDailyCallsTimeExternalAgentInMin = new IntegerFilter();
        }
        return totalDailyCallsTimeExternalAgentInMin;
    }

    public void setTotalDailyCallsTimeExternalAgentInMin(IntegerFilter totalDailyCallsTimeExternalAgentInMin) {
        this.totalDailyCallsTimeExternalAgentInMin = totalDailyCallsTimeExternalAgentInMin;
    }

    public IntegerFilter getTotalDailyCallsTimeInternalAgentInMin() {
        return totalDailyCallsTimeInternalAgentInMin;
    }

    public IntegerFilter totalDailyCallsTimeInternalAgentInMin() {
        if (totalDailyCallsTimeInternalAgentInMin == null) {
            totalDailyCallsTimeInternalAgentInMin = new IntegerFilter();
        }
        return totalDailyCallsTimeInternalAgentInMin;
    }

    public void setTotalDailyCallsTimeInternalAgentInMin(IntegerFilter totalDailyCallsTimeInternalAgentInMin) {
        this.totalDailyCallsTimeInternalAgentInMin = totalDailyCallsTimeInternalAgentInMin;
    }

    public FloatFilter getAvgDailyOperationTimeExternalAgentInMin() {
        return avgDailyOperationTimeExternalAgentInMin;
    }

    public FloatFilter avgDailyOperationTimeExternalAgentInMin() {
        if (avgDailyOperationTimeExternalAgentInMin == null) {
            avgDailyOperationTimeExternalAgentInMin = new FloatFilter();
        }
        return avgDailyOperationTimeExternalAgentInMin;
    }

    public void setAvgDailyOperationTimeExternalAgentInMin(FloatFilter avgDailyOperationTimeExternalAgentInMin) {
        this.avgDailyOperationTimeExternalAgentInMin = avgDailyOperationTimeExternalAgentInMin;
    }

    public FloatFilter getAvgDailyOperationTimeInternalAgentInMin() {
        return avgDailyOperationTimeInternalAgentInMin;
    }

    public FloatFilter avgDailyOperationTimeInternalAgentInMin() {
        if (avgDailyOperationTimeInternalAgentInMin == null) {
            avgDailyOperationTimeInternalAgentInMin = new FloatFilter();
        }
        return avgDailyOperationTimeInternalAgentInMin;
    }

    public void setAvgDailyOperationTimeInternalAgentInMin(FloatFilter avgDailyOperationTimeInternalAgentInMin) {
        this.avgDailyOperationTimeInternalAgentInMin = avgDailyOperationTimeInternalAgentInMin;
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
        final DailyCallsCriteria that = (DailyCallsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(totalDailyReceivedCalls, that.totalDailyReceivedCalls) &&
            Objects.equals(totalDailyAttendedCalls, that.totalDailyAttendedCalls) &&
            Objects.equals(totalDailyMissedCalls, that.totalDailyMissedCalls) &&
            Objects.equals(totalDailyReceivedCallsExternalAgent, that.totalDailyReceivedCallsExternalAgent) &&
            Objects.equals(totalDailyAttendedCallsExternalAgent, that.totalDailyAttendedCallsExternalAgent) &&
            Objects.equals(totalDailyReceivedCallsInternalAgent, that.totalDailyReceivedCallsInternalAgent) &&
            Objects.equals(totalDailyAttendedCallsInternalAgent, that.totalDailyAttendedCallsInternalAgent) &&
            Objects.equals(totalDailyCallsTimeInMin, that.totalDailyCallsTimeInMin) &&
            Objects.equals(totalDailyCallsTimeExternalAgentInMin, that.totalDailyCallsTimeExternalAgentInMin) &&
            Objects.equals(totalDailyCallsTimeInternalAgentInMin, that.totalDailyCallsTimeInternalAgentInMin) &&
            Objects.equals(avgDailyOperationTimeExternalAgentInMin, that.avgDailyOperationTimeExternalAgentInMin) &&
            Objects.equals(avgDailyOperationTimeInternalAgentInMin, that.avgDailyOperationTimeInternalAgentInMin) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            day,
            totalDailyReceivedCalls,
            totalDailyAttendedCalls,
            totalDailyMissedCalls,
            totalDailyReceivedCallsExternalAgent,
            totalDailyAttendedCallsExternalAgent,
            totalDailyReceivedCallsInternalAgent,
            totalDailyAttendedCallsInternalAgent,
            totalDailyCallsTimeInMin,
            totalDailyCallsTimeExternalAgentInMin,
            totalDailyCallsTimeInternalAgentInMin,
            avgDailyOperationTimeExternalAgentInMin,
            avgDailyOperationTimeInternalAgentInMin,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyCallsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (totalDailyReceivedCalls != null ? "totalDailyReceivedCalls=" + totalDailyReceivedCalls + ", " : "") +
            (totalDailyAttendedCalls != null ? "totalDailyAttendedCalls=" + totalDailyAttendedCalls + ", " : "") +
            (totalDailyMissedCalls != null ? "totalDailyMissedCalls=" + totalDailyMissedCalls + ", " : "") +
            (totalDailyReceivedCallsExternalAgent != null ? "totalDailyReceivedCallsExternalAgent=" + totalDailyReceivedCallsExternalAgent + ", " : "") +
            (totalDailyAttendedCallsExternalAgent != null ? "totalDailyAttendedCallsExternalAgent=" + totalDailyAttendedCallsExternalAgent + ", " : "") +
            (totalDailyReceivedCallsInternalAgent != null ? "totalDailyReceivedCallsInternalAgent=" + totalDailyReceivedCallsInternalAgent + ", " : "") +
            (totalDailyAttendedCallsInternalAgent != null ? "totalDailyAttendedCallsInternalAgent=" + totalDailyAttendedCallsInternalAgent + ", " : "") +
            (totalDailyCallsTimeInMin != null ? "totalDailyCallsTimeInMin=" + totalDailyCallsTimeInMin + ", " : "") +
            (totalDailyCallsTimeExternalAgentInMin != null ? "totalDailyCallsTimeExternalAgentInMin=" + totalDailyCallsTimeExternalAgentInMin + ", " : "") +
            (totalDailyCallsTimeInternalAgentInMin != null ? "totalDailyCallsTimeInternalAgentInMin=" + totalDailyCallsTimeInternalAgentInMin + ", " : "") +
            (avgDailyOperationTimeExternalAgentInMin != null ? "avgDailyOperationTimeExternalAgentInMin=" + avgDailyOperationTimeExternalAgentInMin + ", " : "") +
            (avgDailyOperationTimeInternalAgentInMin != null ? "avgDailyOperationTimeInternalAgentInMin=" + avgDailyOperationTimeInternalAgentInMin + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
