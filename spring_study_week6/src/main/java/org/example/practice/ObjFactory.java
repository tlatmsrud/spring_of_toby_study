package org.example.practice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
@Configuration
public class ObjFactory {

    @Bean
    public Calculator2 calculator2(){
        Calculator2 calculator2 = new Calculator2();
        calculator2.setBufferedReaderTemplate(bufferedReaderTemplate());
        return calculator2;
    }

    @Bean
    public BufferedReaderTemplate bufferedReaderTemplate(){
        return new BufferedReaderTemplate();
    }
}
