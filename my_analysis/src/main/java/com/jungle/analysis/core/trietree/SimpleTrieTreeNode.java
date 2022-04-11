package com.jungle.analysis.core.trietree;

import cn.hutool.core.collection.CollectionUtil;
import com.jungle.analysis.core.SimpleAnalysis;
import com.sun.tools.javac.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleTrieTreeNode {
    private final List<SimpleTrieTreeNode> nodes = new ArrayList<>();
    private String key;
    private boolean isWord;

    public void add(String word) {
        if (word != null && word.length() > 0) {
            String nextWord = word.substring(1);
            String key = word.substring(0, 1);
            Optional<SimpleTrieTreeNode> treeNode = nodes.stream()
                    .filter(data -> data.key.equals(key))
                    .findFirst();
            if (treeNode.isPresent()) {
                treeNode.get().add(nextWord);
            } else {
                SimpleTrieTreeNode node = new SimpleTrieTreeNode();
                node.key = key;
                if (nextWord.length() == 0) {
                    node.isWord = true;
                }
                nodes.add(node);
                node.add(nextWord);
            }
        } else {
            this.isWord = true;
        }
    }

    public SimpleTrieTreeNode getNode(String word) {
        if (word != null && word.length() > 0 && !CollectionUtil.isEmpty(nodes)) {
            String nextWord = word.substring(1);

            String key = word.substring(0, 1);
            Optional<SimpleTrieTreeNode> node = nodes.stream()
                    .filter(data -> data.key.equals(key))
                    .findFirst();
            if (node.isPresent()) {
                if (nextWord.length() <= 0) {
                    return node.get();
                }
                return node.get().getNode(nextWord);
            }
        }
        return new SimpleTrieTreeNode();
    }

    public boolean checkWord(String word) {
        if (!CollectionUtil.isEmpty(nodes)) {
            SimpleTrieTreeNode node = getNode(word);
            System.out.println("Check word：" + word + ", Result:" + node.isWord);
            return node.isWord;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SimpleTrieTreeNode{" +
                "nodes=" + nodes +
                ", key='" + key + '\'' +
                ", isWord=" + isWord +
                '}';
    }
}
