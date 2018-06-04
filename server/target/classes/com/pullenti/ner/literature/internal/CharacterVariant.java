/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharacterVariant extends com.pullenti.ner.Referent implements Comparable<CharacterVariant> {

    public CharacterVariant() {
        super("CHAR");
    }

    public java.util.ArrayList<CharItemVar> items = new java.util.ArrayList< >();

    /**
     * Это встречаемости в нормальной форме
     */
    public java.util.ArrayList<com.pullenti.ner.ReferentToken> normOccures = new java.util.ArrayList< >();

    /**
     * Это для "дочь Пупкина"
     */
    public CharacterVariant linkVariant;

    public int getNotEmptyItemsCount() {
        int cou = items.size();
        if (cou > 0 && items.get(0).isEmptyAttr()) 
            cou--;
        if (linkVariant != null && cou == 1) 
            cou += linkVariant.getNotEmptyItemsCount();
        return cou;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (!shortVariant) {
            if (isError) 
                res.append("[ERROR] ");
            if (result != null) 
                res.append("[R] ");
            if (isCharacter) 
                res.append("[C] ");
            com.pullenti.morph.MorphGender g = getGender();
            if (g == com.pullenti.morph.MorphGender.MASCULINE) 
                res.append("(M) ");
            else if (g == com.pullenti.morph.MorphGender.FEMINIE) 
                res.append("(F) ");
            else if (g == com.pullenti.morph.MorphGender.NEUTER) 
                res.append("(N) ");
        }
        for(CharItemVar it : items) {
            if (it.isNeedPrev()) 
                res.append("+ ");
            res.append(it.item.values.get(0)).append(" ");
            if (it.isNeedNext()) 
                res.append("+ ");
            if (linkVariant != null && it.isAttr()) 
                break;
        }
        if (linkVariant != null) 
            res.append(" => ").append(linkVariant.toString(true, lang, 0)).append(" ");
        if (!shortVariant) 
            res.append(" Total:").append(totalCount).append(" Verb:").append(withVerbCount).append(" Agent:").append(inDialogAgentCount).append(" Pacient:").append(inDialogPacientCount).append(" ");
        return res.toString();
    }

    public int totalCount;

    public int inDialogPacientCount;

    public int inDialogAgentCount;

    public int withVerbCount;

    public int genderMaleCount;

    public int genderFemaleCount;

    public int genderNeutralCount;

    public int genderError;

    public com.pullenti.morph.MorphGender getGender() {
        if (m_Gender != com.pullenti.morph.MorphGender.UNDEFINED) 
            return m_Gender;
        if (genderMaleCount > ((genderFemaleCount * 2)) && genderMaleCount > ((genderNeutralCount * 2))) 
            return com.pullenti.morph.MorphGender.MASCULINE;
        if (genderFemaleCount > ((genderMaleCount * 2)) && genderFemaleCount > ((genderNeutralCount * 2))) 
            return com.pullenti.morph.MorphGender.FEMINIE;
        if (genderNeutralCount > ((((genderMaleCount + genderFemaleCount)) * 2))) 
            return com.pullenti.morph.MorphGender.NEUTER;
        if (items.size() == 0) 
            return com.pullenti.morph.MorphGender.UNDEFINED;
        com.pullenti.morph.MorphGender g;
        g = items.get(0).getGender();
        if (g == com.pullenti.morph.MorphGender.MASCULINE || g == com.pullenti.morph.MorphGender.FEMINIE) 
            return g;
        return com.pullenti.morph.MorphGender.UNDEFINED;
    }

    public com.pullenti.morph.MorphGender setGender(com.pullenti.morph.MorphGender value) {
        m_Gender = value;
        return value;
    }


    private com.pullenti.morph.MorphGender m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;

    public boolean isError;

    public boolean isCharacter;

    public LitCharacter result;

    /**
     * Проверить, что возможна ошибка стыковки
     * @return 
     */
    public boolean isLinkProbableError() {
        if (items.size() < 2) 
            return false;
        if (totalCount > 0 && totalCount == genderError) 
            return true;
        com.pullenti.morph.MorphGender gen = getGender();
        for(int i = 0; i < (items.size() - 1); i++) {
            int n1 = items.get(i).item.occures.getOccursCount();
            int n2 = items.get(i + 1).item.occures.getOccursCount();
            boolean checkPersAfter = false;
            if (items.get(i).isAttr() && linkVariant != null) 
                checkPersAfter = true;
            else if (!items.get(i).isAttr() && items.get(i).isNounInDict()) 
                checkPersAfter = true;
            if (totalCount == 1) {
            }
            else if (((totalCount * 4) < n1) && ((totalCount * 4) < n2)) {
            }
            else if (!checkPersAfter) 
                continue;
            if (normOccures.size() == 0) {
                int j = -1;
                if (items.size() == 3) 
                    j = 0;
                else if (items.size() == 4 && items.get(0).isAttr()) 
                    j = 1;
                if (j >= 0) {
                    if (items.get(j).isCanBeFirstName() && items.get(j + 1).isCanBeMiddleName() && !items.get(j + 2).isCanBeFirstName()) {
                        if (items.get(j).getGender() == getGender() && items.get(j + 1).getGender() == getGender() && items.get(j + 2).getGender() == getGender()) {
                        }
                    }
                }
                return true;
            }
            for(com.pullenti.ner.ReferentToken no : normOccures) {
                CharItemToken o1 = items.get(i).occures.findOccure(no.beginChar, no.endChar);
                CharItemToken o2 = items.get(i + 1).occures.findOccure(no.beginChar, no.endChar);
                if (o1 == null || o2 == null) 
                    continue;
                String t1 = o1.getTermValue();
                String t2 = o2.getTermValue();
                if (t1 == null || t2 == null) 
                    continue;
                int nom1 = items.get(i).item.occures.canBeNominative(t1, gen);
                int nom2 = items.get(i + 1).item.occures.canBeNominative(t2, gen);
                if (nom1 >= 0 && nom2 >= 0) {
                    if (nom1 != nom2) {
                        if (linkVariant != null && nom1 == 1 && nom2 == 0) {
                            int nom3 = items.get(i + 1).item.occures.canBeGenetive(t2, gen);
                            if (nom3 == 1) 
                                continue;
                        }
                        return true;
                    }
                }
                if (checkPersAfter) {
                    if (nom1 != 1 || nom2 != 1) 
                        return true;
                    int nom3 = items.get(i + 1).item.occures.canBeGenetive(t2, gen);
                    if (nom3 == 1 && items.get(i).isNounInDict()) 
                        return true;
                    items.get(i).setBePersonAfter(false);
                    linkVariant = null;
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(CharacterVariant other) {
        if (getNotEmptyItemsCount() > other.getNotEmptyItemsCount()) 
            return -1;
        if (getNotEmptyItemsCount() < other.getNotEmptyItemsCount()) 
            return 1;
        if (totalCount > other.totalCount) 
            return -1;
        if (totalCount < other.totalCount) 
            return 1;
        return 0;
    }

    public int canBeOneCharacter(CharacterVariant cha) {
        if (cha.items.size() > items.size()) 
            return cha.canBeOneCharacter(this);
        com.pullenti.morph.MorphGender g1 = getGender();
        com.pullenti.morph.MorphGender g2 = cha.getGender();
        if (g1 == com.pullenti.morph.MorphGender.UNDEFINED && result != null) 
            g1 = result.getGender();
        if (g2 == com.pullenti.morph.MorphGender.UNDEFINED && cha.result != null) 
            g2 = cha.result.getGender();
        if (g1 != com.pullenti.morph.MorphGender.UNDEFINED && g2 != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (g1 != g2) 
                return -1;
        }
        if (cha.items.size() == 0) 
            return (cha.items.size() > 0 ? 1 : 0);
        if (linkVariant != null || cha.linkVariant != null) {
            if (cha.linkVariant == linkVariant) {
            }
            else if (cha.linkVariant == null && cha.items.get(0) == items.get(0) && cha.items.size() == 1) 
                return 1;
            else 
                return -1;
        }
        if (items.get(0).isCanBePersonAfter() || cha.items.get(0).isCanBePersonAfter()) {
            if (items.get(0) != cha.items.get(0)) 
                return 0;
        }
        if (com.pullenti.n2j.Utils.stringsEq(items.get(0).item.values.get(0), "Я") || com.pullenti.n2j.Utils.stringsEq(cha.items.get(0).item.values.get(0), "Я")) {
            if (items.get(0) != cha.items.get(0)) 
                return 0;
        }
        if (items.size() > cha.items.size()) {
            int i;
            for(i = 0; i < cha.items.size(); i++) {
                if (items.get(i) != cha.items.get(i)) 
                    break;
            }
            if (i >= cha.items.size()) {
                if (items.get(i).isNeedPrev()) 
                    return 0;
                return cha.getNotEmptyItemsCount();
            }
            int d = items.size() - cha.items.size();
            for(i = 0; i < cha.items.size(); i++) {
                if (items.get(i + d) != cha.items.get(i)) 
                    break;
            }
            if (i >= cha.items.size()) {
                if (items.get(0).isCanBePersonAfter()) 
                    return 0;
                if (items.get(d - 1).isNeedNext()) 
                    return 0;
                return cha.getNotEmptyItemsCount();
            }
            i = items.size() - 1;
            if ((cha.items.size() == 2 && items.size() >= 3 && items.get(i - 1) == cha.items.get(1)) && items.get(i - 2) == cha.items.get(0)) {
                if (cha.items.get(0).isCanBeFirstName() && cha.items.get(1).isCanBeMiddleName()) {
                    i = items.size() - 4;
                    if ((i < 0) || items.get(i).isAttr()) 
                        return 2;
                }
            }
        }
        if (items.size() == 2 && cha.items.size() == 2) {
            if (items.get(1) == cha.items.get(1)) {
                if (items.get(0).isAttr() != cha.items.get(0).isAttr() && g1 != com.pullenti.morph.MorphGender.UNDEFINED && g2 != com.pullenti.morph.MorphGender.UNDEFINED) 
                    return 1;
                if (items.get(0).isAttr() && cha.items.get(0).isAttr()) {
                    if (items.get(0).isEmptyAttr() && !cha.items.get(0).isCanBePersonAfter()) 
                        return 1;
                    if (cha.items.get(0).isEmptyAttr() && !items.get(0).isCanBePersonAfter()) 
                        return 1;
                }
            }
            if (g1 != com.pullenti.morph.MorphGender.UNDEFINED && g2 != com.pullenti.morph.MorphGender.UNDEFINED) {
                if ((items.get(0).isAttr() && !items.get(0).isCanBePersonAfter() && !items.get(1).isAttr()) && items.get(1) == cha.items.get(0)) 
                    return 1;
                if ((cha.items.get(0).isAttr() && !cha.items.get(0).isCanBePersonAfter() && !cha.items.get(1).isAttr()) && cha.items.get(1) == items.get(0)) 
                    return 1;
            }
            if (items.get(0) == cha.items.get(1) && items.get(1) == cha.items.get(0)) {
                if (items.get(0).isAttr() || items.get(1).isAttr()) 
                    return 2;
            }
        }
        if (items.size() == 3 && cha.items.size() == 2) {
            if (items.get(0) == cha.items.get(0) && items.get(2) == cha.items.get(1) && items.get(0).isAttr()) {
                if (!items.get(1).isAttr()) 
                    return 1;
            }
        }
        if (items.size() == 3 && cha.items.size() == 1) {
            if (cha.items.get(0) == items.get(1) && !cha.items.get(0).isAttr() && !items.get(1).isNeedNext()) 
                return 1;
        }
        if (items.size() == cha.items.size() && items.size() > 2) {
            int i;
            for(i = 1; i < items.size(); i++) {
                if (items.get(i) != cha.items.get(i)) 
                    break;
            }
            if (i >= items.size()) {
                if ((items.get(0).isAttr() && !items.get(0).isCanBePersonAfter() && cha.items.get(0).isAttr()) && !cha.items.get(0).isCanBePersonAfter()) 
                    return items.size() - 1;
            }
        }
        if (cha.items.size() == 2 && cha.items.get(0).isCanBeFirstName() && cha.items.get(1).isCanBeMiddleName()) {
            for(int i = 0; i < (items.size() - 1); i++) {
                if (items.get(i) == cha.items.get(0) && !items.get(i).isNeedNext() && !items.get(i + 1).isCanBeMiddleName()) 
                    return 1;
            }
        }
        if (cha.items.size() == 2 && cha.items.get(0).isAttr() && cha.items.get(1) == items.get(items.size() - 1)) {
            int i = 0;
            if (items.get(0).isAttr()) 
                i++;
            if (((i + 1) < items.size()) && items.get(i).isCanBeFirstName()) {
                i++;
                if (((i + 1) < items.size()) && items.get(i).isCanBeMiddleName()) 
                    i++;
            }
            if ((i + 1) == items.size()) 
                return 1;
        }
        return 0;
    }

    public String getNormalAttr(com.pullenti.morph.MorphGender gen) {
        if (!items.get(0).isAttr()) 
            return null;
        java.util.HashMap<String, Integer> stat = new java.util.HashMap< >();
        for(CharItemToken v : items.get(0).occures.getOccurs()) {
            String val = null;
            if (v.getBeginToken() == v.getEndToken()) 
                val = v.getBeginToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, gen, false);
            else {
                val = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(v, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
                if (val.indexOf('-') > 0) 
                    continue;
            }
            if (val != null) {
                if (!stat.containsKey(val)) 
                    stat.put(val, 1);
                else 
                    stat.put(val, stat.get(val) + 1);
            }
        }
        int max = 0;
        String res = null;
        for(java.util.Map.Entry<String, Integer> kp : stat.entrySet()) {
            if (kp.getValue() > max) {
                max = kp.getValue();
                res = kp.getKey();
            }
        }
        if (res != null) 
            return res.toLowerCase();
        return null;
    }

    public String getNormalName() {
        StringBuilder tmp = new StringBuilder();
        int i;
        int i0;
        for(i0 = 0; i0 < items.size(); i0++) {
            if (!items.get(i0).isAttr()) 
                break;
        }
        if (i0 >= items.size()) 
            return null;
        java.util.HashMap<String, Integer> stat = new java.util.HashMap< >();
        for(com.pullenti.ner.ReferentToken oc : normOccures) {
            tmp.setLength(0);
            com.pullenti.ner.Token tt;
            int sp = i0;
            boolean isQuot = false;
            for(tt = (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(oc, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= oc.endChar; tt = (tt == null ? null : tt.getNext())) {
                if (sp > 0) {
                    sp--;
                    continue;
                }
                if (tt.isHiphen()) {
                    if (tmp.length() == 0) {
                        tt = tt.getNext();
                        continue;
                    }
                    tmp.append('-');
                    isQuot = false;
                }
                else if (com.pullenti.ner.core.BracketHelper.isBracket(tt, true)) 
                    isQuot = true;
                else {
                    if (!isQuot && tmp.length() > 0 && tmp.charAt(tmp.length() - 1) != '-') 
                        tmp.append(' ');
                    if (tt instanceof com.pullenti.ner.TextToken) 
                        tmp.append((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
                    else if (tt instanceof com.pullenti.ner.NumberToken) 
                        tmp.append((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value);
                    isQuot = false;
                }
            }
            if (tmp.length() > 0) {
                String str = tmp.toString();
                if (!stat.containsKey(str)) 
                    stat.put(str, 1);
                else 
                    stat.put(str, stat.get(str) + 1);
            }
        }
        int max = 0;
        String res = null;
        for(java.util.Map.Entry<String, Integer> kp : stat.entrySet()) {
            if (kp.getValue() > max) {
                max = kp.getValue();
                res = kp.getKey();
            }
        }
        if (res != null) 
            return res;
        tmp.setLength(0);
        for(i = i0; i < items.size(); i++) {
            if (tmp.length() > 0) 
                tmp.append(' ');
            String val = items.get(i).occures.getNominativeValue(getGender());
            if (val == null || (((items.get(i).occures.getOccursCount() == 1 && items.get(i).item.occures.getOccursCount() > 10 && !items.get(i).isCanBeFirstName()) && !items.get(i).isCanBeMiddleName()))) 
                val = items.get(i).item.occures.getNominativeValue(getGender());
            if (val == null) 
                val = items.get(0).item.values.get(0);
            tmp.append(val);
        }
        if (tmp.length() > 0) 
            return tmp.toString();
        return null;
    }
}
