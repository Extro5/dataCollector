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
public class InstrumentBlockReferent extends com.pullenti.ner.Referent {

    public InstrumentBlockReferent(String typename) {
        super((String)com.pullenti.n2j.Utils.notnull(typename, OBJ_TYPENAME));
        setInstanceOf(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "INSTRBLOCK";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_KIND2 = "KIND_SEC";

    public static final String ATTR_CHILD = "CHILD";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_EXPIRED = "EXPIRED";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_MINNUMBER = "MINNUMBER";

    public static final String ATTR_SUBNUMBER = "ADDNUMBER";

    public static final String ATTR_SUB2NUMBER = "ADDSECNUMBER";

    public static final String ATTR_SUB3NUMBER = "ADDTHIRDNUMBER";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        InstrumentKind ki = getKind();
        String str;
        str = (String)com.pullenti.n2j.Utils.cast(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(ki.toString(), lang), String.class);
        if (str != null) {
            res.append(str);
            if (getKind2() != InstrumentKind.UNDEFINED) {
                str = (String)com.pullenti.n2j.Utils.cast(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(getKind2().toString(), lang), String.class);
                if (str != null) 
                    res.append(" (").append(str).append(")");
            }
        }
        if (getNumber() > 0) {
            if (ki == InstrumentKind.TABLE) 
                res.append(" ").append(getChildren().size()).append(" строк, ").append(getNumber()).append(" столбцов");
            else {
                res.append(" №").append(getNumber());
                if (getSubNumber() > 0) {
                    res.append(".").append(getSubNumber());
                    if (getSubNumber2() > 0) {
                        res.append(".").append(getSubNumber2());
                        if (getSubNumber3() > 0) 
                            res.append(".").append(getSubNumber3());
                    }
                }
                if (getMinNumber() > 0) {
                    for(int i = res.length() - 1; i >= 0; i--) {
                        if (res.charAt(i) == ' ' || res.charAt(i) == '.') {
                            res.insert(i + 1, ((Integer)getMinNumber()).toString() + "-");
                            break;
                        }
                    }
                }
            }
        }
        boolean ignoreRef = false;
        if (isExpired()) {
            res.append(" (утратить силу)");
            ignoreRef = true;
        }
        else if (ki != InstrumentKind.EDITIONS && ki != InstrumentKind.APPROVED && (getRef() instanceof com.pullenti.ner.decree.DecreeReferent)) {
            res.append(" (*)");
            ignoreRef = true;
        }
        if ((((str = getStringValue(ATTR_NAME)))) == null) 
            str = getStringValue(ATTR_VALUE);
        if (str != null) {
            if (str.length() > 100) 
                str = str.substring(0, 0+100) + "...";
            res.append(" \"").append(str).append("\"");
        }
        else if (!ignoreRef && (getRef() instanceof com.pullenti.ner.Referent) && (lev < 30)) 
            res.append(" \"").append(getRef().toString(shortVariant, lang, lev + 1)).append("\"");
        return res.toString().trim();
    }

    /**
     * Классификатор
     */
    public InstrumentKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return InstrumentKind.UNDEFINED;
        try {
            if (com.pullenti.n2j.Utils.stringsEq(s, "Part") || com.pullenti.n2j.Utils.stringsEq(s, "Base") || com.pullenti.n2j.Utils.stringsEq(s, "Special")) 
                return InstrumentKind.UNDEFINED;
            Object res = InstrumentKind.of(s);
            if (res instanceof InstrumentKind) 
                return (InstrumentKind)res;
        } catch(Exception ex1426) {
        }
        return InstrumentKind.UNDEFINED;
    }

    /**
     * Классификатор
     */
    public InstrumentKind setKind(InstrumentKind _value) {
        if (_value != InstrumentKind.UNDEFINED) 
            addSlot(ATTR_KIND, _value.toString().toUpperCase(), true, 0);
        return _value;
    }


    /**
     * Классификатор дополнительный
     */
    public InstrumentKind getKind2() {
        String s = getStringValue(ATTR_KIND2);
        if (s == null) 
            return InstrumentKind.UNDEFINED;
        try {
            Object res = InstrumentKind.of(s);
            if (res instanceof InstrumentKind) 
                return (InstrumentKind)res;
        } catch(Exception ex1427) {
        }
        return InstrumentKind.UNDEFINED;
    }

    /**
     * Классификатор дополнительный
     */
    public InstrumentKind setKind2(InstrumentKind _value) {
        if (_value != InstrumentKind.UNDEFINED) 
            addSlot(ATTR_KIND2, _value.toString().toUpperCase(), true, 0);
        return _value;
    }


    /**
     * Значение
     */
    public String getValue() {
        return getStringValue(ATTR_VALUE);
    }

