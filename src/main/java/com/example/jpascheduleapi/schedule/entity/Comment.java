package com.example.jpascheduleapi.schedule.entity;

import com.example.jpascheduleapi.common.BaseEntity;
import com.example.jpascheduleapi.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Setter
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String contents) {
        this.contents = contents;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}
