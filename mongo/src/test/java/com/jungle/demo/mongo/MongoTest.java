package com.jungle.demo.mongo;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jungle.demo.mongo.entity.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;
    private static String COLLECTION_NAME = "test";


    @Test
    public void insertMongo() throws IOException {
        BufferedReader reader = FileUtil.getUtf8Reader("testMongo.json");
        StringBuilder builder = new StringBuilder();
        while (reader.ready()) {
            builder.append(reader.readLine());
        }
        JSONArray insertDemoJson = JSON.parseArray(builder.toString());
        for (Object o : insertDemoJson) {
            mongoTemplate.insert(o, COLLECTION_NAME);
        }
        System.out.println("Insert Done!!");
    }

    @Test
    public void selectAllMongo() {
        List<JSONObject> all = mongoTemplate.findAll(JSONObject.class, COLLECTION_NAME);
        for (JSONObject testCase : all) {
            System.out.println(testCase);
        }
        System.out.println("Select all Done!!");
    }

    @Test
    public void selectFilterMongo() {
        Query query = new Query(Criteria.where("directors").is("Christopher Nolan"));
        List<JSONObject> all = mongoTemplate.find(query, JSONObject.class, COLLECTION_NAME);
        for (JSONObject testCase : all) {
            System.out.println(testCase);
        }
        System.out.println("Select Done!!");
    }

}
