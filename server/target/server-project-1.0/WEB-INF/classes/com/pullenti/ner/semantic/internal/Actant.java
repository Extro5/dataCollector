/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

/**
 * Поддержка анализа актантов у предикатов
 */
public class Actant {

    public SynToken obj;

    public SynToken act;

    public int distCoef;

    public boolean getObjBeforeAct() {
        return obj.beginChar < act.beginChar;
    }


    public boolean as;

    public int agentCoef;

    public com.pullenti.morph.MorphBaseInfo agentMorph;

    public int pacientCoef;

    public com.pullenti.morph.MorphBaseInfo pacientMorph;

    public int operandCoef;

    public com.pullenti.morph.MorphBaseInfo operandMorph;

    public boolean isNeibour() {
        if ((obj.beginChar < act.beginChar) && obj.getEndToken().getNext() == act.getBeginToken()) 
            return true;
        if (obj.beginChar > act.beginChar && obj.getBeginToken().getPrevious() == act.getEndToken()) 
            return true;
        return false;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append((getObjBeforeAct() ? "OA" : "AO"));
        res.append(" (d").append(distCoef).append(")");
        if (agentCoef > 0) 
            res.append(" A").append(agentCoef);
        if (pacientCoef > 0) 
            res.append(" P").append(pacientCoef);
        if (operandCoef > 0) 
            res.append(" O").append(operandCoef);
        if (as) 
            res.append(" как");
        res.append(" '").append(obj.getSourceText()).append("' + '").append(act.getSourceText()).append("'");
        return res.toString();
    }

