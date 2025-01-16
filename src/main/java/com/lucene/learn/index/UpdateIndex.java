package com.lucene.learn.index;

import com.lucene.learn.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UpdateIndex {
    public static void main(String[] args) {
        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        Path indexPath = Paths.get("indexDir");
        Directory directory = null;
        try {
            directory = FSDirectory.open(indexPath);
            IndexWriter indexWriter = new IndexWriter(directory, icw);
            Document doc = new Document();
            doc.add(new TextField("id","2", Field.Store.YES));
            doc.add(new TextField("title","lucene入门指南", Field.Store.YES));
            doc.add(new TextField("content","Lucene是一个高性能，基于Java的全文检索引擎工具包", Field.Store.YES));
            indexWriter.updateDocument(new Term("title", "美国"),doc);

            indexWriter.commit();
            indexWriter.close();
            System.out.println("更新完成");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
