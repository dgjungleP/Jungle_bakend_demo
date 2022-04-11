package com.jungle.analysis.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleAnalysisTest {
    @Test
    public void simpleTest() {
        List<String> he = new SimpleAnalysis().cut("这里是位于中国的四川，看一看是不是可以正确的进行分词");
        System.out.println(he);
    }

}