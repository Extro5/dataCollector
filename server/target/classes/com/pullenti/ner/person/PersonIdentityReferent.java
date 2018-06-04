/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.person;

/**
 * Удостоверение личности (паспорт и пр.)
 */
public class PersonIdentityReferent extends com.pullenti.ner.Referent {

    public PersonIdentityReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.person.internal.MetaPersonIdentity.globalMeta);
    }

    public static final String OBJ_TYPENAME = "PERSONIDENTITY";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_ORG = "ORG";

    public static final String ATTR_STATE = "STATE";

    public static final String ATTR_ADDRESS = "ADDRESS";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append((String)com.pullenti.n2j.Utils.notnull(getTyp(), "?"));
        if (getNumber() != null) 
            res.append(" №").append(getNumber());
        if (getState() != null) 
            res.append(", ").append(getState().toString(true, lang, lev + 1));
        if (!shortVariant) {
            String dat = getStringValue(ATTR_DATE);
            String _org = getStringValue(ATTR_ORG);
            if (dat != null || _org != null) {
                res.append(", выдан");
                if (dat != null) 
                    res.append(" ").append(dat);
                if (_org != null) 
                    res.append(" ").append(_org);
            }
        }
        return res.toString();
    }

    /**
     * Тип документа
     */
    public String getTyp() {
        return getStringValue(ATTR_TYPE);
    }

    /**
     * Тип документа
     */
    public String setTyp(String value) {
        addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    /**
     * Номер (вместе с серией)
     */
    public String getNumber() {
        return getStringValue(ATTR_NUMBER);
    }

    /**
     * Номер (вместе с серией)
     */
    public String setNumber(String value) {
        addSlot(ATTR_NUMBER, value, true, 0);
        return value;
    }


    /**
     * Государство
     */
    public com.pullenti.ner.Referent getState() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_STATE), com.pullenti.ner.Referent.class);
    }

    /**
     * Государство
     */
    public com.pullenti.ner.Referent setState(com.pullenti.ner.Referent value) {
        addSlot(ATTR_STATE, value, true, 0);
        return value;
    }


    /**
     * Адрес регистрации
     */
    public com.pullenti.ner.Referent getAddress() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_ADDRESS), com.pullenti.ner.Referent.class);
    }

    /**
     * Адрес регистрации
     */
    public com.pullenti.ner.Referent setAddress(com.pullenti.ner.Referent value) {
        addSlot(ATTR_ADDRESS, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        PersonIdentityReferent id = (PersonIdentityReferent)com.pullenti.n2j.Utils.cast(obj, PersonIdentityReferent.class);
        if (id == null) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(getTyp(), id.getTyp())) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(getNumber(), id.getNumber())) 
            return false;
        if (getState() != null && id.getState() != null) {
            if (getState() != id.getState()) 
                return false;
        }
        return true;
    }
}
