package com.kalnee.trivor.nlp.nlp.models;

public class Insight<T> {

    private String code;
    private T value;

    public Insight() {
    }

    public Insight(String code, T value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Insight{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}';
    }
}
