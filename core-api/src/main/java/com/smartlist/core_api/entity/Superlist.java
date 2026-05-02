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
public class Superlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "superlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListItem> listItems = new ArrayList<>();

    public void addItem(ListItem item) {
        // INSIGHT: In bidirectional JPA relationships, YOU must keep both sides in sync manually.
        listItems.add(item);
        item.setSuperlist(this);
    }

    public void removeItem(ListItem item) {
        listItems.remove(item);
        item.setSuperlist(null);
    }
}
