package com.smartlist.core_api.mapper;

import com.smartlist.core_api.dto.superlist.SuperListDetailResponse;
import com.smartlist.core_api.dto.superlist.SuperlistResponse;
import com.smartlist.core_api.entity.Superlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// INSIGHT: componentModel = to Generate this mapper as a Spring Bean (auto-inject it).
// INSIGHT: uses = tells, If you need to map nested objects, use this other mapper.
@Mapper(componentModel = "spring")
public interface SuperlistMapper {

    SuperlistResponse toResponse(Superlist entity);

    Superlist toEntity(SuperlistResponse response);

    List<SuperlistResponse> toResponseList(List<Superlist> entities);

    @Mapping(source = "listItems", target = "items")
    SuperListDetailResponse toDetailResponse(Superlist entity);
}
