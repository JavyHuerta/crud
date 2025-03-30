package com.javyhuerta.crud.mapper;

import java.time.LocalDate;
import java.util.List;

import com.javyhuerta.crud.model.dto.AuthorItemDto;
import com.javyhuerta.crud.model.dto.AuthorResponseDto;
import com.javyhuerta.crud.model.dto.RegisterAuthorDto;
import com.javyhuerta.crud.model.dto.UpdateAuthorDto;
import com.javyhuerta.crud.model.projection.AuthorProjection;
import com.javyhuerta.crud.persistence.entity.Author;
import com.javyhuerta.crud.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    public static AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(source = "birthdate", target = "birthdate", qualifiedByName = "stringToLocalDate")
    Author registerAuthorDtoToAuthor(RegisterAuthorDto registerAuthorDto);

    @Mapping(source = "birthdate", target = "birthdate", qualifiedByName = "stringToLocalDate")
    Author updateAuthorDtoToAuthor(UpdateAuthorDto updateAuthorDto);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String dateAsString) {
        return DateTimeUtil.stringToLocalDate(dateAsString, DateTimeUtil.DAY_MONTH_YEAR);
    }

    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd")
    AuthorItemDto authorToAuthorItemDto(Author author);

    List<AuthorItemDto> authorToAuthorItemDto(List<Author> authors);

    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd")
    AuthorResponseDto authorProjectionToAuthorResponseDto(AuthorProjection authorProjection);

    @Mapping(
            target = "fullName",
            expression = "java(author.getFirstName() + \" \" + author.getLastName())")
    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd")
    AuthorResponseDto authorToAuthorResponseDto(Author author);

    List<AuthorResponseDto> authorToAuthorResponseDto(List<Author> authors);

    default String localDateToString(LocalDate date, String pattern) {
        return DateTimeUtil.localDateToString(date, pattern);
    }
}


