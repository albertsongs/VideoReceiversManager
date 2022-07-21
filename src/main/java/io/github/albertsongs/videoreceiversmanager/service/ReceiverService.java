package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public interface ReceiverService {
    Receiver add(Receiver receiver);

    Receiver getById(String id);

    Receiver getOnlineReceiverById(String id);

    void deleteById(String id);

    @Deprecated
    Iterable<Receiver> getAllWithLastIp(String remoteClientIp);

    Receiver update(String id, Receiver receiver);

    UUID prepareReceiverId(String id);

    void handleRespondReceiver(String receiverId);

    Boolean isReceiverOnline(UUID receiverId);

    Iterable<Receiver> getAll(Predicate<Receiver> filterPredicate, Comparator<Receiver> sortComparator);
}
