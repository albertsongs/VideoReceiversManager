package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.ReceiverEntity;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiverTest {
    @Test
    void testToEntity(){
        Receiver receiver = new Receiver(
                UUID.randomUUID(),
                "Test receiver",
                "192.168.1.1",
                new Date(123)
        );
        ReceiverEntity receiverEntity = receiver.toEntity();
        assertEquals(receiver.getId(), receiverEntity.getId());
        assertEquals(receiver.getName(), receiverEntity.getName());
        assertEquals(receiver.getLastIpAddress(), receiverEntity.getLastIpAddress());
        assertEquals(receiver.getUpdatedAt(), receiverEntity.getUpdatedAt());
    }
    @Test
    void testConstructor(){
        ReceiverEntity receiverEntity = new ReceiverEntity();
        receiverEntity.setId(UUID.randomUUID());
        receiverEntity.setName("Test receiver");
        receiverEntity.setLastIpAddress("192.168.1.1");
        receiverEntity.setUpdatedAt(new Date(123));
        Receiver receiver = new Receiver(receiverEntity);
        assertEquals(receiverEntity.getId(),receiver.getId());
        assertEquals(receiverEntity.getName(), receiver.getName());
        assertEquals(receiverEntity.getLastIpAddress(), receiver.getLastIpAddress());
        assertEquals(receiverEntity.getUpdatedAt(), receiver.getUpdatedAt());
    }
}
