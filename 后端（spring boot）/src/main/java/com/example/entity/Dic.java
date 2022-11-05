package com.example.entity;

public class Dic {
    private String word;

    private String meaning;

    private String enAccent;

    private String usAccent;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning == null ? null : meaning.trim();
    }

    public String getEnAccent() {
        return enAccent;
    }

    public void setEnAccent(String enAccent) {
        this.enAccent = enAccent == null ? null : enAccent.trim();
    }

    public String getUsAccent() {
        return usAccent;
    }

    public void setUsAccent(String usAccent) {
        this.usAccent = usAccent == null ? null : usAccent.trim();
    }
}