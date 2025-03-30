package com.javyhuerta.crud.web.api;

import com.javyhuerta.crud.model.dto.*;
import com.javyhuerta.crud.persistence.repository.specification.AuthorSpecification;
import com.javyhuerta.crud.service.AuthorService;
import com.javyhuerta.crud.util.PageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthorRestController implements AuthorsApi {
    private final AuthorService authorService;

    @Override
    public ResponseEntity<Page<AuthorItemDto>> getAllAuthorsToPage(
            @Valid String q,
            @Valid LocalDate birthdate,
            @Min(0) @Valid Integer page,
            @Min(1) @Valid Integer size,
            @Valid String sort) {

        Sort sortFormat = PageUtil.getSort(sort);

        Pageable pageable = PageRequest.of(page, size, sortFormat);

        AuthorSpecification authorSpecification =
                AuthorSpecification.builder().q(q).birthdate(birthdate).build();

        Page<AuthorItemDto> authors = this.authorService.findAllToPage(authorSpecification, pageable);

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthorResponseDto> getAuthorById(Integer authorId) {
        AuthorResponseDto author = authorService.findAuthorId(authorId);

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AuthorResponseDto>> getAuthorToList(
            @Valid String q, @Valid LocalDate birthdate) {
        AuthorSpecification authorSpecification =
                AuthorSpecification.builder().q(q).birthdate(birthdate).build();

        List<AuthorResponseDto> authors = authorService.findAll(authorSpecification);

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthorResponseDto> registerAuthor(
            @Valid RegisterAuthorDto registerAuthorDto) {
        AuthorResponseDto authorResponseDto = authorService.saveAuthor(registerAuthorDto);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdateAuthorResponseDto>  updateAuthor(
            Integer authorId, @Valid UpdateAuthorDto updateAuthorDto) {
        AuthorResponseDto authorResponseDto = authorService.updateAuthor(authorId, updateAuthorDto);

        UpdateAuthorResponseDto msg = new UpdateAuthorResponseDto();
        msg.setMessage("Author record modified");
        msg.setContent(authorResponseDto);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(Integer authorId) {
        authorService.deleteAuthor(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