    public static Actant tryCreate(SynToken _obj, SynToken _act) {
        if (!_obj.isActant() || !_act.isPredicate()) 
            return null;
        Actant res = _new2469(_obj, _act);
        if (_obj.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) {
            if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(_obj.getBeginToken().getPrevious(), com.pullenti.ner.TextToken.class))).term, "КАК")) 
                res.as = true;
        }
        java.util.ArrayList<com.pullenti.morph.DerivateWord> ews = _act.getExplainInfo();
        String pref = (String)com.pullenti.n2j.Utils.notnull(_obj.preposition, "");
        boolean operAfter = false;
        boolean instrAfter = false;
        for(com.pullenti.morph.DerivateWord ew : ews) {
            if (ew.nexts != null) {
                com.pullenti.morph.MorphCase cas;
                com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphCase> inoutarg2470 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres2471 = com.pullenti.n2j.Utils.tryGetValue(ew.nexts, pref, inoutarg2470);
                cas = inoutarg2470.value;
                if (!inoutres2471) 
                    continue;
                if (pref.length() > 0) {
                    if (!cas.isUndefined()) {
                        res.operandMorph = _obj.getMorph().findItem(cas, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                        res.operandCoef = 2;
                    }
                    else 
                        res.operandCoef = 2;
                    return res;
                }
                if (!((com.pullenti.morph.MorphCase.ooBitand(cas, _obj.getMorph().getCase()))).isUndefined()) {
                    if (res.operandMorph == null) {
                        com.pullenti.morph.MorphBaseInfo mo = null;
                        if (cas.isGenitive() && mo == null) 
                            mo = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                        if (cas.isDative() && mo == null) 
                            mo = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.DATIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                        if (cas.isInstrumental() && mo == null) 
                            mo = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.INSTRUMENTAL, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                        if (cas.isAccusative() && mo == null) 
                            mo = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.ACCUSATIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                        if (mo == null) 
                            mo = _obj.getMorph().findItem(cas, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                        res.operandMorph = mo;
                    }
                    res.operandCoef = (com.pullenti.morph.MorphCase.ooEq((com.pullenti.morph.MorphCase.ooBitand(cas, _obj.getMorph().getCase())), _obj.getMorph().getCase()) ? 2 : 1);
                    operAfter = true;
                    if (((com.pullenti.morph.MorphCase.ooBitand(cas, _obj.getMorph().getCase()))).isInstrumental()) 
                        instrAfter = true;
                }
            }
        }
        if (_obj.preposition != null) {
            res.operandCoef = 1;
            com.pullenti.morph.MorphCase cas = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(pref);
            if (!cas.isUndefined()) 
                res.operandMorph = _obj.getMorph().findItem(cas, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
            return res;
        }
        if (_act.isPredicateInfinitive()) {
            if (_obj.getMorph().getCase().isDative()) {
                if (res.operandMorph != null && res.operandMorph.getCase().isDative()) {
                    res.pacientCoef = 2;
                    res.pacientMorph = res.operandMorph;
                }
                else {
                    res.agentCoef = 1;
                    res.agentMorph = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.DATIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                }
            }
        }
        else {
            boolean hasAcc = false;
            for(com.pullenti.morph.MorphBaseInfo ot : _obj.getMorph().getItems()) {
                for(com.pullenti.morph.MorphBaseInfo at : _act.getMorph().getItems()) {
                    if (ot.checkAccord(at, false)) {
                        hasAcc = true;
                        if (_act.typ == Types.ACT) {
                            if (!ot.getCase().isUndefined()) {
                                if (!ot.getCase().isNominative()) 
                                    continue;
                            }
                        }
                        if (_act.getMorph().getVoice() == com.pullenti.morph.MorphVoice.PASSIVE) {
                            res.pacientCoef = 2;
                            res.pacientMorph = ot;
                            if (_act.typ == Types.ACT) {
                                if (!ot.getCase().isNominative()) 
                                    res.pacientCoef = 1;
                            }
                        }
                        else {
                            res.agentCoef = 2;
                            res.agentMorph = ot;
                            if (_act.typ == Types.ACT) {
                                if (!ot.getCase().isNominative()) 
                                    res.agentCoef = 1;
                            }
                        }
                    }
                }
            }
            if (!hasAcc && _obj.getMorph().getCase().isUndefined() && _obj.ref != null) {
                if (_act.getMorph().getVoice() == com.pullenti.morph.MorphVoice.PASSIVE) 
                    res.pacientCoef = 1;
                else 
                    res.agentCoef = 1;
            }
        }
        if (_act.getMorph().getVoice() == com.pullenti.morph.MorphVoice.PASSIVE) {
            if (res.agentCoef == 0 && _obj.getMorph().getCase().isInstrumental()) {
                res.agentCoef = (com.pullenti.morph.MorphCase.ooEq(_obj.getMorph().getCase(), com.pullenti.morph.MorphCase.INSTRUMENTAL) ? 2 : 1);
                res.agentMorph = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.INSTRUMENTAL, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
            }
        }
        else if (_act.isPredicateBe() && _act.typ == Types.ACTPRICH) {
        }
        else if (_obj.getMorph().getCase().isGenitive() || _obj.getMorph().getCase().isAccusative()) {
            if (res.pacientCoef == 0) {
                res.pacientCoef = 1;
                res.pacientMorph = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.ooBitor(com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphCase.ACCUSATIVE), com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
                if (res.operandMorph != null && ((res.operandMorph.getCase().isGenitive() || res.operandMorph.getCase().isAccusative()))) {
                    res.pacientCoef = res.operandCoef;
                    res.pacientMorph = res.operandMorph;
                }
            }
            else if (operAfter) {
                res.pacientCoef = res.operandCoef;
                res.operandCoef = 0;
                res.pacientMorph = res.operandMorph;
            }
        }
        else if (((_obj.getMorph().getCase().isInstrumental() && ((_act.isPredicateBe() || _act.isPredicateSelf())))) || instrAfter) {
            if (_obj.getMorph()._getClass().isPersonalPronoun() && res.operandMorph != null && res.operandMorph.getCase().isDative()) {
                res.pacientCoef = res.operandCoef;
                res.pacientMorph = res.operandMorph;
            }
            else {
                res.pacientCoef = 1;
                res.pacientMorph = _obj.getMorph().findItem(com.pullenti.morph.MorphCase.INSTRUMENTAL, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED);
            }
        }
        if (res.as) {
            if (_act.typ == Types.ACT) {
                res.agentCoef = 0;
                res.pacientCoef = 0;
            }
        }
        if (res.operandCoef == 0 && res.agentCoef == 0 && res.pacientCoef == 0) 
            res.operandCoef = 1;
        return res;
    }

    private static SynToken _createActant(SynToken _obj, com.pullenti.ner.semantic.ActantRole rol, com.pullenti.morph.MorphBaseInfo _morph, boolean checkAs) {
        if (rol == com.pullenti.ner.semantic.ActantRole.SENTACTANT) {
        }
        if (_obj.typ == Types.ACTANT) {
            if (rol != com.pullenti.ner.semantic.ActantRole.UNDEFINED) 
                _obj.rol = rol;
            return _obj;
        }
        SynToken sy = SynToken._new2472(_obj.getBeginToken(), _obj.getEndToken(), Types.ACTANT, rol, _obj.getMorph());
        if (_morph != null) {
            sy.setMorph(new com.pullenti.ner.MorphCollection(null));
            sy.getMorph().addItem(_morph);
            if (_obj.typ == Types.PRONOUNOBJ) 
                _obj.setMorph(sy.getMorph());
        }
        if (((_obj.typ2 == Types.NUMBER || _obj.typ == Types.NUMBER)) && _obj.children.size() > 0) {
            sy.addChildren(_obj.children);
            for(SynToken ch : _obj.children) {
                for(java.util.Map.Entry<String, ValTypes> v : ch.vals.entrySet()) {
                    if (v.getValue() == ValTypes.ACTANTPROP) 
                        sy.addVal(v.getKey(), ValTypes.PROP);
                }
            }
        }
        else if (_obj.isContainer()) {
            sy.addChildren(_obj.children);
            for(SynToken ch : _obj.children) {
                for(java.util.Map.Entry<String, ValTypes> v : ch.vals.entrySet()) {
                    if (v.getValue() == ValTypes.ACTANTPROP) 
                        sy.addVal(v.getKey(), ValTypes.PROP);
                }
            }
        }
        else {
            sy.addChild(_obj);
            if (_obj.getVal(ValTypes.NUMBER) == null) {
                if (sy.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                    sy.addVal("1..*", ValTypes.NUMBER);
                    _obj.getMorph().removeItems(com.pullenti.morph.MorphNumber.PLURAL);
                }
                else if (_obj.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    sy.addVal("1..*", ValTypes.NUMBER);
            }
        }
        if (_obj.preposition != null) 
            sy.preposition = _obj.preposition;
        for(java.util.Map.Entry<String, ValTypes> v : _obj.vals.entrySet()) {
            if (v.getValue() == ValTypes.ACTANTPROP) 
                sy.addVal(v.getKey(), ValTypes.PROP);
            else if (v.getValue() == ValTypes.NUMBER) 
                sy.addVal(v.getKey(), ValTypes.NUMBER);
        }
        if (_obj.getVal(ValTypes.NUMBER) != null) 
            _obj.delVals(ValTypes.NUMBER);
        if (checkAs && (sy.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) && ((com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(sy.getBeginToken().getPrevious(), com.pullenti.ner.TextToken.class))).term, "КАК") || (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(sy.getBeginToken().getPrevious(), com.pullenti.ner.TextToken.class))).term, "ЯК") && sy.getBeginToken().getPrevious().getMorph().getLanguage().isUa())))) {
            sy.setBeginToken(sy.getBeginToken().getPrevious());
            sy.addVal((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(sy.getBeginToken(), com.pullenti.ner.TextToken.class))).term, ValTypes.PROP);
        }
        return sy;
    }

    public static void process(java.util.ArrayList<SynToken> li) {
        if (li.size() > 30) {
            int crlf = 0;
            int prds = 0;
            for(SynToken s : li) {
                if (s.isNewlineBefore() && s != li.get(0)) 
                    crlf++;
                if (s.isPredicate()) 
                    prds++;
            }
            java.util.ArrayList<SynToken> tmp = new java.util.ArrayList< >();
            for(int ii = 0; ii < li.size(); ii++) {
                if (ii > 0 && li.get(ii).isNewlineBefore()) {
                    process(tmp);
                    tmp.clear();
                }
                else if (tmp.size() >= 25) {
                    process(tmp);
                    tmp.clear();
                }
                tmp.add(li.get(ii));
            }
            if (tmp.size() > 0) 
                process(tmp);
            return;
        }
        java.util.ArrayList<SynToken> acts = new java.util.ArrayList< >();
        java.util.ArrayList<Actant> aa = new java.util.ArrayList< >();
        int i;
        for(i = 0; i < li.size(); i++) {
            if (li.get(i).isPredicate()) {
                acts.add(li.get(i));
                for(int j = 0; j < li.size(); j++) {
                    if (!li.get(j).isActant()) 
                        continue;
                    Actant a = tryCreate(li.get(j), li.get(i));
                    if (a == null) 
                        continue;
                    aa.add(a);
                    int i0 = (i < j ? i : j);
                    int i1 = (i < j ? j : i);
                    a.distCoef = 0;
                    for(int k = i0 + 1; k < i1; k++) {
                        if (li.get(k).typ == Types.ACTPRICH || li.get(k).typ == Types.ACT) 
                            a.distCoef += 5;
                        else if (li.get(k).isConjOrComma() || li.get(k).typ == Types.WHAT) 
                            a.distCoef++;
                    }
                }
            }
        }
        if (acts.size() == 0) 
            return;
        for(i = 1; i < (li.size() - 1); i++) {
            if ((li.get(i - 1).isActant() && li.get(i - 1).getEndToken().getNext() == li.get(i).getBeginToken() && li.get(i - 1).children.size() == 0) && li.get(i).typ == Types.ACTPRICH && !li.get(i).isPredicateBe()) {
                for(int j = i + 1; j < li.size(); j++) {
                    if (li.get(j).isActant()) {
                        Actant a = tryCreate(li.get(j), li.get(i));
                        if (a != null && a.agentCoef > 0) {
                            int co = ObjectHelper.cancNextCoef(li.get(i - 1), li.get(j));
                            if (co > 0) {
                                li.get(i - 1).embed(li.get(j), false, 0);
                                for(int jj = aa.size() - 1; jj >= 0; jj--) {
                                    if (aa.get(jj).obj == li.get(i - 1) && aa.get(jj).act == li.get(i)) 
                                        aa.remove(jj);
                                    else {
                                        int oind = li.indexOf(aa.get(jj).obj);
                                        if (oind > i && oind <= j) {
                                            int pind = li.indexOf(aa.get(jj).act);
                                            if (pind > j) 
                                                aa.remove(jj);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                    else 
                        break;
                }
            }
        }
        for(Actant a : aa) {
            a.act.tag = null;
        }
        java.util.ArrayList<PredicateLinks> items = new java.util.ArrayList< >();
        for(Actant a : aa) {
            PredicateLinks pl = (PredicateLinks)com.pullenti.n2j.Utils.cast(a.act.tag, PredicateLinks.class);
            if (pl == null) {
                a.act.tag = (pl = PredicateLinks._new2473(a.act));
                items.add(pl);
            }
            if (a.agentCoef > 0) 
                pl.agents.add(a);
            if (a.pacientCoef > 0) 
                pl.pacients.add(a);
        }
        long vars = (long)1;
        for(PredicateLinks it : items) {
            vars *= ((long)(((it.agents.size() + 1)) * ((it.pacients.size() + 1))));
            if (vars >= ((long)0xFFFF)) 
                return;
        }
        float bestCoef = (float)0;
        if (items.size() > 0) {
            com.pullenti.n2j.Outargwrapper<Float> inoutarg2474 = new com.pullenti.n2j.Outargwrapper< >(bestCoef);
            _process((float)0, 0, items, inoutarg2474);
            bestCoef = inoutarg2474.value;
        }
        for(i = 0; i < items.size(); i++) {
            PredicateLinks it = items.get(i);
            if (it.bestAgent != null) {
                SynToken ac = _createActant(it.bestAgent.obj, com.pullenti.ner.semantic.ActantRole.AGENT, it.bestAgent.agentMorph, false);
                it.act.addChild(ac);
                it.bestAgent.obj._LastPredicate = it.act;
            }
            if (it.bestPacient != null) {
                SynToken ac = _createActant(it.bestPacient.obj, com.pullenti.ner.semantic.ActantRole.PATIENT, it.bestPacient.pacientMorph, false);
                it.act.addChild(ac);
                it.bestPacient.obj._LastPredicate = it.act;
            }
        }
        for(Actant a : aa) {
            if (a.obj._LastPredicate != null) 
                continue;
            Actant best = null;
            for(Actant aaa : aa) {
                if (aaa.obj == a.obj) {
                    if (best == null) 
                        best = aaa;
                    else if (aaa.distCoef < best.distCoef) 
                        best = aaa;
                    else if (aaa.act.isPredicateInfinitive() && !best.act.isPredicateInfinitive() && aaa.operandCoef > 0) 
                        best = aaa;
                }
            }
            if (best == null) 
                continue;
            SynToken ac = _createActant(a.obj, com.pullenti.ner.semantic.ActantRole.UNDEFINED, a.operandMorph, a.as);
            best.act.addChild(ac);
            a.obj._LastPredicate = best.act;
        }
        for(i = 1; i < acts.size(); i++) {
            if (acts.get(i).isPredicateInfinitive()) {
                for(int j = i - 1; j >= 0; j--) {
                    if (!acts.get(j).isPredicateInfinitive()) {
                        SynToken ac = _createActant(acts.get(i), com.pullenti.ner.semantic.ActantRole.SENTACTANT, null, false);
                        acts.get(j).addChild(ac);
                        acts.get(i)._LastPredicate = acts.get(j);
                        break;
                    }
                }
            }
        }
    }

    private static void _process(float coef, int i, java.util.ArrayList<PredicateLinks> items, com.pullenti.n2j.Outargwrapper<Float> bestCoef) {
        if (i >= items.size()) {
            if (coef > bestCoef.value) {
                bestCoef.value = coef;
                for(i = 0; i < items.size(); i++) {
                    items.get(i).bestAgent = items.get(i).getAgent();
                    items.get(i).bestPacient = items.get(i).getPacient();
                }
            }
            return;
        }
        PredicateLinks it = items.get(i);
        if (it.agents.size() == 0 && it.pacients.size() == 0) {
            _process(coef, i + 1, items, bestCoef);
            return;
        }
        for(it.aInd = 0; it.aInd <= it.agents.size(); it.aInd++) {
            float coef0 = coef;
            Actant a = it.getAgent();
            if (a != null) {
                if (!a.getObjBeforeAct()) {
                    int j;
                    for(j = i - 1; j >= 0; j--) {
                        if (items.get(j).getAgentObj() == a.obj || items.get(j).getPacientObj() == a.obj) 
                            break;
                    }
                    if (j >= 0) 
                        continue;
                    if (a.act.typ == Types.ACTPRICH) {
                        for(j = i - 1; j >= 0; j--) {
                            SynToken o = items.get(j).getAgentObj();
                            if (o != null && o.beginChar > a.act.endChar && (o.endChar < a.obj.beginChar)) 
                                break;
                            o = items.get(j).getPacientObj();
                            if (o != null && o.beginChar > a.act.endChar && (o.endChar < a.obj.beginChar)) 
                                break;
                        }
                        if (j >= 0) 
                            continue;
                    }
                }
                else if (((i + 1) < items.size()) && items.get(i + 1).act.isPredicateInfinitive()) {
                    boolean ex = false;
                    for(Actant p : items.get(i + 1).pacients) {
                        if (p.obj == a.obj && p.pacientCoef >= a.agentCoef) {
                            ex = true;
                            break;
                        }
                    }
                    if (ex) 
                        continue;
                }
                float mult = (float)(a.act.typ == Types.ACTPRICH ? 1 : 2);
                coef0 += ((float)a.agentCoef);
                if (a.isNeibour()) 
                    coef0 += mult;
                else 
                    coef0 += (mult / ((mult + ((float)a.distCoef))));
            }
            for(it.pInd = 0; it.pInd <= it.pacients.size(); it.pInd++) {
                if (it.getAgentObj() == it.getPacientObj() && a != null) 
                    continue;
                float coef1 = coef0;
                Actant p = it.getPacient();
                if (p != null) {
                    int j;
                    for(j = i - 1; j >= 0; j--) {
                        if (items.get(j).getAgentObj() == p.obj) {
                            if ((p.getObjBeforeAct() && j == (i - 1) && items.get(j).getAgent().getObjBeforeAct()) && !it.act.isPredicateInfinitive()) {
                            }
                            else 
                                break;
                        }
                        else if (items.get(j).getPacientObj() == p.obj) {
                            if (!items.get(j).getPacient().getObjBeforeAct()) 
                                break;
                        }
                    }
                    if (j >= 0) 
                        continue;
                    boolean ok = true;
                    for(j = i + 1; j < items.size(); j++) {
                        if (items.get(j).act.beginChar > p.obj.beginChar) 
                            break;
                        else if (items.get(j).act.isPredicateInfinitive()) {
                            ok = false;
                            break;
                        }
                    }
                    if (!ok) 
                        continue;
                    float mult = (float)(p.act.typ == Types.ACTPRICH ? 1 : 2);
                    if (p.isNeibour()) 
                        coef1 += mult;
                    else 
                        coef1 += (mult / ((mult + ((float)p.distCoef))));
                    if (a != null) 
                        coef1 += 0.5F;
                }
                _process(coef1, i + 1, items, bestCoef);
            }
        }
    }

    public static class PredicateLinks {
    
        public com.pullenti.ner.semantic.internal.SynToken act;
    
        public java.util.ArrayList<com.pullenti.ner.semantic.internal.Actant> agents = new java.util.ArrayList< >();
    
        public java.util.ArrayList<com.pullenti.ner.semantic.internal.Actant> pacients = new java.util.ArrayList< >();
    
        public com.pullenti.ner.semantic.internal.Actant getAgent() {
            return (aInd < agents.size() ? agents.get(aInd) : null);
        }
    
    
        public com.pullenti.ner.semantic.internal.Actant getPacient() {
            return (pInd < pacients.size() ? pacients.get(pInd) : null);
        }
    
    
        public com.pullenti.ner.semantic.internal.SynToken getAgentObj() {
            return (aInd < agents.size() ? agents.get(aInd).obj : null);
        }
    
    
        public com.pullenti.ner.semantic.internal.SynToken getPacientObj() {
            return (pInd < pacients.size() ? pacients.get(pInd).obj : null);
        }
    
    
        public int aInd;
    
        public int pInd;
    
        public com.pullenti.ner.semantic.internal.Actant bestAgent;
    
        public com.pullenti.ner.semantic.internal.Actant bestPacient;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append((getAgent() == null ? "нет" : com.pullenti.ner.semantic.internal.Actant._outObjActant_PredicateLinks(getAgent(), false)));
            if (getAgent() == bestAgent) 
                tmp.append(" (!)");
            else if (bestAgent != null) 
                tmp.append(" (").append(com.pullenti.ner.semantic.internal.Actant._outObjActant_PredicateLinks(bestAgent, false)).append(")");
            tmp.append(" + '").append(act.getSourceText()).append("' + ");
            tmp.append((getPacient() == null ? "нет" : com.pullenti.ner.semantic.internal.Actant._outObjActant_PredicateLinks(getPacient(), true)));
            if (getPacient() == bestPacient) 
                tmp.append(" (!)");
            else if (bestPacient != null) 
                tmp.append(" (").append(com.pullenti.ner.semantic.internal.Actant._outObjActant_PredicateLinks(bestPacient, true)).append(")");
            return tmp.toString();
        }
    
        public static PredicateLinks _new2473(com.pullenti.ner.semantic.internal.SynToken _arg1) {
            PredicateLinks res = new PredicateLinks();
            res.act = _arg1;
            return res;
        }
        public PredicateLinks() {
        }
    }


    private static String _outObjActant_PredicateLinks(Actant a, boolean pac) {
        return ((Integer)(pac ? a.pacientCoef : a.agentCoef)).toString() + " '" + a.obj.getSourceText() + "'";
    }

    public static Actant _new2469(SynToken _arg1, SynToken _arg2) {
        Actant res = new Actant();
        res.obj = _arg1;
        res.act = _arg2;
        return res;
    }
    public Actant() {
    }
    public static Actant _globalInstance;
    static {
        _globalInstance = new Actant(); 
    }
}
