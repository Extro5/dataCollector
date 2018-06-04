/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharItemContainer {

    public java.util.ArrayList<CharItem> items = new java.util.ArrayList< >();

    private java.util.HashMap<String, CharItem> m_Dic = new java.util.HashMap< >();

    public CharItem createFirstPersonItem(com.pullenti.morph.MorphGender gender) {
        CharItem res;
        com.pullenti.n2j.Outargwrapper<CharItem> inoutarg1492 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1493 = com.pullenti.n2j.Utils.tryGetValue(m_Dic, "Я", inoutarg1492);
        res = inoutarg1492.value;
        if (inoutres1493) 
            return res;
        res = new CharItem();
        res.values.add("Я");
        m_Dic.put(res.values.get(0), res);
        items.add(res);
        return res;
    }

    private static void _createKeyStrings(java.util.ArrayList<String> names, java.util.ArrayList<String> res) {
        StringBuilder tmp = new StringBuilder();
        for(String n : names) {
            tmp.setLength(0);
            tmp.append(n);
            for(int i = 0; i < (tmp.length() - 1); i++) {
                if (tmp.charAt(i) == tmp.charAt(i + 1)) {
                    tmp.delete(i, (i)+1);
                    i--;
                    continue;
                }
            }
            for(int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == 'Ъ' || tmp.charAt(i) == 'Ь') {
                    tmp.delete(i, (i)+1);
                    i--;
                    continue;
                }
                else if (tmp.charAt(i) == 'Э' || tmp.charAt(i) == 'А' || tmp.charAt(i) == 'О') 
                    tmp.setCharAt(i, 'Е');
                else if (tmp.charAt(i) == 'Ы') 
                    tmp.setCharAt(i, 'И');
            }
            for(int i = tmp.length() - 1; i >= 3; i--) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(tmp.charAt(i))) 
                    tmp.setLength(tmp.length() - 1);
                else 
                    break;
            }
            String key = (tmp.length() == 0 ? n : tmp.toString());
            if (!res.contains(key)) 
                res.add(key);
        }
    }

    public com.pullenti.ner.MetaToken tryParse(com.pullenti.ner.Token t, java.util.ArrayList<CharItemVar> res, CharacterVariantContainer cvCnt) {
        java.util.ArrayList<CharItemToken> li = CharItemToken.tryParseList(t);
        if (li == null) 
            return null;
        java.util.ArrayList<String> keys = new java.util.ArrayList< >();
        for(CharItemToken chi : li) {
            keys.clear();
            if (chi.typ == CharItemType.ATTR) 
                keys.addAll(chi.values);
            else 
                _createKeyStrings(chi.values, keys);
            java.util.ArrayList<CharItem> cis = null;
            for(String k : keys) {
                CharItem ci;
                com.pullenti.n2j.Outargwrapper<CharItem> inoutarg1494 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres1495 = com.pullenti.n2j.Utils.tryGetValue(m_Dic, k, inoutarg1494);
                ci = inoutarg1494.value;
                if (inoutres1495) {
                    if (cis == null) 
                        cis = new java.util.ArrayList< >();
                    if (!cis.contains(ci)) 
                        cis.add(ci);
                }
            }
            if (cis == null) {
                if (chi.values.contains("Я")) 
                    return null;
                CharItem ci = new CharItem();
                ci.values.addAll(chi.values);
                ci.occures.addOccur(chi);
                items.add(ci);
                for(String k : keys) {
                    m_Dic.put(k, ci);
                }
                res.add(new CharItemVar(ci, chi));
                if (chi.fullValues.size() > 0) {
                    keys.clear();
                    _createKeyStrings(chi.fullValues, keys);
                    cis = null;
                    for(String k : keys) {
                        CharItem ci1;
                        com.pullenti.n2j.Outargwrapper<CharItem> inoutarg1496 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres1497 = com.pullenti.n2j.Utils.tryGetValue(m_Dic, k, inoutarg1496);
                        ci1 = inoutarg1496.value;
                        if (inoutres1497) {
                            if (cis == null) 
                                cis = new java.util.ArrayList< >();
                            if (!cis.contains(ci1)) 
                                cis.add(ci1);
                        }
                    }
                    if (cis == null) {
                        CharItem ci0 = new CharItem();
                        ci0.values.addAll(chi.fullValues);
                        items.add(ci0);
                        for(String k : keys) {
                            m_Dic.put(k, ci0);
                        }
                        ci.fullVariant = ci0;
                    }
                    else 
                        ci.fullVariant = cis.get(0);
                }
                continue;
            }
            if (cis.size() > 1) {
            }
            for(String k : keys) {
                if (!m_Dic.containsKey(k)) 
                    m_Dic.put(k, cis.get(0));
            }
            cis.get(0).occures.addOccur(chi);
            res.add(new CharItemVar(cis.get(0), chi));
        }
        return com.pullenti.ner.MetaToken._new564(li.get(0).getBeginToken(), li.get(li.size() - 1).getEndToken(), li.get(0).getMorph());
    }
    public CharItemContainer() {
    }
}
