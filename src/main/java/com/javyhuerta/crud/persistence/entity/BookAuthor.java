package com.javyhuerta.crud.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_authors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookAuthor implements Serializable {

    @EmbeddedId private BookAuthorId bookAuthorId;
}
