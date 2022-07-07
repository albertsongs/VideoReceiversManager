package io.github.albertsongs.videoreceiversmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectListContainer<Object> {
    private Iterable<Object> list;
}
