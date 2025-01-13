package com.lucene.learn.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class VariousAnalyzers {

    private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "标准分词");

        analyzer = new WhitespaceAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "空格分词");

        analyzer = new SimpleAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "简单分词");

        analyzer = new CJKAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "二分分词");

        analyzer = new KeywordAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "关键字分词");

        analyzer = new StopAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "停用词分词");

        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer);
        System.out.println(analyzer.getClass().getSimpleName() + "中文智能分词");
    }

    private static void printAnalyzer(Analyzer analyzer) throws IOException {
        StringReader reader = new StringReader(strCh);
        TokenStream tokenStream = analyzer.tokenStream(strCh, reader);
        // 切换为读取模式，和nio很像
        tokenStream.reset();
        CharTermAttribute teAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.println(teAttribute.toString() + "|");
        }
        System.out.println("\n");

    }

}
