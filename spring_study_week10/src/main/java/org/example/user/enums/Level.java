package org.example.user.enums;

/**
 * title        : Level
 * author       : sim
 * date         : 2023-06-02
 * description  : User Level Enum Class
 */
public enum Level {

    PLATINUM(4, null),
    GOLD(3, PLATINUM),

    SILVER(2, GOLD),

    BASIC(1, SILVER);

    private final int value;
    private final Level nexeLevel;

    Level(int value, Level nexeLevel){
        this.value = value;
        this.nexeLevel = nexeLevel;
    }

    public int intValue(){
        return value;
    }

    public Level getNexeLevel(){ return nexeLevel; }
    public static Level valueOf(int value){
        switch (value){
            case 1 : return BASIC;
            case 2 : return SILVER;
            case 3 : return GOLD;
            case 4 : return PLATINUM;
            default: throw new AssertionError("Unknown value : "+value);
        }
    }
}
