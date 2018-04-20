package com.crowdevents.core.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CursorPageImpl<T> implements CursorPage<T> {
    private List<T> content;
    private boolean hasNext;

    public static <T> CursorPageImpl<T> of(Stream<T> stream, int limit) {
        List<T> content;
        boolean hasNext;
        try (Stream<T> s = stream) {
            content = s.limit(limit).collect(Collectors.toList());
            hasNext = s.skip(limit).findAny().isPresent();
        }
        return new CursorPageImpl<>(content, hasNext);
    }

    public CursorPageImpl(List<T> content, boolean hasNext) {
        this.content = content;
        this.hasNext = hasNext;
    }

    @Override
    public List<T> getContent() {
        return null;
    }

    @Override
    public int getSize() {
        return content.size();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T getLast() {
        return content.get(content.size() - 1);
    }

    @Override
    public T getFirst() {
        return content.get(0);
    }
}
