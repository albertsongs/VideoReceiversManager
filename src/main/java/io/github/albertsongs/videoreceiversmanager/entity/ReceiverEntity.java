package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity (name = "receiver")
@Data
public final class ReceiverEntity {
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id = UUID.randomUUID();
    private String name;
    private String lastIpAddress;
}
