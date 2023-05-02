package com.taritari.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author taritari
 * @date 2023-04-30 14:54
 * @description
 */
@Entity
@Table(name = "blog_articleviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogArticleViews {
    @Id
    private Integer id;

    private String articleNumber;

    private Integer numberOfViews;
}
