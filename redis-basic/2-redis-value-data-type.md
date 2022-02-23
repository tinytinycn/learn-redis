# redis数据类型

redis 是Key-Value类型缓存型数据库，Value值提供一下数据类型的支持(非key值数据类型)：

- string 字符串
- hash 散列
- list 列表
- set 集合
- zset 有序集合
- HyperLoglog
- Stream

## string 字符串

基本数据类型，字符串是一组字节，具有二进制特性，长度已知，不有任何其他终止符决定，一个字符串类型的值最多能够存储512M的内容。
- `set key "hello redis"`
- `mset key1 val1 key2 val2`
- `mget key1 key2`

## hash 散列

散列是由字符串类型的field和value组成的映射表，可以理解为一个多个键值对的集合。一般被用来存储对象的多个属性。一个散列最多包含2^32-1个键值对。
- `hmset key username tiny password 123 address changsha`
- `mgetall key`

## list 列表

list 中的元素是字符串类型，其中元素按照插入顺序排列，允许重复插入，最多可插入2^32-1个。
- `lpush key java`
- `lpush key python`
- `lpush key c`
- `lrange key 0 6` // redis列表类型遵循索引机制，从前到后查询元素，输出为 c python java

## set 集合

set 是一个字符串类型元素构成的无序集合。集合是通过hash映射表实现的，操作元素的时间复杂度都为O(1)。集合的同样可容纳 2^32 -1 个元素。
- `sadd key html`
- `sadd key js`
- `sadd key css`
- `sadd key css` // 成员具有唯一性，所以重复插入 HTML 元素不会成功
- `smemebers key`

## zset 有序集合

zset 是一个字符串类型元素构成的有序集合。集合中的元素不仅具有唯一性，而且每个元素还会关联一 个 double 类型的分数，该分数允许重复。Redis 正是通过这个分数来为集合中的成员排序。
- `zadd key 0 java`
- `zadd key 1 python`
- `zadd key 2 c++`
- `zadd key 3 c++` // 若元素存在于集合中，则不能添加成功
- `zadd key 2 redis` // 添加成功
- `zscore key redis` // 查看分数
- `zrange key 0 4` // 查看所有成员