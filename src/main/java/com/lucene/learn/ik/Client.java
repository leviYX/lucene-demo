package com.lucene.learn.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private static final String input = "厉害了我的哥，北京人民将会开始治理雾霾。";
    public static void main(String[] args) throws IOException {
        // 加载自定义的停止词字典
        //CharArraySet stopWords = loadStopWords("/Users/levi/develop/project/elastic/code/lucene/lucene-learn/src/main/resources/stopword.dic");
        Analyzer analyzer = new IKAnalyzer6x();
        StringReader reader = new StringReader(input);
        TokenStream tokenStream = analyzer.tokenStream(input, reader);
        tokenStream.reset();
        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.println(termAttribute.toString());
        }
        analyzer.close();
    }

    private static CharArraySet loadStopWords(String filePath) throws IOException {
        // 创建一个 CharArraySet 来存储停止词
        CharArraySet stopWords = new CharArraySet(10, true);

        // 使用 BufferedReader 读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> words = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
            // 将读取的停止词添加到 CharArraySet
            stopWords.addAll(words);
        }
        return stopWords;
    }
}
