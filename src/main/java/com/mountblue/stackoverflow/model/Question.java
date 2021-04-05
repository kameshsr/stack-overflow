package com.mountblue.stackoverflow.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {

    @Id
    private int id;

    @Column(length=10485760)
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String userName;

    private String title;

    private String tags;

    private int vote=0;

    private String email;

    private int reputation = 1;

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.REMOVE})

    List<QuestionComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.REMOVE})

    List<Answer> answers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "question_tags",
            joinColumns = { @JoinColumn(name = "question_id")},
            inverseJoinColumns = { @JoinColumn (name = "tag_id")})
    private Set<Tag> tagList = new HashSet<>();

    public Question() {
    }

    public Question(int id, String content, LocalDateTime createdAt, String userName, String title, String tags, int vote, String email, int reputation, List<QuestionComment> comments, List<Answer> answers) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.title = title;
        this.tags = tags;
        this.vote = vote;
        this.email = email;
        this.reputation = reputation;
        this.comments = comments;
        this.answers = answers;
    }

    public Question(String content, LocalDateTime createdAt, String userName, String title, String tags, int vote, String email, int reputation, List<QuestionComment> comments, List<Answer> answers) {
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.title = title;
        this.tags = tags;
        this.vote = vote;
        this.email = email;
        this.reputation = reputation;
        this.comments = comments;
        this.answers = answers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public List<QuestionComment> getComments() {
        return comments;
    }

    public void setComments(List<QuestionComment> comments) {
        this.comments = comments;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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

    public Set<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(Set<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userName='" + userName + '\'' +
                ", comments=" + comments +
                '}';
    }
}
