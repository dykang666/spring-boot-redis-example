1 、 reids 主要用于以下几个方面  
缓存（@Cacheable、@CachePut、@CacheEvict）、

分布式锁（redissonClient的tryLock） 场景 如：扣减库存等

统计签到(计数器（带时间）、setBit（Bitmap）或者increment)， increment 的场景 例如：  浏览量、视频网站视频的播放数、验证码发送限制 ，点赞，关注； setBit的场景  用于统计一个月、一周或者一年的较多。

排行榜应用（Zset） 例如：月度销量榜单、商品按时间的上新排行榜

分布式会话（ @EnableRedisHttpSession注解）服务，例如：当应用增多相对复杂的系统中，一般都会搭建以Redis等内存数据库为中心的session服务，session不再由容器管理，而是由session服务及内存数据库管理，以及token的储存。  

2、  参考以下链接，大致相同 ；src的test文件夹下不同

https://github.com/fengcheZt/spring-boot-redis-application-demo/tree/master
