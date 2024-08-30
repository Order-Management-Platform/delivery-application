package com.sparta.delivery.help.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.help.dto.HelpRequestDto;
import com.sparta.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_help")
@SQLRestriction("deleted_at is null")
@Builder(access = AccessLevel.PRIVATE)
public class Help extends BaseEntity {
    @Id
    @Column(name = "help_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "help_title")
    private String title;

    @Column(name = "help_question")
    private String question;

    @Column(name = "help_answer")
    private String answer;

    public static Help create(User user, HelpRequestDto request) {
        return Help.builder()
                .user(user)
                .title(request.getTitle())
                .question(request.getQuestion())
                .build();
    }

    public void update(String title, String question, String answer) {
        this.title = title;
        this.question = question;
        this.answer = answer;
    }
}
