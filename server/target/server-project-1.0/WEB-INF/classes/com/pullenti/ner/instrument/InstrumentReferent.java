/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument;

/**
 * Представление нормативно-правового документа или его части
 */
public class InstrumentReferent extends InstrumentBlockReferent {

    public InstrumentReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.instrument.internal.MetaInstrument.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "INSTRUMENT";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_REGNUMBER = "NUMBER";

    public static final String ATTR_CASENUMBER = "CASENUMBER";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_SIGNER = "SIGNER";

    public static final String ATTR_SOURCE = "SOURCE";

    public static final String ATTR_GEO = "GEO";

    public static final String ATTR_PART = "PART";

    public static final String ATTR_APPENDIX = "APPENDIX";

    public static final String ATTR_PARTICIPANT = "PARTICIPANT";

    public static final String ATTR_ARTEFACT = "ARTEFACT";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str;
        if ((((str = getStringValue(ATTR_APPENDIX)))) != null) {
            java.util.ArrayList<String> strs = getStringValues(ATTR_APPENDIX);
            if (strs.size() == 1) 
                res.append("Приложение").append((str.length() == 0 ? "" : " ")).append(str).append("; ");
            else {
                res.append("Приложения ");
                for(int i = 0; i < strs.size(); i++) {
                    if (i > 0) 
                        res.append(",");
                    res.append(strs.get(i));
                }
                res.append("; ");
            }
        }
        if ((((str = getStringValue(ATTR_PART)))) != null) 
            res.append("Часть ").append(str).append("; ");
        if (getTyp() != null) 
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(getTyp()));
        else 
            res.append("Документ");
        if (getRegNumber() != null) {
            res.append(" №").append(getRegNumber());
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REGNUMBER) && com.pullenti.n2j.Utils.stringsNe(s.getValue().toString(), getRegNumber())) 
                    res.append("/").append(s.getValue());
            }
        }
        if (getCaseNumber() != null) 
            res.append(" дело №").append(getCaseNumber());
        String dt = getStringValue(ATTR_DATE);
        if (dt != null) 
            res.append(" от ").append(dt);
        if ((((str = getStringValue(InstrumentBlockReferent.ATTR_NAME)))) != null) {
            if (str.length() > 100) 
                str = str.substring(0, 0+100) + "...";
            res.append(" \"").append(str).append("\"");
        }
        if ((((str = getStringValue(ATTR_GEO)))) != null) 
            res.append(" (").append(str).append(")");
        return res.toString().trim();
    }

    /**
     * Тип
     */
    public String getTyp() {
        return getStringValue(ATTR_TYPE);
    }

    /**
     * Тип
     */
    public String setTyp(String _value) {
        addSlot(ATTR_TYPE, _value, true, 0);
        return _value;
    }


    /**
     * Номер
     */
    public String getRegNumber() {
        return getStringValue(ATTR_REGNUMBER);
    }

    /**
     * Номер
     */
    public String setRegNumber(String _value) {
        if (com.pullenti.n2j.Utils.isNullOrEmpty(_value)) {
            addSlot(ATTR_REGNUMBER, null, true, 0);
            return _value;
        }
        if (".,".indexOf(_value.charAt(_value.length() - 1)) >= 0) 
            _value = _value.substring(0, 0+(_value.length() - 1));
        addSlot(ATTR_REGNUMBER, _value, true, 0);
        return _value;
    }


    /**
     * Номер дела
     */
    public String getCaseNumber() {
        return getStringValue(ATTR_CASENUMBER);
    }

    /**
     * Номер дела
     */
    public String setCaseNumber(String _value) {
        if (com.pullenti.n2j.Utils.isNullOrEmpty(_value)) 
            return _value;
        if (".,".indexOf(_value.charAt(_value.length() - 1)) >= 0) 
            _value = _value.substring(0, 0+(_value.length() - 1));
        addSlot(ATTR_CASENUMBER, _value, true, 0);
        return _value;
    }


    /**
     * Дата подписания
     */
    public java.time.LocalDateTime getDate() {
        String s = getStringValue(ATTR_DATE);
        if (s == null) 
            return null;
        return com.pullenti.ner.decree.internal.DecreeHelper.parseDateTime(s);
    }


    public boolean addDate(Object dt) {
        if (dt == null) 
            return false;
        if (dt instanceof com.pullenti.ner.decree.internal.DecreeToken) {
            if ((((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.n2j.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class))).ref instanceof com.pullenti.ner.ReferentToken) 
                return addDate((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.n2j.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class))).ref, com.pullenti.ner.ReferentToken.class))).referent);
            if ((((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.n2j.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class))).value != null) {
                addSlot(ATTR_DATE, (((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.n2j.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class))).value, true, 0);
                return true;
            }
            return false;
        }
        if (dt instanceof com.pullenti.ner.ReferentToken) 
            return addDate((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(dt, com.pullenti.ner.ReferentToken.class))).referent);
        if (dt instanceof com.pullenti.ner.date.DateReferent) {
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.n2j.Utils.cast(dt, com.pullenti.ner.date.DateReferent.class);
            int year = dr.getYear();
            int mon = dr.getMonth();
            int day = dr.getDay();
            if (year == 0) 
                return dr.getPointer() == com.pullenti.ner.date.DatePointerType.UNDEFINED;
            java.time.LocalDateTime exDate = getDate();
            if (exDate != null && exDate.getYear() == year) {
                if (mon == 0 && exDate.getMonthValue() > 0) 
                    return false;
                if (day == 0 && exDate.getDayOfMonth() > 0) 
                    return false;
                boolean delExist = false;
                if (mon > 0 && exDate.getMonthValue() == 0) 
                    delExist = true;
                if (delExist) {
                    for(com.pullenti.ner.Slot s : getSlots()) {
                        if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_DATE)) {
                            getSlots().remove(s);
                            break;
                        }
                    }
                }
            }
            StringBuilder tmp = new StringBuilder();
            tmp.append(year);
            if (mon > 0) 
                tmp.append(".").append(String.format("%02d", mon));
            if (day > 0) 
                tmp.append(".").append(String.format("%02d", day));
            addSlot(com.pullenti.ner.decree.DecreeReferent.ATTR_DATE, tmp.toString(), false, 0);
            return true;
        }
        if (dt instanceof String) {
            addSlot(ATTR_DATE, (String)com.pullenti.n2j.Utils.cast(dt, String.class), true, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        return obj == this;
    }
}
