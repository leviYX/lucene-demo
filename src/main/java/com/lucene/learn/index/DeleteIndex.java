package com.lucene.learn.index;

import com.lucene.learn.utils.IndexUtils;

public class DeleteIndex {

    public static void main(String[] args) {
        // 删除title中含有关键词"美国"的文档
        IndexUtils.deleteIndex("indexDir","title","美国");
    }
}
