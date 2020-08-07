package edu.gatech.seclass.wordfind6300;

public class Letter {
    private String letterValue;
    private int weight;
    private int pointValue;
    private boolean isVowel;

    Letter(String lv,int w, int pv, boolean iv) {
        this.setLetterValue(lv);
        this.setWeight(w);
        this.setPointValue(pv);
        this.setVowel(iv);
    }

    public String getLetterValue() {
        return letterValue;
    }

    public void setLetterValue(String letterValue) {
        this.letterValue = letterValue;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public boolean isVowel() {
        return isVowel;
    }

    public void setVowel(boolean vowel) {
        isVowel = vowel;
    }

    // print out for test
    @Override
    public String toString() {
        return ("letterValue: "+this.getLetterValue()+
                " weight: "+ this.getWeight() +
                " pointValue: "+ this.getPointValue() +
                " isVowel : " + this.isVowel());
    }
}
