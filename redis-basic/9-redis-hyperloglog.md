# redis hyperloglog 基数统计

HyperLoglog 采用了一种基数估计算法，因此，最终得到的结果会存在一定范围的误差（标准误差为 0.81%）。每个 HyperLogLog key 只占用 12 KB 内存，所以理论上可以存储大约2^64个值，而 set（集合）则是元素越多占用的内存就越多，两者形成了鲜明的对比

基数定义：一个集合中不重复的元素个数就表示该集合的基数，比如集合 {1,2,3,1,2} ，它的基数集合为 {1,2,3} ，所以基数为 3。HyperLogLog 正是通过基数估计算法来统计输入元素的基数。

HyperLoglog 不会储存元素值本身，因此，它不能像 set 那样，可以返回具体的元素值。HyperLoglog 只记录元素的数量，并使用基数估计算法，快速地计算出集合的基数是多少。

```text
pfadd key user01 user02 user03
pfadd key2 user04 user05
pfcount key // 返回3
pfcount key2 // 返回2
pfadd key user01 // 重复元素添加不能成功
pfcount key // 返回3
pfmerge key key2 key3// 合并为一个
pfcount key3 // 返回5
```

## 应用场景
- 它非常适用于海量数据的计算、统计，其特点是占用空间小，计算速度快。
- 统计网站用户月活量，或者网站页面的 UV(网站独立访客)数据等