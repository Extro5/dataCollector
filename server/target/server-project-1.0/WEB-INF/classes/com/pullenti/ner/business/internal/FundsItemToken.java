/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.business.internal;

public class FundsItemToken extends com.pullenti.ner.MetaToken {

    public FundsItemToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public FundsItemTyp typ = FundsItemTyp.UNDEFINED;

    public com.pullenti.ner.business.FundsKind kind = com.pullenti.ner.business.FundsKind.UNDEFINED;

    public com.pullenti.ner.Referent ref = null;

    public float floatVal;

    public long longVal;

    public String stringVal;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ);
        if (kind != com.pullenti.ner.business.FundsKind.UNDEFINED) 
            res.append(" K=").append(kind.toString());
        if (floatVal > 0) 
            res.append(" F=").append(floatVal);
        if (longVal > ((long)0)) 
            res.append(" L=").append(longVal);
        if (ref != null) 
            res.append(" R=").append(ref.toString());
        if (stringVal != null) 
            res.append(" S=").append(stringVal);
        return res.toString();
    }

    private static java.util.ArrayList<String> m_ActTypes = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ОБЫКНОВЕННЫЙ", "ПРИВИЛЕГИРОВАННЫЙ", "ГОЛОСУЮЩИЙ", "ЗВИЧАЙНИЙ", "ПРИВІЛЕЙОВАНОГО", "ГОЛОСУЄ"}));

    public static FundsItemToken tryParse(com.pullenti.ner.Token t, FundsItemToken prev) {
        if (t == null) 
            return null;
        FundsItemTyp typ0 = FundsItemTyp.UNDEFINED;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isAdverb()) 
                continue;
            if ((tt.isValue("СУММА", null) || tt.isValue("ОКОЛО", null) || tt.isValue("БОЛЕЕ", null)) || tt.isValue("МЕНЕЕ", null) || tt.isValue("СВЫШЕ", null)) 
                continue;
            if ((tt.isValue("НОМИНАЛ", null) || tt.isValue("ЦЕНА", null) || tt.isValue("СТОИМОСТЬ", null)) || tt.isValue("СТОИТЬ", null)) {
                typ0 = FundsItemTyp.PRICE;
                continue;
            }
            if (tt.isValue("НОМИНАЛЬНАЯ", null) || tt.isValue("ОБЩАЯ", null)) 
                continue;
            if (tt.isValue("СОСТАВЛЯТЬ", null)) 
                continue;
            com.pullenti.ner.Referent re = tt.getReferent();
            if (re instanceof com.pullenti.ner.org.OrganizationReferent) 
                return _new428(t, tt, FundsItemTyp.ORG, re);
            if (re instanceof com.pullenti.ner.money.MoneyReferent) {
                if (typ0 == FundsItemTyp.UNDEFINED) 
                    typ0 = FundsItemTyp.SUM;
                if ((tt.getNext() != null && tt.getNext().isValue("ЗА", null) && tt.getNext().getNext() != null) && ((tt.getNext().getNext().isValue("АКЦИЯ", null) || tt.getNext().getNext().isValue("АКЦІЯ", null)))) 
                    typ0 = FundsItemTyp.PRICE;
                FundsItemToken res = _new428(t, tt, typ0, re);
                return res;
            }
            if (re != null) 
                break;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.noun.isValue("ПАКЕТ", null)) 
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(npt.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) {
                FundsItemToken res = null;
                if (npt.noun.isValue("АКЦІЯ", null) || npt.noun.isValue("АКЦИЯ", null)) {
                    res = _new430(t, npt.getEndToken(), FundsItemTyp.NOUN, com.pullenti.ner.business.FundsKind.STOCK);
                    if (npt.adjectives.size() > 0) {
                        for(String v : m_ActTypes) {
                            if (npt.adjectives.get(0).isValue(v, null)) {
                                res.stringVal = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                                if (com.pullenti.n2j.Utils.stringsEq(res.stringVal, "голосовавшая акция")) 
                                    res.stringVal = "голосующая акция";
                                break;
                            }
                        }
                    }
                }
                else if (((npt.noun.isValue("БУМАГА", null) || npt.noun.isValue("ПАПІР", null))) && npt.getEndToken().getPrevious() != null && ((npt.getEndToken().getPrevious().isValue("ЦЕННЫЙ", null) || npt.getEndToken().getPrevious().isValue("ЦІННИЙ", null)))) 
                    res = _new431(t, npt.getEndToken(), FundsItemTyp.NOUN, com.pullenti.ner.business.FundsKind.STOCK, "ценные бумаги");
                else if (((npt.noun.isValue("КАПИТАЛ", null) || npt.noun.isValue("КАПІТАЛ", null))) && npt.adjectives.size() > 0 && ((npt.adjectives.get(0).isValue("УСТАВНОЙ", null) || npt.adjectives.get(0).isValue("УСТАВНЫЙ", null) || npt.adjectives.get(0).isValue("СТАТУТНИЙ", null)))) 
                    res = _new430(t, npt.getEndToken(), FundsItemTyp.NOUN, com.pullenti.ner.business.FundsKind.CAPITAL);
                if (res != null) {
                    com.pullenti.ner.ReferentToken rt = res.kit.processReferent(com.pullenti.ner.org.OrganizationAnalyzer.ANALYZER_NAME, res.getEndToken().getNext());
                    if (rt != null) {
                        res.ref = rt.referent;
                        res.setEndToken(rt.getEndToken());
                    }
                    return res;
                }
            }
            if (prev != null && prev.typ == FundsItemTyp.COUNT) {
                String val = null;
                for(String v : m_ActTypes) {
                    if (tt.isValue(v, null)) {
                        val = v;
                        break;
                    }
                }
                if (val != null) {
                    int cou = 0;
                    boolean ok = false;
                    for(com.pullenti.ner.Token ttt = tt.getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                        if ((++cou) > 100) 
                            break;
                        java.util.ArrayList<com.pullenti.ner.Referent> refs = ttt.getReferents();
                        if (refs == null) 
                            continue;
                        for(com.pullenti.ner.Referent r : refs) {
                            if (r instanceof com.pullenti.ner.business.FundsReferent) {
                                ok = true;
                                break;
                            }
                        }
                        if (ok) 
                            break;
                    }
                    cou = 0;
                    if (!ok) {
                        for(com.pullenti.ner.Token ttt = tt.getNext(); ttt != null; ttt = ttt.getNext()) {
                            if ((++cou) > 100) 
                                break;
                            FundsItemToken fi = FundsItemToken.tryParse(ttt, null);
                            if (fi != null && fi.kind == com.pullenti.ner.business.FundsKind.STOCK) {
                                ok = true;
                                break;
                            }
                        }
                    }
                    if (ok) {
                        FundsItemToken res = _new433(t, tt, com.pullenti.ner.business.FundsKind.STOCK, FundsItemTyp.NOUN);
                        res.stringVal = val.substring(0, 0+(val.length() - 2)).toLowerCase() + "ая акция";
                        return res;
                    }
                }
            }
            if (tt instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.core.NumberExToken num = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(tt);
                if (num != null) {
                    if (tt.getPrevious() != null && tt.getPrevious().isValue("НА", null)) 
                        break;
                    if (num.exTyp == com.pullenti.ner.core.NumberExType.PERCENT) {
                        FundsItemToken res = _new434(t, num.getEndToken(), FundsItemTyp.PERCENT, (float)num.realValue);
                        t = num.getEndToken().getNext();
                        if (t != null && ((t.isChar('+') || t.isValue("ПЛЮС", null))) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                            res.setEndToken(t.getNext());
                            t = res.getEndToken().getNext();
                        }
                        if ((t != null && t.isHiphen() && t.getNext() != null) && t.getNext().chars.isAllLower() && !t.isWhitespaceAfter()) 
                            t = t.getNext().getNext();
                        if (t != null && ((t.isValue("ДОЛЯ", null) || t.isValue("ЧАСТКА", null)))) 
                            res.setEndToken(t);
                        return res;
                    }
                    break;
                }
                com.pullenti.ner.Token t1 = tt;
                if (t1.getNext() != null && t1.getNext().isValue("ШТУКА", null)) 
                    t1 = t1.getNext();
                return _new435(t, t1, FundsItemTyp.COUNT, (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value);
            }
            break;
        }
        return null;
    }

    public static com.pullenti.ner.ReferentToken tryAttach(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        FundsItemToken f = tryParse(t, null);
        if (f == null) 
            return null;
        if (f.typ == FundsItemTyp.ORG) 
            return null;
        if (f.typ == FundsItemTyp.PRICE || f.typ == FundsItemTyp.PERCENT || f.typ == FundsItemTyp.COUNT) {
            if (t.getPrevious() != null && t.getPrevious().isCharOf(",.") && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken)) 
                return null;
        }
        java.util.ArrayList<FundsItemToken> li = new java.util.ArrayList< >();
        li.add(f);
        boolean isInBr = false;
        for(com.pullenti.ner.Token tt = f.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if ((tt.isWhitespaceBefore() && tt.getPrevious() != null && tt.getPrevious().isChar('.')) && tt.chars.isCapitalUpper()) 
                break;
            FundsItemToken f0 = tryParse(tt, f);
            if (f0 != null) {
                if (f0.kind == com.pullenti.ner.business.FundsKind.CAPITAL && isInBr) {
                    for(FundsItemToken l : li) {
                        if (l.typ == FundsItemTyp.NOUN) {
                            f0.kind = l.kind;
                            break;
                        }
                    }
                }
                li.add((f = f0));
                tt = f.getEndToken();
                continue;
            }
            if (tt.isChar('(')) {
                isInBr = true;
                continue;
            }
            if (tt.isChar(')')) {
                if (isInBr || ((t.getPrevious() != null && t.getPrevious().isChar('(')))) {
                    isInBr = false;
                    li.get(li.size() - 1).setEndToken(tt);
                    continue;
                }
            }
            if (tt.getMorph()._getClass().isVerb() || tt.getMorph()._getClass().isAdverb()) 
                continue;
            break;
        }
        com.pullenti.ner.business.FundsReferent funds = new com.pullenti.ner.business.FundsReferent();
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(funds, t, t, null);
        com.pullenti.ner.org.OrganizationReferent orgProb = null;
        for(int i = 0; i < li.size(); i++) {
            if (li.get(i).typ == FundsItemTyp.NOUN) {
                funds.setKind(li.get(i).kind);
                if (li.get(i).stringVal != null) 
                    funds.setTyp(li.get(i).stringVal);
                if (li.get(i).ref instanceof com.pullenti.ner.org.OrganizationReferent) 
                    orgProb = (com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(li.get(i).ref, com.pullenti.ner.org.OrganizationReferent.class);
                res.setEndToken(li.get(i).getEndToken());
            }
            else if (li.get(i).typ == FundsItemTyp.COUNT) {
                if (funds.getCount() > ((long)0) || li.get(i).longVal == ((long)0)) 
                    break;
                funds.setCount(li.get(i).longVal);
                res.setEndToken(li.get(i).getEndToken());
            }
            else if (li.get(i).typ == FundsItemTyp.ORG) {
                if (funds.getSource() != null && funds.getSource() != li.get(i).ref) 
                    break;
                funds.setSource((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(li.get(i).ref, com.pullenti.ner.org.OrganizationReferent.class));
                res.setEndToken(li.get(i).getEndToken());
            }
            else if (li.get(i).typ == FundsItemTyp.PERCENT) {
                if (funds.getPercent() > 0 || li.get(i).floatVal == 0) 
                    break;
                funds.setPercent(li.get(i).floatVal);
                res.setEndToken(li.get(i).getEndToken());
            }
            else if (li.get(i).typ == FundsItemTyp.SUM) {
                if (funds.getSum() != null) 
                    break;
                funds.setSum((com.pullenti.ner.money.MoneyReferent)com.pullenti.n2j.Utils.cast(li.get(i).ref, com.pullenti.ner.money.MoneyReferent.class));
                res.setEndToken(li.get(i).getEndToken());
            }
            else if (li.get(i).typ == FundsItemTyp.PRICE) {
                if (funds.getPrice() != null) 
                    break;
                funds.setPrice((com.pullenti.ner.money.MoneyReferent)com.pullenti.n2j.Utils.cast(li.get(i).ref, com.pullenti.ner.money.MoneyReferent.class));
                res.setEndToken(li.get(i).getEndToken());
            }
            else 
                break;
        }
        if (funds.getPercent() > 0 && funds.getSource() != null && funds.getKind() == com.pullenti.ner.business.FundsKind.UNDEFINED) 
            funds.setKind(com.pullenti.ner.business.FundsKind.STOCK);
        if (!funds.checkCorrect()) 
            return null;
        if (funds.getSource() == null) {
            int cou = 0;
            for(com.pullenti.ner.Token tt = res.getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                if ((++cou) > 500) 
                    break;
                if (tt.isNewlineAfter()) 
                    cou += 10;
                com.pullenti.ner.business.FundsReferent fr = (com.pullenti.ner.business.FundsReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.business.FundsReferent.class);
                if (fr != null && fr.getSource() != null) {
                    funds.setSource(fr.getSource());
                    break;
                }
            }
        }
        if (funds.getSource() == null && orgProb != null) 
            funds.setSource(orgProb);
        if (funds.getSource() == null) {
            int cou = 0;
            for(com.pullenti.ner.Token tt = res.getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                if ((++cou) > 300) 
                    break;
                if (tt.isNewlineAfter()) 
                    cou += 10;
                java.util.ArrayList<com.pullenti.ner.Referent> refs = tt.getReferents();
                if (refs != null) {
                    for(com.pullenti.ner.Referent r : refs) {
                        if (r instanceof com.pullenti.ner.org.OrganizationReferent) {
                            com.pullenti.ner.org.OrganizationKind ki = (((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.org.OrganizationReferent.class))).getKind();
                            if (ki == com.pullenti.ner.org.OrganizationKind.JUSTICE || ki == com.pullenti.ner.org.OrganizationKind.GOVENMENT) 
                                continue;
                            funds.setSource((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.org.OrganizationReferent.class));
                            cou = 10000;
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static FundsItemToken _new428(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, FundsItemTyp _arg3, com.pullenti.ner.Referent _arg4) {
        FundsItemToken res = new FundsItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }
    public static FundsItemToken _new430(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, FundsItemTyp _arg3, com.pullenti.ner.business.FundsKind _arg4) {
        FundsItemToken res = new FundsItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.kind = _arg4;
        return res;
    }
    public static FundsItemToken _new431(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, FundsItemTyp _arg3, com.pullenti.ner.business.FundsKind _arg4, String _arg5) {
        FundsItemToken res = new FundsItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.kind = _arg4;
        res.stringVal = _arg5;
        return res;
    }
    public static FundsItemToken _new433(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.business.FundsKind _arg3, FundsItemTyp _arg4) {
        FundsItemToken res = new FundsItemToken(_arg1, _arg2);
        res.kind = _arg3;
        res.typ = _arg4;
        return res;
    }
    public static FundsItemToken _new434(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, FundsItemTyp _arg3, float _arg4) {
        FundsItemToken res = new FundsItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.floatVal = _arg4;
        return res;
    }
    public static FundsItemToken _new435(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, FundsItemTyp _arg3, long _arg4) {
        FundsItemToken res = new FundsItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.longVal = _arg4;
        return res;
    }
    public FundsItemToken() {
        super();
    }
}
