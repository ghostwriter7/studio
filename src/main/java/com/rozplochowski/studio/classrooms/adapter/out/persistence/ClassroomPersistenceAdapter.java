package com.rozplochowski.studio.classrooms.adapter.out.persistence;

import com.rozplochowski.studio.classrooms.application.port.out.LoadClassroomPort;
import com.rozplochowski.studio.classrooms.application.port.out.SaveClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ClassroomPersistenceAdapter implements SaveClassroomPort, LoadClassroomPort {
    private final ClassroomRepository classroomRepository;

    @Override
    public Classroom save(Classroom classroom) {
        var entity = ClassroomMapper.INSTANCE.toEntity(classroom);
        var saved = classroomRepository.save(entity);
        return ClassroomMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public Optional<Classroom> loadById(long id) {
        return classroomRepository.findById(id).map(ClassroomMapper.INSTANCE::toDomain);
    }
}
