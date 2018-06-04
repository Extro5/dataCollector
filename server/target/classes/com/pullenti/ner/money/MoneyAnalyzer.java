/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.money;

/**
 * Анализатор для денежных сумм
 */
public class MoneyAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "MONEY";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Деньги";
    }


    @Override
    public String getDescription() {
        return "Деньги...";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new MoneyAnalyzer();
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.money.internal.MoneyMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap< >();
        res.put(com.pullenti.ner.money.internal.MoneyMeta.IMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("money2.png"));
        res.put(com.pullenti.ner.money.internal.MoneyMeta.IMAGE2ID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("moneyerr.png"));
        return res;
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"GEO"});
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, MoneyReferent.OBJ_TYPENAME)) 
            return new MoneyReferent();
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
            com.pullenti.ner.ReferentToken mon = tryParse(t);
            if (mon != null) {
                mon.referent = ad.registerReferent(mon.referent);
                kit.embedToken(mon);
                t = mon;
                continue;
            }
        }
    }

    public static com.pullenti.ner.ReferentToken tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (!((t instanceof com.pullenti.ner.NumberToken)) && t.getLengthChar() != 1) 
            return null;
        com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(t);
        if (nex == null || nex.exTyp != com.pullenti.ner.core.NumberExType.MONEY) {
            if ((t instanceof com.pullenti.ner.NumberToken) && (t.getNext() instanceof com.pullenti.ner.TextToken) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                if (t.getNext().isHiphen() || t.getNext().getMorph()._getClass().isPreposition()) {
                    com.pullenti.ner.core.NumberExToken res1 = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(t.getNext().getNext());
                    if (res1 != null && res1.exTyp == com.pullenti.ner.core.NumberExType.MONEY) {
                        MoneyReferent res0 = new MoneyReferent();
                        if ((t.getNext().isHiphen() && res1.value == ((long)0) && res1.getEndToken().getNext() != null) && res1.getEndToken().getNext().isChar('(')) {
                            com.pullenti.ner.core.NumberExToken nex2 = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(res1.getEndToken().getNext().getNext());
                            if ((nex2 != null && com.pullenti.n2j.Utils.stringsEq(nex2.exTypParam, res1.exTypParam) && nex2.getEndToken().getNext() != null) && nex2.getEndToken().getNext().isChar(')')) {
                                if (nex2.value == (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value) {
                                    res0.setCurrency(nex2.exTypParam);
                                    res0.setValue(nex2.value);
                                    return new com.pullenti.ner.ReferentToken(res0, t, nex2.getEndToken().getNext(), null);
                                }
                                if (t.getPrevious() instanceof com.pullenti.ner.NumberToken) {
                                    if (nex2.value == ((((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class))).value * ((long)1000)) + (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value))) {
                                        res0.setCurrency(nex2.exTypParam);
                                        res0.setValue(nex2.value);
                                        return new com.pullenti.ner.ReferentToken(res0, t.getPrevious(), nex2.getEndToken().getNext(), null);
                                    }
                                    else if (t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) {
                                        if (nex2.value == ((((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getPrevious().getPrevious(), com.pullenti.ner.NumberToken.class))).value * ((long)1000000)) + ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class))).value * ((long)1000)) + (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value))) {
                                            res0.setCurrency(nex2.exTypParam);
                                            res0.setValue(nex2.value);
                                            return new com.pullenti.ner.ReferentToken(res0, t.getPrevious().getPrevious(), nex2.getEndToken().getNext(), null);
                                        }
                                    }
                                }
                            }
                        }
                        res0.setCurrency(res1.exTypParam);
                        res0.setValue((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value);
                        return new com.pullenti.ner.ReferentToken(res0, t, t, null);
                    }
                }
            }
            return null;
        }
        MoneyReferent res = new MoneyReferent();
        res.setCurrency(nex.exTypParam);
        res.setValue((long)nex.realValue);
        float re = (float)com.pullenti.n2j.Utils.mathRound(((nex.realValue - ((double)res.getValue()))) * ((double)100), 6);
        res.setRest((int)re);
        if (nex.realValue != nex.altRealValue) {
            if (res.getValue() != ((long)nex.altRealValue)) 
                res.setAltValue((long)nex.altRealValue);
            re = (float)com.pullenti.n2j.Utils.mathRound(((nex.altRealValue - ((double)((long)nex.altRealValue)))) * ((double)100), 6);
            if (((int)re) != res.getRest()) 
                res.setAltRest((int)re);
        }
        if (nex.altRestMoney > 0) 
            res.setAltRest(nex.altRestMoney);
        com.pullenti.ner.Token t1 = nex.getEndToken();
        if (t1.getNext() != null && t1.getNext().isChar('(')) {
            com.pullenti.ner.ReferentToken rt = tryParse(t1.getNext().getNext());
            if ((rt != null && rt.referent.canBeEquals(res, com.pullenti.ner.Referent.EqualType.WITHINONETEXT) && rt.getEndToken().getNext() != null) && rt.getEndToken().getNext().isChar(')')) 
                t1 = rt.getEndToken().getNext();
            else {
                rt = tryParse(t1.getNext());
                if (rt != null && rt.referent.canBeEquals(res, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                    t1 = rt.getEndToken();
            }
        }
        if (res.getAltValue() != null && res.getAltValue() > res.getValue()) {
            if (t.getWhitespacesBeforeCount() == 1 && (t.getPrevious() instanceof com.pullenti.ner.NumberToken)) {
                long delt = res.getAltValue() - res.getValue();
                long val = (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class))).value;
                if ((((res.getValue() < ((long)1000)) && ((delt % ((long)1000))) == ((long)0))) || (((res.getValue() < ((long)1000000)) && ((delt % ((long)1000000))) == ((long)0)))) {
                    t = t.getPrevious();
                    res.setValue(res.getAltValue());
                    res.setAltValue(null);
                }
            }
        }
        return new com.pullenti.ner.ReferentToken(res, t, t1, null);
    }

    public static void initialize() {
        com.pullenti.ner.ProcessorService.registerAnalyzer(new MoneyAnalyzer());
    }
    public MoneyAnalyzer() {
        super();
    }
}
