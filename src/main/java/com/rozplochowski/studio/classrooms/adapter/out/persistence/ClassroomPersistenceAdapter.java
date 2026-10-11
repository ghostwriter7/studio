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
    private final ClassroomMapper mapper;

    @Override
    public Classroom save(Classroom classroom) {
        var entity = mapper.toEntity(classroom);
        var saved = classroomRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Classroom> loadById(long id) {
        return classroomRepository.findById(id).map(this::toDomain);
    }

    private Classroom toDomain(ClassroomEntity entity) {
        return Classroom.reconstitute(entity.getId(), entity.getName(), entity.getCapacity());
    }
}
