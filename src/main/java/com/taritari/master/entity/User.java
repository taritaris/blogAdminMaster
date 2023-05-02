package com.taritari.master.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "blog_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name="message")
    private String message;

    @Column(name="title_img_path")
    private String titleImgPath;

    @Column(name="email")
    private String email;

    @Column(name="content")
    private String content;

}