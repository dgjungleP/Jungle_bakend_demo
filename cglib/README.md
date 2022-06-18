# cglib简要阅读


## 模块分析
- net.sf.cglib.core: 底层字节码处理类。
- net.sf.cglib.transform: 编译期或运行期类和类文件的转换。
- net.sf.cglib.proxy: 实现创建代理和方法拦截器的类。
- net.sf.cglib.reflect: 实现快速反射和C#风格代理的类 。
- net.sf.cglib.util: 集合排序等工具类。
- net.sf.cglib.beans: JavaBean相关的工具类。

## 模块归类
- 底层核心部分
> net.sf.cglib.core
- API实现
> net.sf.cglib.proxy 
> net.sf.cglib.reflect 
- 工具类
> net.sf.cglib.transform  
> net.sf.cglib.util  
> net.sf.cglib.beans

## 基本原理
- 回调 CallBack

### 问题
- Cglib产生的字节码存放在哪里？

### 分析
