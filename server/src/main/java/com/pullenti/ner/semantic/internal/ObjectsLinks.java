/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class ObjectsLinks {

    private java.util.ArrayList<SynToken> items = new java.util.ArrayList< >();

    private int[][] coefs;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < items.size(); i++) {
            for(int j = 0; j < items.size(); j++) {
                res.append((coefs[i][j] == 0 ? " " : ((items.get(j)._ObjVarTyp == 0 ? "*" : ((items.get(j)._ObjVarTyp == 1 ? "," : "&")))))).append(coefs[i][j]).append(" ");
            }
            res.append("\r\n");
        }
        return res.toString();
    }

    public static java.util.ArrayList<SynToken> createLinks(java.util.ArrayList<SynToken> li) {
        ObjectsLinks ols = ObjectsLinks.create(li);
        if (ols == null) 
            return null;
        ols.optimize();
        ObjLinkVar var = ols.generate();
        if (var == null) 
            return null;
        java.util.ArrayList<SynToken> res = var.prepare();
        return res;
    }

    private static ObjectsLinks create(java.util.ArrayList<SynToken> li) {
        ObjectsLinks res = new ObjectsLinks();
        for(int i = 0; i < li.size(); i++) {
            if (li.get(i).isActant() || li.get(i).isPredicateInfinitive()) {
                res.items.add(li.get(i));
                li.get(i)._ObjVarTyp = 0;
                if (i > 0 && li.get(i - 1).typ == Types.CONJ) 
                    li.get(i)._ObjVarTyp = 2;
                else if (i > 0 && li.get(i - 1).typ == Types.COMMA) {
                    li.get(i)._ObjVarTyp = 1;
                    if (i == (li.size() - 1)) {
                        com.pullenti.ner.Token tt = li.get(i).getEndToken().getNext();
                        if (tt != null && tt.isChar('.')) 
                            tt = tt.getNext();
                        if (tt == null || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) 
                            li.get(i)._ObjVarTyp = 2;
                    }
                }
            }
        }
        if (res.items.size() < 2) 
            return null;
        res.coefs = new int[res.items.size()][res.items.size()];
        for(int i = 1; i < res.items.size(); i++) {
            for(int j = i - 1; j >= 0; j--) {
                int co;
                if (res.items.get(i)._ObjVarTyp > 0) 
                    co = ObjectHelper.cancAndCoef(res.items.get(j), res.items.get(i));
                else 
                    co = ObjectHelper.cancNextCoef(res.items.get(j), res.items.get(i));
                if (co > 0) 
                    res.coefs[j][i] = co;
            }
        }
        return res;
    }

    private void optimize() {
    }

    public static class LinkItem {
    
        public com.pullenti.ner.semantic.internal.SynToken obj;
    
        public com.pullenti.ner.semantic.internal.SynToken back;
    
        public int coef;
    
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            res.append(coef).append(": [").append(obj.getSourceText()).append("] ").append((obj._ObjVarTyp == 0 ? "<=" : ((obj._ObjVarTyp == 1 ? "," : "&")))).append(" [").append(back.getSourceText()).append("]");
            return res.toString();
        }
        public LinkItem() {
        }
    }


    private ObjLinkVar generate() {
        java.util.ArrayList<ObjLinkVar> res = new java.util.ArrayList< >();
        int[] inds = new int[items.size()];
        java.util.ArrayList<java.util.ArrayList<Integer>> vals = new java.util.ArrayList< >();
        for(int i = 0; i < items.size(); i++) {
            inds[i] = 0;
            java.util.ArrayList<Integer> li = new java.util.ArrayList< >();
            vals.add(li);
            for(int j = i - 1; j >= 0; j--) {
                if (coefs[j][i] > 0) 
                    li.add(j + 1);
            }
        }
        while(true) {
            int i = 0;
            ObjLinkVar var = new ObjLinkVar();
            int mini = 0;
            for(i = 0; i < inds.length; i++) {
                int ii = (inds[i] == 0 ? 0 : vals.get(i).get(inds[i] - 1));
                if (ii == 0) {
                    var.firsts.add(ObjLink._new2490(items.get(i)));
                    mini = i;
                    continue;
                }
                if ((ii - 1) < mini) 
                    break;
                SynToken it0 = items.get(ii - 1);
                if (!var.addTo(it0, items.get(i), i)) 
                    var.firsts.add(ObjLink._new2490(items.get(i)));
                else 
                    var.coef += (coefs[ii - 1][i]);
            }
            if (i >= inds.length) {
                var.correctCoef(this);
                res.add(var);
            }
            for(i = inds.length - 1; i >= 0; i--) {
                if ((++inds[i]) <= vals.get(i).size()) 
                    break;
                else 
                    inds[i] = 0;
            }
            if (i < 0) 
                break;
        }
        java.util.Collections.sort(res);
        if (res.size() == 0) 
            return null;
        if (res.get(0).coef <= 0) 
            return null;
        return res.get(0);
    }

    public static class ObjLink {
    
        public com.pullenti.ner.semantic.internal.SynToken obj;
    
        public int index;
    
        public java.util.ArrayList<ObjLink> links;
    
        public java.util.ArrayList<ObjLink> ands;
    
        public boolean addTo(com.pullenti.ner.semantic.internal.SynToken st0, com.pullenti.ner.semantic.internal.SynToken st, int _index) {
            if (st0 == obj) {
                if (st._ObjVarTyp == 0) {
                    if (links == null) 
                        links = new java.util.ArrayList< >();
                    links.add(_new2492(st, _index));
                }
                else {
                    if (ands == null) 
                        ands = new java.util.ArrayList< >();
                    ands.add(_new2492(st, _index));
                }
                return true;
            }
            if (links != null) {
                for(ObjLink l : links) {
                    if (l.addTo(st0, st, _index)) 
                        return true;
                }
            }
            if (ands != null) {
                for(ObjLink l : ands) {
                    if (l.addTo(st0, st, _index)) 
                        return true;
                }
            }
            return false;
        }
    
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            if (links != null) {
                res.append("[").append(obj.getSourceText());
                for(ObjLink li : links) {
                    res.append(" <= ").append(li.toString());
                }
                res.append(']');
            }
            else 
                res.append(obj.getSourceText());
            if (ands != null) {
                res.insert(0, '(');
                for(ObjLink a : ands) {
                    res.append(" & ").append(a.toString());
                }
                res.append(')');
            }
            return res.toString();
        }
    
        public void correctCoef(com.pullenti.ner.semantic.internal.ObjectsLinks.ObjLinkVar co, com.pullenti.ner.semantic.internal.ObjectsLinks oli) {
            if (links != null) {
                for(ObjLink l : links) {
                    if (l.ands != null) {
                        for(ObjLink a : l.ands) {
                            int cne = com.pullenti.ner.semantic.internal.ObjectHelper.cancNextCoef(obj, a.obj);
                            if (cne == 0) 
                                co.coef -= ((float)2);
                            else 
                                co.coef += (((float)cne) / ((float)2));
                        }
                    }
                    if (l.obj.typ == com.pullenti.ner.semantic.internal.Types.ACT) {
                    }
                    else {
                        int d = l.index - index;
                        co.coef += (1F / ((float)((d + 1))));
                    }
                    l.correctCoef(co, oli);
                }
            }
            if (ands != null) {
                for(ObjLink a : ands) {
                    a.correctCoef(co, oli);
                }
                if (ands.get(ands.size() - 1).obj._ObjVarTyp == 1) 
                    co.coef = (float)-100;
            }
        }
    
        /**
         * Формирование результата (ссылки)
         * @return 
         */
        public com.pullenti.ner.semantic.internal.SynToken prepare() {
            if (obj.typ == com.pullenti.ner.semantic.internal.Types.ACT) 
                obj.transformToTyp(com.pullenti.ner.semantic.internal.Types.OBJ);
            if (links != null) {
                for(ObjLink l : links) {
                    com.pullenti.ner.semantic.internal.SynToken sy = l.prepare();
                    if (sy != null) {
                        obj.embed(sy, false, 0);
                        if (sy.endChar > obj.endChar) 
                            obj.setEndToken(sy.getEndToken());
                    }
                }
                if (obj.typ == com.pullenti.ner.semantic.internal.Types.PRONOUNOBJ && obj.getMorph()._getClass().isPronoun()) {
                    obj.getMorph().removeItems(com.pullenti.morph.MorphClass.PRONOUN, false);
                    if (obj.children.size() == 1) {
                        com.pullenti.ner.semantic.internal.SynToken ch0 = obj.children.get(0);
                        obj.children.clear();
                        ch0.children.add(obj);
                        obj.setEndToken(ch0.getBeginToken().getPrevious());
                        ch0.setBeginToken(obj.getBeginToken());
                        obj = ch0;
                    }
                    else if (obj.children.size() > 1) {
                        com.pullenti.ner.semantic.internal.SynToken re1 = com.pullenti.ner.semantic.internal.SynToken._new2480(obj.getBeginToken(), obj.getEndToken(), com.pullenti.ner.semantic.internal.Types.OBJ);
                        re1.setMorph(new com.pullenti.ner.MorphCollection(obj.getMorph()));
                        re1.addChildren(obj.children);
                        obj.children.clear();
                        for(com.pullenti.ner.semantic.internal.SynToken ch : re1.children) {
                            ch.addChild(obj);
                        }
                        obj = re1;
                    }
                }
            }
            if (ands == null) 
                return obj;
            boolean dekart = false;
            if (links == null && ands.get(ands.size() - 1).links != null) {
                dekart = true;
                for(int i = 0; i < (ands.size() - 1); i++) {
                    if (ands.get(i).links != null || ands.get(i).ands != null) {
                        dekart = false;
                        break;
                    }
                }
                if (dekart) {
                    dekart = true;
                    java.util.ArrayList<ObjLink> lii = ands.get(ands.size() - 1).links;
                    for(ObjLink ll : lii) {
                        int cli = com.pullenti.ner.semantic.internal.ObjectHelper.cancNextCoef(obj, ll.obj);
                        if (cli <= 0) {
                            dekart = false;
                            break;
                        }
                    }
                    if (dekart) {
                        for(int i = 0; i < (ands.size() - 1); i++) {
                            for(ObjLink ll : lii) {
                                int cli = com.pullenti.ner.semantic.internal.ObjectHelper.cancNextCoef(ands.get(i).obj, ll.obj);
                                if (cli <= 0) {
                                    dekart = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            com.pullenti.ner.semantic.internal.SynToken re = com.pullenti.ner.semantic.internal.SynToken._new2480(obj.getBeginToken(), obj.getEndToken(), com.pullenti.ner.semantic.internal.Types.OBJ);
            re.setMorph(new com.pullenti.ner.MorphCollection(null));
            re.getMorph().setNumber(com.pullenti.morph.MorphNumber.PLURAL);
            re.preposition = obj.preposition;
            re.embed(obj, false, 0);
            com.pullenti.ner.semantic.internal.SynToken lastAnd = null;
            java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> _ands = new java.util.ArrayList< >();
            com.pullenti.morph.MorphBaseInfo mi = com.pullenti.morph.MorphBaseInfo._new2496(com.pullenti.morph.MorphNumber.PLURAL, obj.getMorph().getGender(), obj.getMorph().getCase());
            for(ObjLink a : ands) {
                com.pullenti.ner.semantic.internal.SynToken sy = a.prepare();
                if (sy != null) {
                    _ands.add(sy);
                    re.embed(sy, false, 0);
                    if (sy.endChar > re.endChar) 
                        re.setEndToken(sy.getEndToken());
                    if (a == ands.get(ands.size() - 1)) 
                        lastAnd = sy;
                    mi.setGender(com.pullenti.morph.MorphGender.of((mi.getGender().value()) | (sy.getMorph().getGender().value())));
                    mi.setCase(com.pullenti.morph.MorphCase.ooBitor(mi.getCase(), sy.getMorph().getCase()));
                }
            }
            re.getMorph().addItem(mi);
            if (dekart && lastAnd != null) {
                obj.addChildren(lastAnd.children);
                for(com.pullenti.ner.semantic.internal.SynToken a : _ands) {
                    if (a != lastAnd) 
                        a.addChildren(lastAnd.children);
                }
            }
            return re;
        }
    
        public static ObjLink _new2490(com.pullenti.ner.semantic.internal.SynToken _arg1) {
            ObjLink res = new ObjLink();
            res.obj = _arg1;
            return res;
        }
        public static ObjLink _new2492(com.pullenti.ner.semantic.internal.SynToken _arg1, int _arg2) {
            ObjLink res = new ObjLink();
            res.obj = _arg1;
            res.index = _arg2;
            return res;
        }
        public ObjLink() {
        }
    }


    public static class ObjLinkVar implements Comparable<ObjLinkVar> {
    
        public float coef;
    
        public java.util.ArrayList<com.pullenti.ner.semantic.internal.ObjectsLinks.ObjLink> firsts = new java.util.ArrayList< >();
    
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            res.append(coef).append(": ");
            for(com.pullenti.ner.semantic.internal.ObjectsLinks.ObjLink f : firsts) {
                res.append(f).append("  ");
            }
            return res.toString();
        }
    
        @Override
        public int compareTo(ObjLinkVar other) {
            if (coef > other.coef) 
                return -1;
            if (coef < other.coef) 
                return 1;
            return 0;
        }
    
        public boolean addTo(com.pullenti.ner.semantic.internal.SynToken st0, com.pullenti.ner.semantic.internal.SynToken st, int index) {
            for(com.pullenti.ner.semantic.internal.ObjectsLinks.ObjLink f : firsts) {
                if (f.addTo(st0, st, index)) 
                    return true;
            }
            return false;
        }
    
        public void correctCoef(com.pullenti.ner.semantic.internal.ObjectsLinks oli) {
            for(com.pullenti.ner.semantic.internal.ObjectsLinks.ObjLink fi : firsts) {
                fi.correctCoef(this, oli);
            }
        }
    
        public java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> prepare() {
            java.util.ArrayList<com.pullenti.ner.semantic.internal.SynToken> res = new java.util.ArrayList< >();
            for(com.pullenti.ner.semantic.internal.ObjectsLinks.ObjLink fi : firsts) {
                com.pullenti.ner.semantic.internal.SynToken sy = fi.prepare();
                if (sy != null) 
                    res.add(sy);
            }
            return res;
        }
        public ObjLinkVar() {
        }
    }

    public ObjectsLinks() {
    }
    public static ObjectsLinks _globalInstance;
    static {
        _globalInstance = new ObjectsLinks(); 
    }
}
