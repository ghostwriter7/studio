package com.rozplochowski.studio.classrooms.adapter.out.persistence;

import com.rozplochowski.studio.classrooms.domain.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassroomMapper {

    ClassroomMapper INSTANCE = Mappers.getMapper(ClassroomMapper.class);

    ClassroomEntity toEntity(Classroom classroom);

    Classroom toDomain(ClassroomEntity classroomEntity);
}
