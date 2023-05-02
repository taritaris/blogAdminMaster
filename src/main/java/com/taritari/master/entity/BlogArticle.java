package com.taritari.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author taritari
 * @date 2023-04-30 14:51
 * @description
 */
@Entity
@Table(name = "blog_article")
@Data
public class BlogArticle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String author;

    private String title;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;

    private String isDelete;

    private String numbers;

    private String imgSrc;

    private Integer tagId;

    private String numberOfViews;
}
