package com.quality.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.quality.app.domain.MetricThreshold} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricThresholdDTO implements Serializable {

    private Long id;

    @NotNull
    private String entityName;

    @NotNull
    private String metricName;

    private Double successPercentage;

    private Double dangerPercentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Double getSuccessPercentage() {
        return successPercentage;
    }

    public void setSuccessPercentage(Double successPercentage) {
        this.successPercentage = successPercentage;
    }

    public Double getDangerPercentage() {
        return dangerPercentage;
    }

    public void setDangerPercentage(Double dangerPercentage) {
        this.dangerPercentage = dangerPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetricThresholdDTO)) {
            return false;
        }

        MetricThresholdDTO metricThresholdDTO = (MetricThresholdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metricThresholdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricThresholdDTO{" +
            "id=" + getId() +
            ", entityName='" + getEntityName() + "'" +
            ", metricName='" + getMetricName() + "'" +
            ", successPercentage=" + getSuccessPercentage() +
            ", dangerPercentage=" + getDangerPercentage() +
            "}";
    }
}
