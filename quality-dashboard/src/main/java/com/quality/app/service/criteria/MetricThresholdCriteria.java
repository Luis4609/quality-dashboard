package com.quality.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.quality.app.domain.MetricThreshold} entity. This class is used
 * in {@link com.quality.app.web.rest.MetricThresholdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /metric-thresholds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricThresholdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter entityName;

    private StringFilter metricName;

    private DoubleFilter successPercentage;

    private DoubleFilter dangerPercentage;

    private Boolean distinct;

    public MetricThresholdCriteria() {}

    public MetricThresholdCriteria(MetricThresholdCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.entityName = other.entityName == null ? null : other.entityName.copy();
        this.metricName = other.metricName == null ? null : other.metricName.copy();
        this.successPercentage = other.successPercentage == null ? null : other.successPercentage.copy();
        this.dangerPercentage = other.dangerPercentage == null ? null : other.dangerPercentage.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MetricThresholdCriteria copy() {
        return new MetricThresholdCriteria(this);
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

    public StringFilter getEntityName() {
        return entityName;
    }

    public StringFilter entityName() {
        if (entityName == null) {
            entityName = new StringFilter();
        }
        return entityName;
    }

    public void setEntityName(StringFilter entityName) {
        this.entityName = entityName;
    }

    public StringFilter getMetricName() {
        return metricName;
    }

    public StringFilter metricName() {
        if (metricName == null) {
            metricName = new StringFilter();
        }
        return metricName;
    }

    public void setMetricName(StringFilter metricName) {
        this.metricName = metricName;
    }

    public DoubleFilter getSuccessPercentage() {
        return successPercentage;
    }

    public DoubleFilter successPercentage() {
        if (successPercentage == null) {
            successPercentage = new DoubleFilter();
        }
        return successPercentage;
    }

    public void setSuccessPercentage(DoubleFilter successPercentage) {
        this.successPercentage = successPercentage;
    }

    public DoubleFilter getDangerPercentage() {
        return dangerPercentage;
    }

    public DoubleFilter dangerPercentage() {
        if (dangerPercentage == null) {
            dangerPercentage = new DoubleFilter();
        }
        return dangerPercentage;
    }

    public void setDangerPercentage(DoubleFilter dangerPercentage) {
        this.dangerPercentage = dangerPercentage;
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
        final MetricThresholdCriteria that = (MetricThresholdCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(entityName, that.entityName) &&
            Objects.equals(metricName, that.metricName) &&
            Objects.equals(successPercentage, that.successPercentage) &&
            Objects.equals(dangerPercentage, that.dangerPercentage) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityName, metricName, successPercentage, dangerPercentage, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricThresholdCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (entityName != null ? "entityName=" + entityName + ", " : "") +
            (metricName != null ? "metricName=" + metricName + ", " : "") +
            (successPercentage != null ? "successPercentage=" + successPercentage + ", " : "") +
            (dangerPercentage != null ? "dangerPercentage=" + dangerPercentage + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
