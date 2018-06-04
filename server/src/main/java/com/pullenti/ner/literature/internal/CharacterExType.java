/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharacterExType implements Comparable<CharacterExType> {

    public static final CharacterExType UNDEFINED; // 0

    /**
     * Первое лицо
     */
    public static final CharacterExType FIRSTPERSON; // 1

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CharacterExType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(CharacterExType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CharacterExType> mapIntToEnum; 
    private static java.util.HashMap<String, CharacterExType> mapStringToEnum; 
    public static CharacterExType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CharacterExType item = new CharacterExType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CharacterExType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new CharacterExType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        FIRSTPERSON = new CharacterExType(1, "FIRSTPERSON");
        mapIntToEnum.put(FIRSTPERSON.value(), FIRSTPERSON);
        mapStringToEnum.put(FIRSTPERSON.m_str.toUpperCase(), FIRSTPERSON);
    }
}
