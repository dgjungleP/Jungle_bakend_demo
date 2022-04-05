package com.jungle.analysis.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleAnalysisTest {
    @Test
    public void simpleTest() {
        List<String> he = new SimpleAnalysis().cut("He");
        System.out.println(he);
    }

}