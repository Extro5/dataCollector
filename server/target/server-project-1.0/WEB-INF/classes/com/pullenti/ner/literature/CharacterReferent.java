/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Литературный персонаж
 */
public class CharacterReferent extends com.pullenti.ner.Referent {

    public static final String OBJ_TYPENAME = "CHARACTER";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NAME1 = "NAME1";

    public static final String ATTR_FIRSTNAME = "FIRSTNAME";

    public static final String ATTR_LASTNAME = "LASTNAME";

    public static final String ATTR_MIDDLENAME = "MIDDLENAME";

    public static final String ATTR_ATTR = "ATTR";

    public static final String ATTR_MISC = "MISC";

    public static final String ATTR_EMO = "EMO";

    public static final String ATTR_ROLE = "ROLE";

    public static final String ATTR_GENDER = "GENDER";

    public CharacterReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.literature.internal.MetaCharacter.GLOBALMETA);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        String nam = null;
        String attr = null;
        int max = 0;
        String na = getStringValue(ATTR_FIRSTNAME);
        String su = getStringValue(ATTR_LASTNAME);
        String se = getStringValue(ATTR_MIDDLENAME);
        if (su != null && na != null) {
            if (se == null) 
                nam = na + " " + su;
            else 
                nam = na + " " + se + " " + su;
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_LASTNAME) && com.pullenti.n2j.Utils.stringsNe((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class), su)) 
                    nam = nam + "/" + s.getValue();
            }
        }
        if (nam == null) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                    String n = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                    if (nam == null) {
                        nam = n;
                        max = s.getCount();
                    }
                    else if (s.getCount() > max) {
                        if (n.length() > (nam.length() - 3)) {
                            nam = n;
                            max = s.getCount();
                        }
                    }
                    else if ((n.length() - 3) > nam.length() && (((s.getCount() * 2) > max || s.getCount() > 2))) {
                        nam = n;
                        max = s.getCount();
                    }
                }
            }
        }
        if (nam != null && nam.indexOf(' ') > 0 && getTyp() == CharacterType.MAN) {
        }
        else {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ATTR) && (s.getValue() instanceof String)) {
                    if ((((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class))).length() > 15) 
                        continue;
                    attr = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                    break;
                }
            }
            if (attr == null) {
                for(com.pullenti.ner.Slot s : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ATTR) && (s.getValue() instanceof LinkReferent)) {
                        attr = (((LinkReferent)com.pullenti.n2j.Utils.cast(s.getValue(), LinkReferent.class))).getShortName();
                        break;
                    }
                }
            }
            if (attr == null && !shortVariant) 
                attr = getStringValue(ATTR_MISC);
        }
        StringBuilder res = new StringBuilder();
        if (attr != null) 
            res.append(attr.toLowerCase());
        if (nam != null) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(nam));
        }
        if (isAuthor()) 
            res.append(" (автор)");
        return res.toString();
    }

    /**
     * Собственные имена (все)
     */
    public java.util.ArrayList<String> getNames() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME) || com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME1)) 
                res.add((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    public java.util.ArrayList<String> getNames0() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    public java.util.ArrayList<LinkReferent> getLinks() {
        java.util.ArrayList<LinkReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ATTR) && (s.getValue() instanceof LinkReferent)) 
                res.add((LinkReferent)com.pullenti.n2j.Utils.cast(s.getValue(), LinkReferent.class));
        }
        return res;
    }


    /**
     * Тип персонажа
     */
    public CharacterType getTyp() {
        String str = getStringValue(ATTR_TYPE);
        if (str == null) 
            return CharacterType.UNDEFINED;
        try {
            return CharacterType.of(str);
        } catch(Exception ex1568) {
        }
        return CharacterType.UNDEFINED;
    }

    /**
     * Тип персонажа
     */
    public CharacterType setTyp(CharacterType value) {
        if (value == CharacterType.UNDEFINED) 
            addSlot(ATTR_TYPE, null, true, 0);
        else 
            addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    /**
     * Пол
     */
    public CharacterGender getGender() {
        String str = getStringValue(ATTR_GENDER);
        if (str == null) 
            return CharacterGender.UNDEFINED;
        try {
            return CharacterGender.of(str);
        } catch(Exception ex1569) {
        }
        return CharacterGender.UNDEFINED;
    }

    /**
     * Пол
     */
    public CharacterGender setGender(CharacterGender value) {
        if (value == CharacterGender.UNDEFINED) 
            addSlot(ATTR_GENDER, null, true, 0);
        else 
            addSlot(ATTR_GENDER, value.toString().toLowerCase(), true, 0);
        return value;
    }


    /**
     * Признак того, что повествования ведётся от этого лица
     */
    public boolean isAuthor() {
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ROLE)) {
                if (com.pullenti.n2j.Utils.stringsEq(((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class)), "author")) 
                    return true;
            }
        }
        return false;
    }

    /**
     * Признак того, что повествования ведётся от этого лица
     */
    public boolean setAuthor(boolean value) {
        if (value) 
            addSlot(ATTR_ROLE, "author", false, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        return obj == this;
    }
}
