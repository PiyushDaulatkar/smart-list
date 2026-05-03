# Delete Operations in One-to-Many Relationships (JPA)

## Context
- `User → Superlist` (One-to-Many)
- `Superlist → ListItem` (One-to-Many)

Both are similar relationships, but **delete operations differ based on what is being removed**.

---

## 1. Delete Superlist (Parent Entity)

```java
superListRepository.delete(superlist);
```

Why?
- Deletes the entire parent entity.
- All related ListItem entries are also removed (if cascade = ALL is used).
- No need to call save() afterward.

Key Idea\
👉 You are deleting the whole aggregate root (superList + listItem).

## 2. Delete ListItem (Child Entity)

```java
superlist.removeItem(item);
superListRepository.save(superlist);
```

Why?
- Only a single child is removed.
- Parent (Superlist) still exists.
- With orphanRemoval = true, removing from collection marks child for deletion.
- save(superlist) applies the updated relationship.


## Key Difference
| Operation        | What is deleted       | Approach                               |
| ---------------- | --------------------- |----------------------------------------|
| Delete Superlist | Parent + all children | `delete(superlist)`                    |
| Delete ListItem  | Single child only     | remove from collection OR delete item. |

---

# @Transactional 

- Execute the method inside a single database transaction.
- 🧠 Simple analogy: “Either all DB changes happen, or none of them happen.”

⚙️ What Spring actually does

When a method is annotated with @Transactional, Spring:
1. Opens a DB transaction.
2. Executes all operations inside the method.
3. If everything is fine → commits transaction.
4. If any exception happens → rolls back everything.

💥 Why transactions are important

Without transaction:
- partial updates may persist.
- inconsistent data may remain in DB.

With transaction:

`SUCCESS → commit all changes.`\
`FAILURE → rollback everything.`

---
