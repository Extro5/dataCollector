/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.person.internal;

public class PersonAttrTerminType2 implements Comparable<PersonAttrTerminType2> {

    public static final PersonAttrTerminType2 UNDEFINED; // 0

    public static final PersonAttrTerminType2 IO; // 1

    public static final PersonAttrTerminType2 GRADE; // 2

    public static final PersonAttrTerminType2 ABBR; // 3

    public static final PersonAttrTerminType2 ADJ; // 4

    public static final PersonAttrTerminType2 IGNOREDADJ; // 5

    public static final PersonAttrTerminType2 IO2; // 6

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private PersonAttrTerminType2(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(PersonAttrTerminType2 v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, PersonAttrTerminType2> mapIntToEnum; 
    private static java.util.HashMap<String, PersonAttrTerminType2> mapStringToEnum; 
    public static PersonAttrTerminType2 of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        PersonAttrTerminType2 item = new PersonAttrTerminType2(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static PersonAttrTerminType2 of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new PersonAttrTerminType2(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        IO = new PersonAttrTerminType2(1, "IO");
        mapIntToEnum.put(IO.value(), IO);
        mapStringToEnum.put(IO.m_str.toUpperCase(), IO);
        GRADE = new PersonAttrTerminType2(2, "GRADE");
        mapIntToEnum.put(GRADE.value(), GRADE);
        mapStringToEnum.put(GRADE.m_str.toUpperCase(), GRADE);
        ABBR = new PersonAttrTerminType2(3, "ABBR");
        mapIntToEnum.put(ABBR.value(), ABBR);
        mapStringToEnum.put(ABBR.m_str.toUpperCase(), ABBR);
        ADJ = new PersonAttrTerminType2(4, "ADJ");
        mapIntToEnum.put(ADJ.value(), ADJ);
        mapStringToEnum.put(ADJ.m_str.toUpperCase(), ADJ);
        IGNOREDADJ = new PersonAttrTerminType2(5, "IGNOREDADJ");
        mapIntToEnum.put(IGNOREDADJ.value(), IGNOREDADJ);
        mapStringToEnum.put(IGNOREDADJ.m_str.toUpperCase(), IGNOREDADJ);
        IO2 = new PersonAttrTerminType2(6, "IO2");
        mapIntToEnum.put(IO2.value(), IO2);
        mapStringToEnum.put(IO2.m_str.toUpperCase(), IO2);
    }
}
