/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharItem {

    public java.util.ArrayList<String> values = new java.util.ArrayList< >();

    public OccuresContainer occures = new OccuresContainer();

    public CharItem fullVariant;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(String v : values) {
            if (com.pullenti.n2j.Utils.stringsNe(v, values.get(0))) 
                res.append("/");
            res.append(v);
        }
        if (fullVariant != null) 
            res.append(" FULL: ").append(fullVariant.values.get(0));
        return res.toString();
    }
    public CharItem() {
    }
}
