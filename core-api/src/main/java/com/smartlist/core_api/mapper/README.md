## 🚨 Problem: Manual DTO ↔ Entity mapping is tedious boilerplate

```java
// ❌ 50+ lines of repetitive code
public UserDto toDto(User user) {
    UserDto dto = new UserDto();
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    // ... 50 more getters/setters
    return dto;
}
```

## ✅ Solution: MapStruct (library) generates this automatically
```java
// ✅ 1 line mapper interface
@Mapper
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
```

### Benefits:
- Zero runtime overhead (compile-time generation)
- Type-safe (compiler catches errors)
- Handles nested objects, collections, custom conversions

---

## Setup

✅ `mapstruct` = "runtime library"
- Contains @Mapper annotations.
- Needed when your app runs.

✅ `mapstruct-processor` = "boilerplate generator tool"
- Runs ONLY during mvn compile.
- Creates your mapper classes automatically . 
- NOT needed when your app runs.
```xml
<dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.6.3</version>
</dependency>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

<build>
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version>
        <configuration>
            <source>21</source>
            <target>21</target>
            <annotationProcessorPaths>
                <path>
                    <groupId>org.mapstruct</groupId>
                    <artifactId>mapstruct-processor</artifactId>
                    <version>1.6.3</version>
                </path>
            </annotationProcessorPaths>
        </configuration>
    </plugin>
</plugins>
</build>
```
