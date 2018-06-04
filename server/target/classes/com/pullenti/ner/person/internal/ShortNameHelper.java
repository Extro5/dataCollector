/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.person.internal;

public class ShortNameHelper {

    private static java.util.HashMap<String, java.util.ArrayList<ShortnameVar>> m_Shorts_Names = new java.util.HashMap< >();

    public static class ShortnameVar {
    
        public String name;
    
        public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;
    
        @Override
        public String toString() {
            return name;
        }
    
        public static ShortnameVar _new2385(String _arg1, com.pullenti.morph.MorphGender _arg2) {
            ShortnameVar res = new ShortnameVar();
            res.name = _arg1;
            res.gender = _arg2;
            return res;
        }
        public ShortnameVar() {
        }
    }


    public static java.util.ArrayList<String> getShortnamesForName(String name) {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(java.util.Map.Entry<String, java.util.ArrayList<ShortnameVar>> kp : m_Shorts_Names.entrySet()) {
            for(ShortnameVar v : kp.getValue()) {
                if (com.pullenti.n2j.Utils.stringsEq(v.name, name)) {
                    if (!res.contains(kp.getKey())) 
                        res.add(kp.getKey());
                }
            }
        }
        return res;
    }

    public static java.util.ArrayList<ShortnameVar> getNamesForShortname(String shortname) {
        java.util.ArrayList<ShortnameVar> res;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<ShortnameVar>> inoutarg2383 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2384 = com.pullenti.n2j.Utils.tryGetValue(m_Shorts_Names, shortname, inoutarg2383);
        res = inoutarg2383.value;
        if (!inoutres2384) 
            return null;
        else 
            return res;
    }

    private static boolean m_Inited = false;

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        String obj = ResourceHelper.getString("ShortNames.txt");
        if (obj != null) {
            com.pullenti.ner.core.AnalysisKit kit = new com.pullenti.ner.core.AnalysisKit(new com.pullenti.ner.SourceOfAnalysis(obj), false, new com.pullenti.morph.MorphLang(null), null);
            for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isNewlineBefore()) {
                    com.pullenti.morph.MorphGender g = (t.isValue("F", null) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE);
                    t = t.getNext();
                    String nam = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    java.util.ArrayList<String> shos = new java.util.ArrayList< >();
                    for(t = t.getNext(); t != null; t = t.getNext()) {
                        if (t.isNewlineBefore()) 
                            break;
                        else 
                            shos.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                    }
                    for(String s : shos) {
                        java.util.ArrayList<ShortnameVar> li = null;
                        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<ShortnameVar>> inoutarg2386 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2387 = com.pullenti.n2j.Utils.tryGetValue(m_Shorts_Names, s, inoutarg2386);
                        li = inoutarg2386.value;
                        if (!inoutres2387) 
                            m_Shorts_Names.put(s, (li = new java.util.ArrayList< >()));
                        li.add(ShortnameVar._new2385(nam, g));
                    }
                    if (t == null) 
                        break;
                    t = t.getPrevious();
                }
            }
        }
    }
    public ShortNameHelper() {
    }
    public static ShortNameHelper _globalInstance;
    static {
        _globalInstance = new ShortNameHelper(); 
    }
}
