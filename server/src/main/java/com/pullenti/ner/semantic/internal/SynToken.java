/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class SynToken extends com.pullenti.ner.MetaToken implements Comparable<SynToken> {

    public static SynToken tryParse(com.pullenti.ner.Token t, java.util.ArrayList<SynToken> prevs) {
        if (t == null) 
            return null;
        SynToken prev = (prevs == null || prevs.size() == 0 ? null : prevs.get(prevs.size() - 1));
        SynToken res = null;
        if (t instanceof com.pullenti.ner.ReferentToken) {
            res = _new2513(t, t, Types.OBJ, t.getReferent(), t.getMorph());
            if (com.pullenti.n2j.Utils.stringsEq(res.ref.getTypeName(), "DENOMINATION")) {
                res.typ = Types.PROPERNAME;
                for(com.pullenti.ner.Slot s : res.ref.getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "VALUE") && (s.getValue() instanceof String)) 
                        res.addVal((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class), ValTypes.NAME);
                }
                res.ref = null;
            }
            return res;
        }
        if ((t.getNext() != null && t.getNext().isHiphen() && ((t.getMorph().getLanguage().isRu() && ((t.isValue("В", null) || t.isValue("ВО", null)))))) || (((t.getMorph().getLanguage().isUa() && t.isValue("ПО", null))) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken))) 
            return _new2480(t, t.getNext().getNext(), Types.DELIMETER);
        if (t.chars.isLatinLetter() && !t.chars.isAllLower()) {
            res = _new2480(t, t, Types.PROPERNAME);
            for(com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore()) 
                    break;
                if (!tt.chars.isLatinLetter()) {
                    if (tt.chars.isLetter()) 
                        break;
                    if (tt.isChar('(')) 
                        break;
                    continue;
                }
                res.setEndToken(tt);
            }
            res.addVal(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.NO), ValTypes.NAME);
            if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('(')) {
                java.util.ArrayList<SynToken> tmp = new java.util.ArrayList< >();
                tmp.add(res);
                SynToken res1 = tryParse(res.getEndToken().getNext().getNext(), tmp);
                if ((res1 != null && res1.typ == Types.PROPERNAME && res1.getEndToken().getNext() != null) && res1.getEndToken().getNext().isChar(')')) {
                    res.addVals(res1.vals);
                    res.setEndToken(res1.getEndToken().getNext());
                }
            }
            return res;
        }
        String term = (t instanceof com.pullenti.ner.TextToken ? (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term : null);
        if (com.pullenti.n2j.Utils.stringsEq(term, "ТОЙ")) {
        }
        if (com.pullenti.n2j.Utils.stringsEq(term, "ТАК")) {
            SynToken res1 = tryParse(t.getNext(), null);
            if (res1 != null && res1.typ == Types.ADVERB) {
                res1.setBeginToken(t);
                return res1;
            }
        }
        if ((com.pullenti.n2j.Utils.stringsEq(term, "ЖЕ") || com.pullenti.n2j.Utils.stringsEq(term, "ЛИ") || com.pullenti.n2j.Utils.stringsEq(term, "БЫ")) || ((com.pullenti.n2j.Utils.stringsEq(term, "БИ") && t.getMorph().getLanguage().isUa()))) 
            return _new2516(t, t, term, Types.ADVERB);
        if (t instanceof com.pullenti.ner.TextToken) {
            com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (t1.getMorph()._getClass().isPreposition() && (t1.getNext() instanceof com.pullenti.ner.TextToken)) 
                t1 = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class);
            if (t1.getMorph()._getClass().isPronoun()) {
                com.pullenti.ner.core.TerminToken tok1 = m_Onto.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok1 != null && ((Types)tok1.termin.tag) == Types.WHAT) {
                    SynToken res1 = _new2517(t, t1, Types.WHAT, t1.getMorph());
                    res1.setBase(tok1.termin.getCanonicText());
                    if (tok1.termin.tag2 != null) 
                        res1.anaforRef0 = t1;
                    res1.getMorph().removeItems(com.pullenti.morph.MorphClass.PRONOUN, false);
                    if (t1 != t) {
                        res1.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        String prep = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        com.pullenti.morph.MorphCase pca = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(prep);
                        if (!((com.pullenti.morph.MorphCase.ooBitand(pca, res1.getMorph().getCase()))).isUndefined()) 
                            res1.getMorph().removeItems(pca);
                    }
                    com.pullenti.ner.Token tt1 = t1.getNext();
                    if (tt1 != null && tt1.isHiphen()) 
                        tt1 = tt1.getNext();
                    if (tt1 instanceof com.pullenti.ner.TextToken) {
                        term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term;
                        if ((com.pullenti.n2j.Utils.stringsEq(term, "НИБУДЬ") || com.pullenti.n2j.Utils.stringsEq(term, "ТО") || com.pullenti.n2j.Utils.stringsEq(term, "ЛИБО")) || com.pullenti.n2j.Utils.stringsEq(term, "НЕБУДЬ")) {
                            res1.anaforRef0 = null;
                            res1.setBase(res1.getBase() + "-" + term);
                            res1.typ = Types.ACTANT;
                            res1.setEndToken(tt1);
                        }
                    }
                    return res1;
                }
            }
        }
        com.pullenti.ner.core.TerminToken tok = m_Onto.tryParse(t, com.pullenti.ner.core.TerminParseAttr.TERMONLY);
        if (tok != null && ((Types)tok.termin.tag) == Types.EMPTY) {
            com.pullenti.ner.core.NounPhraseToken npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse((t.getMorph()._getClass().isPreposition() && tok.getBeginToken() != tok.getEndToken() ? t.getNext() : t), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt0 != null && npt0.endChar > tok.endChar) 
                tok = null;
        }
        if (tok != null && ((Types)tok.termin.tag) == Types.EMPTY) {
            res = _new2480(t, tok.getEndToken(), Types.EMPTY);
            for(com.pullenti.ner.Token tt = tok.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (((tt.isChar(',') || com.pullenti.n2j.Utils.stringsEq(term, "ЧТО") || com.pullenti.n2j.Utils.stringsEq(term, "ГДЕ")) || com.pullenti.n2j.Utils.stringsEq(term, "КАК") || tt.isValue("ЖЕ", null)) || ((tt.getMorph().getLanguage().isUa() && ((com.pullenti.n2j.Utils.stringsEq(term, "ЩО") || com.pullenti.n2j.Utils.stringsEq(term, "ДЕ") || com.pullenti.n2j.Utils.stringsEq(term, "ЯК")))))) {
                    res.setEndToken(tt);
                    continue;
                }
                break;
            }
            return res;
        }
        if (tok == null) {
            tok = m_Onto.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null && t.getMorph()._getClass().isPreposition()) 
                tok = m_Onto.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && ((Types)tok.termin.tag) != Types.TIME) 
                tok = null;
        }
        if (tok != null && ((Types)tok.termin.tag) != Types.TIMEMISC) {
            res = _new2517(t, tok.getEndToken(), (Types)tok.termin.tag, tok.getMorph());
            if (t.getMorph()._getClass().isPreposition()) 
                res.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
            res.setBase(tok.termin.getCanonicText());
            return res;
        }
        tok = m_OntoNum.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            com.pullenti.ner.Token tt = tok.getEndToken().getNext();
            com.pullenti.ner.MorphCollection _morph = tok.getMorph();
            boolean ok = false;
            if (tt != null && (((tt.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value()) && tt.getMorph().getCase().isGenitive()) 
                ok = true;
            else if ((tt != null && (((tt.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value()) && tt.getMorph().getCase().isNominative()) && ((com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "КАЖДЫЙ") || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ЛЮБОЙ") || ((t.getMorph().getLanguage().isUa() && ((com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "КОЖЕН") || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "БУДЬ")))))))) 
                ok = true;
            else if (t.isValue("ВЕСЬ", null) && t.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
            }
            else 
                ok = true;
            if (ok) {
                res = _new2517(t, tok.getEndToken(), Types.NUMBER, _morph);
                String val = tok.termin.getCanonicText();
                if (((com.pullenti.n2j.Utils.stringsEq(val, "НЕСКОЛЬКО") || com.pullenti.n2j.Utils.stringsEq(val, "КІЛЬКА"))) && (tok.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
                    term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tok.getEndToken().getNext(), com.pullenti.ner.TextToken.class))).term;
                    if (com.pullenti.n2j.Utils.stringsEq(term, "СОТ")) 
                        term = (t.getMorph().getLanguage().isUa() ? "СОТЕНЬ" : "СОТЕН");
                    if ((com.pullenti.n2j.Utils.stringsEq(term, "ДЕСЯТКОВ") || com.pullenti.n2j.Utils.stringsEq(term, "СОТЕН") || com.pullenti.n2j.Utils.stringsEq(term, "ТЫСЯЧ")) || com.pullenti.n2j.Utils.stringsEq(term, "МИЛЛИОНОВ") || com.pullenti.n2j.Utils.stringsEq(term, "МИЛЛИАРДОВ")) {
                        res.setEndToken(tok.getEndToken().getNext());
                        val = val + " " + term;
                    }
                    else if (t.getMorph().getLanguage().isUa()) {
                        if ((com.pullenti.n2j.Utils.stringsEq(term, "ДЕСЯТКІВ") || com.pullenti.n2j.Utils.stringsEq(term, "СОТЕНЬ") || com.pullenti.n2j.Utils.stringsEq(term, "ТИСЯЧ")) || com.pullenti.n2j.Utils.stringsEq(term, "МІЛЬЙОНІВ") || com.pullenti.n2j.Utils.stringsEq(term, "МІЛЬЯРДІВ")) {
                            res.setEndToken(tok.getEndToken().getNext());
                            val = val + " " + term;
                        }
                    }
                }
                res.addVal(val, ValTypes.NUMBER);
                return res;
            }
        }
        if ((t.getMorph()._getClass().isPreposition() && t.getNext() != null && t.getNext().getMorph()._getClass().isPronoun()) && t.getNext().getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if ((((tok = m_OntoNum.tryParse(t.getNext().getNext(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                res = _new2521(t, tok.getEndToken(), Types.NUMBER, tok.getMorph(), (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class));
                res.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                res.addVal(tok.termin.getCanonicText(), ValTypes.NUMBER);
                return res;
            }
        }
        if (t.isComma()) {
            if (t.getNext() != null && t.getNext().isValue("А", null)) {
                com.pullenti.ner.Token tt = t.getNext().getNext();
                if (tt != null && tt.isComma()) 
                    tt = tt.getNext();
                tok = m_Onto.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.TERMONLY);
                if (tok != null && ((com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "СЛЕДОВАТЕЛЬНО") || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ОТЖЕ")))) 
                    return _new2480(t, tok.getEndToken(), Types.CONJ);
                return _new2480(t, t.getNext(), Types.DELIMETER);
            }
            return _new2480(t, t, Types.COMMA);
        }
        if (t.isOr() || t.isAnd()) 
            return _new2480(t, t, Types.CONJ);
        if ((t instanceof com.pullenti.ner.NumberToken) && !t.getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(t);
            if (nex != null) {
                if (nex.getMorph()._getClass().isAdjective()) {
                    res = _new2517(t, nex.getEndToken(), Types.NUMBER, nex.getMorph());
                    res.setBase(nex.toString().toUpperCase());
                }
                else {
                    res = _new2517(t, nex.getEndToken(), Types.OBJ, nex.getMorph());
                    res.setBase(com.pullenti.ner.core.NumberExToken.exTypToString(nex.exTyp, com.pullenti.ner.core.NumberExType.UNDEFINED));
                    if (com.pullenti.morph.LanguageHelper.endsWith(res.getBase(), ".")) 
                        res.setBase(nex.getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    res.getMorph().setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                    String num = com.pullenti.ner.core.NumberExToken.convertToString(nex.realValue);
                    if (res.getEndToken().getNext() != null && ((res.getEndToken().getNext().isHiphen() || res.getEndToken().getNext().isValue("ДО", null)))) {
                        com.pullenti.ner.core.NumberExToken nex2 = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(res.getEndToken().getNext().getNext());
                        if (nex2 != null && nex2.realValue > nex.realValue && nex.exTyp == nex2.exTyp) {
                            num = num + ".." + com.pullenti.ner.core.NumberExToken.convertToString(nex2.realValue);
                            res.setEndToken(nex2.getEndToken());
                        }
                    }
                    if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition()) {
                        if (t.getPrevious().isValue("ОТ", "ВІД")) {
                            res.setBeginToken(t.getPrevious());
                            if (!(num.indexOf("..") >= 0)) 
                                num = num + "..*";
                        }
                    }
                    res.addVal(num, ValTypes.NUMBER);
                }
                return res;
            }
            res = _new2517(t, t, Types.NUMBER, t.getMorph());
            String nval = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString();
            if (t.getNext() != null && ((t.getNext().isHiphen() || t.getNext().isValue("ДО", null))) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                if ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).value > (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value) {
                    nval = nval + ".." + (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).value;
                    res.setEndToken(t.getNext().getNext());
                    if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("ВКЛЮЧИТЕЛЬНО", "ВКЛЮЧНО")) 
                        res.setEndToken(res.getEndToken().getNext());
                }
            }
            if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition()) {
                if (t.getPrevious().isValue("ОТ", "ВІД")) {
                    res.setBeginToken(t.getPrevious());
                    if (!(nval.indexOf("..") >= 0)) 
                        nval = nval + "..*";
                }
            }
            res.addVal(nval, ValTypes.NUMBER);
            if (res.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && com.pullenti.n2j.Utils.stringsNe(nval, "1")) {
                for(com.pullenti.morph.MorphBaseInfo it : res.getMorph().getItems()) {
                    if (it._getClass().isNoun()) 
                        it.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                }
            }
            res.getMorph().removeItems(com.pullenti.morph.MorphClass.NOUN, false);
            t = res.getEndToken().getNext();
            if (t != null && t.getMorph()._getClass().isConjunction() && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
                term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term;
                if ((((com.pullenti.n2j.Utils.stringsEq(term, "БОЛЕЕ") || com.pullenti.n2j.Utils.stringsEq(term, "БІЛЬШ") || com.pullenti.n2j.Utils.stringsEq(term, "МЕНЕЕ")) || com.pullenti.n2j.Utils.stringsEq(term, "МЕНШ"))) && !((t.getNext().getNext() instanceof com.pullenti.ner.NumberToken))) {
                    String num = res.getVal(ValTypes.NUMBER);
                    if (num != null) {
                        res.delVals(ValTypes.NUMBER);
                        res.addVal((com.pullenti.n2j.Utils.stringsEq(term, "БОЛЕЕ") || com.pullenti.n2j.Utils.stringsEq(term, "БІЛЬШ") ? num + "..*" : "0.." + num), ValTypes.NUMBER);
                        res.setEndToken(t.getNext());
                        res.getMorph().setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                    }
                }
            }
            return res;
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && (br.getLengthChar() < 100)) {
                boolean nam = false;
                if (!t.getNext().chars.isAllLower()) {
                    if (t.getNext().chars.isLatinLetter()) 
                        nam = true;
                    else if ((t.getNext() instanceof com.pullenti.ner.TextToken) && prev != null) {
                        if (prev.typ == Types.OBJ || prev.typ == Types.COMMA || prev.typ == Types.CONJ) 
                            nam = true;
                    }
                    if (!nam) {
                        SynToken res1 = tryParse(t.getNext(), prevs);
                        if (res1 != null) {
                            if (res1.typ == Types.PROPERNAME || ((res1.ref != null && com.pullenti.n2j.Utils.stringsEq(res1.ref.getTypeName(), "GEO")))) 
                                nam = true;
                        }
                    }
                }
                if (nam) {
                    res = _new2480(t, br.getEndToken(), Types.PROPERNAME);
                    res.addVal(com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), ValTypes.NAME);
                    res.addVal(com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE), ValTypes.NAME);
                    return res;
                }
            }
            return _new2480(t, t, Types.BRACKETOPEN);
        }
        if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false)) 
            return _new2480(t, t, Types.BRACKETCLOSE);
        if (t.isValue("САМ", null)) {
            if (t.getNext() != null && t.getNext().isValue("САМ", null)) 
                return _new2532(t, t.getNext(), Types.EMPTY, (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class), t.getMorph(), t.getNext().getMorph());
            if ((t.getNext() != null && t.getNext().getMorph()._getClass().isPreposition() && t.getNext().getNext() != null) && t.getNext().getNext().isValue("САМ", null)) 
                return _new2533(t, t.getNext().getNext(), Types.EMPTY, (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class), t.getMorph());
        }
        if (t.isValue("КОТОРЫЙ", "КОТРИЙ") || t.isValue("САМ", "ЯКИЙ")) 
            return _new2533(t, t, Types.EMPTY, (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class), t.getMorph());
        com.pullenti.morph.MorphClass cla = new com.pullenti.morph.MorphClass(null);
        if (t instanceof com.pullenti.ner.TextToken) 
            cla = t.getMorphClassInDictionary();
        if ((t instanceof com.pullenti.ner.TextToken) && cla.isVerb() && cla.isAdjective()) {
            boolean ok = true;
            boolean comma = false;
            if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition()) 
                ok = false;
            else 
                for(com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.isChar('.') || tt.isNewlineAfter()) 
                        break;
                    if (tt.isCommaAnd()) {
                        comma = true;
                        continue;
                    }
                    com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                    if (mc.isNoun()) {
                        if (!comma) 
                            ok = false;
                        break;
                    }
                    if (mc.isVerb()) {
                        ok = false;
                        break;
                    }
                }
            if (ok) {
                res = _new2517(t, t, Types.ACTPRICH, t.getMorph());
                res.setBase(t.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, true, com.pullenti.morph.MorphGender.UNDEFINED, false));
                return res;
            }
        }
        if (cla.isPersonalPronoun() || cla.isPronoun()) {
            com.pullenti.ner.core.TerminToken tok1 = m_OntoPronoun.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok1 != null || t.isValue("СВОЙ", "СВІЙ")) {
                res = _new2480(t, t, Types.PRONOUNOBJ);
                for(com.pullenti.morph.MorphBaseInfo it : t.getMorph().getItems()) {
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    com.pullenti.morph.MorphWordForm bi = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf.clone(), com.pullenti.morph.MorphWordForm.class);
                    String key = wf.normalCase;
                    int ii = m_PronounsAdj.indexOf(key);
                    if ((ii < 0) && wf.normalFull != null) 
                        ii = m_PronounsAdj.indexOf(wf.normalFull);
                    if (ii >= 0) {
                        key = (bi.normalCase = m_Pronouns.get(ii));
                        bi._setClass(com.pullenti.morph.MorphClass.PRONOUN);
                    }
                    else if (tok1 != null) {
                        bi._setClass(com.pullenti.morph.MorphClass.PERSONALPRONOUN);
                        if ((m_Pronouns.indexOf(key) < 0) && wf.normalFull != null && m_Pronouns.indexOf(wf.normalFull) >= 0) 
                            key = (bi.normalCase = wf.normalFull);
                    }
                    res.getMorph().addItem(bi);
                }
                return res;
            }
        }
        com.pullenti.morph.MorphBaseInfo undefBestVar = null;
        if ((t instanceof com.pullenti.ner.TextToken) && t.getMorphClassInDictionary().isUndefined()) 
            undefBestVar = t.getMorph().findItem(com.pullenti.morph.MorphCase.UNDEFINED, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
        boolean checkForProper = false;
        com.pullenti.ner.core.NounPhraseToken _npt = null;
        if (undefBestVar != null && undefBestVar._getClass().isVerb()) {
        }
        else 
            _npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.CANNOTHASCOMMAAND.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN.value())), 0);
        if (_npt != null && _npt.noun.isValue("БЫТЬ", null)) 
            _npt = null;
        else if (_npt != null && _npt.getBeginToken() == _npt.getEndToken()) {
            if (com.pullenti.n2j.Utils.stringsEq(term, "СТАЛИ") || com.pullenti.n2j.Utils.stringsEq(term, "ГОТОВ") || com.pullenti.n2j.Utils.stringsEq(term, "ЗНАТЬ")) 
                _npt = null;
            if (t.getMorph().getLanguage().isUa()) {
                if (com.pullenti.n2j.Utils.stringsEq(term, "СТАВ")) 
                    _npt = null;
            }
        }
        if (_npt != null) {
            if (t.isValue("КОЛИЧЕСТВО", "КІЛЬКІСТЬ")) {
                SynToken res1 = tryParse(t.getNext(), prevs);
                if (res1 != null && res1.typ == Types.NUMBER) {
                    res1.setBeginToken(t);
                    return res1;
                }
            }
            if (_npt.getBeginToken() == _npt.getEndToken() && _npt.getEndToken().getMorph()._getClass().isPreposition()) {
                res = tryParse(t.getNext(), prevs);
                if (res != null) {
                    res.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    res.setBeginToken(t);
                    return res;
                }
            }{
                    res = _new2537(t, _npt.getEndToken(), Types.OBJ, _npt, _npt.getMorph());
                    if (undefBestVar != null && undefBestVar._getClass().isNoun()) {
                        res.setMorph(new com.pullenti.ner.MorphCollection(null));
                        res.getMorph().addItem(undefBestVar);
                    }
                    SynToken res00 = null;
                    if (_npt.internalNoun != null) {
                        res00 = tryParse(_npt.internalNoun.getBeginToken(), null);
                        if (res00 != null && res00.getEndToken() == _npt.internalNoun.getEndToken()) 
                            res.tag = res00;
                        else 
                            res00 = null;
                    }
                    if (t.getMorph()._getClass().isPreposition()) 
                        res.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    for(com.pullenti.ner.Token tt = t; tt != null && (tt.endChar < res.npt.noun.beginChar); tt = tt.getNext()) {
                        if (tt instanceof com.pullenti.ner.TextToken) {
                            if (tt.getMorph()._getClass().isPronoun() || tt.getMorph()._getClass().isPersonalPronoun()) {
                                if (res00 != null && res00.beginChar <= tt.beginChar && tt.endChar <= res00.endChar) {
                                }
                                else if (tt.isValue("ВЕСЬ", null) && _npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                                    res.addVal("ВЕСЬ", ValTypes.ACTANTPROP);
                                else if (res.anaforRef0 == null) 
                                    res.anaforRef0 = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class);
                            }
                        }
                    }
                    if ((res.npt.adjectives.size() == 0 && !res.npt.noun.chars.isAllLower() && res.npt.noun.getBeginToken() == res.npt.noun.getEndToken()) && (res.npt.noun.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                        com.pullenti.morph.MorphClass mc = res.npt.noun.getBeginToken().getMorphClassInDictionary();
                        if (mc.isProper()) 
                            res.typ = Types.PROPERNAME;
                        else if (mc.isUndefined()) {
                            com.pullenti.ner.core.StatisticCollection.WordInfo inf = t.kit.statistics.getWordInfo(res.npt.noun.getBeginToken());
                            if (inf != null && inf.lowerCount == 0) {
                                if (inf.totalCount > 1 && mc.isUndefined()) 
                                    res.typ = Types.PROPERNAME;
                                else if (t.getPrevious() != null && t.getPrevious().chars.isAllLower() && t.getPrevious().getLengthChar() > 2) 
                                    res.typ = Types.PROPERNAME;
                            }
                        }
                        if (mc.isUndefined()) 
                            res.addVal(res.npt.noun.getSourceText().toUpperCase(), ValTypes.NAME);
                        res.addVal(res.npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false), ValTypes.NAME);
                        if (res.typ == Types.PROPERNAME) 
                            res.npt = null;
                        else 
                            res.typ2 = Types.PROPERNAME;
                        if (t.getNext() != null && !t.isWhitespaceAfter()) {
                            if (t.getNext() instanceof com.pullenti.ner.NumberToken) 
                                res = null;
                            else if (t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && !t.getNext().isWhitespaceAfter()) 
                                res = null;
                            else if (t.getNext().isCharOf(".\\/") && !t.getNext().isWhitespaceAfter()) 
                                res = null;
                            if (res == null) 
                                checkForProper = true;
                        }
                    }
                    if (res != null) {
                        if (res.npt != null && (res.npt.noun.getBeginToken() instanceof com.pullenti.ner.ReferentToken)) {
                            for(com.pullenti.ner.MetaToken a : res.npt.adjectives) {
                                String aa = a.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                if (aa != null) 
                                    res.addVal(aa, ValTypes.PROP);
                            }
                            res.ref = res.npt.noun.getBeginToken().getReferent();
                            res.npt = null;
                        }
                        if (res.npt != null && res.npt.noun.isValue("НАЗВАНИЕ", "НАЗВА")) {
                            java.util.ArrayList<SynToken> tmp = new java.util.ArrayList< >();
                            tmp.add(res);
                            SynToken res1 = tryParse(res.getEndToken().getNext(), tmp);
                            if (res1 != null && res1.typ == Types.PROPERNAME) {
                                res1.setBeginToken(res.getBeginToken());
                                res1.preposition = null;
                                return res1;
                            }
                        }
                        boolean isVerb = false;
                        if (res.getBeginToken() == res.getEndToken() && res.getBeginToken().getMorphClassInDictionary().isVerb()) {
                            if (prevs != null && !res.getBeginToken().getMorph().containsAttr("2 л.", new com.pullenti.morph.MorphClass(null))) {
                                for(int i = prevs.size() - 1; i >= 0; i--) {
                                    if (prevs.get(i).typ == Types.SEQEND) 
                                        break;
                                    else if (prevs.get(i).isPredicate()) 
                                        break;
                                    else if (prevs.get(i).isActant()) {
                                        Actant aa = Actant.tryCreate(prevs.get(i), _new2517(t, t, Types.ACT, t.getMorph()));
                                        if (aa != null && ((aa.agentCoef > 0 || aa.pacientCoef > 0 || aa.operandCoef > 1))) {
                                            isVerb = true;
                                            break;
                                        }
                                    }
                                    else if (prevs.get(i).typ == Types.ADVERB && i == (prevs.size() - 1)) {
                                        isVerb = true;
                                        break;
                                    }
                                    else 
                                        break;
                                }
                            }
                            if (!isVerb && !res.getBeginToken().getMorph().containsAttr("2 л.", new com.pullenti.morph.MorphClass(null))) {
                                SynToken res1 = tryParse(res.getEndToken().getNext(), null);
                                if (res1 != null && res1.isActant()) {
                                    Actant aa = Actant.tryCreate(res1, _new2517(t, t, Types.ACT, t.getMorph()));
                                    if (aa != null && ((aa.agentCoef > 0 || aa.pacientCoef > 0 || aa.operandCoef > 1))) 
                                        isVerb = true;
                                }
                            }
                        }
                        if (!isVerb) 
                            return res;
                    }
                }
        }
        if ((t instanceof com.pullenti.ner.TextToken) && t.getMorph()._getClass().isVerb()) {
            if (!t.chars.isAllLower() && com.pullenti.morph.MorphClass.ooNoteq(t.getMorphClassInDictionary(), com.pullenti.morph.MorphClass.VERB)) {
            }
            else if ((t.getMorph()._getClass().isAdjective() || t.getMorph()._getClass().isPreposition() || ((t.getMorph()._getClass().isAdverb() && com.pullenti.n2j.Utils.stringsNe(term, "СТАЛО")))) || t.getMorph()._getClass().isConjunction()) {
            }
            else {
                res = _new2480(t, t, Types.ACT);
                res.setMorph(new com.pullenti.ner.MorphCollection(t.getMorph()));
                res.getMorph().removeItems(com.pullenti.morph.MorphClass.VERB, false);
                res.setBase(t.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, true, com.pullenti.morph.MorphGender.UNDEFINED, false));
                return res;
            }
        }
        if ((cla.isVerb() && !t.getMorph()._getClass().isPreposition() && !t.getMorph()._getClass().isAdverb()) && !t.getMorph()._getClass().isConjunction()) {
            res = _new2517(t, t, Types.ACTPRICH, t.getMorph());
            res.setBase(t.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, true, com.pullenti.morph.MorphGender.UNDEFINED, false));
            if (com.pullenti.n2j.Utils.stringsEq(res.getBase(), "БЫТЬ") || com.pullenti.n2j.Utils.stringsEq(res.getBase(), "БУТИ")) 
                res.typ = Types.ACT;
            return res;
        }
        if (((cla.isAdverb() || t.getMorph().containsAttr("прдктв.", new com.pullenti.morph.MorphClass(null)))) && ((t instanceof com.pullenti.ner.TextToken))) {
            SynToken res1 = _new2542(t, t, Types.ADVERB, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, t.getMorph());
            if (cla.isVerb()) {
                res1.typ = Types.ACTORADVERB;
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
                if (tt.getMorph().containsAttr("страд.з.", new com.pullenti.morph.MorphClass(null))) {
                    res1.typ = Types.ACT;
                    res1.setBase(tt.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, true, com.pullenti.morph.MorphGender.UNDEFINED, false));
                }
                else if (tt.getMorph().containsAttr("2 л.", new com.pullenti.morph.MorphClass(null))) 
                    res1.typ = Types.ADVERB;
            }
            if (res1.typ == Types.ADVERB && (t.getWhitespacesAfterCount() < 2) && t.getNext() != null) {
                SynToken res2 = tryParse(t.getNext(), null);
                if (res2 != null && res2.typ == Types.ADVERB) {
                    res1.setEndToken(res2.getEndToken());
                    res1.setBase(res1.getBase() + " " + res2.getBase());
                }
            }
            if ((res1.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(res1.getEndToken().getNext(), com.pullenti.ner.TextToken.class))).term, "ТАК")) 
                res1.setEndToken(res1.getEndToken().getNext());
            return res1;
        }
        if (com.pullenti.morph.MorphClass.ooEq(cla, com.pullenti.morph.MorphClass.ADJECTIVE)) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
            String norm = null;
            for(com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                if (wf.containsAttr("к.ф.", new com.pullenti.morph.MorphClass()) && wf._getClass().isAdjective()) {
                    norm = (String)com.pullenti.n2j.Utils.notnull((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalFull, (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase);
                    break;
                }
            }
            if (norm != null) {
                if (com.pullenti.morph.LanguageHelper.endsWithEx(norm, "ЫЙ", "ИЙ", null, null)) 
                    norm = norm.substring(0, 0+(norm.length() - 2)) + "О";
                res = _new2517(t, t, Types.ACT, t.getMorph());
                res.setBase((t.getMorph().getLanguage().isUa() ? "БУТИ" : "БЫТЬ"));
                res.addVal(norm, ValTypes.PROP);
                return res;
            }
        }
        if (t.isValue("НЕ", null)) {
            if ((((res = tryParse(t.getNext(), prevs)))) != null) {
                res.setBeginToken(t);
                res.not = true;
                return res;
            }
        }
        if (t.isValue("НЕКИЙ", "ЯКИЙСЬ") || t.isValue("НЕКТО", "ХТОСЬ")) 
            return _new2544(t, t, Types.OBJ, t.getMorph(), (t.getMorph().getLanguage().isUa() ? "ХТОСЬ" : "НЕКТО"));
        if (t.isValue("НИКОГО", "НІКОГО") || t.isValue("НИКТО", "НІХТО")) 
            return _new2544(t, t, Types.OBJ, t.getMorph(), (t.getMorph().getLanguage().isUa() ? "НІХТО" : "НИКТО"));
        if (t.getMorph()._getClass().isPersonalPronoun() || t.getMorph()._getClass().isPronoun()) 
            return _new2533(t, t, Types.EMPTY, (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class), t.getMorph());
        if (t.getMorph()._getClass().isPreposition()) {
            res = tryParse(t.getNext(), prevs);
            if (res != null && res.typ != Types.PROPERNAME) {
                if (res.getBeginToken() != t) {
                    res.setBeginToken(t);
                    res.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                }
            }
            return res;
        }
        com.pullenti.morph.MorphClass mm = t.getMorphClassInDictionary();
        if ((mm.isUndefined() || mm.isProper() || checkForProper) || mm.isNoun()) {
            if (((t.chars.isCapitalUpper() || t.chars.isLastLower())) || ((t.chars.isAllUpper() && mm.isUndefined()))) {
                res = _new2517(t, t, Types.PROPERNAME, t.getMorph());
                for(t = t.getNext(); t != null; t = t.getNext()) {
                    if (t.getWhitespacesBeforeCount() > 2) 
                        break;
                    if (t instanceof com.pullenti.ner.NumberToken) {
                        if (t.isWhitespaceBefore()) 
                            break;
                        res.setEndToken(t);
                        continue;
                    }
                    if (!((t instanceof com.pullenti.ner.TextToken))) 
                        break;
                    if (t.chars.isLetter()) {
                        if (com.pullenti.morph.CharsInfo.ooNoteq(t.chars, res.getBeginToken().chars)) {
                            if (t.getPrevious() != null && t.getPrevious().isHiphen()) {
                            }
                            else if (t.isWhitespaceBefore()) 
                                break;
                        }
                        res.setEndToken(t);
                        continue;
                    }
                    if (t.isWhitespaceBefore()) 
                        break;
                    if (t.isCharOf(",;")) 
                        break;
                    if (com.pullenti.ner.core.BracketHelper.isBracket(t, false)) 
                        break;
                    if (t.isChar('.')) {
                        if (t.isWhitespaceAfter()) 
                            break;
                    }
                    else if (!t.isHiphen()) 
                        res.setEndToken(t);
                }
                if (res.getBeginToken().getLengthChar() >= 3 || res.getEndToken() != res.getBeginToken() || mm.isUndefined()) {
                    if (!res.getMorph().getCase().isUndefined() && !res.getMorph().getCase().isNominative()) {
                        res.addVal(com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE), ValTypes.NAME);
                        res.addVal(com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), ValTypes.NAME);
                    }
                    else {
                        res.addVal(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.NO), ValTypes.NAME);
                        res.addVal(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE), ValTypes.NAME);
                    }
                    return res;
                }
            }
        }
        if (mm.isAdjective()) {
            res = _new2517(t, t, Types.OBJ, t.getMorph());
            res.setBase(t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, com.pullenti.morph.MorphGender.UNDEFINED, false));
            res.getMorph().removeItems(com.pullenti.morph.MorphClass.ADJECTIVE, false);
            return res;
        }
        if (!t.chars.isLetter()) 
            return _new2480(t, t, Types.DELIMETER);
        return _new2480(t, t, Types.UNDEFINED);
    }

    private static com.pullenti.ner.core.TerminCollection m_Onto = new com.pullenti.ner.core.TerminCollection();

    private static com.pullenti.ner.core.TerminCollection m_OntoNum = new com.pullenti.ner.core.TerminCollection();

    private static com.pullenti.ner.core.TerminCollection m_OntoPronoun = null;

    private static java.util.ArrayList<String> m_Pronouns = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"Я", "ТЫ", "МЫ", "ВЫ", "ОН", "ОНА", "ОНО", "ОНИ"}));

    private static java.util.ArrayList<String> m_PronounsUA = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"Я", "ТИ", "МИ", "ВИ", "ВІН", "ВОНА", "ВОНО", "ВОНИ"}));

    private static java.util.ArrayList<String> m_PronounsAdj = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"МОЙ", "ТВОЙ", "НАШ", "ВАШ", "ЕГО", "ЕЕ", "ЕГО", "ИХ"}));

    private static java.util.ArrayList<String> m_PronounsAdjUA = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"МІЙ", "ТВІЙ", "НАШ", "ВАШ", "ЙОГО", "ЇЇ", "ЙОГО", "ЇХ"}));

    public static void initialize() {
        if (m_OntoPronoun != null) 
            return;
        m_OntoPronoun = new com.pullenti.ner.core.TerminCollection();
        for(int i = 0; i < m_Pronouns.size(); i++) {
            m_OntoPronoun.add(new com.pullenti.ner.core.Termin(m_Pronouns.get(i), new com.pullenti.morph.MorphLang(null), false));
            m_OntoPronoun.add(com.pullenti.ner.core.Termin._new1054(m_PronounsAdj.get(i), m_Pronouns.get(i)));
        }
        for(int i = 0; i < m_PronounsUA.size(); i++) {
            m_OntoPronoun.add(com.pullenti.ner.core.Termin._new858(m_PronounsUA.get(i), com.pullenti.morph.MorphLang.UA));
            m_OntoPronoun.add(com.pullenti.ner.core.Termin._new2553(m_PronounsAdjUA.get(i), m_PronounsUA.get(i), com.pullenti.morph.MorphLang.UA));
        }
        for(String s : new String[] {"КТО", "ЧТО", "ЧЕЙ", "КОТОРЫЙ", "КАКОЙ", "КАКОВОЙ", "КОЙ"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new120(s, Types.WHAT, s));
        }
        for(String s : new String[] {"ХТО", "ЩО", "ЧИЙ", "КОТРИЙ", "ЯКИЙ", "ЯКИЙ", "КОЙ"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new868(s, com.pullenti.morph.MorphLang.UA, Types.WHAT, s));
        }
        for(String s : new String[] {"КОГДА", "ОТКУДА", "КУДА", "ГДЕ", "КАК"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new118(s, Types.WHAT));
        }
        for(String s : new String[] {"КОЛИ", "ЗВІДКИ", "КУДИ", "ДЕ", "ЯК"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new459(s, com.pullenti.morph.MorphLang.UA, Types.WHAT));
        }
        for(String s : new String[] {"ТАКИМ ОБРАЗОМ", "СЛЕДОВАТЕЛЬНО", "ОЧЕВИДНО", "ОТСЮДА СЛЕДУЕТ", "ВСЛЕДСТВИИ", "ВСЛЕДСТВИЕ", "ПОТОМУ", "ПОЭТОМУ", "ПОСЕМУ", "ИТАК", "ПОСКОЛЬКУ", "ПО СУТИ", "ПО-СУТИ", "ФАКТИЧЕСКИ", "ТЕОРЕТИЧЕСКИ", "НАКОНЕЦ", "ТАК КАК", "ИТОГО", "ВМЕСТЕ С ТЕМ", "НАРЯДУ С", "В ОБЩЕМ ВИДЕ", "В СООТВЕТСТВИИ", "В ОТЛИЧИЕ", "В ЧАСТНОСТИ", "В ЦЕЛОМ", "В ОСНОВНОМ", "В СВЯЗИ", "УЖЕ", "ЕЩЕ", "ЧАЩЕ ВСЕГО", "ВПРОЧЕМ", "НА САМОМ ДЕЛЕ", "СОВСЕМ", "ВОВСЕ", "ПОСЛЕ ЧЕГО", "ПОСЛЕ ЭТОГО", "ЗАТЕМ", "ПОТОМ", "ДРУГ С ДРУГОМ", "ДРУГ БЕЗ ДРУГА"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new118(s, Types.ADVERB));
        }
        for(String s : new String[] {"ТАКИМ ЧИНОМ", "ОТЖЕ", "ОЧЕВИДНО", "ЗВІДСИ ВИПЛИВАЄ", "ВНАСЛІДОК", "ВНАСЛІДОК", "БО", "ТОМУ", "ТОМУ", "ОТЖЕ", "ОСКІЛЬКИ", "ПО СУТІ", "ПО-СУТІ", "ФАКТИЧНО", "ТЕОРЕТИЧНО", "НАРЕШТІ", "ОСКІЛЬКИ", "РАЗОМ", "РАЗОМ З ТИМ", "ПОРЯД З", "В ЗАГАЛЬНОМУ ВИГЛЯДІ", "ВІДПОВІДНО", "ВІДМІННІСТЬ", "ЗОКРЕМА", "У ЦІЛОМУ", "В ОСНОВНОМУ", "У ЗВ'ЯЗКУ", "ВЖЕ", "ЩЕ", "НАЙЧАСТІШЕ", "ВТІМ", "НАСПРАВДІ", "ЗОВСІМ", "ЗОВСІМ", "ПІСЛЯ ЧОГО", "ПІСЛЯ ЦЬОГО", "ПОТІМ", "ПОТІМ", "ОДИН З ОДНИМ", "ОДИН БЕЗ ОДНОГО"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new459(s, com.pullenti.morph.MorphLang.UA, Types.ADVERB));
        }
        for(String s : new String[] {"И", "ИЛИ", "ТАК И", "ТАКЖЕ", "А ТАКЖЕ", "НО И", "В ТОМ ЧИСЛЕ", "ВКЛЮЧАЯ", "ИСКЛЮЧАЯ", "КАК И"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new118(s, Types.CONJ));
        }
        for(String s : new String[] {"І", "АБО", "ТА", "ТАКОЖ", "А ТАКОЖ", "АЛЕ", "В ТОМУ ЧИСЛІ", "ВКЛЮЧАЮЧИ", "КРІМ", "ЯК І"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new459(s, com.pullenti.morph.MorphLang.UA, Types.CONJ));
        }
        for(String s : new String[] {"БОЛЬШИНСТВО", "МНОГИЕ", "НЕКОТОРЫЕ", "НЕКИЙ", "НЕСКОЛЬКО", "ЛЮБОЙ", "КАЖДЫЙ", "НИКТО", "НИКОГО", "НИЧТО", "ВСЕ", "ПОЛОВИНА", "ТРЕТЬ", "ЧЕТВЕРТЬ", "НЕМНОГИЕ", "МАЛО КТО", "КРОМЕ", "ИСКЛЮЧАЯ"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new118(s, Types.NUMBER));
        }
        for(String s : new String[] {"БІЛЬШІСТЬ", "БАГАТО", "ДЕЯКІ", "ЯКИЙСЬ", "КІЛЬКА", "БУДЬ-ЯКИЙ", "КОЖЕН", "НІХТО", "НІКОГО", "НІЩО", "ВСІ", "ПОЛОВИНА", "ТРЕТИНА", "ЧВЕРТЬ", "НЕБАГАТО", "МАЛО ХТО", "КРІМ", "ВИКЛЮЧАЮЧИ"}) {
            m_Onto.add(com.pullenti.ner.core.Termin._new459(s, com.pullenti.morph.MorphLang.UA, Types.NUMBER));
        }
    }

    private static java.util.ArrayList<String> m_SpeechActs = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ГОВОРИТЬ", "СООБЩИТЬ", "СООБЩАТЬ", "СКАЗАТЬ", "ОТВЕТИТЬ", "ОТВЕЧАТЬ", "ПИСАТЬ", "ПОВЕДАТЬ", "СПРОСИТЬ", "СПРАШИВАТЬ", "РАССКАЗАТЬ", "РАССКАЗЫВАТЬ", "НАПЕЧАТАТЬ", "ПЕЧАТАТЬ", "ОТМЕЧАТЬ", "ОТМЕТИТЬ", "ПОДЧЕРКНУТЬ", "ПОДЧЕРКИВАТЬ", "ОБЪЯСНЯТЬ", "ОБЪЯСНИТЬ", "ПОЯСНЯТЬ", "ПОЯСНИТЬ", "УТОЧНИТЬ", "УТОЧНЯТЬ"}));

    private static java.util.ArrayList<String> m_SpeechActsUA = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ГОВОРИТИ", "ПОВІДОМИТИ", "ПОВІДОМЛЯТИ", "СКАЗАТИ", "ВІДПОВІСТИ", "ВІДПОВІДАТИ", "ПИСАТИ", "ПОВІДАТИ", "ЗАПИТАТИ", "ЗАПИТУВАТИ", "РОЗПОВІСТИ", "РОЗПОВІДАТИ", "НАДРУКУВАТИ", "ДРУК", "ВІДЗНАЧАТИ", "НАГОЛОСИТИ", "НАГОЛОСИТИ", "ПІДКРЕСЛЮВАТИ", "ПОЯСНЮВАТИ", "ПОЯСНИТИ", "ПОЯСНЮВАТИ", "ПОЯСНИТИ", "УТОЧНИТИ", "УТОЧНЮВАТИ"}));

    public SynToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ);
        if (typ2 != Types.UNDEFINED && typ2 != typ) 
            res.append("/").append(typ2.toString());
        if (level > 0) 
            res.append(" LEV:").append(level);
        if (not) 
            res.append(" NOT");
        for(java.util.Map.Entry<String, ValTypes> kp : vals.entrySet()) {
            res.append(" ").append(kp.getValue().toString()).append(": ").append(kp.getKey());
        }
        if (npt != null) 
            res.append(" NPT: ").append(npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false));
        if (ref != null) 
            res.append(" REF: ").append(ref.toString());
        if (getAnaforRef() != null) 
            res.append(" ANAFOR: [").append(getAnaforRef().toString()).append("]");
        if (anaforRef0 != null) 
            res.append(" ANAFOR0: ").append(anaforRef0.toString());
        if (children.size() > 0) 
            res.append(" CH: ").append(children.size());
        if (real != null) 
            res.append(" REAL: ").append(real.toString());
        res.append(" SRC:").append(getSourceText());
        res.append(" (").append(getMorph().toString()).append(")");
        return res.toString();
    }

    public Types typ = Types.UNDEFINED;

    public Types typ2 = Types.UNDEFINED;

    public com.pullenti.ner.semantic.ActantRole rol = com.pullenti.ner.semantic.ActantRole.UNDEFINED;

    public com.pullenti.ner.MorphCollection secMorph;

    public boolean isActant() {
        if (typ == Types.OBJ || typ == Types.PROPERNAME || typ == Types.PRONOUNOBJ) 
            return true;
        if (typ == Types.WHAT) {
            if (anaforRef0 != null) {
                if (com.pullenti.n2j.Utils.stringsEq(getBase(), "ЧТО") || com.pullenti.n2j.Utils.stringsEq(getBase(), "ЩО")) 
                    return false;
                return true;
            }
        }
        if (typ == Types.NUMBER) {
            if (children.size() > 0) 
                return true;
            if (getMorph()._getClass().isPronoun()) 
                return true;
            return true;
        }
        if (typ == Types.ACTANT) 
            return true;
        if (typ == Types.EMPTY && anaforRef0 != null) {
            if (anaforRef0.isValue("САМ", null)) 
                return true;
        }
        return false;
    }


    public boolean isConjOrComma() {
        return typ == Types.CONJ || typ == Types.COMMA;
    }


    public boolean isPredicate() {
        return typ == Types.ACT || typ == Types.ACTPRICH;
    }


    /**
     * Это предикат с ключевым словом "БЫТЬ" ("ЯВЛЯТЬСЯ")
     */
    public boolean isPredicateBe() {
        if (!isPredicate()) 
            return false;
        String bas = getBase();
        if (bas == null) 
            return false;
        return com.pullenti.n2j.Utils.stringsEq(bas, "БЫТЬ") || com.pullenti.n2j.Utils.stringsEq(bas, "ЯВЛЯТЬСЯ") || com.pullenti.n2j.Utils.stringsEq(bas, "БУТИ");
    }


    /**
     * Возвратные предикат
     */
    public boolean isPredicateSelf() {
        if (!isPredicate()) 
            return false;
        String bas = getBase();
        if (bas == null) 
            return false;
        if (bas.endsWith("СЯ") || bas.endsWith("СЬ")) 
            return true;
        return false;
    }


    public boolean isPredicateInfinitive() {
        if (typ != Types.ACT) 
            return false;
        if (getMorph().containsAttr("инф.", new com.pullenti.morph.MorphClass(null))) 
            return true;
        return false;
    }


    public com.pullenti.ner.core.NounPhraseToken npt = null;

    public com.pullenti.ner.Referent ref;

    public boolean not;

    public String getBase() {
        String v = getVal(ValTypes.BASE);
        if (v != null) 
            return v;
        if (npt != null) 
            return npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, getMorph().getGender(), false);
        return null;
    }

    public String setBase(String value) {
        delVals(ValTypes.BASE);
        if (value != null) 
            addVal(value, ValTypes.BASE);
        return value;
    }


    public java.util.HashMap<String, ValTypes> vals = new java.util.HashMap< >();

    public void addVal(String v, ValTypes ty) {
        if (v == null) 
            return;
        if (ty == ValTypes.BASE) 
            m_ExplainInfo = null;
        if (vals.containsKey(v)) 
            vals.put(v, ty);
        else 
            vals.put(v, ty);
    }

    public void addVals(java.util.HashMap<String, ValTypes> _vals) {
        for(java.util.Map.Entry<String, ValTypes> kp : _vals.entrySet()) {
            addVal(kp.getKey(), kp.getValue());
        }
    }

    public void addVals(SynToken sy) {
        for(java.util.Map.Entry<String, ValTypes> kp : sy.vals.entrySet()) {
            addVal(kp.getKey(), kp.getValue());
        }
        npt = sy.npt;
    }

    public String getVal(ValTypes ty) {
        for(java.util.Map.Entry<String, ValTypes> kp : vals.entrySet()) {
            if (kp.getValue() == ty) 
                return kp.getKey();
        }
        return null;
    }

    public void delVals(ValTypes ty) {
        if (ty == ValTypes.BASE) 
            m_ExplainInfo = null;
        while(vals.containsValue(ty)) {
            for(java.util.Map.Entry<String, ValTypes> kp : vals.entrySet()) {
                if (kp.getValue() == ty) {
                    vals.remove(kp.getKey());
                    break;
                }
            }
        }
    }

    public void clearVals() {
        vals.clear();
        npt = null;
    }

    public void mergeValsBySpace(java.util.HashMap<String, ValTypes> _vals) {
        java.util.HashMap<String, ValTypes> res = new java.util.HashMap< >();
        for(java.util.Map.Entry<String, ValTypes> kp : vals.entrySet()) {
            for(java.util.Map.Entry<String, ValTypes> kp2 : _vals.entrySet()) {
                if (kp.getValue() == kp2.getValue()) {
                    String vv = kp.getKey() + " " + kp2.getKey();
                    if (!res.containsKey(vv)) 
                        res.put(vv, kp.getValue());
                }
            }
        }
        vals = res;
    }

    public void manageNpt() {
        if (typ == Types.OBJ && getBase() != null) {
            int ii = getBase().indexOf('-');
            if (ii > 0 && getEndToken().getPrevious() != null && getEndToken().getPrevious().isHiphen()) {
                SynToken st = _new2480(getEndToken(), getEndToken(), Types.OBJ);
                st.setBase(getBase().substring(ii + 1));
                setBase(getBase().substring(0, 0+(ii)));
                addChild(st);
                if (npt != null && npt.noun.getEndToken() == getEndToken()) 
                    npt.removeLastNounWord();
            }
        }
        if (typ != Types.PROPERNAME && getVal(ValTypes.NAME) != null) {
        }
        if (npt == null) 
            return;
        if (typ == Types.TIME) {
            if (getVal(ValTypes.BASE) == null) 
                addVal(npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false), ValTypes.BASE);
        }
        else {
            String str = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, getMorph().getGender(), false);
            if (typ2 != Types.PROPERNAME) 
                setBase(str);
            else 
                addVal(str, ValTypes.NAME);
            for(com.pullenti.ner.MetaToken a : npt.adjectives) {
                String v = a.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, getMorph().getGender(), false);
                if (v == null) 
                    continue;
                if (a.getBeginToken() == a.getEndToken()) {
                    String nv = a.getBeginToken().getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, com.pullenti.morph.MorphGender.MASCULINE, false);
                    String noun = com.pullenti.morph.Explanatory.getWordClassVar(nv, com.pullenti.morph.MorphClass.NOUN, a.getMorph().getLanguage());
                    if (noun != null) {
                        SynToken act = _new2472(a.getBeginToken(), a.getEndToken(), Types.ACTANT, com.pullenti.ner.semantic.ActantRole.OBJECT, a.getMorph());
                        act.setBase(v);
                        addChild(act);
                        SynToken obj = _new2480(a.getBeginToken(), a.getEndToken(), Types.OBJ);
                        obj.setBase(noun);
                        act.addChild(obj);
                        continue;
                    }
                }
                addVal(v, ValTypes.PROP);
            }
        }
    }

    public java.util.ArrayList<SynToken> children = new java.util.ArrayList< >();

    public void addChild(SynToken sy) {
        if (!children.contains(sy)) 
            children.add(sy);
    }

    public void addChildren(java.util.ArrayList<SynToken> ch) {
        for(SynToken c : ch) {
            if (!children.contains(c)) 
                children.add(c);
        }
    }

    public SynToken getFirstChild() {
        if (children.size() == 0) 
            return null;
        SynToken res = children.get(0);
        int cou = 0;
        SynToken cur = res;
        for(; cou < 30; cou++) {
            SynToken la = null;
            for(SynToken k : cur.children) {
                la = k;
                break;
            }
            if (la == null) 
                break;
            if (la == res) 
                return null;
            cur = la;
        }
        if (cou >= 30) 
            return null;
        return res;
    }


    public SynToken getLastChild() {
        if (children.size() == 0) 
            return null;
        SynToken res = children.get(children.size() - 1);
        int cou = 0;
        SynToken cur = res;
        for(; cou < 30; cou++) {
            if (cur.children.size() == 0) 
                break;
            SynToken la = cur.children.get(cur.children.size() - 1);
            if (la == res) 
                return null;
            cur = la;
        }
        if (cou >= 30) 
            return null;
        return res;
    }


    public SynToken getThisOrLastChildren(int lev) {
        if (lev > 20) 
            return null;
        if (getLastChild() == null) 
            return this;
        return getLastChild().getThisOrLastChildren(lev + 1);
    }

    public com.pullenti.ner.TextToken anaforRef0;

    public SynToken getAnaforRef() {
        return m_AnaforRef;
    }

    public SynToken setAnaforRef(SynToken value) {
        m_AnaforRef = value;
        if (value != null) {
            if (value.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) 
                getMorph().removeItems(value.getMorph().getNumber());
        }
        return value;
    }


    private SynToken m_AnaforRef;

    public int level = 0;

    public boolean canBeProperName() {
        if (ref != null && typ == Types.OBJ) {
            if (com.pullenti.n2j.Utils.stringsEq(ref.getTypeName(), "GEO")) 
                return false;
            return true;
        }
        if (typ == Types.PROPERNAME || typ2 == Types.PROPERNAME) 
            return true;
        if (typ == Types.OBJ && getVal(ValTypes.NAME) != null) 
            return true;
        return false;
    }


    public boolean containsChild(SynToken ch, int lev) {
        if (lev > 10) 
            return false;
        if (ch == getAnaforRef() || ch == this) 
            return true;
        for(SynToken cc : children) {
            if (cc.containsChild(ch, lev + 1)) 
                return true;
        }
        return false;
    }

    public String preposition;

    public boolean getActCanBeSpeech() {
        if (getBase() != null) {
            if (getMorph().getLanguage().isUa()) {
                if (m_SpeechActsUA.contains(getBase())) 
                    return true;
                if (getBase().length() > 5 && com.pullenti.morph.LanguageHelper.endsWith(getBase(), "СЯ")) {
                    if (m_SpeechActsUA.contains(getBase().substring(0, 0+(getBase().length() - 2)))) {
                        String str = getBase().substring(0, 0+(getBase().length() - 2));
                        delVals(ValTypes.BASE);
                        addVal(str, ValTypes.BASE);
                        return true;
                    }
                }
            }
            else {
                if (m_SpeechActs.contains(getBase())) 
                    return true;
                if (getBase().length() > 5 && com.pullenti.morph.LanguageHelper.endsWith(getBase(), "СЯ")) {
                    if (m_SpeechActs.contains(getBase().substring(0, 0+(getBase().length() - 2)))) {
                        String str = getBase().substring(0, 0+(getBase().length() - 2));
                        delVals(ValTypes.BASE);
                        addVal(str, ValTypes.BASE);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean isRootValue(String val, String valUA) {
        if (npt != null) {
            if (npt.noun.isValue(val, valUA)) 
                return true;
        }
        ValTypes ty;
        com.pullenti.n2j.Outargwrapper<ValTypes> inoutarg2569 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2570 = com.pullenti.n2j.Utils.tryGetValue(vals, val, inoutarg2569);
        ty = inoutarg2569.value;
        if (inoutres2570) 
            return ty == ValTypes.BASE || ty == ValTypes.NAME;
        if (valUA != null) {
            com.pullenti.n2j.Outargwrapper<ValTypes> inoutarg2567 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2568 = com.pullenti.n2j.Utils.tryGetValue(vals, valUA, inoutarg2567);
            ty = inoutarg2567.value;
            if (inoutres2568) 
                return ty == ValTypes.BASE || ty == ValTypes.NAME;
        }
        return false;
    }

    public com.pullenti.ner.Referent real;

    public boolean isContainer() {
        if (typ == Types.OBJ || typ == Types.PROPERNAME || typ == Types.TIME) 
            return (((children.size() > 0 && npt == null && getBase() == null) && ref == null && real == null) && getVal(ValTypes.NAME) == null && getAnaforRef() == null) && getVal(ValTypes.NUMBER) == null;
        if (typ == Types.ACT) 
            return children.size() > 0 && getBase() == null;
        return false;
    }


    public boolean embed(SynToken sy, boolean toLastChild, int lev) {
        if (lev > 20) 
            return false;
        if (toLastChild && children.size() > 0) 
            return getLastChild().embed(sy, toLastChild, lev + 1);
        if (sy.isContainer()) {
            if (isContainer()) {
                boolean ret = false;
                for(SynToken ch : children) {
                    if (ch.embed(sy, toLastChild, 0)) 
                        ret = true;
                }
                return ret;
            }
            addChildren(sy.children);
            return true;
        }
        if (typ != Types.NUMBER && sy.typ == Types.NUMBER) {
            if (sy.children.size() == 0) {
                for(java.util.Map.Entry<String, ValTypes> v : sy.vals.entrySet()) {
                    if (v.getValue() == ValTypes.NUMBER) 
                        addVal(v.getKey(), ValTypes.PROP);
                }
            }
            else {
                sy.typ = Types.ACTANT;
                sy.rol = com.pullenti.ner.semantic.ActantRole.OBJECT;
                addChild(sy);
            }
        }
        else 
            addChild(sy);
        if (((typ == Types.ACTANT || typ == Types.NUMBER)) && sy.isActant()) 
            setMorph(corrMorph(getMorph(), sy.getMorph()));
        return true;
    }

    private static com.pullenti.ner.MorphCollection corrMorph(com.pullenti.ner.MorphCollection c1, com.pullenti.ner.MorphCollection c2) {
        java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> li = null;
        for(com.pullenti.morph.MorphBaseInfo i1 : c1.getItems()) {
            for(com.pullenti.morph.MorphBaseInfo i2 : c2.getItems()) {
                if (i1.checkAccord(i2, false)) {
                    if (li == null) 
                        li = new java.util.ArrayList< >();
                    if (!li.contains(i1)) 
                        li.add(i1);
                }
            }
        }
        if (li != null) {
            if (li.size() == c1.getItemsCount()) 
                return c1;
            com.pullenti.ner.MorphCollection re = new com.pullenti.ner.MorphCollection(null);
            for(com.pullenti.morph.MorphBaseInfo l : li) {
                re.addItem(l);
            }
            return re;
        }
        if (c1.getItemsCount() == 0 && c2.getItemsCount() > 0) 
            return c2;
        return c1;
    }

    private java.util.ArrayList<com.pullenti.morph.DerivateWord> m_ExplainInfo = null;

    public java.util.ArrayList<com.pullenti.morph.DerivateWord> getExplainInfo() {
        if (m_ExplainInfo != null) 
            return m_ExplainInfo;
        if (isActant()) {
            String word = null;
            if (getBase() != null) 
                word = getBase();
            else if (npt != null) 
                word = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
            if (word != null) {
                m_ExplainInfo = com.pullenti.morph.Explanatory.findWords(word, getMorph().getLanguage());
                if (m_ExplainInfo == null && word.length() > 5 && word.startsWith("НЕ")) 
                    m_ExplainInfo = com.pullenti.morph.Explanatory.findWords(word.substring(2), getMorph().getLanguage());
            }
        }
        else if (isPredicate()) {
            String word = getBase();
            if (word != null) 
                m_ExplainInfo = com.pullenti.morph.Explanatory.findWords(word, getMorph().getLanguage());
        }
        if (m_ExplainInfo == null) 
            m_ExplainInfo = new java.util.ArrayList< >();
        return m_ExplainInfo;
    }

    public java.util.ArrayList<com.pullenti.morph.DerivateWord> setExplainInfo(java.util.ArrayList<com.pullenti.morph.DerivateWord> value) {
        m_ExplainInfo = value;
        return value;
    }


    public boolean canTransformToTyp(Types ty) {
        if (ty == Types.OBJ && typ == Types.ACT) {
            for(com.pullenti.morph.DerivateWord ei : getExplainInfo()) {
                if (ei.group != null) {
                    for(com.pullenti.morph.DerivateWord w : ei.group.words) {
                        if (w._class.isNoun()) 
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public void transformToTyp(Types ty) {
        if (ty == Types.OBJ && typ == Types.ACT) {
            for(com.pullenti.morph.DerivateWord ei : getExplainInfo()) {
                if (ei.group != null && typ != Types.OBJ) {
                    for(com.pullenti.morph.DerivateWord w : ei.group.words) {
                        if (w._class.isNoun()) {
                            delVals(ValTypes.BASE);
                            addVal(w.spelling, ValTypes.BASE);
                            typ = Types.OBJ;
                            break;
                        }
                    }
                }
            }
            if (ty == Types.OBJ) {
                com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.Morphology.getWordBaseInfo(getBase(), new com.pullenti.morph.MorphLang(null), false);
                if (getVal(ValTypes.PROP) != null) {
                    java.util.ArrayList<String> adv = new java.util.ArrayList< >();
                    for(java.util.Map.Entry<String, ValTypes> kp : vals.entrySet()) {
                        if (kp.getValue() == ValTypes.PROP) 
                            adv.add(kp.getKey());
                    }
                    delVals(ValTypes.PROP);
                    for(String a : adv) {
                        String aa = com.pullenti.morph.Morphology.convertAdverbToAdjective(a, bi);
                        addVal(aa, ValTypes.PROP);
                    }
                }
            }
        }
    }

    public boolean isAnimatedOrNamed() {
        return _getAnimatedOrNamed(false);
    }


    public boolean isAnimated() {
        return _getAnimatedOrNamed(true);
    }


    private boolean _getAnimatedOrNamed(boolean animOnly) {
        if (getEndToken().getMorph().containsAttr("одуш.", new com.pullenti.morph.MorphClass(null))) 
            return true;
        java.util.ArrayList<com.pullenti.morph.DerivateWord> ews = null;
        if (getEndToken() == getBeginToken()) 
            ews = getExplainInfo();
        else {
            String word = getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
            ews = com.pullenti.morph.Explanatory.findWords(word, getMorph().getLanguage());
        }
        if (ews != null) {
            for(com.pullenti.morph.DerivateWord ee : ews) {
                if (ee.attrs.isAnimated() || ((ee.attrs.isNamed() && !animOnly))) 
                    return true;
            }
        }
        return false;
    }

    public boolean isMeasured() {
        for(com.pullenti.morph.DerivateWord ee : getExplainInfo()) {
            if (ee.attrs.isMeasured()) 
                return true;
        }
        return false;
    }


    public boolean canBeMesure() {
        if (ref != null && com.pullenti.n2j.Utils.stringsEq(ref.getTypeName(), "MONEY")) 
            return true;
        if (typ == Types.NUMBER) {
            if (children.size() > 0) 
                return true;
            if (getBase() != null) 
                return true;
        }
        return false;
    }


    public boolean canHasProperName() {
        if (typ != Types.OBJ) 
            return false;
        String word = null;
        if (getBase() != null) 
            word = getBase();
        else if (npt != null) 
            word = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (word == null) 
            return false;
        return com.pullenti.morph.Explanatory.isNamed(word, new com.pullenti.morph.MorphLang());
    }


    public SynToken getNamedObj() {
        if (typ != Types.OBJ) 
            return null;
        SynToken res = null;
        for(SynToken sy = this; sy != null; sy = (sy.children.size() == 1 ? sy.getFirstChild() : null)) {
            if (sy.typ != Types.OBJ) 
                break;
            if (sy.getVal(ValTypes.NUMBER) != null) 
                continue;
            String bas = null;
            if (sy.npt != null) 
                bas = sy.npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
            else if (sy.ref instanceof com.pullenti.ner.semantic.ObjectReferent) 
                bas = (((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(sy.ref, com.pullenti.ner.semantic.ObjectReferent.class))).getBase();
            if (bas == null) 
                break;
            if (com.pullenti.morph.Explanatory.isNamed(bas, getMorph().getLanguage())) 
                res = sy;
            else if (com.pullenti.morph.Explanatory.isAnimated(bas, getMorph().getLanguage())) 
                res = sy;
        }
        return res;
    }

    private String _getAbbrStr() {
        if (npt == null) 
            return null;
        StringBuilder res = new StringBuilder();
        for(com.pullenti.ner.MetaToken a : npt.adjectives) {
            res.append(a.getSourceText().charAt(0));
        }
        res.append(npt.noun.getSourceText().charAt(0));
        return res.toString().toUpperCase();
    }

    public SynToken getAbbrObj(SynToken abbrObj) {
        if (typ != Types.OBJ) 
            return null;
        String abbr = abbrObj.getVal(ValTypes.NAME);
        if (abbr == null) 
            return null;
        if (vals.containsKey(abbr)) 
            return this;
        java.util.ArrayList<SynToken> li = new java.util.ArrayList< >();
        for(SynToken sy = this; sy != null; sy = (sy.children.size() == 1 ? sy.getFirstChild() : null)) {
            if (sy.typ != Types.OBJ) 
                break;
            if (sy.getVal(ValTypes.NUMBER) != null) 
                continue;
            li.add(sy);
        }
        for(int i = 0; i < li.size(); i++) {
            int j;
            int ii = i;
            for(j = 0; j < abbr.length(); ) {
                String aa = li.get(ii)._getAbbrStr();
                if (aa == null) 
                    break;
                if ((j + aa.length()) > abbr.length()) 
                    break;
                int k;
                for(k = 0; k < aa.length(); k++) {
                    if (aa.charAt(k) != abbr.charAt(j + k)) 
                        break;
                }
                if (k < aa.length()) 
                    break;
                j += aa.length();
                if (j >= abbr.length()) 
                    return li.get(i);
                ii++;
                if (ii >= li.size()) 
                    break;
            }
        }
        return null;
    }

    public boolean embedNamed(SynToken target, SynToken name) {
        SynToken own = null;
        for(SynToken sy = this; sy != null; sy = (sy.children.size() == 1 ? sy.getFirstChild() : null)) {
            if (sy == target) {
                if (own == null) 
                    return false;
                name.embed(sy, false, 0);
                own.children.clear();
                own.addChild(name);
                return true;
            }
            own = sy;
        }
        return false;
    }

    @Override
    public int compareTo(SynToken other) {
        if (endChar < other.beginChar) 
            return -1;
        if (beginChar > other.endChar) 
            return 1;
        if (beginChar < other.beginChar) 
            return -1;
        if (endChar > other.endChar) 
            return 1;
        return 0;
    }

    public void setPredicateReferent(com.pullenti.ner.semantic.PredicateReferent res) {
        com.pullenti.ner.semantic.PredicateAttr ptyp = com.pullenti.ner.semantic.PredicateAttr.UNDEFINED;
        com.pullenti.ner.semantic.PredicateAttr asp = com.pullenti.ner.semantic.PredicateAttr.UNDEFINED;
        com.pullenti.ner.semantic.PredicateAttr pos = com.pullenti.ner.semantic.PredicateAttr.VERB;
        boolean isReflex = false;
        if (getMorph()._getClass().isVerb() && getMorph()._getClass().isAdjective()) 
            pos = com.pullenti.ner.semantic.PredicateAttr.PARTICIPLE;
        if (not) 
            res.addAttr(com.pullenti.ner.semantic.PredicateAttr.NOT);
        for(int k = 0; k < 2; k++) {
            com.pullenti.ner.MorphCollection _morph = (k == 0 ? getMorph() : secMorph);
            if (_morph == null) 
                continue;
            for(com.pullenti.morph.MorphBaseInfo it : _morph.getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                if (wf == null) 
                    continue;
                if (wf.misc.getAspect() != com.pullenti.morph.MorphAspect.UNDEFINED) 
                    asp = (wf.misc.getAspect() == com.pullenti.morph.MorphAspect.IMPERFECTIVE ? com.pullenti.ner.semantic.PredicateAttr.IMPERFECTIVE : com.pullenti.ner.semantic.PredicateAttr.PERFECTIVE);
                if (wf.misc.getAttrs().contains("инф.") && k == 0) 
                    ptyp = com.pullenti.ner.semantic.PredicateAttr.INFINITIVE;
                else if (wf.misc.getMood() == com.pullenti.morph.MorphMood.IMPERATIVE) 
                    ptyp = com.pullenti.ner.semantic.PredicateAttr.IMPERATIVE;
                else {
                    com.pullenti.morph.MorphTense fu = wf.misc.getTense();
                    if (fu == com.pullenti.morph.MorphTense.FUTURE) 
                        ptyp = com.pullenti.ner.semantic.PredicateAttr.FUTURE;
                    else if (fu == com.pullenti.morph.MorphTense.PAST) 
                        ptyp = com.pullenti.ner.semantic.PredicateAttr.PAST;
                    else if (fu == com.pullenti.morph.MorphTense.PRESENT) 
                        ptyp = com.pullenti.ner.semantic.PredicateAttr.PRESENT;
                }
                if (wf.misc.getAttrs().contains("возвр.") && k == 0) 
                    isReflex = true;
            }
        }
        if (asp == com.pullenti.ner.semantic.PredicateAttr.PERFECTIVE) {
            String bas = getBase();
            boolean ok = false;
            for(com.pullenti.morph.DerivateWord ei : getExplainInfo()) {
                if (ei._class.isVerb() && ei.aspect == com.pullenti.morph.MorphAspect.IMPERFECTIVE) {
                    setBase(ei.spelling);
                    ok = true;
                    break;
                }
            }
        }
        for(int i = 0; i < children.size(); i++) {
            SynToken ch = children.get(i);
            if (ch.typ == Types.ACTANT && ch.children.size() == 1) 
                ch = ch.children.get(0);
            if (ch.anaforRef0 != null && ch.anaforRef0.isValue("САМ", null)) {
                if (ch.beginChar < beginChar) 
                    setBeginToken(ch.getBeginToken());
                if (ch.endChar > endChar) 
                    setEndToken(ch.getEndToken());
                isReflex = true;
                for(java.util.Map.Entry<String, ValTypes> v : children.get(i).vals.entrySet()) {
                    if (v.getValue() == ValTypes.PROP || v.getValue() == ValTypes.ACTANTPROP) 
                        addVal(v.getKey(), ValTypes.PROP);
                }
                children.remove(i);
                i--;
            }
        }
        if (isReflex || getBase().endsWith("СЯ") || getBase().endsWith("СЬ")) {
            String bas = getBase();
            if (bas.endsWith("СЯ") || bas.endsWith("СЬ")) {
                com.pullenti.morph.MorphBaseInfo mi = com.pullenti.morph.Morphology.getWordBaseInfo(bas.substring(0, 0+(bas.length() - 2)), new com.pullenti.morph.MorphLang(null), false);
                if (mi._getClass().isVerb()) {
                    isReflex = true;
                    setBase(bas.substring(0, 0+(bas.length() - 2)));
                }
            }
        }
        for(java.util.Map.Entry<String, ValTypes> kp : vals.entrySet()) {
            if (kp.getValue() == ValTypes.BASE) 
                res.addSlot(com.pullenti.ner.semantic.PredicateReferent.ATTR_BASE, kp.getKey(), false, 0);
            else if (kp.getValue() == ValTypes.PROP) 
                res.addSlot(com.pullenti.ner.semantic.PredicateReferent.ATTR_PROP, kp.getKey(), false, 0);
        }
        if (isReflex) 
            res.addAttr(com.pullenti.ner.semantic.PredicateAttr.REFLEXIVE);
        if (pos != com.pullenti.ner.semantic.PredicateAttr.UNDEFINED) 
            res.addAttr(pos);
        if (ptyp != com.pullenti.ner.semantic.PredicateAttr.UNDEFINED) 
            res.addAttr(ptyp);
        if (asp != com.pullenti.ner.semantic.PredicateAttr.UNDEFINED) 
            res.addAttr(asp);
    }

    /**
     * Это используется внутренним образом для алгоритма стыковки объектов
     */
    public int _ObjVarTyp;

    /**
     * Это используется внутренним образом для алгоритма формирования актантов предикатов
     */
    public SynToken _LastPredicate;

    /**
     * Использовать это токен вместо текущего
     */
    public SynToken _UseThisToken;

    public static SynToken _new2472(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.semantic.ActantRole _arg4, com.pullenti.ner.MorphCollection _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.rol = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static SynToken _new2480(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public static SynToken _new2482(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.semantic.ActantRole _arg4) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.rol = _arg4;
        return res;
    }
    public static SynToken _new2488(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, Types _arg4, com.pullenti.ner.MorphCollection _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.typ2 = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static SynToken _new2513(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.Referent _arg4, com.pullenti.ner.MorphCollection _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static SynToken _new2516(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, Types _arg4) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.setBase(_arg3);
        res.typ = _arg4;
        return res;
    }
    public static SynToken _new2517(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.MorphCollection _arg4) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public static SynToken _new2521(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.MorphCollection _arg4, com.pullenti.ner.TextToken _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.anaforRef0 = _arg5;
        return res;
    }
    public static SynToken _new2532(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.TextToken _arg4, com.pullenti.ner.MorphCollection _arg5, com.pullenti.ner.MorphCollection _arg6) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.anaforRef0 = _arg4;
        res.setMorph(_arg5);
        res.secMorph = _arg6;
        return res;
    }
    public static SynToken _new2533(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.TextToken _arg4, com.pullenti.ner.MorphCollection _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.anaforRef0 = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static SynToken _new2537(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.core.NounPhraseToken _arg4, com.pullenti.ner.MorphCollection _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.npt = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static SynToken _new2542(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setBase(_arg4);
        res.setMorph(_arg5);
        return res;
    }
    public static SynToken _new2544(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, com.pullenti.ner.MorphCollection _arg4, String _arg5) {
        SynToken res = new SynToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.setBase(_arg5);
        return res;
    }
    public SynToken() {
        super();
    }
}
