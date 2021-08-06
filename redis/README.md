# Redis

## setup redis cluster 

```
helm install redis bitnami/redis-cluster --values values.yaml
```

## data structure

当使用 zipList 来代替 string 类型的 hash 时，100万条数据，内存使用从 70m 左右降到了 10m 左右。
```java
    /**
     * 1000000 number cost 70m when use the string
     */
    @Test
    public void string() {
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForValue().multiSet(buildImageId());
        }
        System.out.println("watch redis memory usage");
    }

    /**
     * use zip list to replace string hash value
     *
     * 1000000 number cost 10m when use the zip list
     */
    @Test
    public void zipList() {
        Map<String, String> map = buildImageIds();
        Map<String, Map<String, String>> values = new HashMap<>();
        map.forEach((key, value) -> {
            String imageId = key.substring(0, 7);
            String storeId = key.substring(7, 10);
            Map<String, String> storeIdMap;
            if (values.containsKey(imageId)) {
                storeIdMap = values.get(imageId);
            } else {
                storeIdMap = new HashMap<>();
            }
            storeIdMap.put(storeId, value);
            values.put(imageId, storeIdMap);
        });
        values.forEach((key, value) -> {
            redisTemplate.opsForHash().putAll(key, value);
        });
        System.out.println("watch redis memory usage");
    }
```

### bitmap
当使用 bitmap 类型来保存用户的活跃状态时，100w 条数据，一天需要的内存只需 130k
init data lua scripts
```shell
local trueAndFalse = {true, false}

for j=1,5,1 do
    for i=1,1000000,1 do
        if trueAndFalse[math.random(1, 2)] then
            redis.call('SETBIT',j,i,1)
        else
            redis.call('SETBIT',j,i,0)
        end
    end
end
```
code
```java
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    public void count(String key) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        long count = bitSet.cardinality();
        System.out.println("size: " + bitSet.size());
        System.out.println("count: " + count);
        System.out.println("sizeInMemory: " + bitSet.sizeInMemory());
    }
```
result 
```shell
size: 1000008
count: 500412
sizeInMemory: 131120
```

### hyperLogLog
当时用 hyperLogLog 类型来保存 100w 条用户的日活跃情况，一天需要的内存只需惊人的 14k
init data lua scripts
```shell
local trueAndFalse = {true, false}

for j=1,5,1 do
    for i=1,1000000,1 do
        if trueAndFalse[math.random(1, 2)] then
            redis.call('PFADD',j,i)
        end
    end
end

```
code
```java
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    public void count(String key) {
        RHyperLogLog rHyperLogLog = redissonClient.getHyperLogLog(key);
        long count = rHyperLogLog.count();
        System.out.println("count: " + count);
        System.out.println("sizeInMemory: " + rHyperLogLog.sizeInMemory());
    }
```
result
```shell
when key = 1
count = 4960560
sizeInMemory: 14384
```


