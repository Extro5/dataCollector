/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

/**
 * Ввели для оптимизации на Питоне.
 */
public class UnicodeInfo {

    public static java.util.ArrayList<UnicodeInfo> ALLCHARS;

    public static void initialize() {
    }

    private UnicodeInfo(short v) {
        uniChar = (char)v;
        code = (int)v;
        m_Value = (short)0;
    }

    private short m_Value;

    public char uniChar;

    public int code;

    private boolean getValue(int i) {
        return ((((((int)m_Value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            m_Value |= ((short)((1 << i)));
        else 
            m_Value &= ((short)(~((1 << i))));
    }

    public boolean isWhitespace() {
        return ((((int)m_Value) & 0x1)) != 0;
    }

    public boolean setWhitespace(boolean value) {
        setValue(0, value);
        return value;
    }


    public boolean isDigit() {
        return ((((int)m_Value) & 0x2)) != 0;
    }

    public boolean setDigit(boolean value) {
        setValue(1, value);
        return value;
    }


    public boolean isLetter() {
        return ((((int)m_Value) & 0x4)) != 0;
    }

    public boolean setLetter(boolean value) {
        setValue(2, value);
        return value;
    }


    public boolean isUpper() {
        return ((((int)m_Value) & 0x8)) != 0;
    }

    public boolean setUpper(boolean value) {
        setValue(3, value);
        return value;
    }


    public boolean isLower() {
        return ((((int)m_Value) & 0x10)) != 0;
    }

    public boolean setLower(boolean value) {
        setValue(4, value);
        return value;
    }


    public boolean isLatin() {
        return ((((int)m_Value) & 0x20)) != 0;
    }

    public boolean setLatin(boolean value) {
        setValue(5, value);
        return value;
    }


    public boolean isCyrillic() {
        return ((((int)m_Value) & 0x40)) != 0;
    }

    public boolean setCyrillic(boolean value) {
        setValue(6, value);
        return value;
    }


    public boolean isHiphen() {
        return ((((int)m_Value) & 0x80)) != 0;
    }

    public boolean setHiphen(boolean value) {
        setValue(7, value);
        return value;
    }


    public boolean isVowel() {
        return ((((int)m_Value) & 0x100)) != 0;
    }

    public boolean setVowel(boolean value) {
        setValue(8, value);
        return value;
    }


    public boolean isQuot() {
        return ((((int)m_Value) & 0x200)) != 0;
    }

    public boolean setQuot(boolean value) {
        setValue(9, value);
        return value;
    }


    public boolean isApos() {
        return ((((int)m_Value) & 0x400)) != 0;
    }

    public boolean setApos(boolean value) {
        setValue(10, value);
        return value;
    }


    public boolean isUdaren() {
        return ((((int)m_Value) & 0x800)) != 0;
    }

    public boolean setUdaren(boolean value) {
        setValue(11, value);
        return value;
    }

    public UnicodeInfo() {
        this((short)0);
    }
    static {
        ALLCHARS = new java.util.ArrayList< >(0x10000);
        for(int i = 0; i < 0x10000; i++) {
            char ch = (char)i;
            UnicodeInfo ui = new UnicodeInfo((short)i);
            if (com.pullenti.n2j.Utils.isWhitespace(ch)) 
                ui.setWhitespace(true);
            if (Character.isDigit(ch)) 
                ui.setDigit(true);
            if (Character.isLetter(ch)) {
                ui.setLetter(true);
                if (i >= 0x400 && (i < 0x500)) {
                    ui.setCyrillic(true);
                    if ("АЕЁИОУЮЯЫЭЄІЇЎӘӨҰҮІ".indexOf(ch) >= 0) 
                        ui.setVowel(true);
                }
                else if (i < 0x200) {
                    ui.setLatin(true);
                    if ("AEIOUY".indexOf(ch) >= 0) 
                        ui.setVowel(true);
                }
                if (Character.isUpperCase(ch)) 
                    ui.setUpper(true);
                if (Character.isLowerCase(ch)) 
                    ui.setLower(true);
            }
            else {
                if (((((ch == '-' || ch == '–' || ch == '¬') || ch == '-' || ch == ((char)0x00AD)) || ch == ((char)0x2011) || ch == '-') || ch == '—' || ch == '–') || ch == '−' || ch == '-') 
                    ui.setHiphen(true);
                if ("\"'`“”’".indexOf(ch) >= 0) 
                    ui.setQuot(true);
                if ("'`’".indexOf(ch) >= 0) {
                    ui.setApos(true);
                    ui.setQuot(true);
                }
            }
            if (i >= 0x300 && (i < 0x370)) 
                ui.setUdaren(true);
            ALLCHARS.add(ui);
        }
    }
}
