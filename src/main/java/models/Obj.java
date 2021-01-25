package models;

import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Component
@Getter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode @ToString
public class Obj {
    private int value;
    private Date date;
}
