package com.javyhuerta.crud.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public interface AuthorProjection {
    Integer getAuthorId();

    String getFirstName();

    String getLastName();

    String getFullName();

    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate getBirthdate();
}

