package kim.intae.assignment.payment.controller

import com.fasterxml.jackson.core.JsonParseException
import kim.intae.assignment.payment.controller.response.PaymentResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import java.security.InvalidParameterException
import javax.persistence.EntityNotFoundException

@RestControllerAdvice(annotations = [RestController::class], basePackages = ["com.kakaopay.assignment.payment.ikim"])
class RestControllerExceptionHandler {
    @ExceptionHandler(value = [
        JsonParseException::class,
        InvalidParameterException::class,
        MethodArgumentNotValidException::class,
        MethodArgumentTypeMismatchException::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class
    ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestException(ex: Exception, request: WebRequest): PaymentResponse {
        return PaymentResponse(
            HttpStatus.BAD_REQUEST,
            ex.message,
        )
    }

    @ExceptionHandler(value = [
        EntityNotFoundException::class,
        NoHandlerFoundException::class
    ])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(ex: Exception, request: WebRequest?): PaymentResponse {
        return PaymentResponse(
            HttpStatus.NOT_FOUND,
            ex.message,
        )
    }

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun paymentServiceException(ex: RuntimeException, request: WebRequest?): PaymentResponse {
        return PaymentResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.message,
        )
    }
}
