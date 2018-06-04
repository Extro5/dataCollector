/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Аспект (для глаголов)
 */
public class MorphAspect implements Comparable<MorphAspect> {

    /**
     * Неопределено
     */
    public static final MorphAspect UNDEFINED; // 0

    /**
     * Совершенный
     */
    public static final MorphAspect PERFECTIVE; // 1

    /**
     * Несовершенный
     */
    public static final MorphAspect IMPERFECTIVE; // 2

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphAspect(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphAspect v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphAspect> mapIntToEnum; 
    private static java.util.HashMap<String, MorphAspect> mapStringToEnum; 
    public static MorphAspect of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphAspect item = new MorphAspect(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphAspect of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new MorphAspect(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PERFECTIVE = new MorphAspect(1, "PERFECTIVE");
        mapIntToEnum.put(PERFECTIVE.value(), PERFECTIVE);
        mapStringToEnum.put(PERFECTIVE.m_str.toUpperCase(), PERFECTIVE);
        IMPERFECTIVE = new MorphAspect(2, "IMPERFECTIVE");
        mapIntToEnum.put(IMPERFECTIVE.value(), IMPERFECTIVE);
        mapStringToEnum.put(IMPERFECTIVE.m_str.toUpperCase(), IMPERFECTIVE);
    }
}
