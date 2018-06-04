/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Пол персонажа
 */
public class CharacterGender implements Comparable<CharacterGender> {

    public static final CharacterGender UNDEFINED; // 0

    public static final CharacterGender MASCULINE; // 1

    public static final CharacterGender FEMINIE; // 2

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CharacterGender(int val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Integer)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(CharacterGender v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CharacterGender> mapIntToEnum; 
    private static java.util.HashMap<String, CharacterGender> mapStringToEnum; 
    public static CharacterGender of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CharacterGender item = new CharacterGender(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CharacterGender of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new CharacterGender(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        MASCULINE = new CharacterGender(1, "MASCULINE");
        mapIntToEnum.put(MASCULINE.value(), MASCULINE);
        mapStringToEnum.put(MASCULINE.m_str.toUpperCase(), MASCULINE);
        FEMINIE = new CharacterGender(2, "FEMINIE");
        mapIntToEnum.put(FEMINIE.value(), FEMINIE);
        mapStringToEnum.put(FEMINIE.m_str.toUpperCase(), FEMINIE);
    }
}
