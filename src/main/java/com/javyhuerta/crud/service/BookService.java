package com.javyhuerta.crud.service;

import java.util.List;

import com.javyhuerta.crud.model.dto.BookItemDto;
import com.javyhuerta.crud.model.dto.BookResponseDto;
import com.javyhuerta.crud.model.dto.RegisterBookDto;
import com.javyhuerta.crud.model.dto.UpdateBookDto;
import com.javyhuerta.crud.persistence.repository.specification.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BookService {

    BookResponseDto saveBook(RegisterBookDto registerBookDto);

    Page<BookItemDto> findAllToPage(BookSpecification bookSpec, Pageable pageable);

    BookResponseDto findBookId(Integer bookId);

    void deleteBook(Integer bookId);

    BookResponseDto updateBook(Integer bookId, UpdateBookDto updateBookDto);

    List<BookResponseDto> findAllToList(BookSpecification bookSpec, Sort sort);
}

