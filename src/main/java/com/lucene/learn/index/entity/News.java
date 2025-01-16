package com.lucene.learn.index.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private int id;
    private String title;
    private String content;
    private int reply;
}
