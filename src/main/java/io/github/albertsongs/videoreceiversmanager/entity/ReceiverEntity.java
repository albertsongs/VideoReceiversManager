package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity(name = "receiver")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ReceiverEntity {
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    UUID id;
    String name;
    String lastIpAddress;
    Date updatedAt;
}
