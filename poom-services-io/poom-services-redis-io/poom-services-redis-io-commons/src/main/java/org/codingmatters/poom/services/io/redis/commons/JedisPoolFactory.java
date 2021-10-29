package org.codingmatters.poom.services.io.redis.commons;

import org.codingmatters.poom.services.support.Env;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class JedisPoolFactory {

    static public JedisPool fromEnv(String envPrefix) {
        JedisPoolConfig poolConfig = poolConfigFromEnv(envPrefix);

        return new JedisPool(
                poolConfig,
                Env.mandatory(envPrefix + "HOST").asString(),
                Env.mandatory(envPrefix + "PORT").asInteger()
        );
    }

    static public JedisPoolConfig poolConfigFromEnv(String envPrefix) {
        /*
        https://partners-intl.aliyun.com/help/doc-detail/98726.htm
        https://www.baeldung.com/jedis-java-redis-client-library
         */

        int maxTotal = Env.optional(envPrefix + "POOL_MAX_TOTAL")
                .orElse(new Env.Var("64")).asInteger();                                        // 64
        int maxIdle = Env.optional(envPrefix + "POOL_MAX_IDLE")
                .orElse(new Env.Var("" + maxTotal)).asInteger();                                // 64
        int minIdle = Env.optional(envPrefix + "POOL_MIN_IDLE")
                .orElse(new Env.Var("" + Math.round(maxIdle / 8))).asInteger();                 // 8

        long minEvictableIdleInSeconds = Env.optional(envPrefix + "POOL_MIN_EVICTABLE_IN_SECONDS")
                .orElse(new Env.Var("60")).asLong();                                            // 60
        long timeBetweenEvictionRunsInSeconds = Env.optional(envPrefix + "POOL_TIME_BETWEEN_EVICTION_RUNS_IN_SECONDS")
                .orElse(new Env.Var("" + Math.round(minEvictableIdleInSeconds / 2))).asLong();  // 30

        int numTestsPerEvictionRun = Env.optional(envPrefix + "POOL_NUM_TESTS_PER_EVICTION_RUN")
                .orElse(new Env.Var("3")).asInteger();                                           // 3

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(minEvictableIdleInSeconds).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(timeBetweenEvictionRunsInSeconds).toMillis());
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
