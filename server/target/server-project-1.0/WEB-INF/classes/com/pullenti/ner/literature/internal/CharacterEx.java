/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharacterEx extends com.pullenti.ner.Referent implements Comparable<CharacterEx> {

    public CharacterEx() {
        super("CHAREX");
    }

    public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;

    public CharacterAge age = CharacterAge.UNDEFINED;

    public java.util.ArrayList<CharItemVar> attrs = new java.util.ArrayList< >();

    public java.util.ArrayList<NameVariant> names = new java.util.ArrayList< >();

    public CharacterExType typ = CharacterExType.UNDEFINED;

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> norms = new java.util.ArrayList< >();

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> notNorms = new java.util.ArrayList< >();

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> anafors = new java.util.ArrayList< >();

    public int getTotalCount() {
        return norms.size() + notNorms.size() + anafors.size();
    }


    public int firstPersonInDialogCount = 0;

    public CharacterEx real;

    public com.pullenti.ner.literature.CharacterReferent result;

    public boolean isAnimal() {
        for(CharItemVar a : attrs) {
            if (a.isAnimal()) 
                return true;
        }
        return false;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder tmp = new StringBuilder();
        if (typ != CharacterExType.UNDEFINED) 
            tmp.append(typ.toString()).append(" ");
        if (gender != com.pullenti.morph.MorphGender.UNDEFINED) 
            tmp.append((gender == com.pullenti.morph.MorphGender.FEMINIE ? "F" : (gender == com.pullenti.morph.MorphGender.NEUTER ? "N" : "M"))).append(": ");
        if (age != CharacterAge.UNDEFINED) 
            tmp.append(age.toString()).append(": ");
        if (!shortVariant) {
            for(CharItemVar a : attrs) {
                tmp.append("[").append(a.toString()).append("] ");
            }
        }
        for(NameVariant v : names) {
            if (v != names.get(0)) 
                tmp.append(" / ");
            tmp.append(v.toString());
            if (shortVariant) 
                break;
        }
        return tmp.toString();
    }

    public void addNormal(com.pullenti.ner.ReferentToken t, com.pullenti.morph.MorphGender gen) {
        gender = com.pullenti.morph.MorphGender.of((gender.value()) & (gen.value()));
        norms.add(t);
        CharacterVariant var = (CharacterVariant)com.pullenti.n2j.Utils.cast(t.getReferent(), CharacterVariant.class);
        if (var != null) 
            addVariant(var);
    }

    public void addVariant(CharacterVariant var) {
        if (var.items.size() == 0) 
            return;
        if (var.items.get(0).isAttr()) {
            if (var.items.get(0).age != CharacterAge.UNDEFINED && var.items.size() > 1) 
                age = CharacterAge.of((age.value()) | (var.items.get(0).age.value()));
            boolean ex = false;
            for(CharItemVar a : attrs) {
                if (CharItemVar.isEquals(a, var.items.get(0))) 
                    ex = true;
            }
            if (!ex) {
                if (var.items.size() == 1 && var.items.get(0).isEmptyAttr() && var.items.get(0).getRefCharacter() == null) {
                }
                else 
                    attrs.add(var.items.get(0));
            }
        }
        for(NameVariant v : names) {
            if (v.isEqualWithCV(var)) 
                return;
        }
        NameVariant ve = new NameVariant(var);
        if (ve.items.size() > 0) 
            names.add(ve);
    }

    public boolean canBeEqual(CharacterVariant var, com.pullenti.morph.MorphGender gen) {
        if (typ == CharacterExType.FIRSTPERSON) 
            return false;
        if (gen != com.pullenti.morph.MorphGender.UNDEFINED) {
            if ((((gen.value()) & (gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                return false;
        }
        if (age != CharacterAge.UNDEFINED) {
            if (var.items.get(0).isAttr() && var.items.get(0).age != CharacterAge.UNDEFINED) {
                if ((((age.value()) & (var.items.get(0).age.value()))) == (CharacterAge.UNDEFINED.value())) 
                    return false;
            }
        }
        if (var.items.get(0).isAttr()) {
            if (var.items.get(0).isAnimal()) {
                for(CharItemVar a : attrs) {
                    if (CharItemVar.isEquals(a, var.items.get(0))) {
                    }
                    else 
                        return false;
                }
            }
        }
        if (var.items.size() == 1 && var.items.get(0).isAttr()) {
            if (var.items.get(0).isEmptyAttr()) 
                return true;
            if (var.items.get(0).isEmoAttr()) 
                return false;
            for(CharItemVar a : attrs) {
                if (CharItemVar.isEquals(a, var.items.get(0))) 
                    return true;
            }
            return false;
        }
        for(NameVariant v : names) {
            if (v.isEqualWithCV(var)) 
                return true;
        }
        NameVariant vex = new NameVariant(var);
        for(NameVariant v : names) {
            if (v.items.size() > 0 && v.canBeEqualWith(vex)) 
                return true;
        }
        return false;
    }

    public boolean canNotBeEqual(CharacterEx sec) {
        if (sec.gender != gender) 
            return true;
        if (sec.age != CharacterAge.UNDEFINED && age != CharacterAge.UNDEFINED) {
            if ((((sec.age.value()) & (age.value()))) == (CharacterAge.UNDEFINED.value())) 
                return true;
        }
        if (sec.typ == CharacterExType.FIRSTPERSON) 
            return true;
        if (typ == CharacterExType.FIRSTPERSON) 
            return false;
        int m1;
        int m2;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1481 = new com.pullenti.n2j.Outargwrapper< >();
        java.util.HashMap<String, Integer> nams1 = _getNamesDict(gender, inoutarg1481);
        m1 = (inoutarg1481.value != null ? inoutarg1481.value : 0);
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg1480 = new com.pullenti.n2j.Outargwrapper< >();
        java.util.HashMap<String, Integer> nams2 = sec._getNamesDict(gender, inoutarg1480);
        m2 = (inoutarg1480.value != null ? inoutarg1480.value : 0);
        if (nams1.size() > 0 && nams2.size() > 0 && ((m1 & m2)) != 0) {
            int exi = 0;
            int notexi = 0;
            for(java.util.Map.Entry<String, Integer> kp : nams1.entrySet()) {
                if (nams2.containsKey(kp.getKey())) 
                    exi |= kp.getValue();
                else 
                    notexi |= kp.getValue();
            }
            for(java.util.Map.Entry<String, Integer> kp : nams2.entrySet()) {
                if (nams1.containsKey(kp.getKey())) 
                    exi |= kp.getValue();
                else 
                    notexi |= kp.getValue();
            }
            int err = notexi & (~exi);
            err &= (m1 & m2);
            if (err != 0) 
                return true;
        }
        if (isAnimal() != sec.isAnimal()) 
            return true;
        if (isAnimal()) {
            for(CharItemVar a : attrs) {
                if (a.isAnimal()) {
                    for(CharItemVar aa : sec.attrs) {
                        if (aa.isAnimal()) {
                            if (aa.item != a.item) 
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isFullEquivalentWith(CharacterEx cha) {
        if (cha.gender != gender) 
            return false;
        if (cha.age != CharacterAge.UNDEFINED && age != CharacterAge.UNDEFINED) {
            if ((((cha.age.value()) & (age.value()))) == (CharacterAge.UNDEFINED.value())) 
                return false;
        }
        if (names.size() != cha.names.size()) 
            return false;
        if (names.size() > 0) {
            for(NameVariant v : names) {
                if (v.items.size() > 0) {
                    boolean ex = false;
                    for(NameVariant vv : cha.names) {
                        if (vv.isEqualWithNV(v)) {
                            ex = true;
                            break;
                        }
                    }
                    if (!ex) 
                        return false;
                }
            }
            return true;
        }
        if (attrs.size() != cha.attrs.size()) 
            return false;
        for(CharItemVar a : attrs) {
            boolean ex = false;
            for(CharItemVar aa : cha.attrs) {
                if (CharItemVar.isEquals(a, aa)) {
                    ex = true;
                    break;
                }
            }
            if (!ex) 
                return false;
        }
        return true;
    }

    public boolean canBeMergedByResult(CharacterEx cha) {
        if (cha.gender != gender) 
            return false;
        if (cha.age != CharacterAge.UNDEFINED && age != CharacterAge.UNDEFINED) {
            if ((((cha.age.value()) & (age.value()))) == (CharacterAge.UNDEFINED.value())) 
                return false;
        }
        for(NameVariant v : names) {
            if (v.items.size() > 0) {
                for(NameVariant vv : cha.names) {
                    if (vv.items.size() > 0 && vv.isEqualWithNV(v)) 
                        return true;
                }
            }
        }
        if (names.size() == 1) {
            for(NameVariant vv : cha.names) {
                if (vv.canBeEqualWith(names.get(0))) 
                    return true;
            }
            if (cha.names.size() > 1 && names.get(0).canBeEqualWithList(cha.names)) 
                return true;
        }
        if (cha.names.size() == 1) {
            for(NameVariant vv : names) {
                if (vv.canBeEqualWith(cha.names.get(0))) 
                    return true;
            }
            if (names.size() > 1 && cha.names.get(0).canBeEqualWithList(names)) 
                return true;
        }
        if (cha.names.size() == 0 || names.size() == 0) {
            for(CharItemVar a : attrs) {
                for(CharItemVar aa : cha.attrs) {
                    if (CharItemVar.isEquals(aa, a)) {
                        if (aa.isAnimal()) 
                            return true;
                    }
                }
            }
        }
        java.util.ArrayList<String> nams = _getNamesStrs(1, gender);
        if (nams.size() == 1) {
            java.util.ArrayList<String> nams2 = cha._getNamesStrs(1, gender);
            if (nams2.size() == 1 && com.pullenti.n2j.Utils.stringsEq(nams.get(0), nams2.get(0))) {
                java.util.ArrayList<String> secs = _getNamesStrs(2, gender);
                java.util.ArrayList<String> secs2 = cha._getNamesStrs(2, gender);
                if (secs.size() == secs2.size() && ((secs.size() == 0 || com.pullenti.n2j.Utils.stringsEq(secs.get(0), secs2.get(0))))) {
                }
            }
        }
        return false;
    }

    public void mergeWith(CharacterEx cha) {
        if (cha == this) 
            return;
        cha.real = this;
        firstPersonInDialogCount += cha.firstPersonInDialogCount;
        for(NameVariant v : cha.names) {
            boolean ex = false;
            for(NameVariant vv : names) {
                if (vv.isEqualWithNV(v)) {
                    ex = true;
                    break;
                }
            }
            if (!ex) 
                names.add(v);
        }
        for(CharItemVar a : cha.attrs) {
            boolean ex = false;
            for(CharItemVar aa : attrs) {
                if (CharItemVar.isEquals(aa, a)) 
                    ex = true;
            }
            if (!ex) 
                attrs.add(a);
        }
        for(com.pullenti.ner.ReferentToken n : cha.norms) {
            n.referent = this;
            norms.add(n);
        }
        for(com.pullenti.ner.ReferentToken n : cha.notNorms) {
            n.referent = this;
            notNorms.add(n);
        }
        if (cha.age != CharacterAge.UNDEFINED) {
            if (age != CharacterAge.UNDEFINED && (((age.value()) & (cha.age.value()))) != (CharacterAge.UNDEFINED.value())) 
                age = CharacterAge.of((age.value()) & (cha.age.value()));
            else 
                age = CharacterAge.of((age.value()) | (cha.age.value()));
        }
    }

    private java.util.HashMap<String, Integer> _getNamesDict(com.pullenti.morph.MorphGender gen, com.pullenti.n2j.Outargwrapper<Integer> ma) {
        ma.value = 0;
        java.util.HashMap<String, Integer> res = new java.util.HashMap< >();
        for(int i = 1; i <= 3; i++) {
            java.util.ArrayList<String> strs = _getNamesStrs(i, gen);
            int mask = (i == 3 ? 4 : i);
            if (strs != null) {
                for(String s : strs) {
                    if (res.containsKey(s)) 
                        res.put(s, res.get(s) | mask);
                    else 
                        res.put(s, mask);
                    ma.value |= mask;
                }
            }
        }
        return res;
    }

    private java.util.ArrayList<String> _getNamesStrs(int ty, com.pullenti.morph.MorphGender gen) {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        java.util.ArrayList<CharItemVar> its = _getNames(ty);
        if (its != null) {
            for(CharItemVar it : its) {
                String s = it.occures.getNominativeValue(gen);
                if (it.item.fullVariant != null) {
                    String ss = it.item.fullVariant.occures.getNominativeValue(gen);
                    if (ss == null) 
                        ss = it.item.fullVariant.values.get(0);
                    if (ss != null) 
                        s = ss;
                }
                if (s != null && !res.contains(s)) 
                    res.add(s);
            }
        }
        return res;
    }

    private java.util.ArrayList<CharItemVar> _getNames(int ty) {
        java.util.ArrayList<CharItemVar> res = new java.util.ArrayList< >();
        for(NameVariant v : names) {
            if (v.items.size() == 0) 
                continue;
            if (ty == 1) {
                if (v.items.get(0).isCanBeFirstName()) 
                    res.add(v.items.get(0));
                else if (v.items.size() > 1) {
                    if (v.items.get(1).isCanBeFirstName()) 
                        res.add(v.items.get(1));
                    else if (v.items.get(1).isCanBeMiddleName() && !v.items.get(0).isCanBeLastName()) 
                        res.add(v.items.get(0));
                    else if (v.items.size() == 2 && v.items.get(1).isCanBeLastName()) 
                        res.add(v.items.get(0));
                }
            }
            else if (ty == 2) {
                if (((v.items.size() == 2 || v.items.size() == 3)) && v.items.get(1).isCanBeMiddleName()) 
                    res.add(v.items.get(1));
                else if (v.items.size() == 3 && v.items.get(2).isCanBeMiddleName() && ((v.items.get(0).isCanBeLastName() || v.items.get(1).isCanBeFirstName()))) 
                    res.add(v.items.get(2));
            }
            else {
                CharItemVar last = v.items.get(v.items.size() - 1);
                if (v.items.get(0).isCanBeLastName() && !v.items.get(0).isCanBeFirstName()) 
                    res.add(v.items.get(0));
                else if (last.isCanBeLastName() && v.items.size() > 1) 
                    res.add(last);
                else if (v.items.size() == 2 && v.items.get(0).isCanBeFirstName() && !v.items.get(1).isCanBeMiddleName()) 
                    res.add(last);
            }
        }
        if (ty != 1 && ty != 2 && res.size() == 0) {
            for(NameVariant v : names) {
                if (v.items.size() == 0) 
                    continue;
                CharItemVar last = v.items.get(v.items.size() - 1);
                if (last.isCanBeFirstName() || last.isCanBeMiddleName()) 
                    continue;
                boolean ok = true;
                for(NameVariant vv : names) {
                    int i;
                    for(i = 0; i < vv.items.size(); i++) {
                        if (CharItemVar.isEquals(vv.items.get(i), last)) 
                            break;
                    }
                    if (i < (vv.items.size() - 1)) {
                        ok = false;
                        break;
                    }
                }
                if (ok) 
                    res.add(last);
            }
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            for(int j = i + 1; j < res.size(); j++) {
                if (CharItemVar.isEquals(res.get(i), res.get(j))) {
                    res.remove(j);
                    j--;
                }
            }
        }
        return res;
    }

    @Override
    public int compareTo(CharacterEx other) {
        if (getTotalCount() > other.getTotalCount()) 
            return -1;
        if (getTotalCount() < other.getTotalCount()) 
            return 1;
        return 0;
    }

    public com.pullenti.ner.literature.CharacterReferent createReferent() {
        com.pullenti.ner.literature.CharacterReferent res = new com.pullenti.ner.literature.CharacterReferent();
        if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            res.setGender(com.pullenti.ner.literature.CharacterGender.FEMINIE);
        else if (gender == com.pullenti.morph.MorphGender.MASCULINE) 
            res.setGender(com.pullenti.ner.literature.CharacterGender.MASCULINE);
        res.setTyp(com.pullenti.ner.literature.CharacterType.MAN);
        for(NameVariant v : names) {
            if (v.items.size() > 0) {
                if (norms.size() > 0) {
                    java.util.HashMap<String, Integer> li = v.getNames(norms);
                    if (li != null && li.size() > 0) {
                        for(java.util.Map.Entry<String, Integer> n : li.entrySet()) {
                            res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, n.getKey(), false, n.getValue());
                        }
                        continue;
                    }
                }
                String nam = v.getNormalName(gender);
                if (nam != null) 
                    res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, nam, false, v.items.get(0).occures.getOccursCount());
            }
        }
        java.util.ArrayList<String> nams = _getNamesStrs(1, gender);
        java.util.ArrayList<String> surs = _getNamesStrs(0, gender);
        java.util.ArrayList<String> secs = _getNamesStrs(2, gender);
        if (nams != null) {
            for(String n : nams) {
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_FIRSTNAME, n, false, 0);
            }
        }
        if (secs != null && secs.size() == 1) {
            for(String n : secs) {
                if (surs != null && surs.contains(n)) 
                    continue;
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_MIDDLENAME, n, false, 0);
            }
        }
        if (surs != null && surs.size() > 0) {
            for(String n : surs) {
                if (secs != null && secs.contains(n)) 
                    continue;
                if (nams != null && nams.contains(n)) 
                    continue;
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_LASTNAME, n, false, 0);
            }
        }
        for(CharItemVar a : attrs) {
            if (a.isAnimal()) 
                res.setTyp(com.pullenti.ner.literature.CharacterType.ANIMAL);
            String s = a.item.values.get(0);
            if (s == null) 
                continue;
            s = s.toLowerCase();
            if (a.isEmoAttr()) 
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_EMO, s, false, 0);
            else if (a.isEmptyAttr()) 
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_MISC, s, false, 0);
            else 
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, s, false, 0);
            if (a.getRefCharacter() != null || a.persProp != null) {
                com.pullenti.ner.literature.LinkReferent link = new com.pullenti.ner.literature.LinkReferent();
                if (a.persProp != null) {
                    link.setName(a.persProp.toString(true, new com.pullenti.morph.MorphLang(null), 0));
                    link.setShortName(a.item.values.get(0).toLowerCase());
                }
                else if (a.isAnimal()) 
                    link.setName("собственность");
                else 
                    link.setName(a.item.values.get(0).toLowerCase());
                link.setTag(a);
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, link, false, 0);
            }
        }
        if (res.getTyp() != com.pullenti.ner.literature.CharacterType.ANIMAL && attrs.size() == 0) {
            boolean undef = true;
            if (names.size() > 0 && names.get(0).items.size() > 0) {
                if (names.get(0).items.get(0).isCanBeFirstName()) 
                    undef = false;
                else if (names.get(0).items.size() > 1) {
                    if (names.get(0).items.get(1).isCanBeMiddleName() || names.get(0).items.get(1).isCanBeLastName()) 
                        undef = false;
                }
            }
            if (firstPersonInDialogCount > 2) 
                undef = false;
            else {
                int inBr = 0;
                int tot = norms.size() + notNorms.size();
                for(com.pullenti.ner.ReferentToken n : norms) {
                    if (com.pullenti.ner.core.BracketHelper.isBracket(n.getBeginToken().getPrevious(), true) && com.pullenti.ner.core.BracketHelper.isBracket(n.getEndToken().getNext(), true)) 
                        inBr++;
                }
                for(com.pullenti.ner.ReferentToken n : notNorms) {
                    if (com.pullenti.ner.core.BracketHelper.isBracket(n.getBeginToken().getPrevious(), true) && com.pullenti.ner.core.BracketHelper.isBracket(n.getEndToken().getNext(), true)) 
                        inBr++;
                }
                if (inBr == tot || (inBr * 2) > tot) 
                    undef = true;
            }
            if (undef) 
                res.setTyp(com.pullenti.ner.literature.CharacterType.UNDEFINED);
        }
        if (res.getTyp() == com.pullenti.ner.literature.CharacterType.UNDEFINED) {
            if (getTotalCount() == 1) 
                return null;
        }
        return res;
    }

    public static CharacterEx _new1465(com.pullenti.morph.MorphGender _arg1, CharacterExType _arg2) {
        CharacterEx res = new CharacterEx();
        res.gender = _arg1;
        res.typ = _arg2;
        return res;
    }
    public static CharacterEx _new1466(com.pullenti.morph.MorphGender _arg1) {
        CharacterEx res = new CharacterEx();
        res.gender = _arg1;
        return res;
    }
}
