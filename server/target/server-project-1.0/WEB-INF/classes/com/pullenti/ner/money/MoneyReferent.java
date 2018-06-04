/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.money;

/**
 * Представление денежных сумм
 */
public class MoneyReferent extends com.pullenti.ner.Referent {

    public MoneyReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.money.internal.MoneyMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "MONEY";

    public static final String ATTR_CURRENCY = "CURRENCY";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_ALTVALUE = "ALTVALUE";

    public static final String ATTR_REST = "REST";

    public static final String ATTR_ALTREST = "ALTREST";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        long v = getValue();
        int r = getRest();
        if (v > ((long)0) || r > 0) {
            res.append(v);
            int cou = 0;
            for(int i = res.length() - 1; i > 0; i--) {
                if ((++cou) == 3) {
                    res.insert(i, '.');
                    cou = 0;
                }
            }
        }
        else 
            res.append("?");
        if (r > 0) 
            res.append(",").append(String.format("%02d", r));
        res.append(" ").append(getCurrency());
        return res.toString();
    }

    /**
     * Тип валюты (3-х значный код ISO 4217)
     */
    public String getCurrency() {
        return getStringValue(ATTR_CURRENCY);
    }

    /**
     * Тип валюты (3-х значный код ISO 4217)
     */
    public String setCurrency(String _value) {
        addSlot(ATTR_CURRENCY, _value, true, 0);
        return _value;
    }


    /**
     * Значение
     */
    public long getValue() {
        String val = getStringValue(ATTR_VALUE);
        if (val == null) 
            return (long)0;
        long v;
        com.pullenti.n2j.Outargwrapper<Long> inoutarg1578 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1579 = com.pullenti.n2j.Utils.parseLong(val, inoutarg1578);
        v = (inoutarg1578.value != null ? inoutarg1578.value : 0);
        if (!inoutres1579) 
            return (long)0;
        return v;
    }

    /**
     * Значение
     */
    public long setValue(long _value) {
        addSlot(ATTR_VALUE, ((Long)_value).toString(), true, 0);
        return _value;
    }


    /**
     * Альтернативное значение (если есть, то значит неправильно написали сумму 
     *  числом и далее прописью в скобках)
     */
    public Long getAltValue() {
        String val = getStringValue(ATTR_ALTVALUE);
        if (val == null) 
            return null;
        long v;
        com.pullenti.n2j.Outargwrapper<Long> inoutarg1580 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1581 = com.pullenti.n2j.Utils.parseLong(val, inoutarg1580);
        v = (inoutarg1580.value != null ? inoutarg1580.value : 0);
        if (!inoutres1581) 
            return null;
        return v;
    }

    /**
     * Альтернативное значение (если есть, то значит неправильно написали сумму 
     *  числом и далее прописью в скобках)
     */
    public Long setAltValue(Long _value) {
        addSlot(ATTR_ALTVALUE, (_value == null ? null : ((Long)_value).toString()), true, 0);
        return _value;
    }


    /**
     * Остаток (от 0 до 99) - копеек, центов и т.п.
     */
    public int getRest() {
        String val = getStringValue(ATTR_REST);
        if (val == null) 
            return 0;
        int v;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1582 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1583 = com.pullenti.n2j.Utils.parseInteger(val, inoutarg1582);
        v = (inoutarg1582.value != null ? inoutarg1582.value : 0);
        if (!inoutres1583) 
            return 0;
        return v;
    }

    /**
     * Остаток (от 0 до 99) - копеек, центов и т.п.
     */
    public int setRest(int _value) {
        if (_value > 0) 
            addSlot(ATTR_REST, ((Integer)_value).toString(), true, 0);
        else 
            addSlot(ATTR_REST, null, true, 0);
        return _value;
    }


    /**
     * Остаток (от 0 до 99) - копеек, центов и т.п.
     */
    public Integer getAltRest() {
        String val = getStringValue(ATTR_ALTREST);
        if (val == null) 
            return null;
        int v;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1584 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1585 = com.pullenti.n2j.Utils.parseInteger(val, inoutarg1584);
        v = (inoutarg1584.value != null ? inoutarg1584.value : 0);
        if (!inoutres1585) 
            return null;
        return v;
    }

    /**
     * Остаток (от 0 до 99) - копеек, центов и т.п.
     */
    public Integer setAltRest(Integer _value) {
        if (_value != null && _value > 0) 
            addSlot(ATTR_ALTREST, ((Integer)_value).toString(), true, 0);
        else 
            addSlot(ATTR_ALTREST, null, true, 0);
        return _value;
    }


    /**
     * Действительное значение
     */
    public double getRealValue() {
        return ((double)getValue()) + ((((double)getRest()) / ((double)100)));
    }

    /**
     * Действительное значение
     */
    public double setRealValue(double _value) {
        setValue((long)_value);
        double re = ((_value - ((double)getValue()))) * ((double)100);
        setRest((int)((re + 0.0001)));
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        MoneyReferent s = (MoneyReferent)com.pullenti.n2j.Utils.cast(obj, MoneyReferent.class);
        if (s == null) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(s.getCurrency(), getCurrency())) 
            return false;
        if (s.getValue() != getValue()) 
            return false;
        if (s.getRest() != getRest()) 
            return false;
        if (s.getAltValue() != getAltValue()) 
            return false;
        if (s.getAltRest() != getAltRest()) 
            return false;
        return true;
    }

    public static MoneyReferent _new801(String _arg1, double _arg2) {
        MoneyReferent res = new MoneyReferent();
        res.setCurrency(_arg1);
        res.setRealValue(_arg2);
        return res;
    }
}
