package ibf.paf.inclass25.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

@Service
public class MessageService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void lpush(String id, String message) {

        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("id", id);
        jsonMessage.addProperty("message", message);

        ListOperations<String, String> listOps = redisTemplate.opsForList();
        
        
        listOps.leftPush("myqueue", jsonMessage.toString());

        System.out.println("----------- Successfully pushed message!");
    }


}
