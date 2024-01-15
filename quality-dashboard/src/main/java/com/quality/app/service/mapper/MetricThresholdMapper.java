package com.quality.app.service.mapper;

import com.quality.app.domain.MetricThreshold;
import com.quality.app.service.dto.MetricThresholdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetricThreshold} and its DTO {@link MetricThresholdDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetricThresholdMapper extends EntityMapper<MetricThresholdDTO, MetricThreshold> {}
