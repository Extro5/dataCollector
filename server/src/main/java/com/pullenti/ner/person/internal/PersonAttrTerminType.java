/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.person.internal;

public class PersonAttrTerminType implements Comparable<PersonAttrTerminType> {

    public static final PersonAttrTerminType PREFIX; // 0

    public static final PersonAttrTerminType BESTREGARDS; // 1

    public static final PersonAttrTerminType POSITION; // 2

    public static final PersonAttrTerminType KING; // 3

    public static final PersonAttrTerminType OTHER; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private PersonAttrTerminType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(PersonAttrTerminType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, PersonAttrTerminType> mapIntToEnum; 
    private static java.util.HashMap<String, PersonAttrTerminType> mapStringToEnum; 
    public static PersonAttrTerminType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        PersonAttrTerminType item = new PersonAttrTerminType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static PersonAttrTerminType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        PREFIX = new PersonAttrTerminType(0, "PREFIX");
        mapIntToEnum.put(PREFIX.value(), PREFIX);
        mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
        BESTREGARDS = new PersonAttrTerminType(1, "BESTREGARDS");
        mapIntToEnum.put(BESTREGARDS.value(), BESTREGARDS);
        mapStringToEnum.put(BESTREGARDS.m_str.toUpperCase(), BESTREGARDS);
        POSITION = new PersonAttrTerminType(2, "POSITION");
        mapIntToEnum.put(POSITION.value(), POSITION);
        mapStringToEnum.put(POSITION.m_str.toUpperCase(), POSITION);
        KING = new PersonAttrTerminType(3, "KING");
        mapIntToEnum.put(KING.value(), KING);
        mapStringToEnum.put(KING.m_str.toUpperCase(), KING);
        OTHER = new PersonAttrTerminType(4, "OTHER");
        mapIntToEnum.put(OTHER.value(), OTHER);
        mapStringToEnum.put(OTHER.m_str.toUpperCase(), OTHER);
    }
}
