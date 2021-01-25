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



//as long as we cant consume kafka objects on recieve and we have to process them each minuet according to their send time we are gonna save them in a queue for later consumption




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
        //checking if we got any new objects from kafka
        if(objQueue.isEmpty()) {
            System.out.println("no given inputs in that minuet");
            return;
        }


        long totalVal=0;
        Obj currentObj;
        //getting the first object in queue
        {
            currentObj=objQueue.remove();
        }
        //checking if lastchecked date was initialized
        if(lastCheckedDate.getTime()==0) {
            lastCheckedDate.setTime(currentObj.getDate().getTime());
        }
        //changing the last checked time to last time+60 seconds so we can use that as a boundry for consuming objects
        lastCheckedDate.setTime(lastCheckedDate.getTime()+60000);

        //calculating total val
        while(objQueue.peek().getDate().compareTo(lastCheckedDate)<0) {
            totalVal+=objQueue.remove().getValue();
        }
        System.out.println("the total value of given objects in the processed minute is: "+totalVal);
    }

}
