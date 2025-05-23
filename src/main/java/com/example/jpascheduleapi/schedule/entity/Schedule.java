package com.example.jpascheduleapi.schedule.entity;

import com.example.jpascheduleapi.common.BaseEntity;
import com.example.jpascheduleapi.user.entity.User;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
