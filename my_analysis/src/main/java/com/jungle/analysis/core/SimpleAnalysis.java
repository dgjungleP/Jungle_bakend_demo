package com.jungle.analysis.core;

import java.util.List;

public class SimpleAnalysis extends AbstractAnalysis {
    @Override
    protected List<String> handleMissWord(List<String> missWords) {
        return missWords;
    }

    @Override
    protected List<String> handleClassifier(List<String> words) {
        return words;
    }

    @Override
    public void handlePrepareWords(String in) {

    }

    @Override
    public void loadClassifier(String uri) {

    }

    @Override
    public void loadStop(String uri) {

    }

    @Override
    public void loadMain(String uri) {

    }

    @Override
    public String getMainDictionaryUri() {
        return null;
    }

    @Override
    public void setMainDictionaryUri(String mainDictionaryUri) {

    }

    @Override
    public String getStopDictionaryUri() {
        return null;
    }

    @Override
    public void setStopDictionaryUri(String stopDictionaryUri) {

    }

    @Override
    public String getClassifierDictionaryUri() {
        return null;
    }

    @Override
    public void setClassifierDictionaryUri(String classifierDictionaryUri) {

    }
}
