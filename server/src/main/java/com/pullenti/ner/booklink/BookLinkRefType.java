/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.booklink;

/**
 * Тип ссылки
 */
public class BookLinkRefType implements Comparable<BookLinkRefType> {

    public static final BookLinkRefType UNDEFINED; // 0

    /**
     * Встроенная в текст
     */
    public static final BookLinkRefType INLINE; // 1

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private BookLinkRefType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(BookLinkRefType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, BookLinkRefType> mapIntToEnum; 
    private static java.util.HashMap<String, BookLinkRefType> mapStringToEnum; 
    public static BookLinkRefType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        BookLinkRefType item = new BookLinkRefType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static BookLinkRefType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new BookLinkRefType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        INLINE = new BookLinkRefType(1, "INLINE");
        mapIntToEnum.put(INLINE.value(), INLINE);
        mapStringToEnum.put(INLINE.m_str.toUpperCase(), INLINE);
    }
}
