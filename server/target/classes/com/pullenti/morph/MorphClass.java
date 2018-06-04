/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Часть речи
 */
public class MorphClass {

    public MorphClass(MorphClass val) {
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
     * Существительное
     */
    public boolean isNoun() {
        return getValue(0);
    }

    /**
     * Существительное
     */
    public boolean setNoun(boolean _value) {
        if (_value) 
            value = (short)0;
        setValue(0, _value);
        return _value;
    }


    public static boolean isNounInt(int val) {
        return ((val & 1)) != 0;
    }

    /**
     * Прилагательное
     */
    public boolean isAdjective() {
        return getValue(1);
    }

    /**
     * Прилагательное
     */
    public boolean setAdjective(boolean _value) {
        if (_value) 
            value = (short)0;
        setValue(1, _value);
        return _value;
    }


    public static boolean isAdjectiveInt(int val) {
        return ((val & 2)) != 0;
    }

    /**
     * Глагол
     */
    public boolean isVerb() {
        return getValue(2);
    }

    /**
     * Глагол
     */
    public boolean setVerb(boolean _value) {
        if (_value) 
            value = (short)0;
        setValue(2, _value);
        return _value;
    }


    public static boolean isVerbInt(int val) {
        return ((val & 4)) != 0;
    }

    /**
     * Наречие
     */
    public boolean isAdverb() {
        return getValue(3);
    }

    /**
     * Наречие
     */
    public boolean setAdverb(boolean _value) {
        if (_value) 
            value = (short)0;
        setValue(3, _value);
        return _value;
    }


    public static boolean isAdverbInt(int val) {
        return ((val & 8)) != 0;
    }

    /**
     * Местоимение
     */
    public boolean isPronoun() {
        return getValue(4);
    }

    /**
     * Местоимение
     */
    public boolean setPronoun(boolean _value) {
        if (_value) 
            value = (short)0;
        setValue(4, _value);
        return _value;
    }


    public static boolean isPronounInt(int val) {
        return ((val & 0x10)) != 0;
    }

    /**
     * Всякая ерунда (частицы, междометия)
     */
    public boolean isMisc() {
        return getValue(5);
    }

    /**
     * Всякая ерунда (частицы, междометия)
     */
    public boolean setMisc(boolean _value) {
        if (_value) 
            value = (short)0;
        setValue(5, _value);
        return _value;
    }


    public static boolean isMiscInt(int val) {
        return ((val & 0x20)) != 0;
    }

    /**
     * Предлог
     */
    public boolean isPreposition() {
        return getValue(6);
    }

    /**
     * Предлог
     */
    public boolean setPreposition(boolean _value) {
        setValue(6, _value);
        return _value;
    }


    public static boolean isPrepositionInt(int val) {
        return ((val & 0x40)) != 0;
    }

    /**
     * Союз
     */
    public boolean isConjunction() {
        return getValue(7);
    }

    /**
     * Союз
     */
    public boolean setConjunction(boolean _value) {
        setValue(7, _value);
        return _value;
    }


    public static boolean isConjunctionInt(int val) {
        return ((val & 0x80)) != 0;
    }

    /**
     * Собственное имя (фамилия, имя, отчество, геогр.название и др.)
     */
    public boolean isProper() {
        return getValue(8);
    }

    /**
     * Собственное имя (фамилия, имя, отчество, геогр.название и др.)
     */
    public boolean setProper(boolean _value) {
        setValue(8, _value);
        return _value;
    }


    public static boolean isProperInt(int val) {
        return ((val & 0x100)) != 0;
    }

    /**
     * Фамилия
     */
    public boolean isProperSurname() {
        return getValue(9);
    }

    /**
     * Фамилия
     */
    public boolean setProperSurname(boolean _value) {
        if (_value) 
            setProper(true);
        setValue(9, _value);
        return _value;
    }


    public static boolean isProperSurnameInt(int val) {
        return ((val & 0x200)) != 0;
    }

    /**
     * Фамилия
     */
    public boolean isProperName() {
        return getValue(10);
    }

    /**
     * Фамилия
     */
    public boolean setProperName(boolean _value) {
        if (_value) 
            setProper(true);
        setValue(10, _value);
        return _value;
    }


    public static boolean isProperNameInt(int val) {
        return ((val & 0x400)) != 0;
    }

    /**
     * Отчество
     */
    public boolean isProperSecname() {
        return getValue(11);
    }

    /**
     * Отчество
     */
    public boolean setProperSecname(boolean _value) {
        if (_value) 
            setProper(true);
        setValue(11, _value);
        return _value;
    }


    public static boolean isProperSecnameInt(int val) {
        return ((val & 0x800)) != 0;
    }

    /**
     * Географическое название
     */
    public boolean isProperGeo() {
        return getValue(12);
    }

    /**
     * Географическое название
     */
    public boolean setProperGeo(boolean _value) {
        if (_value) 
            setProper(true);
        setValue(12, _value);
        return _value;
    }


    public static boolean isProperGeoInt(int val) {
        return ((val & 0x1000)) != 0;
    }

    /**
     * Личное местоимение (я, мой, ты, он ...)
     */
    public boolean isPersonalPronoun() {
        return getValue(13);
    }

    /**
     * Личное местоимение (я, мой, ты, он ...)
     */
    public boolean setPersonalPronoun(boolean _value) {
        setValue(13, _value);
        return _value;
    }


    public static boolean isPersonalPronounInt(int val) {
        return ((val & 0x2000)) != 0;
    }

    private static String[] m_Names = new String[] {"существ.", "прилаг.", "глагол", "наречие", "местоим.", "разное", "предлог", "союз", "собств.", "фамилия", "имя", "отч.", "геогр.", "личн.местоим."};

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        for(int i = 0; i < m_Names.length; i++) {
            if (getValue(i)) {
                if (i == 5) {
                    if (isConjunction() || isPreposition() || isProper()) 
                        continue;
                }
                if (tmpStr.length() > 0) 
                    tmpStr.append("|");
                tmpStr.append(m_Names[i]);
            }
        }
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

    public static MorphClass ooBitand(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new63((short)((((int)val1) & ((int)val2))));
    }

    public static MorphClass ooBitor(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new63((short)((((int)val1) | ((int)val2))));
    }

    public static MorphClass ooBitxor(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new63((short)((((int)val1) ^ ((int)val2))));
    }

    public static boolean ooEq(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(MorphClass arg1, MorphClass arg2) {
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
    public static MorphClass UNDEFINED = _new66(true);

    /**
     * Существительное
     */
    public static MorphClass NOUN = _new67(true);

    /**
     * Местоимение
     */
    public static MorphClass PRONOUN = _new68(true);

    /**
     * Личное местоимение
     */
    public static MorphClass PERSONALPRONOUN = _new69(true);

    /**
     * Глагол
     */
    public static MorphClass VERB = _new70(true);

    /**
     * Прилагательное
     */
    public static MorphClass ADJECTIVE = _new71(true);

    /**
     * Наречие
     */
    public static MorphClass ADVERB = _new72(true);

    /**
     * Предлог
     */
    public static MorphClass PREPOSITION = _new73(true);

    /**
     * Союз
     */
    public static MorphClass CONJUNCTION = _new74(true);

    public static MorphClass _new63(short _arg1) {
        MorphClass res = new MorphClass(null);
        res.value = _arg1;
        return res;
    }
    public static MorphClass _new66(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setUndefined(_arg1);
        return res;
    }
    public static MorphClass _new67(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setNoun(_arg1);
        return res;
    }
    public static MorphClass _new68(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setPronoun(_arg1);
        return res;
    }
    public static MorphClass _new69(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setPersonalPronoun(_arg1);
        return res;
    }
    public static MorphClass _new70(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setVerb(_arg1);
        return res;
    }
    public static MorphClass _new71(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setAdjective(_arg1);
        return res;
    }
    public static MorphClass _new72(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setAdverb(_arg1);
        return res;
    }
    public static MorphClass _new73(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setPreposition(_arg1);
        return res;
    }
    public static MorphClass _new74(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setConjunction(_arg1);
        return res;
    }
    public static MorphClass _new1511(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setProperSurname(_arg1);
        return res;
    }
    public static MorphClass _new1516(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setProper(_arg1);
        return res;
    }
    public MorphClass() {
        this(null);
    }
}
