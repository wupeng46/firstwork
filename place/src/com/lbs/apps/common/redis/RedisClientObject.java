package com.lbs.apps.common.redis;

import com.lbs.commons.SpringContextUtil;

public class RedisClientObject {
	protected static RedisClientTemplate redis = (RedisClientTemplate) SpringContextUtil.getBean("redisClientTemplate");
	
	 /**set Object*/
    public String set(Object object,String key)
   {
          return redis.set(key.getBytes(), SerializeUtil.serialize(object));
   }
   
    /**get Object*/
    public Object get(String key)
   {
          byte[] value = redis.get(key.getBytes());
          return SerializeUtil.unserialize(value);
   }
   
    /**delete a key**/
    public boolean del(String key)
   {
          return redis.del(key)>0;
   }
}
