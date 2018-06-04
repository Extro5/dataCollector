/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class MorphRule {

    public int id;

    public java.util.HashMap<String, java.util.ArrayList<MorphRuleVariant>> variants = new java.util.HashMap< >();

    public java.util.ArrayList<java.util.ArrayList<MorphRuleVariant>> variantsList = new java.util.ArrayList< >();

    public java.util.ArrayList<String> variantsKey = new java.util.ArrayList< >();

    public LazyInfo lazy;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(String k : variantsKey) {
            if (res.length() > 0) 
                res.append(", ");
            res.append("-").append(k);
        }
        return res.toString();
    }

    public void add(String tail, MorphRuleVariant var) {
        tail = com.pullenti.morph.LanguageHelper.correctWord(tail);
        if (var._getClass().isUndefined()) {
        }
        java.util.ArrayList<MorphRuleVariant> li;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<MorphRuleVariant>> inoutarg33 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres34 = com.pullenti.n2j.Utils.tryGetValue(variants, tail, inoutarg33);
        li = inoutarg33.value;
        if (!inoutres34) {
            li = new java.util.ArrayList< >();
            variants.put(tail, li);
        }
        var.tail = tail;
        li.add(var);
        var.rule = this;
    }

    public void processResult(java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, String wordBegin, java.util.ArrayList<MorphRuleVariant> mvs) {
        for(MorphRuleVariant mv : mvs) {
            com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(mv, null);{
                    if (mv.normalTail != null && mv.normalTail.length() > 0 && mv.normalTail.charAt(0) != '-') 
                        r.normalCase = wordBegin + mv.normalTail;
                    else 
                        r.normalCase = wordBegin;
                }
            if (mv.fullNormalTail != null) {
                if (mv.fullNormalTail.length() > 0 && mv.fullNormalTail.charAt(0) != '-') 
                    r.normalFull = wordBegin + mv.fullNormalTail;
                else 
                    r.normalFull = wordBegin;
            }
            if (!com.pullenti.morph.MorphWordForm.hasMorphEquals(res, r)) {
                r.undefCoef = (short)0;
                res.add(r);
            }
        }
    }
    public MorphRule() {
    }
}
