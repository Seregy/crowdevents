package com.crowdevents.core.web;

import java.util.List;

public interface CursorPage<T> {
    List<T> getContent();
    int getSize();
    boolean hasNext();
    T getLast();
    T getFirst();
}
