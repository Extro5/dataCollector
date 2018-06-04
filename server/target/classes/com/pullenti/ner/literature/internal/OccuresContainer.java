/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class OccuresContainer {

    private java.util.ArrayList<CharItemToken> m_Occurs = new java.util.ArrayList< >();

    public Iterable<CharItemToken> getOccurs() {
        return m_Occurs;
    }


    public int getOccursCount() {
        return m_Occurs.size();
    }


    public void addOccur(CharItemToken o) {
        m_Occurs.add(o);
        String str = o.getTermValue();
        if (str == null) 
            return;
        TermStat stat;
        com.pullenti.n2j.Outargwrapper<TermStat> inoutarg1539 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1540 = com.pullenti.n2j.Utils.tryGetValue(m_Terms, str, inoutarg1539);
        stat = inoutarg1539.value;
        if (!inoutres1540) 
            m_Terms.put(str, (stat = new TermStat()));
        stat.total++;
        for(com.pullenti.morph.MorphBaseInfo it : o.getMorph().getItems()) {
            if (!it.getCase().isUndefined() && it.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    continue;
                com.pullenti.morph.MorphWordForm mwf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                if (mwf != null) {
                    if (o.typ == CharItemType.ATTR) {
                        if (!mwf.isInDictionary()) 
                            continue;
                    }
                    else {
                    }
                }
                if (it.getCase().isNominative() || it.getCase().isVocative()) {
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.maleNomCount++;
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.femaleNomCount++;
                }
                else {
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.maleNotNomCount++;
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.femaleNotNomCount++;
                    boolean add = false;
                    if (o.typ != CharItemType.ATTR && mwf != null && com.pullenti.n2j.Utils.stringsNe(mwf.normalCase, str)) {
                        if (mwf.isInDictionary() && ((mwf._getClass().isProperName() || mwf._getClass().isProperSecname() || mwf._getClass().isProperSurname()))) 
                            add = true;
                        else if (mwf._getClass().isProperSurname()) 
                            add = true;
                        if (add) {
                            TermStat stat2;
                            com.pullenti.n2j.Outargwrapper<TermStat> inoutarg1537 = new com.pullenti.n2j.Outargwrapper< >();
                            Boolean inoutres1538 = com.pullenti.n2j.Utils.tryGetValue(m_Terms, mwf.normalCase, inoutarg1537);
                            stat2 = inoutarg1537.value;
                            if (!inoutres1538) 
                                m_Terms.put(mwf.normalCase, (stat2 = new TermStat()));
                            stat2.total++;
                            if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                stat2.maleNomCount++;
                            if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                stat2.femaleNomCount++;
                        }
                    }
                }
                if (it.getCase().isGenitive() && !it.getCase().isNominative()) {
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.maleGenCount++;
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.femaleGenCount++;
                }
                else {
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.maleNotGenCount++;
                    if ((((it.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        stat.femaleNotGenCount++;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(m_Occurs.size()).append(": ");
        if (m_Occurs.size() > 0) 
            res.append(m_Occurs.get(0).toString());
        return res.toString();
    }

    public static class TermStat {
    
        public int total;
    
        public int maleNomCount;
    
        public int maleNotNomCount;
    
        public int femaleNomCount;
    
        public int femaleNotNomCount;
    
        public int maleGenCount;
    
        public int maleNotGenCount;
    
        public int femaleGenCount;
    
        public int femaleNotGenCount;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append(total).append(": Male: ").append(maleNomCount).append("/").append(maleNotNomCount).append(" Fem: ").append(femaleNomCount).append("/").append(femaleNotNomCount);
            return tmp.toString();
        }
        public TermStat() {
        }
    }


    private java.util.HashMap<String, TermStat> m_Terms = new java.util.HashMap< >();

    public int canBeGender(String val, com.pullenti.morph.MorphGender gen) {
        if (gen == com.pullenti.morph.MorphGender.UNDEFINED) 
            return -1;
        TermStat stat;
        com.pullenti.n2j.Outargwrapper<TermStat> inoutarg1541 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1542 = com.pullenti.n2j.Utils.tryGetValue(m_Terms, val, inoutarg1541);
        stat = inoutarg1541.value;
        if (!inoutres1542) 
            return -1;
        if (gen == com.pullenti.morph.MorphGender.MASCULINE) {
            if ((stat.maleNomCount + stat.maleNotNomCount) > 0) 
                return 1;
            if ((stat.femaleNomCount + stat.femaleNotNomCount) > 0) 
                return 0;
        }
        else if (gen == com.pullenti.morph.MorphGender.FEMINIE) {
            if ((stat.femaleNomCount + stat.femaleNotNomCount) > 0) 
                return 1;
            if ((stat.maleNomCount + stat.maleNotNomCount) > 0) 
                return 0;
        }
        return -1;
    }

    public int canBeNominative(String val, com.pullenti.morph.MorphGender gen) {
        TermStat stat;
        com.pullenti.n2j.Outargwrapper<TermStat> inoutarg1543 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1544 = com.pullenti.n2j.Utils.tryGetValue(m_Terms, val, inoutarg1543);
        stat = inoutarg1543.value;
        if (!inoutres1544) 
            return -1;
        if (gen != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (gen == com.pullenti.morph.MorphGender.MASCULINE) {
                if (stat.maleNomCount > 0) 
                    return 1;
                if (stat.maleNotNomCount > 0) 
                    return 0;
            }
            if (gen == com.pullenti.morph.MorphGender.FEMINIE) {
                if (stat.femaleNomCount > 0) 
                    return 1;
                if (stat.femaleNotNomCount > 0) 
                    return 0;
            }
            if ((stat.maleNomCount == 0 && stat.maleNotNomCount == 0 && stat.femaleNomCount == 0) && stat.femaleNotNomCount == 0) 
                return -1;
        }
        for(java.util.Map.Entry<String, TermStat> kp : m_Terms.entrySet()) {
            if (kp.getKey().length() < val.length()) 
                return 0;
            else if (kp.getKey().length() == val.length() && com.pullenti.n2j.Utils.stringsNe(val, kp.getKey()) && kp.getValue().total > stat.total) 
                return 0;
        }
        return 1;
    }

    public int canBeGenetive(String val, com.pullenti.morph.MorphGender gen) {
        TermStat stat;
        com.pullenti.n2j.Outargwrapper<TermStat> inoutarg1545 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres1546 = com.pullenti.n2j.Utils.tryGetValue(m_Terms, val, inoutarg1545);
        stat = inoutarg1545.value;
        if (!inoutres1546) 
            return -1;
        if (gen != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (gen == com.pullenti.morph.MorphGender.MASCULINE) {
                if (stat.maleGenCount > 0) 
                    return 1;
                if (stat.maleNotGenCount > 0) 
                    return 0;
            }
            if (gen == com.pullenti.morph.MorphGender.FEMINIE) {
                if (stat.femaleGenCount > 0) 
                    return 1;
                if (stat.femaleNotGenCount > 0) 
                    return 0;
            }
        }
        char last = val.charAt(val.length() - 1);
        if (last == 'И' || last == 'Ы' || last == 'А') 
            return 1;
        return 0;
    }

    public String getNominativeValue(com.pullenti.morph.MorphGender gen) {
        String res = null;
        int cou = 0;
        for(java.util.Map.Entry<String, TermStat> kp : m_Terms.entrySet()) {
            if (gen == com.pullenti.morph.MorphGender.MASCULINE && kp.getValue().maleNomCount > 0) 
                return kp.getKey();
            if (gen == com.pullenti.morph.MorphGender.FEMINIE && kp.getValue().femaleNomCount > 0) 
                return kp.getKey();
            if (kp.getValue().maleNotNomCount > 0 || kp.getValue().femaleNotNomCount > 0) 
                continue;
            if (res == null) {
                res = kp.getKey();
                cou = kp.getValue().total;
            }
            else if (res.length() > kp.getKey().length() && (cou < kp.getValue().total)) {
                res = kp.getKey();
                cou = kp.getValue().total;
            }
        }
        return res;
    }

    public java.util.ArrayList<String> getTermVariants() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(CharItemToken o : m_Occurs) {
            String str = o.getTermValue();
            if (str != null && !res.contains(str)) 
                res.add(str);
        }
        java.util.Collections.sort(res);
        return res;
    }

    public CharItemToken findOccure(int begin, int end) {
        for(CharItemToken o : m_Occurs) {
            if (begin <= o.beginChar && o.endChar <= end) 
                return o;
            else if (o.beginChar > end) 
                break;
        }
        return null;
    }

    /**
     * Признак того, что встречается только в прямой речи диалогов
     */
    public boolean isAllInDialogs() {
        if (m_Occurs.size() == 0) 
            return false;
        for(CharItemToken o : m_Occurs) {
            if (!DialogItemToken.isInDialog(o)) 
                return false;
        }
        return true;
    }

    public OccuresContainer() {
    }
    public static OccuresContainer _globalInstance;
    static {
        _globalInstance = new OccuresContainer(); 
    }
}
