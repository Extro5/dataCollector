/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Связь персонажа с другим персонажем
 */
public class LinkReferent extends com.pullenti.ner.Referent {

    public static final String OBJ_TYPENAME = "CHARLINK";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_SHORTNAME = "SHORTNAME";

    public static final String ATTR_CHAR = "CHAR";

    public LinkReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.literature.internal.MetaLink.GLOBALMETA);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append((String)com.pullenti.n2j.Utils.notnull(getName(), "?"));
        if (!shortVariant) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_CHAR)) {
                    if (s.getValue() instanceof CharacterReferent) 
                        res.append(": ").append((((CharacterReferent)com.pullenti.n2j.Utils.cast(s.getValue(), CharacterReferent.class))).toString(true, lang, 0));
                    else 
                        res.append(": ").append(s.getValue());
                }
            }
        }
        return res.toString();
    }

    /**
     * Ссылки на персонажей
     */
    public java.util.ArrayList<CharacterReferent> getChars() {
        java.util.ArrayList<CharacterReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_CHAR) && (s.getValue() instanceof CharacterReferent)) 
                res.add((CharacterReferent)com.pullenti.n2j.Utils.cast(s.getValue(), CharacterReferent.class));
        }
        return res;
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
    public String setName(String value) {
        addSlot(ATTR_NAME, value, true, 0);
        return value;
    }


    /**
     * Краткое наименование
     */
    public String getShortName() {
        return (String)com.pullenti.n2j.Utils.notnull(getStringValue(ATTR_SHORTNAME), getStringValue(ATTR_NAME));
    }

    /**
     * Краткое наименование
     */
    public String setShortName(String value) {
        addSlot(ATTR_SHORTNAME, value, true, 0);
        return value;
    }


    /**
     * Тип персонажа
     */
    public LinkType getTyp() {
        String str = getStringValue(ATTR_TYPE);
        if (str == null) 
            return LinkType.UNDEFINED;
        try {
            return LinkType.of(str);
        } catch(Exception ex1570) {
        }
        return LinkType.UNDEFINED;
    }

    /**
     * Тип персонажа
     */
    public LinkType setTyp(LinkType value) {
        if (value == LinkType.UNDEFINED) 
            addSlot(ATTR_TYPE, null, true, 0);
        else 
            addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        LinkReferent lr = (LinkReferent)com.pullenti.n2j.Utils.cast(obj, LinkReferent.class);
        if (lr == null) 
            return false;
        if (lr == this) 
            return true;
        if (com.pullenti.n2j.Utils.stringsNe(getName(), lr.getName())) 
            return false;
        java.util.ArrayList<CharacterReferent> chs1 = getChars();
        java.util.ArrayList<CharacterReferent> chs2 = lr.getChars();
        if (chs1.size() != chs2.size()) 
            return false;
        for(CharacterReferent ch : chs1) {
            if (!chs2.contains(ch)) 
                return false;
        }
        return true;
    }

    public static LinkReferent _new1533(String _arg1) {
        LinkReferent res = new LinkReferent();
        res.setName(_arg1);
        return res;
    }
}
