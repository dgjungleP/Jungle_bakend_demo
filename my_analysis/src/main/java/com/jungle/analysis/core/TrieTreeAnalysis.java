package com.jungle.analysis.core;

import com.jungle.analysis.core.trietree.SimpleTrieTreeNode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TrieTreeAnalysis extends AbstractAnalysis {
    private final SimpleTrieTreeNode TREE_ROOT = new SimpleTrieTreeNode();

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
            if (startPost == endPost) {
                endPost = endPost - 1;
                startPost = endPost - limit;
            } else if (TREE_ROOT.checkWord(word)) {
                result.add(word);
                endPost = startPost;
                startPost = endPost - limit;
            } else {
                startPost++;
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
    protected void handlePrepareWords(String in) {

    }

    @Override
    protected void loadClassifier(String uri) {

    }

    @Override
    protected void loadStop(String uri) {

    }

    @Override
    protected void loadMain(String uri) {
        InputStream baseDic = this.getClass().getClassLoader().getResourceAsStream(uri);
        try (BufferedReader dicReader = new BufferedReader(new InputStreamReader(baseDic))) {
            String line;
            while ((line = dicReader.readLine()) != null) {
                String wordKey = line.split("\t")[0];
                TREE_ROOT.add(wordKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String getMainDictionaryUri() {
        return "dic.txt";
    }

    @Override
    protected void setMainDictionaryUri(String mainDictionaryUri) {

    }

    @Override
    protected String getStopDictionaryUri() {
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
