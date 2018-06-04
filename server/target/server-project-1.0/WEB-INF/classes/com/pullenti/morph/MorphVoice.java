/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Залог (для глаголов)
 */
public class MorphVoice implements Comparable<MorphVoice> {

    /**
     * Неопределено
     */
    public static final MorphVoice UNDEFINED; // 0

    /**
     * Действительный
     */
    public static final MorphVoice ACTIVE; // 1

    /**
     * Страдательный
     */
    public static final MorphVoice PASSIVE; // 2

    /**
     * Средний
     */
    public static final MorphVoice MIDDLE; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphVoice(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphVoice v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphVoice> mapIntToEnum; 
    private static java.util.HashMap<String, MorphVoice> mapStringToEnum; 
    public static MorphVoice of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphVoice item = new MorphVoice(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphVoice of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new MorphVoice(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ACTIVE = new MorphVoice(1, "ACTIVE");
        mapIntToEnum.put(ACTIVE.value(), ACTIVE);
        mapStringToEnum.put(ACTIVE.m_str.toUpperCase(), ACTIVE);
        PASSIVE = new MorphVoice(2, "PASSIVE");
        mapIntToEnum.put(PASSIVE.value(), PASSIVE);
        mapStringToEnum.put(PASSIVE.m_str.toUpperCase(), PASSIVE);
        MIDDLE = new MorphVoice(4, "MIDDLE");
        mapIntToEnum.put(MIDDLE.value(), MIDDLE);
        mapStringToEnum.put(MIDDLE.m_str.toUpperCase(), MIDDLE);
    }
}
