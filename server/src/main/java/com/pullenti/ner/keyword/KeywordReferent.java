/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.keyword;

/**
 * Оформление ключевых слов и комбинаций
 */
public class KeywordReferent extends com.pullenti.ner.Referent {

    public KeywordReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.keyword.internal.KeywordMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "KEYWORD";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_NORMAL = "NORMAL";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_RANK = "RANK";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        double _rank = getRank();
        String val = getStringValue(ATTR_VALUE);
        if (val == null) {
            com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (r != null) 
                val = r.toString(true, lang, lev + 1);
            else 
                val = getStringValue(ATTR_NORMAL);
        }
        if (shortVariant) 
            return (String)com.pullenti.n2j.Utils.notnull(val, "?");
        String norm = getStringValue(ATTR_NORMAL);
        if (norm == null) 
            return ((Double)_rank).toString() + ": " + ((String)com.pullenti.n2j.Utils.notnull(val, "?"));
        else 
            return ((Double)_rank).toString() + ": " + ((String)com.pullenti.n2j.Utils.notnull(val, "?")) + " [" + norm + "]";
    }

    public double getRank() {
        String val = getStringValue(ATTR_RANK);
        if (val == null) 
            return (double)0;
        double d = (double)0;
        com.pullenti.n2j.Outargwrapper<Double> inoutarg1461 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres1462 = com.pullenti.n2j.Utils.parseDouble(val.replace(',', '.'), inoutarg1461);
        d = inoutarg1461.value;
        if (val.indexOf(',') >= 0 && inoutres1462) {
        }
        else {
            com.pullenti.n2j.Outargwrapper<Double> inoutarg1459 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1460 = com.pullenti.n2j.Utils.parseDouble(val.replace('.', ','), inoutarg1459);
            d = inoutarg1459.value;
            if (val.indexOf('.') >= 0 && inoutres1460) {
            }
            else {
                com.pullenti.n2j.Outargwrapper<Double> inoutarg1458 = new com.pullenti.n2j.Outargwrapper< >();
                com.pullenti.n2j.Utils.parseDouble(val, inoutarg1458);
                d = inoutarg1458.value;
            }
        }
        return com.pullenti.n2j.Utils.mathRound(d, 2);
    }

    public double setRank(double value) {
        double val = com.pullenti.n2j.Utils.mathRound(value, 2);
        addSlot(ATTR_RANK, ((Double)val).toString().replace(',', '.'), true, 0);
        return value;
    }


    public KeywordType getTyp() {
        String str = getStringValue(ATTR_TYPE);
        if (str == null) 
            return KeywordType.UNDEFINED;
        try {
            return KeywordType.of(str);
        } catch(Exception ex) {
            return KeywordType.UNDEFINED;
        }
    }

    public KeywordType setTyp(KeywordType value) {
        addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    public int getChildWords() {
        return _getChildWords(this, 0);
    }


    private int _getChildWords(KeywordReferent root, int lev) {
        if (lev > 5) 
            return 0;
        int res = 0;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof KeywordReferent)) {
                if (s.getValue() == root) 
                    return 0;
                res += (((KeywordReferent)com.pullenti.n2j.Utils.cast(s.getValue(), KeywordReferent.class)))._getChildWords(root, lev + 1);
            }
        }
        if (res == 0) 
            res = 1;
        return res;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        KeywordReferent kw = (KeywordReferent)com.pullenti.n2j.Utils.cast(obj, KeywordReferent.class);
        if (kw == null) 
            return false;
        KeywordType ki = getTyp();
        if (ki != kw.getTyp()) 
            return false;
        if (ki == KeywordType.REFERENT) {
            com.pullenti.ner.Referent re = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (re == null) 
                return false;
            com.pullenti.ner.Referent re2 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(kw.getValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (re2 == null) 
                return false;
            return re.canBeEquals(re2, _typ);
        }
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NORMAL) || com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_VALUE)) {
                if (kw.findSlot(ATTR_NORMAL, s.getValue(), true) != null) 
                    return true;
                if (kw.findSlot(ATTR_VALUE, s.getValue(), true) != null) 
                    return true;
            }
        }
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        double r1 = getRank() + (((KeywordReferent)com.pullenti.n2j.Utils.cast(obj, KeywordReferent.class))).getRank();
        super.mergeSlots(obj, mergeStatistic);
        if (getSlots().size() > 50) {
        }
        setRank(r1);
    }

    public void union(KeywordReferent kw1, KeywordReferent kw2, String word2) {
        setTyp(kw1.getTyp());
        java.util.ArrayList<String> tmp = new java.util.ArrayList< >();
        StringBuilder tmp2 = new StringBuilder();
        for(String v : kw1.getStringValues(ATTR_VALUE)) {
            addSlot(ATTR_VALUE, v + " " + word2, false, 0);
        }
        java.util.ArrayList<String> norms1 = kw1.getStringValues(ATTR_NORMAL);
        if (norms1.size() == 0 && kw1.getChildWords() == 1) 
            norms1 = kw1.getStringValues(ATTR_VALUE);
        java.util.ArrayList<String> norms2 = kw2.getStringValues(ATTR_NORMAL);
        if (norms2.size() == 0 && kw2.getChildWords() == 1) 
            norms2 = kw2.getStringValues(ATTR_VALUE);
        for(String n1 : norms1) {
            for(String n2 : norms2) {
                tmp.clear();
                tmp.addAll(java.util.Arrays.asList(com.pullenti.n2j.Utils.split(n1, String.valueOf(' '), false)));
                for(String n : com.pullenti.n2j.Utils.split(n2, String.valueOf(' '), false)) {
                    if (!tmp.contains(n)) 
                        tmp.add(n);
                }
                java.util.Collections.sort(tmp);
                tmp2.setLength(0);
                for(int i = 0; i < tmp.size(); i++) {
                    if (i > 0) 
                        tmp2.append(' ');
                    tmp2.append(tmp.get(i));
                }
                addSlot(ATTR_NORMAL, tmp2.toString(), false, 0);
            }
        }
        addSlot(ATTR_REF, kw1, false, 0);
        addSlot(ATTR_REF, kw2, false, 0);
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem res = new com.pullenti.ner.core.IntOntologyItem(this);
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NORMAL) || com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_VALUE)) 
                res.termins.add(new com.pullenti.ner.core.Termin(s.getValue().toString(), new com.pullenti.morph.MorphLang(null), false));
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.Referent) && !((s.getValue() instanceof KeywordReferent))) 
                res.termins.add(new com.pullenti.ner.core.Termin((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).toString(true, new com.pullenti.morph.MorphLang(null), 0), new com.pullenti.morph.MorphLang(null), false));
        }
        return res;
    }

    public static KeywordReferent _new1449(KeywordType _arg1) {
        KeywordReferent res = new KeywordReferent();
        res.setTyp(_arg1);
        return res;
    }
}
