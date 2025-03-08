package com.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private Integer rating;
    @ManyToOne
    @JoinColumn(name = "addressed_to_user_id")
    private User seller;
    private boolean isVerified;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}
