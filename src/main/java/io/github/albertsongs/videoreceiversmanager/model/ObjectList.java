package io.github.albertsongs.videoreceiversmanager.model;

import lombok.Data;

@Data
public class ObjectList<Object> {
    private Iterable<Object> list;
}
