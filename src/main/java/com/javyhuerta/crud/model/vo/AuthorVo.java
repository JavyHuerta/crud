package com.javyhuerta.crud.model.vo;

import java.time.LocalDate;

import com.javyhuerta.crud.model.projection.AuthorProjection;
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
public class AuthorVo implements AuthorProjection {

    private Integer authorId;
    private String firtName;
    private String lastName;
    private String fullName;

    private LocalDate birthdate;

    @Override
    public Integer getAuthorId() {
        return authorId;
    }

    @Override
    public String getFirstName() {
        return firtName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public LocalDate getBirthdate() {
        return birthdate;
    }
}

