package com.rozplochowski.studio.classrooms.adapter.out.persistence;

import com.rozplochowski.studio.classrooms.application.exception.DuplicateClassroomNameException;
import com.rozplochowski.studio.classrooms.application.port.out.LoadClassroomPort;
import com.rozplochowski.studio.classrooms.application.port.out.SaveClassroomPort;
import com.rozplochowski.studio.classrooms.domain.Classroom;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ClassroomPersistenceAdapter implements SaveClassroomPort, LoadClassroomPort {
    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper mapper;

    @Override
    public Classroom save(Classroom classroom) {
        var entity = mapper.toEntity(classroom);
        try {
            var saved = classroomRepository.saveAndFlush(entity);
            return toDomain(saved);
        } catch (DataIntegrityViolationException e) {
            if (isUniqueConstraintViolation(e)) {
                throw new DuplicateClassroomNameException(classroom.getName());
            }

            throw e;
        }
    }

    @Override
    public Optional<Classroom> loadById(long id) {
        return classroomRepository.findById(id).map(this::toDomain);
    }

    private Classroom toDomain(ClassroomEntity entity) {
        return Classroom.reconstitute(entity.getId(), entity.getName(), entity.getCapacity());
    }

    private boolean isUniqueConstraintViolation(DataIntegrityViolationException e) {
        if (!(e.getCause() instanceof ConstraintViolationException cve)) return false;

        if (cve.getCause() instanceof SQLException sqlException)
            return sqlException.getErrorCode() == 1062;

        return false;
    }
}
