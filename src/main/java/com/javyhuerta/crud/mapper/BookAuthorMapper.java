package com.javyhuerta.crud.mapper;

import com.javyhuerta.crud.model.dto.BookResponseDto;
import com.javyhuerta.crud.model.projection.BookProjection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookAuthorMapper {

    static BookAuthorMapper INSTANCE = Mappers.getMapper(BookAuthorMapper.class);

    BookResponseDto bookProjectionTobookResponseDto(BookProjection bookProjection);
}

