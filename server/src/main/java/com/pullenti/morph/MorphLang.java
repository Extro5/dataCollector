/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Язык(и)
 */
public class MorphLang {

    public MorphLang(MorphLang lng) {
        value = (short)0;
        if (lng != null) 
            value = lng.value;
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
     * Неопределённый язык
     */
    public boolean isUndefined() {
        return value == ((short)0);
    }

    /**
     * Неопределённый язык
     */
    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


    /**
     * Русский язык
     */
    public boolean isRu() {
        return getValue(0);
    }

    /**
     * Русский язык
     */
    public boolean setRu(boolean _value) {
        setValue(0, _value);
        return _value;
    }


    /**
     * Украинский язык
     */
    public boolean isUa() {
        return getValue(1);
    }

    /**
     * Украинский язык
     */
    public boolean setUa(boolean _value) {
        setValue(1, _value);
        return _value;
    }


    /**
     * Белорусский язык
     */
    public boolean isBy() {
        return getValue(2);
    }

    /**
     * Белорусский язык
     */
    public boolean setBy(boolean _value) {
        setValue(2, _value);
        return _value;
    }


    /**
     * Русский, украинский, белорусский или казахский язык
     */
    public boolean isCyrillic() {
        return (isRu() | isUa() | isBy()) | isKz();
    }


    /**
     * Английский язык
     */
    public boolean isEn() {
        return getValue(3);
    }

    /**
     * Английский язык
     */
    public boolean setEn(boolean _value) {
        setValue(3, _value);
        return _value;
    }


    /**
     * Итальянский язык
     */
    public boolean isIt() {
        return getValue(4);
    }

    /**
     * Итальянский язык
     */
    public boolean setIt(boolean _value) {
        setValue(4, _value);
        return _value;
    }


    /**
     * Казахский язык
     */
    public boolean isKz() {
        return getValue(5);
    }

    /**
     * Казахский язык
     */
    public boolean setKz(boolean _value) {
        setValue(5, _value);
        return _value;
    }


    private static String[] m_Names = new String[] {"RU", "UA", "BY", "EN", "IT", "KZ"};

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        for(int i = 0; i < m_Names.length; i++) {
            if (getValue(i)) {
                if (tmpStr.length() > 0) 
                    tmpStr.append(";");
                tmpStr.append(m_Names[i]);
            }
        }
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphLang))) 
            return false;
        return value == (((MorphLang)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    /**
     * Преобразовать из строки
     * @param str 
     * @param lang 
     * @return 
     */
    public static boolean tryParse(String str, com.pullenti.n2j.Outargwrapper<MorphLang> lang) {
        lang.value = new MorphLang(null);
        while(!com.pullenti.n2j.Utils.isNullOrEmpty(str)) {
            int i;
            for(i = 0; i < m_Names.length; i++) {
                if (str.toUpperCase().startsWith(m_Names[i].toUpperCase())) 
                    break;
            }
            if (i >= m_Names.length) 
                break;
            lang.value.value |= ((short)((1 << i)));
            for(i = 2; i < str.length(); i++) {
                if (Character.isLetter(str.charAt(i))) 
                    break;
            }
            if (i >= str.length()) 
                break;
            str = str.substring(i);
        }
        if (lang.value.isUndefined()) 
            return false;
        return true;
    }

    public static MorphLang ooBitand(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new6((short)((((int)val1) & ((int)val2))));
    }

    public static MorphLang ooBitor(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new6((short)((((int)val1) | ((int)val2))));
    }

    public static boolean ooEq(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(MorphLang arg1, MorphLang arg2) {
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
    public static MorphLang UNKNOWN = new MorphLang(null);

    /**
     * Русский
     */
    public static MorphLang RU = _new77(true);

    /**
     * Украинский
     */
    public static MorphLang UA = _new78(true);

    /**
     * Белорусский
     */
    public static MorphLang BY = _new79(true);

    /**
     * Английский
     */
    public static MorphLang EN = _new80(true);

    /**
     * Итальянский
     */
    public static MorphLang IT = _new81(true);

    /**
     * Казахский
     */
    public static MorphLang KZ = _new82(true);

    public static MorphLang _new6(short _arg1) {
        MorphLang res = new MorphLang(null);
        res.value = _arg1;
        return res;
    }
    public static MorphLang _new77(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setRu(_arg1);
        return res;
    }
    public static MorphLang _new78(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setUa(_arg1);
        return res;
    }
    public static MorphLang _new79(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setBy(_arg1);
        return res;
    }
    public static MorphLang _new80(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setEn(_arg1);
        return res;
    }
    public static MorphLang _new81(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setIt(_arg1);
        return res;
    }
    public static MorphLang _new82(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setKz(_arg1);
        return res;
    }
    public MorphLang() {
        this(null);
    }
}