    /**
     * Значение
     */
    public String setValue(String _value) {
        addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    public com.pullenti.ner.Referent getRef() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_REF), com.pullenti.ner.Referent.class);
    }


    public boolean isExpired() {
        return com.pullenti.n2j.Utils.stringsEq(getStringValue(ATTR_EXPIRED), "true");
    }

    public boolean setExpired(boolean _value) {
        addSlot(ATTR_EXPIRED, (_value ? "true" : null), true, 0);
        return _value;
    }


    /**
     * Номер (для диапазона - максимальный номер)
     */
    public int getNumber() {
        String str = getStringValue(ATTR_NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1428 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1429 = com.pullenti.n2j.Utils.parseInteger(str, inoutarg1428);
        i = (inoutarg1428.value != null ? inoutarg1428.value : 0);
        if (inoutres1429) 
            return i;
        return 0;
    }

    /**
     * Номер (для диапазона - максимальный номер)
     */
    public int setNumber(int _value) {
        addSlot(ATTR_NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * Дополнительный номер (через точку за основным)
     */
    public int getSubNumber() {
        String str = getStringValue(ATTR_SUBNUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1430 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1431 = com.pullenti.n2j.Utils.parseInteger(str, inoutarg1430);
        i = (inoutarg1430.value != null ? inoutarg1430.value : 0);
        if (inoutres1431) 
            return i;
        return 0;
    }

    /**
     * Дополнительный номер (через точку за основным)
     */
    public int setSubNumber(int _value) {
        addSlot(ATTR_SUBNUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * Дополнительный второй номер (через точку за дополнительным)
     */
    public int getSubNumber2() {
        String str = getStringValue(ATTR_SUB2NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1432 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1433 = com.pullenti.n2j.Utils.parseInteger(str, inoutarg1432);
        i = (inoutarg1432.value != null ? inoutarg1432.value : 0);
        if (inoutres1433) 
            return i;
        return 0;
    }

    /**
     * Дополнительный второй номер (через точку за дополнительным)
     */
    public int setSubNumber2(int _value) {
        addSlot(ATTR_SUB2NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * Дополнительный третий номер (через точку за вторым дополнительным)
     */
    public int getSubNumber3() {
        String str = getStringValue(ATTR_SUB3NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1434 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1435 = com.pullenti.n2j.Utils.parseInteger(str, inoutarg1434);
        i = (inoutarg1434.value != null ? inoutarg1434.value : 0);
        if (inoutres1435) 
            return i;
        return 0;
    }

    /**
     * Дополнительный третий номер (через точку за вторым дополнительным)
     */
    public int setSubNumber3(int _value) {
        addSlot(ATTR_SUB3NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * Минимальный номер, если задан диапазон
     */
    public int getMinNumber() {
        String str = getStringValue(ATTR_MINNUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1436 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1437 = com.pullenti.n2j.Utils.parseInteger(str, inoutarg1436);
        i = (inoutarg1436.value != null ? inoutarg1436.value : 0);
        if (inoutres1437) 
            return i;
        return 0;
    }

    /**
     * Минимальный номер, если задан диапазон
     */
    public int setMinNumber(int _value) {
        addSlot(ATTR_MINNUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * Наименование
     */
    public String getName() {
        return getStringValue(ATTR_NAME);
    }

    /**
     * Наименование
     */
    public String setName(String _value) {
        addSlot(ATTR_NAME, _value, true, 0);
        return _value;
    }


    /**
     * Внутреннее содержимое
     */
    public java.util.ArrayList<InstrumentBlockReferent> getChildren() {
        if (m_Children == null) {
            m_Children = new java.util.ArrayList< >();
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_CHILD)) {
                    if (s.getValue() instanceof InstrumentBlockReferent) 
                        m_Children.add((InstrumentBlockReferent)com.pullenti.n2j.Utils.cast(s.getValue(), InstrumentBlockReferent.class));
                }
            }
        }
        return m_Children;
    }


    private java.util.ArrayList<InstrumentBlockReferent> m_Children;

    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        m_Children = null;
        return super.addSlot(attrName, attrValue, clearOldValue, statCount);
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return obj == this;
    }

    public static String kindToRusString(InstrumentKind typ, boolean shortVal) {
        if (typ == InstrumentKind.APPENDIX) 
            return (shortVal ? "прил." : "Приложение");
        if (typ == InstrumentKind.CLAUSE) 
            return (shortVal ? "ст." : "Статья");
        if (typ == InstrumentKind.CHAPTER) 
            return (shortVal ? "гл." : "Глава");
        if (typ == InstrumentKind.ITEM) 
            return (shortVal ? "п." : "Пункт");
        if (typ == InstrumentKind.PARAGRAPH) 
            return (shortVal ? "§" : "Параграф");
        if (typ == InstrumentKind.SUBPARAGRAPH) 
            return (shortVal ? "подпарагр." : "Подпараграф");
        if (typ == InstrumentKind.DOCPART) 
            return (shortVal ? "ч." : "Часть");
        if (typ == InstrumentKind.SECTION) 
            return (shortVal ? "раздел" : "Раздел");
        if (typ == InstrumentKind.INTERNALDOCUMENT) 
            return "Документ";
        if (typ == InstrumentKind.SUBITEM) 
            return (shortVal ? "пп." : "Подпункт");
        if (typ == InstrumentKind.SUBSECTION) 
            return (shortVal ? "подразд." : "Подраздел");
        if (typ == InstrumentKind.CLAUSEPART) 
            return (shortVal ? "ч." : "Часть");
        if (typ == InstrumentKind.INDENTION) 
            return (shortVal ? "абз." : "Абзац");
        if (typ == InstrumentKind.PREAMBLE) 
            return (shortVal ? "преамб." : "Преамбула");
        return null;
    }
    public InstrumentBlockReferent() {
        this(null);
    }
}
