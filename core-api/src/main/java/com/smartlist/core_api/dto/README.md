# ⚠️ Problem: Infinite JSON Recursion in *Bidirectional* JPA Relationships.

### ❗ Issue
- When using bidirectional relationships in JPA like:\
`User → Superlist → User → Superlist → ...`\
i.e. `User (1) ──── (N) Superlist`.
- Spring Boot tries to convert entities into JSON, which creates an infinite loop.
- Like when you query user, in user it looks for Superlist, in Superlist it looks for user and so on infinite.

### 📌 Example from Code

```java
User {
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Superlist> superlists;
}

Superlist {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

### ❌ What goes wrong
When returning a Superlist from API:
```json
{
  "id": 1,
  "name": "Groceries",
  "user": {
    "id": 4,
    "name": "test",
    "superlists": [
      {
        "id": 1,
        "user": {
          "superlists": [
            {
              "user": {
                "superlists": [
                  ...
                ]
              }
            }
          ]
        }
      }
    ]
  }
}
```
👉 This becomes an infinite nesting loop.

---
## Solution

### Fix 1: ***Fix 1: Jackson Annotations***
- Use Jackson annotations to break the recursion.

```java
User {
    @JsonManagedReference //............
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Superlist> superlists;
}

Superlist {
    @JsonBackReference //............
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```
✔ How it works\
`@JsonManagedReference` → serialized normally (i.e. serialize this side)\
`@JsonBackReference` → ignored during serialization (i.e. ignore this side during serialization)\

👉 Breaks the infinite loop.

### ⚠️ Limitation
- Still exposes full entities
- Can cause tight coupling between DB and API response
- Not flexible for API design.

---

### ✅ Fix 2: DTO (Recommended Solution)

- Instead of returning entities directly, return Data Transfer Objects (DTOs).
- DO NOT expose ENTITY directly.

```java
UserDTO {
    private Long id;
    private String name;
    // DO NOT EXPOSE SuperLIst.
}

SuperlistDTO {
    private Long id;
    private String name;
    private List<ListItemDTO> items;
    // DO NOT EXPOSE User.
}
```

✔ Benefits of DTO approach
- Prevents recursion completely.
- Full control over API response.
- Improves security (no internal fields exposed).
- Avoids lazy loading issues.
- Makes API versioning easier.

---

# Record Classes (Java 17+)

- Mainly used if you want classes that only used for data storage(data carrier classes).
- Reocrd create immutable data classes with zero boilerplate code.
- Records auto-generate:
  - All `Private final` fields (private final String name).
  - Read-only getters (name()).
  - `equals(), hashCode(), toString()`.
  - ❌ No setters.
- Redords do not have default "no args" Constructor, you need to implement it manually.
- Note: getter in records do not have `get` prefix.

⚠️ When NOT to use records\
- you need setters (mutable DTO).
- complex mapping logic.
- partial updates (PATCH).

💡 Final takeaway

✅ Records are ideal for request/response DTOs.\
❌ Entities should NOT be records.

---
