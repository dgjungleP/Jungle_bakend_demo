package com.jungle.demo.mongo;

import com.jungle.demo.mongo.entity.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
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
}
