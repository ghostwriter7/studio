package com.rozplochowski.studio.classmanagement.adapter.out.persistence;

import com.rozplochowski.studio.classmanagement.application.port.out.SaveDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DanceClassPersistenceAdapter implements SaveDanceClassPort {
    private final DanceClassMapper mapper;
    private final DanceClassRepository repository;

    @Override
    public DanceClass save(DanceClass danceClass) {
        var entity = mapper.toEntity(danceClass);
        var saved = repository.save(entity);
        return DanceClass.reconstitute(saved.getId(), saved.getActivity(), saved.getDescription(), saved.getDifficulty());
    }
}
