package org.codingmatters.poom.services.io.redis.repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

class RedisReadiness {

    static void waitUntilReady(Jedis jedis) throws InterruptedException {
        JedisConnectionException last = null;
        for (int attempt = 0; attempt < 100; attempt++) {
            try {
                if ("PONG".equalsIgnoreCase(jedis.ping())) return;
            } catch (JedisConnectionException e) {
                last = e;
            }
            Thread.sleep(100L);
        }
        throw new IllegalStateException("Redis did not accept connections within 10s", last);
    }
}