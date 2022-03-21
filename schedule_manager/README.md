# 原理

## 自动注入定时任务

```mermaid
graph TB
 A(获取配置管理器MyScheduledConfig) --> B(获取当前实例化完成的Bean的所有方法)
 B -->|逐一处理| C(尝试在方法上获取Scheduled注解)
 B -->|所有方法处理完毕| D(正常返回Bean对象)
 C-->|无法获取到Scheduled注解| E(跳过当前方法)
 C-->|无法获取到Scheduled注解| F(创建定时任务的元数据 并且保存到配置管理器中)
 F-->G(将原本的SpringBoot的定时任务取消)
```

### 所用到的组件

- ApplicationContextAware 获取SpringBoot的上下文
- BeanPostProcessor 标记后置处理器，在每个Bean实例化之后进行后置处理
- @DependsOn 强制依赖 MyScheduledConfig

### Corn 表达式说明

## 注入自定义定时任务运行器

``` mermaid
graph TB
A(定时任务配置管理器中缓存定时任务执行线程)-->B(获取所有定时任务源数据 逐一处理定时任务)
B-->|逐一处理| D(获取定时任务源数据)
B-->|所有方法处理完毕| C(正常返回Bean对象)
D-->E(获取所有增强类)
E-->F(创建并初始化执行控制器)
F-->G(处理增强类)
G-->H(将执行逻辑封装并缓存到定时任务配置管理器中)
H-->I(启动定时任务 将线程回调钩子存到任务配置管理器中)
```

### 所用到的组件

- ApplicationContextAware 获取SpringBoot上下文
- ApplicationRunner 在Bean初始化之后执行自定义逻辑
- @DependsOn 强制依赖 threadPoolTaskScheduler