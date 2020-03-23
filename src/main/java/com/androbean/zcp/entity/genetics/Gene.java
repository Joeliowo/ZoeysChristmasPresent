package com.androbean.zcp.entity.genetics;

public class Gene {
    public String name;
    public float value;

    public Gene(String name, float value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void addValue(float value){
        this.value += value;
    }
}
