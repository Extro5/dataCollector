/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.titlepage.internal;

public class PersonRelation {

    public com.pullenti.ner.person.PersonReferent person;

    public java.util.HashMap<TitleItemToken.Types, Float> coefs = new java.util.HashMap< >();

    public TitleItemToken.Types getBest() {
        TitleItemToken.Types res = TitleItemToken.Types.UNDEFINED;
        float max = (float)0;
        for(java.util.Map.Entry<TitleItemToken.Types, Float> v : coefs.entrySet()) {
            if (v.getValue() > max) {
                res = v.getKey();
                max = v.getValue();
            }
            else if (v.getValue() == max) 
                res = TitleItemToken.Types.UNDEFINED;
        }
        return res;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(person.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0)).append(" ").append(getBest().toString());
        for(java.util.Map.Entry<TitleItemToken.Types, Float> v : coefs.entrySet()) {
            res.append(" ").append(v.getValue()).append("(").append(v.getKey().toString()).append(")");
        }
        return res.toString();
    }

    public static PersonRelation _new2592(com.pullenti.ner.person.PersonReferent _arg1) {
        PersonRelation res = new PersonRelation();
        res.person = _arg1;
        return res;
    }
    public PersonRelation() {
    }
}
