package com.mountblue.stackoverflow.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length=10485760)
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String userName;

    private int vote=0;

    private String email;

    private int reputation = 1;

    @OneToMany(mappedBy = "answer",
            cascade = {CascadeType.REMOVE})

    List<AnswerComment> answerComments = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    public Answer() {
    }

    public Answer(int id, String content, LocalDateTime createdAt, String userName, int vote, String email, int reputation, List<AnswerComment> answerComments, Question question) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.vote = vote;
        this.email = email;
        this.reputation = reputation;
        this.answerComments = answerComments;
        this.question = question;
    }

    public Answer(String content, LocalDateTime createdAt, String userName, int vote, String email, int reputation, List<AnswerComment> answerComments, Question question) {
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.vote = vote;
        this.email = email;
        this.reputation = reputation;
        this.answerComments = answerComments;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<AnswerComment> getAnswerComments() {
        return answerComments;
    }

    public void setAnswerComments(List<AnswerComment> answerComments) {
        this.answerComments = answerComments;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", vote=" + vote +
                ", email='" + email + '\'' +
                ", reputation=" + reputation +
                '}';
    }
}
