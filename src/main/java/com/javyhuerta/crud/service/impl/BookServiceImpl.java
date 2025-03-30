package com.javyhuerta.crud.service.impl;

import java.util.List;
import java.util.Optional;

import com.javyhuerta.crud.exception.ApiException;
import com.javyhuerta.crud.mapper.BookMapper;
import com.javyhuerta.crud.model.dto.BookItemDto;
import com.javyhuerta.crud.model.dto.BookResponseDto;
import com.javyhuerta.crud.model.dto.RegisterBookDto;
import com.javyhuerta.crud.model.dto.UpdateBookDto;
import com.javyhuerta.crud.model.projection.BookProjection;
import com.javyhuerta.crud.persistence.entity.Author;
import com.javyhuerta.crud.persistence.entity.Book;
import com.javyhuerta.crud.persistence.entity.BookAuthor;
import com.javyhuerta.crud.persistence.entity.BookAuthorId;
import com.javyhuerta.crud.persistence.repository.BookAuthorRepository;
import com.javyhuerta.crud.persistence.repository.BookRepository;
import com.javyhuerta.crud.persistence.repository.specification.BookSpecification;
import com.javyhuerta.crud.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Override
    @Transactional
    public BookResponseDto saveBook(RegisterBookDto registerBookDto) {
        Book bookEntity;

        Book bookSave = null;
        try {
            bookEntity = BookMapper.INSTANCE.registerBookDtoToBook(registerBookDto);
            bookSave = bookRepository.save(bookEntity);
            Integer resultBookId = bookSave.getBookId();
            registerBookDto.getAuthors().stream()
                    .forEach(
                            authorId -> {
                                Author author = Author.builder().authorId(authorId).build();

                                Book bookId = Book.builder().bookId(resultBookId).build();

                                BookAuthorId bookAuthorId =
                                        BookAuthorId.builder().bookId(bookId).authorId(author).build();

                                BookAuthor bookAuthorEntity =
                                        BookAuthor.builder().bookAuthorId(bookAuthorId).build();

                                bookAuthorRepository.save(bookAuthorEntity);
                            });
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ApiException("Error in Detail", HttpStatus.NOT_FOUND);
        }

        return findBookId(bookSave.getBookId());
    }

    @Override
    public Page<BookItemDto> findAllToPage(BookSpecification bookSpec, Pageable pageable) {

        Page<BookProjection> pages = bookRepository.findAllToPage(bookSpec, pageable);

        List<BookItemDto> books =
                BookMapper.INSTANCE.bookProjectionListToBookItemDtoList(pages.getContent());

        return new PageImpl<>(books, pages.getPageable(), pages.getTotalElements());
    }

    @Override
    public BookResponseDto findBookId(Integer bookId) {

        Optional<BookProjection> bookProjectionOp = bookAuthorRepository.findByBookId(bookId);

        if (bookProjectionOp.isEmpty()) {
            throw new ApiException("Error: bookId " + bookId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return BookMapper.INSTANCE.bookProjectionToBookResponseDto(bookProjectionOp.get());
    }

    @Override
    @Transactional
    public void deleteBook(Integer bookId) {

        try {
            bookAuthorRepository.deleteByBookId(bookId);
            Book bookEntity = Book.builder().bookId(bookId).build();
            bookRepository.delete(bookEntity);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ApiException("Not delete book", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public BookResponseDto updateBook(Integer bookId, UpdateBookDto updateBookDto) {

        Book bookEntity;

        try {

            bookEntity = BookMapper.INSTANCE.updateBookDtoToBook(updateBookDto);
            bookEntity.setBookId(bookId);

            // Delete authors
            bookAuthorRepository.deleteByBookId(bookId);

            // Update Book
            Book bookUpdate = bookRepository.save(bookEntity);

            // Save Authors
            List<BookAuthor> newAuthors =
                    updateBookDto.getAuthors().stream()
                            .map(
                                    authorId -> {
                                        BookAuthorId bookAuthorId =
                                                BookAuthorId.builder()
                                                        .bookId(bookUpdate)
                                                        .authorId(Author.builder().authorId(authorId).build())
                                                        .build();

                                        BookAuthor bookAuthor = new BookAuthor();
                                        bookAuthor.setBookAuthorId(bookAuthorId);
                                        return bookAuthor;
                                    })
                            .toList();
            bookAuthorRepository.saveAll(newAuthors);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ApiException("Error in Detail", HttpStatus.NOT_FOUND);
        }

        return findBookId(bookId);
    }

    @Override
    public List<BookResponseDto> findAllToList(BookSpecification bookSpec, Sort sort) {
        List<BookProjection> pages = bookRepository.findAllToList(bookSpec, sort);

        return BookMapper.INSTANCE.bookProjectionListToBookResponseDtoList(pages);
    }
}


