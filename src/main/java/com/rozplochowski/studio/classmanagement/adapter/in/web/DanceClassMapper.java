package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DanceClassMapper {

    DanceClassResponse toResponse(DanceClass danceClass);
    CreateDanceClassCommand toCommand(CreateDanceClassRequest request);
}
