/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class ValTypes implements Comparable<ValTypes> {

    public static final ValTypes BASE; // 0

    public static final ValTypes PROP; // 1

    public static final ValTypes NAME; // 2

    public static final ValTypes NUMBER; // 3

    public static final ValTypes ALIAS; // 4

    public static final ValTypes ACTANTPROP; // 5

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ValTypes(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ValTypes v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ValTypes> mapIntToEnum; 
    private static java.util.HashMap<String, ValTypes> mapStringToEnum; 
    public static ValTypes of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ValTypes item = new ValTypes(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ValTypes of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        BASE = new ValTypes(0, "BASE");
        mapIntToEnum.put(BASE.value(), BASE);
        mapStringToEnum.put(BASE.m_str.toUpperCase(), BASE);
        PROP = new ValTypes(1, "PROP");
        mapIntToEnum.put(PROP.value(), PROP);
        mapStringToEnum.put(PROP.m_str.toUpperCase(), PROP);
        NAME = new ValTypes(2, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        NUMBER = new ValTypes(3, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        ALIAS = new ValTypes(4, "ALIAS");
        mapIntToEnum.put(ALIAS.value(), ALIAS);
        mapStringToEnum.put(ALIAS.m_str.toUpperCase(), ALIAS);
        ACTANTPROP = new ValTypes(5, "ACTANTPROP");
        mapIntToEnum.put(ACTANTPROP.value(), ACTANTPROP);
        mapStringToEnum.put(ACTANTPROP.m_str.toUpperCase(), ACTANTPROP);
    }
}
