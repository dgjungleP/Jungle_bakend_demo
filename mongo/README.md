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

### Capped collection

> - 他有很高的性能以及队列过期的特性
> - 必须显示创建，并指定大小
> - 创建指令`db.creatCollectioin("mycoll",{caped:true,size:100000}`
> - 数据库不允许删除，只能使用`drop()`方法删除所有行
> - 删除之后必须显示重新创建这个collection

### 元数据

> 指令`{dbname}.system.*`

| 集合空间 | 含义 |
| --- | --- |
|namespaces|所有名称空间|
|indexes|所有索引|
|profile|数据库概要信息|
|users|可访问数据库的用户|
|sources|包含复制对端的服务器信息和状态|

## How to use MongoDB

### 一、Simple CURD

#### database

- use `use {databasaName}`

> - 当数据库存在时为选择数据库，否则为创建数据
> - 创建数据库之后不会马上出现在数据库列表中，直到我们向其中插入数据

- drop `db.dropDatabase()`

#### collection

- find `db.{colletionName}.find()`

- insert  `db.{collectionName}.insert({...})` || save `db.{collectionName}.save({...})`

> - 在不指定_id的情况下`save`和`insert`效果是类似的，指定了_id字段时`save`会更新该_id

- remove `db.{collectionName}.remove(<query>,{jsutOne: <boolean>,writeConcern:<document>})`

> - query(可选):删除文档的条件
> - justOne(可选):只删除一个文档
> - writeConcern(可选):抛出异常的级别

- update `db.{collectionName}.remove(<query>,<update>,{upsert: <boolean>,multi,writeConcern:<document>})`

> - query:更新文档的条件
> - update:update的对象和一些更新`操作符`
> - upsert(可选):不存在更新记录的时候插入
> - multi(可选):按条件查出的数据全部更新或者第一条更新
> - writeConcern(可选):抛出异常的级别

- drop `db.{collectionName}.drop()`

### 二、Other CURD
- find `db.{colletionName}.find(<query document>, <projection document>)`
> - \<fied\>:1 包含该字段
> - \<fied\>:0 不包含该字段

- aggregate `db.{colletionName}.aggregate()`
## 常用的数据类型

|数据类型|描述|
|---|---|
|String |字符串。存储数据常用的数据类型。在 MongoDB 中，UTF-8 编码的字符串才是合法的。|
|Integer| 整型数值。用于存储数值。根据你所采用的服务器，可分为 32 位或 64 位。|
|Boolean| 布尔值。用于存储布尔值（真/假）。|
|Double| 双精度浮点值。用于存储浮点值。|
|Min/Max keys| 将一个值与 BSON（二进制的 JSON）元素的最低值和最高值相对比。|
|Arrays | 用于将数组或列表或多个值存储为一个键。|
|Timestamp|时间戳。记录文档修改或添加的具体时间。|
|Object |用于内嵌文档。|
|Null| 用于创建空值。|
|Symbol| 符号。该数据类型基本上等同于字符串类型，但不同的是，它一般用于采用特殊符号类型的语言。|
|Date| 日期时间。用 UNIX 时间格式来存储当前日期或时间。你可以指定自己的日期时间：创建 Date 对象，传入年月日信息。|
|Object ID|对象 ID。用于创建文档的 ID。|
|Binary Data | 二进制数据。用于存储二进制数据。|
|Code |代码类型。用于在文档中存储 JavaScript 代码。|
|Regular expression | 正则表达式类型。用于存储正则表达式。|

## 链接
- [查询符号](https://docs.mongodb.com/manual/reference/operator/query/#std-label-query-projection-operators-top)
## Tips

- Mongo 中不支持表连接
- 数据存储格式为BSON格式
