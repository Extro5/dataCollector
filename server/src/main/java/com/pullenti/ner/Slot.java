/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Значение атрибута в конкретном экземпляре сущности
 */
public class Slot {

    private String _typename;

    public String getTypeName() {
        return _typename;
    }

    public String setTypeName(String _value) {
        _typename = _value;
        return _typename;
    }


    public boolean isInternal() {
        return getTypeName() != null && getTypeName().charAt(0) == '@';
    }


    private Referent _owner;

    public Referent getOwner() {
        return _owner;
    }

    public Referent setOwner(Referent _value) {
        _owner = _value;
        return _owner;
    }


    private Object m_Value;

    public Object getValue() {
        return m_Value;
    }

    public Object setValue(Object _value) {
        m_Value = _value;
        if (m_Value != null) {
            if (m_Value instanceof Referent) {
            }
            else if (m_Value instanceof Token) {
            }
            else if (m_Value instanceof String) {
            }
            else 
                m_Value = m_Value.toString();
        }
        else {
        }
        return _value;
    }


    private int _count;

    /**
     * Статистика встречаемости в объектах 
     *  (например, используется для имён организаций, чтобы статистически определить 
     *  правильное написание имени)
     */
    public int getCount() {
        return _count;
    }

    /**
     * Статистика встречаемости в объектах 
     *  (например, используется для имён организаций, чтобы статистически определить 
     *  правильное написание имени)
     */
    public int setCount(int _value) {
        _count = _value;
        return _count;
    }


    /**
     * Ссылка на атрибут метамодели
     */
    public Feature getDefiningFeature() {
        if (getOwner() == null) 
            return null;
        if (getOwner().getInstanceOf() == null) 
            return null;
        return getOwner().getInstanceOf().findFeature(getTypeName());
    }


    @Override
    public String toString() {
        return toString(com.pullenti.morph.MorphLang.UNKNOWN);
    }

    public String toString(com.pullenti.morph.MorphLang lang) {
        StringBuilder res = new StringBuilder();
        Feature attr = getDefiningFeature();
        if (attr != null) {
            if (getCount() > 0) 
                res.append(attr.getCaption()).append(" (").append(getCount()).append("): ");
            else 
                res.append(attr.getCaption()).append(": ");
        }
        else 
            res.append(getTypeName()).append(": ");
        if (getValue() != null) {
            if (getValue() instanceof Referent) 
                res.append((((Referent)com.pullenti.n2j.Utils.cast(getValue(), Referent.class))).toString(false, lang, 0));
            else if (attr == null) 
                res.append(getValue().toString());
            else 
                res.append(attr.convertInnerValueToOuterValue(getValue(), new com.pullenti.morph.MorphLang(null)));
        }
        return res.toString();
    }

    public String convertValueToString(com.pullenti.morph.MorphLang lang) {
        if (getValue() == null) 
            return null;
        Feature attr = getDefiningFeature();
        if (attr == null) 
            return getValue().toString();
        Object v = attr.convertInnerValueToOuterValue(getValue(), lang);
        if (v == null) 
            return null;
        if (v instanceof String) 
            return (String)com.pullenti.n2j.Utils.cast(v, String.class);
        else 
            return v.toString();
    }

    private Object _tag;

    /**
     * Используется произвольным образом
     */
    public Object getTag() {
        return _tag;
    }

    /**
     * Используется произвольным образом
     */
    public Object setTag(Object _value) {
        _tag = _value;
        return _tag;
    }


    /**
     * Удалить слот из сущности
     */
    public void delete() {
        if (getOwner() != null && getOwner().getSlots().contains(this)) {
            getOwner().getSlots().remove(this);
            setOwner(null);
        }
    }

    public static Slot _new1045(String _arg1, Object _arg2, int _arg3) {
        Slot res = new Slot();
        res.setTypeName(_arg1);
        res.setTag(_arg2);
        res.setCount(_arg3);
        return res;
    }
    public static Slot _new2772(String _arg1, Object _arg2, int _arg3) {
        Slot res = new Slot();
        res.setTypeName(_arg1);
        res.setValue(_arg2);
        res.setCount(_arg3);
        return res;
    }
    public Slot() {
    }
}
