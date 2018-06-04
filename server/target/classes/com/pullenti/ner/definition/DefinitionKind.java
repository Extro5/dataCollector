/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.definition;

/**
 * Тип определения
 */
public class DefinitionKind implements Comparable<DefinitionKind> {

    /**
     * Непонятно
     */
    public static final DefinitionKind UNDEFINED; // 0

    /**
     * Просто утрерждение
     */
    public static final DefinitionKind ASSERTATION; // 1

    /**
     * Строгое определение
     */
    public static final DefinitionKind DEFINITION; // 2

    /**
     * Отрицание
     */
    public static final DefinitionKind NEGATION; // 3

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DefinitionKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DefinitionKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DefinitionKind> mapIntToEnum; 
    private static java.util.HashMap<String, DefinitionKind> mapStringToEnum; 
    public static DefinitionKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DefinitionKind item = new DefinitionKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DefinitionKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new DefinitionKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ASSERTATION = new DefinitionKind(1, "ASSERTATION");
        mapIntToEnum.put(ASSERTATION.value(), ASSERTATION);
        mapStringToEnum.put(ASSERTATION.m_str.toUpperCase(), ASSERTATION);
        DEFINITION = new DefinitionKind(2, "DEFINITION");
        mapIntToEnum.put(DEFINITION.value(), DEFINITION);
        mapStringToEnum.put(DEFINITION.m_str.toUpperCase(), DEFINITION);
        NEGATION = new DefinitionKind(3, "NEGATION");
        mapIntToEnum.put(NEGATION.value(), NEGATION);
        mapStringToEnum.put(NEGATION.m_str.toUpperCase(), NEGATION);
    }
}
