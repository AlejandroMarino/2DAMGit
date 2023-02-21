package com.example.examen.errores

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@Component("MisErrores")
class ControlErrores {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        NotFoundException::class
    )
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ApiError?>? {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body<ApiError?>(ApiError(e.message, "debug", "404"))
    }

}
