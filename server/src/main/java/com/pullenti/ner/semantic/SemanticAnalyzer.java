/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Семантический анализатор выделения персон
 */
public class SemanticAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "SEMANTIC";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Семантический анализ";
    }


    @Override
    public String getDescription() {
        return "Выделение семантических объекты";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new SemanticAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.semantic.internal.MetaObject.globalMeta, com.pullenti.ner.semantic.internal.MetaSentence.globalMeta, com.pullenti.ner.semantic.internal.MetaActant.globalMeta, com.pullenti.ner.semantic.internal.MetaPredicate.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap< >();
        res.put(com.pullenti.ner.semantic.internal.MetaObject.OBJIMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("obj.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaObject.OBJPRONOUNIMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("objref.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaObject.OBJNAMEIMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("objname.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaObject.NUMIMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("num.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaObject.TIMEIMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("time.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaSentence.IMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("sent.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaSentence.ANNOIMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("press.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaActant.IMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("actant.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaActant.IMAGEAGENTID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("actant_agent.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaActant.IMAGEPATIENTID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("actant_patient.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaActant.IMAGESENTACTANTID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("actant_sent.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaActant.IMAGETIMEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("time.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaActant.IMAGEOBJID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("actant_obj.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaPredicate.IMAGEID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("act.png"));
        res.put(com.pullenti.ner.semantic.internal.MetaPredicate.IMAGEPARTID, com.pullenti.ner.semantic.internal.ResourceHelper.getBytes("act_part.png"));
        return res;
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"ALL"});
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, ObjectReferent.OBJ_TYPENAME)) 
            return new ObjectReferent();
        if (com.pullenti.n2j.Utils.stringsEq(type, SentenceReferent.OBJ_TYPENAME)) 
            return new SentenceReferent();
        if (com.pullenti.n2j.Utils.stringsEq(type, ActantReferent.OBJ_TYPENAME)) 
            return new ActantReferent();
        if (com.pullenti.n2j.Utils.stringsEq(type, PredicateReferent.OBJ_TYPENAME)) 
            return new PredicateReferent();
        return null;
    }

    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public int getProgressWeight() {
        return 30;
    }


    /**
     * Не выделять действия
     */
    public boolean ignoreActions = false;

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        int max = 0;
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            max++;
        }
        max *= 5;
        int delta = 1000;
        int cur = 0;
        int nextPos = delta;
        java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> all = new java.util.ArrayList< >();
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            cur++;
            if (t.beginChar > nextPos) {
                nextPos += delta;
                if (!onProgress(cur, max, kit)) 
                    return;
            }
            if ((t instanceof com.pullenti.ner.TextToken) && !t.chars.isLetter()) {
                if (t.getNext() == null || com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t.getNext())) {
                    all.add(com.pullenti.ner.semantic.internal.SynToken._new2480(t, t, com.pullenti.ner.semantic.internal.Types.SEQEND));
                    continue;
                }
            }
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                if (all.size() > 0 && all.get(all.size() - 1).typ == com.pullenti.ner.semantic.internal.Types.SEQEND) {
                }
                else if (t.getPrevious() != null) 
                    all.add(com.pullenti.ner.semantic.internal.SynToken._new2480(t.getPrevious(), t.getPrevious(), com.pullenti.ner.semantic.internal.Types.SEQEND));
            }
            com.pullenti.ner.semantic.internal.SynToken sy = com.pullenti.ner.semantic.internal.SynToken.tryParse(t, all);
            if (sy == null && t.getMorph()._getClass().isPreposition()) {
                sy = com.pullenti.ner.semantic.internal.SynToken.tryParse(t.getNext(), all);
                if (sy != null) {
                    sy.setBeginToken(t);
                    sy.preposition = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                }
            }
            if (sy == null) 
                continue;
            all.add(sy);
            if (sy.tag instanceof com.pullenti.ner.semantic.internal.SynToken) 
                all.add((com.pullenti.ner.semantic.internal.SynToken)com.pullenti.n2j.Utils.cast(sy.tag, com.pullenti.ner.semantic.internal.SynToken.class));
            t = sy.getEndToken();
        }
        com.pullenti.ner.semantic.internal.History hist = new com.pullenti.ner.semantic.internal.History();
        com.pullenti.ner.semantic.internal.SentenceContainer sc = new com.pullenti.ner.semantic.internal.SentenceContainer();
        java.util.ArrayList<com.pullenti.ner.semantic.internal.Paragraph> paras = new java.util.ArrayList< >();
        com.pullenti.ner.semantic.internal.Paragraph para = new com.pullenti.ner.semantic.internal.Paragraph();
        java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> prevObjs = new java.util.ArrayList< >();
        for(int ii = 0; ii < all.size(); ii++) {
            if (!onProgress(max - ((((all.size() - ii - 1)) * 4)), max, kit)) 
                break;
            if (all.get(ii).typ == com.pullenti.ner.semantic.internal.Types.SEQEND) 
                continue;
            int j = sc._do(all, ii);
            if ((j < 0) || sc.baseSent.size() == 0) 
                continue;
            com.pullenti.ner.Token t0 = all.get(ii).getBeginToken();
            ii = j - 1;
            com.pullenti.ner.Token t1 = all.get(ii).getEndToken();
            if (t0.isNewlineBefore()) {
                if (para.sents.size() > 0) {
                    paras.add(para);
                    para = new com.pullenti.ner.semantic.internal.Paragraph();
                    prevObjs.clear();
                }
            }
            java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> syns = com.pullenti.ner.semantic.internal.SynHelper.analyzeSent(sc.baseSent, hist, ignoreActions);
            if (syns == null || syns.size() == 0) 
                continue;
            java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> li = new java.util.ArrayList< >();
            for(com.pullenti.ner.semantic.internal.SynToken syn : syns) {
                _getOrderedList(syn, li, 0);
            }
            if (li.size() == 0) 
                continue;
            for(java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> slavs : sc.slaveSents) {
                java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> syns1 = com.pullenti.ner.semantic.internal.SynHelper.analyzeSent(slavs, hist, ignoreActions);
                if (syns1 == null || syns1.size() == 0) 
                    continue;
                java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> li1 = new java.util.ArrayList< >();
                for(com.pullenti.ner.semantic.internal.SynToken syn : syns1) {
                    _getOrderedList(syn, li1, 0);
                }
                if (li1.size() == 0) 
                    continue;
                com.pullenti.ner.semantic.internal.Anafor.process(li1, null, li);
                com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Token> inoutarg2577 = new com.pullenti.n2j.Outargwrapper< >(t0);
                com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Token> inoutarg2578 = new com.pullenti.n2j.Outargwrapper< >(t1);
                embedItems(para.data, li1, inoutarg2577, inoutarg2578);
                t0 = inoutarg2577.value;
                t1 = inoutarg2578.value;
                hist.add(li1);
                for(com.pullenti.ner.semantic.internal.SynToken syn : syns) {
                    if (syn.typ == com.pullenti.ner.semantic.internal.Types.ACT) {
                        for(com.pullenti.ner.semantic.internal.SynToken syn1 : syns1) {
                            if ((syn1.real instanceof PredicateReferent) && syn1.rol != ActantRole.SENTACTANT && syn1._LastPredicate == null) {
                                com.pullenti.ner.semantic.internal.SynToken sact = com.pullenti.ner.semantic.internal.SynToken._new2482(syn1.getBeginToken(), syn1.getEndToken(), com.pullenti.ner.semantic.internal.Types.ACTANT, ActantRole.SENTACTANT);
                                sact.addChild(syn1);
                                syn.addChild(sact);
                            }
                        }
                        syn.setBeginToken(t0);
                        syn.setEndToken(t1);
                        break;
                    }
                }
            }
            com.pullenti.ner.semantic.internal.Anafor.process(li, prevObjs, null);
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Token> inoutarg2580 = new com.pullenti.n2j.Outargwrapper< >(t0);
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Token> inoutarg2581 = new com.pullenti.n2j.Outargwrapper< >(t1);
            embedItems(para.data, li, inoutarg2580, inoutarg2581);
            t0 = inoutarg2580.value;
            t1 = inoutarg2581.value;
            hist.add(li);
            for(com.pullenti.ner.semantic.internal.SynToken ll : li) {
                if ((ll.real instanceof ObjectReferent) || (ll.real instanceof ActantReferent)) 
                    prevObjs.add(ll);
            }
            SentenceReferent sent = new SentenceReferent();
            for(com.pullenti.ner.semantic.internal.SynToken l : li) {
                if (l.real != null && !((l.real instanceof ActantReferent))) 
                    sent.addSlot(SentenceReferent.ATTR_REF, l.real, false, 0);
            }
            com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(sent, t0, t1, null);
            sent.setContent(rt0.getSourceText());
            para.sents.add(rt0);
            if (sc.slaveSents.size() > 0 && li.size() > 0) 
                sent.ignoredPart = com.pullenti.ner.TextAnnotation._new479(kit.getSofa(), li.get(0).beginChar, li.get(li.size() - 1).endChar);
        }
        if (para.sents.size() > 0) 
            paras.add(para);
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> objs = new java.util.HashMap< >();
        for(com.pullenti.ner.semantic.internal.Paragraph p : paras) {
            p.process(ad, objs);
        }
        SentenceReferent anno = com.pullenti.ner.semantic.internal.AnnoHelper.createAnnotation(ad.getReferents());
        if (anno != null) 
            ad.getReferents().add(anno);
    }

    private void embedItems(com.pullenti.ner.core.AnalyzerData ad, java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> li, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Token> t0, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Token> t1) {
        java.util.HashMap<Integer, com.pullenti.ner.Token> startChars = new java.util.HashMap< >();
        java.util.HashMap<Integer, com.pullenti.ner.Token> endChars = new java.util.HashMap< >();
        for(int i = 0; i < li.size(); i++) {
            com.pullenti.ner.Referent real = null;
            if (li.get(i).real != null) {
                if (li.get(i).anaforRef0 != null) 
                    real = li.get(i).real;
                else 
                    continue;
            }
            else if ((li.get(i).typ == com.pullenti.ner.semantic.internal.Types.OBJ || li.get(i).typ == com.pullenti.ner.semantic.internal.Types.PROPERNAME || li.get(i).typ == com.pullenti.ner.semantic.internal.Types.WHAT) || li.get(i).typ == com.pullenti.ner.semantic.internal.Types.PRONOUNOBJ) {
                com.pullenti.ner.semantic.internal.SynToken stok = li.get(i);
                if (li.get(i).getAnaforRef() != null && li.get(i).getAnaforRef().real == null) 
                    stok = li.get(i).getAnaforRef();
                if (li.get(i).getAnaforRef() != null && li.get(i).getAnaforRef().real != null) 
                    real = li.get(i).getAnaforRef().real;
                else if (li.get(i).ref instanceof ObjectReferent) 
                    real = li.get(i).ref;
                else if (li.get(i).typ != com.pullenti.ner.semantic.internal.Types.WHAT) {
                    ObjectReferent so = ObjectReferent._new2582(SemanticKind.OBJECT);
                    real = so;
                    if (li.get(i).typ == com.pullenti.ner.semantic.internal.Types.PRONOUNOBJ) 
                        so.setKind(SemanticKind.PRONOUN);
                    if (stok.ref != null) 
                        so.setProxy(stok.ref);
                    for(java.util.Map.Entry<String, com.pullenti.ner.semantic.internal.ValTypes> kp : stok.vals.entrySet()) {
                        if (kp.getValue() == com.pullenti.ner.semantic.internal.ValTypes.BASE) 
                            so.setBase(kp.getKey());
                        else if (kp.getValue() == com.pullenti.ner.semantic.internal.ValTypes.PROP) 
                            so.addSlot(ObjectReferent.ATTR_PROP, kp.getKey(), false, 0);
                        else if (kp.getValue() == com.pullenti.ner.semantic.internal.ValTypes.ALIAS) 
                            so.addAlias(kp.getKey());
                        else if (kp.getValue() == com.pullenti.ner.semantic.internal.ValTypes.NAME) {
                            if (li.get(i).typ == com.pullenti.ner.semantic.internal.Types.PROPERNAME || li.get(i).typ2 == com.pullenti.ner.semantic.internal.Types.PROPERNAME) 
                                so.addName(kp.getKey());
                        }
                    }
                    if (li.get(i).typ == com.pullenti.ner.semantic.internal.Types.PRONOUNOBJ && li.get(i).getBase() == null) {
                        for(com.pullenti.morph.MorphBaseInfo it : li.get(i).getMorph().getItems()) {
                            if (it instanceof com.pullenti.morph.MorphWordForm) 
                                so.addSlot(ObjectReferent.ATTR_BASE, (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).normalCase, false, 0);
                        }
                    }
                    for(com.pullenti.ner.semantic.internal.SynToken ch : stok.children) {
                        if (ch.real != null) {
                            if (ch.real.canBeEquals(so, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                                continue;
                            so.addSlot(ObjectReferent.ATTR_PROP, ch.real, false, 0);
                        }
                    }
                }
            }
            else if (li.get(i).typ == com.pullenti.ner.semantic.internal.Types.ACTANT && li.get(i).rol == ActantRole.OBJECT) {
                ActantReferent ar = ActantReferent._new2583(li.get(i).rol);
                real = ar;
                if (li.get(i).getBase() != null) 
                    ar.setValue(li.get(i).getBase());
                if (li.get(i).preposition != null) 
                    ar.setPrep(li.get(i).preposition);
                for(java.util.Map.Entry<String, com.pullenti.ner.semantic.internal.ValTypes> v : li.get(i).vals.entrySet()) {
                    if (v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.PROP || v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.ACTANTPROP) 
                        ar.addSlot(ActantReferent.ATTR_PROP, v.getKey(), false, 0);
                    else if (v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.NUMBER) 
                        ar.addSlot(ActantReferent.ATTR_COUNT, v.getKey(), false, 0);
                }
                for(com.pullenti.ner.semantic.internal.SynToken ch : li.get(i).children) {
                    if (ch.real != null) 
                        ar.addSlot(ActantReferent.ATTR_REF, ch.real, false, 0);
                }
            }
            else if (li.get(i).typ == com.pullenti.ner.semantic.internal.Types.NUMBER || li.get(i).typ2 == com.pullenti.ner.semantic.internal.Types.NUMBER) {
            }
            else if (li.get(i).isPredicate()) {{
                        PredicateReferent act = new PredicateReferent();
                        real = act;
                        li.get(i).setPredicateReferent(act);
                        for(com.pullenti.ner.semantic.internal.SynToken ch : li.get(i).children) {
                            if (ch.typ == com.pullenti.ner.semantic.internal.Types.ACTANT) {
                                ActantReferent ar = new ActantReferent();
                                ch.real = ar;
                                for(com.pullenti.ner.semantic.internal.SynToken chh : ch.children) {
                                    if (chh.real != null) {
                                        ar.addSlot(ActantReferent.ATTR_REF, chh.real, false, 0);
                                        if (chh.isPredicate()) 
                                            ar.setRole(ActantRole.SENTACTANT);
                                    }
                                }
                                if (ch.rol != ActantRole.UNDEFINED) 
                                    ar.setRole(ch.rol);
                                if (ar.findSlot(ActantReferent.ATTR_REF, null, true) == null) 
                                    ar.setValue(ch.getSourceText());
                                ar.addOccurenceOfRefTok(new com.pullenti.ner.ReferentToken(ar, ch.getBeginToken(), ch.getEndToken(), null));
                                if (ch.preposition != null) 
                                    ar.setPrep(ch.preposition);
                                for(java.util.Map.Entry<String, com.pullenti.ner.semantic.internal.ValTypes> v : ch.vals.entrySet()) {
                                    if (v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.ACTANTPROP || v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.PROP || v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.BASE) 
                                        ar.addSlot(ActantReferent.ATTR_PROP, v.getKey(), false, 0);
                                    else if (v.getValue() == com.pullenti.ner.semantic.internal.ValTypes.NUMBER) 
                                        ar.addSlot(ActantReferent.ATTR_COUNT, v.getKey(), false, 0);
                                }
                                act.addSlot(PredicateReferent.ATTR_ACTANT, ar, false, 0);
                            }
                        }
                    }
            }
            if (real == null) 
                continue;
            li.get(i).real = ad.registerReferent(real);
            com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new709(li.get(i).real, li.get(i).getBeginToken(), li.get(i).getEndToken(), li.get(i).getMorph());
            if (startChars.containsKey(rt.beginChar)) 
                rt.setBeginToken(startChars.get(rt.beginChar));
            if (endChars.containsKey(rt.endChar)) 
                rt.setEndToken(endChars.get(rt.endChar));
            rt.referent.addOccurenceOfRefTok(rt);
            if (t0.value.beginChar == rt.beginChar) 
                t0.value = rt;
            if (t1.value.endChar == rt.endChar) 
                t1.value = rt;
            if (!startChars.containsKey(rt.beginChar)) 
                startChars.put(rt.beginChar, rt);
            else 
                startChars.put(rt.beginChar, rt);
            if (!endChars.containsKey(rt.endChar)) 
                endChars.put(rt.endChar, rt);
            else 
                endChars.put(rt.endChar, rt);
        }
    }

    private static void _getOrderedList(com.pullenti.ner.semantic.internal.SynToken syn, java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> res, int lev) {
        if (lev > 10) 
            return;
        if (syn._UseThisToken != null) {
            _getOrderedList(syn._UseThisToken, res, lev + 1);
            return;
        }
        if (syn.getAnaforRef() != null && !res.contains(syn.getAnaforRef())) 
            _getOrderedList(syn.getAnaforRef(), res, lev + 1);
        for(com.pullenti.ner.semantic.internal.SynToken ch : syn.children) {
            _getOrderedList(ch, res, lev + 1);
        }
        if (!syn.isContainer()) {
            if (!res.contains(syn)) {
                if (syn.typ == com.pullenti.ner.semantic.internal.Types.OBJ && syn.getVal(com.pullenti.ner.semantic.internal.ValTypes.NUMBER) != null) {
                    if (syn.children.size() > 0 && ((syn.getBase() == null && syn.getVal(com.pullenti.ner.semantic.internal.ValTypes.NAME) == null))) {
                        syn.typ = com.pullenti.ner.semantic.internal.Types.NUMBER;
                        res.add(syn);
                    }
                    else {
                        com.pullenti.ner.semantic.internal.SynToken sy1 = com.pullenti.ner.semantic.internal.SynToken._new2480(syn.getBeginToken(), syn.getEndToken(), com.pullenti.ner.semantic.internal.Types.OBJ);
                        sy1.addVals(syn);
                        sy1.delVals(com.pullenti.ner.semantic.internal.ValTypes.NUMBER);
                        sy1.addChildren(syn.children);
                        syn.children.clear();
                        syn.typ = com.pullenti.ner.semantic.internal.Types.NUMBER;
                        syn.delVals(com.pullenti.ner.semantic.internal.ValTypes.PROP);
                        syn.delVals(com.pullenti.ner.semantic.internal.ValTypes.BASE);
                        syn.addChild(sy1);
                        res.add(sy1);
                        res.add(syn);
                    }
                }
                else 
                    res.add(syn);
            }
        }
    }

    public static void initialize() {
        com.pullenti.ner.semantic.internal.SynToken.initialize();
        com.pullenti.ner.ProcessorService.registerAnalyzer(new SemanticAnalyzer());
    }
    public SemanticAnalyzer() {
        super();
    }
}
