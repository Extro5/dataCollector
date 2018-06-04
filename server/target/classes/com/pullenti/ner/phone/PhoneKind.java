/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.phone;

public class PhoneKind implements Comparable<PhoneKind> {

    public static final PhoneKind UNDEFINED; // 0

    public static final PhoneKind HOME; // 1

    public static final PhoneKind MOBILE; // 2

    public static final PhoneKind WORK; // 3

    public static final PhoneKind FAX; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private PhoneKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(PhoneKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, PhoneKind> mapIntToEnum; 
    private static java.util.HashMap<String, PhoneKind> mapStringToEnum; 
    public static PhoneKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        PhoneKind item = new PhoneKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static PhoneKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new PhoneKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        HOME = new PhoneKind(1, "HOME");
        mapIntToEnum.put(HOME.value(), HOME);
        mapStringToEnum.put(HOME.m_str.toUpperCase(), HOME);
        MOBILE = new PhoneKind(2, "MOBILE");
        mapIntToEnum.put(MOBILE.value(), MOBILE);
        mapStringToEnum.put(MOBILE.m_str.toUpperCase(), MOBILE);
        WORK = new PhoneKind(3, "WORK");
        mapIntToEnum.put(WORK.value(), WORK);
        mapStringToEnum.put(WORK.m_str.toUpperCase(), WORK);
        FAX = new PhoneKind(4, "FAX");
        mapIntToEnum.put(FAX.value(), FAX);
        mapStringToEnum.put(FAX.m_str.toUpperCase(), FAX);
    }
}
