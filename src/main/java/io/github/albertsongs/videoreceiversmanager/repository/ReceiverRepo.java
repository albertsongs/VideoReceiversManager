package io.github.albertsongs.videoreceiversmanager.repository;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceiverRepo extends JpaRepository<ReceiverEntity, UUID> {
}
