package com.javyhuerta.crud.service;

import java.util.List;

import com.javyhuerta.crud.model.dto.AuthorItemDto;
import com.javyhuerta.crud.model.dto.AuthorResponseDto;
import com.javyhuerta.crud.model.dto.RegisterAuthorDto;
import com.javyhuerta.crud.model.dto.UpdateAuthorDto;
import com.javyhuerta.crud.persistence.repository.specification.AuthorSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    AuthorResponseDto saveAuthor(RegisterAuthorDto registerAuthorDto);

    Page<AuthorItemDto> findAllToPage(AuthorSpecification authorSpecification, Pageable pageable);

    AuthorResponseDto updateAuthor(Integer authorId, UpdateAuthorDto updateAuthorDto);

    void deleteAuthor(Integer authorId);

    AuthorResponseDto findAuthorId(Integer authorId);

    List<AuthorResponseDto> findAll(AuthorSpecification authorSpecification);
}

