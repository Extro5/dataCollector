/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Роли актанта
 */
public class ActantRole implements Comparable<ActantRole> {

    public static final ActantRole UNDEFINED; // 0

    public static final ActantRole AGENT; // 1

    public static final ActantRole PATIENT; // 2

    public static final ActantRole SENTACTANT; // 3

    public static final ActantRole OBJECT; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ActantRole(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ActantRole v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ActantRole> mapIntToEnum; 
    private static java.util.HashMap<String, ActantRole> mapStringToEnum; 
    public static ActantRole of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ActantRole item = new ActantRole(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ActantRole of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new ActantRole(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        AGENT = new ActantRole(1, "AGENT");
        mapIntToEnum.put(AGENT.value(), AGENT);
        mapStringToEnum.put(AGENT.m_str.toUpperCase(), AGENT);
        PATIENT = new ActantRole(2, "PATIENT");
        mapIntToEnum.put(PATIENT.value(), PATIENT);
        mapStringToEnum.put(PATIENT.m_str.toUpperCase(), PATIENT);
        SENTACTANT = new ActantRole(3, "SENTACTANT");
        mapIntToEnum.put(SENTACTANT.value(), SENTACTANT);
        mapStringToEnum.put(SENTACTANT.m_str.toUpperCase(), SENTACTANT);
        OBJECT = new ActantRole(4, "OBJECT");
        mapIntToEnum.put(OBJECT.value(), OBJECT);
        mapStringToEnum.put(OBJECT.m_str.toUpperCase(), OBJECT);
    }
}
