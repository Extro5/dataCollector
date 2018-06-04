/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Классы фрагментов
 */
public class TextPeaceKind implements Comparable<TextPeaceKind> {

    /**
     * Текстовой фрагмент
     */
    public static final TextPeaceKind TEXT; // 0

    /**
     * Заголовок части, раздела, главы
     */
    public static final TextPeaceKind TITLE; // 1

    /**
     * Заголовок всего произведения
     */
    public static final TextPeaceKind HEAD; // 2

    /**
     * Фрагмент в конце произведения
     */
    public static final TextPeaceKind TAIL; // 3

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private TextPeaceKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(TextPeaceKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, TextPeaceKind> mapIntToEnum; 
    private static java.util.HashMap<String, TextPeaceKind> mapStringToEnum; 
    public static TextPeaceKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        TextPeaceKind item = new TextPeaceKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static TextPeaceKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        TEXT = new TextPeaceKind(0, "TEXT");
        mapIntToEnum.put(TEXT.value(), TEXT);
        mapStringToEnum.put(TEXT.m_str.toUpperCase(), TEXT);
        TITLE = new TextPeaceKind(1, "TITLE");
        mapIntToEnum.put(TITLE.value(), TITLE);
        mapStringToEnum.put(TITLE.m_str.toUpperCase(), TITLE);
        HEAD = new TextPeaceKind(2, "HEAD");
        mapIntToEnum.put(HEAD.value(), HEAD);
        mapStringToEnum.put(HEAD.m_str.toUpperCase(), HEAD);
        TAIL = new TextPeaceKind(3, "TAIL");
        mapIntToEnum.put(TAIL.value(), TAIL);
        mapStringToEnum.put(TAIL.m_str.toUpperCase(), TAIL);
    }
}
