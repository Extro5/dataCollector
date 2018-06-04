/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.uri;

/**
 * Картридж для выделения интернетных объектов (URL, E-mail)
 */
public class UriAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "URI";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "URI";
    }


    @Override
    public String getDescription() {
        return "URI (URL, EMail), ISBN, УДК, ББК ...";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new UriAnalyzer();
    }

    @Override
    public int getProgressWeight() {
        return 2;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.uri.internal.MetaUri.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap< >();
        res.put(com.pullenti.ner.uri.internal.MetaUri.MAILIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("email.png"));
        res.put(com.pullenti.ner.uri.internal.MetaUri.URIIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("uri.png"));
        return res;
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"PHONE"});
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, UriReferent.OBJ_TYPENAME)) 
            return new UriReferent();
        return null;
    }

    /**
     * Основная функция выделения объектов
     * @param container 
     * @param lastStage 
     * @return 
     */
    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.Token tt = t;
            int i;
            com.pullenti.ner.core.TerminToken tok = m_Schemes.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                i = (int)tok.termin.tag;
                tt = tok.getEndToken();
                if (tt.getNext() != null && tt.getNext().isChar('(')) {
                    com.pullenti.ner.core.TerminToken tok1 = m_Schemes.tryParse(tt.getNext().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                    if ((tok1 != null && com.pullenti.n2j.Utils.stringsEq(tok1.termin.getCanonicText(), tok.termin.getCanonicText()) && tok1.getEndToken().getNext() != null) && tok1.getEndToken().getNext().isChar(')')) 
                        tt = tok1.getEndToken().getNext();
                }
                if (i == 0) {
                    if ((tt.getNext() == null || ((!tt.getNext().isCharOf(":|") && !tt.isTableControlChar())) || tt.getNext().isWhitespaceBefore()) || tt.getNext().getWhitespacesAfterCount() > 2) 
                        continue;
                    com.pullenti.ner.Token t1 = tt.getNext().getNext();
                    while(t1 != null && t1.isCharOf("/\\")) {
                        t1 = t1.getNext();
                    }
                    if (t1 == null || t1.getWhitespacesBeforeCount() > 2) 
                        continue;
                    com.pullenti.ner.uri.internal.UriItemToken ut = com.pullenti.ner.uri.internal.UriItemToken.attachUriContent(t1, false);
                    if (ut == null) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2649(tok.termin.getCanonicText().toLowerCase(), ut.value)), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(ur), t, ut.getEndToken(), null);
                    rt.setBeginToken((com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(_siteBefore(t.getPrevious()), t));
                    if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isCharOf("/\\")) 
                        rt.setEndToken(rt.getEndToken().getNext());
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
                if (i == 10) {
                    tt = tt.getNext();
                    if (tt == null || !tt.isChar(':')) 
                        continue;
                    for(tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isCharOf("/\\")) {
                        }
                        else 
                            break;
                    }
                    if (tt == null) 
                        continue;
                    if (tt.isValue("WWW", null) && tt.getNext() != null && tt.getNext().isChar('.')) 
                        tt = tt.getNext().getNext();
                    if (tt == null || tt.isNewlineBefore()) 
                        continue;
                    com.pullenti.ner.uri.internal.UriItemToken ut = com.pullenti.ner.uri.internal.UriItemToken.attachUriContent(tt, true);
                    if (ut == null) 
                        continue;
                    if (ut.value.length() < 4) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2649(tok.termin.getCanonicText().toLowerCase(), ut.value)), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(ur), t, ut.getEndToken(), null);
                    rt.setBeginToken((com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(_siteBefore(t.getPrevious()), t));
                    if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isCharOf("/\\")) 
                        rt.setEndToken(rt.getEndToken().getNext());
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
                if (i == 2) {
                    if (tt.getNext() == null || !tt.getNext().isChar('.') || tt.getNext().isWhitespaceBefore()) 
                        continue;
                    if (tt.getNext().isWhitespaceAfter() && com.pullenti.n2j.Utils.stringsNe(tok.termin.getCanonicText(), "WWW")) 
                        continue;
                    com.pullenti.ner.uri.internal.UriItemToken ut = com.pullenti.ner.uri.internal.UriItemToken.attachUriContent(tt.getNext().getNext(), true);
                    if (ut == null) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2649("http", ut.value)), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ur, t, ut.getEndToken(), null);
                    rt.setBeginToken((com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(_siteBefore(t.getPrevious()), t));
                    if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isCharOf("/\\")) 
                        rt.setEndToken(rt.getEndToken().getNext());
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
                if (i == 1) {
                    String sch = tok.termin.getCanonicText();
                    com.pullenti.ner.uri.internal.UriItemToken ut = null;
                    if (com.pullenti.n2j.Utils.stringsEq(sch, "ISBN")) {
                        ut = com.pullenti.ner.uri.internal.UriItemToken.attachISBN(tt.getNext());
                        if ((ut == null && t.getPrevious() != null && t.getPrevious().isChar('(')) && t.getNext() != null && t.getNext().isChar(')')) {
                            for(com.pullenti.ner.Token tt0 = t.getPrevious().getPrevious(); tt0 != null; tt0 = tt0.getPrevious()) {
                                if (tt0.getWhitespacesAfterCount() > 2) 
                                    break;
                                if (tt0.isWhitespaceBefore()) {
                                    ut = com.pullenti.ner.uri.internal.UriItemToken.attachISBN(tt0);
                                    if (ut != null && ut.getEndToken().getNext() != t.getPrevious()) 
                                        ut = null;
                                    break;
                                }
                            }
                        }
                    }
                    else if ((com.pullenti.n2j.Utils.stringsEq(sch, "RFC") || com.pullenti.n2j.Utils.stringsEq(sch, "ISO") || com.pullenti.n2j.Utils.stringsEq(sch, "ОКФС")) || com.pullenti.n2j.Utils.stringsEq(sch, "ОКОПФ")) 
                        ut = com.pullenti.ner.uri.internal.UriItemToken.attachISOContent(tt.getNext(), ":");
                    else if (com.pullenti.n2j.Utils.stringsEq(sch, "ГОСТ")) 
                        ut = com.pullenti.ner.uri.internal.UriItemToken.attachISOContent(tt.getNext(), "-.");
                    else if (com.pullenti.n2j.Utils.stringsEq(sch, "ТУ")) {
                        if (tok.chars.isAllUpper()) {
                            ut = com.pullenti.ner.uri.internal.UriItemToken.attachISOContent(tt.getNext(), "-.");
                            if (ut != null && (ut.getLengthChar() < 10)) 
                                ut = null;
                        }
                    }
                    else 
                        ut = com.pullenti.ner.uri.internal.UriItemToken.attachBBK(tt.getNext());
                    if (ut == null) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2652(ut.value, sch)), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt;
                    if (ut.beginChar < t.beginChar) {
                        rt = new com.pullenti.ner.ReferentToken(ur, ut.getBeginToken(), t, null);
                        if (t.getNext() != null && t.getNext().isChar(')')) 
                            rt.setEndToken(t.getNext());
                    }
                    else 
                        rt = new com.pullenti.ner.ReferentToken(ur, t, ut.getEndToken(), null);
                    if (t.getPrevious() != null && t.getPrevious().isValue("КОД", null)) 
                        rt.setBeginToken(t.getPrevious());
                    if (ur.getScheme().startsWith("ОК")) 
                        _checkDetail(rt);
                    kit.embedToken(rt);
                    t = rt;
                    if (ur.getScheme().startsWith("ОК")) {
                        while(t.getNext() != null) {
                            if (t.getNext().isCommaAnd() && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                            }
                            else 
                                break;
                            ut = com.pullenti.ner.uri.internal.UriItemToken.attachBBK(t.getNext().getNext());
                            if (ut == null) 
                                break;
                            ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2652(ut.value, sch)), UriReferent.class);
                            rt = new com.pullenti.ner.ReferentToken(ur, t.getNext().getNext(), ut.getEndToken(), null);
                            _checkDetail(rt);
                            kit.embedToken(rt);
                            t = rt;
                        }
                    }
                    continue;
                }
                if (i == 3) {
                    com.pullenti.ner.Token t0 = tt.getNext();
                    while(t0 != null) {
                        if (t0.isCharOf(":|") || t0.isTableControlChar() || t0.isHiphen()) 
                            t0 = t0.getNext();
                        else 
                            break;
                    }
                    if (t0 == null) 
                        continue;
                    com.pullenti.ner.uri.internal.UriItemToken ut = com.pullenti.ner.uri.internal.UriItemToken.attachSkype(t0);
                    if (ut == null) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2652(ut.value.toLowerCase(), (com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "SKYPE") ? "skype" : tok.termin.getCanonicText()))), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ur, t, ut.getEndToken(), null);
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
                if (i == 4) {
                    com.pullenti.ner.Token t0 = tt.getNext();
                    if (t0 != null && ((t0.isChar(':') || t0.isHiphen()))) 
                        t0 = t0.getNext();
                    if (t0 == null) 
                        continue;
                    com.pullenti.ner.uri.internal.UriItemToken ut = com.pullenti.ner.uri.internal.UriItemToken.attachIcqContent(t0);
                    if (ut == null) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2652(ut.value, "ICQ")), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ur, t, t0, null);
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
                if (i == 5 || i == 6) {
                    com.pullenti.ner.Token t0 = tt.getNext();
                    boolean hasTabCel = false;
                    for(; t0 != null; t0 = t0.getNext()) {
                        if ((((t0.isValue("БАНК", null) || t0.getMorph()._getClass().isPreposition() || t0.isHiphen()) || t0.isCharOf(".:") || t0.isValue("РУБЛЬ", null)) || t0.isValue("РУБ", null) || t0.isValue("ДОЛЛАР", null)) || t0.isValue("№", null) || t0.isValue("N", null)) {
                        }
                        else if (t0.isTableControlChar()) 
                            hasTabCel = true;
                        else if (t0 instanceof com.pullenti.ner.TextToken) {
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t0, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                            if (npt != null && npt.getMorph().getCase().isGenitive()) {
                                t0 = npt.getEndToken();
                                continue;
                            }
                            break;
                        }
                        else 
                            break;
                    }
                    if (t0 == null) 
                        continue;
                    UriReferent ur2 = null;
                    com.pullenti.ner.Token ur2Begin = null;
                    com.pullenti.ner.Token ur2End = null;
                    com.pullenti.ner.Token t00 = t0;
                    String val = t0.getSourceText();
                    if (Character.isDigit(val.charAt(0)) && ((((i == 6 || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ИНН") || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "БИК")) || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ОГРН") || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "СНИЛС")) || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ОКПО")))) {
                        if (t0.chars.isLetter()) 
                            continue;
                        if (com.pullenti.n2j.Utils.isNullOrEmpty(val) || !Character.isDigit(val.charAt(0))) 
                            continue;
                        if (t0.getLengthChar() < 9) {
                            StringBuilder tmp = new StringBuilder();
                            tmp.append(val);
                            for(com.pullenti.ner.Token ttt = t0.getNext(); ttt != null; ttt = ttt.getNext()) {
                                if (ttt.getWhitespacesBeforeCount() > 1) 
                                    break;
                                if (ttt instanceof com.pullenti.ner.NumberToken) {
                                    tmp.append(ttt.getSourceText());
                                    t0 = ttt;
                                    continue;
                                }
                                if (ttt.isHiphen() || ttt.isChar('.')) {
                                    if (ttt.getNext() == null || !((ttt.getNext() instanceof com.pullenti.ner.NumberToken))) 
                                        break;
                                    if (ttt.isWhitespaceAfter() || ttt.isWhitespaceBefore()) 
                                        break;
                                    continue;
                                }
                                break;
                            }
                            val = null;
                            if (tmp.length() == 20) 
                                val = tmp.toString();
                            else if (tmp.length() == 9 && com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "БИК")) 
                                val = tmp.toString();
                            else if (((tmp.length() == 10 || tmp.length() == 12)) && com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ИНН")) 
                                val = tmp.toString();
                            else if (tmp.length() >= 15 && com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "Л/С")) 
                                val = tmp.toString();
                            else if (tmp.length() >= 11 && ((com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ОГРН") || com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "СНИЛС")))) 
                                val = tmp.toString();
                            else if (com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ОКПО")) 
                                val = tmp.toString();
                        }
                        if (val == null) 
                            continue;
                    }
                    else if (!((t0 instanceof com.pullenti.ner.NumberToken))) {
                        if (!t0.isCharOf("/\\") || t0.getNext() == null) 
                            continue;
                        com.pullenti.ner.core.TerminToken tok2 = m_Schemes.tryParse(t0.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 == null || !((tok2.termin.tag instanceof Integer)) || ((int)tok2.termin.tag) != i) 
                            continue;
                        t0 = tok2.getEndToken().getNext();
                        while(t0 != null) {
                            if (t0.isCharOf(":N№")) 
                                t0 = t0.getNext();
                            else if (t0.isTableControlChar()) {
                                t0 = t0.getNext();
                                t00 = t0;
                                hasTabCel = true;
                            }
                            else 
                                break;
                        }
                        if (!((t0 instanceof com.pullenti.ner.NumberToken))) 
                            continue;
                        StringBuilder tmp = new StringBuilder();
                        for(; t0 != null; t0 = t0.getNext()) {
                            if (!((t0 instanceof com.pullenti.ner.NumberToken))) 
                                break;
                            else 
                                tmp.append(t0.getSourceText());
                        }
                        if (t0 == null || !t0.isCharOf("/\\,") || !((t0.getNext() instanceof com.pullenti.ner.NumberToken))) 
                            continue;
                        val = tmp.toString();
                        tmp.setLength(0);
                        ur2Begin = t0.getNext();
                        for(t0 = t0.getNext(); t0 != null; t0 = t0.getNext()) {
                            if (!((t0 instanceof com.pullenti.ner.NumberToken))) 
                                break;
                            else {
                                tmp.append(t0.getSourceText());
                                ur2End = t0;
                            }
                        }
                        ur2 = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2649(tok2.termin.getCanonicText(), tmp.toString())), UriReferent.class);
                    }
                    if (val.length() < 5) 
                        continue;
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2652(val, tok.termin.getCanonicText())), UriReferent.class);
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ur, t, (ur2Begin == null ? t0 : ur2Begin.getPrevious()), null);
                    if (hasTabCel) 
                        rt.setBeginToken(t00);
                    if (ur.getScheme().startsWith("ОК")) 
                        _checkDetail(rt);
                    for(com.pullenti.ner.Token ttt = t.getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                        if (ttt.isTableControlChar()) 
                            break;
                        if (ttt.getMorph()._getClass().isPreposition()) 
                            continue;
                        if (ttt.isValue("ОРГАНИЗАЦИЯ", null)) 
                            continue;
                        if (ttt.isValue("НОМЕР", null) || ttt.isValue("КОД", null)) 
                            t = rt.setBeginToken(ttt);
                        break;
                    }
                    kit.embedToken(rt);
                    t = rt;
                    if (ur2 != null) {
                        com.pullenti.ner.ReferentToken rt2 = new com.pullenti.ner.ReferentToken(ur2, ur2Begin, ur2End, null);
                        kit.embedToken(rt2);
                        t = rt2;
                    }
                    continue;
                }
                continue;
            }
            if (t.isChar('@')) {
                java.util.ArrayList<com.pullenti.ner.uri.internal.UriItemToken> u1s = com.pullenti.ner.uri.internal.UriItemToken.attachMailUsers(t.getPrevious());
                if (u1s == null) 
                    continue;
                com.pullenti.ner.uri.internal.UriItemToken u2 = com.pullenti.ner.uri.internal.UriItemToken.attachDomainName(t.getNext(), false, true);
                if (u2 == null) 
                    continue;
                for(int ii = u1s.size() - 1; ii >= 0; ii--) {
                    UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2652((u1s.get(ii).value + "@" + u2.value).toLowerCase(), "mailto")), UriReferent.class);
                    com.pullenti.ner.Token b = u1s.get(ii).getBeginToken();
                    com.pullenti.ner.Token t0 = b.getPrevious();
                    if (t0 != null && t0.isChar(':')) 
                        t0 = t0.getPrevious();
                    if (t0 != null && ii == 0) {
                        boolean br = false;
                        for(com.pullenti.ner.Token ttt = t0; ttt != null; ttt = ttt.getPrevious()) {
                            if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                                break;
                            if (ttt != t0 && ttt.getWhitespacesAfterCount() > 1) 
                                break;
                            if (ttt.isChar(')')) {
                                br = true;
                                continue;
                            }
                            if (ttt.isChar('(')) {
                                if (!br) 
                                    break;
                                br = false;
                                continue;
                            }
                            if (ttt.isValue("EMAIL", null) || ttt.isValue("MAILTO", null)) {
                                b = ttt;
                                break;
                            }
                            if (ttt.isValue("MAIL", null)) {
                                b = ttt;
                                if ((ttt.getPrevious() != null && ttt.getPrevious().isHiphen() && ttt.getPrevious().getPrevious() != null) && ((ttt.getPrevious().getPrevious().isValue("E", null) || ttt.getPrevious().getPrevious().isValue("Е", null)))) 
                                    b = ttt.getPrevious().getPrevious();
                                break;
                            }
                            if (ttt.isValue("ПОЧТА", null) || ttt.isValue("АДРЕС", null)) {
                                b = t0;
                                ttt = ttt.getPrevious();
                                if (ttt != null && ttt.isChar('.')) 
                                    ttt = ttt.getPrevious();
                                if (ttt != null && ((t0.isValue("ЭЛ", null) || ttt.isValue("ЭЛЕКТРОННЫЙ", null)))) 
                                    b = ttt;
                                if (b.getPrevious() != null && b.getPrevious().isValue("АДРЕС", null)) 
                                    b = b.getPrevious();
                                break;
                            }
                            if (ttt.getMorph()._getClass().isPreposition()) 
                                continue;
                        }
                    }
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ur, b, (ii == (u1s.size() - 1) ? u2.getEndToken() : u1s.get(ii).getEndToken()), null);
                    kit.embedToken(rt);
                    t = rt;
                }
                continue;
            }
            if (!t.getMorph().getLanguage().isCyrillic()) {
                if (t.isWhitespaceBefore() || ((t.getPrevious() != null && t.getPrevious().isCharOf(",(")))) {
                    com.pullenti.ner.uri.internal.UriItemToken u1 = com.pullenti.ner.uri.internal.UriItemToken.attachUrl(t);
                    if (u1 != null) {
                        if (u1.isWhitespaceAfter() || u1.getEndToken().getNext() == null || !u1.getEndToken().getNext().isChar('@')) {
                            UriReferent ur = (UriReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(UriReferent._new2649("http", u1.value)), UriReferent.class);
                            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ur, u1.getBeginToken(), u1.getEndToken(), null);
                            rt.setBeginToken((com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(_siteBefore(u1.getBeginToken().getPrevious()), (u1 != null ? u1.getBeginToken() : null)));
                            kit.embedToken(rt);
                            t = rt;
                            continue;
                        }
                    }
                }
            }
            if ((t.chars.isLatinLetter() && !t.chars.isAllLower() && t.getNext() != null) && !t.isWhitespaceAfter()) {
                if (t.getNext().isChar('/')) {
                    com.pullenti.ner.ReferentToken rt = _TryAttachLotus((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class));
                    if (rt != null) {
                        rt.referent = ad.registerReferent(rt.referent);
                        kit.embedToken(rt);
                        t = rt;
                        continue;
                    }
                }
            }
        }
    }

    private static void _checkDetail(com.pullenti.ner.ReferentToken rt) {
        if (rt.getEndToken().getWhitespacesAfterCount() > 2 || rt.getEndToken().getNext() == null) 
            return;
        if (rt.getEndToken().getNext().isChar('(')) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(rt.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                (((UriReferent)com.pullenti.n2j.Utils.cast(rt.referent, UriReferent.class))).setDetail(com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO));
                rt.setEndToken(br.getEndToken());
            }
        }
    }

    private static com.pullenti.ner.Token _siteBefore(com.pullenti.ner.Token t) {
        if (t != null && t.isChar(':')) 
            t = t.getPrevious();
        if (t == null) 
            return null;
        if ((t.isValue("ВЕБСАЙТ", null) || t.isValue("WEBSITE", null) || t.isValue("WEB", null)) || t.isValue("WWW", null)) 
            return t;
        com.pullenti.ner.Token t0 = null;
        if (t.isValue("САЙТ", null) || t.isValue("SITE", null)) {
            t0 = t;
            t = t.getPrevious();
        }
        else if (t.isValue("АДРЕС", null)) {
            t0 = t.getPrevious();
            if (t0 != null && t0.isChar('.')) 
                t0 = t0.getPrevious();
            if (t0 != null) {
                if (t0.isValue("ЭЛ", null) || t0.isValue("ЭЛЕКТРОННЫЙ", null)) 
                    return t0;
            }
            return null;
        }
        else 
            return null;
        if (t != null && t.isHiphen()) 
            t = t.getPrevious();
        if (t == null) 
            return t0;
        if (t.isValue("WEB", null) || t.isValue("ВЕБ", null)) 
            t0 = t;
        if (t0.getPrevious() != null && t0.getPrevious().getMorph()._getClass().isAdjective() && (t0.getWhitespacesBeforeCount() < 3)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t0.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) 
                t0 = npt.getBeginToken();
        }
        return t0;
    }

    private static com.pullenti.ner.ReferentToken _TryAttachLotus(com.pullenti.ner.TextToken t) {
        if (t == null || t.getNext() == null) 
            return null;
        com.pullenti.ner.Token t1 = t.getNext().getNext();
        java.util.ArrayList<String> tails = null;
        for(com.pullenti.ner.Token tt = t1; tt != null; tt = tt.getNext()) {
            if (tt.isWhitespaceBefore()) {
                if (!tt.isNewlineBefore()) 
                    break;
                if (tails == null || (tails.size() < 2)) 
                    break;
            }
            if (!tt.isLetters() || tt.chars.isAllLower()) 
                return null;
            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                return null;
            if (tails == null) 
                tails = new java.util.ArrayList< >();
            tails.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
            t1 = tt;
            if (tt.isWhitespaceAfter() || tt.getNext() == null) 
                break;
            tt = tt.getNext();
            if (!tt.isChar('/')) 
                break;
        }
        if (tails == null || (tails.size() < 3)) 
            return null;
        java.util.ArrayList<String> heads = new java.util.ArrayList< >();
        heads.add(t.term);
        com.pullenti.ner.Token t0 = t;
        boolean ok = true;
        for(int k = 0; k < 2; k++) {
            if (!((t0.getPrevious() instanceof com.pullenti.ner.TextToken))) 
                break;
            if (t0.getWhitespacesBeforeCount() != 1) {
                if (!t0.isNewlineBefore() || k > 0) 
                    break;
            }
            if (!t0.isWhitespaceBefore() && t0.getPrevious().isChar('/')) 
                break;
            if (com.pullenti.morph.CharsInfo.ooEq(t0.getPrevious().chars, t.chars)) {
                t0 = t0.getPrevious();
                heads.add(0, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.TextToken.class))).term);
                ok = true;
                continue;
            }
            if ((t0.getPrevious().chars.isLatinLetter() && t0.getPrevious().chars.isAllUpper() && t0.getPrevious().getLengthChar() == 1) && k == 0) {
                t0 = t0.getPrevious();
                heads.add(0, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.TextToken.class))).term);
                ok = false;
                continue;
            }
            break;
        }
        if (!ok) 
            heads.remove(0);
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < heads.size(); i++) {
            if (i > 0) 
                tmp.append(' ');
            tmp.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(heads.get(i)));
        }
        for(String tail : tails) {
            tmp.append("/").append(tail);
        }
        if (((t1.getNext() != null && t1.getNext().isChar('@') && t1.getNext().getNext() != null) && t1.getNext().getNext().chars.isLatinLetter() && !t1.getNext().isWhitespaceAfter()) && !t1.isWhitespaceAfter()) 
            t1 = t1.getNext().getNext();
        UriReferent _uri = UriReferent._new2649("lotus", tmp.toString());
        return new com.pullenti.ner.ReferentToken(_uri, t0, t1, null);
    }

    private static com.pullenti.ner.core.TerminCollection m_Schemes;

    public static void initialize() throws Exception {
        if (m_Schemes != null) 
            return;
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        try {
            m_Schemes = new com.pullenti.ner.core.TerminCollection();
            String obj = com.pullenti.ner.bank.internal.ResourceHelper.getString("UriSchemes.csv");
            if (obj == null) 
                throw new Exception("Can't file resource file " + "UriSchemes.csv" + " in Organization analyzer");
            try (java.io.BufferedReader tr = new java.io.BufferedReader(new java.io.StringReader(obj))) {
                while(true) {
                    String line = tr.readLine();
                    if (line == null) 
                        break;
                    if (com.pullenti.n2j.Utils.isNullOrEmpty(line)) 
                        continue;
                    m_Schemes.add(com.pullenti.ner.core.Termin._new668(line, com.pullenti.morph.MorphLang.UNKNOWN, true, 0));
                }
            }
            for(String s : new String[] {"ISBN", "УДК", "ББК", "ТНВЭД", "ОКВЭД"}) {
                m_Schemes.add(com.pullenti.ner.core.Termin._new668(s, com.pullenti.morph.MorphLang.UNKNOWN, true, 1));
            }
            m_Schemes.add(com.pullenti.ner.core.Termin._new2663("Общероссийский классификатор форм собственности", "ОКФС", 1, "ОКФС"));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2663("Общероссийский классификатор организационно правовых форм", "ОКОПФ", 1, "ОКОПФ"));
            com.pullenti.ner.core.Termin t;
            m_Schemes.add(com.pullenti.ner.core.Termin._new668("WWW", com.pullenti.morph.MorphLang.UNKNOWN, true, 2));
            m_Schemes.add(com.pullenti.ner.core.Termin._new668("HTTP", com.pullenti.morph.MorphLang.UNKNOWN, true, 10));
            m_Schemes.add(com.pullenti.ner.core.Termin._new668("HTTPS", com.pullenti.morph.MorphLang.UNKNOWN, true, 10));
            m_Schemes.add(com.pullenti.ner.core.Termin._new668("SHTTP", com.pullenti.morph.MorphLang.UNKNOWN, true, 10));
            m_Schemes.add(com.pullenti.ner.core.Termin._new668("FTP", com.pullenti.morph.MorphLang.UNKNOWN, true, 10));
            t = com.pullenti.ner.core.Termin._new668("SKYPE", com.pullenti.morph.MorphLang.UNKNOWN, true, 3);
            t.addVariant("СКАЙП", true);
            t.addVariant("SKYPEID", true);
            t.addVariant("SKYPE ID", true);
            m_Schemes.add(t);
            t = com.pullenti.ner.core.Termin._new668("SWIFT", com.pullenti.morph.MorphLang.UNKNOWN, true, 3);
            t.addVariant("СВИФТ", true);
            m_Schemes.add(t);
            m_Schemes.add(com.pullenti.ner.core.Termin._new668("ICQ", com.pullenti.morph.MorphLang.UNKNOWN, true, 4));
            t = com.pullenti.ner.core.Termin._new2673("основной государственный регистрационный номер", "ОГРН", 5, "ОГРН", true);
            t.addVariant("ОГРН ИП", true);
            m_Schemes.add(t);
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Индивидуальный идентификационный номер", "ИИН", 5, "ИИН", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Индивидуальный номер налогоплательщика", "ИНН", 5, "ИНН", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Код причины постановки на учет", "КПП", 5, "КПП", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Банковский идентификационный код", "БИК", 5, "БИК", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("основной государственный регистрационный номер индивидуального предпринимателя", "ОГРНИП", 5, "ОГРНИП", true));
            t = com.pullenti.ner.core.Termin._new2673("Страховой номер индивидуального лицевого счёта", "СНИЛС", 5, "СНИЛС", true);
            t.addVariant("Свидетельство пенсионного страхования", false);
            t.addVariant("Страховое свидетельство обязательного пенсионного страхования", false);
            t.addVariant("Страховое свидетельство", false);
            m_Schemes.add(t);
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Общероссийский классификатор предприятий и организаций", "ОКПО", 5, "ОКПО", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Общероссийский классификатор объектов административно-территориального деления", "ОКАТО", 5, "ОКАТО", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Общероссийский классификатор территорий муниципальных образований", "ОКТМО", 5, "ОКТМО", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Общероссийский классификатор органов государственной власти и управления", "ОКОГУ", 5, "ОКОГУ", true));
            m_Schemes.add(com.pullenti.ner.core.Termin._new2673("Общероссийский классификатор Отрасли народного хозяйства", "ОКОНХ", 5, "ОКОНХ", true));
            t = com.pullenti.ner.core.Termin._new2685("РАСЧЕТНЫЙ СЧЕТ", com.pullenti.morph.MorphLang.UNKNOWN, true, "Р/С", 6, 20);
            t.addAbridge("Р.С.");
            t.addAbridge("Р.СЧ.");
            t.addAbridge("P.C.");
            t.addAbridge("РАСЧ.СЧЕТ");
            t.addAbridge("РАС.СЧЕТ");
            t.addAbridge("РАСЧ.СЧ.");
            t.addAbridge("РАС.СЧ.");
            t.addAbridge("Р.СЧЕТ");
            t.addVariant("СЧЕТ ПОЛУЧАТЕЛЯ", false);
            t.addVariant("СЧЕТ ОТПРАВИТЕЛЯ", false);
            t.addVariant("СЧЕТ", false);
            m_Schemes.add(t);
            t = com.pullenti.ner.core.Termin._new2686("ЛИЦЕВОЙ СЧЕТ", "Л/С", 6, 20);
            t.addAbridge("Л.С.");
            t.addAbridge("Л.СЧ.");
            t.addAbridge("Л/С");
            t.addAbridge("ЛИЦ.СЧЕТ");
            t.addAbridge("ЛИЦ.СЧ.");
            t.addAbridge("Л.СЧЕТ");
            m_Schemes.add(t);
            t = com.pullenti.ner.core.Termin._new2685("СПЕЦИАЛЬНЫЙ ЛИЦЕВОЙ СЧЕТ", com.pullenti.morph.MorphLang.UNKNOWN, true, "СПЕЦ/С", 6, 20);
            t.addAbridge("СПЕЦ.С.");
            t.addAbridge("СПЕЦ.СЧЕТ");
            t.addAbridge("СПЕЦ.СЧ.");
            t.addVariant("СПЕЦСЧЕТ", true);
            t.addVariant("СПЕЦИАЛЬНЫЙ СЧЕТ", true);
            m_Schemes.add(t);
            t = com.pullenti.ner.core.Termin._new2685("КОРРЕСПОНДЕНТСКИЙ СЧЕТ", com.pullenti.morph.MorphLang.UNKNOWN, true, "К/С", 6, 20);
            t.addAbridge("КОРР.СЧЕТ");
            t.addAbridge("КОР.СЧЕТ");
            t.addAbridge("КОРР.СЧ.");
            t.addAbridge("КОР.СЧ.");
            t.addAbridge("К.СЧЕТ");
            t.addAbridge("КОР.С.");
            t.addAbridge("К.С.");
            t.addAbridge("K.C.");
            t.addAbridge("К-С");
            t.addAbridge("К/С");
            t.addAbridge("К.СЧ.");
            t.addAbridge("К/СЧ");
            m_Schemes.add(t);
            t = com.pullenti.ner.core.Termin._new2689("КОД БЮДЖЕТНОЙ КЛАССИФИКАЦИИ", "КБК", "КБК", 6, 20, true);
            m_Schemes.add(t);
            com.pullenti.ner.uri.internal.UriItemToken.initialize();
        } catch(Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new UriAnalyzer());
    }
    public UriAnalyzer() {
        super();
    }
}
