/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Это для английских глаголов
 */
public class MorphFinite implements Comparable<MorphFinite> {

    public static final MorphFinite UNDEFINED; // 0

    public static final MorphFinite FINITE; // 1

    public static final MorphFinite INFINITIVE; // 2

    public static final MorphFinite PARTICIPLE; // 4

    public static final MorphFinite GERUND; // 8

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphFinite(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphFinite v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphFinite> mapIntToEnum; 
    private static java.util.HashMap<String, MorphFinite> mapStringToEnum; 
    public static MorphFinite of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphFinite item = new MorphFinite(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphFinite of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new MorphFinite(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        FINITE = new MorphFinite(1, "FINITE");
        mapIntToEnum.put(FINITE.value(), FINITE);
        mapStringToEnum.put(FINITE.m_str.toUpperCase(), FINITE);
        INFINITIVE = new MorphFinite(2, "INFINITIVE");
        mapIntToEnum.put(INFINITIVE.value(), INFINITIVE);
        mapStringToEnum.put(INFINITIVE.m_str.toUpperCase(), INFINITIVE);
        PARTICIPLE = new MorphFinite(4, "PARTICIPLE");
        mapIntToEnum.put(PARTICIPLE.value(), PARTICIPLE);
        mapStringToEnum.put(PARTICIPLE.m_str.toUpperCase(), PARTICIPLE);
        GERUND = new MorphFinite(8, "GERUND");
        mapIntToEnum.put(GERUND.value(), GERUND);
        mapStringToEnum.put(GERUND.m_str.toUpperCase(), GERUND);
    }
}
