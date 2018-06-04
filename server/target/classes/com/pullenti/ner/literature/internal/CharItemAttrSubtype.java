/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharItemAttrSubtype implements Comparable<CharItemAttrSubtype> {

    public static final CharItemAttrSubtype UNDEFINED; // 0

    public static final CharItemAttrSubtype MISTER; // 1

    public static final CharItemAttrSubtype PERSONAFTER; // 2

    public static final CharItemAttrSubtype EMOTION; // 3

    public static final CharItemAttrSubtype NOUNINDICT; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CharItemAttrSubtype(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(CharItemAttrSubtype v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CharItemAttrSubtype> mapIntToEnum; 
    private static java.util.HashMap<String, CharItemAttrSubtype> mapStringToEnum; 
    public static CharItemAttrSubtype of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CharItemAttrSubtype item = new CharItemAttrSubtype(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CharItemAttrSubtype of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new CharItemAttrSubtype(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        MISTER = new CharItemAttrSubtype(1, "MISTER");
        mapIntToEnum.put(MISTER.value(), MISTER);
        mapStringToEnum.put(MISTER.m_str.toUpperCase(), MISTER);
        PERSONAFTER = new CharItemAttrSubtype(2, "PERSONAFTER");
        mapIntToEnum.put(PERSONAFTER.value(), PERSONAFTER);
        mapStringToEnum.put(PERSONAFTER.m_str.toUpperCase(), PERSONAFTER);
        EMOTION = new CharItemAttrSubtype(3, "EMOTION");
        mapIntToEnum.put(EMOTION.value(), EMOTION);
        mapStringToEnum.put(EMOTION.m_str.toUpperCase(), EMOTION);
        NOUNINDICT = new CharItemAttrSubtype(4, "NOUNINDICT");
        mapIntToEnum.put(NOUNINDICT.value(), NOUNINDICT);
        mapStringToEnum.put(NOUNINDICT.m_str.toUpperCase(), NOUNINDICT);
    }
}
