package com.rozplochowski.studio.classmanagement.adapter.out.persistence;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PersistenceDanceClassMapper {

    DanceClassEntity toEntity(DanceClass danceClass);
}
