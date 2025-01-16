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
        /**
         * 分词器，选择具体的实现类来处理不同的语言规则文本。其内部分词主要依赖TokenStream实现，
         * Tokenizer 和 TokenFilter 是TokenStream的两个实现子类。Tokenizer处理单个字符组成的字符流
         * 读取reader对象中的原始数据，处理之后输出为词汇单元。
         * TokenFilter处理Tokenizer输出的词汇单元，对其进行过滤、修改等操作。完成文本过滤器的功能，其中TokenFilter的使用顺序会影响最终的输出结果。
         */
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        // 创建字符串读取器对象，用于读取文本内容，读取文本内容，并将其转换为字符流。
        StringReader reader = new StringReader(str);
        // 通过分析器获取一个 TokenStream，它表示从输入字符串中提取的词项流。TokenStream 是 Lucene 中的一个抽象类，表示一个流式的词项。
        TokenStream toStream = analyzer.tokenStream(str, reader);
        // 重置 TokenStream，准备从头开始读取词项
        toStream.reset();
        // 功能: 获取 CharTermAttribute 属性，它用于表示当前词项的字符值。
        //类: CharTermAttribute 是 Lucene 中的一个接口，提供对当前词项字符内容的访问。
        CharTermAttribute teAttribute = toStream.getAttribute(CharTermAttribute.class);
        System.out.println("分词结果:");
        // 功能: 逐个获取 TokenStream 中的词项，直到没有更多的词项。每个词项通过 teAttribute.toString() 输出。
        //类: 通过 TokenStream 的 incrementToken() 方法进行词项的迭代。
        /**
         * toStream.incrementToken() 方法的迭代会影响 teAttribute.toString() 的输出，原因在于 TokenStream 和 CharTermAttribute 之间的工作机制。
         * 以下是详细解释：
         *
         * TokenStream 的工作原理:
         * TokenStream 是一个迭代器，用于表示从输入文本中生成的词项流。每次调用 incrementToken() 方法时，它会将流中的下一个词项加载到当前的上下文中。
         * CharTermAttribute 的作用:
         * CharTermAttribute 是一个属性接口，它提供对当前词项字符内容的访问。它不存储词项数据，而是依赖于 TokenStream 在每次调用 incrementToken() 时更新的当前状态。
         * 迭代过程:
         * 当调用 toStream.incrementToken() 时，TokenStream 会更新当前词项并将其内容加载到 CharTermAttribute 中。每次成功调用 incrementToken() 返回 true 时，CharTermAttribute 中的内容都会被更新为当前词项的值。
         * 因此，teAttribute.toString() 会返回当前词项的字符值。
         * 示例:
         * 假设输入字符串为 "Hello world"。
         * 第一次调用 incrementToken()，TokenStream 可能会将 "Hello" 加载到 CharTermAttribute。
         * 此时，如果调用 teAttribute.toString()，输出将是 "Hello"。
         * 下一次调用 incrementToken()，TokenStream 会加载 "world" 到 CharTermAttribute。
         * 调用 teAttribute.toString() 时，输出将变为 "world"。
         * 因此，toStream.incrementToken() 的迭代过程直接影响 teAttribute 的内容，使得每次输出的词项都是当前的激活词项。
         */
        while (toStream.incrementToken()) {
            System.out.println(teAttribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }
}
