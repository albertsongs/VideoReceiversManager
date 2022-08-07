package io.github.albertsongs.videoreceiversmanager.repository;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepo extends JpaRepository<VideoEntity, Long> {
}
