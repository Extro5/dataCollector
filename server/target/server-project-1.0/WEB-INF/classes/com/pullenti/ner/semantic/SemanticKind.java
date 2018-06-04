/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Классы синтаксических элементов
 */
public class SemanticKind implements Comparable<SemanticKind> {

    public static final SemanticKind UNDEFINED; // 0

    /**
     * Объект
     */
    public static final SemanticKind OBJECT; // 1

    /**
     * Неизвестный (местоимение)
     */
    public static final SemanticKind PRONOUN; // 2

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemanticKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemanticKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemanticKind> mapIntToEnum; 
    private static java.util.HashMap<String, SemanticKind> mapStringToEnum; 
    public static SemanticKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemanticKind item = new SemanticKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemanticKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new SemanticKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        OBJECT = new SemanticKind(1, "OBJECT");
        mapIntToEnum.put(OBJECT.value(), OBJECT);
        mapStringToEnum.put(OBJECT.m_str.toUpperCase(), OBJECT);
        PRONOUN = new SemanticKind(2, "PRONOUN");
        mapIntToEnum.put(PRONOUN.value(), PRONOUN);
        mapStringToEnum.put(PRONOUN.m_str.toUpperCase(), PRONOUN);
    }
}
