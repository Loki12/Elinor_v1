package com.sergejninzyy.Models;

public class DoubleField extends Field{

    public Field field_field;
    public Field second_Field;

    public DoubleField(Field field, Field second_Field) {
        this.field_field = field;
        this.second_Field = second_Field;
    }
}
