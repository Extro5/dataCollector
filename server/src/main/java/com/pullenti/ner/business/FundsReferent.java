/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.business;

/**
 * Ценные бумаги (акции, доли в уставном капитале и пр.)
 */
public class FundsReferent extends com.pullenti.ner.Referent {

    public FundsReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.business.internal.FundsMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "FUNDS";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_SOURCE = "SOURCE";

    public static final String ATTR_PERCENT = "PERCENT";

    public static final String ATTR_COUNT = "COUNT";

    public static final String ATTR_SUM = "SUM";

    public static final String ATTR_PRICE = "PRICE";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getTyp() != null) 
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(getTyp()));
        else {
            String _kind = getStringValue(ATTR_KIND);
            if (_kind != null) 
                _kind = (String)com.pullenti.n2j.Utils.cast(com.pullenti.ner.business.internal.FundsMeta.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(_kind, new com.pullenti.morph.MorphLang(null)), String.class);
            if (_kind != null) 
                res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(_kind));
            else 
                res.append("?");
        }
        if (getSource() != null) 
            res.append("; ").append(getSource().toString(shortVariant, lang, 0));
        if (getCount() > ((long)0)) 
            res.append("; кол-во ").append(getCount());
        if (getPercent() > 0) 
            res.append("; ").append(getPercent()).append("%");
        if (!shortVariant) {
            if (getSum() != null) 
                res.append("; ").append(getSum().toString(false, lang, 0));
            if (getPrice() != null) 
                res.append("; номинал ").append(getPrice().toString(false, lang, 0));
        }
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getSource();
    }


    /**
     * Классификатор ценной бумаги
     */
    public FundsKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return FundsKind.UNDEFINED;
        try {
            Object res = FundsKind.of(s);
            if (res instanceof FundsKind) 
                return (FundsKind)res;
        } catch(Exception ex449) {
        }
        return FundsKind.UNDEFINED;
    }

    /**
     * Классификатор ценной бумаги
     */
    public FundsKind setKind(FundsKind value) {
        if (value != FundsKind.UNDEFINED) 
            addSlot(ATTR_KIND, value.toString(), true, 0);
        else 
            addSlot(ATTR_KIND, null, true, 0);
        return value;
    }


    /**
     * Эмитент
     */
    public com.pullenti.ner.org.OrganizationReferent getSource() {
        return (com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_SOURCE), com.pullenti.ner.org.OrganizationReferent.class);
    }

    /**
     * Эмитент
     */
    public com.pullenti.ner.org.OrganizationReferent setSource(com.pullenti.ner.org.OrganizationReferent value) {
        addSlot(ATTR_SOURCE, value, true, 0);
        return value;
    }


    /**
     * Тип (например, привелигированная акция)
     */
    public String getTyp() {
        return getStringValue(ATTR_TYPE);
    }

    /**
     * Тип (например, привелигированная акция)
     */
    public String setTyp(String value) {
        addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    /**
     * Процент от общего количества
     */
    public float getPercent() {
        String val = getStringValue(ATTR_PERCENT);
        if (val == null) 
            return (float)0;
        float f;
        com.pullenti.n2j.Outargwrapper<Float> inoutarg452 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres453 = com.pullenti.n2j.Utils.parseFloat(val, inoutarg452);
        f = inoutarg452.value;
        if (!inoutres453) {
            com.pullenti.n2j.Outargwrapper<Float> inoutarg450 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres451 = com.pullenti.n2j.Utils.parseFloat(val.replace('.', ','), inoutarg450);
            f = inoutarg450.value;
            if (!inoutres451) 
                return (float)0;
        }
        return f;
    }

    /**
     * Процент от общего количества
     */
    public float setPercent(float value) {
        if (value > 0) 
            addSlot(ATTR_PERCENT, ((Float)value).toString().replace(',', '.'), true, 0);
        else 
            addSlot(ATTR_PERCENT, null, true, 0);
        return value;
    }


    /**
     * Количество
     */
    public long getCount() {
        String val = getStringValue(ATTR_COUNT);
        if (val == null) 
            return (long)0;
        long v;
        com.pullenti.n2j.Outargwrapper<Long> inoutarg454 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres455 = com.pullenti.n2j.Utils.parseLong(val, inoutarg454);
        v = (inoutarg454.value != null ? inoutarg454.value : 0);
        if (!inoutres455) 
            return (long)0;
        return v;
    }

    /**
     * Количество
     */
    public long setCount(long value) {
        addSlot(ATTR_COUNT, ((Long)value).toString(), true, 0);
        return value;
    }


    /**
     * Сумма за все акции
     */
    public com.pullenti.ner.money.MoneyReferent getSum() {
        return (com.pullenti.ner.money.MoneyReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_SUM), com.pullenti.ner.money.MoneyReferent.class);
    }

    /**
     * Сумма за все акции
     */
    public com.pullenti.ner.money.MoneyReferent setSum(com.pullenti.ner.money.MoneyReferent value) {
        addSlot(ATTR_SUM, value, true, 0);
        return value;
    }


    /**
     * Сумма за одну акцию
     */
    public com.pullenti.ner.money.MoneyReferent getPrice() {
        return (com.pullenti.ner.money.MoneyReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_PRICE), com.pullenti.ner.money.MoneyReferent.class);
    }

    /**
     * Сумма за одну акцию
     */
    public com.pullenti.ner.money.MoneyReferent setPrice(com.pullenti.ner.money.MoneyReferent value) {
        addSlot(ATTR_PRICE, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        FundsReferent f = (FundsReferent)com.pullenti.n2j.Utils.cast(obj, FundsReferent.class);
        if (f == null) 
            return false;
        if (getKind() != f.getKind()) 
            return false;
        if (getTyp() != null && f.getTyp() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(getTyp(), f.getTyp())) 
                return false;
        }
        if (getSource() != f.getSource()) 
            return false;
        if (getCount() != f.getCount()) 
            return false;
        if (getPercent() != f.getPercent()) 
            return false;
        if (getSum() != f.getSum()) 
            return false;
        return true;
    }

    public boolean checkCorrect() {
        if (getKind() == FundsKind.UNDEFINED) 
            return false;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsNe(s.getTypeName(), ATTR_TYPE) && com.pullenti.n2j.Utils.stringsNe(s.getTypeName(), ATTR_KIND)) 
                return true;
        }
        return false;
    }
}
