package com.rozplochowski.studio.classrooms.adapter.out.persistence;

import com.rozplochowski.studio.classrooms.domain.Classroom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    ClassroomEntity toEntity(Classroom classroom);

}
