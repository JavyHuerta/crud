package com.javyhuerta.crud.service.impl;

import java.util.List;

import com.javyhuerta.crud.exception.ApiException;
import com.javyhuerta.crud.mapper.AuthorMapper;
import com.javyhuerta.crud.model.dto.AuthorItemDto;
import com.javyhuerta.crud.model.dto.AuthorResponseDto;
import com.javyhuerta.crud.model.dto.RegisterAuthorDto;
import com.javyhuerta.crud.model.dto.UpdateAuthorDto;
import com.javyhuerta.crud.model.projection.AuthorProjection;
import com.javyhuerta.crud.persistence.entity.Author;
import com.javyhuerta.crud.persistence.repository.AuthorRepository;
import com.javyhuerta.crud.persistence.repository.BookAuthorRepository;
import com.javyhuerta.crud.persistence.repository.specification.AuthorSpecification;
import com.javyhuerta.crud.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Override
    @Transactional
    public AuthorResponseDto saveAuthor(RegisterAuthorDto registerAuthorDto) {

        Author authorEntity = null;

        Author authorResult = null;
        try {
            authorEntity = AuthorMapper.INSTANCE.registerAuthorDtoToAuthor(registerAuthorDto);
            authorResult = authorRepository.save(authorEntity);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ApiException("Error al insetar datos", HttpStatus.BAD_REQUEST);
        }

        return findAuthorId(authorResult.getAuthorId());
    }

    @Override
    public Page<AuthorItemDto> findAllToPage(
            AuthorSpecification authorSpecification, Pageable pageable) {

        Page<Author> result = null;
        try {
            result = authorRepository.findAll(authorSpecification, pageable);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Empty", HttpStatus.NOT_FOUND);
        }

        List<AuthorItemDto> authorItemDtos =
                AuthorMapper.INSTANCE.authorToAuthorItemDto(result.getContent());

        return new PageImpl<>(authorItemDtos, result.getPageable(), result.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteAuthor(Integer authorId) {

        try {

            Boolean exist = bookAuthorRepository.existByAuthorId(authorId);

            if (exist != null && exist) {
                bookAuthorRepository.deleteByAuthorId(authorId);
            }

            authorRepository.delete(authorId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Empty", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public AuthorResponseDto findAuthorId(Integer authorId) throws ApiException {

        AuthorProjection authorProjection = null;

        try {
            authorProjection = authorRepository.findByUserId(authorId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Empty", HttpStatus.NOT_FOUND);
        }

        if (authorProjection == null) {
            throw new ApiException("Not exist author " + authorId, HttpStatus.NOT_FOUND);
        }

        return AuthorMapper.INSTANCE.authorProjectionToAuthorResponseDto(authorProjection);
    }

    @Override
    @Transactional
    public AuthorResponseDto updateAuthor(Integer authorId, UpdateAuthorDto updateAuthorDto) {

        Author authorEntity = null;
        Author authorResult = null;
        try {
            authorEntity = AuthorMapper.INSTANCE.
                          updateAuthorDtoToAuthor(updateAuthorDto);
            authorEntity.setAuthorId(authorId);
            authorResult =
                    authorRepository.save(authorEntity);
        } catch (IllegalArgumentException e)
        {
            log.error(e.getMessage());
            throw new ApiException("Error in Update " + authorId, HttpStatus.NOT_FOUND);
        }

        AuthorProjection authorProjection = authorRepository.findByUserId(authorResult.getAuthorId());
        return AuthorMapper.INSTANCE.authorProjectionToAuthorResponseDto(authorProjection);
    }

    @Override
    public List<AuthorResponseDto> findAll(AuthorSpecification authorSpecification) {
        List<Author> authors = authorRepository.findAll(authorSpecification);

        return AuthorMapper.INSTANCE.authorToAuthorResponseDto(authors);
    }
}


