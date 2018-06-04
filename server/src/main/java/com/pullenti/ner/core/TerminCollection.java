/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Коллекций некоторых обозначений, терминов
 */
public class TerminCollection {

    /**
     * Добавить термин. После добавления в термин нельзя вносить изменений, 
     *  кроме как в значения Tag и Tag2 (иначе потом нужно вызвать Reindex)
     * @param term 
     */
    public void add(Termin term) {
        termins.add(term);
        m_HashCanonic = null;
        reindex(term);
    }

    /**
     * Добавить строку вместе с морфологическими вариантами
     * @param _termins 
     * @param tag 
     * @param lang 
     * @return 
     */
    public Termin addStr(String _termins, Object tag, com.pullenti.morph.MorphLang lang, boolean isNormalText) {
        Termin t = new Termin(_termins, lang, isNormalText || allAddStrsNormalized);
        t.tag = tag;
        if (tag != null && t.terms.size() == 1) {
        }
        add(t);
        return t;
    }

    /**
     * Полный список понятий
     */
    public java.util.ArrayList<Termin> termins = new java.util.ArrayList< >();

    /**
     * Если установлено true, то все входные термины уже нормализованы
     */
    public boolean allAddStrsNormalized = false;

    public static class CharNode {
    
        public java.util.HashMap<Short, CharNode> children;
    
        public java.util.ArrayList<com.pullenti.ner.core.Termin> termins;
        public CharNode() {
        }
    }


    private CharNode m_Root;

    private CharNode m_RootUa;

    private CharNode _getRoot(com.pullenti.morph.MorphLang lang, boolean isLat) {
        if (lang != null && lang.isUa() && !lang.isRu()) 
            return m_RootUa;
        return m_Root;
    }

    private java.util.HashMap<Short, java.util.ArrayList<Termin>> m_Hash1 = new java.util.HashMap< >();

    private java.util.HashMap<String, java.util.ArrayList<Termin>> m_HashCanonic = null;

    /**
     * Переиндексировать термин (если после добавления у него что-либо поменялось)
     * @param t 
     */
    public void reindex(Termin t) {
        if (t == null) 
            return;
        if (t.terms.size() > 20) {
        }
        if (t.acronymSmart != null) 
            addToHash1((short)t.acronymSmart.charAt(0), t);
        if (t.abridges != null) {
            for(Termin.Abridge a : t.abridges) {
                if (a.parts.get(0).value.length() == 1) 
                    addToHash1((short)a.parts.get(0).value.charAt(0), t);
            }
        }
        for(String v : t.getHashVariants()) {
            _AddToTree(v, t);
        }
        if (t.additionalVars != null) {
            for(Termin av : t.additionalVars) {
                av.ignoreTermsOrder = t.ignoreTermsOrder;
                for(String v : av.getHashVariants()) {
                    _AddToTree(v, t);
                }
            }
        }
    }

    public void remove(Termin t) {
        for(String v : t.getHashVariants()) {
            _RemoveFromTree(v, t);
        }
        for(java.util.ArrayList<Termin> li : m_Hash1.values()) {
            for(Termin tt : li) {
                if (tt == t) {
                    li.remove(tt);
                    break;
                }
            }
        }
        int i = termins.indexOf(t);
        if (i >= 0) 
            termins.remove(i);
    }

    private void _AddToTree(String key, Termin t) {
        if (key == null) 
            return;
        CharNode nod = _getRoot(t.lang, t.lang.isUndefined() && com.pullenti.morph.LanguageHelper.isLatin(key));
        for(int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                nod.children = new java.util.HashMap< >();
            CharNode nn;
            com.pullenti.n2j.Outargwrapper<CharNode> inoutarg603 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres604 = com.pullenti.n2j.Utils.tryGetValue(nod.children, ch, inoutarg603);
            nn = inoutarg603.value;
            if (!inoutres604) 
                nod.children.put(ch, (nn = new CharNode()));
            nod = nn;
        }
        if (nod.termins == null) 
            nod.termins = new java.util.ArrayList< >();
        if (!nod.termins.contains(t)) 
            nod.termins.add(t);
    }

    private void _RemoveFromTree(String key, Termin t) {
        if (key == null) 
            return;
        CharNode nod = _getRoot(t.lang, t.lang.isUndefined() && com.pullenti.morph.LanguageHelper.isLatin(key));
        for(int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                return;
            CharNode nn;
            com.pullenti.n2j.Outargwrapper<CharNode> inoutarg605 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres606 = com.pullenti.n2j.Utils.tryGetValue(nod.children, ch, inoutarg605);
            nn = inoutarg605.value;
            if (!inoutres606) 
                return;
            nod = nn;
        }
        if (nod.termins == null) 
            return;
        if (nod.termins.contains(t)) 
            nod.termins.remove(t);
    }

