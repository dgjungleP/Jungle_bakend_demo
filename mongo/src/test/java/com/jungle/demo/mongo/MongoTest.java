package com.jungle.demo.mongo;

import cn.hutool.core.io.FileUtil;
import com.jungle.demo.mongo.entity.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    public void testMongo() {
        Query query = new Query(Criteria.where("name").is("mysql"));
        List<TestCase> all = mongoTemplate.find(query, TestCase.class, "test");
        for (TestCase testCase : all) {
            System.out.println(testCase);
        }
    }

    @Test
    public void insertMongo() throws IOException {
        BufferedReader reader = FileUtil.getUtf8Reader("testMongo.json");
        while (reader.ready()) {
            System.out.println("reader.readLine() = " + reader.readLine());
        }

    }
}
