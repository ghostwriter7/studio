package com.rozplochowski.studio.classmanagement.adapter.in.web;

import com.rozplochowski.studio.classmanagement.application.port.in.CreateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.application.port.in.UpdateDanceClassCommand;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface WebDanceClassMapper {

    DanceClassResponse toResponse(DanceClass danceClass);
    CreateDanceClassCommand toCommand(CreateDanceClassRequest request);

    UpdateDanceClassCommand toCommand(Long id, UpdateDanceClassRequest request);
}
