/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharacterAge implements Comparable<CharacterAge> {

    public static final CharacterAge UNDEFINED; // 0

    public static final CharacterAge YOUNGSMALL; // 1

    public static final CharacterAge YOUNGMIDDLE; // 2

    public static final CharacterAge YOUNGTOP; // 4

    public static final CharacterAge YOUNG; // (YOUNGSMALL.value()) | (YOUNGMIDDLE.value()) | (YOUNGTOP.value())

    public static final CharacterAge MIDDLEAGESMALL; // 8

    public static final CharacterAge MIDDLEAGEMIDDLE; // 0x10

    public static final CharacterAge MIDDLEAGETOP; // 0x20

    public static final CharacterAge MIDDLE; // (MIDDLEAGESMALL.value()) | (MIDDLEAGEMIDDLE.value()) | (MIDDLEAGETOP.value())

    public static final CharacterAge OLD; // 0x40

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CharacterAge(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(CharacterAge v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CharacterAge> mapIntToEnum; 
    private static java.util.HashMap<String, CharacterAge> mapStringToEnum; 
    public static CharacterAge of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CharacterAge item = new CharacterAge(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CharacterAge of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new CharacterAge(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        YOUNGSMALL = new CharacterAge(1, "YOUNGSMALL");
        mapIntToEnum.put(YOUNGSMALL.value(), YOUNGSMALL);
        mapStringToEnum.put(YOUNGSMALL.m_str.toUpperCase(), YOUNGSMALL);
        YOUNGMIDDLE = new CharacterAge(2, "YOUNGMIDDLE");
        mapIntToEnum.put(YOUNGMIDDLE.value(), YOUNGMIDDLE);
        mapStringToEnum.put(YOUNGMIDDLE.m_str.toUpperCase(), YOUNGMIDDLE);
        YOUNGTOP = new CharacterAge(4, "YOUNGTOP");
        mapIntToEnum.put(YOUNGTOP.value(), YOUNGTOP);
        mapStringToEnum.put(YOUNGTOP.m_str.toUpperCase(), YOUNGTOP);
        YOUNG = new CharacterAge((YOUNGSMALL.value()) | (YOUNGMIDDLE.value()) | (YOUNGTOP.value()), "YOUNG");
        mapIntToEnum.put(YOUNG.value(), YOUNG);
        mapStringToEnum.put(YOUNG.m_str.toUpperCase(), YOUNG);
        MIDDLEAGESMALL = new CharacterAge(8, "MIDDLEAGESMALL");
        mapIntToEnum.put(MIDDLEAGESMALL.value(), MIDDLEAGESMALL);
        mapStringToEnum.put(MIDDLEAGESMALL.m_str.toUpperCase(), MIDDLEAGESMALL);
        MIDDLEAGEMIDDLE = new CharacterAge(0x10, "MIDDLEAGEMIDDLE");
        mapIntToEnum.put(MIDDLEAGEMIDDLE.value(), MIDDLEAGEMIDDLE);
        mapStringToEnum.put(MIDDLEAGEMIDDLE.m_str.toUpperCase(), MIDDLEAGEMIDDLE);
        MIDDLEAGETOP = new CharacterAge(0x20, "MIDDLEAGETOP");
        mapIntToEnum.put(MIDDLEAGETOP.value(), MIDDLEAGETOP);
        mapStringToEnum.put(MIDDLEAGETOP.m_str.toUpperCase(), MIDDLEAGETOP);
        MIDDLE = new CharacterAge((MIDDLEAGESMALL.value()) | (MIDDLEAGEMIDDLE.value()) | (MIDDLEAGETOP.value()), "MIDDLE");
        mapIntToEnum.put(MIDDLE.value(), MIDDLE);
        mapStringToEnum.put(MIDDLE.m_str.toUpperCase(), MIDDLE);
        OLD = new CharacterAge(0x40, "OLD");
        mapIntToEnum.put(OLD.value(), OLD);
        mapStringToEnum.put(OLD.m_str.toUpperCase(), OLD);
    }
}
