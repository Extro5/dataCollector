/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Форма
 */
public class MorphForm implements Comparable<MorphForm> {

    /**
     * Не определена
     */
    public static final MorphForm UNDEFINED; // 0

    /**
     * Краткая форма
     */
    public static final MorphForm SHORT; // 1

    /**
     * Синонимичная форма
     */
    public static final MorphForm SYNONYM; // 2

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphForm(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphForm v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphForm> mapIntToEnum; 
    private static java.util.HashMap<String, MorphForm> mapStringToEnum; 
    public static MorphForm of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphForm item = new MorphForm(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphForm of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new MorphForm(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SHORT = new MorphForm(1, "SHORT");
        mapIntToEnum.put(SHORT.value(), SHORT);
        mapStringToEnum.put(SHORT.m_str.toUpperCase(), SHORT);
        SYNONYM = new MorphForm(2, "SYNONYM");
        mapIntToEnum.put(SYNONYM.value(), SYNONYM);
        mapStringToEnum.put(SYNONYM.m_str.toUpperCase(), SYNONYM);
    }
}
