# redis PubSub发布订阅

Redis PubSub 模块又称发布订阅者模式，是一种消息传递系统，实现了消息多播功能。发布者（即发送方）发送消息，订阅者（即接收方）接收消息，而用来传递消息的链路则被称为 channel。在 Redis 中，一个客户端可以订阅任意数量的 channel（可译为频道）。

> 消息多播：生产者生产一次消息，中间件负责将消息复制到多个消息队列中，每个消息队列由相应的消费组进行消费，这是分布式系统常用的一种解耦方式。

-  客户端订阅/等待接收消息
```text
subscribe mychannel // 订阅 mychannel 的频道，处于等待接收消息的阻塞状态
```

- 服务端发布消息
```text
publish mychannel hello
publish mychannel world
```

- 客户端成功接收消息
```text
hello
world
```


