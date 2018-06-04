/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.named;

/**
 * Разновидности организаций
 */
public class NamedEntityKind implements Comparable<NamedEntityKind> {

    /**
     * Неопределённая
     */
    public static final NamedEntityKind UNDEFINED; // 0

    /**
     * Планеты
     */
    public static final NamedEntityKind PLANET; // 1

    /**
     * Разные географические объекты (не города) - реки, моря, континенты ...
     */
    public static final NamedEntityKind LOCATION; // 2

    /**
     * Памятники и монументы
     */
    public static final NamedEntityKind MONUMENT; // 3

    /**
     * Выдающиеся здания
     */
    public static final NamedEntityKind BUILDING; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NamedEntityKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NamedEntityKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NamedEntityKind> mapIntToEnum; 
    private static java.util.HashMap<String, NamedEntityKind> mapStringToEnum; 
    public static NamedEntityKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NamedEntityKind item = new NamedEntityKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NamedEntityKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new NamedEntityKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PLANET = new NamedEntityKind(1, "PLANET");
        mapIntToEnum.put(PLANET.value(), PLANET);
        mapStringToEnum.put(PLANET.m_str.toUpperCase(), PLANET);
        LOCATION = new NamedEntityKind(2, "LOCATION");
        mapIntToEnum.put(LOCATION.value(), LOCATION);
        mapStringToEnum.put(LOCATION.m_str.toUpperCase(), LOCATION);
        MONUMENT = new NamedEntityKind(3, "MONUMENT");
        mapIntToEnum.put(MONUMENT.value(), MONUMENT);
        mapStringToEnum.put(MONUMENT.m_str.toUpperCase(), MONUMENT);
        BUILDING = new NamedEntityKind(4, "BUILDING");
        mapIntToEnum.put(BUILDING.value(), BUILDING);
        mapStringToEnum.put(BUILDING.m_str.toUpperCase(), BUILDING);
    }
}
