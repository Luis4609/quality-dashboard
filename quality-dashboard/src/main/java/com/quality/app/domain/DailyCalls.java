package com.quality.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DailyCalls.
 */
@Entity
@Table(name = "daily_calls")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyCalls implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "day", nullable = false, unique = true)
    private LocalDate day;

    @Column(name = "total_daily_received_calls")
    private Integer totalDailyReceivedCalls;

    @NotNull
    @Column(name = "total_daily_attended_calls", nullable = false)
    private Integer totalDailyAttendedCalls;

    @NotNull
    @Column(name = "total_daily_missed_calls", nullable = false)
    private Integer totalDailyMissedCalls;

    @NotNull
    @Column(name = "total_daily_received_calls_external_agent", nullable = false)
    private Integer totalDailyReceivedCallsExternalAgent;

    @NotNull
    @Column(name = "total_daily_attended_calls_external_agent", nullable = false)
    private Integer totalDailyAttendedCallsExternalAgent;

    @Column(name = "total_daily_received_calls_internal_agent")
    private Integer totalDailyReceivedCallsInternalAgent;

    @NotNull
    @Column(name = "total_daily_attended_calls_internal_agent", nullable = false)
    private Integer totalDailyAttendedCallsInternalAgent;

    @NotNull
    @Column(name = "total_daily_calls_time_in_min", nullable = false)
    private Integer totalDailyCallsTimeInMin;

    @NotNull
    @Column(name = "total_daily_calls_time_external_agent_in_min", nullable = false)
    private Integer totalDailyCallsTimeExternalAgentInMin;

    @NotNull
    @Column(name = "total_daily_calls_time_internal_agent_in_min", nullable = false)
    private Integer totalDailyCallsTimeInternalAgentInMin;

    @NotNull
    @Column(name = "avg_daily_operation_time_external_agent_in_min", nullable = false)
    private Float avgDailyOperationTimeExternalAgentInMin;

    @Column(name = "avg_daily_operation_time_internal_agent_in_min")
    private Float avgDailyOperationTimeInternalAgentInMin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DailyCalls id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public DailyCalls day(LocalDate day) {
        this.setDay(day);
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getTotalDailyReceivedCalls() {
        return this.totalDailyReceivedCalls;
    }

    public DailyCalls totalDailyReceivedCalls(Integer totalDailyReceivedCalls) {
        this.setTotalDailyReceivedCalls(totalDailyReceivedCalls);
        return this;
    }

    public void setTotalDailyReceivedCalls(Integer totalDailyReceivedCalls) {
        this.totalDailyReceivedCalls = totalDailyReceivedCalls;
    }

    public Integer getTotalDailyAttendedCalls() {
        return this.totalDailyAttendedCalls;
    }

    public DailyCalls totalDailyAttendedCalls(Integer totalDailyAttendedCalls) {
        this.setTotalDailyAttendedCalls(totalDailyAttendedCalls);
        return this;
    }

    public void setTotalDailyAttendedCalls(Integer totalDailyAttendedCalls) {
        this.totalDailyAttendedCalls = totalDailyAttendedCalls;
    }

    public Integer getTotalDailyMissedCalls() {
        return this.totalDailyMissedCalls;
    }

    public DailyCalls totalDailyMissedCalls(Integer totalDailyMissedCalls) {
        this.setTotalDailyMissedCalls(totalDailyMissedCalls);
        return this;
    }

    public void setTotalDailyMissedCalls(Integer totalDailyMissedCalls) {
        this.totalDailyMissedCalls = totalDailyMissedCalls;
    }

    public Integer getTotalDailyReceivedCallsExternalAgent() {
        return this.totalDailyReceivedCallsExternalAgent;
    }

    public DailyCalls totalDailyReceivedCallsExternalAgent(Integer totalDailyReceivedCallsExternalAgent) {
        this.setTotalDailyReceivedCallsExternalAgent(totalDailyReceivedCallsExternalAgent);
        return this;
    }

    public void setTotalDailyReceivedCallsExternalAgent(Integer totalDailyReceivedCallsExternalAgent) {
        this.totalDailyReceivedCallsExternalAgent = totalDailyReceivedCallsExternalAgent;
    }

    public Integer getTotalDailyAttendedCallsExternalAgent() {
        return this.totalDailyAttendedCallsExternalAgent;
    }

    public DailyCalls totalDailyAttendedCallsExternalAgent(Integer totalDailyAttendedCallsExternalAgent) {
        this.setTotalDailyAttendedCallsExternalAgent(totalDailyAttendedCallsExternalAgent);
        return this;
    }

    public void setTotalDailyAttendedCallsExternalAgent(Integer totalDailyAttendedCallsExternalAgent) {
        this.totalDailyAttendedCallsExternalAgent = totalDailyAttendedCallsExternalAgent;
    }

    public Integer getTotalDailyReceivedCallsInternalAgent() {
        return this.totalDailyReceivedCallsInternalAgent;
    }

    public DailyCalls totalDailyReceivedCallsInternalAgent(Integer totalDailyReceivedCallsInternalAgent) {
        this.setTotalDailyReceivedCallsInternalAgent(totalDailyReceivedCallsInternalAgent);
        return this;
    }

    public void setTotalDailyReceivedCallsInternalAgent(Integer totalDailyReceivedCallsInternalAgent) {
        this.totalDailyReceivedCallsInternalAgent = totalDailyReceivedCallsInternalAgent;
    }

    public Integer getTotalDailyAttendedCallsInternalAgent() {
        return this.totalDailyAttendedCallsInternalAgent;
    }

    public DailyCalls totalDailyAttendedCallsInternalAgent(Integer totalDailyAttendedCallsInternalAgent) {
        this.setTotalDailyAttendedCallsInternalAgent(totalDailyAttendedCallsInternalAgent);
        return this;
    }

    public void setTotalDailyAttendedCallsInternalAgent(Integer totalDailyAttendedCallsInternalAgent) {
        this.totalDailyAttendedCallsInternalAgent = totalDailyAttendedCallsInternalAgent;
    }

    public Integer getTotalDailyCallsTimeInMin() {
        return this.totalDailyCallsTimeInMin;
    }

    public DailyCalls totalDailyCallsTimeInMin(Integer totalDailyCallsTimeInMin) {
        this.setTotalDailyCallsTimeInMin(totalDailyCallsTimeInMin);
        return this;
    }

    public void setTotalDailyCallsTimeInMin(Integer totalDailyCallsTimeInMin) {
        this.totalDailyCallsTimeInMin = totalDailyCallsTimeInMin;
    }

    public Integer getTotalDailyCallsTimeExternalAgentInMin() {
        return this.totalDailyCallsTimeExternalAgentInMin;
    }

    public DailyCalls totalDailyCallsTimeExternalAgentInMin(Integer totalDailyCallsTimeExternalAgentInMin) {
        this.setTotalDailyCallsTimeExternalAgentInMin(totalDailyCallsTimeExternalAgentInMin);
        return this;
    }

    public void setTotalDailyCallsTimeExternalAgentInMin(Integer totalDailyCallsTimeExternalAgentInMin) {
        this.totalDailyCallsTimeExternalAgentInMin = totalDailyCallsTimeExternalAgentInMin;
    }

    public Integer getTotalDailyCallsTimeInternalAgentInMin() {
        return this.totalDailyCallsTimeInternalAgentInMin;
    }

    public DailyCalls totalDailyCallsTimeInternalAgentInMin(Integer totalDailyCallsTimeInternalAgentInMin) {
        this.setTotalDailyCallsTimeInternalAgentInMin(totalDailyCallsTimeInternalAgentInMin);
        return this;
    }

    public void setTotalDailyCallsTimeInternalAgentInMin(Integer totalDailyCallsTimeInternalAgentInMin) {
        this.totalDailyCallsTimeInternalAgentInMin = totalDailyCallsTimeInternalAgentInMin;
    }

    public Float getAvgDailyOperationTimeExternalAgentInMin() {
        return this.avgDailyOperationTimeExternalAgentInMin;
    }

    public DailyCalls avgDailyOperationTimeExternalAgentInMin(Float avgDailyOperationTimeExternalAgentInMin) {
        this.setAvgDailyOperationTimeExternalAgentInMin(avgDailyOperationTimeExternalAgentInMin);
        return this;
    }

    public void setAvgDailyOperationTimeExternalAgentInMin(Float avgDailyOperationTimeExternalAgentInMin) {
        this.avgDailyOperationTimeExternalAgentInMin = avgDailyOperationTimeExternalAgentInMin;
    }

    public Float getAvgDailyOperationTimeInternalAgentInMin() {
        return this.avgDailyOperationTimeInternalAgentInMin;
    }

    public DailyCalls avgDailyOperationTimeInternalAgentInMin(Float avgDailyOperationTimeInternalAgentInMin) {
        this.setAvgDailyOperationTimeInternalAgentInMin(avgDailyOperationTimeInternalAgentInMin);
        return this;
    }

    public void setAvgDailyOperationTimeInternalAgentInMin(Float avgDailyOperationTimeInternalAgentInMin) {
        this.avgDailyOperationTimeInternalAgentInMin = avgDailyOperationTimeInternalAgentInMin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyCalls)) {
            return false;
        }
        return getId() != null && getId().equals(((DailyCalls) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyCalls{" +
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
