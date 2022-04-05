package com.jungle.analysis.core;


import com.jungle.analysis.handle.AmbiguityHandle;
import com.jungle.analysis.util.AnalysisUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractAnalysis implements MyAnalysis {


    private final List<String> MISS_WORDS = new ArrayList<>();

    private static final List<AmbiguityHandle> AMBIGUITY_HANDLES = new ArrayList<>();

    @Override
    public final String pretreatment(String in) {
        handlePrepareWords(in);
        return AnalysisUtil.pretreatment(in);
    }


    @Override
    public void loadDictionary() {
        loadMain(getMainDictionaryUri());
        loadStop(getStopDictionaryUri());
        loadClassifier(getClassifierDictionaryUri());
    }


    @Override
    public List<String> analysis(String in) {
        return null;
    }

    @Override
    public List<String> handleAmbiguity(List<String> words) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            String preWord = word;
            for (AmbiguityHandle handle : AMBIGUITY_HANDLES) {
                preWord = handle.handle(preWord);
            }
            result.add(preWord);
        }
        return result;
    }

    @Override
    public final List<String> export(List<String> handleAmbiguity) {

        return null;
    }

    @Override
    public final List<String> cut(String in) {
        loadDictionary();
        String pretreatment = pretreatment(in);
        List<String> analysis = analysis(pretreatment);
        List<String> handleAmbiguity = handleAmbiguity(analysis);
        return export(handleAmbiguity);
    }


    public abstract void handlePrepareWords(String in);

    public abstract void loadClassifier(String uri);

    public abstract void loadStop(String uri);

    public abstract void loadMain(String uri);

    public abstract String getMainDictionaryUri();

    public abstract void setMainDictionaryUri(String mainDictionaryUri);

    public abstract String getStopDictionaryUri();

    public abstract void setStopDictionaryUri(String stopDictionaryUri);

    public abstract String getClassifierDictionaryUri();

    public abstract void setClassifierDictionaryUri(String classifierDictionaryUri);
}
