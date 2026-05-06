# ✅ Validation Guide

## 📌 Overview

Validation is implemented at multiple levels:

| Layer        | Responsibility                        |
|-------------|--------------------------------------|
| DTO          | Input validation (structure & format) |
| Controller   | Trigger validation                   |
| Service      | Business rules validation            |
| Entity       | Database-level constraints (optional)|

---

## 🧩 1. DTO Validation

- DTO validation means validating incoming `request data` before it reaches your business logic using annotations on your DTO (Data Transfer Object) classes.
- Validation annotations are applied to request DTOs to ensure incoming data is valid before processing.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>4.1.0-RC1</version>
</dependency> 
```
- Example:
```java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSuperListRequest(
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name
) {}
```
- Response DTOs usually do not need validation as it is returned.
- We need DTO only for untrusted input coming into your system.

## Spring does NOT run above validation automatically unless you tell it to.
- Add `@Valid` in controller.
---

## 🚀 2. Controller Validation

Use `@Valid` to trigger validation on incoming requests.

```java
@PostMapping("/user/{userId}")
public SuperlistResponse createSuperList(
        @PathVariable Long userId,
        @Valid @RequestBody CreateSuperListRequest request
) {
    return superListService.createSuperList(userId, request);
}
```

---

## 🏛 3. Entity-Level Validation (optional)

Basic constraints can be added at the entity level:

```java
@NotBlank
@Column(nullable = false)
private String name;
```

### ⚠️ Why entity validation is considered optional?
- It runs too late in the lifecycle (dto -> controller -> service -> enitity).

### ✅ So why use entity validation at all?
1. Not all data enters via DTOs, data can come through:
   - Background jobs.
   - Data migrations.
   - Internal service calls.
   - Tests creating entities directly.

2. Guards against developer mistakes.
```java
Superlist s = new Superlist();
s.setName(null); // mistake
repository.save(s);
```

---

⚖️ Clean separation

| Layer   | What to validate                        |
| ------- | --------------------------------------- |
| DTO     | Request-specific rules                  |
| Service | Business logic                          |
| Entity  | Core data integrity (basic constraints) |


---

## Service Layer Validation (Business Rules)
- Need to add manual validation.
- Examples:
  - Prevent duplicate superlist names per user.
  - Limit number of items in a list.
  - Validate user ownership.

## Global Exception Handling
- Handles validation errors and returns user-friendly responses.