    private java.util.ArrayList<Termin> _FindInTree(String key, com.pullenti.morph.MorphLang lang) {
        if (key == null) 
            return null;
        CharNode nod = _getRoot(lang, ((lang == null || lang.isUndefined())) && com.pullenti.morph.LanguageHelper.isLatin(key));
        for(int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                return null;
            CharNode nn;
            com.pullenti.n2j.Outargwrapper<CharNode> inoutarg607 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres608 = com.pullenti.n2j.Utils.tryGetValue(nod.children, ch, inoutarg607);
            nn = inoutarg607.value;
            if (!inoutres608) 
                return null;
            nod = nn;
        }
        return nod.termins;
    }

    private void addToHash1(short key, Termin t) {
        java.util.ArrayList<Termin> li = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<Termin>> inoutarg609 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres610 = com.pullenti.n2j.Utils.tryGetValue(m_Hash1, key, inoutarg609);
        li = inoutarg609.value;
        if (!inoutres610) 
            m_Hash1.put(key, (li = new java.util.ArrayList< >()));
        if (!li.contains(t)) 
            li.add(t);
    }

    public Termin find(String key) {
        if (com.pullenti.n2j.Utils.isNullOrEmpty(key)) 
            return null;
        java.util.ArrayList<Termin> li;
        if (com.pullenti.morph.LanguageHelper.isLatinChar(key.charAt(0))) 
            li = _FindInTree(key, com.pullenti.morph.MorphLang.EN);
        else {
            li = _FindInTree(key, com.pullenti.morph.MorphLang.RU);
            if (li == null) 
                li = _FindInTree(key, com.pullenti.morph.MorphLang.UA);
        }
        return (li != null && li.size() > 0 ? li.get(0) : null);
    }

    /**
     * Попытка привязать к аналитическому контейнеру с указанной позиции
     * @param token начальная позиция
     * @param pars параметры выделения
     * @return 
     */
    public TerminToken tryParse(com.pullenti.ner.Token token, TerminParseAttr pars) {
        if (termins.size() == 0) 
            return null;
        java.util.ArrayList<TerminToken> li = tryParseAll(token, pars);
        if (li != null) 
            return li.get(0);
        else 
            return null;
    }

    /**
     * Попытка привязать все возможные варианты
     * @param token 
     * @param pars параметры выделения
     * @return 
     */
    public java.util.ArrayList<TerminToken> tryParseAll(com.pullenti.ner.Token token, TerminParseAttr pars) {
        if (token == null) 
            return null;
        java.util.ArrayList<TerminToken> re = _TryAttachAll_(token, pars, false);
        if (re == null && token.getMorph().getLanguage().isUa()) 
            re = _TryAttachAll_(token, pars, true);
        return re;
    }

