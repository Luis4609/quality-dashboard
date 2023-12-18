package com.quality.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.quality.app.domain.DailyCalls} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyCallsDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate day;

    private Integer totalDailyReceivedCalls;

    @NotNull
    private Integer totalDailyAttendedCalls;

    @NotNull
    private Integer totalDailyMissedCalls;

    @NotNull
    private Integer totalDailyReceivedCallsExternalAgent;

    @NotNull
    private Integer totalDailyAttendedCallsExternalAgent;

    private Integer totalDailyReceivedCallsInternalAgent;

    @NotNull
    private Integer totalDailyAttendedCallsInternalAgent;

    @NotNull
    private Integer totalDailyCallsTimeInMin;

    @NotNull
    private Integer totalDailyCallsTimeExternalAgentInMin;

    @NotNull
    private Integer totalDailyCallsTimeInternalAgentInMin;

    @NotNull
    private Float avgDailyOperationTimeExternalAgentInMin;

    private Float avgDailyOperationTimeInternalAgentInMin;

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

    public Integer getTotalDailyReceivedCalls() {
        return totalDailyReceivedCalls;
    }

    public void setTotalDailyReceivedCalls(Integer totalDailyReceivedCalls) {
        this.totalDailyReceivedCalls = totalDailyReceivedCalls;
    }

    public Integer getTotalDailyAttendedCalls() {
        return totalDailyAttendedCalls;
    }

    public void setTotalDailyAttendedCalls(Integer totalDailyAttendedCalls) {
        this.totalDailyAttendedCalls = totalDailyAttendedCalls;
    }

    public Integer getTotalDailyMissedCalls() {
        return totalDailyMissedCalls;
    }

    public void setTotalDailyMissedCalls(Integer totalDailyMissedCalls) {
        this.totalDailyMissedCalls = totalDailyMissedCalls;
    }

    public Integer getTotalDailyReceivedCallsExternalAgent() {
        return totalDailyReceivedCallsExternalAgent;
    }

    public void setTotalDailyReceivedCallsExternalAgent(Integer totalDailyReceivedCallsExternalAgent) {
        this.totalDailyReceivedCallsExternalAgent = totalDailyReceivedCallsExternalAgent;
    }

    public Integer getTotalDailyAttendedCallsExternalAgent() {
        return totalDailyAttendedCallsExternalAgent;
    }

    public void setTotalDailyAttendedCallsExternalAgent(Integer totalDailyAttendedCallsExternalAgent) {
        this.totalDailyAttendedCallsExternalAgent = totalDailyAttendedCallsExternalAgent;
    }

    public Integer getTotalDailyReceivedCallsInternalAgent() {
        return totalDailyReceivedCallsInternalAgent;
    }

    public void setTotalDailyReceivedCallsInternalAgent(Integer totalDailyReceivedCallsInternalAgent) {
        this.totalDailyReceivedCallsInternalAgent = totalDailyReceivedCallsInternalAgent;
    }

    public Integer getTotalDailyAttendedCallsInternalAgent() {
        return totalDailyAttendedCallsInternalAgent;
    }

    public void setTotalDailyAttendedCallsInternalAgent(Integer totalDailyAttendedCallsInternalAgent) {
        this.totalDailyAttendedCallsInternalAgent = totalDailyAttendedCallsInternalAgent;
    }

    public Integer getTotalDailyCallsTimeInMin() {
        return totalDailyCallsTimeInMin;
    }

    public void setTotalDailyCallsTimeInMin(Integer totalDailyCallsTimeInMin) {
        this.totalDailyCallsTimeInMin = totalDailyCallsTimeInMin;
    }

    public Integer getTotalDailyCallsTimeExternalAgentInMin() {
        return totalDailyCallsTimeExternalAgentInMin;
    }

    public void setTotalDailyCallsTimeExternalAgentInMin(Integer totalDailyCallsTimeExternalAgentInMin) {
        this.totalDailyCallsTimeExternalAgentInMin = totalDailyCallsTimeExternalAgentInMin;
    }

    public Integer getTotalDailyCallsTimeInternalAgentInMin() {
        return totalDailyCallsTimeInternalAgentInMin;
    }

    public void setTotalDailyCallsTimeInternalAgentInMin(Integer totalDailyCallsTimeInternalAgentInMin) {
        this.totalDailyCallsTimeInternalAgentInMin = totalDailyCallsTimeInternalAgentInMin;
    }

    public Float getAvgDailyOperationTimeExternalAgentInMin() {
        return avgDailyOperationTimeExternalAgentInMin;
    }

    public void setAvgDailyOperationTimeExternalAgentInMin(Float avgDailyOperationTimeExternalAgentInMin) {
        this.avgDailyOperationTimeExternalAgentInMin = avgDailyOperationTimeExternalAgentInMin;
    }

    public Float getAvgDailyOperationTimeInternalAgentInMin() {
        return avgDailyOperationTimeInternalAgentInMin;
    }

    public void setAvgDailyOperationTimeInternalAgentInMin(Float avgDailyOperationTimeInternalAgentInMin) {
        this.avgDailyOperationTimeInternalAgentInMin = avgDailyOperationTimeInternalAgentInMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyCallsDTO)) {
            return false;
        }

        DailyCallsDTO dailyCallsDTO = (DailyCallsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dailyCallsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyCallsDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", totalDailyReceivedCalls=" + getTotalDailyReceivedCalls() +
            ", totalDailyAttendedCalls=" + getTotalDailyAttendedCalls() +
            ", totalDailyMissedCalls=" + getTotalDailyMissedCalls() +
            ", totalDailyReceivedCallsExternalAgent=" + getTotalDailyReceivedCallsExternalAgent() +
            ", totalDailyAttendedCallsExternalAgent=" + getTotalDailyAttendedCallsExternalAgent() +
            ", totalDailyReceivedCallsInternalAgent=" + getTotalDailyReceivedCallsInternalAgent() +
            ", totalDailyAttendedCallsInternalAgent=" + getTotalDailyAttendedCallsInternalAgent() +
            ", totalDailyCallsTimeInMin=" + getTotalDailyCallsTimeInMin() +
            ", totalDailyCallsTimeExternalAgentInMin=" + getTotalDailyCallsTimeExternalAgentInMin() +
            ", totalDailyCallsTimeInternalAgentInMin=" + getTotalDailyCallsTimeInternalAgentInMin() +
            ", avgDailyOperationTimeExternalAgentInMin=" + getAvgDailyOperationTimeExternalAgentInMin() +
            ", avgDailyOperationTimeInternalAgentInMin=" + getAvgDailyOperationTimeInternalAgentInMin() +
            "}";
    }
}
