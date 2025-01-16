package com.lucene.learn.utils;

import com.lucene.learn.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexUtils {

    public static void deleteIndex(String indexDir,String field, String delVal){
        // 删除文档的逻辑是删除查到的
        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        Path indexPath = Paths.get(indexDir);
        Directory directory = null;
        try {
            directory = FSDirectory.open(indexPath);
            IndexWriter indexWriter = new IndexWriter(directory, iwc);
            indexWriter.deleteDocuments(new TermQuery(new Term(field, delVal)));
            //indexWriter.deleteAll();
            indexWriter.commit();
            indexWriter.close();
            System.out.println("删除完成");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
