package controls;

import models.Obj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@EnableScheduling
@Controller
@ComponentScan
public class ListeningController {

    @KafkaListener(topics = "objects container")
    public Obj objReceiver(Obj obj) {
        return obj;
    }

    @Bean
    public Date createDate() {
        return new Date(0);
    }

    Queue<Obj> objQueue=new LinkedList<>();

    @Autowired Date lastCheckedDate;

    @Scheduled(fixedRate = 60000)
    void consume () {
        if(objQueue.isEmpty())
            return;

        Obj currentObj=objQueue.remove();
        if(lastCheckedDate.getTime()==0) {
            lastCheckedDate.setTime(currentObj.getDate().getTime());
        }
    }

}
