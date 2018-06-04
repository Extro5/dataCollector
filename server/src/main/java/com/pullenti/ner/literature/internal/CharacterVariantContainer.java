/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharacterVariantContainer {

    public java.util.ArrayList<CharacterVariant> all = new java.util.ArrayList< >();

    private java.util.HashMap<String, java.util.ArrayList<CharacterVariant>> m_HashByFirst = new java.util.HashMap< >();

    public void mergeCharItems(CharItem ci, CharItem ci2) {
        java.util.ArrayList<CharacterVariant> chas2 = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1484 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1485 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, ci2.values.get(0), inoutarg1484);
        chas2 = inoutarg1484.value;
        if (inoutres1485) 
            m_HashByFirst.remove(ci2.values.get(0));
        for(CharacterVariant it : all) {
            for(int i = 0; i < it.items.size(); i++) {
                if (it.items.get(i).item == ci2) 
                    it.items.get(i).item = ci;
            }
        }
        if (chas2 != null) {
            java.util.ArrayList<CharacterVariant> chas = null;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1482 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres1483 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, ci.values.get(0), inoutarg1482);
            chas = inoutarg1482.value;
            if (inoutres1483) 
                chas.addAll(chas2);
            else 
                m_HashByFirst.put(ci.values.get(0), chas2);
        }
    }

    public java.util.ArrayList<CharacterVariant> findOnFirst(CharItem item) {
        java.util.ArrayList<CharacterVariant> chas = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1486 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1487 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, item.values.get(0), inoutarg1486);
        chas = inoutarg1486.value;
        if (!inoutres1487) 
            m_HashByFirst.put(item.values.get(0), (chas = new java.util.ArrayList< >()));
        return chas;
    }

    public CharacterVariant find(java.util.ArrayList<CharItemVar> items) {
        if (items == null || items.size() == 0) 
            return null;
        java.util.ArrayList<CharacterVariant> chas = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1488 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1489 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, items.get(0).item.values.get(0), inoutarg1488);
        chas = inoutarg1488.value;
        if (!inoutres1489) 
            m_HashByFirst.put(items.get(0).item.values.get(0), (chas = new java.util.ArrayList< >()));
        for(CharacterVariant ch : chas) {
            if (ch.items.size() == items.size()) {
                if (!CharItemVar.isEquals(ch.items.get(0), items.get(0))) 
                    continue;
                int i;
                for(i = 1; i < items.size(); i++) {
                    if (!CharItemVar.isEquals(ch.items.get(i), items.get(i))) 
                        break;
                }
                if (i >= items.size()) 
                    return ch;
            }
        }
        return null;
    }

    public CharacterVariant register(java.util.ArrayList<CharItemVar> items) {
        CharacterVariant res = find(items);
        if (res != null) {
            for(int i = 0; i < items.size(); i++) {
                res.items.get(i).addOccures(items.get(i));
            }
            return res;
        }
        res = new CharacterVariant();
        res.items.addAll(items);
        all.add(res);
        java.util.ArrayList<CharacterVariant> chas = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1490 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1491 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, items.get(0).item.values.get(0), inoutarg1490);
        chas = inoutarg1490.value;
        if (!inoutres1491) 
            m_HashByFirst.put(items.get(0).item.values.get(0), (chas = new java.util.ArrayList< >()));
        chas.add(res);
        return res;
    }
    public CharacterVariantContainer() {
    }
}
