package io.github.albertsongs.videoreceiversmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public final class Receiver {
    private UUID id;
    private String name;
    @JsonIgnore
    private String lastIpAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date updatedAt;

    public Receiver(ReceiverEntity entity) {
        id = entity.getId();
        name = entity.getName();
        lastIpAddress = entity.getLastIpAddress();
        updatedAt = entity.getUpdatedAt();
    }

    public ReceiverEntity toEntity() {
        ReceiverEntity receiverEntity = new ReceiverEntity();
        receiverEntity.setId(id);
        receiverEntity.setName(name);
        receiverEntity.setLastIpAddress(lastIpAddress);
        receiverEntity.setUpdatedAt(updatedAt);
        return receiverEntity;
    }
}
