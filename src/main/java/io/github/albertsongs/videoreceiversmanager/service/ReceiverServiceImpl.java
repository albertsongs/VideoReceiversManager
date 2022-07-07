package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidFormat;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidValue;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.repository.ReceiverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public final class ReceiverServiceImpl implements ReceiverService {
    @Autowired
    private ReceiverRepo receiverRepo;

    public Receiver add(Receiver receiver) {
        return new Receiver(receiverRepo.save(receiver.toEntity()));
    }

    public Receiver getById(String id) {
        UUID uuid = prepareReceiverId(id);
        ReceiverEntity receiverEntity = receiverRepo.findById(uuid)
                .orElseThrow(() -> new ObjectNotFound(Receiver.class.getSimpleName(), id));
        return new Receiver(receiverEntity);
    }

    public void deleteById(String id) {
        try {
            UUID uuid = prepareReceiverId(id);
            receiverRepo.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFound(Receiver.class.getSimpleName(), id);
        }
    }

    //TODO: replace to getAll(filter, sorter)
    public Iterable<Receiver> getAllWithLastIp(String remoteClientIp) {
        List<Receiver> receivers = new LinkedList<>();
        receiverRepo.findAll().forEach(receiverEntity -> {
            String receiverIp = receiverEntity.getLastIpAddress();
            if (Objects.equals(receiverIp, remoteClientIp)) {
                receivers.add(new Receiver(receiverEntity));
            }
        });
        return receivers;
    }

    public Receiver update(String id, Receiver receiver) {
        UUID uuid = prepareReceiverId(id);
        if (!receiverRepo.existsById(uuid)) {
            throw new ObjectNotFound(Receiver.class.getSimpleName(), id);
        }
        if (receiver.getId() == null) {
            receiver.setId(uuid);
        } else if (!Objects.equals(receiver.getId(), uuid)) {
            throw new ReceiverIdInvalidValue(id);
        }

        return new Receiver(receiverRepo.save(receiver.toEntity()));
    }

    public UUID prepareReceiverId(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ReceiverIdInvalidFormat(e.getMessage());
        }
    }
}
