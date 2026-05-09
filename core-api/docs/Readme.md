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
---

## void vs Void
### void
- Type: A **primitive keyword** in Java.
- Purpose: Specifies that a method does not return any value.
- You cannot use void as a variable type.
- Only used in method declarations.

### Void
- Type: A reference type (class) in java.lang.
- Purpose: Used mostly in reflection or generics, to represent a void return type as an object.
- Void.TYPE is a class object representing the primitive void.
- You cannot create an instance (object) of Void because it only has a private constructor.
---

# Backend Development:
- Add schema and define relationships.
- Then Add controller, service, repository.
- Then add dto and mappers.
- Then add validations.
- Then add exception handling.
- Then add custom schema for apiResonse (for both success and error it should be same).
