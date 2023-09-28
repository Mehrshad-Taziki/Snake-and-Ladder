package ir.sharif.math.bp99_1.snake_and_ladder.model;

import java.util.Random;


public class Dice {
    //done
    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    private int chanceOne;
    private int chanceTwo;
    private int chanceThree;
    private int chanceFour;
    private int chanceFive;
    private int chanceSix;

    public Dice() {
        this.chanceOne = 1;
        this.chanceTwo = 1;
        this.chanceThree = 1;
        this.chanceFour = 1;
        this.chanceFive = 1;
        this.chanceSix = 1;
    }

    public int getChanceOne() {
        return chanceOne;
    }

    public int getChanceTwo() {
        return chanceTwo;
    }

    public int getChanceThree() {
        return chanceThree;
    }

    public int getChanceFour() {
        return chanceFour;
    }

    public int getChanceFive() {
        return chanceFive;
    }

    public int getChanceSix() {
        return chanceSix;
    }

    public void setChanceOne(int chanceOne) {
        this.chanceOne = chanceOne;
    }

    public void setChanceTwo(int chanceTwo) {
        this.chanceTwo = chanceTwo;
    }

    public void setChanceThree(int chanceThree) {
        this.chanceThree = chanceThree;
    }

    public void setChanceFour(int chanceFour) {
        this.chanceFour = chanceFour;
    }

    public void setChanceFive(int chanceFive) {
        this.chanceFive = chanceFive;
    }

    public void setChanceSix(int chanceSix) {
        this.chanceSix = chanceSix;
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public void resetDice() {
        this.chanceOne = 1;
        this.chanceTwo = 1;
        this.chanceThree = 1;
        this.chanceFour = 1;
        this.chanceFive = 1;
        this.chanceSix = 1;
    }

    public int roll() {
        Random rand = new Random();
        int diceNum = rand.nextInt(this.chanceOne + this.chanceTwo + this.chanceThree + this.chanceSix + this.chanceFour + this.chanceFive);
        diceNum -= this.chanceOne;
        if (diceNum < 0) {
            return 1;
        }
        diceNum -= this.chanceTwo;
        if (diceNum < 0) {
            return 2;
        }
        diceNum -= this.chanceThree;
        if (diceNum < 0) {
            return 3;
        }
        diceNum -= this.chanceFour;
        if (diceNum < 0) {
            return 4;
        }
        diceNum -= this.chanceFive;
        if (diceNum < 0) {
            return 5;
        }
        diceNum -= this.chanceSix;
        if (diceNum < 0) {
            return 6;
        }
        return 0;
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negetive(it can be zero)
     */
    public void addChance(int number, int chance) {
        if (number == 1) {
            if (this.chanceOne + chance < 0) {
                this.chanceOne = 0;
            }
            this.chanceOne += chance;
            if (this.chanceOne > 8) {
                this.chanceOne = 8;
            }
        }
        if (number == 2) {
            if (this.chanceTwo + chance < 0) {
                this.chanceTwo = 0;
            }
            this.chanceTwo += chance;
            if (this.chanceTwo > 8) {
                this.chanceTwo = 8;
            }
        }
        if (number == 3) {
            if (this.chanceThree + chance < 0) {
                this.chanceThree = 0;
            }
            this.chanceThree += chance;
            if (this.chanceThree > 8) {
                this.chanceThree = 8;
            }
        }
        if (number == 4) {
            if (this.chanceFour + chance < 0) {
                this.chanceFour = 0;
            }
            this.chanceFour += chance;
            if (this.chanceFour > 8) {
                this.chanceFour = 8;
            }
        }
        if (number == 5) {
            if (this.chanceFive + chance < 0) {
                this.chanceFive = 0;
            }
            this.chanceFive += chance;
            if (this.chanceFive > 8) {
                this.chanceFive = 8;
            }
        }
        if (number == 6) {
            if (this.chanceSix + chance < 0) {
                this.chanceSix = 0;
            }
            this.chanceSix += chance;
            if (this.chanceSix > 8) {
                this.chanceSix = 8;
            }
        }
    }


    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {
        String details = String.format("1 with %d chance.\n2 with %d chance.\n3 with %d chance.\n4 with %d chance.\n5 with %d chance.\n6 with %d chance.", this.chanceOne, this.chanceTwo, this.chanceThree, this.chanceFour, this.chanceFive, this.chanceSix);
        return details;
    }
}
