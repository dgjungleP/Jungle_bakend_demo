## Executor组件

- Runnable/Callable
- Executor
- Future

## 一些特殊的术语

- AQS
- CAS
- 池化

> 减少获取资源的消耗，提高对资源的利用率

- this逃逸问题

> 在构造函数返回之前，其他线程就持有该对象引用，调用尚未构造完全的对象方法，可能会引起令人疑惑的错误

- 上下文切换成本
    - CPU 密集型任务（N+1）:内存大量操作
    - I/O密集型任务（2N）:网络读取，文件读取等