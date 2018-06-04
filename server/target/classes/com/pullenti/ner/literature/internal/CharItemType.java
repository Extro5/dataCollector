/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharItemType implements Comparable<CharItemType> {

    public static final CharItemType NAME; // 0

    public static final CharItemType PROPER; // 1

    public static final CharItemType ATTR; // 2

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CharItemType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(CharItemType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CharItemType> mapIntToEnum; 
    private static java.util.HashMap<String, CharItemType> mapStringToEnum; 
    public static CharItemType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CharItemType item = new CharItemType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CharItemType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        NAME = new CharItemType(0, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        PROPER = new CharItemType(1, "PROPER");
        mapIntToEnum.put(PROPER.value(), PROPER);
        mapStringToEnum.put(PROPER.m_str.toUpperCase(), PROPER);
        ATTR = new CharItemType(2, "ATTR");
        mapIntToEnum.put(ATTR.value(), ATTR);
        mapStringToEnum.put(ATTR.m_str.toUpperCase(), ATTR);
    }
}
