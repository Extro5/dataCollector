/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class DerivateDictionary {

    public com.pullenti.morph.MorphLang lang;

    private boolean m_Inited = false;

    public boolean init(com.pullenti.morph.MorphLang _lang) throws java.io.IOException {
        if (m_Inited) 
            return true;
        // ignored:  assembly = .();
        String rsname = "d_" + _lang.toString() + ".dat";
        String[] names = com.pullenti.morph.properties.Resources.getNames();
        for(String n : names) {
            if (n.toUpperCase().endsWith(rsname.toUpperCase())) {
                Object inf = com.pullenti.morph.properties.Resources.getResourceInfo(n);
                if (inf == null) 
                    continue;
                try (com.pullenti.n2j.Stream stream = com.pullenti.morph.properties.Resources.getStream(n)) {
                    stream.setPosition((long)0);
                    m_AllGroups.clear();
                    ExplanSerializeHelper.deserializeDD(stream, this, true);
                    lang = _lang;
                }
                m_Inited = true;
                return true;
            }
        }
        return false;
    }

    public ExplanTreeNode m_Root = new ExplanTreeNode();

    public void unload() {
        m_Root = new ExplanTreeNode();
        m_AllGroups.clear();
        lang = new com.pullenti.morph.MorphLang(null);
    }

    public java.util.ArrayList<com.pullenti.morph.DerivateGroup> m_AllGroups = new java.util.ArrayList< >();

    public void add(com.pullenti.morph.DerivateGroup dg) {
        m_AllGroups.add(dg);
        for(com.pullenti.morph.DerivateWord w : dg.words) {
            if (w.spelling == null) 
                continue;
            ExplanTreeNode tn = m_Root;
            for(int i = 0; i < w.spelling.length(); i++) {
                short k = (short)w.spelling.charAt(i);
                ExplanTreeNode tn1 = null;
                if (tn.nodes == null) 
                    tn.nodes = new java.util.HashMap< >();
                com.pullenti.n2j.Outargwrapper<ExplanTreeNode> inoutarg1 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres2 = com.pullenti.n2j.Utils.tryGetValue(tn.nodes, k, inoutarg1);
                tn1 = inoutarg1.value;
                if (!inoutres2) 
                    tn.nodes.put(k, (tn1 = new ExplanTreeNode()));
                tn = tn1;
            }
            tn.addGroup(dg);
        }
    }

    public java.util.ArrayList<com.pullenti.morph.DerivateGroup> find(String word, boolean tryCreate, com.pullenti.morph.MorphLang _lang) {
        if (com.pullenti.n2j.Utils.isNullOrEmpty(word)) 
            return null;
        ExplanTreeNode tn = m_Root;
        int i;
        for(i = 0; i < word.length(); i++) {
            short k = (short)word.charAt(i);
            ExplanTreeNode tn1 = null;
            if (tn.nodes == null) 
                break;
            com.pullenti.n2j.Outargwrapper<ExplanTreeNode> inoutarg3 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres4 = com.pullenti.n2j.Utils.tryGetValue(tn.nodes, k, inoutarg3);
            tn1 = inoutarg3.value;
            if (!inoutres4) 
                break;
            tn = tn1;
            if (tn.lazy != null) 
                tn.load();
        }
        Object res = (i < word.length() ? null : tn.groups);
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li;
        if (res instanceof java.util.ArrayList) {
            li = new java.util.ArrayList<com.pullenti.morph.DerivateGroup>((java.util.ArrayList<com.pullenti.morph.DerivateGroup>)com.pullenti.n2j.Utils.cast(res, java.util.ArrayList.class));
            boolean gen = false;
            boolean nogen = false;
            for(com.pullenti.morph.DerivateGroup g : li) {
                if (g.isGenerated) 
                    gen = true;
                else 
                    nogen = true;
            }
            if (gen && nogen) {
                for(i = li.size() - 1; i >= 0; i--) {
                    if (li.get(i).isGenerated) 
                        li.remove(i);
                }
            }
            return li;
        }
        if (res instanceof com.pullenti.morph.DerivateGroup) {
            li = new java.util.ArrayList< >();
            li.add((com.pullenti.morph.DerivateGroup)com.pullenti.n2j.Utils.cast(res, com.pullenti.morph.DerivateGroup.class));
            return li;
        }
        if (word.length() < 4) 
            return null;
        char ch0 = word.charAt(word.length() - 1);
        char ch1 = word.charAt(word.length() - 2);
        char ch2 = word.charAt(word.length() - 3);
        if (ch0 == 'О' || ((ch0 == 'И' && ch1 == 'К'))) {
            String word1 = word.substring(0, 0+(word.length() - 1));
            if ((((li = find(word1 + "ИЙ", false, _lang)))) != null) 
                return li;
            if ((((li = find(word1 + "ЫЙ", false, _lang)))) != null) 
                return li;
            if (ch0 == 'О' && ch1 == 'Н') {
                if ((((li = find(word1 + "СКИЙ", false, _lang)))) != null) 
                    return li;
            }
        }
        else if (((ch0 == 'Я' || ch0 == 'Ь')) && ((word.charAt(word.length() - 2) == 'С'))) {
            String word1 = word.substring(0, 0+(word.length() - 2));
            if ((((li = find(word1, false, _lang)))) != null) 
                return li;
        }
        else if (ch0 == 'Е' && ch1 == 'Ь') {
            String word1 = word.substring(0, 0+(word.length() - 2)) + "ИЕ";
            if ((((li = find(word1, false, _lang)))) != null) 
                return li;
        }
        else if (ch0 == 'Й' && ch2 == 'Н' && tryCreate) {
            char ch3 = word.charAt(word.length() - 4);
            String word1 = null;
            if (ch3 != 'Н') {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch3)) 
                    word1 = word.substring(0, 0+(word.length() - 3)) + "Н" + word.substring(word.length() - 3);
            }
            else 
                word1 = word.substring(0, 0+(word.length() - 4)) + word.substring(word.length() - 3);
            if (word1 != null) {
                if ((((li = find(word1, false, _lang)))) != null) 
                    return li;
            }
        }
        if (ch0 == 'Й' && ch1 == 'О') {
            String word2 = word.substring(0, 0+(word.length() - 2));
            if ((((li = find(word2 + "ИЙ", false, _lang)))) != null) 
                return li;
            if ((((li = find(word2 + "ЫЙ", false, _lang)))) != null) 
                return li;
        }
        if (!tryCreate) 
            return null;
        int len = word.length() - 4;
        for(i = 1; i <= len; i++) {
            String rest = word.substring(i);
            java.util.ArrayList<com.pullenti.morph.DerivateGroup> li1 = find(rest, false, _lang);
            if (li1 == null) 
                continue;
            String pref = word.substring(0, 0+(i));
            java.util.ArrayList<com.pullenti.morph.DerivateGroup> gen = new java.util.ArrayList< >();
            for(com.pullenti.morph.DerivateGroup dg : li1) {
                if (!dg.isDummy && !dg.isGenerated) {
                    if (dg.notGenerate) {
                        if (rest.length() < 5) 
                            continue;
                    }
                    com.pullenti.morph.DerivateGroup gg = dg.createByPrefix(pref, _lang);
                    if (gg != null) {
                        gen.add(gg);
                        add(gg);
                    }
                }
            }
            if (gen.size() == 0) 
                return null;
            return gen;
        }
        return null;
    }
    public DerivateDictionary() {
    }
}
