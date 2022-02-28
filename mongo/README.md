# [MongoDB](https://docs.mongodb.com/manual/) with [Spring](https://spring.io)

## The concept in Mongo

| 名词 | 含义 |  
| --- | --- |  
| database | 数据库 |  
| collection | 数据库表/集合 |  
| document | 数据记录行/文档 |  
| field | 数据记字段/域 |  
| index | 索引 |  
| primary key | 主键，自动将_id作为主键 |  

### Capped collectopm

> - 他有很高的性能以及队列过期的特性
> - 必须显示创建，并指定大小
> - 创建指令
    ``` shell db.creatCollectioin("mycoll",{caped:true,size:100000})
    ```
> 

## How to use MongoDB

## Tips

- Mongo 中不支持表连接
- 数据存储格式为BSON格式
