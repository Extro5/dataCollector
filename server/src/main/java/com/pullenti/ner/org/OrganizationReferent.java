/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org;

/**
 * Организация как сущность
 */
public class OrganizationReferent extends com.pullenti.ner.Referent {

    public OrganizationReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.org.internal.MetaOrganization.globalMeta);
    }

    public static final String OBJ_TYPENAME = "ORGANIZATION";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_EPONYM = "EPONYM";

    public static final String ATTR_HIGHER = "HIGHER";

    public static final String ATTR_OWNER = "OWNER";

    public static final String ATTR_GEO = "GEO";

    public static final String ATTR_KLADR = "KLADR";

    public static final String ATTR_MISC = "MISC";

    public static final String ATTR_PROFILE = "PROFILE";

    /**
     * При выводе в ToString() первым ставить номер, если есть
     */
    public static boolean SHOWNUMBERONFIRSTPOSITION = false;

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        boolean isDep = getKind() == OrganizationKind.DEPARTMENT;
        String name = null;
        String altname = null;
        int namesCount = 0;
        int len = 0;
        boolean noType = false;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                String n = s.getValue().toString();
                namesCount++;
                len += n.length();
            }
        }
        if (namesCount > 0) {
            len /= namesCount;
            if (len > 10) 
                len -= ((len / 7));
            int cou = 0;
            int altcou = 0;
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                    String n = s.getValue().toString();
                    if (n.length() >= len) {
                        if (s.getCount() > cou) {
                            name = n;
                            cou = s.getCount();
                        }
                        else if (s.getCount() == cou) {
                            if (name == null) 
                                name = n;
                            else if (name.length() < n.length()) 
                                name = n;
                        }
                    }
                    else if (s.getCount() > altcou) {
                        altname = n;
                        altcou = s.getCount();
                    }
                    else if (s.getCount() == altcou) {
                        if (altname == null) 
                            altname = n;
                        else if (altname.length() > n.length()) 
                            altname = n;
                    }
                }
            }
        }
        if (name != null) {
            if (altname != null) {
                if ((name.replace(" ", "").indexOf(altname) >= 0)) 
                    altname = null;
            }
            if (altname != null && ((altname.length() > 30 || altname.length() > (name.length() / 2)))) 
                altname = null;
            if (altname == null) {
                for(com.pullenti.ner.Slot s : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                        if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(name, s.getValue().toString())) {
                            altname = s.getValue().toString();
                            break;
                        }
                    }
                }
            }
        }
        else {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                    String nam = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken._getKind(nam, null, this) == OrganizationKind.UNDEFINED) 
                        continue;
                    if (name == null || nam.length() > name.length()) 
                        name = nam;
                    noType = true;
                }
            }
            if (name == null) {
                for(com.pullenti.ner.Slot s : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                        String nam = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                        if (name == null || nam.length() > name.length()) 
                            name = nam;
                        noType = true;
                    }
                }
            }
        }
        boolean outOwnInName = false;
        if (name != null) {
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(name));
            if (((!isDep && namesCount == 0 && getHigher() != null) && getHigher().getHigher() == null && getNumber() == null) && getEponyms().size() == 0) 
                outOwnInName = true;
        }
        if (getNumber() != null) {
            if (SHOWNUMBERONFIRSTPOSITION) 
                res.insert(0, getNumber() + " ");
            else 
                res.append(" №").append(getNumber());
        }
        java.util.ArrayList<String> fams = null;
        for(com.pullenti.ner.Slot r : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), ATTR_EPONYM) && r.getValue() != null) {
                if (fams == null) 
                    fams = new java.util.ArrayList< >();
                fams.add(r.getValue().toString());
            }
        }
        if (fams != null) {
            java.util.Collections.sort(fams);
            res.append(" имени ");
            for(int i = 0; i < fams.size(); i++) {
                if (i > 0 && ((i + 1) < fams.size())) 
                    res.append(", ");
                else if (i > 0) 
                    res.append(" и ");
                res.append(fams.get(i));
            }
        }
        if (altname != null && !isDep) 
            res.append(" (").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(altname)).append(")");
        if (getOwner() != null) 
            res.append("; ").append(getOwner().toString(true, lang, lev + 1));
        if (!shortVariant) {
            if (!noType && !isDep) {
                String typ = null;
                for(String t : getTypes()) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken._getKind(t, null, this) == OrganizationKind.UNDEFINED) 
                        continue;
                    if (typ == null || typ.length() > t.length()) 
                        typ = t;
                }
                if (typ == null) {
                    for(String t : getTypes()) {
                        if (typ == null || typ.length() > t.length()) 
                            typ = t;
                    }
                }
                if (name != null && !com.pullenti.n2j.Utils.isNullOrEmpty(typ) && !Character.isUpperCase(typ.charAt(0))) {
                    if ((name.toUpperCase().indexOf(typ.toUpperCase()) >= 0)) 
                        typ = null;
                }
                if (typ != null) 
                    res.append(", ").append(typ);
            }
            for(com.pullenti.ner.Slot ss : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(ss.getTypeName(), ATTR_GEO) && ss.getValue() != null) 
                    res.append(", ").append(ss.getValue().toString());
            }
            String kl = getStringValue(ATTR_KLADR);
            if (kl != null) 
                res.append(" (КЛАДР № ").append(kl).append(")");
        }
        if (isDep || outOwnInName) {
            for(com.pullenti.ner.Slot ss : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(ss.getTypeName(), ATTR_HIGHER) && (ss.getValue() instanceof com.pullenti.ner.Referent) && (lev < 20)) {
                    OrganizationReferent hi = (OrganizationReferent)com.pullenti.n2j.Utils.cast(ss.getValue(), OrganizationReferent.class);
                    if (hi != null) {
                        java.util.ArrayList<com.pullenti.ner.Referent> tmp = new java.util.ArrayList< >();
                        tmp.add(this);
                        for(; hi != null; hi = hi.getHigher()) {
                            if (tmp.contains(hi)) 
                                break;
                            else 
                                tmp.add(hi);
                        }
                        if (hi != null) 
                            continue;
                    }
                    res.append(';');
                    res.append(" ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(ss.getValue(), com.pullenti.ner.Referent.class))).toString(shortVariant, lang, lev + 1));
                    break;
                }
            }
        }
        if (res.length() == 0) {
            if (getINN() != null) 
                res.append("ИНН: ").append(getINN());
            if (getOGRN() != null) 
                res.append(" ОГРН: ").append(getINN());
        }
        return res.toString();
    }

    @Override
    public String toSortString() {
        return getKind() + toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME) || com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_EPONYM)) {
                String str = s.getValue().toString();
                if (!res.contains(str)) 
                    res.add(str);
                if (str.indexOf(' ') > 0 || str.indexOf('-') > 0) {
                    str = str.replace(" ", "").replace("-", "");
                    if (!res.contains(str)) 
                        res.add(str);
                }
            }
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NUMBER)) 
                res.add(getKind().toString() + " " + s.getValue().toString());
        }
        if (res.size() == 0) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                    String t = s.getValue().toString();
                    if (!res.contains(t)) 
                        res.add(t);
                }
            }
        }
        if (getINN() != null) 
            res.add("ИНН:" + getINN());
        if (getOGRN() != null) 
            res.add("ОГРН:" + getOGRN());
        if (res.size() > 0) 
            return res;
        else 
            return super.getCompareStrings();
    }

    public boolean checkCorrection() {
        if (getSlots().size() < 1) 
            return false;
        String s = toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0).toLowerCase();
        if ((s.indexOf("прокуратура") >= 0) || (s.indexOf("штаб") >= 0) || (s.indexOf("кабинет") >= 0)) 
            return true;
        if (getSlots().size() == 1) {
            if (com.pullenti.n2j.Utils.stringsNe(getSlots().get(0).getTypeName(), ATTR_NAME)) {
                if (getKind() == OrganizationKind.GOVENMENT || getKind() == OrganizationKind.JUSTICE) 
                    return true;
                return false;
            }
        }
        if (findSlot(ATTR_TYPE, null, true) == null && findSlot(ATTR_NAME, null, true) == null) 
            return false;
        if (com.pullenti.n2j.Utils.stringsEq(s, "государственная гражданская служба") || com.pullenti.n2j.Utils.stringsEq(s, "здравоохранения")) 
            return false;
        if (getTypes().contains("колония")) {
            if (getNumber() == null) 
                return false;
        }
        if ((s.indexOf("конгресс") >= 0)) {
            if (findSlot(ATTR_GEO, null, true) == null) 
                return false;
        }
        java.util.ArrayList<String> nams = getNames();
        if (nams.size() == 1 && nams.get(0).length() == 1 && (getTypes().size() < 3)) 
            return false;
        if (nams.contains("ВА")) {
            if (getKind() == OrganizationKind.BANK) 
                return false;
        }
        return true;
    }

    /**
     * Номер ИНН
     */
    public String getINN() {
        return _getMiscValue("ИНН:");
    }

    /**
     * Номер ИНН
     */
    public String setINN(String value) {
        if (value != null) 
            addSlot(ATTR_MISC, "ИНН:" + value, false, 0);
        return value;
    }


    /**
     * Номер ОГРН
     */
    public String getOGRN() {
        return _getMiscValue("ОГРН");
    }

    /**
     * Номер ОГРН
     */
    public String setOGRN(String value) {
        if (value != null) 
            addSlot(ATTR_MISC, "ОГРН:" + value, false, 0);
        return value;
    }


    private String _getMiscValue(String pref) {
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_MISC)) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                    if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "URI")) {
                        String val = r.getStringValue("SCHEME");
                        if (com.pullenti.n2j.Utils.stringsEq(val, pref)) 
                            return r.getStringValue("VALUE");
                    }
                }
                else if (s.getValue() instanceof String) {
                    String str = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                    if (str.startsWith(pref) && str.length() > (pref.length() + 1)) 
                        return str.substring(pref.length() + 1);
                }
            }
        }
        return null;
    }

    private static java.util.ArrayList<String> m_EmptyNames = new java.util.ArrayList< >();

    /**
     * Список имён организации
     */
    public java.util.ArrayList<String> getNames() {
        java.util.ArrayList<String> res = null;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (res == null) 
                    res = new java.util.ArrayList< >();
                res.add(s.getValue().toString());
            }
        }
        return (java.util.ArrayList<String>)com.pullenti.n2j.Utils.notnull(res, m_EmptyNames);
    }


    private String correctName(String name, com.pullenti.n2j.Outargwrapper<Integer> num) {
        num.value = 0;
        if (name == null || (name.length() < 1)) 
            return null;
        if (Character.isDigit(name.charAt(0)) && name.indexOf(' ') > 0) {
            int i;
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg2179 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres2180 = com.pullenti.n2j.Utils.parseInteger(name.substring(0, 0+(name.indexOf(' '))), inoutarg2179);
            i = (inoutarg2179.value != null ? inoutarg2179.value : 0);
            if (inoutres2180) {
                if (i > 1) {
                    num.value = i;
                    name = name.substring(name.indexOf(' ')).trim();
                }
            }
        }
        else if (Character.isDigit(name.charAt(name.length() - 1))) {
            int i;
            for(i = name.length() - 1; i >= 0; i--) {
                if (!Character.isDigit(name.charAt(i))) 
                    break;
            }
            if (i >= 0 && name.charAt(i) == '.') {
            }
            else {
                boolean inoutres2181 = com.pullenti.n2j.Utils.parseInteger(name.substring(i + 1), num);
                if (i > 0 && inoutres2181 && num.value > 0) {
                    if (i < 1) 
                        return null;
                    name = name.substring(0, 0+(i)).trim();
                    if (name.length() > 0 && name.charAt(name.length() - 1) == '-') 
                        name = name.substring(0, 0+(name.length() - 1)).trim();
                }
            }
        }
        return correctName0(name);
    }

    private String correctName0(String name) {
        name = name.toUpperCase();
        if (name.length() > 2 && !Character.isLetterOrDigit(name.charAt(name.length() - 1)) && com.pullenti.n2j.Utils.isWhitespace(name.charAt(name.length() - 2))) 
            name = name.substring(0, 0+(name.length() - 2)) + name.substring(name.length() - 1);
        if ((name.indexOf(" НА СТ.") >= 0)) 
            name = name.replace(" НА СТ.", " НА СТАНЦИИ");
        return correctType(name);
    }

    private String correctType(String name) {
        if (name == null) 
            return null;
        StringBuilder tmp = new StringBuilder();
        boolean notEmpty = false;
        for(int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isLetterOrDigit(ch)) 
                notEmpty = true;
            else if (ch != '&' && ch != ',' && ch != '.') 
                ch = ' ';
            if (com.pullenti.n2j.Utils.isWhitespace(ch)) {
                if (tmp.length() == 0) 
                    continue;
                if (tmp.charAt(tmp.length() - 1) != ' ' && tmp.charAt(tmp.length() - 1) != '.') 
                    tmp.append(' ');
                continue;
            }
            boolean isSpBefore = tmp.length() == 0 || tmp.charAt(tmp.length() - 1) == ' ';
            if (ch == '&' && !isSpBefore) 
                tmp.append(' ');
            if (((ch == ',' || ch == '.')) && isSpBefore && tmp.length() > 0) 
                tmp.setLength(tmp.length() - 1);
            tmp.append(ch);
        }
        if (!notEmpty) 
            return null;
        while(tmp.length() > 0) {
            char ch = tmp.charAt(tmp.length() - 1);
            if ((ch == ' ' || ch == ',' || ch == '.') || com.pullenti.n2j.Utils.isWhitespace(ch)) 
                tmp.setLength(tmp.length() - 1);
            else 
                break;
        }
        return tmp.toString();
    }

    public void addName(String name, boolean removeLongGovNames, com.pullenti.ner.Token t) {
        int num;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg2182 = new com.pullenti.n2j.Outargwrapper< >();
        String s = correctName(name, inoutarg2182);
        num = (inoutarg2182.value != null ? inoutarg2182.value : 0);
        if (s == null) {
            if (num > 0 && getNumber() == null) 
                setNumber(((Integer)num).toString());
            return;
        }
        if (com.pullenti.n2j.Utils.stringsEq(s, "УПРАВЛЕНИЕ")) {
        }
        int i = s.indexOf(' ');
        if (i == 2 && s.charAt(1) == 'К' && ((i + 3) < s.length())) {
            addSlot(ATTR_TYPE, s.substring(0, 0+2), false, 0);
            s = s.substring(3).trim();
        }
        if (getKind() == OrganizationKind.BANK || (s.indexOf("БАНК") >= 0)) {
            if (s.startsWith("КБ ")) {
                addTypeStr("коммерческий банк");
                s = s.substring(3);
            }
            else if (s.startsWith("АКБ ")) {
                addTypeStr("акционерный коммерческий банк");
                s = s.substring(3);
            }
        }
        if (num > 0) {
            if (s.length() > 10) 
                setNumber(((Integer)num).toString());
            else 
                s = s + num;
        }
        int cou = 1;
        if (t != null && !t.chars.isLetter() && com.pullenti.ner.core.BracketHelper.isBracket(t, false)) 
            t = t.getNext();
        if (((t instanceof com.pullenti.ner.TextToken) && (s.indexOf(' ') < 0) && s.length() > 3) && com.pullenti.n2j.Utils.stringsEq(s, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term)) {
            java.util.ArrayList<com.pullenti.morph.MorphToken> mt = com.pullenti.morph.Morphology.process(s, t.getMorph().getLanguage(), null);
            if (mt != null && mt.size() == 1) {
                String sNorm = mt.get(0).getLemma();
                if (com.pullenti.n2j.Utils.stringsEq(sNorm, s)) {
                    if (m_NameSingleNormalReal == null) {
                        m_NameSingleNormalReal = s;
                        for(int ii = getSlots().size() - 1; ii >= 0; ii--) {
                            if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(ii).getTypeName(), ATTR_NAME) && com.pullenti.n2j.Utils.stringsNe(((String)com.pullenti.n2j.Utils.cast(getSlots().get(ii).getValue(), String.class)), s)) {
                                mt = com.pullenti.morph.Morphology.process((String)com.pullenti.n2j.Utils.cast(getSlots().get(ii).getValue(), String.class), t.getMorph().getLanguage(), null);
                                if (mt != null && mt.size() == 1) {
                                    if (com.pullenti.n2j.Utils.stringsEq(mt.get(0).getLemma(), m_NameSingleNormalReal)) {
                                        cou += getSlots().get(ii).getCount();
                                        getSlots().remove(ii);
                                        m_NameVars = null;
                                        m_NameHashs = null;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (com.pullenti.n2j.Utils.stringsEq(sNorm, m_NameSingleNormalReal) && sNorm != null) 
                    s = sNorm;
            }
        }
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) {
                String n = a.getValue().toString();
                if (com.pullenti.n2j.Utils.stringsEq(s, n)) {
                    a.setCount(a.getCount() + cou);
                    return;
                }
            }
            else if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_TYPE)) {
                String n = a.getValue().toString();
                if (com.pullenti.n2j.Utils.stringsCompare(s, n, true) == 0) 
                    return;
                if (s.startsWith(n + " ")) 
                    s = s.substring(n.length() + 1);
            }
        }
        addSlot(ATTR_NAME, s, false, 1);
        if (com.pullenti.morph.LanguageHelper.endsWith(s, " ПО")) {
            s = s.substring(0, 0+(s.length() - 2)) + "ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ";
            addSlot(ATTR_NAME, s, false, 0);
        }
        correctData(removeLongGovNames);
    }

    public void addNameStr(String name, com.pullenti.ner.org.internal.OrgItemTypeToken typ, int cou) {
        if (typ != null && typ.altTyp != null && !typ.isNotTyp) 
            addTypeStr(typ.altTyp);
        if (name == null) {
            if (typ.isNotTyp) 
                return;
            if (typ.name != null && typ.name.length() > typ.typ.length()) {
                int num = 0;
                com.pullenti.n2j.Outargwrapper<Integer> inoutarg2183 = new com.pullenti.n2j.Outargwrapper< >();
                String s = correctName(typ.name, inoutarg2183);
                num = (inoutarg2183.value != null ? inoutarg2183.value : 0);
                if (s.length() > typ.typ.length()) 
                    addSlot(ATTR_NAME, s, false, cou);
                if (num > 0 && typ.isDep() && getNumber() == null) 
                    setNumber(((Integer)num).toString());
            }
            else if (typ.altTyp != null) 
                addSlot(ATTR_NAME, correctName0(typ.altTyp), false, cou);
        }
        else {
            String s = correctName0(name);
            if (typ == null || typ.isNotTyp) 
                addSlot(ATTR_NAME, s, false, cou);
            else {
                addSlot(ATTR_NAME, typ.typ.toUpperCase() + " " + s, false, cou);
                if (typ.name != null) {
                    int num = 0;
                    com.pullenti.n2j.Outargwrapper<Integer> inoutarg2184 = new com.pullenti.n2j.Outargwrapper< >();
                    String ss = correctName(typ.name, inoutarg2184);
                    num = (inoutarg2184.value != null ? inoutarg2184.value : 0);
                    if (ss != null) {
                        addTypeStr(ss);
                        addSlot(ATTR_NAME, ss + " " + s, false, cou);
                        if (num > 0 && typ.isDep() && getNumber() == null) 
                            setNumber(((Integer)num).toString());
                    }
                }
            }
            if (com.pullenti.morph.LanguageHelper.endsWithEx(name, " ОБЛАСТИ", " РАЙОНА", " КРАЯ", " РЕСПУБЛИКИ")) {
                int ii = name.lastIndexOf(' ');
                addNameStr(name.substring(0, 0+(ii)), typ, cou);
            }
        }
        correctData(true);
    }

    /**
     * Профиль деятельности
     */
    public java.util.ArrayList<OrgProfile> getProfiles() {
        java.util.ArrayList<OrgProfile> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROFILE)) {
                try {
                    String str = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                    if (com.pullenti.n2j.Utils.stringsEq(str, "Politics")) 
                        str = "Policy";
                    else if (com.pullenti.n2j.Utils.stringsEq(str, "PartOf")) 
                        str = "Unit";
                    OrgProfile v = OrgProfile.of(str);
                    res.add(v);
                } catch(Exception ex2185) {
                }
            }
        }
        return res;
    }


    public void addProfile(OrgProfile prof) {
        if (prof != OrgProfile.UNDEFINED) 
            addSlot(ATTR_PROFILE, prof.toString(), false, 0);
    }

    public boolean containsProfile(OrgProfile prof) {
        return findSlot(ATTR_PROFILE, prof.toString(), true) != null;
    }

    /**
     * Список типов и префиксов организации (ЗАО, компания, институт ...)
     */
    public java.util.ArrayList<String> getTypes() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public boolean _typesContains(String substr) {
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                if ((((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class)).indexOf(substr) >= 0)) 
                    return true;
            }
        }
        return false;
    }

    public void addType(com.pullenti.ner.org.internal.OrgItemTypeToken typ, boolean finalAdd) {
        if (typ == null) 
            return;
        for(OrgProfile p : typ.getProfiles()) {
            addProfile(p);
        }
        if (typ.isNotTyp) 
            return;
        if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "следственный комитет")) {
            addTypeStr("комитет");
            addName(typ.typ, true, null);
        }
        else {
            addTypeStr(typ.typ);
            if (typ.number != null) 
                setNumber(typ.number);
            if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "АКБ")) 
                addTypeStr("банк");
            if (typ.name != null) {
                if (typ.nameIsName) 
                    addName(typ.name, true, null);
                else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "министерство") && typ.name.toUpperCase().startsWith(typ.typ + " ".toUpperCase())) 
                    addName(typ.name, true, null);
                else if (typ.typ.endsWith("электростанция") && typ.name.toUpperCase().endsWith(" " + typ.typ.toUpperCase())) 
                    addName(typ.name, true, null);
                else if (findSlot(ATTR_NAME, null, true) != null && findSlot(ATTR_NAME, typ.name, true) == null) 
                    addTypeStr(typ.name.toLowerCase());
                else if (finalAdd) {
                    String ss = typ.name.toLowerCase();
                    if (com.pullenti.morph.LanguageHelper.isLatin(ss) && ss.endsWith(" " + typ.typ)) {
                        if (typ.root != null && ((typ.root.canHasLatinName || typ.root.canHasSingleName)) && !typ.root.mustBePartofName) {
                            com.pullenti.ner.Slot sl = findSlot(ATTR_NAME, typ.name, true);
                            if (sl != null) 
                                getSlots().remove(sl);
                            addName(ss.substring(0, 0+(ss.length() - typ.typ.length() - 1)).toUpperCase(), true, null);
                            addName(ss.toUpperCase(), true, null);
                            ss = null;
                        }
                    }
                    if (ss != null) 
                        addTypeStr(ss);
                }
                if (typ.altName != null) 
                    addName(typ.altName, true, null);
            }
        }
        if (typ.altTyp != null) 
            addTypeStr(typ.altTyp);
        if (typ.number != null) 
            setNumber(typ.number);
        if (typ.root != null) {
            if (typ.root.acronym != null) {
                if (findSlot(ATTR_TYPE, typ.root.acronym, true) == null) 
                    addSlot(ATTR_TYPE, typ.root.acronym, false, 0);
            }
            if (typ.root.getCanonicText() != null && com.pullenti.n2j.Utils.stringsNe(typ.root.getCanonicText(), "СБЕРЕГАТЕЛЬНЫЙ БАНК") && com.pullenti.n2j.Utils.stringsNe(typ.root.getCanonicText(), typ.root.acronym)) 
                addTypeStr(typ.root.getCanonicText().toLowerCase());
        }
        if (typ.geo != null) {
            if (((typ.geo.referent instanceof com.pullenti.ner.geo.GeoReferent)) && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(typ.geo.referent, com.pullenti.ner.geo.GeoReferent.class))).isRegion() && getKind() == OrganizationKind.STUDY) {
            }
            else 
                addGeoObject(typ.geo);
        }
        if (typ.geo2 != null) 
            addGeoObject(typ.geo2);
        if (finalAdd) {
            if (getKind() == OrganizationKind.BANK) 
                addSlot(ATTR_TYPE, "банк", false, 0);
        }
    }

    public void addTypeStr(String typ) {
        if (typ == null) 
            return;
        typ = correctType(typ);
        if (typ == null) 
            return;
        boolean ok = true;
        for(String n : getNames()) {
            if (n.toUpperCase().startsWith(typ.toUpperCase())) {
                ok = false;
                break;
            }
        }
        if (!ok) 
            return;
        addSlot(ATTR_TYPE, typ, false, 0);
        correctData(true);
    }

    private java.util.Collection<String> getSortedTypes(boolean forOntos) {
        java.util.ArrayList<String> res = new java.util.ArrayList(getTypes());
        java.util.Collections.sort(res);
        for(int i = 0; i < res.size(); i++) {
            if (Character.isLowerCase(res.get(i).charAt(0))) {
                boolean into = false;
                for(String r : res) {
                    if (com.pullenti.n2j.Utils.stringsNe(r, res.get(i)) && (r.indexOf(res.get(i)) >= 0)) {
                        into = true;
                        break;
                    }
                }
                if (!into && !forOntos) {
                    String v = res.get(i).toUpperCase();
                    for(String n : getNames()) {
                        if ((n.indexOf(v) >= 0)) {
                            into = true;
                            break;
                        }
                    }
                }
                if (into) {
                    res.remove(i);
                    i--;
                    continue;
                }
            }
        }
        return res;
    }

    /**
     * Номер (если есть)
     */
    public String getNumber() {
        if (!m_NumberCalc) {
            m_Number = getStringValue(ATTR_NUMBER);
            m_NumberCalc = true;
        }
        return m_Number;
    }

    /**
     * Номер (если есть)
     */
    public String setNumber(String value) {
        addSlot(ATTR_NUMBER, value, true, 0);
        return value;
    }


    private boolean m_NumberCalc;

    private String m_Number;

    /**
     * Типа владелец - (Аппарат Президента)
     */
    public com.pullenti.ner.Referent getOwner() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_OWNER), com.pullenti.ner.Referent.class);
    }

    /**
     * Типа владелец - (Аппарат Президента)
     */
    public com.pullenti.ner.Referent setOwner(com.pullenti.ner.Referent value) {
        addSlot(ATTR_OWNER, (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(value, com.pullenti.ner.Referent.class), true, 0);
        return value;
    }


    /**
     * Вышестоящая организация
     */
    public OrganizationReferent getHigher() {
        if (m_ParentCalc) 
            return m_Parent;
        m_ParentCalc = true;
        m_Parent = (OrganizationReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_HIGHER), OrganizationReferent.class);
        if (m_Parent == this || m_Parent == null) 
            return (m_Parent = null);
        com.pullenti.ner.Slot sl = m_Parent.findSlot(ATTR_HIGHER, null, true);
        if (sl == null) 
            return m_Parent;
        java.util.ArrayList<OrganizationReferent> li = new java.util.ArrayList< >();
        li.add(this);
        li.add(m_Parent);
        for(OrganizationReferent oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(sl.getValue(), OrganizationReferent.class); oo != null; oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(oo.getValue(ATTR_HIGHER), OrganizationReferent.class)) {
            if (li.contains(oo)) 
                return (m_Parent = null);
            li.add(oo);
        }
        return m_Parent;
    }

    /**
     * Вышестоящая организация
     */
    public OrganizationReferent setHigher(OrganizationReferent value) {
        if (value != null) {
            OrganizationReferent d = value;
            java.util.ArrayList<OrganizationReferent> li = new java.util.ArrayList< >();
            for(; d != null; d = d.getHigher()) {
                if (d == this) 
                    return value;
                else if (com.pullenti.n2j.Utils.stringsEq(d.toString(), this.toString())) 
                    return value;
                if (li.contains(d)) 
                    return value;
                li.add(d);
            }
        }
        addSlot(ATTR_HIGHER, null, true, 0);
        if (value != null) 
            addSlot(ATTR_HIGHER, value, true, 0);
        m_ParentCalc = false;
        return value;
    }


    private OrganizationReferent m_Parent;

    private boolean m_ParentCalc;

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        OrganizationReferent hi = getHigher();
        if (hi != null) 
            return hi;
        return getOwner();
    }


    private static java.util.ArrayList<String> m_EmpryEponyms = new java.util.ArrayList< >();

    /**
     * Список объектов, которым посвящена организации (имени кого)
     */
    public java.util.ArrayList<String> getEponyms() {
        java.util.ArrayList<String> res = null;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_EPONYM)) {
                if (res == null) 
                    res = new java.util.ArrayList< >();
                res.add(s.getValue().toString());
            }
        }
        return (java.util.ArrayList<String>)com.pullenti.n2j.Utils.notnull(res, m_EmpryEponyms);
    }


    public void addEponym(String rodPadezSurname) {
        if (rodPadezSurname == null) 
            return;
        rodPadezSurname = com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(rodPadezSurname);
        if (findSlot(ATTR_EPONYM, rodPadezSurname, true) == null) 
            addSlot(ATTR_EPONYM, rodPadezSurname, false, 0);
    }

    private static java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> m_EmptyGeos = new java.util.ArrayList< >();

    public java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> getGeoObjects() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> res = null;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                if (res == null) 
                    res = new java.util.ArrayList< >();
                res.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class));
            }
        }
        return (java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>)com.pullenti.n2j.Utils.notnull(res, m_EmptyGeos);
    }


    public boolean addGeoObject(Object r) {
        if (r instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    com.pullenti.ner.geo.GeoReferent gg = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class);
                    if (gg.canBeEquals(_geo, com.pullenti.ner.Referent.EqualType.WITHINONETEXT) || gg.getHigher() == _geo) 
                        return true;
                    if (_geo.isState() != gg.isState()) {
                        if (gg.isState()) {
                            if (getKind() == OrganizationKind.GOVENMENT) 
                                return false;
                            if (!_geo.isCity()) 
                                return false;
                        }
                    }
                    if (_geo.isCity() == gg.isCity()) {
                        boolean sovm = false;
                        for(String t : getTypes()) {
                            if ((t.indexOf("совместн") >= 0) || (t.indexOf("альянс") >= 0)) 
                                sovm = true;
                        }
                        if (!sovm) 
                            return false;
                    }
                    if (_geo.getHigher() == gg) {
                        uploadSlot(s, _geo);
                        return true;
                    }
                }
            }
            addSlot(ATTR_GEO, r, false, 0);
            return true;
        }
        else if (r instanceof com.pullenti.ner.ReferentToken) {
            if ((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                if (!addGeoObject((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getReferent())) 
                    return false;
                addExtReferent((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class));
                return true;
            }
            if ((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getReferent() instanceof com.pullenti.ner.address.AddressReferent) 
                return addGeoObject((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getBeginToken().getReferent());
        }
        return false;
    }

    private String m_NameSingleNormalReal;

    private java.util.HashMap<String, Boolean> m_NameVars;

    private java.util.ArrayList<String> m_NameHashs;

    public java.util.HashMap<String, Boolean> getNameVars() {
        if (m_NameVars != null) 
            return m_NameVars;
        m_NameVars = new java.util.HashMap< >();
        m_NameHashs = new java.util.ArrayList< >();
        java.util.ArrayList<String> nameAbbr = null;
        OrganizationKind ki = getKind();
        for(String n : getNames()) {
            if (!m_NameVars.containsKey(n)) 
                m_NameVars.put(n, false);
        }
        for(String n : getNames()) {
            String a;
            if (ki == OrganizationKind.BANK) {
                if (!(n.indexOf("БАНК") >= 0)) {
                    a = n + "БАНК";
                    if (!m_NameVars.containsKey(a)) 
                        m_NameVars.put(a, false);
                }
            }
            if ((((a = com.pullenti.ner.core.MiscHelper.getAbbreviation(n)))) != null && a.length() > 1) {
                if (!m_NameVars.containsKey(a)) 
                    m_NameVars.put(a, true);
                if (nameAbbr == null) 
                    nameAbbr = new java.util.ArrayList< >();
                if (!nameAbbr.contains(a)) 
                    nameAbbr.add(a);
                for(com.pullenti.ner.geo.GeoReferent _geo : getGeoObjects()) {
                    String aa = a + _geo.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0).charAt(0);
                    if (!m_NameVars.containsKey(aa)) 
                        m_NameVars.put(aa, true);
                    if (!nameAbbr.contains(aa)) 
                        nameAbbr.add(aa);
                }
            }
            if ((((a = com.pullenti.ner.core.MiscHelper.getTailAbbreviation(n)))) != null) {
                if (!m_NameVars.containsKey(a)) 
                    m_NameVars.put(a, true);
            }
            int i = n.indexOf(' ');
            if (i > 0 && (n.indexOf(' ', i + 1) < 0)) {
                a = n.replace(" ", "");
                if (!m_NameVars.containsKey(a)) 
                    m_NameVars.put(a, false);
            }
        }
        for(String e : getEponyms()) {
            for(String ty : getTypes()) {
                String na = (ty + " " + e).toUpperCase();
                if (!m_NameVars.containsKey(na)) 
                    m_NameVars.put(na, false);
            }
        }
        java.util.ArrayList<String> newVars = new java.util.ArrayList< >();
        for(String n : getTypes()) {
            String a = com.pullenti.ner.core.MiscHelper.getAbbreviation(n);
            if (a == null) 
                continue;
            for(String v : m_NameVars.keySet()) {
                if (!v.startsWith(a)) {
                    newVars.add(a + v);
                    newVars.add(a + " " + v);
                }
            }
        }
        for(String v : newVars) {
            if (!m_NameVars.containsKey(v)) 
                m_NameVars.put(v, true);
        }
        for(java.util.Map.Entry<String, Boolean> kp : m_NameVars.entrySet()) {
            if (!kp.getValue()) {
                String s = com.pullenti.ner.core.MiscHelper.getAbsoluteNormalValue(kp.getKey(), false);
                if (s != null && s.length() > 4) {
                    if (!m_NameHashs.contains(s)) 
                        m_NameHashs.add(s);
                }
            }
        }
        return m_NameVars;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        boolean ret = canBeEqualsEx(obj, false, typ);
        return ret;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        if (!canBeEqualsEx(obj, true, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) 
            return false;
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos1 = getGeoObjects();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos2 = (((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))).getGeoObjects();
        if (geos1.size() == 0 && geos2.size() > 0) {
            if (_checkEqEponyms((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))) 
                return false;
            return true;
        }
        else if (geos1.size() == geos2.size()) {
            if (_checkEqEponyms((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))) 
                return false;
            if (getHigher() != null && (((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))).getHigher() != null) {
                if (getHigher().canBeGeneralFor((((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))).getHigher())) 
                    return true;
            }
        }
        return false;
    }

    private boolean _checkEqEponyms(OrganizationReferent _org) {
        if (findSlot(ATTR_EPONYM, null, true) == null && _org.findSlot(ATTR_EPONYM, null, true) == null) 
            return false;
        java.util.Collection<String> eps = getEponyms();
        java.util.Collection<String> eps1 = _org.getEponyms();
        for(String e : eps) {
            if (eps1.contains(e)) 
                return true;
            if (!com.pullenti.morph.LanguageHelper.endsWith(e, "а")) {
                if (eps1.contains(e + "а")) 
                    return true;
            }
        }
        for(String e : eps1) {
            if (eps.contains(e)) 
                return true;
            if (!com.pullenti.morph.LanguageHelper.endsWith(e, "а")) {
                if (eps.contains(e + "а")) 
                    return true;
            }
        }
        if (findSlot(ATTR_EPONYM, null, true) != null && _org.findSlot(ATTR_EPONYM, null, true) != null) 
            return false;
        String s = _org.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
        for(String e : getEponyms()) {
            if ((s.indexOf(e) >= 0)) 
                return true;
        }
        s = toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
        for(String e : _org.getEponyms()) {
            if ((s.indexOf(e) >= 0)) 
                return true;
        }
        return false;
    }

    public OrganizationReferent m_TempParentOrg;

    public boolean canBeEqualsEx(com.pullenti.ner.Referent obj, boolean ignoreGeoObjects, com.pullenti.ner.Referent.EqualType typ) {
        boolean ret = _canBeEquals(obj, ignoreGeoObjects, typ, 0);
        if (!ret) {
        }
        return ret;
    }

    private boolean _canBeEquals(com.pullenti.ner.Referent obj, boolean ignoreGeoObjects, com.pullenti.ner.Referent.EqualType typ, int lev) {
        OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class);
        if (_org == null) 
            return false;
        if (_org == this) 
            return true;
        if (lev > 4) 
            return false;
        boolean empty = true;
        boolean geoNotEquals = false;
        OrganizationKind k1 = getKind();
        OrganizationKind k2 = _org.getKind();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos1 = getGeoObjects();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos2 = _org.getGeoObjects();
        if (geos1.size() > 0 && geos2.size() > 0) {
            geoNotEquals = true;
            for(com.pullenti.ner.geo.GeoReferent g1 : geos1) {
                boolean eq = false;
                for(com.pullenti.ner.geo.GeoReferent g2 : geos2) {
                    if (g1.canBeEquals(g2, typ)) {
                        geoNotEquals = false;
                        eq = true;
                        break;
                    }
                }
                if (!eq) 
                    return false;
            }
            if (geos2.size() > geos1.size()) {
                for(com.pullenti.ner.geo.GeoReferent g1 : geos2) {
                    boolean eq = false;
                    for(com.pullenti.ner.geo.GeoReferent g2 : geos1) {
                        if (g1.canBeEquals(g2, typ)) {
                            geoNotEquals = false;
                            eq = true;
                            break;
                        }
                    }
                    if (!eq) 
                        return false;
                }
            }
        }
        String inn = getINN();
        String inn2 = _org.getINN();
        if (inn != null && inn2 != null) 
            return com.pullenti.n2j.Utils.stringsEq(inn, inn2);
        String ogrn = getOGRN();
        String ogrn2 = _org.getOGRN();
        if (ogrn != null && ogrn2 != null) 
            return com.pullenti.n2j.Utils.stringsEq(ogrn, ogrn2);
        OrganizationReferent hi1 = (OrganizationReferent)com.pullenti.n2j.Utils.notnull(getHigher(), m_TempParentOrg);
        OrganizationReferent hi2 = (OrganizationReferent)com.pullenti.n2j.Utils.notnull(_org.getHigher(), _org.m_TempParentOrg);
        boolean hiEq = false;
        if (hi1 != null && hi2 != null) {
            if (_org.findSlot(ATTR_HIGHER, hi1, false) == null) {
                if (hi1._canBeEquals(hi2, ignoreGeoObjects, typ, lev + 1)) {
                }
                else 
                    return false;
            }
            hiEq = true;
        }
        if (getOwner() != null || _org.getOwner() != null) {
            if (getOwner() == null || _org.getOwner() == null) 
                return false;
            if (!getOwner().canBeEquals(_org.getOwner(), typ)) 
                return false;
            hiEq = true;
        }
        if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS && !hiEq) {
            if (getHigher() != null || _org.getHigher() != null) 
                return false;
        }
        if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(this, _org)) 
            return false;
        if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) {
            if (k1 == OrganizationKind.DEPARTMENT || k2 == OrganizationKind.DEPARTMENT) {
                if (hi1 == null && hi2 != null) 
                    return false;
                if (hi1 != null && hi2 == null) 
                    return false;
            }
            else if (k1 != k2) 
                return false;
        }
        boolean eqEponyms = _checkEqEponyms(_org);
        boolean eqNumber = false;
        if (getNumber() != null || _org.getNumber() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(_org.getNumber(), getNumber())) {
                if (((_org.getNumber() == null || getNumber() == null)) && eqEponyms) {
                }
                else if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((_org.getNumber() == null || getNumber() == null))) {
                }
                else 
                    return false;
            }
            else {
                empty = false;
                for(com.pullenti.ner.Slot a : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_TYPE)) {
                        if (obj.findSlot(a.getTypeName(), a.getValue(), true) != null || obj.findSlot(ATTR_NAME, (((String)com.pullenti.n2j.Utils.cast(a.getValue(), String.class))).toUpperCase(), true) != null) {
                            eqNumber = true;
                            break;
                        }
                    }
                }
            }
        }
        if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) {
            if (getNumber() != null || _org.getNumber() != null) {
                if (!eqNumber && !eqEponyms) 
                    return false;
            }
        }
        if (k1 != OrganizationKind.UNDEFINED && k2 != OrganizationKind.UNDEFINED) {
            if (k1 != k2) {
                boolean oo = false;
                for(String ty1 : getTypes()) {
                    if (_org.getTypes().contains(ty1)) {
                        oo = true;
                        break;
                    }
                }
                if (!oo) {
                    boolean hasPr = false;
                    for(OrgProfile p : getProfiles()) {
                        if (_org.containsProfile(p)) {
                            hasPr = true;
                            break;
                        }
                    }
                    if (!hasPr) 
                        return false;
                }
            }
        }
        else {
            if (k1 == OrganizationKind.UNDEFINED) 
                k1 = k2;
            if ((k1 == OrganizationKind.BANK || k1 == OrganizationKind.MEDICAL || k1 == OrganizationKind.PARTY) || k1 == OrganizationKind.CULTURE) {
                if (getTypes().size() > 0 && _org.getTypes().size() > 0) 
                    return false;
            }
        }
        if ((k1 == OrganizationKind.GOVENMENT || k2 == OrganizationKind.GOVENMENT || k1 == OrganizationKind.MILITARY) || k2 == OrganizationKind.MILITARY) {
            java.util.ArrayList<String> typs = _org.getTypes();
            boolean ok = false;
            for(String ty : getTypes()) {
                if (typs.contains(ty)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) 
                return false;
        }
        if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING) {
        }
        else if (findSlot(ATTR_NAME, null, true) != null || _org.findSlot(ATTR_NAME, null, true) != null) {
            if (((eqNumber || eqEponyms)) && ((findSlot(ATTR_NAME, null, true) == null || _org.findSlot(ATTR_NAME, null, true) == null))) {
            }
            else {
                empty = false;
                int maxLen = 0;
                for(java.util.Map.Entry<String, Boolean> v : getNameVars().entrySet()) {
                    if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS && v.getValue()) 
                        continue;
                    boolean b;
                    com.pullenti.n2j.Outargwrapper<Boolean> inoutarg2186 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2187 = com.pullenti.n2j.Utils.tryGetValue(_org.getNameVars(), v.getKey(), inoutarg2186);
                    b = (inoutarg2186.value != null ? inoutarg2186.value : false);
                    if (!inoutres2187) 
                        continue;
                    if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS && b) 
                        continue;
                    if (b && v.getValue()) 
                        continue;
                    if (b && getNames().size() > 1 && (v.getKey().length() < 4)) 
                        continue;
                    if (v.getValue() && _org.getNames().size() > 1 && (v.getKey().length() < 4)) 
                        continue;
                    if (v.getKey().length() > maxLen) 
                        maxLen = v.getKey().length();
                }
                if (typ != com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) {
                    for(String v : m_NameHashs) {
                        if (_org.m_NameHashs.contains(v)) {
                            if (v.length() > maxLen) 
                                maxLen = v.length();
                        }
                    }
                }
                if ((maxLen < 2) && ((k1 == OrganizationKind.GOVENMENT || typ == com.pullenti.ner.Referent.EqualType.FORMERGING)) && typ != com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) {
                    if (geos1.size() == geos2.size()) {
                        java.util.Collection<String> nams = (typ == com.pullenti.ner.Referent.EqualType.FORMERGING ? _org.getNameVars().keySet() : _org.getNames());
                        java.util.Collection<String> nams0 = (typ == com.pullenti.ner.Referent.EqualType.FORMERGING ? getNameVars().keySet() : getNames());
                        for(String n : nams0) {
                            for(String nn : nams) {
                                if (n.startsWith(nn)) {
                                    maxLen = nn.length();
                                    break;
                                }
                                else if (nn.startsWith(n)) {
                                    maxLen = n.length();
                                    break;
                                }
                            }
                        }
                    }
                }
                if (maxLen < 2) 
                    return false;
                if (maxLen < 4) {
                    boolean ok = false;
                    if (!ok) {
                        if (getNames().size() == 1 && (getNames().get(0).length() < 4)) 
                            ok = true;
                        else if (_org.getNames().size() == 1 && (_org.getNames().get(0).length() < 4)) 
                            ok = true;
                    }
                    if (!ok) 
                        return false;
                }
            }
        }
        if (eqEponyms) 
            return true;
        if (findSlot(ATTR_EPONYM, null, true) != null || obj.findSlot(ATTR_EPONYM, null, true) != null) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((findSlot(ATTR_EPONYM, null, true) == null || obj.findSlot(ATTR_EPONYM, null, true) == null))) {
            }
            else {
                boolean ok = false;
                java.util.Collection<String> eps = getEponyms();
                java.util.Collection<String> eps1 = _org.getEponyms();
                for(String e : eps) {
                    if (eps1.contains(e)) {
                        ok = true;
                        break;
                    }
                    if (!com.pullenti.morph.LanguageHelper.endsWith(e, "а")) {
                        if (eps1.contains(e + "а")) {
                            ok = true;
                            break;
                        }
                    }
                }
                if (!ok) {
                    for(String e : eps1) {
                        if (eps.contains(e)) {
                            ok = true;
                            break;
                        }
                        if (!com.pullenti.morph.LanguageHelper.endsWith(e, "а")) {
                            if (eps.contains(e + "а")) {
                                ok = true;
                                break;
                            }
                        }
                    }
                }
                if (ok) 
                    return true;
                if (findSlot(ATTR_EPONYM, null, true) == null || obj.findSlot(ATTR_EPONYM, null, true) == null) {
                    String s = obj.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
                    for(String e : getEponyms()) {
                        if ((s.indexOf(e) >= 0)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        s = toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
                        for(String e : _org.getEponyms()) {
                            if ((s.indexOf(e) >= 0)) {
                                ok = true;
                                break;
                            }
                        }
                    }
                    if (ok) 
                        return true;
                    else if (empty) 
                        return false;
                }
                else 
                    return false;
            }
        }
        if (geoNotEquals) {
            if (k1 == OrganizationKind.BANK || k1 == OrganizationKind.GOVENMENT || k1 == OrganizationKind.DEPARTMENT) 
                return false;
        }
        if (k1 != OrganizationKind.DEPARTMENT) {
            if (!empty) 
                return true;
            if (hiEq) {
                java.util.ArrayList<String> typs = _org.getTypes();
                for(String ty : getTypes()) {
                    if (typs.contains(ty)) 
                        return true;
                }
            }
        }
        if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
            return com.pullenti.n2j.Utils.stringsEq(toString(), _org.toString());
        if (empty) {
            if (((geos1.size() > 0 && geos2.size() > 0)) || k1 == OrganizationKind.DEPARTMENT || k1 == OrganizationKind.JUSTICE) {
                java.util.ArrayList<String> typs = _org.getTypes();
                for(String ty : getTypes()) {
                    if (typs.contains(ty)) 
                        return true;
                }
            }
            boolean fullNotEq = false;
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (_org.findSlot(s.getTypeName(), s.getValue(), true) == null) {
                    fullNotEq = true;
                    break;
                }
            }
            for(com.pullenti.ner.Slot s : _org.getSlots()) {
                if (findSlot(s.getTypeName(), s.getValue(), true) == null) {
                    fullNotEq = true;
                    break;
                }
            }
            if (!fullNotEq) 
                return true;
        }
        else if (k1 == OrganizationKind.DEPARTMENT) 
            return true;
        if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING) 
            return true;
        return false;
    }

    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        if (com.pullenti.n2j.Utils.stringsEq(attrName, ATTR_NAME) || com.pullenti.n2j.Utils.stringsEq(attrName, ATTR_TYPE)) {
            m_NameVars = null;
            m_NameHashs = null;
        }
        else if (com.pullenti.n2j.Utils.stringsEq(attrName, ATTR_HIGHER)) 
            m_ParentCalc = false;
        else if (com.pullenti.n2j.Utils.stringsEq(attrName, ATTR_NUMBER)) 
            m_NumberCalc = false;
        m_KindCalc = false;
        com.pullenti.ner.Slot sl = super.addSlot(attrName, attrValue, clearOldValue, statCount);
        return sl;
    }

    @Override
    public void uploadSlot(com.pullenti.ner.Slot slot, Object newVal) {
        m_ParentCalc = false;
        super.uploadSlot(slot, newVal);
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        OrganizationReferent ownThis = getHigher();
        OrganizationReferent ownObj = (((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))).getHigher();
        super.mergeSlots(obj, mergeStatistic);
        for(int i = getSlots().size() - 1; i >= 0; i--) {
            if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(i).getTypeName(), ATTR_HIGHER)) 
                getSlots().remove(i);
        }
        if (ownThis == null) 
            ownThis = ownObj;
        if (ownThis != null) 
            setHigher(ownThis);
        if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, OrganizationReferent.class))).isFromGlobalOntos) 
            isFromGlobalOntos = true;
        correctData(true);
    }

    public boolean isFromGlobalOntos;

    public void correctData(boolean removeLongGovNames) {
        for(int i = getSlots().size() - 1; i >= 0; i--) {
            if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(i).getTypeName(), ATTR_TYPE)) {
                String ty = getSlots().get(i).toString().toUpperCase();
                boolean del = false;
                for(com.pullenti.ner.Slot s : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                        String na = s.getValue().toString();
                        if (com.pullenti.morph.LanguageHelper.endsWith(ty, na)) 
                            del = true;
                    }
                }
                if (del) 
                    getSlots().remove(i);
            }
        }
        for(String t : getTypes()) {
            com.pullenti.ner.Slot n = findSlot(ATTR_NAME, t.toUpperCase(), true);
            if (n != null) 
                getSlots().remove(n);
        }
        for(String t : getNames()) {
            if (t.indexOf('.') > 0) {
                com.pullenti.ner.Slot n = findSlot(ATTR_NAME, t.replace('.', ' '), true);
                if (n == null) 
                    addSlot(ATTR_NAME, t.replace('.', ' '), false, 0);
            }
            else if (t.indexOf(' ') >= 0) {
                com.pullenti.ner.Slot n = findSlot(ATTR_NAME, t.replace(" ", ""), true);
                if (n != null && n.getCount() == 0) 
                    getSlots().remove(n);
            }
        }
        java.util.Collection<String> eps = getEponyms();
        if (eps.size() > 1) {
            for(String e : eps) {
                for(String ee : eps) {
                    if (com.pullenti.n2j.Utils.stringsNe(e, ee) && e.startsWith(ee)) {
                        com.pullenti.ner.Slot s = findSlot(ATTR_EPONYM, ee, true);
                        if (s != null) 
                            s.delete();
                    }
                }
            }
        }
        java.util.ArrayList<String> typs = getTypes();
        java.util.ArrayList<String> epons = getEponyms();
        for(String t : typs) {
            for(String e : epons) {
                com.pullenti.ner.Slot n = findSlot(ATTR_NAME, t.toUpperCase() + " " + e.toUpperCase(), true);
                if (n != null) 
                    getSlots().remove(n);
            }
        }
        if (removeLongGovNames && getKind() == OrganizationKind.GOVENMENT) {
            java.util.ArrayList<String> nams = getNames();
            for(int i = getSlots().size() - 1; i >= 0; i--) {
                if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(i).getTypeName(), ATTR_NAME)) {
                    String n = getSlots().get(i).getValue().toString();
                    for(String nn : nams) {
                        if (n.startsWith(nn) && n.length() > nn.length()) {
                            getSlots().remove(i);
                            break;
                        }
                    }
                }
            }
        }
        m_NameVars = null;
        m_NameHashs = null;
        m_KindCalc = false;
        extOntologyAttached = false;
    }

    public void finalCorrection() {
        java.util.ArrayList<String> typs = getTypes();
        if (containsProfile(OrgProfile.EDUCATION) && containsProfile(OrgProfile.SCIENCE)) {
            if (typs.contains("академия") || typs.contains("академія") || typs.contains("academy")) {
                boolean isSci = false;
                for(String n : getNames()) {
                    if ((n.indexOf("НАУЧН") >= 0) || (n.indexOf("НАУК") >= 0) || (n.indexOf("SCIENC") >= 0)) {
                        isSci = true;
                        break;
                    }
                }
                com.pullenti.ner.Slot s = null;
                if (isSci) 
                    s = findSlot(ATTR_PROFILE, OrgProfile.EDUCATION.toString(), true);
                else 
                    s = findSlot(ATTR_PROFILE, OrgProfile.SCIENCE.toString(), true);
                if (s != null) 
                    getSlots().remove(s);
            }
        }
        if (typs.size() > 0 && com.pullenti.morph.LanguageHelper.isLatin(typs.get(0))) {
            if (findSlot(ATTR_NAME, null, true) == null && typs.size() > 1) {
                String nam = typs.get(0);
                for(String v : typs) {
                    if (v.length() > nam.length()) 
                        nam = v;
                }
                if (nam.indexOf(' ') > 0) {
                    addSlot(ATTR_NAME, nam.toUpperCase(), false, 0);
                    com.pullenti.ner.Slot s = findSlot(ATTR_TYPE, nam, true);
                    if (s != null) 
                        getSlots().remove(s);
                }
            }
            if ((findSlot(ATTR_NAME, null, true) == null && findSlot(ATTR_GEO, null, true) != null && findSlot(ATTR_NUMBER, null, true) == null) && typs.size() > 0) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
                if (_geo != null) {
                    String nam = _geo.getStringValue(com.pullenti.ner.geo.GeoReferent.ATTR_NAME);
                    if (nam != null && com.pullenti.morph.LanguageHelper.isLatin(nam)) {
                        boolean nn = false;
                        for(String t : typs) {
                            if ((t.toUpperCase().indexOf(nam) >= 0)) {
                                addSlot(ATTR_NAME, t.toUpperCase(), false, 0);
                                nn = true;
                                if (typs.size() > 1) {
                                    com.pullenti.ner.Slot s = findSlot(ATTR_TYPE, t, true);
                                    if (s != null) 
                                        getSlots().remove(s);
                                }
                                break;
                            }
                        }
                        if (!nn) 
                            addSlot(ATTR_NAME, (nam + " " + typs.get(0)).toUpperCase(), false, 0);
                    }
                }
            }
        }
        m_NameVars = null;
        m_NameHashs = null;
        m_KindCalc = false;
        extOntologyAttached = false;
    }

    public java.util.ArrayList<String> _getPureNames() {
        java.util.ArrayList<String> vars = new java.util.ArrayList< >();
        java.util.ArrayList<String> typs = getTypes();
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) {
                String s = a.getValue().toString().toUpperCase();
                if (!vars.contains(s)) 
                    vars.add(s);
                for(String t : typs) {
                    if (s.toUpperCase().startsWith(t.toUpperCase())) {
                        if ((s.length() < (t.length() + 4)) || s.charAt(t.length()) != ' ') 
                            continue;
                        String ss = s.substring(t.length() + 1);
                        if (!vars.contains(ss)) 
                            vars.add(ss);
                    }
                }
            }
        }
        return vars;
    }

    /**
     * Признак того, что была попытка привязаться к внешней онтологии
     */
    public boolean extOntologyAttached;

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        return createOntologyItemEx(2, false, false);
    }

    public com.pullenti.ner.core.IntOntologyItem createOntologyItemEx(int minLen, boolean onlyNames, boolean pureNames) {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        java.util.ArrayList<String> vars = new java.util.ArrayList< >();
        java.util.ArrayList<String> typs = getTypes();
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) {
                String s = a.getValue().toString().toUpperCase();
                if (!vars.contains(s)) 
                    vars.add(s);
                if (!pureNames) {
                    int sp = 0;
                    for(int jj = 0; jj < s.length(); jj++) {
                        if (s.charAt(jj) == ' ') 
                            sp++;
                    }
                    if (sp == 1) {
                        s = s.replace(" ", "");
                        if (!vars.contains(s)) 
                            vars.add(s);
                    }
                }
            }
        }
        if (!pureNames) {
            for(String v : getNameVars().keySet()) {
                if (!vars.contains(v)) 
                    vars.add(v);
            }
        }
        if (!onlyNames) {
            if (getNumber() != null) {
                for(com.pullenti.ner.Slot a : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_TYPE)) {
                        String s = a.getValue().toString().toUpperCase();
                        if (!vars.contains(s)) 
                            vars.add(s);
                    }
                }
            }
            if (vars.size() == 0) {
                for(String t : getTypes()) {
                    String up = t.toUpperCase();
                    if (!vars.contains(up)) 
                        vars.add(up);
                }
            }
            if (getINN() != null) 
                vars.add(0, "ИНН:" + getINN());
            if (getOGRN() != null) 
                vars.add(0, "ОГРН:" + getOGRN());
        }
        int max = 20;
        int cou = 0;
        for(String v : vars) {
            if (v.length() >= minLen) {
                com.pullenti.ner.core.Termin term;
                if (pureNames) {
                    term = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                    term.initByNormalText(v, new com.pullenti.morph.MorphLang(null));
                }
                else 
                    term = new com.pullenti.ner.core.Termin(v, new com.pullenti.morph.MorphLang(null), false);
                oi.termins.add(term);
                if ((++cou) >= max) 
                    break;
            }
        }
        if (oi.termins.size() == 0) 
            return null;
        return oi;
    }

    /**
     * Коласс организации (некоторая экспертная оценка на основе названия и типов)
     */
    public OrganizationKind getKind() {
        if (!m_KindCalc) {
            m_Kind = com.pullenti.ner.org.internal.OrgItemTypeToken.checkKind(this);
            if (m_Kind == OrganizationKind.UNDEFINED) {
                for(OrgProfile p : getProfiles()) {
                    if (p == OrgProfile.UNIT) {
                        m_Kind = OrganizationKind.DEPARTMENT;
                        break;
                    }
                }
            }
            m_KindCalc = true;
        }
        return m_Kind;
    }


    private OrganizationKind m_Kind = OrganizationKind.UNDEFINED;

    private boolean m_KindCalc = false;

    @Override
    public String getStringValue(String attrName) {
        if (com.pullenti.n2j.Utils.stringsEq(attrName, "KIND")) {
            OrganizationKind ki = getKind();
            if (ki == OrganizationKind.UNDEFINED) 
                return null;
            return ki.toString();
        }
        return super.getStringValue(attrName);
    }

    /**
     * Проверка, что организация slave может быть дополнительным описанием основной организации
     * @param master 
     * @param slave 
     * @return 
     */
    public static boolean canBeSecondDefinition(OrganizationReferent master, OrganizationReferent slave) {
        if (master == null || slave == null) 
            return false;
        java.util.ArrayList<String> mTypes = master.getTypes();
        java.util.ArrayList<String> sTypes = slave.getTypes();
        boolean ok = false;
        for(String t : mTypes) {
            if (sTypes.contains(t)) {
                ok = true;
                break;
            }
        }
        if (ok) 
            return true;
        if (master.getKind() != OrganizationKind.UNDEFINED && slave.getKind() != OrganizationKind.UNDEFINED) {
            if (master.getKind() != slave.getKind()) 
                return false;
        }
        if (sTypes.size() > 0) 
            return false;
        if (slave.getNames().size() == 1) {
            String acr = slave.getNames().get(0);
            if (com.pullenti.morph.LanguageHelper.endsWith(acr, "АН")) 
                return true;
            for(String n : master.getNames()) {
                if (checkAcronym(acr, n) || checkAcronym(n, acr)) 
                    return true;
                if (checkLatinAccords(n, acr)) 
                    return true;
                for(String t : mTypes) {
                    if (checkAcronym(acr, t.toUpperCase() + n)) 
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean checkLatinAccords(String rusName, String latName) {
        if (!com.pullenti.morph.LanguageHelper.isCyrillicChar(rusName.charAt(0)) || !com.pullenti.morph.LanguageHelper.isLatinChar(latName.charAt(0))) 
            return false;
        String[] ru = com.pullenti.n2j.Utils.split(rusName, String.valueOf(' '), false);
        String[] la = com.pullenti.n2j.Utils.split(latName, String.valueOf(' '), false);
        int i = 0;
        int j = 0;
        while((i < ru.length) && (j < la.length)) {
            if (com.pullenti.n2j.Utils.stringsCompare(la[j], "THE", true) == 0 || com.pullenti.n2j.Utils.stringsCompare(la[j], "OF", true) == 0) {
                j++;
                continue;
            }
            if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(ru[i], la[j])) 
                return true;
            i++;
            j++;
        }
        if ((i < ru.length) || (j < la.length)) 
            return false;
        if (i >= 2) 
            return true;
        return false;
    }

    private static boolean checkAcronym(String acr, String text) {
        int i = 0;
        int j = 0;
        for(i = 0; i < acr.length(); i++) {
            for(; j < text.length(); j++) {
                if (text.charAt(j) == acr.charAt(i)) 
                    break;
            }
            if (j >= text.length()) 
                break;
            j++;
        }
        return i >= acr.length();
    }

    /**
     * Проверка на отношения "вышестоящий - нижестоящий"
     * @param _higher 
     * @param lower 
     * @return 
     */
    public static boolean canBeHigher(OrganizationReferent _higher, OrganizationReferent lower) {
        return com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(_higher, lower, false);
    }
}
