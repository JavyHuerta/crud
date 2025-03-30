package com.javyhuerta.crud.web.api;

import com.javyhuerta.crud.model.dto.BookItemDto;
import com.javyhuerta.crud.model.dto.BookResponseDto;
import com.javyhuerta.crud.model.dto.RegisterBookDto;
import com.javyhuerta.crud.model.dto.UpdateBookDto;
import com.javyhuerta.crud.persistence.repository.specification.BookSpecification;
import com.javyhuerta.crud.service.BookService;
import com.javyhuerta.crud.util.PageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BookRestController implements BooksApi {

    private final BookService bookService;

    @Override
    public ResponseEntity<Page<BookItemDto>> getAllBooksToPage(
            @Valid String q,
            @Valid LocalDate publicationDate,
            @Valid Boolean onlineAvailability,
            @Min(0) @Valid Integer page,
            @Min(1) @Valid Integer size,
            @Valid String sort) {

        Sort sortRequest = PageUtil.getSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortRequest);

        BookSpecification bookSpec =
                BookSpecification.builder()
                        .q(q)
                        .publicationDate(publicationDate)
                        .onlineAvailability(onlineAvailability)
                        .build();

        Page<BookItemDto> books = bookService.findAllToPage(bookSpec, pageable);

        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<BookResponseDto> getBookById(Integer bookId) {
        BookResponseDto book = bookService.findBookId(bookId);

        return ResponseEntity.ok(book);
    }

    @Override
    public ResponseEntity<BookResponseDto> registerBook(@Valid RegisterBookDto registerBookDto) {
        BookResponseDto bookResponseDto = bookService.saveBook(registerBookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDto);
    }

    @Override
    public ResponseEntity<BookResponseDto> updateBook(
            Integer bookId, @Valid UpdateBookDto updateBookDto) {
        BookResponseDto bookResponseDto = bookService.updateBook(bookId, updateBookDto);

        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BookResponseDto>> getBooksToList(
            @Valid String q,
            @Valid LocalDate publicationDate,
            @Valid Boolean onlineAvailability,
            @Valid String sort) {
        BookSpecification bookSpec =
                BookSpecification.builder()
                        .q(q)
                        .publicationDate(publicationDate)
                        .onlineAvailability(onlineAvailability)
                        .build();

        Sort sortRequest = PageUtil.getSort(sort);
        List<BookResponseDto> books = bookService.findAllToList(bookSpec, sortRequest);

        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Integer bookId) {
        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }
}


