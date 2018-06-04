/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.address.internal;

public class StreetItemType implements Comparable<StreetItemType> {

    /**
     * Это существительное - улица, проезд и пр.
     */
    public static final StreetItemType NOUN; // 0

    /**
     * Это название
     */
    public static final StreetItemType NAME; // 1

    /**
     * Номер
     */
    public static final StreetItemType NUMBER; // 2

    /**
     * Стандартное прилагательное (Большой, Средний ...)
     */
    public static final StreetItemType STDADJECTIVE; // 3

    /**
     * Стандартное имя
     */
    public static final StreetItemType STDNAME; // 4

    /**
     * Стандартная часть имени
     */
    public static final StreetItemType STDPARTOFNAME; // 5

    /**
     * 40-летия чего-то там
     */
    public static final StreetItemType AGE; // 6

    /**
     * Некоторое фиусированное название (МКАД)
     */
    public static final StreetItemType FIX; // 7

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private StreetItemType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(StreetItemType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, StreetItemType> mapIntToEnum; 
    private static java.util.HashMap<String, StreetItemType> mapStringToEnum; 
    public static StreetItemType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        StreetItemType item = new StreetItemType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static StreetItemType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        NOUN = new StreetItemType(0, "NOUN");
        mapIntToEnum.put(NOUN.value(), NOUN);
        mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
        NAME = new StreetItemType(1, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        NUMBER = new StreetItemType(2, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        STDADJECTIVE = new StreetItemType(3, "STDADJECTIVE");
        mapIntToEnum.put(STDADJECTIVE.value(), STDADJECTIVE);
        mapStringToEnum.put(STDADJECTIVE.m_str.toUpperCase(), STDADJECTIVE);
        STDNAME = new StreetItemType(4, "STDNAME");
        mapIntToEnum.put(STDNAME.value(), STDNAME);
        mapStringToEnum.put(STDNAME.m_str.toUpperCase(), STDNAME);
        STDPARTOFNAME = new StreetItemType(5, "STDPARTOFNAME");
        mapIntToEnum.put(STDPARTOFNAME.value(), STDPARTOFNAME);
        mapStringToEnum.put(STDPARTOFNAME.m_str.toUpperCase(), STDPARTOFNAME);
        AGE = new StreetItemType(6, "AGE");
        mapIntToEnum.put(AGE.value(), AGE);
        mapStringToEnum.put(AGE.m_str.toUpperCase(), AGE);
        FIX = new StreetItemType(7, "FIX");
        mapIntToEnum.put(FIX.value(), FIX);
        mapStringToEnum.put(FIX.m_str.toUpperCase(), FIX);
    }
}
