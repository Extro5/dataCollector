/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class AnnoHelper {

    public static com.pullenti.ner.semantic.SentenceReferent createAnnotation(java.util.Collection<com.pullenti.ner.Referent> refs) {
        for(com.pullenti.ner.Referent e : refs) {
            if (e instanceof com.pullenti.ner.semantic.ObjectReferent) 
                _calcRank((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(e, com.pullenti.ner.semantic.ObjectReferent.class), 0);
        }
        for(com.pullenti.ner.Referent e : refs) {
            e.setTag(null);
        }
        java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> stat = new java.util.ArrayList< >();
        float ee = (float)0;
        for(com.pullenti.ner.Referent e : refs) {
            com.pullenti.ner.semantic.ObjectReferent sy = (com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(e, com.pullenti.ner.semantic.ObjectReferent.class);
            if (sy == null) 
                continue;
            if (sy.getKind() != com.pullenti.ner.semantic.SemanticKind.OBJECT) 
                continue;
            if (sy.getOccurrence().size() < 2) 
                continue;
            if (sy.rank > 0) {
                stat.add(sy);
                ee += sy.rank;
            }
        }
        float minRank = (float)0;
        float minRank2 = (float)0;
        if (stat.size() > 0) {
            ee /= ((float)(int)stat.size());
            float dd = (float)0;
            for(com.pullenti.ner.semantic.ObjectReferent s : stat) {
                dd += (((s.rank - ee)) * ((s.rank - ee)));
            }
            if (dd > 0) {
                dd = (float)Math.sqrt((double)(dd / ((float)(int)stat.size())));
                minRank = ee + (((float)2) * dd);
                minRank2 = minRank / ((float)2);
            }
            stat.sort(m_CompA);
        }
        for(int i = 0; i < stat.size(); i++) {
            int j;
            for(j = 0; j < i; j++) {
                if (_checkContains(stat.get(j), stat.get(i), 0) || _checkContains(stat.get(i), stat.get(j), 0)) 
                    break;
            }
            if (j < i) {
                stat.remove(i);
                i--;
                continue;
            }
            if (stat.get(i).rank >= minRank && minRank > 0) {
                stat.get(i).setTag(i);
                continue;
            }
            if (i > 4 || (stat.get(i).rank < 2F)) {
                for(int indRemoveRange = i + stat.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) stat.remove(indRemoveRange);
                break;
            }
            stat.get(i).setTag(i);
        }
        java.util.ArrayList<SemPair> pairs = new java.util.ArrayList< >();
        for(com.pullenti.ner.semantic.ObjectReferent s : stat) {
            s.isSignific = true;
            s.setTag(0);
        }
        for(int i = 0; i < (stat.size() - 1); i++) {
            for(int j = i + 1; j < stat.size(); j++) {
                pairs.add(SemPair._new2476(stat.get(i), stat.get(j)));
            }
        }
        StringBuilder annoText = new StringBuilder();
        for(int k = 1; k < 2; k++) {
            com.pullenti.ner.semantic.SentenceReferent anno = com.pullenti.ner.semantic.SentenceReferent._new2477(com.pullenti.ner.semantic.SentenceKind.ANNOTATION);
            boolean allText = true;
            com.pullenti.ner.TextAnnotation firstTa = null;
            com.pullenti.ner.semantic.SentenceReferent first = null;
            int sentNum = 0;
            for(com.pullenti.ner.Referent e : refs) {
                com.pullenti.ner.semantic.SentenceReferent sent = (com.pullenti.ner.semantic.SentenceReferent)com.pullenti.n2j.Utils.cast(e, com.pullenti.ner.semantic.SentenceReferent.class);
                if (sent == null) 
                    continue;
                sentNum++;
                java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> li = new java.util.ArrayList< >();
                for(com.pullenti.ner.semantic.ObjectReferent se : sent.getSemRefs()) {
                    _getAnnoObjs(se, li, 0);
                }
                if (sent.getOccurrence().size() == 0) 
                    continue;
                com.pullenti.ner.TextAnnotation ta = sent.getOccurrence().get(0);
                boolean ok = false;
                if (k == 0) {
                    int newPars = 0;
                    java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> tmpLi = new java.util.ArrayList< >();
                    for(SemPair p : pairs) {
                        if (!p.isUsed) {
                            if (li.contains(p.ref1) && li.contains(p.ref2)) {
                                p.isUsed = true;
                                if ((((int)p.ref1.getTag()) < 2) && (((int)p.ref2.getTag()) < 2)) {
                                    newPars++;
                                    if (!tmpLi.contains(p.ref1)) 
                                        tmpLi.add(p.ref1);
                                    if (!tmpLi.contains(p.ref2)) 
                                        tmpLi.add(p.ref2);
                                }
                            }
                        }
                    }
                    if (newPars > 0) {
                        ok = true;
                        for(com.pullenti.ner.semantic.ObjectReferent ss : li) {
                            ss.setTag(1 + (((int)ss.getTag())));
                        }
                    }
                }
                else {
                    float co = (float)0;
                    java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> ignList = null;
                    for(com.pullenti.ner.semantic.ObjectReferent ss : li) {
                        if ((ss.getTag() instanceof Integer) && ((int)ss.getTag()) == 0) {
                            boolean ign = false;
                            if (sent.ignoredPart != null) {
                                for(com.pullenti.ner.TextAnnotation aa : ss.getOccurrence()) {
                                    if (aa.beginChar >= sent.ignoredPart.beginChar && (aa.beginChar < sent.ignoredPart.endChar)) {
                                        ign = true;
                                        break;
                                    }
                                }
                            }
                            if (ign) {
                                if (ignList == null) 
                                    ignList = new java.util.ArrayList< >();
                                ignList.add(ss);
                                continue;
                            }
                            co += ss.rank;
                            ss.setTag(1 + (((int)ss.getTag())));
                        }
                    }
                    if (co > 0 && ignList != null) {
                        for(com.pullenti.ner.semantic.ObjectReferent ii : ignList) {
                            co += ii.rank;
                            ii.setTag(1 + (((int)ii.getTag())));
                        }
                    }
                    float distco = 0.1F + (((0.9F * ((float)((ta.sofa.getText().length() - ta.beginChar)))) / ((float)ta.sofa.getText().length())));
                    co *= distco;
                    if (co > 2) {
                        ok = true;
                        if (sentNum == 2 && firstTa != null) {
                            if ((((ta.endChar - ta.beginChar)) * 2) > (firstTa.endChar - firstTa.beginChar)) {
                                anno.addOccurence(com.pullenti.ner.TextAnnotation._new2478(firstTa.beginChar, firstTa.endChar, ta.sofa, anno));
                                anno.addSlot(com.pullenti.ner.semantic.SentenceReferent.ATTR_REF, first, false, 0);
                                allText = true;
                                if (annoText.length() > 0) 
                                    annoText.append(' ');
                                String tte = firstTa.getText();
                                annoText.append(tte);
                                if (!com.pullenti.morph.LanguageHelper.endsWith(tte, ".")) 
                                    annoText.append('.');
                            }
                        }
                    }
                    else if (sentNum == 1) {
                        firstTa = ta;
                        first = sent;
                    }
                }
                if (ok) {
                    if (annoText.length() > 0) 
                        annoText.append(' ');
                    String tte = ta.getText();
                    annoText.append(tte);
                    if (!com.pullenti.morph.LanguageHelper.endsWith(tte, ".")) 
                        annoText.append('.');
                    anno.addOccurence(com.pullenti.ner.TextAnnotation._new2478(ta.beginChar, ta.endChar, ta.sofa, anno));
                    anno.addSlot(com.pullenti.ner.semantic.SentenceReferent.ATTR_REF, sent, false, 0);
                }
                else if ((ta.endChar - ta.beginChar) > 20) 
                    allText = false;
            }
            if (!allText && anno.getOccurrence().size() > 0) {
                anno.setContent(annoText.toString());
                return anno;
            }
        }
        return null;
    }

    private static boolean _checkContains(com.pullenti.ner.semantic.ObjectReferent own, com.pullenti.ner.semantic.ObjectReferent ch, int lev) {
        if (lev > 20) 
            return false;
        if (ch == own) 
            return true;
        for(com.pullenti.ner.Slot s : own.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.semantic.ObjectReferent) {
                if (_checkContains((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.semantic.ObjectReferent.class), ch, lev + 1)) 
                    return true;
            }
        }
        return false;
    }

    public static class SemPair {
    
        public com.pullenti.ner.semantic.ObjectReferent ref1;
    
        public com.pullenti.ner.semantic.ObjectReferent ref2;
    
        public boolean isUsed;
    
        @Override
        public String toString() {
            return (isUsed ? "U" : "N") + ": " + ref1.toString() + " - " + ref2.toString();
        }
    
        public static SemPair _new2476(com.pullenti.ner.semantic.ObjectReferent _arg1, com.pullenti.ner.semantic.ObjectReferent _arg2) {
            SemPair res = new SemPair();
            res.ref1 = _arg1;
            res.ref2 = _arg2;
            return res;
        }
        public SemPair() {
        }
    }


    private static CompSyns m_CompA;

    public static class CompSyns implements java.util.Comparator<com.pullenti.ner.semantic.ObjectReferent> {
    
        @Override
        public int compare(com.pullenti.ner.semantic.ObjectReferent x, com.pullenti.ner.semantic.ObjectReferent y) {
            if (x.rank > y.rank) 
                return -1;
            if (x.rank < y.rank) 
                return 1;
            return 0;
        }
        public CompSyns() {
        }
    }


    private static void _getAnnoObjs(com.pullenti.ner.semantic.ObjectReferent syn, java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> res, int lev) {
        if (lev > 20) 
            return;
        com.pullenti.ner.semantic.SemanticKind ki = syn.getKind();
        if (ki == com.pullenti.ner.semantic.SemanticKind.OBJECT) {
            if (syn.isSignific) {
                if (!res.contains(syn)) 
                    res.add(syn);
            }
        }
        if (ki == com.pullenti.ner.semantic.SemanticKind.OBJECT) {
            for(com.pullenti.ner.Referent r : syn.getPropRefs()) {
                if (r instanceof com.pullenti.ner.semantic.ObjectReferent) 
                    _getAnnoObjs((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.semantic.ObjectReferent.class), res, lev + 1);
            }
            return;
        }
    }

    private static void _calcObjRank(com.pullenti.ner.semantic.ObjectReferent s) {
        float r = s.rank;
        if (r > 0) 
            return;
        java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> li = new java.util.ArrayList< >();
        s.rank = (float)((((((double)1) + Math.log((double)(s.getOccurrence().size() == 0 ? 1 : s.getOccurrence().size())))) * ((((double)1) + Math.log((double)_calcTreeRank(s, li))))));
        if (s.getOccurrence().size() > 0) {
            s.rank *= ((float)((s.getOccurrence().get(0).sofa.getText().length() - s.getOccurrence().get(0).beginChar)));
            s.rank /= ((float)s.getOccurrence().get(0).sofa.getText().length());
        }
    }

    private static int _calcTreeRank(com.pullenti.ner.semantic.ObjectReferent s, java.util.ArrayList<com.pullenti.ner.semantic.ObjectReferent> li) {
        if (li.contains(s)) 
            return 1;
        li.add(s);
        int res = 1 + s.getProps().size();
        for(com.pullenti.ner.Referent ch : s.getPropRefs()) {
            if (ch instanceof com.pullenti.ner.semantic.ObjectReferent) 
                res += (_calcTreeRank((com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(ch, com.pullenti.ner.semantic.ObjectReferent.class), li) / 2);
        }
        if (s.getName() != null) 
            res *= 2;
        else if (s.getProxy() != null) {
            com.pullenti.ner.Referent r = s.getProxy();
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "URI") || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "PHONE") || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "MONEY")) {
            }
            else 
                res *= 2;
        }
        return res;
    }

    private static void _calcRank(com.pullenti.ner.semantic.ObjectReferent s, int lev) {
        if (lev > 100) 
            return;
        if (s.getKind() == com.pullenti.ner.semantic.SemanticKind.OBJECT) 
            _calcObjRank(s);
    }
    public AnnoHelper() {
    }
    public static AnnoHelper _globalInstance;
    static {
        _globalInstance = new AnnoHelper(); 
        m_CompA = new CompSyns();
    }
}
