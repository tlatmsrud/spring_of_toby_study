package org.example.practice;

import java.io.*;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
public class Calculator1 {

    //1. 공통된 부분
    // BufferedReader 읽어와서 한줄씩 읽는부분
    // br.close 하는 부분

    // 2. 변하는 부분
    // 연산하는 부분 sum += Integer.valueOf(line)

    public Integer calcSum(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        Integer sum = 0;
        String line = null;

        while((line = br.readLine()) != null){
            sum += Integer.valueOf(line);
        }

        br.close();
        return sum;
    }
}
