package com.smartlist.core_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private Superlist superlist;
}
