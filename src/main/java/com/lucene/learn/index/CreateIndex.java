package com.lucene.learn.index;

import com.lucene.learn.ik.IKAnalyzer6x;
import com.lucene.learn.index.entity.News;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class CreateIndex {

    public static void main(String[] args) {
        News news1 = News.builder().id(1)
                .title("习近平会见美国总统奥巴马，学习国外经验")
                .content("国家主席习近平9月3日在杭州西湖国宾馆会见前来出席的二十国集团领导人杭州峰会的美国总统奥巴马。")
                .reply(672)
                .build();

        News news2 = News.builder().id(2)
                .title("北大迎4380名新生，农村学生700多人，为近年来最多")
                .content("昨天，北京大学迎来4380名来自全国各地以及数十个国家的本科新生，其中农村学生700余名，为近年来最多...")
                .reply(995)
                .build();

        News news3 = News.builder().id(2)
                .title("特朗普宣誓(Donald J.Trump)就任美国第45任总统")
                .content("当地时间1月20日，唐纳德特朗普在美国国会宣誓就职，正式成为美国第45任总统。")
                .reply(1872)
                .build();

        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = null;
        IndexWriter indexWriter = null;
        // 索引目录
        Path indexPath = Paths.get("indexDir");
        // 开始时间
        long startTime = Instant.now().toEpochMilli();
        try {
            if(!Files.isReadable(indexPath)) {
                System.out.println("索引目录不存在，创建索引...请检查对应的路径");
                System.exit(1);
            }
            dir = FSDirectory.open(indexPath);
            indexWriter = new IndexWriter(dir, icw);
            // 设置新闻id并且存储
            FieldType idType = new FieldType();
            idType.setIndexOptions(IndexOptions.DOCS);
            idType.setStored(true);
            // 设置新闻标题索引文档，词项频率，位移信息，偏移量
            FieldType titleType = new FieldType();
            titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            titleType.setStored(true);
            titleType.setTokenized(true);

            FieldType contentType = new FieldType();
            contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            contentType.setStored(true);
            contentType.setTokenized(true);
            contentType.setStoreTermVectors(true);
            contentType.setStoreTermVectorPositions(true);
            contentType.setStoreTermVectorOffsets(true);
            contentType.setStoreTermVectorPayloads(true);

            Document doc1 = new Document();
            doc1.add(new Field("id", String.valueOf(news1.getId()), idType));
            doc1.add(new Field("title", news1.getTitle(), titleType));
            doc1.add(new Field("content", news1.getContent(), contentType));
            doc1.add(new IntPoint("reply", news1.getReply()));
            doc1.add(new StoredField("reply_display", news1.getReply()));

            Document doc2 = new Document();
            doc2.add(new Field("id", String.valueOf(news2.getId()), idType));
            doc2.add(new Field("title", news2.getTitle(), titleType));
            doc2.add(new Field("content", news2.getContent(), contentType));
            doc2.add(new IntPoint("reply", news2.getReply()));
            doc2.add(new StoredField("reply_display", news2.getReply()));

            Document doc3 = new Document();
            doc3.add(new Field("id", String.valueOf(news3.getId()), idType));
            doc3.add(new Field("title", news3.getTitle(), titleType));
            doc3.add(new Field("content", news3.getContent(), contentType));
            doc3.add(new IntPoint("reply", news3.getReply()));
            doc3.add(new StoredField("reply_display", news3.getReply()));

            indexWriter.addDocument(doc1);
            indexWriter.addDocument(doc2);
            indexWriter.addDocument(doc3);
            indexWriter.commit();
            indexWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        long endTime = Instant.now().toEpochMilli();
        System.out.println("创建索引耗时：" + (endTime - startTime) + "ms");
    }
}
