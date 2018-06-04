/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.mail;

/**
 * Письмо (точнее, блок письма)
 */
public class MailReferent extends com.pullenti.ner.Referent {

    public MailReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.mail.internal.MetaLetter.globalMeta);
    }

    public static final String OBJ_TYPENAME = "MAIL";

    public static final String ATTR_KIND = "TYPE";

    public static final String ATTR_TEXT = "TEXT";

    public static final String ATTR_REF = "REF";

    /**
     * Тип блока письма
     */
    public MailKind getKind() {
        String val = getStringValue(ATTR_KIND);
        try {
            if (val != null) 
                return MailKind.of(val);
        } catch(Exception ex1577) {
        }
        return MailKind.UNDEFINED;
    }

    /**
     * Тип блока письма
     */
    public MailKind setKind(MailKind value) {
        addSlot(ATTR_KIND, value.toString().toUpperCase(), true, 0);
        return value;
    }


    public String getText() {
        return getStringValue(ATTR_TEXT);
    }

    public String setText(String value) {
        addSlot(ATTR_TEXT, value, true, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(getKind().toString()).append(": ");
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).toString(true, lang, lev + 1)).append(", ");
        }
        if (res.length() < 100) {
            String str = (String)com.pullenti.n2j.Utils.notnull(getText(), "");
            str = str.replace('\r', ' ').replace('\n', ' ');
            if (str.length() > 100) 
                str = str.substring(0, 0+100) + "...";
            res.append(str);
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return obj == this;
    }

    public void addRef(com.pullenti.ner.Referent r, int lev) {
        if (r == null || lev > 4) 
            return;
        if ((((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent) || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "PHONE") || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "URI")) || (r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.address.AddressReferent)) 
            addSlot(ATTR_REF, r, false, 0);
        for(com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.Referent) 
                addRef((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class), lev + 1);
        }
    }

    public static MailReferent _new1573(MailKind _arg1) {
        MailReferent res = new MailReferent();
        res.setKind(_arg1);
        return res;
    }
}
