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
public class SentenceKind implements Comparable<SentenceKind> {

    public static final SentenceKind UNDEFINED; // 0

    /**
     * Аннотация всего текста (реферат)
     */
    public static final SentenceKind ANNOTATION; // 1

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SentenceKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SentenceKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SentenceKind> mapIntToEnum; 
    private static java.util.HashMap<String, SentenceKind> mapStringToEnum; 
    public static SentenceKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SentenceKind item = new SentenceKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SentenceKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new SentenceKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ANNOTATION = new SentenceKind(1, "ANNOTATION");
        mapIntToEnum.put(ANNOTATION.value(), ANNOTATION);
        mapStringToEnum.put(ANNOTATION.m_str.toUpperCase(), ANNOTATION);
    }
}
