/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Тип персонажа
 */
public class CharacterType implements Comparable<CharacterType> {

    /**
     * Неизвестно
     */
    public static final CharacterType UNDEFINED; // 0

    /**
     * Человек
     */
    public static final CharacterType MAN; // 1

    /**
     * Животное
     */
    public static final CharacterType ANIMAL; // 2

    /**
     * Мифическое
     */
    public static final CharacterType MYTHIC; // 3

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CharacterType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(CharacterType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CharacterType> mapIntToEnum; 
    private static java.util.HashMap<String, CharacterType> mapStringToEnum; 
    public static CharacterType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CharacterType item = new CharacterType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CharacterType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new CharacterType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        MAN = new CharacterType(1, "MAN");
        mapIntToEnum.put(MAN.value(), MAN);
        mapStringToEnum.put(MAN.m_str.toUpperCase(), MAN);
        ANIMAL = new CharacterType(2, "ANIMAL");
        mapIntToEnum.put(ANIMAL.value(), ANIMAL);
        mapStringToEnum.put(ANIMAL.m_str.toUpperCase(), ANIMAL);
        MYTHIC = new CharacterType(3, "MYTHIC");
        mapIntToEnum.put(MYTHIC.value(), MYTHIC);
        mapStringToEnum.put(MYTHIC.m_str.toUpperCase(), MYTHIC);
    }
}
