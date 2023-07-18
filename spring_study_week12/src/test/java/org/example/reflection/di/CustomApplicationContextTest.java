package org.example.reflection.di;

import org.example.reflection.Human;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.Assert.assertNotNull;


/**
 * title        :
 * author       : sim
 * date         : 2023-07-18
 * description  :
 */
class CustomApplicationContextTest {

    @Test
    void getInstance() throws Exception {

        TestService testService = CustomApplicationContext.getInstance(TestService.class);

        assertNotNull(testService.getRobot());
        testService.start();
    }
}