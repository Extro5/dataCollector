/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class ChapterTextToken extends com.pullenti.ner.MetaToken {

    public ChapterTextToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public String caption;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(characters.size()).append(" chars: ").append(((String)com.pullenti.n2j.Utils.notnull(caption, "?"))).append(" ");
        return tmp.toString();
    }

    public CharacterVariantContainer m_VarCnt;

    public java.util.ArrayList<CharacterEx> characters = new java.util.ArrayList< >();

    /**
     * Сформировать для главы список потенциальных персонажей
     */
    public void primaryAnalyze() {
        java.util.ArrayList<CharacterEx> stack = new java.util.ArrayList< >();
        com.pullenti.ner.Token agent = null;
        com.pullenti.ner.Token verb = null;
        CharacterEx firstPerson = null;
        CharacterVariant firstPersonVar = new CharacterVariant();
        for(int step = 0; step < 2; step++) {
            stack.clear();
            for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
                if (DialogItemToken.isInDialog(t) != DialogItemToken.isInDialog(t.getNext())) 
                    agent = (verb = null);
                if (t.getMorph()._getClass().isConjunction() || t.getMorph()._getClass().isAdverb()) 
                    continue;
                com.pullenti.ner.core.NounPhraseToken npt;
                if (t.getMorph()._getClass().isPreposition()) {
                    if ((((npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0)))) != null) {
                        t = npt.getEndToken();
                        continue;
                    }
                    if (t.getNext() instanceof com.pullenti.ner.ReferentToken) 
                        t = t.getNext();
                    continue;
                }
                if (t.getMorph()._getClass().isPersonalPronoun() && !DialogItemToken.isInDialog(t) && t.isValue("Я", null)) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new709(firstPersonVar, t, t, t.getMorph());
                    t.kit.embedToken(rt);
                    t = rt;
                    if (beginChar == t.beginChar) 
                        setBeginToken(t);
                    if (endChar == t.endChar) 
                        setEndToken(t);
                }
                if (t instanceof com.pullenti.ner.TextToken) {
                    com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                    if (mc.isVerb() && !mc.isAdjective() && !mc.isPronoun()) {
                        if (!t.getMorph().containsAttr("инф.", new com.pullenti.morph.MorphClass(null))) 
                            verb = t;
                        continue;
                    }
                    if (mc.isPersonalPronoun()) {
                        if (t.getMorph().getCase().isNominative()) 
                            agent = t;
                        continue;
                    }
                    npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null) {
                        if (!npt.getMorph().getCase().isUndefined() && !npt.getMorph().getCase().isNominative() && !npt.getMorph().getCase().isVocative()) 
                            continue;
                        agent = t;
                        continue;
                    }
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t.getNext())) 
                        agent = (verb = null);
                    continue;
                }
                com.pullenti.morph.MorphGender gen1 = com.pullenti.morph.MorphGender.UNDEFINED;
                com.pullenti.ner.Token byName = null;
                com.pullenti.morph.MorphGender gen2 = com.pullenti.morph.MorphGender.UNDEFINED;
                CharacterVariant cha = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
                if (cha != null) {
                    com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> inoutarg1464 = new com.pullenti.n2j.Outargwrapper< >();
                    byName = LitHelper.findByName(t.getNext(), inoutarg1464);
                    gen2 = inoutarg1464.value;
                    if (byName != null) 
                        cha.isCharacter = true;
                    else {
                        com.pullenti.ner.Token st = _splitVariant(t, gen1, true);
                        if (st != null) {
                            cha = (CharacterVariant)com.pullenti.n2j.Utils.cast(st.getReferent(), CharacterVariant.class);
                            if (cha == null) 
                                continue;
                            t = st;
                        }
                    }
                }
                if (cha == firstPersonVar) {
                }
                else if (cha == null) {
                    if (t.getMorph().getCase().isUndefined() || t.getMorph().getCase().isNominative() || t.getMorph().getCase().isVocative()) 
                        agent = t;
                    continue;
                }
                else if (cha.items.get(0).isAttr() && cha.items.size() == 1 && byName == null) {
                    CharItemVar attr = cha.items.get(0);
                    if (attr.isEmoAttr() || attr.isEmptyAttr() || cha.inDialogAgentCount == 0) {
                        if (t.getMorph().getCase().isUndefined() || t.getMorph().getCase().isNominative() || t.getMorph().getCase().isVocative()) 
                            agent = t;
                        continue;
                    }
                }
                else {
                }
                if (!t.getMorph().getCase().isUndefined()) {
                    if (!t.getMorph().getCase().isNominative() && !t.getMorph().getCase().isVocative()) 
                        continue;
                }
                else if (t.getPrevious() != null && t.getPrevious() == agent) 
                    continue;
                com.pullenti.ner.Token agent1 = agent;
                com.pullenti.ner.Token agent2 = null;
                com.pullenti.ner.Token verb2 = null;
                boolean hasDelim = false;
                for(com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.endChar <= endChar; tt = tt.getNext()) {
                    if (byName != null) 
                        break;
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                        break;
                    if (DialogItemToken.isInDialog(t) != DialogItemToken.isInDialog(tt)) 
                        break;
                    if (tt.getMorph()._getClass().isConjunction() || tt.getMorph()._getClass().isAdverb()) 
                        continue;
                    if (tt.getMorph()._getClass().isPreposition()) {
                        if ((((npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0)))) != null) {
                            tt = npt.getEndToken();
                            continue;
                        }
                        if (tt.getNext() instanceof com.pullenti.ner.ReferentToken) 
                            tt = tt.getNext();
                        continue;
                    }
                    if (tt instanceof com.pullenti.ner.TextToken) {
                        if ((tt == t.getNext() && tt.isComma() && tt.getNext() != null) && tt.getNext().isValue("КОТОРЫЙ", null)) {
                            verb2 = tt.getNext();
                            break;
                        }
                        npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                        if (npt != null) {
                            if (!npt.getMorph().getCase().isUndefined() && !npt.getMorph().getCase().isNominative() && !npt.getMorph().getCase().isVocative()) 
                                continue;
                            agent2 = tt;
                            continue;
                        }
                        com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                        if (mc.isPersonalPronoun()) {
                            if (tt.getMorph().getCase().isNominative()) 
                                agent2 = tt;
                            continue;
                        }
                        if (mc.isVerb() && !mc.isAdjective() && !mc.isPronoun()) {
                            if (!tt.getMorph().containsAttr("инф.", new com.pullenti.morph.MorphClass(null))) {
                                if (verb2 != null) 
                                    break;
                                verb2 = tt;
                            }
                            continue;
                        }
                    }
                    if (tt instanceof com.pullenti.ner.ReferentToken) {
                        if (tt.getMorph().getCase().isUndefined() || tt.getMorph().getCase().isNominative() || tt.getMorph().getCase().isVocative()) 
                            agent2 = tt;
                        continue;
                    }
                    if (tt.isCommaAnd()) 
                        hasDelim = true;
                }
                if (verb != null) {
                    com.pullenti.ner.MorphCollection mc = new com.pullenti.ner.MorphCollection(verb.getMorph());
                    mc.removeItems(com.pullenti.morph.MorphClass.VERB, false);
                    if (_isDefGender(mc.getGender()) && mc.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        boolean err = false;
                        if (agent != null && agent.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                            if (agent.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && (((mc.getGender().value()) & (agent.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                            }
                            else 
                                err = true;
                        }
                        if (agent2 != null && agent2.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                            if (agent2.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && (((mc.getGender().value()) & (agent2.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                            }
                            else 
                                err = true;
                        }
                        if (!err) 
                            gen1 = mc.getGender();
                    }
                }
                if (verb2 != null) {
                    com.pullenti.ner.MorphCollection mc = new com.pullenti.ner.MorphCollection(verb2.getMorph());
                    if (verb2.getMorph()._getClass().isVerb()) 
                        mc.removeItems(com.pullenti.morph.MorphClass.VERB, false);
                    if (_isDefGender(mc.getGender()) && mc.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        boolean err = false;
                        if (verb2.getMorph()._getClass().isPronoun()) {
                        }
                        else {
                            if (agent != null && agent.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                                if (agent.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && (((mc.getGender().value()) & (agent.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                }
                                else 
                                    err = true;
                            }
                            if (agent2 != null && agent2.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                                if (agent2.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && (((mc.getGender().value()) & (agent2.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                }
                                else 
                                    err = true;
                            }
                        }
                        if (!err) 
                            gen2 = mc.getGender();
                    }
                }
                if (gen1 != com.pullenti.morph.MorphGender.UNDEFINED && gen2 != com.pullenti.morph.MorphGender.UNDEFINED && (((gen1.value()) & (gen2.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                    if ((((gen1.value()) & (cha.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        gen1 = com.pullenti.morph.MorphGender.UNDEFINED;
                    if ((((gen2.value()) & (cha.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        gen2 = com.pullenti.morph.MorphGender.UNDEFINED;
                }
                gen1 = com.pullenti.morph.MorphGender.of((gen1.value()) | (gen2.value()));
                agent = t;
                gen2 = com.pullenti.morph.MorphGender.UNDEFINED;
                com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> inoutarg1468 = new com.pullenti.n2j.Outargwrapper< >();
                boolean inoutres1469 = DialogItemToken.isSecondPerson(t, inoutarg1468);
                gen2 = inoutarg1468.value;
                if (inoutres1469) 
                    gen1 = gen2;
                else if (cha.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (byName != null && gen1 == com.pullenti.morph.MorphGender.UNDEFINED) 
                        gen1 = cha.getGender();
                    else 
                        gen1 = com.pullenti.morph.MorphGender.of((gen1.value()) & (cha.getGender().value()));
                }
                if (!_isDefGender(gen1)) 
                    continue;
                if (cha == firstPersonVar) {
                    if (firstPerson == null) 
                        firstPerson = CharacterEx._new1465(gen1, CharacterExType.FIRSTPERSON);
                    if ((((gen1.value()) & (firstPerson.gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        continue;
                    firstPerson.norms.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
                    (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).referent = firstPerson;
                    continue;
                }
                com.pullenti.ner.Token mergeWithNext = _analyzeAnaforAndNextMerge(t);
                if (step == 0 && cha.items.size() == 1 && byName == null) {
                    CharItemVar a = (cha.items.get(0).isAttr() ? cha.items.get(0) : null);
                    if (a != null && ((a.isCanBePersonAfter() || a.isAnimal()))) {
                        if (a.anaforRef == null && a.getRefCharacter() == null) 
                            continue;
                    }
                }
                int i;
                for(i = 0; i < stack.size(); i++) {
                    if (stack.get(i).canBeEqual(cha, gen1)) 
                        break;
                }
                if (i < stack.size()) {
                    stack.get(i).addNormal((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class), gen1);
                    if (i > 0) {
                        CharacterEx ccc = stack.get(i);
                        stack.remove(i);
                        stack.add(0, ccc);
                    }
                }
                else {
                    CharacterEx chNew = CharacterEx._new1466(gen1);
                    chNew.addNormal((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class), gen1);
                    stack.add(0, chNew);
                    characters.add(chNew);
                }
                if (byName != null && (byName.getReferent() instanceof CharacterVariant)) {
                    stack.get(0).addVariant((CharacterVariant)com.pullenti.n2j.Utils.cast(byName.getReferent(), CharacterVariant.class));
                    mergeWithNext = byName;
                }
                if (mergeWithNext == null) 
                    (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).referent = stack.get(0);
                else {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new709(stack.get(0), t, mergeWithNext, t.getMorph());
                    t.kit.embedToken(rt);
                    t = rt;
                }
            }
            stack.clear();
            for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
                CharacterEx cha = (CharacterEx)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterEx.class);
                if (cha != null) {
                    if (cha.real != null) 
                        cha = cha.real;
                    int i = stack.indexOf(cha);
                    if (i > 0) 
                        stack.remove(i);
                    stack.add(0, cha);
                    com.pullenti.morph.MorphGender gen;
                    com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> inoutarg1471 = new com.pullenti.n2j.Outargwrapper< >();
                    com.pullenti.ner.Token st = LitHelper.findByName(t.getNext(), inoutarg1471);
                    gen = inoutarg1471.value;
                    if (st != null) {
                        if (gen == com.pullenti.morph.MorphGender.UNDEFINED || (((gen.value()) & (cha.gender.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                            boolean ok = false;
                            if (st.getReferent() instanceof CharacterVariant) {
                                cha.addVariant((CharacterVariant)com.pullenti.n2j.Utils.cast(st.getReferent(), CharacterVariant.class));
                                ok = true;
                            }
                            else if (st.getReferent() instanceof CharacterEx) {
                                cha.mergeWith((CharacterEx)com.pullenti.n2j.Utils.cast(st.getReferent(), CharacterEx.class));
                                ok = true;
                            }
                            if (ok) {
                                com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new709(cha, t, st, t.getMorph());
                                t.kit.embedToken(rt);
                                t = rt;
                                if (t.getPrevious() != null) 
                                    t = t.getPrevious();
                                continue;
                            }
                        }
                    }
                    continue;
                }
                CharacterVariant var = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
                if (var != null && !var.isError && var.items.size() > 0) {
                    com.pullenti.morph.MorphGender gen = var.getGender();
                    if (!_isDefGender(gen) && _isDefGender(t.getMorph().getGender())) 
                        gen = t.getMorph().getGender();
                    com.pullenti.ner.Token st = _splitVariant(t, gen, false);
                    if (st != null) {
                        var = (CharacterVariant)com.pullenti.n2j.Utils.cast(st.getReferent(), CharacterVariant.class);
                        if (var == null) 
                            continue;
                        t = st;
                    }
                    com.pullenti.ner.Token mergeWithNext = _analyzeAnaforAndNextMerge(t);
                    int i;
                    for(i = 0; i < stack.size(); i++) {
                        if (stack.get(i).canBeEqual(var, gen)) {
                            CharacterEx ce = CharacterEx._new1466(gen);
                            ce.addVariant(var);
                            if (!ce.canBeMergedByResult(stack.get(i))) 
                                continue;
                            if (ce.canNotBeEqual(stack.get(i)) || stack.get(i).canNotBeEqual(ce)) 
                                continue;
                            break;
                        }
                    }
                    if (i < stack.size()) {
                        cha = stack.get(i);
                        cha.addVariant(var);
                        cha.notNorms.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
                        if (i > 0) {
                            stack.remove(i);
                            stack.add(0, cha);
                        }
                        if (mergeWithNext == null) 
                            (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).referent = cha;
                        else {
                            com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new709(cha, t, mergeWithNext, t.getMorph());
                            kit.embedToken(rt);
                            t = rt;
                        }
                        continue;
                    }
                }
            }
        }
        MergeContainer.mergeDublicates(characters);
        java.util.ArrayList<java.util.ArrayList<DialogItemToken>> dlgs = new java.util.ArrayList< >();
        for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            java.util.ArrayList<DialogItemToken> dlg = DialogItemToken.tryAttachList(t);
            if (dlg == null || dlg.size() == 0) 
                continue;
            if (dlg.size() > 1) 
                dlgs.add(dlg);
            for(DialogItemToken d : dlg) {
                if (d.firstEx != null) 
                    d.firstEx.firstPersonInDialogCount++;
            }
            t = dlg.get(dlg.size() - 1).getEndToken();
        }
        MergeContainer dlgMerge = new MergeContainer();
        for(java.util.ArrayList<DialogItemToken> dlg : dlgs) {
            DialogItemToken.mergeByDialog(dlg, dlgMerge, firstPerson);
        }
        if (firstPerson != null) {
            java.util.ArrayList<CharacterEx> reals = dlgMerge.getOthers(firstPerson);
            if (reals != null && reals.size() == 1) {
                dlgMerge.del(firstPerson);
                for(com.pullenti.ner.ReferentToken t : firstPerson.norms) {
                    t.referent = reals.get(0);
                }
            }
        }
        while(dlgMerge.pairs.size() > 0) {
            CharacterEx p = dlgMerge.pairs.get(0).char1;
            java.util.ArrayList<CharacterEx> eq = dlgMerge.getOthers(p);
            if (eq.size() == 1) {
                p.mergeWith(eq.get(0));
                dlgMerge.del(p);
                continue;
            }
            break;
        }
    }

    private static boolean _isDefGender(com.pullenti.morph.MorphGender g) {
        if (g == com.pullenti.morph.MorphGender.FEMINIE) 
            return true;
        if (g == com.pullenti.morph.MorphGender.MASCULINE || g == com.pullenti.morph.MorphGender.NEUTER) 
            return true;
        if ((g.value()) == (((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.NEUTER.value())))) 
            return true;
        return false;
    }

    private com.pullenti.ner.Token _splitVariant(com.pullenti.ner.Token t, com.pullenti.morph.MorphGender gen, boolean isNorm) {
        if (t == null) 
            return null;
        CharacterVariant cha = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
        if (cha == null) 
            return null;
        if (cha.items.size() < 2) 
            return null;
        if (cha.items.get(0).isAfterByName()) 
            return null;
        int spInd = -1;
        int i0 = 0;
        if (((cha.items.get(0).isCanBePersonAfter() || cha.items.get(0).isAnimal())) && !cha.items.get(0).isNeedNext()) {
            if (cha.items.get(0).isEmptyAttr() && cha.items.get(0).occures.isAllInDialogs()) 
                spInd = 0;
            if (gen == com.pullenti.morph.MorphGender.UNDEFINED) 
                gen = cha.getGender();
            int no1 = cha.items.get(0).getNominativeCoef(t.beginChar, t.endChar, gen);
            int no2 = cha.items.get(1).getNominativeCoef(t.beginChar, t.endChar, gen);
            int ge2 = cha.items.get(1).getGenetiveCoef(t.beginChar, t.endChar, gen);
            if (no1 > 0 && no2 > 0) {
                if ((ge2 < 1) || cha.items.get(0).anaforRef != null) {
                }
                else if (!isNorm && ge2 > 0) 
                    spInd = 0;
            }
            else if (no1 > 0 && ge2 > 0 && no2 == 0) 
                spInd = 0;
            i0 = 1;
        }
        if ((cha.totalCount < 5) && (spInd < 0)) {
            for(int ii = i0; ii < (cha.items.size() - 1); ii++) {
                if (cha.items.get(ii).isNeedNext() || cha.items.get(ii + 1).isNeedPrev()) 
                    continue;
                int g1 = cha.items.get(ii).getGenderCoef(t.beginChar, t.endChar, gen);
                int g2 = cha.items.get(ii + 1).getGenderCoef(t.beginChar, t.endChar, gen);
                if (g2 == 0 && g1 > 0) {
                    spInd = ii;
                    break;
                }
                int no1 = cha.items.get(ii).getNominativeCoef(t.beginChar, t.endChar, gen);
                int no2 = cha.items.get(ii + 1).getNominativeCoef(t.beginChar, t.endChar, gen);
                if (no1 >= 0 && no2 >= 0) {
                    if (no1 != no2) {
                        spInd = ii;
                        break;
                    }
                    if (no1 == 0 && cha.totalCount == 1) {
                        spInd = ii;
                        break;
                    }
                }
                int ge1 = cha.items.get(ii).getGenetiveCoef(t.beginChar, t.endChar, gen);
                int ge2 = cha.items.get(ii + 1).getGenetiveCoef(t.beginChar, t.endChar, gen);
                if (ge1 >= 0 && ge2 >= 0 && no1 != 1) {
                    if (ge1 != ge2) {
                        if (cha.items.get(ii + 1).isCanBeMiddleName()) {
                        }
                        else {
                            spInd = ii;
                            break;
                        }
                    }
                }
                if (!cha.items.get(ii).isAttr()) {
                    if (((cha.totalCount * 20) < cha.items.get(ii).item.occures.getOccursCount()) && ((cha.totalCount * 20) < cha.items.get(ii + 1).item.occures.getOccursCount())) {
                        spInd = ii;
                        break;
                    }
                }
            }
        }
        if (spInd < 0) 
            return null;
        CharItemToken oc1 = cha.items.get(spInd).occures.findOccure(t.beginChar, t.endChar);
        CharItemToken oc2 = cha.items.get(spInd + 1).occures.findOccure(t.beginChar, t.endChar);
        if (oc1 == null || oc2 == null) 
            return null;
        int i;
        java.util.ArrayList<CharItemVar> tmp0 = new java.util.ArrayList< >();
        java.util.ArrayList<CharItemVar> tmp1 = new java.util.ArrayList< >();
        for(i = 0; i <= spInd; i++) {
            tmp0.add(cha.items.get(i));
        }
        for(; i < cha.items.size(); i++) {
            tmp1.add(cha.items.get(i));
        }
        CharacterVariant cha0 = m_VarCnt.register(tmp0);
        CharacterVariant cha1 = m_VarCnt.register(tmp1);
        int end = t.endChar;
        com.pullenti.ner.MorphCollection mc = t.getMorph();
        t = t.kit.debedToken(t);
        com.pullenti.ner.ReferentToken rt0 = com.pullenti.ner.ReferentToken._new709(cha0, t, oc1.getEndToken(), mc);
        t.kit.embedToken(rt0);
        if (beginChar == t.beginChar) 
            setBeginToken(rt0);
        com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(cha1, oc2.getBeginToken(), oc2.getEndToken(), oc2.getMorph());
        for(com.pullenti.ner.Token tt = oc2.getEndToken(); tt != null && tt.endChar <= end; tt = tt.getNext()) {
            rt1.setEndToken(tt);
        }
        t.kit.embedToken(rt1);
        if (endChar == rt1.endChar) 
            setEndToken(rt1);
        return rt0;
    }

    private com.pullenti.ner.Token _analyzeAnaforAndNextMerge(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        CharacterVariant var = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
        if (var == null) 
            return null;
        CharItemVar a = (var.items.get(0).isAttr() ? var.items.get(0) : null);
        if (a != null && a.anaforRef != null) {
            CharacterEx acha = LitHelper.findAnafor(t, a.anaforRef, beginChar);
            if (acha != null) {
                a.anaforRef = null;
                a.setRefCharacter(acha);
                if (a.getRefCharacter().real != null) 
                    a.setRefCharacter(a.getRefCharacter().real);
            }
        }
        com.pullenti.ner.Token mergeWithNext = null;
        if (a != null && ((a.isCanBePersonAfter() || a.isAnimal()))) {
            if (a.anaforRef == null && a.getRefCharacter() == null) {
                if (t.getNext() != null && (t.getNext().getReferent() instanceof CharacterEx)) {
                    if (!t.getNext().getMorph().getCase().isNominative()) {
                        a.setRefCharacter((CharacterEx)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), CharacterEx.class));
                        if (a.getRefCharacter().real != null) 
                            a.setRefCharacter(a.getRefCharacter().real);
                        mergeWithNext = t.getNext();
                    }
                }
            }
        }
        return mergeWithNext;
    }

    public void middleAnalyze(java.util.ArrayList<CharacterEx> result) {
        java.util.ArrayList<CharacterEx> eq = new java.util.ArrayList< >();
        for(CharacterEx ch : characters) {
            eq.clear();
            for(CharacterEx r : result) {
                if (r.canBeMergedByResult(ch)) {
                    if (!r.canNotBeEqual(ch)) 
                        eq.add(r);
                }
            }
            if (eq.size() == 1) {
                eq.get(0).mergeWith(ch);
                continue;
            }
        }
        for(CharacterEx ch : characters) {
            if (ch.real == null) 
                result.add(ch);
        }
    }

    public void finalAnalyze(java.util.ArrayList<CharacterEx> result) {
        for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            CharacterEx cha = (CharacterEx)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterEx.class);
            if (cha != null) {
                com.pullenti.ner.Token tt = t.getPrevious();
                if (tt != null && tt.isComma()) 
                    tt = tt.getPrevious();
                if ((tt instanceof com.pullenti.ner.ReferentToken) && !((com.pullenti.morph.MorphCase.ooBitand(tt.getMorph().getCase(), t.getMorph().getCase()))).isUndefined()) {
                    if (tt.getNext() == t && t.getMorph().getCase().isGenitive()) 
                        continue;
                    CharacterEx cha0 = (CharacterEx)com.pullenti.n2j.Utils.cast(tt.getReferent(), CharacterEx.class);
                    if (!((t.getNext() instanceof com.pullenti.ner.TextToken)) || t.getNext().chars.isLetter()) 
                        continue;
                    if (cha0 == null) {
                        CharacterVariant var0 = (CharacterVariant)com.pullenti.n2j.Utils.cast(tt.getReferent(), CharacterVariant.class);
                        if (var0 != null && var0.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                            cha0 = CharacterEx._new1466(var0.getGender());
                            cha0.addVariant(var0);
                        }
                    }
                    if (cha0 != null && !cha.canNotBeEqual(cha0)) {
                        if (cha.names.size() == 0 || cha0.names.size() == 0) {
                            cha.mergeWith(cha0);
                            com.pullenti.ner.ReferentToken rt0 = com.pullenti.ner.ReferentToken._new709(cha, tt, t, tt.getMorph());
                            kit.embedToken(rt0);
                            t = rt0;
                            if (t.getPrevious() != null) 
                                t = t.getPrevious();
                        }
                    }
                }
                continue;
            }
            CharacterVariant var = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
            if (var != null && !var.isError && var.items.size() > 0) {
                if (var.items.size() == 1 && var.items.get(0).isAttr()) {
                    if (var.items.get(0).isEmptyAttr() || var.items.get(0).isEmoAttr()) {
                        if (var.items.get(0).anaforRef == null && var.items.get(0).getRefCharacter() == null) 
                            continue;
                    }
                }
                com.pullenti.morph.MorphGender gen = var.getGender();
                if (!_isDefGender(gen) && _isDefGender(t.getMorph().getGender())) 
                    gen = t.getMorph().getGender();
                com.pullenti.ner.Token mergeWithNext = _analyzeAnaforAndNextMerge(t);
                java.util.ArrayList<CharacterEx> eq = null;
                CharacterEx cex = CharacterEx._new1466(gen);
                cex.addVariant(var);
                for(CharacterEx r : result) {
                    if (r.canBeEqual(var, gen)) {
                        if (r.canNotBeEqual(cex)) 
                            continue;
                        if (eq == null) 
                            eq = new java.util.ArrayList< >();
                        eq.add(r);
                    }
                }
                if (eq == null) 
                    continue;
                if (eq.size() == 1) {
                    cha = eq.get(0);
                    if (var.items.size() == 1 && var.items.get(0).isAttr()) {
                        if (cha.names.size() > 0) 
                            continue;
                    }
                    cha.notNorms.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
                    cha.addVariant(var);
                    (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).referent = cha;
                    if (mergeWithNext != null) {
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new709(cha, t, mergeWithNext, t.getMorph());
                        t.kit.embedToken(rt);
                        t = rt;
                    }
                    if (t.getPrevious() != null) 
                        t = t.getPrevious();
                    continue;
                }
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.getMorph()._getClass().isPersonalPronoun()) {
                    String a = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    if (com.pullenti.n2j.Utils.stringsEq(a, "ОН") || com.pullenti.n2j.Utils.stringsEq(a, "ОНА") || com.pullenti.n2j.Utils.stringsEq(a, "ОНО")) {
                        cha = LitHelper.findAnafor(t, t, beginChar);
                        if (cha != null) {
                            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(cha, t, t, null);
                            cha.anafors.add(rt);
                            t.kit.embedToken(rt);
                            t = rt;
                            continue;
                        }
                    }
                }
            }
        }
    }

    public static ChapterTextToken _new1550(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        ChapterTextToken res = new ChapterTextToken(_arg1, _arg2);
        res.caption = _arg3;
        return res;
    }
    public ChapterTextToken() {
        super();
    }
}
