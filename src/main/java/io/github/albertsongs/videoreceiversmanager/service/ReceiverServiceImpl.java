package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidFormat;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidValue;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.repository.ReceiverRepo;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public final class ReceiverServiceImpl implements ReceiverService {
    private final ReceiverRepo receiverRepo;

    public Receiver add(Receiver receiver) {
        return new Receiver(receiverRepo.save(receiver.toEntity()));
    }

    public Receiver getById(String id) {
        UUID uuid = prepareReceiverId(id);
        ReceiverEntity receiverEntity = receiverRepo.findById(uuid)
                .orElseThrow(() -> new ObjectNotFound(Receiver.class.getSimpleName(), id));
        return new Receiver(receiverEntity);
    }

    @Override
    public Receiver getOnlineReceiverById(String id) {
        Receiver receiver = getById(id);
        if (isReceiverOnline(receiver.getId())) {
            return receiver;
        }
        throw new ObjectNotFound(Receiver.class.getSimpleName(), id);
    }

    public void deleteById(String id) {
        try {
            UUID uuid = prepareReceiverId(id);
            receiverRepo.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFound(Receiver.class.getSimpleName(), id);
        }
    }

    @Override
    public Iterable<Receiver> getAll(Predicate<Receiver> filterPredicate, Comparator<Receiver> sortComparator) {
        List<Receiver> receivers = new LinkedList<>();
        receiverRepo.findAll().forEach(receiverEntity -> {
            Receiver receiver = new Receiver(receiverEntity);
            if (filterPredicate.test(receiver)) {
                receivers.add(receiver);
            }
        });
        receivers.sort(sortComparator);
        return receivers;
    }

    @Deprecated
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

    private final Map<UUID, Long> receiverRespondsMap = new HashMap<>();

    @Override
    public void handleRespondReceiver(String receiverId) {
        UUID receiverUuid = prepareReceiverId(receiverId);
        receiverRespondsMap.put(receiverUuid, new Date().getTime());
    }

    public Boolean isReceiverOnline(UUID receiverId) {
        Long receiverLastRespondTime = receiverRespondsMap.get(receiverId);
        if (receiverLastRespondTime == null) {
            return false;
        }
        final long RECEIVER_RESPOND_INTERVAL = 15000; // 15 sec
        return new Date().getTime() - receiverLastRespondTime <= RECEIVER_RESPOND_INTERVAL + 5000;
    }
}
