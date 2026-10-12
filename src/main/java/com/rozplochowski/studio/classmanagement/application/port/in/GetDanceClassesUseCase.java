package com.rozplochowski.studio.classmanagement.application.port.in;

import com.rozplochowski.studio.classmanagement.domain.DanceClass;
import com.rozplochowski.studio.shared.application.in.PagedRequest;
import com.rozplochowski.studio.shared.application.in.PagedResponse;

public interface GetDanceClassesUseCase {

    PagedResponse<DanceClass> getAll(PagedRequest pagedRequest);

}
