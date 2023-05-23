package org.example.calculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-23
 * description  :
 */
public class Calculator {
    public int calcSum(String path) throws IOException {

        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(path));
            Integer sum = 0;

            String line = null;

            while((line = br.readLine()) != null){
                sum += Integer.valueOf(line);
            }


            return sum;
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }finally{
            if(br != null){
                try{ br.close();}catch(Exception e){e.printStackTrace();}
            }
        }
    }
}
