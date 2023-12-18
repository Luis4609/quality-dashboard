package com.quality.app.service.mapper;

import com.quality.app.domain.DailyChats;
import com.quality.app.service.dto.DailyChatsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyChats} and its DTO {@link DailyChatsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DailyChatsMapper extends EntityMapper<DailyChatsDTO, DailyChats> {}
