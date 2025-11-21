package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Global exception handler that returns compact JSON:
 * { "error": "NOT_FOUND" }
 *
 * This handler tries to extract status from ResponseStatusException via reflection
 * to be compatible across Spring versions. If extraction fails we default to NOT_FOUND.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler for ResponseStatusException.
     * We try to read the status from the exception; if we get 404 we return 404 with small JSON.
     * For all other statuses we still return the small JSON but with that HTTP status.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = extractStatusSafely(ex);
        // Always return compact JSON as requested
        return Mono.just(ResponseEntity.status(status).body(Map.of("error", "NOT_FOUND")));
    }

    /**
     * Generic fallback (keeps error shape consistent).
     * Even on internal errors we return the same small JSON body (status 500).
     */
    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<Map<String, String>>> handleAny(Throwable ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "NOT_FOUND")));
    }

    /**
     * Try to extract an HttpStatus from the ResponseStatusException.
     * Uses reflection to remain compatible across Spring versions.
     * If extraction fails, default to NOT_FOUND.
     */
    private HttpStatus extractStatusSafely(ResponseStatusException ex) {
        try {
            // Common method name in WebFlux: getStatus()
            Method m = ex.getClass().getMethod("getStatus");
            Object result = m.invoke(ex);
            if (result instanceof HttpStatus) {
                return (HttpStatus) result;
            }
            // Some versions may return other types; try to convert if possible
            if (result != null) {
                String s = result.toString();
                try {
                    return HttpStatus.valueOf(s);
                } catch (IllegalArgumentException ignored) { }
            }
        } catch (NoSuchMethodException ignored) {
            // method missing in this Spring version - will fall through to default
        } catch (Exception ignored) {
            // reflection call failed for some reason - fall through to default
        }
        // Default fallback as requested
        return HttpStatus.NOT_FOUND;
    }
}