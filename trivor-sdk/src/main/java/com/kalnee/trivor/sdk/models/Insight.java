package com.kalnee.trivor.sdk.models;

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

    public void setCode(String code) {
        this.code = code;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Insight{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}';
    }
}
