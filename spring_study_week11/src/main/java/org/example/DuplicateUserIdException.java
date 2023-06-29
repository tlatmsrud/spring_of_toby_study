package org.example;

/**
 * title        :
 * author       : sim
 * date         : 2023-05-28
 * description  :
 */
public class DuplicateUserIdException extends RuntimeException{

    public DuplicateUserIdException(){

    }
    public DuplicateUserIdException(String message){
        super(message);
    }

    public DuplicateUserIdException(Exception e){
        super(e);
    }

    public DuplicateUserIdException(Exception e, String message){
        super(message, e);
    }
}
