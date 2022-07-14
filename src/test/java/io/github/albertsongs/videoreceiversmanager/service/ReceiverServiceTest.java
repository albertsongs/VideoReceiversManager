package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.TestConfig;
import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidFormat;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidValue;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.repository.ReceiverRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiverServiceTest {
    @Autowired
    private ReceiverService receiverService;

    @MockBean
    private ReceiverRepo receiverRepo;

    private final String NOT_A_UUID = "NOT A UUID";

    final String TEST_RECEIVER_IP = "127.0.0.1";

    private long testReceiverEntityCounter = 1;
    private final long initTimestamp = (new Date()).getTime();

    @Test
    void prepareReceiverId() {
        UUID expectedUuid = UUID.randomUUID();
        String receiverId = expectedUuid.toString();
        UUID receiverUuid = receiverService.prepareReceiverId(receiverId);
        assertEquals(expectedUuid, receiverUuid);
        assertThrows(ReceiverIdInvalidFormat.class, () -> receiverService.prepareReceiverId(NOT_A_UUID));
    }

    @Test
    void add() {
        ReceiverEntity expectedEntity = buildTestReceiverEntity();
        Receiver expectedReceiver = new Receiver(expectedEntity);
        //Uniq ID generates by Repo
        Receiver newReceiver = new Receiver(expectedEntity);
        Mockito.doReturn(expectedEntity)
                .when(receiverRepo)
                .save(newReceiver.toEntity());

        Receiver savedReceiver = receiverService.add(newReceiver);
        Mockito.verify(receiverRepo, Mockito.times(1)).save(newReceiver.toEntity());
        assertNotNull(savedReceiver.getId());
        assertEquals(expectedReceiver, savedReceiver);
    }

    @Test
    void getById() {
        ReceiverEntity expectedEntity = buildTestReceiverEntity();
        UUID receiverId = expectedEntity.getId();
        Mockito.doReturn(Optional.of(expectedEntity))
                .when(receiverRepo)
                .findById(receiverId);

        Receiver foundReceiver = receiverService.getById(receiverId.toString());
        Mockito.verify(receiverRepo, Mockito.times(1)).findById(receiverId);
        assertNotNull(foundReceiver);
        assertEquals(expectedEntity, foundReceiver.toEntity());

        assertThrows(ReceiverIdInvalidFormat.class, () -> receiverService.getById(NOT_A_UUID));

        UUID randomReceiverId = UUID.randomUUID();
        assertThrows(ObjectNotFound.class, () -> receiverService.getById(randomReceiverId.toString()));
        Mockito.verify(receiverRepo, Mockito.times(1)).findById(randomReceiverId);
    }

    @Test
    void deleteById() {
        UUID receiverId = UUID.randomUUID();
        UUID removedReceiverId = UUID.randomUUID();

        Mockito.doThrow(EmptyResultDataAccessException.class)
                .when(receiverRepo)
                .deleteById(removedReceiverId);

        assertDoesNotThrow(() -> receiverService.deleteById(receiverId.toString()));
        Mockito.verify(receiverRepo, Mockito.times(1)).deleteById(receiverId);
        assertThrows(ReceiverIdInvalidFormat.class, () -> receiverService.deleteById(NOT_A_UUID));
        assertThrows(ObjectNotFound.class, () -> receiverService.deleteById(removedReceiverId.toString()));
        Mockito.verify(receiverRepo, Mockito.times(1)).deleteById(removedReceiverId);
    }

    @Test
    void getAll() {
        List<ReceiverEntity> receiverEntityList = buildTestReceiverEntityList();
        final Comparator<ReceiverEntity> expectedSortComparator = (r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt());
        receiverEntityList.sort(expectedSortComparator);
        Mockito.doReturn(receiverEntityList)
                .when(receiverRepo)
                .findAll();
        List<Receiver> expectedReceivers = new LinkedList<>();
        receiverEntityList.forEach((receiverEntity -> expectedReceivers.add(new Receiver(receiverEntity))));

        final Predicate<Receiver> isLocalReceiver = receiver -> receiver.getLastIpAddress().equals(TEST_RECEIVER_IP);
        final Comparator<Receiver> sortComparator = (r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt());
        final Iterable<Receiver> receiversFromService = receiverService.getAll(isLocalReceiver, sortComparator);
        Mockito.verify(receiverRepo, Mockito.times(1)).findAll();
        assertIterableEquals(expectedReceivers, receiversFromService);

        final Predicate<Receiver> isNotLocalReceiver = receiver -> receiver.getLastIpAddress().equals("1.1.1.1");
        final Iterable<Receiver> secondReceiversFromService = receiverService.getAll(isNotLocalReceiver, sortComparator);
        Mockito.verify(receiverRepo, Mockito.times(2)).findAll();
        assertIterableEquals(new LinkedList<Receiver>(), secondReceiversFromService);
    }

    @Test
    void updateSuccess() {
        ReceiverEntity expectedEntity = buildTestReceiverEntity();
        String updatedReceiverId = expectedEntity.getId().toString();
        Receiver updatedReceiver = new Receiver(new ReceiverEntity());
        String updatedReceiverName = "updated receiver name";
        updatedReceiver.setName(updatedReceiverName);
        expectedEntity.setName(updatedReceiverName);
        Receiver expectedReceiver = new Receiver(expectedEntity);
        Mockito.doReturn(true)
                .when(receiverRepo)
                .existsById(expectedEntity.getId());
        ReceiverEntity updatedEntityWithId = updatedReceiver.toEntity();
        updatedEntityWithId.setId(expectedEntity.getId());
        Mockito.doReturn(expectedEntity)
                .when(receiverRepo)
                .save(updatedEntityWithId);
        //Update receiver with null ID
        Receiver savedReceiver = receiverService.update(updatedReceiverId, updatedReceiver);
        Mockito.verify(receiverRepo, Mockito.times(1)).existsById(expectedEntity.getId());
        Mockito.verify(receiverRepo, Mockito.times(1)).save(updatedReceiver.toEntity());
        assertNotNull(savedReceiver.getId());
        assertEquals(expectedReceiver, savedReceiver);
        //Update receiver with equals ID
        updatedReceiver.setId(expectedReceiver.getId());
        Receiver secondSavedReceiver = receiverService.update(updatedReceiverId, updatedReceiver);
        Mockito.verify(receiverRepo, Mockito.times(2)).existsById(expectedEntity.getId());
        Mockito.verify(receiverRepo, Mockito.times(2)).save(updatedReceiver.toEntity());
        assertNotNull(savedReceiver.getId());
        assertEquals(expectedReceiver, secondSavedReceiver);
        //Update receiver with don't editable fields
        Date updatedAt = new Date();
        updatedAt.setTime(123);
        updatedReceiver.setUpdatedAt(updatedAt);
        String updatedLastIpAddress = "1.1.1.1";
        updatedReceiver.setLastIpAddress(updatedLastIpAddress);
        Mockito.doReturn(expectedEntity)
                .when(receiverRepo)
                .save(updatedReceiver.toEntity());

        Receiver thirdSavedReceiver = receiverService.update(updatedReceiverId, updatedReceiver);
        Mockito.verify(receiverRepo, Mockito.times(3)).existsById(expectedEntity.getId());
        Mockito.verify(receiverRepo, Mockito.times(1)).save(updatedReceiver.toEntity());
        assertNotNull(savedReceiver.getId());
        assertNotEquals(thirdSavedReceiver.getLastIpAddress(), updatedLastIpAddress);
        assertNotEquals(thirdSavedReceiver.getUpdatedAt(), updatedAt);
        assertEquals(expectedReceiver, thirdSavedReceiver);
    }

    @Test
    void updateWrong() {
        ReceiverEntity expectedEntity = buildTestReceiverEntity();
        String updatedReceiverId = expectedEntity.getId().toString();
        Receiver updatedReceiver = new Receiver(new ReceiverEntity());
        updatedReceiver.setName(expectedEntity.getName());
        Receiver expectedReceiver = new Receiver(expectedEntity);
        Mockito.doReturn(true)
                .when(receiverRepo)
                .existsById(expectedEntity.getId());
        Mockito.doReturn(expectedEntity)
                .when(receiverRepo)
                .save(updatedReceiver.toEntity());
        //Update wrong receiver (ID in query param != ID in object)
        UUID nonExistingReceiverId = UUID.randomUUID();
        updatedReceiver.setId(nonExistingReceiverId);
        assertThrows(ReceiverIdInvalidValue.class,
                () -> receiverService.update(updatedReceiverId, updatedReceiver));
        Mockito.verify(receiverRepo, Mockito.times(1)).existsById(expectedEntity.getId());
        Mockito.verify(receiverRepo, Mockito.times(0)).save(updatedReceiver.toEntity());
        //Update non-existing receiver
        Mockito.doReturn(false)
                .when(receiverRepo)
                .existsById(nonExistingReceiverId);
        assertThrows(ObjectNotFound.class,
                () -> receiverService.update(nonExistingReceiverId.toString(), updatedReceiver));
        Mockito.verify(receiverRepo, Mockito.times(1)).existsById(nonExistingReceiverId);
        Mockito.verify(receiverRepo, Mockito.times(0)).save(updatedReceiver.toEntity());
        //Check error ReceiverIdInvalidFormat
        assertThrows(ReceiverIdInvalidFormat.class, () -> receiverService.update(NOT_A_UUID, expectedReceiver));
        Mockito.verify(receiverRepo, Mockito.times(0)).save(updatedReceiver.toEntity());
    }

    ReceiverEntity buildTestReceiverEntity() {
        ReceiverEntity receiverEntity = new ReceiverEntity();
        receiverEntity.setId(UUID.randomUUID());
        receiverEntity.setName("test receiver " + testReceiverEntityCounter++);
        receiverEntity.setLastIpAddress(TEST_RECEIVER_IP);
        Date updatedAt = new Date();
        updatedAt.setTime(initTimestamp + testReceiverEntityCounter * 1000);
        receiverEntity.setUpdatedAt(updatedAt);
        return receiverEntity;
    }

    List<ReceiverEntity> buildTestReceiverEntityList() {
        List<ReceiverEntity> entityList = new LinkedList<>();
        for (int i = 0; i < TestConfig.TEST_ENTITY_COUNT; i++) {
            entityList.add(buildTestReceiverEntity());
        }

        return entityList;
    }
}