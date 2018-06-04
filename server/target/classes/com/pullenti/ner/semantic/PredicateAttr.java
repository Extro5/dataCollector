/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Атрибут глагола
 */
public class PredicateAttr implements Comparable<PredicateAttr> {

    public static final PredicateAttr UNDEFINED; // 0

    /**
     * Отрицание (не)
     */
    public static final PredicateAttr NOT; // 1

    /**
     * Глагол
     */
    public static final PredicateAttr VERB; // 2

    /**
     * Причастие
     */
    public static final PredicateAttr PARTICIPLE; // 3

    /**
     * Деепричастие
     */
    public static final PredicateAttr TRANSREGRESSIVE; // 4

    /**
     * Инфинитив
     */
    public static final PredicateAttr INFINITIVE; // 5

    /**
     * Прошедшее время
     */
    public static final PredicateAttr PAST; // 6

    /**
     * Настоящее время
     */
    public static final PredicateAttr PRESENT; // 7

    /**
     * Будущее время
     */
    public static final PredicateAttr FUTURE; // 8

    /**
     * Повелительное наклонение
     */
    public static final PredicateAttr IMPERATIVE; // 9

    /**
     * Возвратный
     */
    public static final PredicateAttr REFLEXIVE; // 10

    /**
     * Несовершенный
     */
    public static final PredicateAttr IMPERFECTIVE; // 11

    /**
     * Совершенный
     */
    public static final PredicateAttr PERFECTIVE; // 12

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private PredicateAttr(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(PredicateAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, PredicateAttr> mapIntToEnum; 
    private static java.util.HashMap<String, PredicateAttr> mapStringToEnum; 
    public static PredicateAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        PredicateAttr item = new PredicateAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static PredicateAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new PredicateAttr(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        NOT = new PredicateAttr(1, "NOT");
        mapIntToEnum.put(NOT.value(), NOT);
        mapStringToEnum.put(NOT.m_str.toUpperCase(), NOT);
        VERB = new PredicateAttr(2, "VERB");
        mapIntToEnum.put(VERB.value(), VERB);
        mapStringToEnum.put(VERB.m_str.toUpperCase(), VERB);
        PARTICIPLE = new PredicateAttr(3, "PARTICIPLE");
        mapIntToEnum.put(PARTICIPLE.value(), PARTICIPLE);
        mapStringToEnum.put(PARTICIPLE.m_str.toUpperCase(), PARTICIPLE);
        TRANSREGRESSIVE = new PredicateAttr(4, "TRANSREGRESSIVE");
        mapIntToEnum.put(TRANSREGRESSIVE.value(), TRANSREGRESSIVE);
        mapStringToEnum.put(TRANSREGRESSIVE.m_str.toUpperCase(), TRANSREGRESSIVE);
        INFINITIVE = new PredicateAttr(5, "INFINITIVE");
        mapIntToEnum.put(INFINITIVE.value(), INFINITIVE);
        mapStringToEnum.put(INFINITIVE.m_str.toUpperCase(), INFINITIVE);
        PAST = new PredicateAttr(6, "PAST");
        mapIntToEnum.put(PAST.value(), PAST);
        mapStringToEnum.put(PAST.m_str.toUpperCase(), PAST);
        PRESENT = new PredicateAttr(7, "PRESENT");
        mapIntToEnum.put(PRESENT.value(), PRESENT);
        mapStringToEnum.put(PRESENT.m_str.toUpperCase(), PRESENT);
        FUTURE = new PredicateAttr(8, "FUTURE");
        mapIntToEnum.put(FUTURE.value(), FUTURE);
        mapStringToEnum.put(FUTURE.m_str.toUpperCase(), FUTURE);
        IMPERATIVE = new PredicateAttr(9, "IMPERATIVE");
        mapIntToEnum.put(IMPERATIVE.value(), IMPERATIVE);
        mapStringToEnum.put(IMPERATIVE.m_str.toUpperCase(), IMPERATIVE);
        REFLEXIVE = new PredicateAttr(10, "REFLEXIVE");
        mapIntToEnum.put(REFLEXIVE.value(), REFLEXIVE);
        mapStringToEnum.put(REFLEXIVE.m_str.toUpperCase(), REFLEXIVE);
        IMPERFECTIVE = new PredicateAttr(11, "IMPERFECTIVE");
        mapIntToEnum.put(IMPERFECTIVE.value(), IMPERFECTIVE);
        mapStringToEnum.put(IMPERFECTIVE.m_str.toUpperCase(), IMPERFECTIVE);
        PERFECTIVE = new PredicateAttr(12, "PERFECTIVE");
        mapIntToEnum.put(PERFECTIVE.value(), PERFECTIVE);
        mapStringToEnum.put(PERFECTIVE.m_str.toUpperCase(), PERFECTIVE);
    }
}
