package com.quality.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MetricThreshold.
 */
@Entity
@Table(name = "metric_threshold")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricThreshold implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @NotNull
    @Column(name = "metric_name", nullable = false)
    private String metricName;

    @Column(name = "success_percentage")
    private Double successPercentage;

    @Column(name = "danger_percentage")
    private Double dangerPercentage;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public MetricThreshold() {
    }

    public Long getId() {
        return this.id;
    }

    public MetricThreshold id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public MetricThreshold entityName(String entityName) {
        this.setEntityName(entityName);
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getMetricName() {
        return this.metricName;
    }

    public MetricThreshold metricName(String metricName) {
        this.setMetricName(metricName);
        return this;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Double getSuccessPercentage() {
        return this.successPercentage;
    }

    public MetricThreshold successPercentage(Double successPercentage) {
        this.setSuccessPercentage(successPercentage);
        return this;
    }

    public void setSuccessPercentage(Double successPercentage) {
        this.successPercentage = successPercentage;
    }

    public Double getDangerPercentage() {
        return this.dangerPercentage;
    }

    public MetricThreshold dangerPercentage(Double dangerPercentage) {
        this.setDangerPercentage(dangerPercentage);
        return this;
    }

    public void setDangerPercentage(Double dangerPercentage) {
        this.dangerPercentage = dangerPercentage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetricThreshold)) {
            return false;
        }
        return getId() != null && getId().equals(((MetricThreshold) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricThreshold{" +
            "id=" + getId() +
            ", entityName='" + getEntityName() + "'" +
            ", metricName='" + getMetricName() + "'" +
            ", successPercentage=" + getSuccessPercentage() +
            ", dangerPercentage=" + getDangerPercentage() +
            "}";
    }
}
