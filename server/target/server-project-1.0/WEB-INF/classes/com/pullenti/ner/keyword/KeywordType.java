/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.keyword;

public class KeywordType implements Comparable<KeywordType> {

    public static final KeywordType UNDEFINED; // 0

    public static final KeywordType OBJECT; // 1

    public static final KeywordType REFERENT; // 2

    public static final KeywordType PREDICATE; // 3

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private KeywordType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(KeywordType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, KeywordType> mapIntToEnum; 
    private static java.util.HashMap<String, KeywordType> mapStringToEnum; 
    public static KeywordType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        KeywordType item = new KeywordType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static KeywordType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new KeywordType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        OBJECT = new KeywordType(1, "OBJECT");
        mapIntToEnum.put(OBJECT.value(), OBJECT);
        mapStringToEnum.put(OBJECT.m_str.toUpperCase(), OBJECT);
        REFERENT = new KeywordType(2, "REFERENT");
        mapIntToEnum.put(REFERENT.value(), REFERENT);
        mapStringToEnum.put(REFERENT.m_str.toUpperCase(), REFERENT);
        PREDICATE = new KeywordType(3, "PREDICATE");
        mapIntToEnum.put(PREDICATE.value(), PREDICATE);
        mapStringToEnum.put(PREDICATE.m_str.toUpperCase(), PREDICATE);
    }
}
