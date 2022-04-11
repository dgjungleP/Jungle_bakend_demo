package com.jungle.analysis.core;


import com.jungle.analysis.handle.AmbiguityHandle;
import com.jungle.analysis.handle.SimpleAmbiguityHandle;
import com.jungle.analysis.util.AnalysisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAnalysis implements MyAnalysis {

    protected final List<String> MISS_WORDS = new ArrayList<>();

    protected static final List<AmbiguityHandle> AMBIGUITY_HANDLES = new ArrayList<>();
    protected final List<MyAnalysis> SUB_ANALYSIS = new ArrayList<>();

    static {
        AMBIGUITY_HANDLES.add(new SimpleAmbiguityHandle());
    }

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
        List<String> result = handleAnalysis(in);
        List<String> subResult = SUB_ANALYSIS.stream()
                .flatMap(data -> data.cut(in).stream())
                .collect(Collectors.toList());
        result.addAll(subResult);
        return result;
    }

    protected abstract List<String> handleAnalysis(String in);

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
    public final List<String> export(List<String> words) {
        List<String> result = new ArrayList<>();
        words = handleClassifier(words);
        List<String> missWord = handleMissWord();
        result.addAll(words);
        result.addAll(missWord);
        return result;
    }

    protected void registerSubAnalysis(MyAnalysis analysis) {
        SUB_ANALYSIS.add(analysis);
    }

    @Override
    public final List<String> cut(String in) {
        loadDictionary();
        String pretreatment = pretreatment(in);
        List<String> analysis = analysis(pretreatment);
        List<String> handleAmbiguity = handleAmbiguity(analysis);
        return export(handleAmbiguity);
    }

    protected abstract List<String> handleMissWord();

    protected abstract List<String> handleClassifier(List<String> words);

    protected abstract void handlePrepareWords(String in);

    protected abstract void loadClassifier(String uri);

    protected abstract void loadStop(String uri);

    protected abstract void loadMain(String uri);

    protected abstract String getMainDictionaryUri();

    protected abstract void setMainDictionaryUri(String mainDictionaryUri);

    protected abstract String getStopDictionaryUri();

    public abstract void setStopDictionaryUri(String stopDictionaryUri);

    public abstract String getClassifierDictionaryUri();

    public abstract void setClassifierDictionaryUri(String classifierDictionaryUri);
}
