/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Падеж
 */
public class MorphCase {

    public MorphCase(MorphCase val) {
        value = (short)0;
        if (val != null) 
            value = val.value;
    }

    public short value;

    public boolean isUndefined() {
        return value == ((short)0);
    }

    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


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
     * Количество падежей
     */
    public int getCount() {
        if (value == ((short)0)) 
            return 0;
        int cou = 0;
        for(int i = 0; i < 12; i++) {
            if (((((int)value) & ((1 << i)))) != 0) 
                cou++;
        }
        return cou;
    }


    public static MorphCase UNDEFINED = _new48((short)0);

    /**
     * Именительный падеж
     */
    public static MorphCase NOMINATIVE = _new48((short)1);

    /**
     * Родительный падеж
     */
    public static MorphCase GENITIVE = _new48((short)2);

    /**
     * Дательный падеж
     */
    public static MorphCase DATIVE = _new48((short)4);

    /**
     * Винительный падеж
     */
    public static MorphCase ACCUSATIVE = _new48((short)8);

    /**
     * Творительный падеж
     */
    public static MorphCase INSTRUMENTAL = _new48((short)0x10);

    /**
     * Предложный падеж
     */
    public static MorphCase PREPOSITIONAL = _new48((short)0x20);

    /**
     * Звательный падеж
     */
    public static MorphCase VOCATIVE = _new48((short)0x40);

    /**
     * Частичный падеж
     */
    public static MorphCase PARTIAL = _new48((short)0x80);

    /**
     * Общий падеж
     */
    public static MorphCase COMMON = _new48((short)0x100);

    /**
     * Притяжательный падеж
     */
    public static MorphCase POSSESSIVE = _new48((short)0x200);

    /**
     * Все падежи одновременно
     */
    public static MorphCase ALLCASES = _new48((short)0x3FF);

    /**
     * Именительный
     */
    public boolean isNominative() {
        return getValue(0);
    }

    /**
     * Именительный
     */
    public boolean setNominative(boolean _value) {
        setValue(0, _value);
        return _value;
    }


    /**
     * Родительный
     */
    public boolean isGenitive() {
        return getValue(1);
    }

    /**
     * Родительный
     */
    public boolean setGenitive(boolean _value) {
        setValue(1, _value);
        return _value;
    }


    /**
     * Дательный
     */
    public boolean isDative() {
        return getValue(2);
    }

    /**
     * Дательный
     */
    public boolean setDative(boolean _value) {
        setValue(2, _value);
        return _value;
    }


    /**
     * Винительный
     */
    public boolean isAccusative() {
        return getValue(3);
    }

    /**
     * Винительный
     */
    public boolean setAccusative(boolean _value) {
        setValue(3, _value);
        return _value;
    }


    /**
     * Творительный
     */
    public boolean isInstrumental() {
        return getValue(4);
    }

    /**
     * Творительный
     */
    public boolean setInstrumental(boolean _value) {
        setValue(4, _value);
        return _value;
    }


    /**
     * Предложный
     */
    public boolean isPrepositional() {
        return getValue(5);
    }

    /**
     * Предложный
     */
    public boolean setPrepositional(boolean _value) {
        setValue(5, _value);
        return _value;
    }


    /**
     * Звательный
     */
    public boolean isVocative() {
        return getValue(6);
    }

    /**
     * Звательный
     */
    public boolean setVocative(boolean _value) {
        setValue(6, _value);
        return _value;
    }


    /**
     * Частичный
     */
    public boolean isPartial() {
        return getValue(7);
    }

    /**
     * Частичный
     */
    public boolean setPartial(boolean _value) {
        setValue(7, _value);
        return _value;
    }


    /**
     * Общий (для английского)
     */
    public boolean isCommon() {
        return getValue(8);
    }

    /**
     * Общий (для английского)
     */
    public boolean setCommon(boolean _value) {
        setValue(8, _value);
        return _value;
    }


    /**
     * Притяжательный (для английского)
     */
    public boolean isPossessive() {
        return getValue(9);
    }

    /**
     * Притяжательный (для английского)
     */
    public boolean setPossessive(boolean _value) {
        setValue(9, _value);
        return _value;
    }


    private static String[] m_Names = new String[] {"именит.", "родит.", "дател.", "винит.", "творит.", "предлож.", "зват.", "частич.", "общ.", "притяж."};

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        for(int i = 0; i < m_Names.length; i++) {
            if (getValue(i)) {
                if (tmpStr.length() > 0) 
                    tmpStr.append("|");
                tmpStr.append(m_Names[i]);
            }
        }
        return tmpStr.toString();
    }

    /**
     * Восстановить падежи из строки, полученной ToString
     * @param str 
     * @return 
     */
    public static MorphCase parse(String str) {
        MorphCase res = new MorphCase(null);
        if (com.pullenti.n2j.Utils.isNullOrEmpty(str)) 
            return res;
        for(String s : com.pullenti.n2j.Utils.split(str, String.valueOf('|'), false)) {
            for(int i = 0; i < m_Names.length; i++) {
                if (com.pullenti.n2j.Utils.stringsEq(s, m_Names[i])) {
                    res.setValue(i, true);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphCase))) 
            return false;
        return value == (((MorphCase)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    public static MorphCase ooBitand(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new48((short)((((int)val1) & ((int)val2))));
    }

    public static MorphCase ooBitor(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new48((short)((((int)val1) | ((int)val2))));
    }

    public static MorphCase ooBitxor(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new48((short)((((int)val1) ^ ((int)val2))));
    }

    public static boolean ooEq(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static MorphCase _new48(short _arg1) {
        MorphCase res = new MorphCase(null);
        res.value = _arg1;
        return res;
    }
    public MorphCase() {
        this(null);
    }
}
