/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old;

/**
 * Типы текстов
 */
public class DocumentBlockType implements Comparable<DocumentBlockType> {

    public static final DocumentBlockType UNDEFINED; // 0

    public static final DocumentBlockType TITLE; // 1

    public static final DocumentBlockType TAIL; // 2

    public static final DocumentBlockType INTRODUCTION; // 3

    public static final DocumentBlockType CONCLUSION; // 4

    public static final DocumentBlockType LITERATURE; // 5

    public static final DocumentBlockType APPENDIX; // 6

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DocumentBlockType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DocumentBlockType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DocumentBlockType> mapIntToEnum; 
    private static java.util.HashMap<String, DocumentBlockType> mapStringToEnum; 
    public static DocumentBlockType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DocumentBlockType item = new DocumentBlockType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DocumentBlockType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new DocumentBlockType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        TITLE = new DocumentBlockType(1, "TITLE");
        mapIntToEnum.put(TITLE.value(), TITLE);
        mapStringToEnum.put(TITLE.m_str.toUpperCase(), TITLE);
        TAIL = new DocumentBlockType(2, "TAIL");
        mapIntToEnum.put(TAIL.value(), TAIL);
        mapStringToEnum.put(TAIL.m_str.toUpperCase(), TAIL);
        INTRODUCTION = new DocumentBlockType(3, "INTRODUCTION");
        mapIntToEnum.put(INTRODUCTION.value(), INTRODUCTION);
        mapStringToEnum.put(INTRODUCTION.m_str.toUpperCase(), INTRODUCTION);
        CONCLUSION = new DocumentBlockType(4, "CONCLUSION");
        mapIntToEnum.put(CONCLUSION.value(), CONCLUSION);
        mapStringToEnum.put(CONCLUSION.m_str.toUpperCase(), CONCLUSION);
        LITERATURE = new DocumentBlockType(5, "LITERATURE");
        mapIntToEnum.put(LITERATURE.value(), LITERATURE);
        mapStringToEnum.put(LITERATURE.m_str.toUpperCase(), LITERATURE);
        APPENDIX = new DocumentBlockType(6, "APPENDIX");
        mapIntToEnum.put(APPENDIX.value(), APPENDIX);
        mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
    }
}
