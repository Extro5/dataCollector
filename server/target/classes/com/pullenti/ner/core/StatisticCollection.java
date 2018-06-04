/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Поддержка анализа биграммной зависимости токенов в тексте
 */
public class StatisticCollection {

    public void prepare(com.pullenti.ner.Token first) {
        WordInfo prev = null;
        com.pullenti.ner.Token prevt = null;
        for(com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (t.isHiphen()) 
                continue;
            WordInfo it = null;
            if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && t.getLengthChar() > 1) && !t.chars.isAllLower()) 
                it = addToken((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class));
            else if ((((t instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).getLengthChar() == 1 && t.chars.isAllUpper()) && t.getNext() != null && t.getNext().isChar('.')) && !t.isWhitespaceAfter()) {
                it = addToken((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class));
                t = t.getNext();
            }
            if (prev != null && it != null) {
                addBigramm(prev, it);
                if (com.pullenti.morph.CharsInfo.ooEq(prevt.chars, t.chars)) {
                    prev.addAfter(it);
                    it.addBefore(prev);
                }
            }
            prev = it;
            prevt = t;
        }
        for(com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (t.chars.isLetter() && (t instanceof com.pullenti.ner.TextToken)) {
                WordInfo it = findItem((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class), false);
                if (it != null) {
                    if (t.chars.isAllLower()) 
                        it.lowerCount++;
                    else if (t.chars.isAllUpper()) 
                        it.upperCount++;
                    else if (t.chars.isCapitalUpper()) 
                        it.capitalCount++;
                }
            }
        }
    }

    private WordInfo addToken(com.pullenti.ner.TextToken tt) {
        java.util.ArrayList<String> vars = new java.util.ArrayList< >();
        vars.add(tt.term);
        String s = MiscHelper.getAbsoluteNormalValue(tt.term, false);
        if (s != null && !vars.contains(s)) 
            vars.add(s);
        for(com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf == null) 
                continue;
            if (wf.normalCase != null && !vars.contains(wf.normalCase)) 
                vars.add(wf.normalCase);
            if (wf.normalFull != null && !vars.contains(wf.normalFull)) 
                vars.add(wf.normalFull);
        }
        WordInfo res = null;
        for(String v : vars) {
            com.pullenti.n2j.Outargwrapper<WordInfo> inoutarg571 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres572 = com.pullenti.n2j.Utils.tryGetValue(m_Items, v, inoutarg571);
            res = inoutarg571.value;
            if (inoutres572) 
                break;
        }
        if (res == null) 
            res = WordInfo._new573(tt.lemma);
        for(String v : vars) {
            if (!m_Items.containsKey(v)) 
                m_Items.put(v, res);
        }
        res.totalCount++;
        if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isAllLower()) {
            if (tt.getNext().chars.isCyrillicLetter() && tt.getNext().getMorphClassInDictionary().isVerb()) {
                com.pullenti.morph.MorphGender g = tt.getNext().getMorph().getGender();
                if (g == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.femaleVerbsAfterCount++;
                else if ((((g.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.maleVerbsAfterCount++;
            }
        }
        if (tt.getPrevious() != null) {
            if ((tt.getPrevious() instanceof com.pullenti.ner.TextToken) && tt.getPrevious().chars.isLetter() && !tt.getPrevious().chars.isAllLower()) {
            }
            else 
                res.notCapitalBeforeCount++;
        }
        return res;
    }

    private java.util.HashMap<String, WordInfo> m_Items = new java.util.HashMap< >();

    private WordInfo findItem(com.pullenti.ner.TextToken tt, boolean doAbsolute) {
        if (tt == null) 
            return null;
        WordInfo res;
        com.pullenti.n2j.Outargwrapper<WordInfo> inoutarg580 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres581 = com.pullenti.n2j.Utils.tryGetValue(m_Items, tt.term, inoutarg580);
        res = inoutarg580.value;
        if (inoutres581) 
            return res;
        if (doAbsolute) {
            String s = MiscHelper.getAbsoluteNormalValue(tt.term, false);
            if (s != null) {
                com.pullenti.n2j.Outargwrapper<WordInfo> inoutarg574 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres575 = com.pullenti.n2j.Utils.tryGetValue(m_Items, s, inoutarg574);
                res = inoutarg574.value;
                if (inoutres575) 
                    return res;
            }
        }
        for(com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf == null) 
                continue;
            com.pullenti.n2j.Outargwrapper<WordInfo> inoutarg578 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres579 = com.pullenti.n2j.Utils.tryGetValue(m_Items, (String)com.pullenti.n2j.Utils.notnull(wf.normalCase, ""), inoutarg578);
            res = inoutarg578.value;
            if (inoutres579) 
                return res;
            com.pullenti.n2j.Outargwrapper<WordInfo> inoutarg576 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres577 = com.pullenti.n2j.Utils.tryGetValue(m_Items, wf.normalFull, inoutarg576);
            res = inoutarg576.value;
            if (wf.normalFull != null && inoutres577) 
                return res;
        }
        return null;
    }

    private void addBigramm(WordInfo b1, WordInfo b2) {
        java.util.HashMap<String, Integer> di;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, Integer>> inoutarg584 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres585 = com.pullenti.n2j.Utils.tryGetValue(m_Bigramms, b1.normal, inoutarg584);
        di = inoutarg584.value;
        if (!inoutres585) 
            m_Bigramms.put(b1.normal, (di = new java.util.HashMap< >()));
        if (di.containsKey(b2.normal)) 
            di.put(b2.normal, di.get(b2.normal) + 1);
        else 
            di.put(b2.normal, 1);
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, Integer>> inoutarg582 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres583 = com.pullenti.n2j.Utils.tryGetValue(m_BigrammsRev, b2.normal, inoutarg582);
        di = inoutarg582.value;
        if (!inoutres583) 
            m_BigrammsRev.put(b2.normal, (di = new java.util.HashMap< >()));
        if (di.containsKey(b1.normal)) 
            di.put(b1.normal, di.get(b1.normal) + 1);
        else 
            di.put(b1.normal, 1);
    }

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_Bigramms = new java.util.HashMap< >();

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_BigrammsRev = new java.util.HashMap< >();

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_Initials = new java.util.HashMap< >();

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_InitialsRev = new java.util.HashMap< >();

    public static class BigrammInfo {
    
        public int firstCount;
    
        public int secondCount;
    
        public int pairCount;
    
        public boolean firstHasOtherSecond;
    
        public boolean secondHasOtherFirst;
    
        public static BigrammInfo _new586(int _arg1, int _arg2) {
            BigrammInfo res = new BigrammInfo();
            res.firstCount = _arg1;
            res.secondCount = _arg2;
            return res;
        }
        public BigrammInfo() {
        }
    }


    public BigrammInfo getBigrammInfo(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2) {
        WordInfo si1 = findItem((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.TextToken.class), true);
        WordInfo si2 = findItem((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t2, com.pullenti.ner.TextToken.class), true);
        if (si1 == null || si2 == null) 
            return null;
        return _getBigramsInfo(si1, si2);
    }

    private BigrammInfo _getBigramsInfo(WordInfo si1, WordInfo si2) {
        BigrammInfo res = BigrammInfo._new586(si1.totalCount, si2.totalCount);
        java.util.HashMap<String, Integer> di12 = null;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, Integer>> inoutarg588 = new com.pullenti.n2j.Outargwrapper< >();
        com.pullenti.n2j.Utils.tryGetValue(m_Bigramms, si1.normal, inoutarg588);
        di12 = inoutarg588.value;
        java.util.HashMap<String, Integer> di21 = null;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, Integer>> inoutarg587 = new com.pullenti.n2j.Outargwrapper< >();
        com.pullenti.n2j.Utils.tryGetValue(m_BigrammsRev, si2.normal, inoutarg587);
        di21 = inoutarg587.value;
        if (di12 != null) {
            if (!di12.containsKey(si2.normal)) 
                res.firstHasOtherSecond = true;
            else {
                res.pairCount = di12.get(si2.normal);
                if (di12.size() > 1) 
                    res.firstHasOtherSecond = true;
            }
        }
        if (di21 != null) {
            if (!di21.containsKey(si1.normal)) 
                res.secondHasOtherFirst = true;
            else if (!di21.containsKey(si1.normal)) 
                res.secondHasOtherFirst = true;
            else if (di21.size() > 1) 
                res.secondHasOtherFirst = true;
        }
        return res;
    }

    public BigrammInfo getInitialInfo(String ini, com.pullenti.ner.Token sur) {
        if (com.pullenti.n2j.Utils.isNullOrEmpty(ini)) 
            return null;
        WordInfo si2 = findItem((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(sur, com.pullenti.ner.TextToken.class), true);
        if (si2 == null) 
            return null;
        WordInfo si1 = null;
        com.pullenti.n2j.Outargwrapper<WordInfo> inoutarg589 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres590 = com.pullenti.n2j.Utils.tryGetValue(m_Items, ini.substring(0, 0+1), inoutarg589);
        si1 = inoutarg589.value;
        if (!inoutres590) 
            return null;
        if (si1 == null) 
            return null;
        return _getBigramsInfo(si1, si2);
    }

    public WordInfo getWordInfo(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        return findItem(tt, true);
    }

    public static class WordInfo {
    
        public String normal;
    
        @Override
        public String toString() {
            return normal;
        }
    
        public int totalCount;
    
        public int lowerCount;
    
        public int upperCount;
    
        public int capitalCount;
    
        public int maleVerbsAfterCount;
    
        public int femaleVerbsAfterCount;
    
        /**
         * Есть ли перед атрибут персон (вычисляется только в процессе отработки соотв. анализатора на его 1-м проходе)
         */
        public boolean hasBeforePersonAttr;
    
        /**
         * Количество слов перед этим, которые не тексты или в нижнем регистре 
         *  (например, для проверки отчеств - фамилия ли это)
         */
        public int notCapitalBeforeCount;
    
        public java.util.HashMap<WordInfo, Integer> likeCharsBeforeWords;
    
        public java.util.HashMap<WordInfo, Integer> likeCharsAfterWords;
    
        public void addBefore(WordInfo w) {
            if (likeCharsBeforeWords == null) 
                likeCharsBeforeWords = new java.util.HashMap< >();
            if (!likeCharsBeforeWords.containsKey(w)) 
                likeCharsBeforeWords.put(w, 1);
            else 
                likeCharsBeforeWords.put(w, likeCharsBeforeWords.get(w) + 1);
        }
    
        public void addAfter(WordInfo w) {
            if (likeCharsAfterWords == null) 
                likeCharsAfterWords = new java.util.HashMap< >();
            if (!likeCharsAfterWords.containsKey(w)) 
                likeCharsAfterWords.put(w, 1);
            else 
                likeCharsAfterWords.put(w, likeCharsAfterWords.get(w) + 1);
        }
    
        public static WordInfo _new573(String _arg1) {
            WordInfo res = new WordInfo();
            res.normal = _arg1;
            return res;
        }
        public WordInfo() {
        }
    }

    public StatisticCollection() {
    }
    public static StatisticCollection _globalInstance;
    static {
        _globalInstance = new StatisticCollection(); 
    }
}
