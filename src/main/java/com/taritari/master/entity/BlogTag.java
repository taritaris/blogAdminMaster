package com.taritari.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author taritari
 * @date 2023-04-30 15:56
 * @description
 */
@Entity
@Table(name = "blog_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {
    @Id
    private Integer id;

    private String tagName;

    private Integer tagId;
}
