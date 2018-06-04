/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.address;

/**
 * Типы улиц
 */
public class StreetKind implements Comparable<StreetKind> {

    /**
     * Обычная улица-переулок-площадь
     */
    public static final StreetKind UNDEFINED; // 0

    /**
     * Автодорога
     */
    public static final StreetKind ROAD; // 1

    /**
     * Станция метро
     */
    public static final StreetKind METRO; // 2

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private StreetKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(StreetKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, StreetKind> mapIntToEnum; 
    private static java.util.HashMap<String, StreetKind> mapStringToEnum; 
    public static StreetKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        StreetKind item = new StreetKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static StreetKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new StreetKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ROAD = new StreetKind(1, "ROAD");
        mapIntToEnum.put(ROAD.value(), ROAD);
        mapStringToEnum.put(ROAD.m_str.toUpperCase(), ROAD);
        METRO = new StreetKind(2, "METRO");
        mapIntToEnum.put(METRO.value(), METRO);
        mapStringToEnum.put(METRO.m_str.toUpperCase(), METRO);
    }
}
