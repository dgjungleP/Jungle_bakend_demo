# JMH\(Java微基准测试工具\)

## 依赖

```` xml
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.23</version>
</dependency>
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-generator-annprocess</artifactId>
    <version>1.23</version>
</dependency>
````

> 说明：在JDK9以上版本自带。

## 套件

### @Benchmark

> 指定基本测试方法

### @BenchmarkMode

> 配置运行类型

### @State

> 配置对象范围

### @OutputTimeUnit

> 统计时间单位

### @Warmup

> 预热配置的基本测试参数

### @Measurement

> 调用方法基本测试参数

### @Threads

> 测试线程

### @Fork

> 进行fork的配置

### @Param

> 特殊参数配置

## 结果可视化网站

- [JMH Visual Chart](http://deepoove.com/jmh-visual-chart/)
- [JMH Visualizer](https://jmh.morethan.io/)
