package com.jungle.analysis.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleAnalysis extends AbstractAnalysis {

    private final Map<String, WordsDefinition> DICTIONARY = new HashMap<>();

    @Override
    protected List<String> handleAnalysis(String in) {
        List<String> result = new ArrayList<>();
        int limit = 5;
        int startPost = in.length() - limit;
        int endPost = in.length();
        while (startPost > -limit && endPost > 0) {
            if (startPost < 0) {
                startPost = 0;
            }
            String word = in.substring(startPost, endPost);
            WordsDefinition definition = DICTIONARY.get(word);
            if (startPost == endPost) {
                endPost = endPost - 1;
                startPost = endPost - limit;
            } else if (definition == null) {
                startPost++;
            } else {
                result.add(definition.word);
                endPost = startPost;
                startPost = endPost - limit;
            }
        }
        return result;
    }

    @Override
    protected List<String> handleMissWord() {
        return MISS_WORDS;
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
        InputStream baseDic = this.getClass().getClassLoader().getResourceAsStream(uri);
        try (BufferedReader dicReader = new BufferedReader(new InputStreamReader(baseDic))) {
            String line;
            while ((line = dicReader.readLine()) != null) {
                String wordKey = line.split("\t")[0];
                DICTIONARY.put(wordKey, WordsDefinition.read(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public String getMainDictionaryUri() {
        return "dic.txt";
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

    private static class WordsDefinition {
        private String word;

        public static WordsDefinition read(String line) {
            WordsDefinition definition = new WordsDefinition();
            definition.word = line.split("\t")[0];
            return definition;
        }
    }
}
