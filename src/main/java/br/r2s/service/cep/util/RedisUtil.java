package br.r2s.service.cep.util;

import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;

/**
 * Created by raphael on 15/03/16.
 */
public class RedisUtil {

    private static RedisUtil _instancia;
    Jedis jedis;
    String hostPadrao = "server-r2s-1.cloudapp.net";
    int portPadrao = 6379;

    HashSet<String> sentinels = new HashSet<String>();
    String masterName = "mymaster";

    JedisSentinelPool jedisPool;

    public RedisUtil(){
        /*sentinels.add("127.0.0.1:1520");
        sentinels.add("127.0.0.1:1521");*/
    }

    public static RedisUtil getInstancia(){
        if(_instancia == null){
            _instancia = new RedisUtil();
        }
        return _instancia;
    }

    private JedisSentinelPool getJedisPool() {
        if(jedisPool==null&&!sentinels.isEmpty()&&!StringUtils.isEmpty(masterName)){
            this.jedisPool = new JedisSentinelPool(masterName, sentinels);
        }
        return jedisPool;
    }

    public Jedis getJedis() {
        try{
            if(this.jedis==null){
                String host;
                int port;
                JedisSentinelPool jedisSentinelPool = this.getJedisPool();
                if(jedisSentinelPool!=null&&jedisSentinelPool.getCurrentHostMaster()!=null ){
                    HostAndPort hostAndPort = jedisSentinelPool.getCurrentHostMaster();
                    host = hostAndPort.getHost();
                    port = hostAndPort.getPort();
                }else{
                    host = hostPadrao;
                    port = portPadrao;
                }

                this.jedis = new Jedis(host, port);
                this.jedis.connect();;
            }
        }catch (Exception e){
            jedis = null;
        }
        return jedis;
    }

    public String get(String key){
        return this.getJedis().get(key);
    }

    public void set(String key, String value){
        this.getJedis().set(key, value);
    }

    public boolean isConnect(){
        boolean connect = false;
        if(this.getJedis()!=null){
            connect = getJedis().isConnected();
        }
        return connect;
    }
}
