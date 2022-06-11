package io.github.albertsongs.videoreceiversmanager.repository;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReceiverRepo extends CrudRepository<ReceiverEntity, UUID> {
}
