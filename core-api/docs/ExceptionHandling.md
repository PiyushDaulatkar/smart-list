- Create custom exceptions instead of throwing RuntimeException.
- Use a global exception handler with `@RestControllerAdvice`.
- Return consistent API error responses.
- Map exceptions to proper HTTP status codes.
- Be careful what you log.
- Don’t bury thrown exceptions.
- Use a global Exception handler.
- Don’t close resources manually.
- Throw early and handle exceptions late.
- Don’t log and rethrow Java exceptions.
- Check for suppressed exceptions.
- Explicitly define exception in the throws clause.
- Catch the most explicit exception first.

---

# `@RestControllerAdvice`
- "A global error manager for REST APIs".
- Used for global exception handling in Spring Boot REST APIs.
- Applies to all controllers.
- Combines: `@ControllerAdvice + @ResponseBody`
- Returns JSON/text responses automatically.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return "Something went wrong";
    }
}
```

- If any controller throws exception: `throw new RuntimeException("Error");`.
- Response: `Something went wrong`.

---

# `@ExceptionHandler`
- Handles specific exceptions.
- Works like catch block.
- Can be used:
  - inside controller.
  - inside `@RestControllerAdvice`.

---

## Quick Memory Trick

```
@RestControllerAdvice
= Global error manager

@ExceptionHandler
= Handles specific exception
```

---

## Without centralized handling:

- Every controller needs try-catch.
- Error responses become inconsistent.
- Code duplication increases.

---

## How Spring Internally Works
When exception occurs:
```
Controller -> Exception thrown
            ↓
DispatcherServlet
            ↓
@RestControllerAdvice scans handlers
            ↓
Matching @ExceptionHandler found
            ↓
Response returned
```