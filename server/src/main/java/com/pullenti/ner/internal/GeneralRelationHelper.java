/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.internal;

public class GeneralRelationHelper {

    public static void refreshGenerals(com.pullenti.ner.Processor proc, com.pullenti.ner.core.AnalysisKit kit) {
        java.util.HashMap<String, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> all = new java.util.HashMap< >();
        java.util.ArrayList<Node> allRefs = new java.util.ArrayList< >();
        for(com.pullenti.ner.Analyzer a : proc.getAnalyzers()) {
            com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(a);
            if (ad == null) 
                continue;
            for(com.pullenti.ner.Referent r : ad.getReferents()) {
                Node nod = Node._new1438(r, ad);
                allRefs.add(nod);
                r.setTag(nod);
                java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> si;
                com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> inoutarg1441 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres1442 = com.pullenti.n2j.Utils.tryGetValue(all, a.getName(), inoutarg1441);
                si = inoutarg1441.value;
                if (!inoutres1442) 
                    all.put(a.getName(), (si = new java.util.HashMap< >()));
                java.util.ArrayList<String> strs = r.getCompareStrings();
                if (strs == null || strs.size() == 0) 
                    continue;
                for(String s : strs) {
                    java.util.ArrayList<com.pullenti.ner.Referent> li;
                    com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg1439 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres1440 = com.pullenti.n2j.Utils.tryGetValue(si, s, inoutarg1439);
                    li = inoutarg1439.value;
                    if (!inoutres1440) 
                        si.put(s, (li = new java.util.ArrayList< >()));
                    li.add(r);
                }
            }
        }
        for(Node r : allRefs) {
            for(com.pullenti.ner.Slot s : r.ref.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    com.pullenti.ner.Referent to = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                    Node tn = (Node)com.pullenti.n2j.Utils.cast(to.getTag(), Node.class);
                    if (tn == null) 
                        continue;
                    if (tn.refsFrom == null) 
                        tn.refsFrom = new java.util.ArrayList< >();
                    tn.refsFrom.add(r);
                    if (r.refsTo == null) 
                        r.refsTo = new java.util.ArrayList< >();
                    r.refsTo.add(tn);
                }
            }
        }
        for(java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> ty : all.values()) {
            for(java.util.ArrayList<com.pullenti.ner.Referent> li : ty.values()) {
                if (li.size() < 2) 
                    continue;
                for(int i = 0; i < li.size(); i++) {
                    for(int j = i + 1; j < li.size(); j++) {
                        Node n1 = null;
                        Node n2 = null;
                        if (li.get(i).canBeGeneralFor(li.get(j)) && !li.get(j).canBeGeneralFor(li.get(i))) {
                            n1 = (Node)com.pullenti.n2j.Utils.cast(li.get(i).getTag(), Node.class);
                            n2 = (Node)com.pullenti.n2j.Utils.cast(li.get(j).getTag(), Node.class);
                        }
                        else if (li.get(j).canBeGeneralFor(li.get(i)) && !li.get(i).canBeGeneralFor(li.get(j))) {
                            n1 = (Node)com.pullenti.n2j.Utils.cast(li.get(j).getTag(), Node.class);
                            n2 = (Node)com.pullenti.n2j.Utils.cast(li.get(i).getTag(), Node.class);
                        }
                        if (n1 != null && n2 != null) {
                            if (n1.genFrom == null) 
                                n1.genFrom = new java.util.ArrayList< >();
                            if (!n1.genFrom.contains(n2)) 
                                n1.genFrom.add(n2);
                            if (n2.genTo == null) 
                                n2.genTo = new java.util.ArrayList< >();
                            if (!n2.genTo.contains(n1)) 
                                n2.genTo.add(n1);
                        }
                    }
                }
            }
        }
        for(Node n : allRefs) {
            if (n.genTo != null && n.genTo.size() > 1) {
                for(int i = n.genTo.size() - 1; i >= 0; i--) {
                    Node p = n.genTo.get(i);
                    boolean del = false;
                    for(int j = 0; j < n.genTo.size(); j++) {
                        if (j != i && n.genTo.get(j).isInGenParentsOrHigher(p)) 
                            del = true;
                    }
                    if (del) {
                        p.genFrom.remove(n);
                        n.genTo.remove(i);
                    }
                }
            }
        }
        for(Node n : allRefs) {
            if (!n.deleted && n.genTo != null && n.genTo.size() == 1) {
                Node p = n.genTo.get(0);
                if (p.genFrom.size() == 1) {
                    n.ref.mergeSlots(p.ref, true);
                    p.ref.setTag(n.ref);
                    p.replaceValues(n);
                    for(com.pullenti.ner.TextAnnotation o : p.ref.getOccurrence()) {
                        n.ref.addOccurence(o);
                    }
                    p.deleted = true;
                }
                else 
                    n.ref.setGeneralReferent(p.ref);
            }
        }
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            _correctReferents(t);
        }
        for(Node n : allRefs) {
            if (n.deleted) 
                n.ad.removeReferent(n.ref);
            n.ref.setTag(null);
        }
    }

    private static void _correctReferents(com.pullenti.ner.Token t) {
        com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
        if (rt == null) 
            return;
        if (rt.referent != null && (rt.referent.getTag() instanceof com.pullenti.ner.Referent)) 
            rt.referent = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(rt.referent.getTag(), com.pullenti.ner.Referent.class);
        for(com.pullenti.ner.Token tt = rt.getBeginToken(); tt != null && tt.endChar <= rt.endChar; tt = tt.getNext()) {
            _correctReferents(tt);
        }
    }

    public static class Node {
    
        public com.pullenti.ner.Referent ref;
    
        public com.pullenti.ner.core.AnalyzerData ad;
    
        public java.util.ArrayList<Node> refsTo;
    
        public java.util.ArrayList<Node> refsFrom;
    
        public java.util.ArrayList<Node> genTo;
    
        public java.util.ArrayList<Node> genFrom;
    
        public boolean deleted;
    
        @Override
        public String toString() {
            return ref.toString();
        }
    
        public boolean isInGenParentsOrHigher(Node n) {
            if (genTo == null) 
                return false;
            for(Node p : genTo) {
                if (p == n) 
                    return true;
                else if (p.isInGenParentsOrHigher(n)) 
                    return true;
            }
            return false;
        }
    
        public void replaceValues(Node newNode) {
            if (refsFrom != null) {
                for(Node fr : refsFrom) {
                    boolean ch = false;
                    for(com.pullenti.ner.Slot s : fr.ref.getSlots()) {
                        if (s.getValue() == ref) {
                            fr.ref.uploadSlot(s, newNode.ref);
                            ch = true;
                        }
                    }
                    if (!ch) 
                        continue;
                    for(int i = 0; i < (fr.ref.getSlots().size() - 1); i++) {
                        for(int j = i + 1; j < fr.ref.getSlots().size(); j++) {
                            if (com.pullenti.n2j.Utils.stringsEq(fr.ref.getSlots().get(i).getTypeName(), fr.ref.getSlots().get(j).getTypeName()) && fr.ref.getSlots().get(i).getValue() == fr.ref.getSlots().get(j).getValue()) {
                                fr.ref.getSlots().remove(j);
                                j--;
                            }
                        }
                    }
                }
            }
        }
    
        public static Node _new1438(com.pullenti.ner.Referent _arg1, com.pullenti.ner.core.AnalyzerData _arg2) {
            Node res = new Node();
            res.ref = _arg1;
            res.ad = _arg2;
            return res;
        }
        public Node() {
        }
    }

    public GeneralRelationHelper() {
    }
    public static GeneralRelationHelper _globalInstance;
    static {
        _globalInstance = new GeneralRelationHelper(); 
    }
}
