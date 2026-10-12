package com.rozplochowski.studio.shared.application.in;

public record PagedRequest(
        int pageSize,
        int pageNumber
) {
}
