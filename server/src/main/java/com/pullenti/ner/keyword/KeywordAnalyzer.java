/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.keyword;

/**
 * Анализатор ключевых комбинаций
 */
public class KeywordAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "KEYWORD";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Ключевые слова";
    }


    @Override
    public String getDescription() {
        return "Ключевые слова для различных аналитических систем";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new KeywordAnalyzer();
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"ALL"});
    }


    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerDataWithOntology();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.keyword.internal.KeywordMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap< >();
        res.put(com.pullenti.ner.keyword.internal.KeywordMeta.IMAGEOBJ, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("kwobject.png"));
        res.put(com.pullenti.ner.keyword.internal.KeywordMeta.IMAGEPRED, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("kwpredicate.png"));
        res.put(com.pullenti.ner.keyword.internal.KeywordMeta.IMAGEREF, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("kwreferent.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, KeywordReferent.OBJ_TYPENAME)) 
            return new KeywordReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    /**
     * Основная функция выделения телефонов
     * @param cnt 
     * @param stage 
     * @return 
     */
    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        java.util.ArrayList<KeywordReferent> li = new java.util.ArrayList< >();
        StringBuilder tmp = new StringBuilder();
        java.util.ArrayList<String> tmp2 = new java.util.ArrayList< >();
        int max = 0;
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            max++;
        }
        int cur = 0;
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext(),cur++) {
            com.pullenti.ner.Referent r = t.getReferent();
            if (r != null) {
                t = _addReferents(ad, t, cur, max);
                continue;
            }
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                continue;
            if (!t.chars.isLetter()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0);
            if (npt == null) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isVerb() && !mc.isPreposition()) {
                    KeywordReferent kref = KeywordReferent._new1449(KeywordType.PREDICATE);
                    String norm = t.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (norm == null) 
                        norm = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).getLemma();
                    kref.addSlot(KeywordReferent.ATTR_VALUE, norm, false, 0);
                    if (t.getMorph().getLanguage().isRu() || t.getMorph().getLanguage().isUa()) {
                        java.util.ArrayList<com.pullenti.morph.DerivateGroup> drv = com.pullenti.morph.Explanatory.findDerivates(norm, true, t.getMorph().getLanguage());
                        if (drv != null) {
                            for(com.pullenti.morph.DerivateGroup dr : drv) {
                                if (!dr.isDummy && dr.words.size() > 0) {
                                    if (com.pullenti.n2j.Utils.stringsNe(dr.words.get(0).spelling, norm)) 
                                        kref.addSlot(KeywordReferent.ATTR_NORMAL, dr.words.get(0).spelling, false, 0);
                                }
                            }
                        }
                    }
                    kref = (KeywordReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(kref), KeywordReferent.class);
                    _setRank(kref, cur, max);
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(ad.registerReferent(kref), t, t, t.getMorph());
                    kit.embedToken(rt1);
                    t = rt1;
                    continue;
                }
                continue;
            }
            if (npt.internalNoun != null) 
                continue;
            if (npt.getEndToken().isValue("ЦЕЛОМ", null) || npt.getEndToken().isValue("ЧАСТНОСТИ", null)) {
                if (npt.preposition != null) {
                    t = npt.getEndToken();
                    continue;
                }
            }
            if (npt.getEndToken().isValue("СТОРОНЫ", null) && npt.preposition != null && npt.preposition.isValue("С", null)) {
                t = npt.getEndToken();
                continue;
            }
            li.clear();
            com.pullenti.ner.Token t0 = t;
            for(com.pullenti.ner.Token tt = t; tt != null && tt.endChar <= npt.endChar; tt = tt.getNext()) {
                if (!((tt instanceof com.pullenti.ner.TextToken))) 
                    continue;
                if ((tt.getLengthChar() < 3) || !tt.chars.isLetter()) 
                    continue;
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if ((mc.isPreposition() || mc.isPronoun() || mc.isPersonalPronoun()) || mc.isConjunction()) {
                    if (tt.isValue("ОТНОШЕНИЕ", null)) {
                    }
                    else 
                        continue;
                }
                if (mc.isMisc()) {
                    if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
                        continue;
                }
                KeywordReferent kref = KeywordReferent._new1449(KeywordType.OBJECT);
                String norm = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).getLemma();
                kref.addSlot(KeywordReferent.ATTR_VALUE, norm, false, 0);
                if (((tt.getMorph().getLanguage().isRu() || tt.getMorph().getLanguage().isUa())) && com.pullenti.n2j.Utils.stringsNe(norm, "ЕСТЬ")) {
                    java.util.ArrayList<com.pullenti.morph.DerivateGroup> drv = com.pullenti.morph.Explanatory.findDerivates(norm, true, tt.getMorph().getLanguage());
                    if (drv != null) {
                        for(com.pullenti.morph.DerivateGroup dr : drv) {
                            if (!dr.isDummy && dr.words.size() > 0) {
                                if (com.pullenti.n2j.Utils.stringsNe(dr.words.get(0).spelling, norm)) 
                                    kref.addSlot(KeywordReferent.ATTR_NORMAL, dr.words.get(0).spelling, false, 0);
                            }
                        }
                    }
                }
                kref = (KeywordReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(kref), KeywordReferent.class);
                _setRank(kref, cur, max);
                com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(kref, tt, tt, tt.getMorph());
                kit.embedToken(rt1);
                if (tt == t && li.size() == 0) 
                    t0 = rt1;
                t = rt1;
                li.add(kref);
            }
            if (li.size() > 1) {
                KeywordReferent kref = KeywordReferent._new1449(KeywordType.OBJECT);
                tmp.setLength(0);
                tmp2.clear();
                boolean hasNorm = false;
                for(KeywordReferent kw : li) {
                    String s = kw.getStringValue(KeywordReferent.ATTR_VALUE);
                    if (tmp.length() > 0) 
                        tmp.append(' ');
                    tmp.append(s);
                    String n = kw.getStringValue(KeywordReferent.ATTR_NORMAL);
                    if (n != null) {
                        hasNorm = true;
                        tmp2.add(n);
                    }
                    else 
                        tmp2.add(s);
                    kref.addSlot(KeywordReferent.ATTR_REF, kw, false, 0);
                }
                String val = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                kref.addSlot(KeywordReferent.ATTR_VALUE, val, false, 0);
                tmp.setLength(0);
                java.util.Collections.sort(tmp2);
                for(String s : tmp2) {
                    if (tmp.length() > 0) 
                        tmp.append(' ');
                    tmp.append(s);
                }
                String norm = tmp.toString();
                if (com.pullenti.n2j.Utils.stringsNe(norm, val)) 
                    kref.addSlot(KeywordReferent.ATTR_NORMAL, norm, false, 0);
                kref = (KeywordReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(kref), KeywordReferent.class);
                _setRank(kref, cur, max);
                com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(kref, t0, t, npt.getMorph());
                kit.embedToken(rt1);
                t = rt1;
            }
        }
        cur = 0;
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext(),cur++) {
            KeywordReferent kw = (KeywordReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), KeywordReferent.class);
            if (kw == null || kw.getTyp() != KeywordType.OBJECT) 
                continue;
            if (t.getNext() == null || kw.getChildWords() > 2) 
                continue;
            if (!t.getNext().getMorph().getCase().isGenitive() || t.getWhitespacesAfterCount() > 1) 
                continue;
            KeywordReferent kw2 = (KeywordReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), KeywordReferent.class);
            if (kw2 == null) 
                continue;
            if (kw2.getTyp() != KeywordType.OBJECT || (kw.getChildWords() + kw2.getChildWords()) > 3) 
                continue;
            KeywordReferent kwUn = new KeywordReferent();
            kwUn.union(kw, kw2, com.pullenti.ner.core.MiscHelper.getTextValue(t.getNext(), t.getNext(), com.pullenti.ner.core.GetTextAttr.NO));
            kwUn = (KeywordReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(kwUn), KeywordReferent.class);
            _setRank(kwUn, cur, max);
            com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(kwUn, t, t.getNext(), t.getMorph());
            kit.embedToken(rt1);
            t = rt1;
        }
        java.util.ArrayList<com.pullenti.ner.Referent> all = new java.util.ArrayList(ad.getReferents());
        all.sort(new CompByRank());
        ad.setReferents(all);
    }

    public static class CompByRank implements java.util.Comparator<com.pullenti.ner.Referent> {
    
        @Override
        public int compare(com.pullenti.ner.Referent x, com.pullenti.ner.Referent y) {
            double d1 = (((com.pullenti.ner.keyword.KeywordReferent)com.pullenti.n2j.Utils.cast(x, com.pullenti.ner.keyword.KeywordReferent.class))).getRank();
            double d2 = (((com.pullenti.ner.keyword.KeywordReferent)com.pullenti.n2j.Utils.cast(y, com.pullenti.ner.keyword.KeywordReferent.class))).getRank();
            if (d1 > d2) 
                return -1;
            if (d1 < d2) 
                return 1;
            return 0;
        }
        public CompByRank() {
        }
    }


    private com.pullenti.ner.Token _addReferents(com.pullenti.ner.core.AnalyzerData ad, com.pullenti.ner.Token t, int cur, int max) {
        if (!((t instanceof com.pullenti.ner.ReferentToken))) 
            return t;
        com.pullenti.ner.Referent r = t.getReferent();
        if (r == null) 
            return t;
        if ((r instanceof com.pullenti.ner.phone.PhoneReferent) || (r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) 
            return t;
        if (r instanceof com.pullenti.ner.money.MoneyReferent) {
            com.pullenti.ner.money.MoneyReferent mr = (com.pullenti.ner.money.MoneyReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.money.MoneyReferent.class);
            KeywordReferent kref0 = KeywordReferent._new1449(KeywordType.OBJECT);
            kref0.addSlot(KeywordReferent.ATTR_NORMAL, mr.getCurrency(), false, 0);
            com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(ad.registerReferent(kref0), t, t, null);
            t.kit.embedToken(rt0);
            return rt0;
        }
        if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "DATE") || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "DATERANGE")) 
            return t;
        for(com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
            if (tt instanceof com.pullenti.ner.ReferentToken) 
                _addReferents(ad, tt, cur, max);
        }
        KeywordReferent kref = KeywordReferent._new1449(KeywordType.REFERENT);
        String alpha = r.getStringValue("ALPHA2");
        if (alpha != null && com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "GEO")) 
            kref.addSlot(KeywordReferent.ATTR_NORMAL, alpha, false, 0);
        kref.addSlot(KeywordReferent.ATTR_REF, t.getReferent(), false, 0);
        _setRank(kref, cur, max);
        com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(ad.registerReferent(kref), t, t, null);
        t.kit.embedToken(rt1);
        return rt1;
    }

    private static void _setRank(KeywordReferent kr, int cur, int max) {
        double rank = (double)1;
        KeywordType ty = kr.getTyp();
        if (ty == KeywordType.PREDICATE) 
            rank = (double)1;
        else if (ty == KeywordType.OBJECT) {
            String v = (String)com.pullenti.n2j.Utils.notnull(kr.getStringValue(KeywordReferent.ATTR_VALUE), kr.getStringValue(KeywordReferent.ATTR_NORMAL));
            if (v != null) {
                for(int i = 0; i < v.length(); i++) {
                    if (v.charAt(i) == ' ' || v.charAt(i) == '-') 
                        rank++;
                }
            }
        }
        else if (ty == KeywordType.REFERENT) {
            rank = (double)3;
            com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(kr.getValue(KeywordReferent.ATTR_REF), com.pullenti.ner.Referent.class);
            if (r != null) {
                if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "PERSON")) 
                    rank = (double)4;
            }
        }
        if (max > 0) 
            rank *= ((((double)1) - (((0.5 * ((double)cur)) / ((double)max)))));
        kr.setRank(kr.getRank() + rank);
    }

    public static void initialize() {
        com.pullenti.ner.ProcessorService.registerAnalyzer(new KeywordAnalyzer());
    }
    public KeywordAnalyzer() {
        super();
    }
    public static KeywordAnalyzer _globalInstance;
    static {
        _globalInstance = new KeywordAnalyzer(); 
    }
}
