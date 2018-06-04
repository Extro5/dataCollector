/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old.internal;

public class DocStructItem extends com.pullenti.ner.MetaToken {

    public DocStructItem(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public Typs typ = Typs.INDEX;

    public String value;

    public java.util.ArrayList<DocStructItem> contentItems;

    public DocStructItem attachedItem;

    @Override
    public String toString() {
        return typ.toString() + " " + ((String)com.pullenti.n2j.Utils.notnull(value, "")) + " " + (attachedItem == null ? "" : "(attached)");
    }

    public static DocStructItem tryAttach(com.pullenti.ner.Token t, java.util.ArrayList<DocStructItem> items, boolean isContentItem) {
        if (t == null) 
            return null;
        if (!t.isNewlineBefore()) 
            return null;
        com.pullenti.ner.Token tt;
        com.pullenti.ner.Token t1;
        com.pullenti.ner.Token t2;
        for(tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore() && tt != t) 
                return null;
            if (!tt.chars.isLetter()) 
                continue;
            if (tt.chars.isAllLower()) 
                return null;
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> toks = m_Ontology.tryAttach(tt, null, false);
            if (toks == null) 
                break;
            DocTermin dt = (DocTermin)com.pullenti.n2j.Utils.cast(toks.get(0).termin, DocTermin.class);
            t1 = toks.get(0).getEndToken();
            if ((dt.typ == Typs.INDEX && t1.getNext() != null && !t1.isNewlineAfter()) && t1.getNext().chars.isLetter()) {
                if (!t1.getNext().getMorph().getCase().isGenitive()) 
                    return null;
                else 
                    t1 = t1.getNext();
            }
            String nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt, t1, com.pullenti.ner.core.GetTextAttr.NO);
            boolean ok = true;
            for(com.pullenti.ner.Token ttt = t1.getNext(); ttt != null; ttt = ttt.getNext()) {
                if (ttt.isNewlineBefore()) 
                    break;
                if (ttt.chars.isLetter()) {
                    ok = false;
                    break;
                }
                t1 = ttt;
            }
            if (ok) {
                DocStructItem res = _new1595(t, t1, dt.typ, nam);
                if (res.typ == Typs.INDEX) 
                    _analyzeContent(res);
                else if (items != null) {
                    for(DocStructItem it : items) {
                        if (com.pullenti.n2j.Utils.stringsEq(it.value, nam)) {
                            res.attachedItem = it;
                            break;
                        }
                    }
                }
                return res;
            }
            break;
        }
        for(t1 = tt; t1 != null; t1 = t1.getNext()) {
            if (t1.isNewlineAfter() && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t1.getNext())) 
                break;
        }
        if (t1 == null) 
            return null;
        if ((t1.endChar - tt.beginChar) > maxNameLength) 
            return null;
        String name = com.pullenti.ner.core.MiscHelper.getTextValue(tt, t1, com.pullenti.ner.core.GetTextAttr.NO);
        if (name.length() > maxNameLength) 
            return null;
        String name2 = null;
        t2 = null;
        if (t1.getNext() != null && t1.getNext().chars.isLetter()) {
            for(t2 = t1.getNext(); t2 != null; t2 = t2.getNext()) {
                if (t2.isNewlineAfter()) 
                    break;
            }
            if (t2 != null && ((t2.endChar - tt.beginChar) < maxNameLength)) 
                name2 = com.pullenti.ner.core.MiscHelper.getTextValue(tt, t2, com.pullenti.ner.core.GetTextAttr.NO);
        }
        if (items != null) {
            for(DocStructItem it : items) {
                if (com.pullenti.n2j.Utils.stringsEq(it.value, name) || com.pullenti.n2j.Utils.stringsEq(it.value, name2)) {
                    DocStructItem res = _new1596(t, t1, Typs.CHAPTER, it, name);
                    if (com.pullenti.n2j.Utils.stringsEq(it.value, name2)) {
                        res.value = name2;
                        res.setEndToken(t2);
                    }
                    return res;
                }
            }
        }
        if (isContentItem) {
            boolean ok = false;
            if (tt.isValue("ГЛАВА", null) || tt.isChar('§') || tt.isValue("РАЗДЕЛ", null)) 
                ok = true;
            else if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getNext() != null && tt.getNext().isChar('.')) 
                ok = true;
            if (ok) {
                DocStructItem res = _new1595(t, t1, Typs.INDEXITEM, name);
                return res;
            }
        }
        return null;
    }

    private static final int maxNameLength = 300;

    private static void _analyzeContent(DocStructItem res) {
        com.pullenti.ner.Token t = res.getEndToken().getNext();
        if (t == null || !t.isNewlineBefore()) 
            return;
        java.util.ArrayList<Long> numbers = new java.util.ArrayList< >();
        for(; t != null; t = t.getNext()) {
            if (t.isValue("СТР", null)) 
                continue;
            if (!t.isNewlineBefore()) {
                if (t.chars.isLetter()) 
                    break;
                else 
                    continue;
            }
            DocStructItem dsi = tryAttach(t, res.contentItems, true);
            if (dsi != null) {
                if (dsi.attachedItem != null) 
                    return;
                if (dsi.typ == Typs.INDEX) 
                    return;
                if (res.contentItems == null) 
                    res.contentItems = new java.util.ArrayList< >();
                res.contentItems.add(dsi);
                t = dsi.getEndToken();
                res.setEndToken(t);
                if ((t instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) 
                    numbers.add((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value);
                continue;
            }
            com.pullenti.ner.Token t1 = t;
            boolean ok = false;
            for(; t1 != null; t1 = t1.getNext()) {
                if (t1.chars.isLetter()) {
                    ok = true;
                    break;
                }
                else if (t1.isNewlineAfter()) 
                    break;
            }
            if (t1.chars.isAllLower()) 
                ok = false;
            if (!ok) 
                break;
            com.pullenti.ner.Token t2 = t1;
            for(; t2 != null; t2 = t2.getNext()) {
                if (t2.isNewlineAfter()) 
                    break;
            }
            if (t2 == null) 
                break;
            com.pullenti.ner.Token tt = t2;
            for(; tt != t1; tt = tt.getPrevious()) {
                if ((tt instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                    if (tt == t2) 
                        numbers.add((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value);
                }
                else if (tt.chars.isLetter() && !tt.isValue("СТР", null) && !tt.isValue("С", null)) 
                    break;
            }
            DocStructItem item = _new1598(t, t2, Typs.INDEXITEM);
            item.value = com.pullenti.ner.core.MiscHelper.getTextValue(t1, tt, com.pullenti.ner.core.GetTextAttr.NO);
            if (item.value.length() > maxNameLength) 
                break;
            if (res.contentItems == null) 
                res.contentItems = new java.util.ArrayList< >();
            res.contentItems.add(item);
            t = item.getEndToken();
            res.setEndToken(t);
        }
    }

    private static com.pullenti.ner.core.IntOntologyCollection m_Ontology;

    public static class DocTermin extends com.pullenti.ner.core.Termin {
    
        public DocTermin(String source, boolean addLemmaVariant) {
            super(source, new com.pullenti.morph.MorphLang(null), false);
        }
    
        public com.pullenti.ner.old.internal.DocStructItem.Typs typ = com.pullenti.ner.old.internal.DocStructItem.Typs.INDEX;
    
        public static DocTermin _new1599(String _arg1, com.pullenti.ner.old.internal.DocStructItem.Typs _arg2) {
            DocTermin res = new DocTermin(_arg1, false);
            res.typ = _arg2;
            return res;
        }
        public DocTermin() {
            super();
        }
    }


    public static class Typs implements Comparable<Typs> {
    
        public static final Typs INDEX; // 0
    
        public static final Typs INDEXITEM; // 1
    
        public static final Typs INTRO; // 2
    
        public static final Typs LITERATURE; // 3
    
        public static final Typs APPENDIX; // 4
    
        public static final Typs CONCLUSION; // 5
    
        public static final Typs OTHER; // 6
    
        public static final Typs CHAPTER; // 7
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Typs(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(Typs v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Typs> mapIntToEnum; 
        private static java.util.HashMap<String, Typs> mapStringToEnum; 
        public static Typs of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Typs item = new Typs(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Typs of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            INDEX = new Typs(0, "INDEX");
            mapIntToEnum.put(INDEX.value(), INDEX);
            mapStringToEnum.put(INDEX.m_str.toUpperCase(), INDEX);
            INDEXITEM = new Typs(1, "INDEXITEM");
            mapIntToEnum.put(INDEXITEM.value(), INDEXITEM);
            mapStringToEnum.put(INDEXITEM.m_str.toUpperCase(), INDEXITEM);
            INTRO = new Typs(2, "INTRO");
            mapIntToEnum.put(INTRO.value(), INTRO);
            mapStringToEnum.put(INTRO.m_str.toUpperCase(), INTRO);
            LITERATURE = new Typs(3, "LITERATURE");
            mapIntToEnum.put(LITERATURE.value(), LITERATURE);
            mapStringToEnum.put(LITERATURE.m_str.toUpperCase(), LITERATURE);
            APPENDIX = new Typs(4, "APPENDIX");
            mapIntToEnum.put(APPENDIX.value(), APPENDIX);
            mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
            CONCLUSION = new Typs(5, "CONCLUSION");
            mapIntToEnum.put(CONCLUSION.value(), CONCLUSION);
            mapStringToEnum.put(CONCLUSION.m_str.toUpperCase(), CONCLUSION);
            OTHER = new Typs(6, "OTHER");
            mapIntToEnum.put(OTHER.value(), OTHER);
            mapStringToEnum.put(OTHER.m_str.toUpperCase(), OTHER);
            CHAPTER = new Typs(7, "CHAPTER");
            mapIntToEnum.put(CHAPTER.value(), CHAPTER);
            mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
        }
    }


    public static DocStructItem _new1595(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, String _arg4) {
        DocStructItem res = new DocStructItem(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }
    public static DocStructItem _new1596(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, DocStructItem _arg4, String _arg5) {
        DocStructItem res = new DocStructItem(_arg1, _arg2);
        res.typ = _arg3;
        res.attachedItem = _arg4;
        res.value = _arg5;
        return res;
    }
    public static DocStructItem _new1598(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3) {
        DocStructItem res = new DocStructItem(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public DocStructItem() {
        super();
    }
    public static DocStructItem _globalInstance;
    static {
        _globalInstance = new DocStructItem(); 
        m_Ontology = new com.pullenti.ner.core.IntOntologyCollection();
        for(String s : new String[] {"СОДЕРЖАНИЕ", "СОДЕРЖИМОЕ", "ОГЛАВЛЕНИЕ", "ПЛАН", "PLAN"}) {
            m_Ontology.add(DocTermin._new1599(s, Typs.INDEX));
        }
        for(String s : new String[] {"ВВЕДЕНИЕ", "ВСТУПЛЕНИЕ", "ПРЕДИСЛОВИЕ", "INTRODUCTION"}) {
            m_Ontology.add(DocTermin._new1599(s, Typs.INTRO));
        }
        for(String s : new String[] {"ВЫВОДЫ", "ВЫВОД", "ЗАКЛЮЧЕНИЕ", "CONCLUSION"}) {
            m_Ontology.add(DocTermin._new1599(s, Typs.CONCLUSION));
        }
        for(String s : new String[] {"ЛИТЕРАТУРА", "СПИСОК ЛИТЕРАТУРЫ", "СПИСОК ИСТОЧНИКОВ", "СПИСОК ИСПОЛЬЗОВАННЫХ ИСТОЧНИКОВ", "СПИСОК ИСПОЛЬЗУЕМЫХ ИСТОЧНИКОВ", "СПЕЦИАЛЬНАЯ ЛИТЕРАТУРА", "СПИСОК ИСПОЛЬЗОВАННОЙ ЛИТЕРАТУРЫ", "СПИСОК ИСПОЛЬЗУЕМОЙ ЛИТЕРАТУРЫ", "ИСПОЛЬЗОВАННЫЕ ИСТОЧНИКИ", "ИСПОЛЬЗУЕМАЯ ЛИТЕРАТУРА", "БИБЛИОГРАФИЯ", "BIBLIOGRAPHY"}) {
            m_Ontology.add(DocTermin._new1599(s, Typs.LITERATURE));
        }
        for(String s : new String[] {"ПРИЛОЖЕНИЕ", "СПИСОК СОКРАЩЕНИЙ", "СПИСОК УСЛОВНЫХ СОКРАЩЕНИЙ", "УСЛОВНЫЕ СОКРАЩЕНИЯ", "ОБЗОР ЛИТЕРАТУРЫ", "АННОТАЦИЯ", "БЛАГОДАРНОСТИ", "ПРИЛОЖЕНИЕ", "SUPPLEMENT"}) {
            m_Ontology.add(DocTermin._new1599(s, Typs.OTHER));
        }
    }
}
