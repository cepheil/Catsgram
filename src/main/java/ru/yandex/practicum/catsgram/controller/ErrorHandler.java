package ru.yandex.practicum.catsgram.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.catsgram.exception.*;
import ru.yandex.practicum.catsgram.model.*;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException e) {
        return buildErrorResponse(e);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicatedDataException(DuplicatedDataException e) {
        return buildErrorResponse(e);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handlerConditionsNotMetException(ConditionsNotMetException e) {
        return buildErrorResponse(e);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerParameterNotValidException(ParameterNotValidException e) {
        return buildErrorResponse(e);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerThrowable(Throwable e) {
        return new ErrorResponse(
                "error: " + e.getClass().getSimpleName(),
                "description: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerDataAccessException(DataAccessException e) {
        return buildErrorResponse(e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return buildErrorResponse(e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerInternalServerException(InternalServerException e) {
        return buildErrorResponse(e);
    }


    private ErrorResponse buildErrorResponse(Exception e) {
        return new ErrorResponse(
                "error: " + e.getClass().getSimpleName(),
                "description: " + e.getMessage());

    }


}
