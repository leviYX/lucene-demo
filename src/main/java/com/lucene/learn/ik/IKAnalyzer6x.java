package com.lucene.learn.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class IKAnalyzer6x extends Analyzer {

    private boolean useSmart;

    public IKAnalyzer6x() {
        // IK分词器Lucene Analyzer 接口实现类，默认细粒度切分算法
        this(false);
    }

    public IKAnalyzer6x(boolean useSmart) {
        // IK分词器Lucene Analyzer 接口实现类，为true时，分词器进行智能切分
        super();
        this.useSmart = useSmart;
    }

    // 重写最新版本的createComponents方法，重载父类Analyzer的createComponents方法，构造分词组件
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer ikTokenizer = new IKTokenizer6x(this.useSmart);
        return new TokenStreamComponents(ikTokenizer);
    }

    public boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }
}
