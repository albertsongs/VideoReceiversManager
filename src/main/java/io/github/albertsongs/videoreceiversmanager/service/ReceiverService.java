package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidFormat;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidValue;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.repository.ReceiverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public final class ReceiverService {
    @Autowired
    private ReceiverRepo receiverRepo;

    public Receiver add(Receiver receiver, String receiverIpAddress) {
        final ReceiverEntity receiverEntity = receiver.toEntity();
        receiverEntity.setLastIpAddress(receiverIpAddress);
        return new Receiver(receiverRepo.save(receiverEntity));
    }

    public Receiver getById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            ReceiverEntity receiverEntity = receiverRepo.findById(uuid)
                    .orElseThrow(() -> new ObjectNotFound(Receiver.class.getSimpleName(), id));
            return new Receiver(receiverEntity);
        } catch (IllegalArgumentException e) {
            throw new ReceiverIdInvalidFormat(e.getMessage());
        }
    }

    public void delete(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            receiverRepo.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ReceiverIdInvalidFormat(e.getMessage());
        } catch (Exception e) {
            throw new ObjectNotFound(Receiver.class.getSimpleName(), id);
        }
    }

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
        try {
            UUID uuid = UUID.fromString(id);
            if (!receiverRepo.existsById(uuid)) {
                throw new ObjectNotFound(Receiver.class.getSimpleName(), id);
            }
            if (receiver.getId() == null) {
                final Optional<ReceiverEntity> oldReceiver = receiverRepo.findById(uuid);
                oldReceiver.ifPresent(receiverEntity -> receiver.setId(receiverEntity.getId()));
            } else if (!Objects.equals(receiver.getId(), uuid)) {
                throw new ReceiverIdInvalidValue(id);
            }
        } catch (IllegalArgumentException e) {
            throw new ReceiverIdInvalidFormat(e.getMessage());
        }

        return new Receiver(receiverRepo.save(receiver.toEntity()));
    }
}
