# Annotations
- Annotations in Java are a special kind of metadata that you can add to your code (classes, methods, variables, etc.) to give extra information to the compiler, tools, or runtime.

---

# ResponseEntity
- `ResponseEntity` is a class from the Spring Framework used to represent the entire HTTP response in REST APIs.
- It gives you control over:
  - HTTP status code.
  - Response headers.
  - Response body.
- It is commonly used in Spring Boot REST controllers.
- `ResponseEntity<T>`: `T` = type of response body.
- Example:
```java
return ResponseEntity.status(HttpStatus.CREATED)
                     .body("User created");
```
