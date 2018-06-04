/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.bank;

/**
 * Банковские данные (реквизиты)
 */
public class BankDataReferent extends com.pullenti.ner.Referent {

    public BankDataReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.bank.internal.MetaBank.globalMeta);
    }

    public static final String OBJ_TYPENAME = "BANKDATA";

    public static final String ATTR_ITEM = "ITEM";

    public static final String ATTR_BANK = "BANK";

    public static final String ATTR_CORBANK = "CORBANK";

    public static final String ATTR_MISC = "MISC";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.uri.UriReferent) {
                if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.uri.UriReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class))).getScheme(), "Р/С")) {
                    res.append(s.getValue().toString());
                    break;
                }
            }
        }
        if (res.length() == 0) 
            res.append((String)com.pullenti.n2j.Utils.notnull(getStringValue(ATTR_ITEM), "?"));
        if (getParentReferent() != null && !shortVariant && (lev < 20)) 
            res.append(", ").append(getParentReferent().toString(true, lang, lev + 1));
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_BANK), com.pullenti.ner.Referent.class);
    }


    public String findValue(String schema) {
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.uri.UriReferent) {
                com.pullenti.ner.uri.UriReferent ur = (com.pullenti.ner.uri.UriReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class);
                if (com.pullenti.n2j.Utils.stringsEq(ur.getScheme(), schema)) 
                    return ur.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        BankDataReferent bd = (BankDataReferent)com.pullenti.n2j.Utils.cast(obj, BankDataReferent.class);
        if (bd == null) 
            return false;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ITEM)) {
                com.pullenti.ner.uri.UriReferent ur = (com.pullenti.ner.uri.UriReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class);
                String val = bd.findValue(ur.getScheme());
                if (val != null) {
                    if (com.pullenti.n2j.Utils.stringsNe(val, ur.getValue())) 
                        return false;
                }
            }
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_BANK)) {
                com.pullenti.ner.Referent b1 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                com.pullenti.ner.Referent b2 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(bd.getValue(ATTR_BANK), com.pullenti.ner.Referent.class);
                if (b2 != null) {
                    if (b1 != b2 && !b1.canBeEquals(b2, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                        return false;
                }
            }
        }
        return true;
    }
}
