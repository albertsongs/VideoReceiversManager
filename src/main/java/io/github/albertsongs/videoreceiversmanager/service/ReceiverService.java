package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ReceiverService {
    Receiver add(Receiver receiver);

    Receiver getById(String id);

    void deleteById(String id);

    Iterable<Receiver> getAllWithLastIp(String remoteClientIp);

    Receiver update(String id, Receiver receiver);

    UUID prepareReceiverId(String id);
}
