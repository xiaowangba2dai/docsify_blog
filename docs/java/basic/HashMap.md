## HashMap添加元素分析

在HashMap中，当添加元素时，会通过哈希值和数组的长度来计算得出一个`下标`。

```java
index = hashCode % arr.length // 实际并非这样
```

HashMap为了实现存取高效，尽量减少碰撞，需要把数据分配均匀，保证每隔链表长度大致相同，需要一个算法实现均匀计算下标, 就是`hash % length`。

但是，这种运算不如位移运算计算块，因此，源码中做了优化`hash&(length-1)`。

并且 `hash % length == hash & (length-1)`

## 为什么要在链表长度>=8的时候变成红黑树呢？

如果链表的长度没有达到这个长度，红黑树维护的开销非常大（每次插入一个元素可能破环红黑树的平衡，当插入操作非常多的时候，影响插入的性能）。

当put进来一个元素，通过hash算法定位到同一个链表的概率会随着链表的长度的增加而减少，当这个链表的长度为8的时候，这个概率几乎接近于0，所以我们才会将链表转为红黑树。


## 为什么数组的长度必须是2的指数次幂

hashmap中数组的初始长度（默认为16, 1 << 4）

```java
/**
* The default initial capacity - MUST be a power of two.
* 默认初始容量-必须是2的幂
*/
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
```

最大容量：1 << 30
```java
/**
* The maximum capacity, used if a higher value is implicitly specified
* by either of the constructors with arguments.
* MUST be a power of two <= 1<<30.
*/
static final int MAXIMUM_CAPACITY = 1 << 30;
```

**如果我们传入的初始容量不是2的指数次幂，它就会将这个数改成大于该数的最接近这个数的2的指数次幂**。

当我们使用构造方法传进来一个初始容量的值`initialCapacity`，默认会传入一个加载因子0.75。
```java
public HashMap(int initialCapacity) {
	this(initialCapacity, DEFAULT_LOAD_FACTOR); // 0.75
}
```
继续追踪分析代码：

```java
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    this.threshold = tableSizeFor(initialCapacity); // 重点看这一行
}
```
继续看看`tableSizeFor(initialCapacity)`处理初始容量的方法

```java
/**
 * Returns a power of two size for the given target capacity.
 * 返回一个大于输入参数，且最接近的2的整数次幂的数
 */
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

**来到题目问题，为什么长度必须是2的n次方？**

前面提到，`hash % length == hash & (length-1)`

我们要得到的下标的范围是0~length-1, 如果hash与length-1进行&操作，就不会出现碰撞了。

比如16，32，64。。。

寻址算法是 hash & (table.length-1)

table.length转化为二进制后，一定是（1）是高位，地位全是（0）.

table.length-1 之后转换为二进制变成地位全是（1），高位（0）

任何数与这种高位全部为0，地位都是1的数，按位与之后得到的这个数值一定是>=0而且<=这个二进制数的，加起来正好等于2的幂。

## 为什么初始容量要转为2的指数次幂？

首先来看HashMap的put方法
```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
```

其中的hash方法计算key的哈希值，这里key.hashcode高16位异或低16位得到。
```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

**为什么要先高16位异或低16位**

这么做，是为了降低hash冲突的几率。

这样做保留了key.hashcode的高位和地位的信息，表现目标值的特征，减少碰撞。

因为hashmap通常不会太大。

也就是说length-1实际位数很少，一般都在低16位，这样key.hashcode高16位就浪费了。

最后来看`putVal方法，它是实现具体的put操作的方法。`
```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    HashMap.Node<K,V>[] tab;
    HashMap.Node<K,V> p;
    int n, i;

    // 1. 如果当前table为空，新建默认大小的table，n = 16
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;

    // 2. 用(key.hashcode & table.length-1)得到下标i
    if ((p = tab[i = (n - 1) & hash]) == null)   // 如果该下标没有值，直接存新的值
        tab[i] = newNode(hash, key, value, null);
    else { // 该位置已经有值了
        HashMap.Node<K,V> e;
        K k;
        // 如果key的哈希相同，则覆盖
        if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // 如果当前节点是一个红黑树节点，则添加树节点
        else if (p instanceof TreeNode)
            e = ((HashMap.TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 不是相同节点，也不是红黑树节点，就是链表结构
            for (int binCount = 0; ; ++binCount) {
                // 找到最后哪个节点
                if ((e = p.next) == null) {
                    // 新建一个节点并连接在最后面
                    p.next = newNode(hash, key, value, null);
                    // 如果链表长度超过8，转化为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                // 如果链表中有相同节点，则覆盖
                if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }

        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    // 记录修改次数
    ++modCount;

    // 如果size+1 > 阈值，进行扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

## 了解：解决Hash冲突四个方法

（1）开放定址法，也称再散列法（以冲突的下标为基础再次计算hash，直到不冲突）

（2）重Hash法（使用多个hash函数，直到不冲突）

（3）链地址法（将所有哈希地址为i的元素构成一个单链表）

（4）建立公共溢出区（将哈希表分为公共表和溢出表，当溢出发生时，将所有溢出数据统一放到溢出区）

