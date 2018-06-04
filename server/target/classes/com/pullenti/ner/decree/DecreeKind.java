/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.decree;

public class DecreeKind implements Comparable<DecreeKind> {

    public static final DecreeKind UNDEFINED; // 0

    public static final DecreeKind KODEX; // 1

    public static final DecreeKind USTAV; // 2

    public static final DecreeKind KONVENTION; // 3

    public static final DecreeKind CONTRACT; // 4

    public static final DecreeKind PROJECT; // 5

    /**
     * Источники опубликований
     */
    public static final DecreeKind PUBLISHER; // 6

    /**
     * Госпрограммы
     */
    public static final DecreeKind PROGRAM; // 7

    /**
     * Стандарт (ГОСТ, ТУ, ANSI и пр.)
     */
    public static final DecreeKind STANDARD; // 8

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DecreeKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DecreeKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DecreeKind> mapIntToEnum; 
    private static java.util.HashMap<String, DecreeKind> mapStringToEnum; 
    public static DecreeKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DecreeKind item = new DecreeKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DecreeKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new DecreeKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        KODEX = new DecreeKind(1, "KODEX");
        mapIntToEnum.put(KODEX.value(), KODEX);
        mapStringToEnum.put(KODEX.m_str.toUpperCase(), KODEX);
        USTAV = new DecreeKind(2, "USTAV");
        mapIntToEnum.put(USTAV.value(), USTAV);
        mapStringToEnum.put(USTAV.m_str.toUpperCase(), USTAV);
        KONVENTION = new DecreeKind(3, "KONVENTION");
        mapIntToEnum.put(KONVENTION.value(), KONVENTION);
        mapStringToEnum.put(KONVENTION.m_str.toUpperCase(), KONVENTION);
        CONTRACT = new DecreeKind(4, "CONTRACT");
        mapIntToEnum.put(CONTRACT.value(), CONTRACT);
        mapStringToEnum.put(CONTRACT.m_str.toUpperCase(), CONTRACT);
        PROJECT = new DecreeKind(5, "PROJECT");
        mapIntToEnum.put(PROJECT.value(), PROJECT);
        mapStringToEnum.put(PROJECT.m_str.toUpperCase(), PROJECT);
        PUBLISHER = new DecreeKind(6, "PUBLISHER");
        mapIntToEnum.put(PUBLISHER.value(), PUBLISHER);
        mapStringToEnum.put(PUBLISHER.m_str.toUpperCase(), PUBLISHER);
        PROGRAM = new DecreeKind(7, "PROGRAM");
        mapIntToEnum.put(PROGRAM.value(), PROGRAM);
        mapStringToEnum.put(PROGRAM.m_str.toUpperCase(), PROGRAM);
        STANDARD = new DecreeKind(8, "STANDARD");
        mapIntToEnum.put(STANDARD.value(), STANDARD);
        mapStringToEnum.put(STANDARD.m_str.toUpperCase(), STANDARD);
    }
}
