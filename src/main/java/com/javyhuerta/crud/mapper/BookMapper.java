package com.javyhuerta.crud.mapper;

import java.time.LocalDate;
import java.util.List;

import com.javyhuerta.crud.model.dto.BookItemDto;
import com.javyhuerta.crud.model.dto.BookResponseDto;
import com.javyhuerta.crud.model.dto.RegisterBookDto;
import com.javyhuerta.crud.model.dto.UpdateBookDto;
import com.javyhuerta.crud.model.projection.BookProjection;
import com.javyhuerta.crud.persistence.entity.Book;
import com.javyhuerta.crud.util.DateTimeUtil;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    public static BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(
            source = "publicationDate",
            target = "publicationDate",
            qualifiedByName = "stringToLocalDate")
    Book registerBookDtoToBook(RegisterBookDto registerBookDto);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String dateAsString) {
        return DateTimeUtil.stringToLocalDate(dateAsString, DateTimeUtil.DAY_MONTH_YEAR);
    }

    @Mapping(source = "publicationDate", target = "publicationDate", dateFormat = "yyyy/MM/dd")
    BookItemDto bookProjectionToBookItemDto(BookProjection bookProjection);

    List<BookItemDto> bookProjectionListToBookItemDtoList(List<BookProjection> content);

    BookResponseDto bookProjectionToBookResponseDto(BookProjection bookProjection);

    @Mapping(
            source = "publicationDate",
            target = "publicationDate",
            qualifiedByName = "stringToLocalDate")
    Book updateBookDtoToBook(UpdateBookDto updateBookDto);

    @Named("mapToBookResponseDto")
    @Mapping(source = "publicationDate", target = "publicationDate", dateFormat = "yyyy/MM/dd")
    BookResponseDto bookResponseDtoToBookProjection(BookProjection book);

    @IterableMapping(qualifiedByName = "mapToBookResponseDto")
    List<BookResponseDto> bookProjectionListToBookResponseDtoList(List<BookProjection> books);
}

