package com.lucene.learn.queries;

import com.lucene.learn.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class QueryParseTest {
    public static void main(String[] args) throws Exception {
        String field = "title";
        Path indexPath = Paths.get("indexdir");
        FSDirectory directory = FSDirectory.open(indexPath);
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Analyzer analyzer = new IKAnalyzer6x();
        QueryParser queryParser = new QueryParser(field, analyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.AND);

        Query query = queryParser.parse("农村学生");
        System.out.println(query.toString());

        TopDocs tds = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = tds.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println("id:" + doc.get("id"));
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));
            System.out.println("DocId:" + scoreDoc.doc);
            System.out.println(scoreDoc.score);
            System.out.println("----------------------");
        }
    }
}
