/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Дополнительная морфологическая информация
 */
public class MorphMiscInfo {

    /**
     * Дополнительные атрибуты
     */
    public java.util.Collection<String> getAttrs() {
        return m_Attrs;
    }


    private java.util.ArrayList<String> m_Attrs = new java.util.ArrayList< >();

    public short m_Value;

    private boolean getValue(int i) {
        return ((((((int)m_Value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            m_Value |= ((short)((1 << i)));
        else 
            m_Value &= ((short)(~((1 << i))));
    }

    private void _addAttr(String attr) {
        if (!m_Attrs.contains(attr)) 
            m_Attrs.add(attr);
    }

    public MorphMiscInfo clone() {
        MorphMiscInfo res = new MorphMiscInfo();
        res.m_Value = m_Value;
        res.m_Attrs.addAll(m_Attrs);
        return res;
    }

    /**
     * Лицо
     */
    public MorphPerson getPerson() {
        MorphPerson res = MorphPerson.UNDEFINED;
        if (m_Attrs.contains("1 л.")) 
            res = MorphPerson.of((res.value()) | (MorphPerson.FIRST.value()));
        if (m_Attrs.contains("2 л.")) 
            res = MorphPerson.of((res.value()) | (MorphPerson.SECOND.value()));
        if (m_Attrs.contains("3 л.")) 
            res = MorphPerson.of((res.value()) | (MorphPerson.THIRD.value()));
        return res;
    }

    /**
     * Лицо
     */
    public MorphPerson setPerson(MorphPerson value) {
        if ((((value.value()) & (MorphPerson.FIRST.value()))) != (MorphPerson.UNDEFINED.value())) 
            _addAttr("1 л.");
        if ((((value.value()) & (MorphPerson.SECOND.value()))) != (MorphPerson.UNDEFINED.value())) 
            _addAttr("2 л.");
        if ((((value.value()) & (MorphPerson.THIRD.value()))) != (MorphPerson.UNDEFINED.value())) 
            _addAttr("3 л.");
        return value;
    }


    /**
     * Время (для глаголов)
     */
    public MorphTense getTense() {
        if (m_Attrs.contains("п.вр.")) 
            return MorphTense.PAST;
        if (m_Attrs.contains("н.вр.")) 
            return MorphTense.PRESENT;
        if (m_Attrs.contains("б.вр.")) 
            return MorphTense.FUTURE;
        return MorphTense.UNDEFINED;
    }

    /**
     * Время (для глаголов)
     */
    public MorphTense setTense(MorphTense value) {
        if (value == MorphTense.PAST) 
            _addAttr("п.вр.");
        if (value == MorphTense.PRESENT) 
            _addAttr("н.вр.");
        if (value == MorphTense.FUTURE) 
            _addAttr("б.вр.");
        return value;
    }


    /**
     * Аспект (совершенный - несовершенный)
     */
    public MorphAspect getAspect() {
        if (m_Attrs.contains("нес.в.")) 
            return MorphAspect.IMPERFECTIVE;
        if (m_Attrs.contains("сов.в.")) 
            return MorphAspect.PERFECTIVE;
        return MorphAspect.UNDEFINED;
    }

    /**
     * Аспект (совершенный - несовершенный)
     */
    public MorphAspect setAspect(MorphAspect value) {
        if (value == MorphAspect.IMPERFECTIVE) 
            _addAttr("нес.в.");
        if (value == MorphAspect.PERFECTIVE) 
            _addAttr("сов.в.");
        return value;
    }


    /**
     * Наклонение (для глаголов)
     */
    public MorphMood getMood() {
        if (m_Attrs.contains("пов.накл.")) 
            return MorphMood.IMPERATIVE;
        return MorphMood.UNDEFINED;
    }

    /**
     * Наклонение (для глаголов)
     */
    public MorphMood setMood(MorphMood value) {
        if (value == MorphMood.IMPERATIVE) 
            _addAttr("пов.накл.");
        return value;
    }


    /**
     * Залог (для глаголов)
     */
    public MorphVoice getVoice() {
        if (m_Attrs.contains("дейст.з.")) 
            return MorphVoice.ACTIVE;
        if (m_Attrs.contains("страд.з.")) 
            return MorphVoice.PASSIVE;
        return MorphVoice.UNDEFINED;
    }

    /**
     * Залог (для глаголов)
     */
    public MorphVoice setVoice(MorphVoice value) {
        if (value == MorphVoice.ACTIVE) 
            _addAttr("дейст.з.");
        if (value == MorphVoice.PASSIVE) 
            _addAttr("страд.з.");
        return value;
    }


    /**
     * Форма (краткая, синонимичная)
     */
    public MorphForm getForm() {
        if (m_Attrs.contains("к.ф.")) 
            return MorphForm.SHORT;
        if (m_Attrs.contains("синоним.форма")) 
            return MorphForm.SYNONYM;
        if (isSynonymForm()) 
            return MorphForm.SYNONYM;
        return MorphForm.UNDEFINED;
    }


    /**
     * Синонимическая форма
     */
    public boolean isSynonymForm() {
        return getValue(0);
    }

    /**
     * Синонимическая форма
     */
    public boolean setSynonymForm(boolean value) {
        setValue(0, value);
        return value;
    }


    @Override
    public String toString() {
        if (m_Attrs.size() == 0 && m_Value == ((short)0)) 
            return "";
        StringBuilder res = new StringBuilder();
        if (isSynonymForm()) 
            res.append("синоним.форма ");
        for(int i = 0; i < m_Attrs.size(); i++) {
            res.append(m_Attrs.get(i)).append(" ");
        }
        return com.pullenti.n2j.Utils.trimEnd(res.toString());
    }

    public int id;
    public MorphMiscInfo() {
    }
}
