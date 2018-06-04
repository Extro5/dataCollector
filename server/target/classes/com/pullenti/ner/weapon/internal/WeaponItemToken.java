/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.weapon.internal;

public class WeaponItemToken extends com.pullenti.ner.MetaToken {

    public WeaponItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public Typs typ = Typs.NOUN;

    public String value;

    public String altValue;

    public boolean isDoubt;

    public boolean isAfterConjunction;

    public boolean isInternal;

    private java.util.ArrayList<WeaponItemToken> innerTokens = new java.util.ArrayList< >();

    public com.pullenti.ner.Referent ref;

    @Override
    public String toString() {
        return typ.toString() + ": " + ((String)com.pullenti.n2j.Utils.notnull(value, ((ref == null ? "" : ref.toString())))) + " " + ((String)com.pullenti.n2j.Utils.notnull(altValue, "")) + (isInternal ? "[int]" : "");
    }

    public static java.util.ArrayList<WeaponItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount) {
        WeaponItemToken tr = tryParse(t, null, false, false);
        if (tr == null) 
            return null;
        if (tr.typ == Typs.CLASS || tr.typ == Typs.DATE) 
            return null;
        WeaponItemToken tr0 = tr;
        java.util.ArrayList<WeaponItemToken> res = new java.util.ArrayList< >();
        if (tr.innerTokens.size() > 0) {
            res.addAll(tr.innerTokens);
            if (res.get(0).beginChar > tr.beginChar) 
                res.get(0).setBeginToken(tr.getBeginToken());
        }
        res.add(tr);
        t = tr.getEndToken().getNext();
        if (tr.typ == Typs.NOUN) {
            for(; t != null; t = t.getNext()) {
                if (t.isChar(':') || t.isHiphen()) {
                }
                else 
                    break;
            }
        }
        boolean andConj = false;
        for(; t != null; t = t.getNext()) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            if (t.isChar(':')) 
                continue;
            if (tr0.typ == Typs.NOUN) {
                if (t.isHiphen() && t.getNext() != null) 
                    t = t.getNext();
            }
            tr = tryParse(t, tr0, false, false);
            if (tr == null) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false) && t.getNext() != null) {
                    if (tr0.typ == Typs.MODEL || tr0.typ == Typs.BRAND) {
                        com.pullenti.ner.Token tt1 = t.getNext();
                        if (tt1 != null && tt1.isComma()) 
                            tt1 = tt1.getNext();
                        tr = tryParse(tt1, tr0, false, false);
                    }
                }
            }
            if (tr == null && (t instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                if (rt.getBeginToken() == rt.getEndToken() && (rt.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                    tr = tryParse(rt.getBeginToken(), tr0, false, false);
                    if (tr != null && tr.getBeginToken() == tr.getEndToken()) 
                        tr.setBeginToken(tr.setEndToken(t));
                }
            }
            if (tr == null && t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    com.pullenti.ner.Token tt = br.getEndToken().getNext();
                    if (tt != null && tt.isComma()) 
                        tt = tt.getNext();
                    tr = tryParse(tt, tr0, false, false);
                    if (tr != null && tr.typ == Typs.NUMBER) {
                    }
                    else 
                        tr = null;
                }
            }
            if (tr == null && t.isHiphen()) {
                if (tr0.typ == Typs.BRAND || tr0.typ == Typs.MODEL) 
                    tr = tryParse(t.getNext(), tr0, false, false);
            }
            if (tr == null && t.isComma()) {
                if ((tr0.typ == Typs.NAME || tr0.typ == Typs.BRAND || tr0.typ == Typs.MODEL) || tr0.typ == Typs.CLASS || tr0.typ == Typs.DATE) {
                    tr = tryParse(t.getNext(), tr0, true, false);
                    if (tr != null) {
                        if (tr.typ == Typs.NUMBER) {
                        }
                        else 
                            tr = null;
                    }
                }
            }
            if (tr == null) 
                break;
            if (t.isNewlineBefore()) {
                if (tr.typ != Typs.NUMBER) 
                    break;
            }
            if (tr.innerTokens.size() > 0) 
                res.addAll(tr.innerTokens);
            res.add(tr);
            tr0 = tr;
            t = tr.getEndToken();
            if (andConj) 
                break;
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == Typs.MODEL && res.get(i + 1).typ == Typs.MODEL) {
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.get(i).value = res.get(i).value + (res.get(i).getEndToken().getNext() != null && res.get(i).getEndToken().getNext().isHiphen() ? '-' : ' ') + res.get(i + 1).value;
                res.remove(i + 1);
                i--;
            }
        }
        return res;
    }

    public static WeaponItemToken tryParse(com.pullenti.ner.Token t, WeaponItemToken prev, boolean afterConj, boolean attachHigh) {
        WeaponItemToken res = _TryParse(t, prev, afterConj, attachHigh);
        if (res == null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.noun.beginChar > npt.beginChar) {
                res = _TryParse(npt.noun.getBeginToken(), prev, afterConj, attachHigh);
                if (res != null) {
                    if (res.typ == Typs.NOUN) {
                        String str = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        if (com.pullenti.n2j.Utils.stringsEq(str, "РУЧНОЙ ГРАНАТ")) 
                            str = "РУЧНАЯ ГРАНАТА";
                        if ((((String)com.pullenti.n2j.Utils.notnull(str, ""))).endsWith(res.value)) {
                            if (res.altValue == null) 
                                res.altValue = str;
                            else {
                                str = str.substring(0, 0+(str.length() - res.value.length())).trim();
                                res.altValue = str + " " + res.altValue;
                            }
                            res.setBeginToken(t);
                            return res;
                        }
                    }
                }
            }
            return null;
        }
        if (res.typ == Typs.NAME) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.isChar('(')) {
                String alt = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(res.value, alt)) {
                    res.altValue = alt;
                    res.setEndToken(br.getEndToken());
                }
            }
        }
        return res;
    }

    private static WeaponItemToken _TryParse(com.pullenti.ner.Token t, WeaponItemToken prev, boolean afterConj, boolean attachHigh) {
        if (t == null) 
            return null;
        if (com.pullenti.ner.core.BracketHelper.isBracket(t, true)) {
            WeaponItemToken wit = _TryParse(t.getNext(), prev, afterConj, attachHigh);
            if (wit != null) {
                if (wit.getEndToken().getNext() == null) {
                    wit.setBeginToken(t);
                    return wit;
                }
                if (com.pullenti.ner.core.BracketHelper.isBracket(wit.getEndToken().getNext(), true)) {
                    wit.setBeginToken(t);
                    wit.setEndToken(wit.getEndToken().getNext());
                    return wit;
                }
            }
        }
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            WeaponItemToken res = new WeaponItemToken(t, tok.getEndToken());
            res.typ = (Typs)tok.termin.tag;
            if (res.typ == Typs.NOUN) {
                res.value = tok.termin.getCanonicText();
                if (tok.termin.tag2 != null) 
                    res.isDoubt = true;
                for(com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.getWhitespacesBeforeCount() > 2) 
                        break;
                    WeaponItemToken wit = _TryParse(tt, null, false, false);
                    if (wit != null) {
                        if (wit.typ == Typs.BRAND) {
                            res.innerTokens.add(wit);
                            res.setEndToken((tt = wit.getEndToken()));
                            continue;
                        }
                        break;
                    }
                    if (!((tt instanceof com.pullenti.ner.TextToken))) 
                        break;
                    com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                    if (com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.ADJECTIVE)) {
                        if (res.altValue == null) 
                            res.altValue = res.value;
                        if (res.altValue.endsWith(res.value)) 
                            res.altValue = res.altValue.substring(0, 0+(res.altValue.length() - res.value.length()));
                        res.altValue = res.altValue + (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term + " " + res.value;
                        res.setEndToken(tt);
                        continue;
                    }
                    break;
                }
                return res;
            }
            if (res.typ == Typs.BRAND || res.typ == Typs.NAME) {
                res.value = tok.termin.getCanonicText();
                return res;
            }
            if (res.typ == Typs.MODEL) {
                res.value = tok.termin.getCanonicText();
                if (tok.termin.tag2 instanceof java.util.ArrayList) {
                    java.util.ArrayList<com.pullenti.ner.core.Termin> li = (java.util.ArrayList<com.pullenti.ner.core.Termin>)com.pullenti.n2j.Utils.cast(tok.termin.tag2, java.util.ArrayList.class);
                    for(com.pullenti.ner.core.Termin to : li) {
                        WeaponItemToken wit = _new2690(t, tok.getEndToken(), (Typs)to.tag, to.getCanonicText(), tok.getBeginToken() == tok.getEndToken());
                        res.innerTokens.add(wit);
                        if (to.additionalVars != null && to.additionalVars.size() > 0) 
                            wit.altValue = to.additionalVars.get(0).getCanonicText();
                    }
                }
                res._correctModel();
                return res;
            }
        }
        com.pullenti.ner.Token nnn = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
        if (nnn != null) {
            com.pullenti.ner.transport.internal.TransItemToken tit = com.pullenti.ner.transport.internal.TransItemToken._attachNumber(nnn, true);
            if (tit != null) {
                WeaponItemToken res = _new2691(t, tit.getEndToken(), Typs.NUMBER);
                res.value = tit.value;
                res.altValue = tit.altValue;
                return res;
            }
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && t.chars.isAllUpper()) && (t.getLengthChar() < 4)) {
            if ((t.getNext() != null && ((t.getNext().isHiphen() || t.getNext().isChar('.'))) && (t.getNext().getWhitespacesAfterCount() < 2)) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                WeaponItemToken res = _new2692(t, t.getNext(), Typs.MODEL, true);
                res.value = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                res._correctModel();
                return res;
            }
            if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && !t.isWhitespaceAfter()) {
                WeaponItemToken res = _new2692(t, t, Typs.MODEL, true);
                res.value = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                res._correctModel();
                return res;
            }
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) && t.getLengthChar() > 2) {
            boolean ok = false;
            if (prev != null && ((prev.typ == Typs.NOUN || prev.typ == Typs.MODEL || prev.typ == Typs.BRAND))) 
                ok = true;
            else if (prev == null && t.getPrevious() != null && t.getPrevious().isCommaAnd()) 
                ok = true;
            if (ok) {
                WeaponItemToken res = _new2692(t, t, Typs.NAME, true);
                res.value = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && com.pullenti.morph.CharsInfo.ooEq(t.getNext().getNext().chars, t.chars)) {
                    res.value = res.value + "-" + (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.TextToken.class))).term;
                    res.setEndToken(t.getNext().getNext());
                }
                if (prev != null && prev.typ == Typs.NOUN) 
                    res.typ = Typs.BRAND;
                if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isHiphen() && (res.getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    res.typ = Typs.MODEL;
                    res._correctModel();
                }
                else if (!res.getEndToken().isWhitespaceAfter() && (res.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    res.typ = Typs.MODEL;
                    res._correctModel();
                }
                return res;
            }
        }
        return null;
    }

    private void _correctModel() {
        com.pullenti.ner.Token tt = getEndToken().getNext();
        if (tt == null || tt.getWhitespacesBeforeCount() > 2) 
            return;
        if (tt.isValue(":\\/.", null) || tt.isHiphen()) 
            tt = tt.getNext();
        if (!((tt instanceof com.pullenti.ner.NumberToken))) 
            return;
        StringBuilder tmp = new StringBuilder();
        tmp.append((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value);
        boolean isLat = com.pullenti.morph.LanguageHelper.isLatinChar(value.charAt(0));
        setEndToken(tt);
        for(tt = tt.getNext(); tt != null; tt = tt.getNext()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && tt.chars.isLetter()) {
                if (!tt.isWhitespaceBefore() || ((tt.getPrevious() != null && tt.getPrevious().isHiphen()))) {
                    char ch = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term.charAt(0);
                    setEndToken(tt);
                    char ch2 = (char)0;
                    if (com.pullenti.morph.LanguageHelper.isLatinChar(ch) && !isLat) {
                        ch2 = com.pullenti.morph.LanguageHelper.getCyrForLat(ch);
                        if (ch2 != ((char)0)) 
                            ch = ch2;
                    }
                    else if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch) && isLat) {
                        ch2 = com.pullenti.morph.LanguageHelper.getLatForCyr(ch);
                        if (ch2 != ((char)0)) 
                            ch = ch2;
                    }
                    tmp.append(ch);
                    continue;
                }
            }
            break;
        }
        value = value + "-" + tmp.toString();
        altValue = com.pullenti.ner.core.MiscHelper.createCyrLatAlternative(value);
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        com.pullenti.ner.core.Termin tt;
        java.util.ArrayList<com.pullenti.ner.core.Termin> li;
        t = com.pullenti.ner.core.Termin._new118("ПИСТОЛЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("РЕВОЛЬВЕР", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ВИНТОВКА", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("РУЖЬЕ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("АВТОМАТ", Typs.NOUN, 1);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("КАРАБИН", Typs.NOUN, 1);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new142("ПИСТОЛЕТ-ПУЛЕМЕТ", "ПИСТОЛЕТ-ПУЛЕМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ПУЛЕМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ГРАНАТОМЕТ", Typs.NOUN);
        t.addVariant("СТРЕЛКОВО ГРАНАТОМЕТНЫЙ КОМПЛЕКС", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ОГНЕМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("МИНОМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2706("ПЕРЕНОСНОЙ ЗЕНИТНО РАКЕТНЫЙ КОМПЛЕКС", "ПЗРК", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2706("ПРОТИВОТАНКОВЫЙ РАКЕТНЫЙ КОМПЛЕКС", "ПТРК", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ГРАНАТА", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ЛИМОНКА", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("НОЖ", Typs.NOUN);
        m_Ontology.add(t);
        for(String s : new String[] {"МАКАРОВ", "КАЛАШНИКОВ", "СИМОНОВ", "СТЕЧКИН", "ШМАЙСЕР", "МОСИН", "СЛОСТИН", "НАГАН", "МАКСИМ", "ДРАГУНОВ", "СЕРДЮКОВ", "ЯРЫГИН", "НИКОНОВ", "МАУЗЕР", "БРАУНИНГ", "КОЛЬТ", "ВИНЧЕСТЕР"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new118(s, Typs.BRAND));
        }
        for(String s : new String[] {"УЗИ"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new118(s, Typs.NAME));
        }
        t = com.pullenti.ner.core.Termin._new2713("ТУЛЬСКИЙ ТОКАРЕВА", "ТТ", "ТТ", Typs.MODEL);
        li = new java.util.ArrayList< >();
        li.add(com.pullenti.ner.core.Termin._new118("ПИСТОЛЕТ", Typs.NOUN));
        li.add(com.pullenti.ner.core.Termin._new118("ТОКАРЕВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2713("ПИСТОЛЕТ МАКАРОВА", "ПМ", "ПМ", Typs.MODEL);
        li = new java.util.ArrayList< >();
        li.add(com.pullenti.ner.core.Termin._new118("ПИСТОЛЕТ", Typs.NOUN));
        li.add(com.pullenti.ner.core.Termin._new118("МАКАРОВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2713("ПИСТОЛЕТ МАКАРОВА МОДЕРНИЗИРОВАННЫЙ", "ПММ", "ПММ", Typs.MODEL);
        li = new java.util.ArrayList< >();
        li.add((tt = com.pullenti.ner.core.Termin._new118("ПИСТОЛЕТ", Typs.NOUN)));
        tt.addVariant("МОДЕРНИЗИРОВАННЫЙ ПИСТОЛЕТ", false);
        li.add(com.pullenti.ner.core.Termin._new118("МАКАРОВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2713("АВТОМАТ КАЛАШНИКОВА", "АК", "АК", Typs.MODEL);
        li = new java.util.ArrayList< >();
        li.add(com.pullenti.ner.core.Termin._new118("АВТОМАТ", Typs.NOUN));
        li.add(com.pullenti.ner.core.Termin._new118("КАЛАШНИКОВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
    }

    public static class Typs implements Comparable<Typs> {
    
        public static final Typs NOUN; // 0
    
        public static final Typs BRAND; // 1
    
        public static final Typs MODEL; // 2
    
        public static final Typs NUMBER; // 3
    
        public static final Typs NAME; // 4
    
        public static final Typs CLASS; // 5
    
        public static final Typs DATE; // 6
    
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
            NOUN = new Typs(0, "NOUN");
            mapIntToEnum.put(NOUN.value(), NOUN);
            mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
            BRAND = new Typs(1, "BRAND");
            mapIntToEnum.put(BRAND.value(), BRAND);
            mapStringToEnum.put(BRAND.m_str.toUpperCase(), BRAND);
            MODEL = new Typs(2, "MODEL");
            mapIntToEnum.put(MODEL.value(), MODEL);
            mapStringToEnum.put(MODEL.m_str.toUpperCase(), MODEL);
            NUMBER = new Typs(3, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            NAME = new Typs(4, "NAME");
            mapIntToEnum.put(NAME.value(), NAME);
            mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
            CLASS = new Typs(5, "CLASS");
            mapIntToEnum.put(CLASS.value(), CLASS);
            mapStringToEnum.put(CLASS.m_str.toUpperCase(), CLASS);
            DATE = new Typs(6, "DATE");
            mapIntToEnum.put(DATE.value(), DATE);
            mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
        }
    }


    public static WeaponItemToken _new2690(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, String _arg4, boolean _arg5) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.isInternal = _arg5;
        return res;
    }
    public static WeaponItemToken _new2691(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public static WeaponItemToken _new2692(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, boolean _arg4) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isDoubt = _arg4;
        return res;
    }
    public WeaponItemToken() {
        super();
    }
    public static WeaponItemToken _globalInstance;
    static {
        _globalInstance = new WeaponItemToken(); 
    }
}
