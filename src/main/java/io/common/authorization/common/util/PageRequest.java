package io.common.authorization.common.util;


import org.springframework.data.domain.Sort;

public final class PageRequest {

    private final int page;
    private final int size;
    private final Sort.Direction direction;

    public PageRequest(int page, int size, Sort.Direction direction) {
        this.page = page <= 0 ? 1 : page;

        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 100;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;

        this.direction = direction;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createDt");
    }

    public org.springframework.data.domain.PageRequest of(String column) {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, column);
    }
}
