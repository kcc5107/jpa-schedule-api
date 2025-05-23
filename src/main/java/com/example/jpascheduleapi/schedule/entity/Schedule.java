package com.example.jpascheduleapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    public Schedule(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}
