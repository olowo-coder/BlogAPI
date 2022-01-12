package com.example.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMessage {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long postId;


    @Column(
            name = "post_body",
            columnDefinition = "TEXT"
    )
    private String postBody;


    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "users_id",
            referencedColumnName = "userId"
    )
    private Users users;

    @CreationTimestamp
    private LocalDateTime timeStamp;


//    @OneToMany(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER
//    )
//    @JoinColumn(
//            name = "postId",
//            referencedColumnName = "postId"
//    )
//    private List<Comment> comments;
}
