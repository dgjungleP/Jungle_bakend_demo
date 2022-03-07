package com.jungle.dashbord.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.Binary;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@Accessors(chain = true)
public class UploadFile {
    @Id
    private String id;

    private String name;

    private Instant createTime;

    private Binary content;

    private String contentType;

    private long size;

}
