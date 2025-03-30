package com.javyhuerta.crud.model.vo;

import java.time.LocalDate;

import com.javyhuerta.crud.model.projection.BookProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@ToString
public class BookVo implements BookProjection {
    private Integer bookId;
    private String title;
    private LocalDate publicationDate;
    private String concatAuthors;
    private Boolean onlineAvailability;

    @Override
    public Integer getBookId() {
        return bookId;
    }

    @Override
    public Boolean getOnlineAvailability() {
        return onlineAvailability;
    }

    @Override
    public String getConcatAuthors() {
        return concatAuthors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
}


