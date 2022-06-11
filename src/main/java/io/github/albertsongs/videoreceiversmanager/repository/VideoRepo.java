package io.github.albertsongs.videoreceiversmanager.repository;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepo extends CrudRepository<VideoEntity, Long> {
}
