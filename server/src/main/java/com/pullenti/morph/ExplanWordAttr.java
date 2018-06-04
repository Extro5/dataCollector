/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Дополнительные характеристики слова
 */
public class ExplanWordAttr {

    public ExplanWordAttr(ExplanWordAttr val) {
        value = (short)0;
        if (val != null) 
            value = val.value;
    }

    public short value;

    private boolean getValue(int i) {
        return ((((((int)value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            value |= ((short)((1 << i)));
        else 
            value &= ((short)(~((1 << i))));
    }

    /**
     * Неопределённый тип
     */
    public boolean isUndefined() {
        return value == ((short)0);
    }

    /**
     * Неопределённый тип
     */
    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


    /**
     * Одушевлённое
     */
    public boolean isAnimated() {
        return getValue(0);
    }

    /**
     * Одушевлённое
     */
    public boolean setAnimated(boolean _value) {
        setValue(0, _value);
        return _value;
    }


    /**
     * Может иметь собственное имя
     */
    public boolean isNamed() {
        return getValue(1);
    }

    /**
     * Может иметь собственное имя
     */
    public boolean setNamed(boolean _value) {
        setValue(1, _value);
        return _value;
    }


    /**
     * Может иметь номер (например, Олимпиада 80)
     */
    public boolean isNumbered() {
        return getValue(2);
    }

    /**
     * Может иметь номер (например, Олимпиада 80)
     */
    public boolean setNumbered(boolean _value) {
        setValue(2, _value);
        return _value;
    }


    /**
     * Может ли иметь числовую характеристику (длина, количество, деньги ...)
     */
    public boolean isMeasured() {
        return getValue(3);
    }

    /**
     * Может ли иметь числовую характеристику (длина, количество, деньги ...)
     */
    public boolean setMeasured(boolean _value) {
        setValue(3, _value);
        return _value;
    }


    /**
     * Позитивная окраска
     */
    public boolean isEmoPositive() {
        return getValue(4);
    }

    /**
     * Позитивная окраска
     */
    public boolean setEmoPositive(boolean _value) {
        setValue(4, _value);
        return _value;
    }


    /**
     * Негативная окраска
     */
    public boolean isEmoNegative() {
        return getValue(5);
    }

    /**
     * Негативная окраска
     */
    public boolean setEmoNegative(boolean _value) {
        setValue(5, _value);
        return _value;
    }


    /**
     * Это животное, а не человек (для IsAnimated = true)
     */
    public boolean isAnimal() {
        return getValue(6);
    }

    /**
     * Это животное, а не человек (для IsAnimated = true)
     */
    public boolean setAnimal(boolean _value) {
        setValue(6, _value);
        return _value;
    }


    /**
     * За словом может быть персона в родительном падеже (слуга Хозяина, отец Ивана ...)
     */
    public boolean isCanPersonAfter() {
        return getValue(7);
    }

    /**
     * За словом может быть персона в родительном падеже (слуга Хозяина, отец Ивана ...)
     */
    public boolean setPersonAfter(boolean _value) {
        setValue(7, _value);
        return _value;
    }


    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        if (isAnimated()) 
            tmpStr.append("одуш.");
        if (isAnimal()) 
            tmpStr.append("животн.");
        if (isNamed()) 
            tmpStr.append("именов.");
        if (isNumbered()) 
            tmpStr.append("нумеруем.");
        if (isMeasured()) 
            tmpStr.append("измеряем.");
        if (isEmoPositive()) 
            tmpStr.append("позитив.");
        if (isEmoNegative()) 
            tmpStr.append("негатив.");
        if (isCanPersonAfter()) 
            tmpStr.append("персона_за_родит.");
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof ExplanWordAttr))) 
            return false;
        return value == (((ExplanWordAttr)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    public static ExplanWordAttr ooBitand(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new42((short)((((int)val1) & ((int)val2))));
    }

    public static ExplanWordAttr ooBitor(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new42((short)((((int)val1) | ((int)val2))));
    }

    public static boolean ooEq(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    /**
     * Неопределённое
     */
    public static ExplanWordAttr UNDEFINED = new ExplanWordAttr(null);

    public static ExplanWordAttr _new42(short _arg1) {
        ExplanWordAttr res = new ExplanWordAttr(null);
        res.value = _arg1;
        return res;
    }
    public ExplanWordAttr() {
        this(null);
    }
}
