# nacos
* 配置环境变量
```
NACOS_HOME=D:\tools\nacos\nacos-server-2.5.1\nacos
```
* 启动
```
startup.cmd -m standalone
```

* 访问
```
http://127.0.0.1:8848/nacos
```

* 问题

  ** nacos3.1.0启动报如下错误

* Nacos 服务注册
```
命名空间（Namespace） > 分组（Group） > 服务名（ServiceName）

即使服务名相同，如果 命名空间（Namespace）或分组（Group）不同，则会被视为两个不同的服务。

两服务要相互调用，必须在相同的命名空间（Namespace）和分组（Group）下才可以。
```
# Sentinel
* Sentinel 启动
```
java -Dserver.port=8765 -Dproject.name=sentinel-dashboard -jar D:\tools\sentinel\sentinel-dashboard-1.8.9.jar
```

* Sentinel接管Feign
```
不要在 Feign配置中自定义Feign.Builder，否则Sentinel无法接管Feign
    @Bean
    public Feign.Builder decodeVoidFeignBuilder() {
        return Feign.builder().decodeVoid();
    }
```