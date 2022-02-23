# redis key键

Redis 是一种键值（key-value）型的缓存型数据库，它将数据全部以键值对的形式存储在内存中，并且 key 与 value 一一对应。这里的 key 被形象的称之为密钥，Redis
提供了诸多操作这把“密钥”的命令，从而实现了对存储数据的管理。

## key 的类型

key 的类型对应着 value 的类型，同样也有五种（string、list、hash、set、zset）。 也就是说，如果 key 指向的是一个字符串类型的值，那么 key 的类型就是字符串。

```text
set key "tiny"
type key // 返回string，说明key是字符串类型

lpush key2 "java"
lpush key2 "python"
type key2 // 返回list，说明key是列表类型
```

## key 的命名规范

- key 取值不可以太长，否则会影响 value 的查找效率，并且浪费内存空间。
- key 取值也不能过短，否则会使得 key 可读性变差。

> Redis 官方建议使用“见名知意”的字符串格式

## key 的过期删除策略

Redis 允许你为 key 设置一个过期时间（使用 EXPIRE 等命令），也就是“到点自动删除”，这在实际业务中是非常有用的，一是它可以避免使用频率不高的 key 长期存在，从而占用内存资源；二是控制缓存的失效时间。

redis key 的过期后清理策略有如下几种:

- 立即清理
- 定时清理, 每个设置了过期时间的 key 存放到一个独立的字典中，并且会定时遍历这个字典来删除到期的 key
- 惰性清理, 当客户端访问这个 key 的时候，Redis 对 key 的过期时间进行检查，如果过期了就立即删除。

> 一般 Redis 使用定时清理、惰性清理两种方式相结合的方法来处理过期的 key

过期时间，有许多的应用场景，比如特定的时间节点推出相关的活动，在这种情景下就可以给 key 设置一个过期时间，从而减少无用数据占用内存资源。

## 内存淘汰策略

redis 内存淘汰策略是指达到 `maxmemory` 极限时，使用某种算法来决定来清理哪些数据，以保证新数据存入。

- noeviction 不处理（默认），发现内存不够时，不删除key，执行写入命令时直接返回错误信息。
- 从所有结果集中的key中挑选，进行淘汰。
  * allkeys-random 从所有的key中随机挑选key，进行淘汰
  * allkeys-lru 从所有的key中挑选最近使用时间距离现在最远的key，进行淘汰
  * allkeys-lfu 从所有的key中挑选使用频率最低的key，进行淘汰。（这是Redis 4.0版本后新增的策略）
- 从设置了过期时间的key中挑选，进行淘汰
  * volatile-random 从设置了过期时间的结果集中随机挑选key删除。
  * volatile-lru 从设置了过期时间的结果集中挑选上次使用时间距离现在最久的key开始删除
  * volatile-ttl 从设置了过期时间的结果集中挑选可存活时间最短的key开始删除(也就是从哪些快要过期的key中先删除)
  * volatile-lfu 从过期时间的结果集中选择使用频率最低的key开始删除（这是Redis 4.0版本后新增的策略）

