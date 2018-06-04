/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.mail;

public class MailKind implements Comparable<MailKind> {

    public static final MailKind UNDEFINED; // 0

    public static final MailKind HEAD; // 1

    public static final MailKind HELLO; // 2

    public static final MailKind BODY; // 3

    public static final MailKind TAIL; // 4

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MailKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MailKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MailKind> mapIntToEnum; 
    private static java.util.HashMap<String, MailKind> mapStringToEnum; 
    public static MailKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MailKind item = new MailKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MailKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new MailKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        HEAD = new MailKind(1, "HEAD");
        mapIntToEnum.put(HEAD.value(), HEAD);
        mapStringToEnum.put(HEAD.m_str.toUpperCase(), HEAD);
        HELLO = new MailKind(2, "HELLO");
        mapIntToEnum.put(HELLO.value(), HELLO);
        mapStringToEnum.put(HELLO.m_str.toUpperCase(), HELLO);
        BODY = new MailKind(3, "BODY");
        mapIntToEnum.put(BODY.value(), BODY);
        mapStringToEnum.put(BODY.m_str.toUpperCase(), BODY);
        TAIL = new MailKind(4, "TAIL");
        mapIntToEnum.put(TAIL.value(), TAIL);
        mapStringToEnum.put(TAIL.m_str.toUpperCase(), TAIL);
    }
}
