/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class Types implements Comparable<Types> {

    public static final Types UNDEFINED; // 0

    public static final Types SEQEND; // 1

    public static final Types EMPTY; // 2

    public static final Types OBJ; // 3

    public static final Types ACT; // 4

    public static final Types ACTPRICH; // 5

    public static final Types ACTORADVERB; // 6

    public static final Types NUMBER; // 7

    public static final Types COMMA; // 8

    public static final Types CONJ; // 9

    public static final Types WHAT; // 10

    public static final Types ADVERB; // 11

    public static final Types DELIMETER; // 12

    public static final Types BRACKETOPEN; // 13

    public static final Types BRACKETCLOSE; // 14

    public static final Types PROPERNAME; // 15

    public static final Types TIME; // 16

    public static final Types TIMEMISC; // 17

    public static final Types ACTANT; // 18

    public static final Types PRONOUNOBJ; // 19

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private Types(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(Types v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, Types> mapIntToEnum; 
    private static java.util.HashMap<String, Types> mapStringToEnum; 
    public static Types of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        Types item = new Types(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static Types of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new Types(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SEQEND = new Types(1, "SEQEND");
        mapIntToEnum.put(SEQEND.value(), SEQEND);
        mapStringToEnum.put(SEQEND.m_str.toUpperCase(), SEQEND);
        EMPTY = new Types(2, "EMPTY");
        mapIntToEnum.put(EMPTY.value(), EMPTY);
        mapStringToEnum.put(EMPTY.m_str.toUpperCase(), EMPTY);
        OBJ = new Types(3, "OBJ");
        mapIntToEnum.put(OBJ.value(), OBJ);
        mapStringToEnum.put(OBJ.m_str.toUpperCase(), OBJ);
        ACT = new Types(4, "ACT");
        mapIntToEnum.put(ACT.value(), ACT);
        mapStringToEnum.put(ACT.m_str.toUpperCase(), ACT);
        ACTPRICH = new Types(5, "ACTPRICH");
        mapIntToEnum.put(ACTPRICH.value(), ACTPRICH);
        mapStringToEnum.put(ACTPRICH.m_str.toUpperCase(), ACTPRICH);
        ACTORADVERB = new Types(6, "ACTORADVERB");
        mapIntToEnum.put(ACTORADVERB.value(), ACTORADVERB);
        mapStringToEnum.put(ACTORADVERB.m_str.toUpperCase(), ACTORADVERB);
        NUMBER = new Types(7, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        COMMA = new Types(8, "COMMA");
        mapIntToEnum.put(COMMA.value(), COMMA);
        mapStringToEnum.put(COMMA.m_str.toUpperCase(), COMMA);
        CONJ = new Types(9, "CONJ");
        mapIntToEnum.put(CONJ.value(), CONJ);
        mapStringToEnum.put(CONJ.m_str.toUpperCase(), CONJ);
        WHAT = new Types(10, "WHAT");
        mapIntToEnum.put(WHAT.value(), WHAT);
        mapStringToEnum.put(WHAT.m_str.toUpperCase(), WHAT);
        ADVERB = new Types(11, "ADVERB");
        mapIntToEnum.put(ADVERB.value(), ADVERB);
        mapStringToEnum.put(ADVERB.m_str.toUpperCase(), ADVERB);
        DELIMETER = new Types(12, "DELIMETER");
        mapIntToEnum.put(DELIMETER.value(), DELIMETER);
        mapStringToEnum.put(DELIMETER.m_str.toUpperCase(), DELIMETER);
        BRACKETOPEN = new Types(13, "BRACKETOPEN");
        mapIntToEnum.put(BRACKETOPEN.value(), BRACKETOPEN);
        mapStringToEnum.put(BRACKETOPEN.m_str.toUpperCase(), BRACKETOPEN);
        BRACKETCLOSE = new Types(14, "BRACKETCLOSE");
        mapIntToEnum.put(BRACKETCLOSE.value(), BRACKETCLOSE);
        mapStringToEnum.put(BRACKETCLOSE.m_str.toUpperCase(), BRACKETCLOSE);
        PROPERNAME = new Types(15, "PROPERNAME");
        mapIntToEnum.put(PROPERNAME.value(), PROPERNAME);
        mapStringToEnum.put(PROPERNAME.m_str.toUpperCase(), PROPERNAME);
        TIME = new Types(16, "TIME");
        mapIntToEnum.put(TIME.value(), TIME);
        mapStringToEnum.put(TIME.m_str.toUpperCase(), TIME);
        TIMEMISC = new Types(17, "TIMEMISC");
        mapIntToEnum.put(TIMEMISC.value(), TIMEMISC);
        mapStringToEnum.put(TIMEMISC.m_str.toUpperCase(), TIMEMISC);
        ACTANT = new Types(18, "ACTANT");
        mapIntToEnum.put(ACTANT.value(), ACTANT);
        mapStringToEnum.put(ACTANT.m_str.toUpperCase(), ACTANT);
        PRONOUNOBJ = new Types(19, "PRONOUNOBJ");
        mapIntToEnum.put(PRONOUNOBJ.value(), PRONOUNOBJ);
        mapStringToEnum.put(PRONOUNOBJ.m_str.toUpperCase(), PRONOUNOBJ);
    }
}
