package com.rozplochowski.studio.classmanagement.adapter.out.persistence;

import com.rozplochowski.studio.classmanagement.application.port.out.LoadDanceClassPort;
import com.rozplochowski.studio.classmanagement.application.port.out.SaveDanceClassPort;
import com.rozplochowski.studio.classmanagement.application.port.out.UpdateDanceClassPort;
import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.shared.application.in.PagedRequest;
import com.rozplochowski.studio.shared.application.in.PagedResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DanceClassPersistenceAdapter implements SaveDanceClassPort, LoadDanceClassPort, UpdateDanceClassPort {
    private final PersistenceDanceClassMapper mapper;
    private final DanceClassRepository repository;

    @Override
    public DanceClass save(DanceClass danceClass) {
        var entity = mapper.toEntity(danceClass);
        var saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<DanceClass> getById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public DanceClass update(DanceClass danceClass) {
        Objects.requireNonNull(danceClass.getId());

        var entity = repository.findById(danceClass.getId())
                .orElseThrow(() -> new IllegalArgumentException("Dance class not found"));

        entity.setActivity(danceClass.getActivity());
        entity.setDescription(danceClass.getDescription());
        entity.setDifficulty(danceClass.getDifficulty());

        return toDomain(repository.saveAndFlush(entity));
    }

    @Override
    public PagedResponse<DanceClass> getAll(PagedRequest pagedRequest) {

        var response = repository.findAll(PageRequest.of(pagedRequest.pageNumber(), pagedRequest.pageSize()));

        var danceClasses = response.getContent()
                .stream()
                .map(this::toDomain)
                .toList();

        return new PagedResponse<>(danceClasses, response.getNumber(), response.getSize(), response.getTotalPages());
    }

    private DanceClass toDomain(DanceClassEntity entity) {
        return DanceClass.reconstitute(entity.getId(), entity.getActivity(), entity.getDescription(), entity.getDifficulty());
    }
}
