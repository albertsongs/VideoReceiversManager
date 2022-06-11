package io.github.albertsongs.videoreceiversmanager.repository;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceiverRepo extends CrudRepository<ReceiverEntity, UUID> {
}
