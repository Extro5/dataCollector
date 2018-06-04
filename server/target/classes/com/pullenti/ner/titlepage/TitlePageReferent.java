/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.titlepage;

/**
 * Сущность, описывающая информацию из заголовков статей, книг, диссертация и пр.
 */
public class TitlePageReferent extends com.pullenti.ner.Referent {

    public TitlePageReferent(String name) {
        super((String)com.pullenti.n2j.Utils.notnull(name, OBJ_TYPENAME));
        setInstanceOf(com.pullenti.ner.titlepage.internal.MetaTitleInfo.globalMeta);
    }

    public static final String OBJ_TYPENAME = "TITLEPAGE";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_AUTHOR = "AUTHOR";

    public static final String ATTR_SUPERVISOR = "SUPERVISOR";

    public static final String ATTR_EDITOR = "EDITOR";

    public static final String ATTR_CONSULTANT = "CONSULTANT";

    public static final String ATTR_OPPONENT = "OPPONENT";

    public static final String ATTR_TRANSLATOR = "TRANSLATOR";

    public static final String ATTR_AFFIRMANT = "AFFIRMANT";

    public static final String ATTR_ORG = "ORGANIZATION";

    public static final String ATTR_DEP = "DEPARTMENT";

    public static final String ATTR_STUDENTYEAR = "STUDENTYEAR";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_CITY = "CITY";

    public static final String ATTR_SPECIALITY = "SPECIALITY";

    public static final String ATTR_ATTR = "ATTR";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = getStringValue(ATTR_NAME);
        res.append("\"").append(((String)com.pullenti.n2j.Utils.notnull(str, "?"))).append("\"");
        if (!shortVariant) {
            for(com.pullenti.ner.Slot r : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), ATTR_TYPE)) {
                    res.append(" (").append(r.getValue()).append(")");
                    break;
                }
            }
            for(com.pullenti.ner.Slot r : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), ATTR_AUTHOR) && (r.getValue() instanceof com.pullenti.ner.Referent)) 
                    res.append(", ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(r.getValue(), com.pullenti.ner.Referent.class))).toString(true, lang, 0));
            }
        }
        if (getCity() != null && !shortVariant) 
            res.append(", ").append(getCity().toString(true, lang, 0));
        if (getDate() != null) {
            if (!shortVariant) 
                res.append(", ").append(getDate().toString(true, lang, 0));
            else 
                res.append(", ").append(getDate().getYear());
        }
        return res.toString();
    }

    /**
     * Список типов
     */
    public java.util.ArrayList<String> getTypes() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public void addType(String typ) {
        if (!com.pullenti.n2j.Utils.isNullOrEmpty(typ)) {
            addSlot(ATTR_TYPE, typ.toLowerCase(), false, 0);
            correctData();
        }
    }

    /**
     * Названия (одно или несколько)
     */
    public java.util.ArrayList<String> getNames() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public com.pullenti.ner.core.Termin addName(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(begin, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(begin, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.getEndToken() == end) {
                begin = begin.getNext();
                end = end.getPrevious();
            }
        }
        String val = com.pullenti.ner.core.MiscHelper.getTextValue(begin, end, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
        if (val == null) 
            return null;
        if (val.endsWith(".") && !val.endsWith("..")) 
            val = val.substring(0, 0+(val.length() - 1)).trim();
        addSlot(ATTR_NAME, val, false, 0);
        return new com.pullenti.ner.core.Termin(val.toUpperCase(), new com.pullenti.morph.MorphLang(null), false);
    }

    private void correctData() {
    }

    /**
     * Дата
     */
    public com.pullenti.ner.date.DateReferent getDate() {
        return (com.pullenti.ner.date.DateReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_DATE), com.pullenti.ner.date.DateReferent.class);
    }

    /**
     * Дата
     */
    public com.pullenti.ner.date.DateReferent setDate(com.pullenti.ner.date.DateReferent value) {
        if (value == null) 
            return value;
        if (getDate() == null) {
            addSlot(ATTR_DATE, value, true, 0);
            return value;
        }
        if (getDate().getMonth() > 0 && value.getMonth() == 0) 
            return value;
        if (getDate().getDay() > 0 && value.getDay() == 0) 
            return value;
        addSlot(ATTR_DATE, value, true, 0);
        return value;
    }


    /**
     * Номер курса (для студентов)
     */
    public int getStudentYear() {
        return getIntValue(ATTR_STUDENTYEAR, 0);
    }

    /**
     * Номер курса (для студентов)
     */
    public int setStudentYear(int value) {
        addSlot(ATTR_STUDENTYEAR, value, true, 0);
        return value;
    }


    /**
     * Организация
     */
    public com.pullenti.ner.org.OrganizationReferent getOrg() {
        return (com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_ORG), com.pullenti.ner.org.OrganizationReferent.class);
    }

    /**
     * Организация
     */
    public com.pullenti.ner.org.OrganizationReferent setOrg(com.pullenti.ner.org.OrganizationReferent value) {
        addSlot(ATTR_ORG, value, true, 0);
        return value;
    }


    /**
     * Город
     */
    public com.pullenti.ner.geo.GeoReferent getCity() {
        return (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_CITY), com.pullenti.ner.geo.GeoReferent.class);
    }

    /**
     * Город
     */
    public com.pullenti.ner.geo.GeoReferent setCity(com.pullenti.ner.geo.GeoReferent value) {
        addSlot(ATTR_CITY, value, true, 0);
        return value;
    }


    /**
     * Специальность
     */
    public String getSpeciality() {
        return getStringValue(ATTR_SPECIALITY);
    }

    /**
     * Специальность
     */
    public String setSpeciality(String value) {
        addSlot(ATTR_SPECIALITY, value, true, 0);
        return value;
    }

    public TitlePageReferent() {
        this(null);
    }
}
