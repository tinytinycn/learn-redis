# redis set 集合

Redis set 是通过哈希映射表实现的，所以它的添加、删除、查找操作的时间复杂度为 O(1)。集合中最多可容纳 2^32 - 1 个元素。

- 当集合中最后一个成员被删除时，存储成员所用的数据结构也会被自动删除。
- 集合有一个非常重要的特性就是“自动去重”。

## 底层存储结构

底层存储结构，分别是 intset（整型数组）与 hash table（哈希表）

- 当 set 存储的数据满足以下要求时，使用 intset 结构：
    * 集合内保存的所有成员都是整数值；
    * 集合内保存的成员数量不超过 512 个。
- 当不满足上述要求时，则使用 hash table 结构。

## 应用场景

- 去重
- 无序集合