/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core.internal;

public class BlkTyps implements Comparable<BlkTyps> {

    public static final BlkTyps UNDEFINED; // 0

    public static final BlkTyps INDEX; // 1

    public static final BlkTyps INDEXITEM; // 2

    public static final BlkTyps INTRO; // 3

    public static final BlkTyps LITERATURE; // 4

    public static final BlkTyps APPENDIX; // 5

    public static final BlkTyps CONSLUSION; // 6

    public static final BlkTyps MISC; // 7

    public static final BlkTyps CHAPTER; // 8

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private BlkTyps(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(BlkTyps v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, BlkTyps> mapIntToEnum; 
    private static java.util.HashMap<String, BlkTyps> mapStringToEnum; 
    public static BlkTyps of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        BlkTyps item = new BlkTyps(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static BlkTyps of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new BlkTyps(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        INDEX = new BlkTyps(1, "INDEX");
        mapIntToEnum.put(INDEX.value(), INDEX);
        mapStringToEnum.put(INDEX.m_str.toUpperCase(), INDEX);
        INDEXITEM = new BlkTyps(2, "INDEXITEM");
        mapIntToEnum.put(INDEXITEM.value(), INDEXITEM);
        mapStringToEnum.put(INDEXITEM.m_str.toUpperCase(), INDEXITEM);
        INTRO = new BlkTyps(3, "INTRO");
        mapIntToEnum.put(INTRO.value(), INTRO);
        mapStringToEnum.put(INTRO.m_str.toUpperCase(), INTRO);
        LITERATURE = new BlkTyps(4, "LITERATURE");
        mapIntToEnum.put(LITERATURE.value(), LITERATURE);
        mapStringToEnum.put(LITERATURE.m_str.toUpperCase(), LITERATURE);
        APPENDIX = new BlkTyps(5, "APPENDIX");
        mapIntToEnum.put(APPENDIX.value(), APPENDIX);
        mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
        CONSLUSION = new BlkTyps(6, "CONSLUSION");
        mapIntToEnum.put(CONSLUSION.value(), CONSLUSION);
        mapStringToEnum.put(CONSLUSION.m_str.toUpperCase(), CONSLUSION);
        MISC = new BlkTyps(7, "MISC");
        mapIntToEnum.put(MISC.value(), MISC);
        mapStringToEnum.put(MISC.m_str.toUpperCase(), MISC);
        CHAPTER = new BlkTyps(8, "CHAPTER");
        mapIntToEnum.put(CHAPTER.value(), CHAPTER);
        mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
    }
}
