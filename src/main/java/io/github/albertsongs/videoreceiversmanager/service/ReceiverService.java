package io.github.albertsongs.videoreceiversmanager.service;

import com.sun.istack.NotNull;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

    Boolean isReceiverOnline(Receiver receiver);

    Iterable<Receiver> getAll(Predicate<Receiver> filterPredicate, Comparator<Receiver> sortComparator);

    List<Receiver> getAll(@NotNull Receiver exemplaryReceiver, Sort.Order sortOrder);
}
