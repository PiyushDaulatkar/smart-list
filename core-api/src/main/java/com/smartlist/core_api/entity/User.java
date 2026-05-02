package com.smartlist.core_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // INSIGHT: mappedBy tells JPA: “This is the inverse side of a relationship. The foreign key is owned by the user field in UserList.”
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Superlist> superlists = new ArrayList<>();

    public void addSuperList(Superlist list) {
        // INSIGHT: O2M relation, keep both sides in sync.
        superlists.add(list);
        list.setUser(this);
    }

    public void removeSuperList(Superlist list) {
        superlists.remove(list);
        list.setUser(null);
    }
}
