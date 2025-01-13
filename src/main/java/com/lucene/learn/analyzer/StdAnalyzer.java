package com.lucene.learn.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class StdAnalyzer {
    private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";
    private static String strEn = "The United States of America, commonly known as the United States or America";

    public static void main(String[] args) throws IOException {

        System.out.println("对中文进行分词:");
        stdAnalyzer(strCh);
        System.out.println("对英文进行分词:");
        stdAnalyzer(strEn);
    }

    public static void stdAnalyzer(String str) throws IOException {
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        StringReader reader = new StringReader(str);
        TokenStream toStream = analyzer.tokenStream(str, reader);
        toStream.reset();
        CharTermAttribute teAttribute = toStream.getAttribute(CharTermAttribute.class);
        System.out.println("分词结果:");
        while (toStream.incrementToken()) {
            System.out.println(teAttribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }
}
