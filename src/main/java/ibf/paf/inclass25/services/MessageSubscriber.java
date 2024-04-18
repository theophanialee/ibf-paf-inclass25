package ibf.paf.inclass25.services;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import io.micrometer.common.lang.Nullable;

@Service
public class MessageSubscriber implements MessageListener {

   @Override
   public void onMessage(Message message, @Nullable byte[] pattern) {

      String data = new String(message.getBody());
      System.out.printf(">>>> message: %s\n", data);
      
   }
   
}
