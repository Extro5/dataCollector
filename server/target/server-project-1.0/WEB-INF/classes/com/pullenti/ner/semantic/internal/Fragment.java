/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class Fragment {

    public java.util.ArrayList<SynToken> items = new java.util.ArrayList< >();

    public java.util.ArrayList<SynToken> getAllItems() {
        java.util.ArrayList<SynToken> all = new java.util.ArrayList(items);
        for(Fragment ch : chained) {
            com.pullenti.ner.Token tt = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(ch.items.get(0).getBeginToken().getPrevious(), ch.items.get(0).getBeginToken());
            all.add(SynToken._new2480(tt, tt, (tt.isAnd() ? Types.CONJ : Types.COMMA)));
            all.addAll(ch.items);
        }
        return all;
    }


    public java.util.ArrayList<Fragment> chained = new java.util.ArrayList< >();

    public Fragment child;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (chained.size() > 0) 
            tmp.append('(');
        for(SynToken it : items) {
            tmp.append(it.getSourceText()).append(" ");
        }
        if (child != null) 
            tmp.append(" [").append(child.toString()).append("]");
        for(Fragment ch : chained) {
            tmp.append(" + ").append(ch.toString());
        }
        if (chained.size() > 0) 
            tmp.append(')');
        return tmp.toString();
    }

    public void doActants(Fragment own) {
        java.util.ArrayList<SynToken> all = getAllItems();
        if (own != null && own.items.get(own.items.size() - 1).isActant()) {
            if (all.get(0).typ == Types.WHAT && all.get(0).anaforRef0 != null) {
            }
            else 
                all.add(0, own.items.get(own.items.size() - 1));
        }
        Actant.process(all);
        if (child != null) {
            child.doActants(this);
            _doChildWhatActants(null);
        }
        for(Fragment ch : chained) {
            if (ch.child != null) {
                ch.child.doActants(ch);
                ch._doChildWhatActants(all);
            }
        }
    }

    private void _doChildWhatActants(java.util.ArrayList<SynToken> allList) {
        if (child == null || (child.items.size() < 2)) 
            return;
        SynToken it0 = child.items.get(0);
        if (it0.typ == Types.WHAT && ((it0.anaforRef0 == null || com.pullenti.n2j.Utils.stringsEq(it0.getBase(), "ЧТО") || com.pullenti.n2j.Utils.stringsEq(it0.getBase(), "ЩО")))) {
        }
        else 
            return;
        SynToken _act = null;
        for(int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).isPredicate()) {
                _act = items.get(i);
                break;
            }
        }
        if (child.items.get(1).isActant() && ((com.pullenti.n2j.Utils.stringsEq(it0.getBase(), "КАК") || com.pullenti.n2j.Utils.stringsEq(it0.getBase(), "ЯК")))) {
            if (child.items.get(1)._LastPredicate != null) {
                if (child.items.get(1)._LastPredicate.typ == Types.ACT) 
                    return;
            }
            if (_act == null) {
                java.util.ArrayList<SynToken> li = getAllItems();
                for(SynToken s : li) {
                    if (s.isPredicate()) 
                        _act = s;
                }
                if (_act == null && allList != null) {
                    for(SynToken s : allList) {
                        if (s.isPredicate()) 
                            _act = s;
                    }
                }
            }
            if (_act != null) {
                SynToken aa = SynToken._new2480(it0.getBeginToken(), child.items.get(1).getEndToken(), Types.ACTANT);
                aa.addChild(child.items.get(1));
                aa.addVal(it0.getBase(), ValTypes.PROP);
                _act.addChild(aa);
            }
            return;
        }
        if (com.pullenti.n2j.Utils.stringsNe(it0.getBase(), "КАК") && com.pullenti.n2j.Utils.stringsNe(it0.getBase(), "ЯК") && _act != null) {
            SynToken aa = SynToken._new2482(it0.getBeginToken(), it0.getEndToken(), Types.ACTANT, com.pullenti.ner.semantic.ActantRole.SENTACTANT);
            aa.addVal(it0.getBase(), ValTypes.PROP);
            for(SynToken w : child.getAllItems()) {
                if (w.isPredicate() && w._LastPredicate == null) 
                    aa.addChild(w);
            }
            if (aa.children.size() == 0 && child.child != null) {
                for(SynToken w : child.child.getAllItems()) {
                    if (w.isPredicate() && w._LastPredicate == null) 
                        aa.addChild(w);
                }
            }
            if (aa.children.size() > 0) 
                _act.addChild(aa);
        }
    }

    public static java.util.ArrayList<Fragment> createFragraph(java.util.ArrayList<SynToken> li) {
        java.util.ArrayList<Fragment> frags = new java.util.ArrayList< >();
        Fragment fr = null;
        for(int i = 0; i < li.size(); i++) {
            if (li.get(i).isConjOrComma() || li.get(i).typ == Types.DELIMETER) {
                if (fr != null && fr.items.size() > 0) 
                    frags.add(fr);
                fr = new Fragment();
            }
            else {
                if (fr == null) 
                    fr = new Fragment();
                fr.items.add(li.get(i));
            }
        }
        if (fr != null && fr.items.size() > 0) 
            frags.add(fr);
        if (frags.size() < 2) 
            return frags;
        java.util.ArrayList<Fragment> res = new java.util.ArrayList< >();
        java.util.ArrayList<Fragment> stack = new java.util.ArrayList< >();
        res.add(frags.get(0));
        stack.add(frags.get(0));
        boolean newLev = false;
        for(int i = 1; i < frags.size(); i++) {
            fr = frags.get(i);
            if (fr.items.get(0).typ == Types.ACTPRICH && stack.get(0).getActPrich() != null) {
                int co = stack.get(0).chainCoef(fr);
                if (co > 1) {
                    newLev = false;
                    stack.get(0).chained.add(fr);
                    continue;
                }
            }
            if (fr.items.get(0).typ == Types.WHAT || fr.getActPrich() != null) {
                newLev = true;
                if (stack.get(0).chained.size() > 0) {
                    if (stack.get(0).chained.get(stack.get(0).chained.size() - 1).child == null) 
                        stack.get(0).chained.get(stack.get(0).chained.size() - 1).child = fr;
                    else {
                    }
                }
                else if (stack.get(0).child == null) 
                    stack.get(0).child = fr;
                else {
                }
                stack.add(0, fr);
                continue;
            }
            int max = 0;
            int j = -1;
            for(int jj = (newLev ? 1 : 0); jj < stack.size(); jj++) {
                int co = stack.get(jj).chainCoef(fr);
                if (co > max) {
                    max = co;
                    j = jj;
                }
            }
            newLev = false;
            if (j >= 0) {
                if (j > 0) 
                    for(int indRemoveRange = 0 + j - 1, indMinIndex = 0; indRemoveRange >= indMinIndex; indRemoveRange--) stack.remove(indRemoveRange);
                stack.get(0).chained.add(fr);
                continue;
            }
            stack.clear();
            stack.add(fr);
            res.add(fr);
        }
        if ((res.size() == 2 && res.get(0).getFirstPredicate() == null && res.get(0).child == null) && res.get(1).getFirstPredicate() != null) {
            res.get(0).items.addAll(res.get(1).items);
            res.get(0).child = res.get(1).child;
            res.remove(1);
        }
        return res;
    }

    private static boolean _canBeLikePredicates(SynToken it1, SynToken it2) {
        if (it1.typ != it2.typ) 
            return false;
        if (it1.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED || it2.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
            if ((((it1.getMorph().getNumber().value()) & (it2.getMorph().getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                return false;
        }
        if (it2.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED || it2.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
            if ((((it1.getMorph().getGender().value()) & (it2.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                return false;
        }
        if (it2.typ == Types.ACTPRICH) {
            if (!it1.getMorph().getCase().isUndefined() || it2.getMorph().getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(it1.getMorph().getCase(), it2.getMorph().getCase()))).isUndefined()) 
                    return false;
            }
        }
        if (it1.getMorph().getVoice() != com.pullenti.morph.MorphVoice.UNDEFINED && it2.getMorph().getVoice() != com.pullenti.morph.MorphVoice.UNDEFINED) {
            if ((((it1.getMorph().getVoice().value()) & (it2.getMorph().getVoice().value()))) == (com.pullenti.morph.MorphVoice.UNDEFINED.value())) 
                return false;
        }
        return true;
    }

    private SynToken getFirstPredicate() {
        for(SynToken it : items) {
            if (it.isPredicate()) 
                return it;
        }
        for(Fragment ch : chained) {
            for(SynToken it : ch.items) {
                if (it.isPredicate()) 
                    return it;
            }
        }
        return null;
    }


    private SynToken getLastPredicate() {
        for(int i = chained.size() - 1; i >= 0; i--) {
            for(int j = chained.get(i).items.size() - 1; j >= 0; j--) {
                if (chained.get(i).items.get(j).isPredicate()) 
                    return chained.get(i).items.get(j);
            }
        }
        for(int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).isPredicate()) 
                return items.get(i);
        }
        return null;
    }


    private int chainCoef(Fragment fr) {
        int co = _chainCoef(fr, false);
        if (chained.size() > 0) {
            int co1 = chained.get(chained.size() - 1)._chainCoef(fr, true);
            if (co1 > co) 
                co = co1;
        }
        return co;
    }

    private int _chainCoef(Fragment fr, boolean isLastChain) {
        int co = 0;
        SynToken pr1 = getLastPredicate();
        SynToken pr2 = fr.getFirstPredicate();
        if (pr1 != null && pr2 != null) {
            if (_canBeLikePredicates(pr1, pr2)) {
                int i1 = items.indexOf(pr1);
                int i2 = fr.items.indexOf(pr2);
                boolean ok = true;
                co = 1;
                if (((i1 + 1) < items.size()) && items.get(i1 + 1).isActant()) {
                    if (((i2 + 1) < fr.items.size()) && fr.items.get(i2 + 1).isActant()) 
                        co++;
                    else 
                        ok = false;
                }
                if (i2 > 0 && fr.items.get(i2 - 1).isActant()) {
                    if (i1 > 0 && items.get(i1 - 1).isActant()) 
                        co++;
                    else 
                        ok = false;
                }
                if (ok) {
                    if (items.size() == fr.items.size()) {
                        int i;
                        for(i = 0; i < items.size(); i++) {
                            if (items.get(i).isPredicate() && _canBeLikePredicates(items.get(i), fr.items.get(i))) {
                            }
                            else if (items.get(i).isActant() == fr.items.get(i).isActant()) {
                            }
                            else 
                                break;
                        }
                        if (i >= items.size()) 
                            co++;
                    }
                    return co;
                }
            }
        }
        SynToken _act = fr.getAct();
        if (_act == null && fr.items.size() > 0 && fr.items.get(0).isPredicate()) 
            _act = fr.items.get(0);
        if (_act != null) {
            co = 0;
            if (getAct() == null && getActPrich() == null) {
                for(int i = items.size() - 1; i >= 0; i--) {
                    if (items.get(i).isActant()) {
                        if (i > 0 && items.get(i - 1).typ == Types.WHAT) 
                            break;
                        Actant a = Actant.tryCreate(items.get(i), _act);
                        if (a != null && ((a.agentCoef > 0 || a.pacientCoef > 0))) {
                            co = 1;
                            if (_act == fr.items.get(0)) 
                                co++;
                            return co;
                        }
                        break;
                    }
                }
            }
        }
        if (fr.getAct() == null && fr.getActPrich() == null) {
            _act = (SynToken)com.pullenti.n2j.Utils.notnull(getAct(), getActPrich());
            if (_act != null && _act == items.get(items.size() - 1)) {
                if (fr.items.get(0).isActant()) {
                    Actant a = Actant.tryCreate(fr.items.get(0), _act);
                    if (a != null) {
                        if (a.agentCoef > 0) 
                            return (a.agentCoef > 0 && a.pacientCoef > 0 ? 1 : 2);
                    }
                }
            }
        }
        return co;
    }

    public SynToken getAct() {
        SynToken res = null;
        for(SynToken it : items) {
            if (it.typ == Types.ACTPRICH) 
                return null;
            else if (it.typ == Types.ACT) {
                if (res != null) 
                    return null;
                res = it;
            }
        }
        return res;
    }


    public SynToken getActPrich() {
        SynToken res = null;
        for(SynToken it : items) {
            if (it.typ == Types.ACT) 
                return null;
            else if (it.typ == Types.ACTPRICH) {
                if (res != null) 
                    return null;
                res = it;
            }
        }
        return res;
    }


    public boolean isEmpty() {
        boolean ok = true;
        for(SynToken it : items) {
            if (!it.isActant()) {
                if (it == items.get(0) && it.typ == Types.WHAT && it.anaforRef0 == null) {
                }
                else 
                    ok = false;
            }
        }
        if (ok) 
            return true;
        for(SynToken it : items) {
            if (it.typ == Types.EMPTY || it.typ == Types.WHAT) {
            }
            else if (it.typ == Types.ACT) {
                if (it.getMorph().getGender() == com.pullenti.morph.MorphGender.NEUTER && it.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                }
                else 
                    return false;
            }
            else 
                return false;
        }
        return true;
    }


    /**
     * Признак того, что начинается "о которой", "которая" ... или ЧТО
     */
    public boolean isWhich() {
        if (items.size() == 0) 
            return false;
        SynToken sy = items.get(0);
        if (sy.typ == Types.WHAT) 
            return true;
        return false;
    }

    public Fragment() {
    }
}
