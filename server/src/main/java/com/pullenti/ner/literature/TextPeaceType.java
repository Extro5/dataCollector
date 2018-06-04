/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Типы и подптипы
 */
public class TextPeaceType implements Comparable<TextPeaceType> {

    public static final TextPeaceType UNDEFINED; // 0

    /**
     * Книга
     */
    public static final TextPeaceType BOOK; // 1

    /**
     * Том (для заголовков)
     */
    public static final TextPeaceType VOLUME; // 2

    /**
     * Часть (для заголовков)
     */
    public static final TextPeaceType PART; // 3

    /**
     * Глава (для заголовков)
     */
    public static final TextPeaceType CHAPTER; // 4

    /**
     * Введение (для заголовков)
     */
    public static final TextPeaceType INTRO; // 5

    /**
     * Заключение
     */
    public static final TextPeaceType CONCLUSION; // 6

    /**
     * Замечания
     */
    public static final TextPeaceType REMARKS; // 7

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private TextPeaceType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(TextPeaceType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, TextPeaceType> mapIntToEnum; 
    private static java.util.HashMap<String, TextPeaceType> mapStringToEnum; 
    public static TextPeaceType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        TextPeaceType item = new TextPeaceType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static TextPeaceType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new TextPeaceType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        BOOK = new TextPeaceType(1, "BOOK");
        mapIntToEnum.put(BOOK.value(), BOOK);
        mapStringToEnum.put(BOOK.m_str.toUpperCase(), BOOK);
        VOLUME = new TextPeaceType(2, "VOLUME");
        mapIntToEnum.put(VOLUME.value(), VOLUME);
        mapStringToEnum.put(VOLUME.m_str.toUpperCase(), VOLUME);
        PART = new TextPeaceType(3, "PART");
        mapIntToEnum.put(PART.value(), PART);
        mapStringToEnum.put(PART.m_str.toUpperCase(), PART);
        CHAPTER = new TextPeaceType(4, "CHAPTER");
        mapIntToEnum.put(CHAPTER.value(), CHAPTER);
        mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
        INTRO = new TextPeaceType(5, "INTRO");
        mapIntToEnum.put(INTRO.value(), INTRO);
        mapStringToEnum.put(INTRO.m_str.toUpperCase(), INTRO);
        CONCLUSION = new TextPeaceType(6, "CONCLUSION");
        mapIntToEnum.put(CONCLUSION.value(), CONCLUSION);
        mapStringToEnum.put(CONCLUSION.m_str.toUpperCase(), CONCLUSION);
        REMARKS = new TextPeaceType(7, "REMARKS");
        mapIntToEnum.put(REMARKS.value(), REMARKS);
        mapStringToEnum.put(REMARKS.m_str.toUpperCase(), REMARKS);
    }
}
