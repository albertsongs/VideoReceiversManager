package io.github.albertsongs.videoreceiversmanager.model;

import lombok.Data;

@Data
public class ObjectListContainer<Object> {
    private Iterable<Object> list;
}
