package com.rozplochowski.studio.shared.application.in;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        int totalPages
) {
}
