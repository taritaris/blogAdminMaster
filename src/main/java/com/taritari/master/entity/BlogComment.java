package com.taritari.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author taritari
 * @date 2023-04-30 14:55
 * @description
 */
@Entity
@Table(name = "blog_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogComment {
    @Id
    private Integer id;

    private String number;

    private String articleNumbers;

    private String userNumbers;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String parentNumber;

    private String comment;

    private String parentUser;
}
