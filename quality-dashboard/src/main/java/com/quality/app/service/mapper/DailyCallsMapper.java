package com.quality.app.service.mapper;

import com.quality.app.domain.DailyCalls;
import com.quality.app.service.dto.DailyCallsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyCalls} and its DTO {@link DailyCallsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DailyCallsMapper extends EntityMapper<DailyCallsDTO, DailyCalls> {}
