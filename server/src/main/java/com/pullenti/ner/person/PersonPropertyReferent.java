/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.person;

/**
 * Сущность, описывающая некоторое свойство физического лица
 */
public class PersonPropertyReferent extends com.pullenti.ner.Referent {

    public PersonPropertyReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.person.internal.MetaPersonProperty.globalMeta);
    }

    public static final String OBJ_TYPENAME = "PERSONPROPERTY";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_ATTR = "ATTR";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_HIGHER = "HIGHER";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getName() != null) 
            res.append(getName());
        for(com.pullenti.ner.Slot r : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), ATTR_ATTR) && r.getValue() != null) 
                res.append(", ").append(r.getValue().toString());
        }
        for(com.pullenti.ner.Slot r : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), ATTR_REF) && (r.getValue() instanceof com.pullenti.ner.Referent) && (lev < 10)) 
                res.append("; ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(r.getValue(), com.pullenti.ner.Referent.class))).toString(shortVariant, lang, lev + 1));
        }
        PersonPropertyReferent hi = getHigher();
        if (hi != null && hi != this && checkCorrectHigher(hi, 0)) 
            res.append("; ").append(hi.toString(shortVariant, lang, lev + 1));
        return res.toString();
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add(s.getValue().toString());
        }
        if (res.size() > 0) 
            return res;
        else 
            return super.getCompareStrings();
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
     * Вышестоящая должность
     */
    public PersonPropertyReferent getHigher() {
        return _getHigher(0);
    }

    /**
     * Вышестоящая должность
     */
    public PersonPropertyReferent setHigher(PersonPropertyReferent value) {
        if (checkCorrectHigher(value, 0)) 
            addSlot(ATTR_HIGHER, value, true, 0);
        return value;
    }


    private PersonPropertyReferent _getHigher(int lev) {
        PersonPropertyReferent hi = (PersonPropertyReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_HIGHER), PersonPropertyReferent.class);
        if (hi == null) 
            return null;
        if (!checkCorrectHigher(hi, lev + 1)) 
            return null;
        return hi;
    }

    private boolean checkCorrectHigher(PersonPropertyReferent hi, int lev) {
        if (hi == null) 
            return true;
        if (hi == this) 
            return false;
        if (lev > 20) 
            return false;
        PersonPropertyReferent hii = hi._getHigher(lev + 1);
        if (hii == null) 
            return true;
        if (hii == this) 
            return false;
        java.util.ArrayList<PersonPropertyReferent> li = new java.util.ArrayList< >();
        li.add(this);
        for(PersonPropertyReferent pr = hi; pr != null; pr = pr._getHigher(lev + 1)) {
            if (li.contains(pr)) 
                return false;
            else 
                li.add(pr);
        }
        return true;
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getHigher();
    }


    private static java.util.ArrayList<String> m_Bosses0 = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"глава", "руководитель"}));

    private static java.util.ArrayList<String> m_Bosses1 = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"президент", "генеральный директор", "директор", "председатель"}));

    private static int _tmpStack = 0;

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        PersonPropertyReferent pr = (PersonPropertyReferent)com.pullenti.n2j.Utils.cast(obj, PersonPropertyReferent.class);
        if (pr == null) 
            return false;
        String n1 = getName();
        String n2 = pr.getName();
        if (n1 == null || n2 == null) 
            return false;
        boolean eqBosses = false;
        if (com.pullenti.n2j.Utils.stringsNe(n1, n2)) {
            if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                return false;
            if (m_Bosses0.contains(n1) && m_Bosses1.contains(n2)) 
                eqBosses = true;
            else if (m_Bosses1.contains(n1) && m_Bosses0.contains(n2)) 
                eqBosses = true;
            else {
                if (!n1.startsWith(n2 + " ") && !n2.startsWith(n1 + " ")) 
                    return false;
                eqBosses = true;
            }
            for(PersonPropertyReferent hi = getHigher(); hi != null; hi = hi.getHigher()) {
                if ((++_tmpStack) > 20) {
                }
                else if (hi.canBeEquals(pr, typ)) {
                    _tmpStack--;
                    return false;
                }
                _tmpStack--;
            }
            for(PersonPropertyReferent hi = pr.getHigher(); hi != null; hi = hi.getHigher()) {
                if ((++_tmpStack) > 20) {
                }
                else if (hi.canBeEquals(this, typ)) {
                    _tmpStack--;
                    return false;
                }
                _tmpStack--;
            }
        }
        if (getHigher() != null && pr.getHigher() != null) {
            if ((++_tmpStack) > 20) {
            }
            else if (!getHigher().canBeEquals(pr.getHigher(), typ)) {
                _tmpStack--;
                return false;
            }
            _tmpStack--;
        }
        if (findSlot("@GENERAL", null, true) != null || pr.findSlot("@GENERAL", null, true) != null) 
            return com.pullenti.n2j.Utils.stringsEq(toString(), pr.toString());
        if (findSlot(ATTR_REF, null, true) != null || pr.findSlot(ATTR_REF, null, true) != null) {
            java.util.ArrayList<Object> refs1 = new java.util.ArrayList< >();
            java.util.ArrayList<Object> refs2 = new java.util.ArrayList< >();
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF)) 
                    refs1.add(s.getValue());
            }
            for(com.pullenti.ner.Slot s : pr.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF)) 
                    refs2.add(s.getValue());
            }
            boolean eq = false;
            boolean noeq = false;
            for(int i = 0; i < refs1.size(); i++) {
                if (refs2.contains(refs1.get(i))) {
                    eq = true;
                    continue;
                }
                noeq = true;
                if (refs1.get(i) instanceof com.pullenti.ner.Referent) {
                    for(Object rr : refs2) {
                        if (rr instanceof com.pullenti.ner.Referent) {
                            if ((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(rr, com.pullenti.ner.Referent.class))).canBeEquals((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(refs1.get(i), com.pullenti.ner.Referent.class), typ)) {
                                noeq = false;
                                eq = true;
                                break;
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < refs2.size(); i++) {
                if (refs1.contains(refs2.get(i))) {
                    eq = true;
                    continue;
                }
                noeq = true;
                if (refs2.get(i) instanceof com.pullenti.ner.Referent) {
                    for(Object rr : refs1) {
                        if (rr instanceof com.pullenti.ner.Referent) {
                            if ((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(rr, com.pullenti.ner.Referent.class))).canBeEquals((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(refs2.get(i), com.pullenti.ner.Referent.class), typ)) {
                                noeq = false;
                                eq = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (eq && !noeq) {
            }
            else if (noeq && ((eq || refs1.size() == 0 || refs2.size() == 0))) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS || com.pullenti.n2j.Utils.stringsNe(n1, n2)) 
                    return false;
                if (getHigher() != null || pr.getHigher() != null) 
                    return false;
            }
            else 
                return false;
        }
        else if (!eqBosses && com.pullenti.n2j.Utils.stringsNe(n1, n2)) 
            return false;
        return true;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        PersonPropertyReferent pr = (PersonPropertyReferent)com.pullenti.n2j.Utils.cast(obj, PersonPropertyReferent.class);
        if (pr == null) 
            return false;
        String n1 = getName();
        String n2 = pr.getName();
        if (n1 == null || n2 == null) 
            return false;
        if (findSlot(ATTR_REF, null, true) != null || getHigher() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(n1, n2) && n1.startsWith(n2)) 
                return canBeEquals(obj, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS);
            return false;
        }
        if (com.pullenti.n2j.Utils.stringsEq(n1, n2)) {
            if (pr.findSlot(ATTR_REF, null, true) != null || pr.getHigher() != null) 
                return true;
            return false;
        }
        if (n2.startsWith(n1)) {
            if (n2.startsWith(n1 + " ")) 
                return canBeEquals(obj, com.pullenti.ner.Referent.EqualType.WITHINONETEXT);
        }
        return false;
    }

    public PersonPropertyKind getKind() {
        return com.pullenti.ner.person.internal.PersonAttrToken.checkKind(this);
    }


    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) 
                oi.termins.add(new com.pullenti.ner.core.Termin(a.getValue().toString(), new com.pullenti.morph.MorphLang(null), false));
        }
        return oi;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        String nam = getName();
        String nam1 = (((PersonPropertyReferent)com.pullenti.n2j.Utils.cast(obj, PersonPropertyReferent.class))).getName();
        super.mergeSlots(obj, mergeStatistic);
        if (com.pullenti.n2j.Utils.stringsNe(nam, nam1) && nam1 != null && nam != null) {
            com.pullenti.ner.Slot s = null;
            if (nam.startsWith(nam1)) 
                s = findSlot(ATTR_NAME, nam1, true);
            else if (nam1.startsWith(nam)) 
                s = findSlot(ATTR_NAME, nam, true);
            else if (m_Bosses0.contains(nam) && m_Bosses1.contains(nam1)) 
                s = findSlot(ATTR_NAME, nam, true);
            else if (m_Bosses0.contains(nam1) && m_Bosses1.contains(nam)) 
                s = findSlot(ATTR_NAME, nam1, true);
            if (s != null) 
                getSlots().remove(s);
        }
    }

    /**
     * Проверка, что этот референт может выступать в качестве ATTR_REF
     * @param r 
     * @return 
     */
    public boolean canHasRef(com.pullenti.ner.Referent r) {
        String nam = getName();
        if (nam == null || r == null) 
            return false;
        if (r instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
            if (com.pullenti.morph.LanguageHelper.endsWithEx(nam, "президент", "губернатор", null, null)) 
                return g.isState() || g.isRegion();
            if (com.pullenti.n2j.Utils.stringsEq(nam, "мэр") || com.pullenti.n2j.Utils.stringsEq(nam, "градоначальник")) 
                return g.isCity();
            if (com.pullenti.n2j.Utils.stringsEq(nam, "глава")) 
                return true;
            return false;
        }
        if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) {
            if ((com.pullenti.morph.LanguageHelper.endsWith(nam, "губернатор") || com.pullenti.n2j.Utils.stringsEq(nam, "мэр") || com.pullenti.n2j.Utils.stringsEq(nam, "градоначальник")) || com.pullenti.n2j.Utils.stringsEq(nam, "президент")) 
                return false;
            if ((nam.indexOf("министр") >= 0)) {
                if (r.findSlot(null, "министерство", true) == null) 
                    return false;
            }
            if (nam.endsWith("директор")) {
                if (((r.findSlot(null, "суд", true))) != null) 
                    return false;
            }
            return true;
        }
        return false;
    }

    public static PersonPropertyReferent _new2190(String _arg1) {
        PersonPropertyReferent res = new PersonPropertyReferent();
        res.setName(_arg1);
        return res;
    }
}
