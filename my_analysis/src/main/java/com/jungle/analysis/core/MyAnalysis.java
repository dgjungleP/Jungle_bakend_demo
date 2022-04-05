package com.jungle.analysis.core;

import java.util.List;

public interface MyAnalysis {

    void loadDictionary();

    String pretreatment(String in);

    List<String> analysis(String in);

    List<String> handleAmbiguity(List<String> words);

    List<String> export(List<String> words);

    List<String> cut(String in);
}
