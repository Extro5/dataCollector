/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class DialogItemToken extends com.pullenti.ner.MetaToken {

    public DialogItemToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    /**
     * Кто говорит
     */
    public CharacterVariant speaker;

    public CharacterEx firstEx;

    public CharacterEx secondEx;

    public com.pullenti.ner.ReferentToken speakerToken;

    public com.pullenti.morph.MorphGender speakerGender = com.pullenti.morph.MorphGender.UNDEFINED;

    public boolean speakerIsPronoun;

    /**
     * К кому обращаются
     */
    public java.util.ArrayList<CharacterVariant> callers = new java.util.ArrayList< >();

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> callerTokens = new java.util.ArrayList< >();

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (speaker != null) 
            res.append(speaker.toString()).append(" ->");
        else if (firstEx != null) 
            res.append(firstEx.toString(true, new com.pullenti.morph.MorphLang(null), 0)).append(" => ");
        else if (speakerGender != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (speakerIsPronoun) 
                res.append((speakerGender == com.pullenti.morph.MorphGender.MASCULINE ? "ОН" : ((speakerGender == com.pullenti.morph.MorphGender.FEMINIE ? "ОНА" : "ОНО")))).append(" ");
            else 
                res.append(speakerGender.toString()).append(" ");
        }
        if (secondEx != null) 
            res.append("=> ").append(secondEx.toString(true, new com.pullenti.morph.MorphLang(null), 0));
        for(CharacterVariant c : callers) {
            res.append("-> ").append(c.toString());
        }
        res.append(" : ").append(getSourceText());
        return res.toString();
    }

    public static boolean isInDialog(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t.tag instanceof DialogItemToken) 
            return true;
        if (t instanceof com.pullenti.ner.MetaToken) {
            for(com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
                if (isInDialog(tt)) 
                    return true;
            }
        }
        return false;
    }

    public static void markDialogsByTag(com.pullenti.ner.Token first) {
        int dialogRegime = 0;
        DialogItemToken dlg = null;
        for(com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (t.isNewlineBefore()) {
                dialogRegime = 0;
                dlg = null;
            }
            PeaceToken peace = PeaceToken.getPeace(t);
            if (peace == null || !peace.isText) {
                dialogRegime = 0;
                dlg = null;
                continue;
            }
            if (dialogRegime == 0 && t.isNewlineBefore() && t.isHiphen()) {
                if (dlg == null) 
                    dlg = _new1528(t, t, peace);
                t.tag = dlg;
                dialogRegime = 1;
                continue;
            }
            if (dialogRegime == 0 && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.of((com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES.value()) | (com.pullenti.ner.core.BracketParseAttr.CANCONTAINSVERBS.value())), 1000);
                if (br != null) {
                    DialogItemToken dlg1 = null;
                    if (t.isNewlineBefore() || ((t.getPrevious() != null && ((t.getPrevious().isChar(':') || isInDialog(t.getPrevious())))))) 
                        dlg1 = _new1528(t, t, peace);
                    for(; t.endChar <= br.endChar; t = t.getNext()) {
                        if (dlg1 != null) 
                            t.tag = dlg1;
                        else {
                            br.tag = peace;
                            t.tag = br;
                        }
                    }
                }
                else {
                }
                continue;
            }
            if ((dialogRegime == 1 && t.isCharOf(".?!,]") && t.getNext() != null) && !t.isNewlineAfter() && t.getNext().isHiphen()) {
                t = t.getNext();
                dialogRegime = 2;
                continue;
            }
            if (dialogRegime == 2 && t.isHiphen()) {
                dialogRegime = 1;
                continue;
            }
            if (dialogRegime == 1) {
                if (dlg == null) 
                    dlg = _new1528(t, t, peace);
                t.tag = dlg;
                continue;
            }
        }
    }

    public static java.util.ArrayList<DialogItemToken> tryAttachList(com.pullenti.ner.Token t) {
        DialogItemToken it = tryAttach(t);
        if (it == null) 
            return null;
        java.util.ArrayList<DialogItemToken> res = new java.util.ArrayList< >();
        res.add(it);
        for(t = it.getEndToken().getNext(); t != null; t = t.getNext()) {
            it = tryAttach(t);
            if (it == null) 
                break;
            res.add(it);
            t = it.getEndToken();
        }
        return res;
    }

    public static DialogItemToken tryAttach(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        DialogItemToken res = null;
        DialogItemToken res0 = null;
        boolean quoteRegime = false;
        if (t.isHiphen()) {
            if (!t.isNewlineBefore()) 
                return null;
            res = new DialogItemToken(t, t);
        }
        else {
            if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                return null;
            for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                if (tt.isChar(':')) {
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt.getNext(), true, false)) {
                        res = new DialogItemToken(t, t);
                        com.pullenti.ner.Token tt0 = _analyzeAsker(res, t, true);
                        if (tt0 == tt && ((res.speakerGender != com.pullenti.morph.MorphGender.UNDEFINED || res.speaker != null || res.firstEx != null))) {
                            t = tt.getNext();
                            quoteRegime = true;
                        }
                        else 
                            res = null;
                    }
                }
                if (tt.isCharOf(".:")) {
                    if (tt.isNewlineAfter() && tt.getNext() != null && tt.getNext().isHiphen()) {
                        res0 = new DialogItemToken(t, t);
                        com.pullenti.ner.Token tt0 = _analyzeAsker(res0, t, true);
                        if (tt0 == tt && ((res0.speaker != null || res0.firstEx != null || res0.speakerIsPronoun))) {
                            t = tt.getNext();
                            res = new DialogItemToken(t, t);
                        }
                        else 
                            res0 = null;
                    }
                    break;
                }
                if (tt.isNewlineBefore() && tt != t) 
                    break;
            }
        }
        if (res == null) 
            return null;
        for(t = t.getNext(); t != null; t = (t != null ? t.getNext() : null)) {
            if (t.isNewlineBefore()) 
                break;
            res.setEndToken(t);
            if (quoteRegime && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false)) 
                break;
            if ((t.isCharOf(",!?.]") && t.getNext() != null && t.getNext().isHiphen()) && !t.isNewlineAfter()) {
                t = _analyzeAsker(res, t.getNext().getNext(), false);
                if (t == null || t.isNewlineBefore()) 
                    break;
                continue;
            }
            CharacterVariant cha1 = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
            CharacterEx cha2 = (CharacterEx)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterEx.class);
            if (cha1 == null && cha2 == null) 
                continue;
            com.pullenti.morph.MorphGender gen1;
            com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> inoutarg1531 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1532 = isSecondPerson(t, inoutarg1531);
            gen1 = inoutarg1531.value;
            if (!inoutres1532) 
                continue;
            if (cha1 != null && !res.callers.contains(cha1)) {
                res.callers.add(cha1);
                res.callerTokens.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
            }
            if (cha2 != null && res.secondEx == null) 
                res.secondEx = cha2;
        }
        if (res0 != null) {
            if (res0.speakerGender != com.pullenti.morph.MorphGender.UNDEFINED && res.speakerGender != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (res0.speakerGender != res.speakerGender) 
                    return null;
            }
            if (res0.speaker != null && res.speaker != null) {
                if (res0.speaker != res.speaker) 
                    return null;
            }
            if (res0.speaker != null) {
                if (res.callers.contains(res0.speaker)) 
                    return null;
            }
            if (res.speaker != null) {
                if (res0.callers.contains(res.speaker)) 
                    return null;
            }
            if (res0.firstEx != null && res0.firstEx == res.secondEx) 
                return null;
            if (res.secondEx != null && res0.secondEx == res.firstEx) 
                return null;
            if (res.speaker == null && res0.speaker != null) {
                res.speaker = res0.speaker;
                res.speakerIsPronoun = false;
            }
            if (res.firstEx == null && res0.firstEx != null) {
                res.firstEx = res0.firstEx;
                res.speakerIsPronoun = false;
            }
            if (res.speakerGender == com.pullenti.morph.MorphGender.UNDEFINED) 
                res.speakerGender = res0.speakerGender;
            res.setBeginToken(res0.getBeginToken());
        }
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isCharOf(".?!")) 
            res.setEndToken(res.getEndToken().getNext());
        return res;
    }

    private static com.pullenti.ner.Token _analyzeAsker(DialogItemToken res, com.pullenti.ner.Token t, boolean isInPreamble) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token tVerb = null;
        com.pullenti.ner.Token agent = null;
        com.pullenti.ner.Token pacient = null;
        for(; t != null; t = t.getNext()) {
            if (t.isNewlineBefore() && t != t0) 
                break;
            res.setEndToken(t);
            if ((t.getReferent() instanceof CharacterVariant) || (t.getReferent() instanceof CharacterEx)) {
                if (agent == null && ((t.getMorph().getCase().isUndefined() || t.getMorph().getCase().isNominative()))) 
                    agent = t;
                else if (pacient == null && ((t.getMorph().getCase().isDative() || t.getMorph().getCase().isGenitive() || t.getMorph().getCase().isAccusative()))) {
                    if (t.getPrevious().getMorph()._getClass().isPreposition()) {
                    }
                    else if (tVerb == null) 
                        pacient = t;
                    else if (t.getPrevious() == tVerb || t.getPrevious() == agent) 
                        pacient = t;
                }
                continue;
            }
            if (t.isHiphen() || t.isCharOf(":.")) 
                break;
            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
            if ((mc.isVerb() && !mc.isAdjective() && tVerb == null) && t.chars.isAllLower()) {
                tVerb = t;
                continue;
            }
            if (mc.isAdverb()) 
                continue;
            if (mc.isPreposition()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null) {
                    com.pullenti.morph.MorphCase cas = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(t.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    if (!((com.pullenti.morph.MorphCase.ooBitand(cas, npt.getMorph().getCase()))).isUndefined()) {
                        t = npt.getEndToken();
                        continue;
                    }
                }
                continue;
            }
            if (mc.isPersonalPronoun()) {
                if (t.isValue("ОН", null) || t.isValue("ОНА", null) || t.isValue("ОНО", null)) {
                    if ((agent == null && t.getMorph().getCase().isUndefined()) || t.getMorph().getCase().isNominative()) 
                        agent = t;
                    else if (pacient != null && ((t.getMorph().getCase().isDative() || t.getMorph().getCase().isGenitive()))) 
                        pacient = t;
                    continue;
                }
            }
            if (mc.isNoun() && tVerb != null) {
                if (!t.getMorph().getCase().isUndefined()) {
                    if (!t.getMorph().getCase().isNominative()) {
                        if (!t.getMorph().containsAttr("одуш.", new com.pullenti.morph.MorphClass(null))) 
                            break;
                    }
                }
            }
        }
        if (tVerb != null && (((tVerb.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
            if (agent != null) {
                if (tVerb.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && agent.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    res.speakerGender = com.pullenti.morph.MorphGender.of((tVerb.getMorph().getGender().value()) & (agent.getMorph().getGender().value()));
                    if (agent.getMorph()._getClass().isPersonalPronoun()) 
                        res.speakerIsPronoun = true;
                    if (res.speakerGender == com.pullenti.morph.MorphGender.UNDEFINED) 
                        agent = null;
                    if (res.speakerGender != com.pullenti.morph.MorphGender.MASCULINE && res.speakerGender != com.pullenti.morph.MorphGender.FEMINIE && res.speakerGender != com.pullenti.morph.MorphGender.NEUTER) 
                        res.speakerGender = com.pullenti.morph.MorphGender.UNDEFINED;
                }
            }
            if (pacient != null) {
                if (M_VERBS.tryParse(tVerb, com.pullenti.ner.core.TerminParseAttr.NO) == null || agent == null) 
                    pacient = null;
            }
            if (res.speaker == null && res.firstEx == null) {
                if (agent instanceof com.pullenti.ner.ReferentToken) {
                    res.speaker = (CharacterVariant)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(agent, com.pullenti.ner.ReferentToken.class))).referent, CharacterVariant.class);
                    res.firstEx = (CharacterEx)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(agent, com.pullenti.ner.ReferentToken.class))).referent, CharacterEx.class);
                    res.speakerToken = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(agent, com.pullenti.ner.ReferentToken.class);
                    if (res.speakerGender == com.pullenti.morph.MorphGender.UNDEFINED && ((tVerb.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || tVerb.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || tVerb.getMorph().getGender() == com.pullenti.morph.MorphGender.NEUTER))) 
                        res.speakerGender = tVerb.getMorph().getGender();
                }
                else if ((agent instanceof com.pullenti.ner.TextToken) && ((agent.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || agent.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE))) {
                }
                if (pacient instanceof com.pullenti.ner.ReferentToken) {
                    CharacterVariant ch = (CharacterVariant)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(pacient, com.pullenti.ner.ReferentToken.class))).referent, CharacterVariant.class);
                    if (!res.callers.contains(ch)) 
                        res.callers.add(ch);
                }
            }
        }
        return t;
    }

    public static boolean isSecondPerson(com.pullenti.ner.Token t, com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> gen) {
        gen.value = com.pullenti.morph.MorphGender.UNDEFINED;
        if (!((t instanceof com.pullenti.ner.ReferentToken))) 
            return false;
        CharacterVariant cv = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
        CharacterEx ce = (CharacterEx)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterEx.class);
        if (cv == null && ce == null) 
            return false;
        if (!t.getMorph().getCase().isUndefined()) {
            if (!t.getMorph().getCase().isNominative() && !t.getMorph().getCase().isVocative()) 
                return false;
        }
        com.pullenti.ner.Token t1 = t.getNext();
        if (t1 == null) 
            return false;
        int coef = 0;
        if (t1.isCharOf("!?.];")) {
        }
        else if (t1.isChar(',') && t1.getNext() != null) {
            if (t1.getNext().isHiphen()) {
            }
            else 
                for(com.pullenti.ner.Token ttt = t1.getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                        break;
                    com.pullenti.morph.MorphClass mc = ttt.getMorphClassInDictionary();
                    if (mc.isMisc()) 
                        continue;
                    if (ttt.isValue("ТЫ", null) || ttt.isValue("ВЫ", null)) 
                        coef++;
                    break;
                }
        }
        else 
            return false;
        com.pullenti.ner.Token t0 = t.getPrevious();
        for(; t0 != null; t0 = t0.getPrevious()) {
            com.pullenti.morph.MorphClass mc = t0.getMorphClassInDictionary();
            if (mc.isAdjective()) {
                if (!t0.getMorph().getCase().isNominative()) 
                    break;
                gen.value = t0.getMorph().getGender();
                if (CharItemToken.M_EMPTYWORDS.tryParse(t0, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                    coef++;
                continue;
            }
            if (mc.isPronoun()) {
                if (mc.isPersonalPronoun() || !t0.isValue("МОЙ", null)) 
                    break;
                if (!t0.getMorph().getCase().isNominative()) 
                    break;
                gen.value = t0.getMorph().getGender();
                coef++;
                continue;
            }
            com.pullenti.ner.core.TerminToken tok = CharItemToken.M_EMPTYWORDS.tryParse(t0, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && tok.termin.tag != null) 
                continue;
            break;
        }
        if (t0 == null) 
            return false;
        if (t0.isComma()) {
            if (t0.getPrevious() != null) {
                if (t0.getPrevious().getMorph()._getClass().isVerb() && t0.getPrevious().getMorph().containsAttr("пов.накл.", new com.pullenti.morph.MorphClass(null))) 
                    coef++;
                else if (t0.getPrevious().isValue("ВЫ", null) || t0.getPrevious().isValue("ТЫ", null)) 
                    coef++;
            }
        }
        else if (t0.isHiphen() || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0, false, false)) {
        }
        else 
            return false;
        if (coef == 0) {
            if (ce != null) {
            }
            else if (cv.items.size() == 1 && ((cv.items.get(0).isCanBeFirstName() || cv.items.get(0).isCanBeLastName()))) {
            }
            else if (cv.items.size() == 2 && cv.items.get(0).isAttr() && ((cv.items.get(1).isCanBeFirstName() || cv.items.get(1).isCanBeLastName()))) {
            }
            else 
                return false;
        }
        if (gen.value != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (ce != null) {
                if ((((ce.gender.value()) & (gen.value.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    return false;
            }
            else if (cv != null) {
                if ((((cv.getGender().value()) & (gen.value.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    return false;
            }
        }
        else if (ce != null) 
            gen.value = ce.gender;
        else 
            gen.value = cv.getGender();
        return true;
    }

    public static void mergeByDialog(java.util.ArrayList<DialogItemToken> dlg, MergeContainer merge, CharacterEx firstPerson) {
        int i;
        for(i = 0; i < (dlg.size() - 1); i++) {
            DialogItemToken d1 = dlg.get(i);
            DialogItemToken d2 = dlg.get(i + 1);
            if (d1.secondEx != null && d2.firstEx != null && d1.secondEx != d2.firstEx) {
                if (!d2.firstEx.canNotBeEqual(d1.secondEx)) {
                    boolean ok = false;
                    if (((i + 2) < dlg.size()) && d1.secondEx == dlg.get(i + 2).secondEx) 
                        ok = true;
                    else if (d2.firstEx == firstPerson) 
                        ok = true;
                    if (!ok) 
                        continue;
                }
            }
        }
        com.pullenti.morph.MorphGender gen0 = com.pullenti.morph.MorphGender.UNDEFINED;
        com.pullenti.morph.MorphGender gen1 = com.pullenti.morph.MorphGender.UNDEFINED;
        java.util.ArrayList<CharacterEx> fi0 = new java.util.ArrayList< >();
        java.util.ArrayList<CharacterEx> fi1 = new java.util.ArrayList< >();
        java.util.ArrayList<CharacterEx> sec0 = new java.util.ArrayList< >();
        java.util.ArrayList<CharacterEx> sec1 = new java.util.ArrayList< >();
        int cou = 0;
        for(i = 0; i < dlg.size(); i++) {
            DialogItemToken d = dlg.get(i);
            int k = i & 1;
            com.pullenti.morph.MorphGender g = d.speakerGender;
            if (d.firstEx != null) {
                java.util.ArrayList<CharacterEx> fi = (k == 0 ? fi0 : fi1);
                if (!fi.contains(d.firstEx)) 
                    fi.add(d.firstEx);
                g = d.firstEx.gender;
                cou++;
            }
            else if (d.speaker != null) 
                break;
            if (k == 0) 
                gen0 = com.pullenti.morph.MorphGender.of((gen0.value()) | (g.value()));
            else 
                gen1 = com.pullenti.morph.MorphGender.of((gen1.value()) | (g.value()));
            if (d.secondEx != null) {
                java.util.ArrayList<CharacterEx> fi = (k == 0 ? sec0 : sec1);
                if (!fi.contains(d.secondEx)) 
                    fi.add(d.secondEx);
                cou++;
            }
        }
        if (((i >= dlg.size() && cou > 6 && (fi0.size() < 2)) && (fi1.size() < 2) && (sec0.size() < 2)) && (sec1.size() < 2)) {
            boolean err = false;
            for(CharacterEx f : fi0) {
                if (fi1.contains(fi0.get(0)) || sec0.contains(fi0.get(0))) 
                    err = true;
                for(CharacterEx s : sec1) {
                    if (s.canNotBeEqual(f)) 
                        err = true;
                }
            }
            for(CharacterEx f : fi1) {
                if (fi0.contains(fi1.get(0)) || sec1.contains(fi1.get(0))) 
                    err = true;
                for(CharacterEx s : sec0) {
                    if (s.canNotBeEqual(f)) 
                        err = true;
                }
            }
            if (!err) {
                if ((fi0.size() <= 1 && fi1.size() == 1 && fi1.get(0) != firstPerson) && sec0.size() == 1) {
                    if (!fi1.get(0).canNotBeEqual(sec0.get(0))) 
                        merge.add(fi1.get(0), sec0.get(0));
                }
                if ((fi1.size() <= 1 && fi0.size() == 1 && fi0.get(0) != firstPerson) && sec1.size() == 1) {
                    if (!fi0.get(0).canNotBeEqual(sec1.get(0))) 
                        merge.add(fi0.get(0), sec1.get(0));
                }
            }
        }
    }

    public static com.pullenti.ner.core.TerminCollection M_VERBS;

    public static DialogItemToken _new1528(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Object _arg3) {
        DialogItemToken res = new DialogItemToken(_arg1, _arg2);
        res.tag = _arg3;
        return res;
    }
    public DialogItemToken() {
        super();
    }
    static {
        M_VERBS = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"ГОВОРИТЬ", "СООБЩИТЬ", "СООБЩАТЬ", "СКАЗАТЬ", "ОТВЕТИТЬ", "ОТВЕЧАТЬ", "ПИСАТЬ", "ПОВЕДАТЬ", "СПРОСИТЬ", "СПРАШИВАТЬ", "РАССКАЗАТЬ", "РАССКАЗЫВАТЬ", "ОТМЕЧАТЬ", "ОТМЕТИТЬ", "ПОДЧЕРКНУТЬ", "ПОДЧЕРКИВАТЬ", "ОБЪЯСНЯТЬ", "ОБЪЯСНИТЬ", "ПОЯСНЯТЬ", "ПОЯСНИТЬ", "УТОЧНИТЬ", "УТОЧНЯТЬ", "ОТВЕТСТВОВАТЬ", "ПРОШЕПТАТЬ", "МОЛВИТЬ", "ПРОМОЛВИТЬ", "ПРОГОВОРИТЬ", "ПРОМЯМЛИТЬ", "ПРОБУБНИТЬ", "ЗАКРИЧАТЬ", "ПРОКРИЧАТЬ", "РЯВКНУТЬ", "ВОЗРАЗИТЬ", "ВОЗРАЖАТЬ", "ПРЕРВАТЬ", "ОКЛИКНУТЬ"}) {
            M_VERBS.add(new com.pullenti.ner.core.Termin(s, new com.pullenti.morph.MorphLang(null), false));
        }
    }
}
