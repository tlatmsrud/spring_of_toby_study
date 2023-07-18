package org.example.reflection.di;

import org.example.reflection.Robot;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-18
 * description  :
 */
public class TestService {

    @SSKAutowired
    private Robot robot;

    public Robot getRobot(){
        return robot;
    }
    public void start(){
        robot.fight();
        robot.clean();
    }
}
