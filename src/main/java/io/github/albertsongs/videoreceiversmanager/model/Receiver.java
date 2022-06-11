package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public final class Receiver {
    private UUID id;
    private String name;

    public Receiver(ReceiverEntity entity){
        id = entity.getId();
        name = entity.getName();
    }
    public ReceiverEntity toEntity(){
        ReceiverEntity receiverEntity = new ReceiverEntity();
        receiverEntity.setId(id);
        receiverEntity.setName(name);
        return receiverEntity;
    }
}