    private java.util.ArrayList<TerminToken> _TryAttachAll_(com.pullenti.ner.Token token, TerminParseAttr pars, boolean mainRoot) {
        if (termins.size() == 0 || token == null) 
            return null;
        String s = null;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null && (token instanceof com.pullenti.ner.ReferentToken)) 
            tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(token, com.pullenti.ner.ReferentToken.class))).getBeginToken(), com.pullenti.ner.TextToken.class);
        java.util.ArrayList<TerminToken> res = null;
        boolean wasVars = false;
        CharNode root = (mainRoot ? m_Root : _getRoot(token.getMorph().getLanguage(), token.chars.isLatinLetter()));
        if (tt != null) {
            s = tt.term;
            CharNode nod = root;
            boolean noVars = false;
            int len0 = 0;
            if ((((pars.value()) & (TerminParseAttr.TERMONLY.value()))) != (TerminParseAttr.NO.value())) {
            }
            else if (tt.invariantPrefixLength <= s.length()) {
                len0 = (int)tt.invariantPrefixLength;
                for(int i = 0; i < tt.invariantPrefixLength; i++) {
                    short ch = (short)s.charAt(i);
                    if (nod.children == null) {
                        noVars = true;
                        break;
                    }
                    CharNode nn;
                    com.pullenti.n2j.Outargwrapper<CharNode> inoutarg611 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres612 = com.pullenti.n2j.Utils.tryGetValue(nod.children, ch, inoutarg611);
                    nn = inoutarg611.value;
                    if (!inoutres612) {
                        noVars = true;
                        break;
                    }
                    nod = nn;
                }
            }
            if (!noVars) {
                com.pullenti.n2j.Outargwrapper<java.util.ArrayList<TerminToken>> inoutarg617 = new com.pullenti.n2j.Outargwrapper< >(res);
                boolean inoutres618 = _manageVar(token, pars, s, nod, len0, inoutarg617);
                res = inoutarg617.value;
                if (inoutres618) 
                    wasVars = true;
                for(int i = 0; i < tt.getMorph().getItemsCount(); i++) {
                    if ((((pars.value()) & (TerminParseAttr.TERMONLY.value()))) != (TerminParseAttr.NO.value())) 
                        continue;
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(tt.getMorph().getIndexerItem(i), com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    if ((((pars.value()) & (TerminParseAttr.INDICTIONARYONLY.value()))) != (TerminParseAttr.NO.value())) {
                        if (!wf.isInDictionary()) 
                            continue;
                    }
                    int j;
                    boolean ok = true;
                    if (wf.normalCase == null || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, s)) 
                        ok = false;
                    else {
                        for(j = 0; j < i; j++) {
                            com.pullenti.morph.MorphWordForm wf2 = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(tt.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                            if (wf2 != null) {
                                if (com.pullenti.n2j.Utils.stringsEq(wf2.normalCase, wf.normalCase) || com.pullenti.n2j.Utils.stringsEq(wf2.normalFull, wf.normalCase)) 
                                    break;
                            }
                        }
                        if (j < i) 
                            ok = false;
                    }
                    if (ok) {
                        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<TerminToken>> inoutarg613 = new com.pullenti.n2j.Outargwrapper< >(res);
                        boolean inoutres614 = _manageVar(token, pars, wf.normalCase, nod, (int)tt.invariantPrefixLength, inoutarg613);
                        res = inoutarg613.value;
                        if (inoutres614) 
                            wasVars = true;
                    }
                    if (wf.normalFull == null || com.pullenti.n2j.Utils.stringsEq(wf.normalFull, wf.normalCase) || com.pullenti.n2j.Utils.stringsEq(wf.normalFull, s)) 
                        continue;
                    for(j = 0; j < i; j++) {
                        com.pullenti.morph.MorphWordForm wf2 = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(tt.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                        if (wf2 != null && com.pullenti.n2j.Utils.stringsEq(wf2.normalFull, wf.normalFull)) 
                            break;
                    }
                    if (j < i) 
                        continue;
                    com.pullenti.n2j.Outargwrapper<java.util.ArrayList<TerminToken>> inoutarg615 = new com.pullenti.n2j.Outargwrapper< >(res);
                    boolean inoutres616 = _manageVar(token, pars, wf.normalFull, nod, (int)tt.invariantPrefixLength, inoutarg615);
                    res = inoutarg615.value;
                    if (inoutres616) 
                        wasVars = true;
                }
            }
        }
        else if (token instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<TerminToken>> inoutarg619 = new com.pullenti.n2j.Outargwrapper< >(res);
            boolean inoutres620 = _manageVar(token, pars, ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(token, com.pullenti.ner.NumberToken.class))).value).toString(), root, 0, inoutarg619);
            res = inoutarg619.value;
            if (inoutres620) 
                wasVars = true;
        }
        else 
            return null;
        if (!wasVars && s != null && s.length() == 1) {
            java.util.ArrayList<Termin> vars;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<Termin>> inoutarg621 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres622 = com.pullenti.n2j.Utils.tryGetValue(m_Hash1, (short)s.charAt(0), inoutarg621);
            vars = inoutarg621.value;
            if (inoutres622) {
                for(Termin t : vars) {
                    if (!t.lang.isUndefined()) {
                        if (!token.getMorph().getLanguage().isUndefined()) {
                            if (((com.pullenti.morph.MorphLang.ooBitand(token.getMorph().getLanguage(), t.lang))).isUndefined()) 
                                continue;
                        }
                    }
                    TerminToken ar = t.tryParse(tt, TerminParseAttr.NO);
                    if (ar == null) 
                        continue;
                    ar.termin = t;
                    if (res == null) {
                        res = new java.util.ArrayList< >();
                        res.add(ar);
                    }
                    else if (ar.getTokensCount() > res.get(0).getTokensCount()) {
                        res.clear();
                        res.add(ar);
                    }
                    else if (ar.getTokensCount() == res.get(0).getTokensCount()) 
                        res.add(ar);
                }
            }
        }
        if (res != null) {
            int ii = 0;
            int max = 0;
            for(int i = 0; i < res.size(); i++) {
                if (res.get(i).getLengthChar() > max) {
                    max = res.get(i).getLengthChar();
                    ii = i;
                }
            }
            if (ii > 0) {
                TerminToken v = res.get(ii);
                res.remove(ii);
                res.add(0, v);
            }
        }
        return res;
    }

    private boolean _manageVar(com.pullenti.ner.Token token, TerminParseAttr pars, String v, CharNode nod, int i0, com.pullenti.n2j.Outargwrapper<java.util.ArrayList<TerminToken>> res) {
        for(int i = i0; i < v.length(); i++) {
            short ch = (short)v.charAt(i);
            if (nod.children == null) 
                return false;
            CharNode nn;
            com.pullenti.n2j.Outargwrapper<CharNode> inoutarg623 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres624 = com.pullenti.n2j.Utils.tryGetValue(nod.children, ch, inoutarg623);
            nn = inoutarg623.value;
            if (!inoutres624) 
                return false;
            nod = nn;
        }
        java.util.ArrayList<Termin> vars = nod.termins;
        if (vars == null || vars.size() == 0) 
            return false;
        for(Termin t : vars) {
            TerminToken ar = t.tryParse(token, pars);
            if (ar != null) {
                ar.termin = t;
                if (res.value == null) {
                    res.value = new java.util.ArrayList< >();
                    res.value.add(ar);
                }
                else if (ar.getTokensCount() > res.value.get(0).getTokensCount()) {
                    res.value.clear();
                    res.value.add(ar);
                }
                else if (ar.getTokensCount() == res.value.get(0).getTokensCount()) {
                    int j;
                    for(j = 0; j < res.value.size(); j++) {
                        if (res.value.get(j).termin == ar.termin) 
                            break;
                    }
                    if (j >= res.value.size()) 
                        res.value.add(ar);
                }
            }
            if (t.additionalVars != null) {
                for(Termin av : t.additionalVars) {
                    ar = av.tryParse(token, pars);
                    if (ar == null) 
                        continue;
                    ar.termin = t;
                    if (res.value == null) {
                        res.value = new java.util.ArrayList< >();
                        res.value.add(ar);
                    }
                    else if (ar.getTokensCount() > res.value.get(0).getTokensCount()) {
                        res.value.clear();
                        res.value.add(ar);
                    }
                    else if (ar.getTokensCount() == res.value.get(0).getTokensCount()) {
                        int j;
                        for(j = 0; j < res.value.size(); j++) {
                            if (res.value.get(j).termin == ar.termin) 
                                break;
                        }
                        if (j >= res.value.size()) 
                            res.value.add(ar);
                    }
                }
            }
        }
        return v.length() > 1;
    }

    /**
     * Поискать эквивалентные термины
     * @param termin 
     * @return 
     */
    public java.util.ArrayList<Termin> tryAttach(Termin termin) {
        java.util.ArrayList<Termin> res = null;
        for(String v : termin.getHashVariants()) {
            java.util.ArrayList<Termin> vars = _FindInTree(v, termin.lang);
            if (vars == null) 
                continue;
            for(Termin t : vars) {
                if (t.isEqual(termin)) {
                    if (res == null) 
                        res = new java.util.ArrayList< >();
                    if (!res.contains(t)) 
                        res.add(t);
                }
            }
        }
        return res;
    }

    public java.util.ArrayList<Termin> tryAttachStr(String termin, com.pullenti.morph.MorphLang lang) {
        return _FindInTree(termin, lang);
    }

    public java.util.ArrayList<Termin> findTerminByCanonicText(String text) {
        if (m_HashCanonic == null) {
            m_HashCanonic = new java.util.HashMap< >();
            for(Termin t : termins) {
                String ct = t.getCanonicText();
                java.util.ArrayList<Termin> li;
                com.pullenti.n2j.Outargwrapper<java.util.ArrayList<Termin>> inoutarg625 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres626 = com.pullenti.n2j.Utils.tryGetValue(m_HashCanonic, ct, inoutarg625);
                li = inoutarg625.value;
                if (!inoutres626) 
                    m_HashCanonic.put(ct, (li = new java.util.ArrayList< >()));
                if (!li.contains(t)) 
                    li.add(t);
            }
        }
        java.util.ArrayList<Termin> res;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<Termin>> inoutarg627 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres628 = com.pullenti.n2j.Utils.tryGetValue(m_HashCanonic, text, inoutarg627);
        res = inoutarg627.value;
        if (!inoutres628) 
            return null;
        else 
            return res;
    }
    public TerminCollection() {
        if(_globalInstance == null) return;
        m_Root = new CharNode();
        m_RootUa = new CharNode();
    }
    public static TerminCollection _globalInstance;
    static {
        _globalInstance = new TerminCollection(); 
    }
}
