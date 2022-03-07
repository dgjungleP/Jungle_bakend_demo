package com.jungle.dashbord.controller;


import com.jungle.dashbord.entity.UploadFile;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.PublicKey;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/file/")
public class FileController {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String filePath = "http://localhost:8080/file/image/";
        try {
            UploadFile uploadFile = new UploadFile().setName(file.getOriginalFilename())
                    .setCreateTime(Instant.now())
                    .setContent(new Binary(file.getBytes()))
                    .setContentType(file.getContentType())
                    .setSize(file.getSize());
            UploadFile saveFile = mongoTemplate.save(uploadFile);
            filePath += saveFile.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(filePath);
    }

    @GetMapping(value = "image/{id}")
    public byte[] image(@PathVariable("id") String id) {
        byte[] data = null;
        UploadFile file = mongoTemplate.findById(id, UploadFile.class);
        if (file != null) {
            data = file.getContent().getData();
        }
        return data;
    }

    @GetMapping("list/image")
    public ResponseEntity<List<UploadFile>> listImage() {
        Query query = new Query();
        query.fields().exclude("content");
        List<UploadFile> list = mongoTemplate.find(query, UploadFile.class);
        return ResponseEntity.ok(list);
    }

}
