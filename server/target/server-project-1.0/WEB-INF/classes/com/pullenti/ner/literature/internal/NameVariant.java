/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class NameVariant {

    public java.util.ArrayList<CharItemVar> items = new java.util.ArrayList< >();

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for(CharItemVar n : items) {
            tmp.append("'").append(n.toString()).append("' ");
        }
        return tmp.toString();
    }

    public NameVariant(CharacterVariant var) {
        if (var == null) 
            return;
        items.addAll(var.items);
        if (var.items.get(0).isAttr()) 
            items.remove(0);
    }

    public boolean isEqualWithNV(NameVariant var) {
        if (items.size() != var.items.size()) 
            return false;
        for(int i = 0; i < items.size(); i++) {
            if (!CharItemVar.isEquals(items.get(i), var.items.get(i))) 
                return false;
        }
        return true;
    }

    public boolean isEqualWithCV(CharacterVariant var) {
        int i = 0;
        int j;
        if (var.items.get(0).isAttr()) 
            i++;
        for(j = 0; (i < var.items.size()) && (j < items.size()); i++,j++) {
            if (!CharItemVar.isEquals(items.get(j), var.items.get(i))) 
                return false;
        }
        return i == var.items.size() && j == items.size();
    }

    public boolean canBeEqualWith(NameVariant var) {
        if (var.items.size() > items.size()) 
            return var.canBeEqualWith(this);
        int i;
        if (items.size() == var.items.size()) {
            for(i = 0; i < items.size(); i++) {
                if (!CharItemVar.isEquals(items.get(i), var.items.get(i))) 
                    return false;
            }
            return true;
        }
        if (var.items.size() == 0) 
            return true;
        for(i = 0; i < var.items.size(); i++) {
            if (!CharItemVar.isEquals(items.get(i), var.items.get(i))) 
                break;
        }
        if (i >= var.items.size()) {
            if (var.items.size() == 1 && var.items.get(0).isCanBeFirstName()) 
                return true;
            if (var.items.size() == 2) {
                if (var.items.get(0).isCanBeFirstName() && var.items.get(1).isCanBeMiddleName()) 
                    return true;
                if (var.items.get(0).isCanBeFirstName() || var.items.get(1).isCanBeMiddleName()) {
                    if (items.size() == 3 && items.get(2).isCanBeLastName()) 
                        return true;
                }
            }
            return true;
        }
        i = items.size() - 1;
        if (var.items.size() == 1 && CharItemVar.isEquals(var.items.get(0), items.get(i))) {
            if (items.size() == 2 && items.get(0).isCanBeFirstName() && !var.items.get(0).isCanBeFirstName()) 
                return true;
            if ((items.size() == 3 && items.get(0).isCanBeFirstName() && items.get(1).isCanBeMiddleName()) && !var.items.get(0).isCanBeFirstName()) 
                return true;
            if (var.items.get(0).isCanBeLastName()) 
                return true;
        }
        return false;
    }

    public boolean canBeEqualWithList(java.util.ArrayList<NameVariant> vars) {
        if (vars.size() == 0) 
            return false;
        if (vars.size() == 1) 
            return canBeEqualWith(vars.get(0));
        if (vars.size() == 2) {
            NameVariant tmp = new NameVariant(null);
            tmp.items.addAll(vars.get(0).items);
            tmp.items.addAll(vars.get(1).items);
            if (canBeEqualWith(tmp)) 
                return true;
            tmp.items.clear();
            tmp.items.addAll(vars.get(1).items);
            tmp.items.addAll(vars.get(0).items);
            if (canBeEqualWith(tmp)) 
                return true;
        }
        return false;
    }

    public String getNormalName(com.pullenti.morph.MorphGender gen) {
        if (items.size() == 0) 
            return null;
        String str = items.get(0).occures.getNominativeValue(gen);
        if (str == null || items.size() == 1) 
            return str;
        StringBuilder tmp = new StringBuilder();
        tmp.append(str);
        for(int i = 1; i < items.size(); i++) {
            str = items.get(i).occures.getNominativeValue(gen);
            if (str != null) 
                tmp.append(" ").append(str);
        }
        return tmp.toString();
    }

    public java.util.HashMap<String, Integer> getNames(java.util.ArrayList<com.pullenti.ner.ReferentToken> norms) {
        if (items.size() == 0) 
            return null;
        StringBuilder tmp = new StringBuilder();
        java.util.HashMap<String, Integer> stat = new java.util.HashMap< >();
        for(com.pullenti.ner.ReferentToken n : norms) {
            CharItemToken occ1 = items.get(0).occures.findOccure(n.beginChar, n.endChar);
            if (occ1 == null) 
                continue;
            CharItemToken occ2 = occ1;
            if (items.size() > 0) 
                occ2 = items.get(items.size() - 1).occures.findOccure(n.beginChar, n.endChar);
            if (occ2 == null) 
                continue;
            tmp.setLength(0);
            for(com.pullenti.ner.Token t = occ1.getBeginToken(); t != null && t.endChar <= occ2.endChar; t = t.getNext()) {
                if (t.isHiphen()) {
                    tmp.append('-');
                    continue;
                }
                if (tmp.length() > 0) 
                    tmp.append(' ');
                if (t instanceof com.pullenti.ner.TextToken) 
                    tmp.append((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                else if (t instanceof com.pullenti.ner.NumberToken) 
                    tmp.append((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value);
                else 
                    tmp.append(t.getSourceText().toUpperCase());
            }
            String sss = tmp.toString();
            if (!stat.containsKey(sss)) 
                stat.put(sss, 1);
            else 
                stat.put(sss, stat.get(sss) + 1);
        }
        int max = 0;
        for(java.util.Map.Entry<String, Integer> kp : stat.entrySet()) {
            if (kp.getValue() > max) 
                max = kp.getValue();
        }
        max /= 2;
        java.util.HashMap<String, Integer> res = new java.util.HashMap< >();
        for(java.util.Map.Entry<String, Integer> kp : stat.entrySet()) {
            if (kp.getValue() >= max) 
                res.put(kp.getKey(), kp.getValue());
        }
        return res;
    }
    public NameVariant() {
        this(null);
    }
}
