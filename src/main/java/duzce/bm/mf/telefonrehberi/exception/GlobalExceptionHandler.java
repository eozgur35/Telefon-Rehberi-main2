package duzce.bm.mf.telefonrehberi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model, HttpServletRequest request) {
        LOG.error("[404] {}", ex.getMessage(), ex);

        model.addAttribute("errorCode", "ERR-404");
        model.addAttribute("errorType", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());

        request.setAttribute("jakarta.servlet.error.status_code", 404);

        return "error";
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(BadRequestException ex, Model model, HttpServletRequest request) {
        LOG.error("[400] {}", ex.getMessage(), ex);

        model.addAttribute("errorCode", "ERR-400");
        model.addAttribute("errorType", "Bad Request");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());

        request.setAttribute("jakarta.servlet.error.status_code", 400);

        return "error";
    }

    @ExceptionHandler(DatabaseException.class)
    public String handleDatabase(DatabaseException ex, Model model, HttpServletRequest request) {
        LOG.error("[DB] {}", ex.getMessage(), ex);

        model.addAttribute("errorCode", "ERR-DB");
        model.addAttribute("errorType", "Database Error");
        model.addAttribute("errorMessage", "Sistem hatası oluştu");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());

        request.setAttribute("jakarta.servlet.error.status_code", 500);

        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex, Model model, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        LOG.error("[VALIDATION] {}", errors);

        model.addAttribute("errorCode", "ERR-VALIDATION");
        model.addAttribute("errorType", "Validation Error");
        model.addAttribute("validationErrors", errors);
        model.addAttribute("errorMessage", "Form hatalı");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());

        request.setAttribute("jakarta.servlet.error.status_code", 400);

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model, HttpServletRequest request) {
        LOG.error("[500] Unexpected error", ex);

        model.addAttribute("errorCode", "ERR-500");
        model.addAttribute("errorType", "Internal Server Error");
        model.addAttribute("errorMessage", "Beklenmeyen bir hata oluştu");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());

        request.setAttribute("jakarta.servlet.error.status_code", 500);

        return "error";
    }}
