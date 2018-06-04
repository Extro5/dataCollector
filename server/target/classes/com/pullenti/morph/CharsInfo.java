/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Информация о символах токена
 */
public class CharsInfo {

    public CharsInfo(CharsInfo ci) {
        if (ci != null) 
            value = ci.value;
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
     * Все символы в верхнем регистре
     */
    public boolean isAllUpper() {
        return getValue(0);
    }

    /**
     * Все символы в верхнем регистре
     */
    public boolean setAllUpper(boolean _value) {
        setValue(0, _value);
        return _value;
    }


    /**
     * Все символы в нижнем регистре
     */
    public boolean isAllLower() {
        return getValue(1);
    }

    /**
     * Все символы в нижнем регистре
     */
    public boolean setAllLower(boolean _value) {
        setValue(1, _value);
        return _value;
    }


    /**
     * ПЕрвый символ в верхнем регистре, остальные в нижнем. 
     *  Для однобуквенной комбинации false.
     */
    public boolean isCapitalUpper() {
        return getValue(2);
    }

    /**
     * ПЕрвый символ в верхнем регистре, остальные в нижнем. 
     *  Для однобуквенной комбинации false.
     */
    public boolean setCapitalUpper(boolean _value) {
        setValue(2, _value);
        return _value;
    }


    /**
     * Все символы в верхнеи регистре, кроме последнего (длина >= 3)
     */
    public boolean isLastLower() {
        return getValue(3);
    }

    /**
     * Все символы в верхнеи регистре, кроме последнего (длина >= 3)
     */
    public boolean setLastLower(boolean _value) {
        setValue(3, _value);
        return _value;
    }


    /**
     * Это буквы
     */
    public boolean isLetter() {
        return getValue(4);
    }

    /**
     * Это буквы
     */
    public boolean setLetter(boolean _value) {
        setValue(4, _value);
        return _value;
    }


    /**
     * Это латиница
     */
    public boolean isLatinLetter() {
        return getValue(5);
    }

    /**
     * Это латиница
     */
    public boolean setLatinLetter(boolean _value) {
        setValue(5, _value);
        return _value;
    }


    /**
     * Это кириллица
     */
    public boolean isCyrillicLetter() {
        return getValue(6);
    }

    /**
     * Это кириллица
     */
    public boolean setCyrillicLetter(boolean _value) {
        setValue(6, _value);
        return _value;
    }


    @Override
    public String toString() {
        if (!isLetter()) 
            return "Nonletter";
        StringBuilder tmpStr = new StringBuilder();
        if (isAllUpper()) 
            tmpStr.append("AllUpper");
        else if (isAllLower()) 
            tmpStr.append("AllLower");
        else if (isCapitalUpper()) 
            tmpStr.append("CapitalUpper");
        else if (isLastLower()) 
            tmpStr.append("LastLower");
        else 
            tmpStr.append("Nonstandard");
        if (isLatinLetter()) 
            tmpStr.append(" Latin");
        else if (isCyrillicLetter()) 
            tmpStr.append(" Cyrillic");
        else if (isLetter()) 
            tmpStr.append(" Letter");
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphClass))) 
            return false;
        return value == (((MorphClass)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    public static CharsInfo ooBitand(CharsInfo arg1, CharsInfo arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new38(null, (short)((((int)val1) & ((int)val2))));
    }

    public static CharsInfo ooBitor(CharsInfo arg1, CharsInfo arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new38(null, (short)((((int)val1) | ((int)val2))));
    }

    public static boolean ooEq(CharsInfo arg1, CharsInfo arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(CharsInfo arg1, CharsInfo arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static CharsInfo _new38(CharsInfo _arg1, short _arg2) {
        CharsInfo res = new CharsInfo(_arg1);
        res.value = _arg2;
        return res;
    }
    public static CharsInfo _new2167(boolean _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.setCapitalUpper(_arg1);
        return res;
    }
    public static CharsInfo _new2334(boolean _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.setCyrillicLetter(_arg1);
        return res;
    }
    public static CharsInfo _new2340(boolean _arg1, boolean _arg2) {
        CharsInfo res = new CharsInfo(null);
        res.setCyrillicLetter(_arg1);
        res.setCapitalUpper(_arg2);
        return res;
    }
    public static CharsInfo _new2345(boolean _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        CharsInfo res = new CharsInfo(null);
        res.setCapitalUpper(_arg1);
        res.setCyrillicLetter(_arg2);
        res.setLatinLetter(_arg3);
        res.setLetter(_arg4);
        return res;
    }
    public static CharsInfo _new2367(boolean _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.setLatinLetter(_arg1);
        return res;
    }
    public static CharsInfo _new2785(short _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.value = _arg1;
        return res;
    }
    public CharsInfo() {
        this(null);
    }
}
