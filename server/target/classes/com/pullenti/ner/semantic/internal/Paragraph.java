/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class Paragraph {

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> sents = new java.util.ArrayList< >();

    public SemAnalyzerData data;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(data.getReferents().size()).append(" items, ").append(sents.size()).append(" sentences: ");
        for(com.pullenti.ner.ReferentToken s : sents) {
            res.append("\r\n").append(s.referent.toString()).append(" ");
        }
        return res.toString();
    }

    public static class SemAnalyzerData extends com.pullenti.ner.core.AnalyzerData {
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            if (referent instanceof com.pullenti.ner.semantic.PredicateReferent) {
                getReferents().add(referent);
                return referent;
            }
            com.pullenti.ner.semantic.ObjectReferent sr = (com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(referent, com.pullenti.ner.semantic.ObjectReferent.class);
            if (sr != null) {
                java.util.ArrayList<com.pullenti.ner.Referent> li = null;
                java.util.ArrayList<String> strs = sr.getCompareStrings();
                if (strs != null) {
                    for(String s : strs) {
                        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2497 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2498 = com.pullenti.n2j.Utils.tryGetValue(m_Hash, s, inoutarg2497);
                        li = inoutarg2497.value;
                        if (inoutres2498) {
                            for(com.pullenti.ner.Referent l : li) {
                                if (l.canBeEquals(referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                                    l.mergeSlots(referent, true);
                                    return l;
                                }
                            }
                        }
                    }
                }
                getReferents().add(referent);
                if (strs != null) {
                    for(String s : strs) {
                        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2499 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2500 = com.pullenti.n2j.Utils.tryGetValue(m_Hash, s, inoutarg2499);
                        li = inoutarg2499.value;
                        if (!inoutres2500) 
                            m_Hash.put(s, (li = new java.util.ArrayList< >()));
                        li.add(sr);
                    }
                }
                return sr;
            }
            com.pullenti.ner.semantic.ActantReferent ar = (com.pullenti.ner.semantic.ActantReferent)com.pullenti.n2j.Utils.cast(referent, com.pullenti.ner.semantic.ActantReferent.class);
            if (ar != null) {
                java.util.ArrayList<com.pullenti.ner.Referent> li = null;
                java.util.ArrayList<String> strs = ar.getCompareStrings();
                if (strs != null) {
                    for(String s : strs) {
                        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2501 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2502 = com.pullenti.n2j.Utils.tryGetValue(m_AHash, s, inoutarg2501);
                        li = inoutarg2501.value;
                        if (inoutres2502) {
                            for(com.pullenti.ner.Referent l : li) {
                                if (l.canBeEquals(referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                                    l.mergeSlots(referent, true);
                                    return l;
                                }
                            }
                        }
                    }
                }
                getReferents().add(referent);
                if (strs != null) {
                    for(String s : strs) {
                        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2503 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2504 = com.pullenti.n2j.Utils.tryGetValue(m_AHash, s, inoutarg2503);
                        li = inoutarg2503.value;
                        if (!inoutres2504) 
                            m_AHash.put(s, (li = new java.util.ArrayList< >()));
                        li.add(ar);
                    }
                }
                return ar;
            }
            return super.registerReferent(referent);
        }
    
        private java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> m_Hash = new java.util.HashMap< >();
    
        private java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> m_AHash = new java.util.HashMap< >();
        public SemAnalyzerData() {
            super();
        }
    }


    public void process(com.pullenti.ner.core.AnalyzerData ad, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> objs) {
        for(com.pullenti.ner.Referent r : data.getReferents()) {
            r.setTag(null);
        }
        for(com.pullenti.ner.Referent r : data.getReferents()) {
            if ((r instanceof com.pullenti.ner.semantic.ObjectReferent) || (r instanceof com.pullenti.ner.semantic.ActantReferent)) {
                com.pullenti.ner.Referent sr = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.Referent.class);
                java.util.ArrayList<String> keys = sr.getCompareStrings();
                if (keys == null || keys.size() == 0) {
                    ad.getReferents().add(sr);
                    continue;
                }
                for(String key : keys) {
                    java.util.ArrayList<com.pullenti.ner.Referent> li = null;
                    com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2505 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2506 = com.pullenti.n2j.Utils.tryGetValue(objs, key, inoutarg2505);
                    li = inoutarg2505.value;
                    if (inoutres2506) {
                        for(com.pullenti.ner.Referent ss : li) {
                            if (ss.canBeEquals(sr, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) {
                                sr.setTag(ss);
                                for(com.pullenti.ner.TextAnnotation o : sr.getOccurrence()) {
                                    ss.addOccurence(o);
                                }
                                break;
                            }
                        }
                    }
                    if (sr.getTag() != null) 
                        break;
                }
                if (sr.getTag() != null) 
                    continue;
                for(String key : keys) {
                    java.util.ArrayList<com.pullenti.ner.Referent> li = null;
                    com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2507 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2508 = com.pullenti.n2j.Utils.tryGetValue(objs, key, inoutarg2507);
                    li = inoutarg2507.value;
                    if (!inoutres2508) 
                        objs.put(key, (li = new java.util.ArrayList< >()));
                    li.add(sr);
                }
                ad.getReferents().add(sr);
            }
        }
        for(com.pullenti.ner.Referent r : data.getReferents()) {
            _correctSlots(r);
        }
        java.util.ArrayList<com.pullenti.ner.semantic.PredicateReferent> acts = new java.util.ArrayList< >();
        for(com.pullenti.ner.Referent r : data.getReferents()) {
            if (r instanceof com.pullenti.ner.semantic.PredicateReferent) {
                acts.add((com.pullenti.ner.semantic.PredicateReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.semantic.PredicateReferent.class));
                ad.getReferents().add(r);
            }
        }
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.semantic.ActantReferent>> ahash = new java.util.HashMap< >();
        for(com.pullenti.ner.semantic.PredicateReferent a : acts) {
            for(com.pullenti.ner.Slot s : a.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.semantic.PredicateReferent.ATTR_ACTANT) && (s.getValue() instanceof com.pullenti.ner.semantic.ActantReferent)) {
                    com.pullenti.ner.semantic.ActantReferent ar = (com.pullenti.ner.semantic.ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.semantic.ActantReferent.class);
                    _correctSlots(ar);
                    String str = ar.toString().toUpperCase();
                    java.util.ArrayList<com.pullenti.ner.semantic.ActantReferent> li;
                    com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.semantic.ActantReferent>> inoutarg2509 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2510 = com.pullenti.n2j.Utils.tryGetValue(ahash, str, inoutarg2509);
                    li = inoutarg2509.value;
                    if (!inoutres2510) 
                        ahash.put(str, (li = new java.util.ArrayList< >()));
                    com.pullenti.ner.semantic.ActantReferent ar0 = null;
                    for(com.pullenti.ner.semantic.ActantReferent ll : li) {
                        if (ll.canBeEquals(ar, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                            ar0 = ll;
                            for(com.pullenti.ner.TextAnnotation oo : ar.getOccurrence()) {
                                ar0.addOccurence(oo);
                            }
                            break;
                        }
                    }
                    if (ar0 != null) 
                        s.setValue(ar0);
                    else {
                        li.add(ar);
                        ad.getReferents().add(ar);
                    }
                }
            }
        }
        for(com.pullenti.ner.ReferentToken rt : sents) {
            _correctSlots(rt.referent);
            ad.getReferents().add(rt.referent);
            rt.referent.addOccurenceOfRefTok(rt);
        }
    }

    private static void _correctSlots(com.pullenti.ner.Referent r) {
        for(com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.semantic.ObjectReferent) {
                if ((((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.semantic.ObjectReferent.class))).getTag() instanceof com.pullenti.ner.semantic.ObjectReferent) 
                    s.setValue((((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.semantic.ObjectReferent.class))).getTag());
            }
            else if (s.getValue() instanceof com.pullenti.ner.semantic.ActantReferent) {
                if ((((com.pullenti.ner.semantic.ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.semantic.ActantReferent.class))).getTag() instanceof com.pullenti.ner.semantic.ActantReferent) 
                    s.setValue((((com.pullenti.ner.semantic.ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.semantic.ActantReferent.class))).getTag());
            }
        }
    }
    public Paragraph() {
        if(_globalInstance == null) return;
        data = new SemAnalyzerData();
    }
    public static Paragraph _globalInstance;
    static {
        _globalInstance = new Paragraph(); 
    }
}
