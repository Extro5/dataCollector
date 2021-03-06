/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.date;

/**
 * Семантический анализатор для дат и диапазонов дат
 */
public class DateAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "DATE";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Даты";
    }


    @Override
    public String getDescription() {
        return "Даты и диапазоны дат";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new DateAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.date.internal.MetaDate.GLOBALMETA, com.pullenti.ner.date.internal.MetaDateRange.GLOBALMETA});
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"PHONE"});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap< >();
        res.put(com.pullenti.ner.date.internal.MetaDate.DATEFULLIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("datefull.png"));
        res.put(com.pullenti.ner.date.internal.MetaDate.DATEIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("date.png"));
        res.put(com.pullenti.ner.date.internal.MetaDateRange.DATERANGEIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("daterange.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, DateReferent.OBJ_TYPENAME)) 
            return new DateReferent();
        if (com.pullenti.n2j.Utils.stringsEq(type, DateRangeReferent.OBJ_TYPENAME)) 
            return new DateRangeReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 10;
    }


    public static class DateAnalizerData extends com.pullenti.ner.core.AnalyzerData {
    
        private java.util.HashMap<String, com.pullenti.ner.Referent> m_Hash = new java.util.HashMap< >();
    
        @Override
        public java.util.Collection<com.pullenti.ner.Referent> getReferents() {
            return m_Hash.values();
        }
    
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            String key = referent.toString();
            com.pullenti.ner.Referent dr;
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Referent> inoutarg688 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres689 = com.pullenti.n2j.Utils.tryGetValue(m_Hash, key, inoutarg688);
            dr = inoutarg688.value;
            if (inoutres689) 
                return dr;
            m_Hash.put(key, referent);
            return referent;
        }
        public DateAnalizerData() {
            super();
        }
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new DateAnalizerData();
    }

    /**
     * Основная функция выделения дат
     * @param cnt 
     * @param stage 
     * @return 
     */
    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        DateAnalizerData ad = (DateAnalizerData)com.pullenti.n2j.Utils.cast(kit.getAnalyzerData(this), DateAnalizerData.class);
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> pli = com.pullenti.ner.date.internal.DateItemToken.tryAttachList(t, 20);
            if (pli == null || pli.size() == 0) 
                continue;
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = tryAttach(pli, false);
            if (rts != null) {
                DateReferent dat = null;
                DateReferent hi = null;
                for(int i = 0; i < rts.size(); i++) {
                    com.pullenti.ner.ReferentToken rt = rts.get(i);
                    if (rt.referent instanceof DateRangeReferent) {
                        DateRangeReferent dr = (DateRangeReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateRangeReferent.class);
                        if (dr.getDateFrom() != null) 
                            dr.setDateFrom((DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(dr.getDateFrom()), DateReferent.class));
                        if (dr.getDateTo() != null) 
                            dr.setDateTo((DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(dr.getDateTo()), DateReferent.class));
                        rt.referent = ad.registerReferent(rt.referent);
                        if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().isValue("ПЕРИОД", null)) 
                            rt.setBeginToken(rt.getBeginToken().getPrevious());
                        kit.embedToken(rt);
                        t = rt;
                        break;
                    }
                    DateReferent dt = (DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class);
                    if (dt.getHigher() != null) 
                        dt.setHigher((DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(dt.getHigher()), DateReferent.class));
                    rt.referent = ad.registerReferent(dt);
                    hi = (DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class);
                    if ((i < (rts.size() - 1)) && rt.tag == null) 
                        rt.referent.addOccurence(com.pullenti.ner.TextAnnotation._new690(kit.getSofa(), rt.beginChar, rt.endChar, rt.referent));
                    else {
                        dat = (DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class);
                        kit.embedToken(rt);
                        t = rt;
                        for(int j = i + 1; j < rts.size(); j++) {
                            if (rts.get(j).beginChar == t.beginChar) 
                                rts.get(j).setBeginToken(t);
                            if (rts.get(j).endChar == t.endChar) 
                                rts.get(j).setEndToken(t);
                        }
                    }
                }
                if ((dat != null && t.getPrevious() != null && t.getPrevious().isHiphen()) && t.getPrevious().getPrevious() != null && (t.getPrevious().getPrevious().getReferent() instanceof DateReferent)) {
                    DateReferent dat0 = (DateReferent)com.pullenti.n2j.Utils.cast(t.getPrevious().getPrevious().getReferent(), DateReferent.class);
                    DateRangeReferent dr = (DateRangeReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(DateRangeReferent._new691(dat0, dat)), DateRangeReferent.class);
                    com.pullenti.ner.ReferentToken diap = new com.pullenti.ner.ReferentToken(dr, t.getPrevious().getPrevious(), t, null);
                    kit.embedToken(diap);
                    t = diap;
                    continue;
                }
                if ((dat != null && t.getPrevious() != null && ((t.getPrevious().isHiphen() || t.getPrevious().isValue("ПО", null) || t.getPrevious().isValue("И", null)))) && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken)) {
                    com.pullenti.ner.Token t0 = t.getPrevious().getPrevious();
                    DateReferent dat0 = null;
                    int num = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.NumberToken.class))).value;
                    if (dat.getDay() > 0 && (num < dat.getDay()) && num > 0) {
                        if (dat.getHigher() != null) 
                            dat0 = DateReferent._new692(dat.getHigher(), num);
                        else if (dat.getMonth() > 0) 
                            dat0 = DateReferent._new693(dat.getMonth(), num);
                    }
                    else if (dat.getYear() > 0 && (num < dat.getYear()) && num > 1000) 
                        dat0 = DateReferent._new694(num);
                    if (dat0 != null) {
                        com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(ad.registerReferent(dat0), t0, t0, null);
                        kit.embedToken(rt0);
                        if (!t.getPrevious().isHiphen()) 
                            continue;
                        dat0 = (DateReferent)com.pullenti.n2j.Utils.cast(rt0.referent, DateReferent.class);
                        DateRangeReferent dr = (DateRangeReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(DateRangeReferent._new691(dat0, dat)), DateRangeReferent.class);
                        com.pullenti.ner.ReferentToken diap = new com.pullenti.ner.ReferentToken(dr, rt0, t, null);
                        if (diap.getBeginToken().getPrevious() != null && diap.getBeginToken().getPrevious().isValue("С", null)) 
                            diap.setBeginToken(diap.getBeginToken().getPrevious());
                        kit.embedToken(diap);
                        t = diap;
                        continue;
                    }
                }
                continue;
            }
            t = pli.get(pli.size() - 1).getEndToken();
        }
        applyDateRange0(kit, ad);
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (begin == null) 
            return null;
        if (begin.isValue("ДО", null) && (begin.getNext() instanceof com.pullenti.ner.ReferentToken) && (begin.getNext().getReferent() instanceof DateReferent)) {
            DateRangeReferent drr = DateRangeReferent._new696((DateReferent)com.pullenti.n2j.Utils.cast(begin.getNext().getReferent(), DateReferent.class));
            com.pullenti.ner.ReferentToken res1 = new com.pullenti.ner.ReferentToken(drr, begin, begin.getNext(), null);
            res1.data = (DateAnalizerData)com.pullenti.n2j.Utils.cast(begin.kit.getAnalyzerData(this), DateAnalizerData.class);
            return res1;
        }
        java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> pli = com.pullenti.ner.date.internal.DateItemToken.tryAttachList(begin, 20);
        if (end != null && pli != null) {
            for(int i = 0; i < pli.size(); i++) {
                if (pli.get(i).beginChar > end.endChar) {
                    for(int indRemoveRange = i + pli.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) pli.remove(indRemoveRange);
                    break;
                }
            }
        }
        if (pli == null || pli.size() == 0) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = tryAttach(pli, true);
        if (rts == null || rts.size() == 0) 
            return null;
        DateAnalizerData ad = (DateAnalizerData)com.pullenti.n2j.Utils.cast(begin.kit.getAnalyzerData(this), DateAnalizerData.class);
        com.pullenti.ner.ReferentToken res = rts.get(rts.size() - 1);
        for(int i = 0; i < (rts.size() - 1); i++) {
            if ((res.referent instanceof DateReferent) && (rts.get(i).referent instanceof DateReferent)) 
                res.referent.mergeSlots(rts.get(i).referent, true);
            else 
                rts.get(i).data = ad;
        }
        res.referent.addSlot(DateReferent.ATTR_HIGHER, null, true, 0);
        res.data = ad;
        return res;
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttach(java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> dts, boolean high) {
        if (dts == null || dts.size() == 0) 
            return null;
        if ((dts.get(0).canBeHour() && dts.size() > 2 && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) && dts.get(2).intValue >= 0 && (dts.get(2).intValue < 60)) {
            if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HOUR || ((dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER && ((dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HOUR || dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER))))) {
                if (dts.size() > 3 && dts.get(3).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && com.pullenti.n2j.Utils.stringsEq(dts.get(3).stringValue, dts.get(1).stringValue)) {
                }
                else {
                    java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> dts1 = new java.util.ArrayList(dts);
                    for(int indRemoveRange = 0 + 3 - 1, indMinIndex = 0; indRemoveRange >= indMinIndex; indRemoveRange--) dts1.remove(indRemoveRange);
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res1 = tryAttach(dts1, false);
                    if (res1 != null && (res1.get(res1.size() - 1).referent instanceof DateReferent) && (((DateReferent)com.pullenti.n2j.Utils.cast(res1.get(res1.size() - 1).referent, DateReferent.class))).getDay() > 0) {
                        DateReferent time = DateReferent._new697(dts.get(0).intValue, dts.get(2).intValue);
                        time.setHigher((DateReferent)com.pullenti.n2j.Utils.cast(res1.get(res1.size() - 1).referent, DateReferent.class));
                        res1.add(new com.pullenti.ner.ReferentToken(time, dts.get(0).getBeginToken(), res1.get(res1.size() - 1).getEndToken(), null));
                        return res1;
                    }
                }
            }
        }
        com.pullenti.ner.date.internal.DateItemToken year;
        com.pullenti.ner.date.internal.DateItemToken mon;
        com.pullenti.ner.date.internal.DateItemToken day;
        com.pullenti.ner.date.internal.DateItemToken cent = null;
        com.pullenti.ner.date.internal.DateItemToken point = null;
        boolean yearIsDif = false;
        boolean b = false;
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg742 = new com.pullenti.n2j.Outargwrapper< >();
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg743 = new com.pullenti.n2j.Outargwrapper< >();
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg744 = new com.pullenti.n2j.Outargwrapper< >();
        b = applyRuleFormal(dts, high, inoutarg742, inoutarg743, inoutarg744);
        year = inoutarg742.value;
        mon = inoutarg743.value;
        day = inoutarg744.value;
        if (b) {
            com.pullenti.ner.Token tt = dts.get(0).getBeginToken().getPrevious();
            if (tt != null) {
                if (tt.isValue("№", null) || tt.isValue("N", null)) 
                    b = false;
            }
        }
        if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.CENTURY) {
            cent = dts.get(0);
            b = true;
        }
        if (dts.size() == 1 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.POINTER && com.pullenti.n2j.Utils.stringsEq(dts.get(0).stringValue, "сегодня")) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
            res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new698(DatePointerType.TODAY), dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
            return res0;
        }
        if (dts.size() == 1 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR && dts.get(0).getYear() <= 0) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
            res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new698(DatePointerType.UNDEFINED), dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
            return res0;
        }
        if (!b && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.POINTER && dts.size() > 1) {
            if (dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                year = dts.get(1);
                point = dts.get(0);
                b = true;
            }
            else if (dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.CENTURY) {
                cent = dts.get(1);
                point = dts.get(0);
                b = true;
            }
            else if (dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                mon = dts.get(1);
                point = dts.get(0);
                if (dts.size() > 2 && ((dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR || dts.get(2).canBeYear()))) 
                    year = dts.get(2);
                b = true;
            }
        }
        if (!b) {
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg700 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg701 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg702 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.n2j.Outargwrapper<Boolean> inoutarg703 = new com.pullenti.n2j.Outargwrapper< >();
            b = applyRuleWithMonth(dts, high, inoutarg700, inoutarg701, inoutarg702, inoutarg703);
            year = inoutarg700.value;
            mon = inoutarg701.value;
            day = inoutarg702.value;
            yearIsDif = (inoutarg703.value != null ? inoutarg703.value : false);
        }
        if (!b) {
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg704 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg705 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> inoutarg706 = new com.pullenti.n2j.Outargwrapper< >();
            b = applyRuleYearOnly(dts, inoutarg704, inoutarg705, inoutarg706);
            year = inoutarg704.value;
            mon = inoutarg705.value;
            day = inoutarg706.value;
        }
        if (!b) {
            if (dts.size() == 2 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HOUR && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MINUTE) {
                com.pullenti.ner.Token t00 = dts.get(0).getBeginToken().getPrevious();
                if (t00 != null && (((t00.isValue("ТЕЧЕНИЕ", null) || t00.isValue("ПРОТЯГОМ", null) || t00.isValue("ЧЕРЕЗ", null)) || t00.isValue("ТЕЧІЮ", null)))) {
                }
                else {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    DateReferent time = DateReferent._new697(dts.get(0).intValue, dts.get(1).intValue);
                    res0.add(new com.pullenti.ner.ReferentToken(time, dts.get(0).getBeginToken(), dts.get(1).getEndToken(), null));
                    int cou = 0;
                    for(com.pullenti.ner.Token tt = dts.get(0).getBeginToken().getPrevious(); tt != null && (cou < 1000); tt = tt.getPrevious(),cou++) {
                        if (tt.getReferent() instanceof DateReferent) {
                            DateReferent dr = (DateReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), DateReferent.class);
                            if (dr.findSlot(DateReferent.ATTR_DAY, null, true) == null && dr.getHigher() != null) 
                                dr = dr.getHigher();
                            if (dr.findSlot(DateReferent.ATTR_DAY, null, true) != null) {
                                time.setHigher(dr);
                                break;
                            }
                        }
                    }
                    return res0;
                }
            }
            if ((dts.size() == 4 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) && dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH && dts.get(3).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                DateReferent yea = DateReferent._new694(dts.get(3).intValue);
                res0.add(com.pullenti.ner.ReferentToken._new709(yea, dts.get(3).getBeginToken(), dts.get(3).getEndToken(), dts.get(3).getMorph()));
                DateReferent mon1 = DateReferent._new710(dts.get(0).intValue, yea);
                res0.add(com.pullenti.ner.ReferentToken._new711(mon1, dts.get(0).getBeginToken(), dts.get(0).getEndToken(), mon1));
                DateReferent mon2 = DateReferent._new710(dts.get(2).intValue, yea);
                res0.add(new com.pullenti.ner.ReferentToken(mon2, dts.get(2).getBeginToken(), dts.get(3).getEndToken(), null));
                return res0;
            }
            if (((dts.size() >= 4 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER && dts.get(0).canBeDay()) && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) && dts.get(2).canBeDay() && dts.get(3).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                if (dts.size() == 4 || ((dts.size() == 5 && dts.get(4).canBeYear()))) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    DateReferent yea = null;
                    if (dts.size() == 5) 
                        res0.add(new com.pullenti.ner.ReferentToken((yea = DateReferent._new694(dts.get(4).getYear())), dts.get(4).getBeginToken(), dts.get(4).getEndToken(), null));
                    DateReferent mo = DateReferent._new710(dts.get(3).intValue, yea);
                    res0.add(new com.pullenti.ner.ReferentToken(mo, dts.get(3).getBeginToken(), dts.get(dts.size() - 1).getEndToken(), null));
                    DateReferent da1 = DateReferent._new715(dts.get(0).intValue, mo);
                    res0.add(new com.pullenti.ner.ReferentToken(da1, dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                    DateReferent da2 = DateReferent._new715(dts.get(2).intValue, mo);
                    res0.add(new com.pullenti.ner.ReferentToken(da2, dts.get(2).getBeginToken(), dts.get(dts.size() - 1).getEndToken(), null));
                    DateRangeReferent dr = new DateRangeReferent();
                    dr.setDateFrom(da1);
                    dr.setDateTo(da2);
                    res0.add(new com.pullenti.ner.ReferentToken(dr, dts.get(0).getBeginToken(), dts.get(dts.size() - 1).getEndToken(), null));
                    return res0;
                }
            }
            if ((dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH && dts.size() == 1 && dts.get(0).getEndToken().getNext() != null) && ((dts.get(0).getEndToken().getNext().isHiphen() || dts.get(0).getEndToken().getNext().isValue("ПО", null) || dts.get(0).getEndToken().getNext().isValue("НА", null)))) {
                com.pullenti.ner.ReferentToken rt = dts.get(0).kit.processReferent(DateAnalyzer.ANALYZER_NAME, dts.get(0).getEndToken().getNext().getNext());
                if (rt != null) {
                    DateReferent dr0 = (DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class);
                    if ((dr0 != null && dr0.getYear() > 0 && dr0.getMonth() > 0) && dr0.getDay() == 0 && dr0.getMonth() > dts.get(0).intValue) {
                        DateReferent drYear0 = DateReferent._new694(dr0.getYear());
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                        res0.add(new com.pullenti.ner.ReferentToken(drYear0, dts.get(0).getEndToken(), dts.get(0).getEndToken(), null));
                        DateReferent drMon0 = DateReferent._new710(dts.get(0).intValue, drYear0);
                        res0.add(new com.pullenti.ner.ReferentToken(drMon0, dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                        return res0;
                    }
                }
            }
            if (((dts.size() == 3 && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && dts.get(1).getBeginToken().isHiphen()) && dts.get(0).canBeYear() && dts.get(2).canBeYear()) && (dts.get(0).intValue < dts.get(2).intValue)) {
                boolean ok = false;
                if (dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) 
                    ok = true;
                else if (dts.get(0).getLengthChar() == 4 && dts.get(2).getLengthChar() == 4 && dts.get(0).getBeginToken().getPrevious() != null) {
                    com.pullenti.ner.Token tt0 = dts.get(0).getBeginToken().getPrevious();
                    if (tt0.isChar('(') && dts.get(2).getEndToken().getNext() != null && dts.get(2).getEndToken().getNext().isChar(')')) 
                        ok = true;
                    else if (tt0.isValue("IN", null) || tt0.isValue("SINCE", null) || tt0.isValue("В", "У")) 
                        ok = true;
                }
                if (ok) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new694(dts.get(0).getYear()), dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                    res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new694(dts.get(2).getYear()), dts.get(2).getBeginToken(), dts.get(2).getEndToken(), null));
                    return res0;
                }
            }
            if (dts.size() > 1 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new694(dts.get(0).getYear()), dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                return res0;
            }
            if (high) {
                if (dts.size() == 1 && dts.get(0).canBeYear() && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new694(dts.get(0).getYear()), dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                    return res0;
                }
                if ((((dts.size() == 3 && dts.get(0).canBeYear() && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) && dts.get(2).canBeYear() && dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) && (dts.get(0).getYear() < dts.get(2).getYear()) && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) && dts.get(1).getBeginToken().isHiphen()) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    DateReferent y1 = DateReferent._new694(dts.get(0).getYear());
                    res0.add(new com.pullenti.ner.ReferentToken(y1, dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                    DateReferent y2 = DateReferent._new694(dts.get(2).getYear());
                    res0.add(new com.pullenti.ner.ReferentToken(y1, dts.get(2).getBeginToken(), dts.get(2).getEndToken(), null));
                    DateRangeReferent ra = DateRangeReferent._new691(y1, y2);
                    res0.add(new com.pullenti.ner.ReferentToken(ra, dts.get(0).getBeginToken(), dts.get(2).getEndToken(), null));
                    return res0;
                }
            }
            if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.QUARTAL || dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HALFYEAR) {
                if (dts.size() == 1 || dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    int ii = 0;
                    DateReferent yea = null;
                    if (dts.size() > 1) {
                        ii = 1;
                        yea = DateReferent._new694(dts.get(1).intValue);
                        res0.add(com.pullenti.ner.ReferentToken._new709(yea, dts.get(1).getBeginToken(), dts.get(1).getEndToken(), dts.get(1).getMorph()));
                    }
                    else {
                        int cou = 0;
                        for(com.pullenti.ner.Token tt = dts.get(0).getBeginToken(); tt != null; tt = tt.getPrevious()) {
                            if ((++cou) > 200) 
                                break;
                            if (tt instanceof com.pullenti.ner.ReferentToken) {
                                if ((((yea = _findYear_(tt.getReferent())))) != null) 
                                    break;
                            }
                            if (tt.isNewlineBefore()) 
                                break;
                        }
                    }
                    if (yea == null) 
                        return null;
                    int m1 = 0;
                    int m2 = 0;
                    if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HALFYEAR) {
                        if (dts.get(0).intValue == 1) {
                            m1 = 1;
                            m2 = 6;
                        }
                        else if (dts.get(0).intValue == 2) {
                            m1 = 7;
                            m2 = 12;
                        }
                        else 
                            return null;
                    }
                    else if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.QUARTAL) {
                        if (dts.get(0).intValue == 1) {
                            m1 = 1;
                            m2 = 3;
                        }
                        else if (dts.get(0).intValue == 2) {
                            m1 = 4;
                            m2 = 6;
                        }
                        else if (dts.get(0).intValue == 3) {
                            m1 = 7;
                            m2 = 9;
                        }
                        else if (dts.get(0).intValue == 4) {
                            m1 = 10;
                            m2 = 12;
                        }
                        else 
                            return null;
                    }
                    else 
                        return null;
                    DateReferent mon1 = DateReferent._new710(m1, yea);
                    res0.add(new com.pullenti.ner.ReferentToken(mon1, dts.get(0).getBeginToken(), dts.get(0).getBeginToken(), null));
                    DateReferent mon2 = DateReferent._new710(m2, yea);
                    res0.add(new com.pullenti.ner.ReferentToken(mon2, dts.get(0).getEndToken(), dts.get(0).getEndToken(), null));
                    DateRangeReferent dr = new DateRangeReferent();
                    dr.setDateFrom(mon1);
                    dr.setDateTo(mon2);
                    res0.add(new com.pullenti.ner.ReferentToken(dr, dts.get(0).getBeginToken(), dts.get(ii).getEndToken(), null));
                    return res0;
                }
            }
            if ((dts.size() == 3 && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && ((com.pullenti.n2j.Utils.stringsEq(dts.get(1).stringValue, ".") || com.pullenti.n2j.Utils.stringsEq(dts.get(1).stringValue, ":")))) && dts.get(0).canBeHour() && dts.get(2).canBeMinute()) {
                boolean ok = false;
                if (dts.get(0).getBeginToken().getPrevious() != null && dts.get(0).getBeginToken().getPrevious().isValue("В", null)) 
                    ok = true;
                if (ok) {
                    DateReferent time = DateReferent._new697(dts.get(0).intValue, dts.get(2).intValue);
                    int cou = 0;
                    for(com.pullenti.ner.Token tt = dts.get(0).getBeginToken().getPrevious(); tt != null && (cou < 1000); tt = tt.getPrevious(),cou++) {
                        if (tt.getReferent() instanceof DateReferent) {
                            DateReferent dr = (DateReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), DateReferent.class);
                            if (dr.findSlot(DateReferent.ATTR_DAY, null, true) == null && dr.getHigher() != null) 
                                dr = dr.getHigher();
                            if (dr.findSlot(DateReferent.ATTR_DAY, null, true) != null) {
                                time.setHigher(dr);
                                break;
                            }
                        }
                    }
                    com.pullenti.ner.Token tt1 = dts.get(2).getEndToken();
                    if (tt1.getNext() != null && tt1.getNext().isValue("ЧАС", null)) {
                        tt1 = tt1.getNext();
                        java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> dtsli = com.pullenti.ner.date.internal.DateItemToken.tryAttachList(tt1.getNext(), 20);
                        if (dtsli != null) {
                            java.util.ArrayList<com.pullenti.ner.ReferentToken> res1 = tryAttach(dtsli, true);
                            if (res1 != null && (((DateReferent)com.pullenti.n2j.Utils.cast(res1.get(res1.size() - 1).referent, DateReferent.class))).getDay() > 0) {
                                time.setHigher((DateReferent)com.pullenti.n2j.Utils.cast(res1.get(res1.size() - 1).referent, DateReferent.class));
                                res1.add(new com.pullenti.ner.ReferentToken(time, dts.get(0).getBeginToken(), tt1, null));
                                return res1;
                            }
                        }
                    }
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    res0.add(new com.pullenti.ner.ReferentToken(time, dts.get(0).getBeginToken(), tt1, null));
                    return res0;
                }
            }
            if ((dts.size() == 1 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH && dts.get(0).getBeginToken().getPrevious() != null) && dts.get(0).getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) {
                if (dts.get(0).chars.isLatinLetter() && dts.get(0).chars.isAllLower()) {
                }
                else {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList< >();
                    res0.add(new com.pullenti.ner.ReferentToken(DateReferent._new731(dts.get(0).intValue), dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                    return res0;
                }
            }
            return null;
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList< >();
        DateReferent drYear = null;
        DateReferent drMon = null;
        DateReferent drDay = null;
        com.pullenti.ner.Token t0 = null;
        com.pullenti.ner.Token t1 = null;
        if (cent != null) {
            DateReferent ce = DateReferent._new732((cent.newAge < 0 ? -cent.intValue : cent.intValue));
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ce, cent.getBeginToken(), (t1 = cent.getEndToken()), null);
            res.add(rt);
        }
        if (year != null && year.getYear() > 0) {
            drYear = DateReferent._new694((year.newAge < 0 ? -year.getYear() : year.getYear()));
            if (!yearIsDif) {
                t1 = year.getEndToken();
                if (t1.getNext() != null && t1.getNext().isValue("ГОРОД", null)) {
                    com.pullenti.ner.Token tt2 = t1.getNext().getNext();
                    if (tt2 == null) 
                        year.setEndToken((t1 = t1.getNext()));
                    else if ((tt2.getWhitespacesBeforeCount() < 3) && ((tt2.getMorph()._getClass().isPreposition() || tt2.chars.isAllLower()))) 
                        year.setEndToken((t1 = t1.getNext()));
                }
            }
            res.add(com.pullenti.ner.ReferentToken._new709(drYear, (t0 = year.getBeginToken()), year.getEndToken(), year.getMorph()));
        }
        if (mon != null) {
            drMon = DateReferent._new731(mon.intValue);
            if (drYear != null) 
                drMon.setHigher(drYear);
            if (t0 == null || (mon.beginChar < t0.beginChar)) 
                t0 = mon.getBeginToken();
            if (t1 == null || mon.endChar > t1.endChar) 
                t1 = mon.getEndToken();
            if (drYear == null && t1.getNext() != null && ((t1.getNext().isValue("ПО", null) || t1.getNext().isValue("НА", null)))) {
                com.pullenti.ner.ReferentToken rt = t1.kit.processReferent(DateAnalyzer.ANALYZER_NAME, t1.getNext().getNext());
                if (rt != null) {
                    DateReferent dr0 = (DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class);
                    if (dr0 != null && dr0.getYear() > 0 && dr0.getMonth() > 0) {
                        drYear = DateReferent._new694(dr0.getYear());
                        res.add(new com.pullenti.ner.ReferentToken(drYear, (t0 = t1), t1, null));
                        drMon.setHigher(drYear);
                    }
                }
            }
            res.add(com.pullenti.ner.ReferentToken._new709(drMon, t0, t1, mon.getMorph()));
            if (day != null) {
                drDay = DateReferent._new738(day.intValue);
                drDay.setHigher(drMon);
                if (day.beginChar < t0.beginChar) 
                    t0 = day.getBeginToken();
                if (day.endChar > t1.endChar) 
                    t1 = day.getEndToken();
                com.pullenti.ner.Token tt;
                for(tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (!tt.isCharOf(",.")) 
                        break;
                }
                com.pullenti.ner.core.TerminToken dow = com.pullenti.ner.date.internal.DateItemToken.DAYSOFWEEK.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (dow != null) {
                    t0 = tt;
                    drDay.setDayOfWeek((int)dow.termin.tag);
                }
                res.add(com.pullenti.ner.ReferentToken._new709(drDay, t0, t1, day.getMorph()));
                if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HOUR && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MINUTE) {
                    DateReferent hou = DateReferent._new740(drDay);
                    hou.setHour(dts.get(0).intValue);
                    hou.setMinute(dts.get(1).intValue);
                    if (dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.SECOND) 
                        hou.setSecond(dts.get(2).intValue);
                    res.add(new com.pullenti.ner.ReferentToken(hou, dts.get(0).getBeginToken(), t1, null));
                    return res;
                }
            }
        }
        if (point != null && res.size() > 0) {
            DateReferent poi = new DateReferent();
            if (com.pullenti.n2j.Utils.stringsEq(point.stringValue, "начало")) 
                poi.setPointer(DatePointerType.BEGIN);
            else if (com.pullenti.n2j.Utils.stringsEq(point.stringValue, "середина")) 
                poi.setPointer(DatePointerType.CENTER);
            else if (com.pullenti.n2j.Utils.stringsEq(point.stringValue, "конец")) 
                poi.setPointer(DatePointerType.END);
            else if (point.intValue != 0) 
                poi.setPointer(DatePointerType.of(point.intValue));
            poi.setHigher((DateReferent)com.pullenti.n2j.Utils.cast(res.get(res.size() - 1).referent, DateReferent.class));
            res.add(new com.pullenti.ner.ReferentToken(poi, point.getBeginToken(), t1, null));
            return res;
        }
        if (drDay != null && !yearIsDif) {
            com.pullenti.ner.ReferentToken rt = tryAttachTime(t1.getNext(), true);
            if (rt != null) {
                (((DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class))).setHigher(drDay);
                rt.setBeginToken(t0);
                res.add(rt);
            }
            else 
                for(int i = 1; i < dts.size(); i++) {
                    if (t0.beginChar == dts.get(i).beginChar) {
                        if (i > 2) {
                            for(int indRemoveRange = i + dts.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) dts.remove(indRemoveRange);
                            rt = tryAttachTimeLi(dts, true);
                            if (rt != null) {
                                (((DateReferent)com.pullenti.n2j.Utils.cast(rt.referent, DateReferent.class))).setHigher(drDay);
                                rt.setEndToken(t1);
                                res.add(rt);
                            }
                            break;
                        }
                    }
                }
        }
        if (res.size() == 1) {
            DateReferent dt0 = (DateReferent)com.pullenti.n2j.Utils.cast(res.get(0).referent, DateReferent.class);
            if (dt0.getMonth() == 0) {
                com.pullenti.ner.Token tt = res.get(0).getBeginToken().getPrevious();
                if (tt != null && tt.isChar('_') && !tt.isNewlineAfter()) {
                    for(; tt != null; tt = tt.getPrevious()) {
                        if (!tt.isChar('_')) 
                            break;
                        else 
                            res.get(0).setBeginToken(tt);
                    }
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt, true, null, false)) {
                        for(tt = tt.getPrevious(); tt != null; tt = tt.getPrevious()) {
                            if (tt.isNewlineAfter()) 
                                break;
                            else if (tt.isChar('_')) {
                            }
                            else {
                                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) 
                                    res.get(0).setBeginToken(tt);
                                break;
                            }
                        }
                    }
                }
                tt = res.get(0).getEndToken().getNext();
                if (tt != null && tt.isCharOf("(,")) {
                    com.pullenti.ner.date.internal.DateItemToken dit = com.pullenti.ner.date.internal.DateItemToken.tryAttach(tt.getNext(), null);
                    if (dit != null && dit.typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                        drMon = DateReferent._new741(dt0, dit.intValue);
                        com.pullenti.ner.ReferentToken prMon = new com.pullenti.ner.ReferentToken(drMon, res.get(0).getBeginToken(), dit.getEndToken(), null);
                        if (tt.isChar('(') && prMon.getEndToken().getNext() != null && prMon.getEndToken().getNext().isChar(')')) 
                            prMon.setEndToken(prMon.getEndToken().getNext());
                        res.add(prMon);
                    }
                }
            }
        }
        if (res.size() > 0 && drDay != null) {
            com.pullenti.ner.ReferentToken la = res.get(res.size() - 1);
            com.pullenti.ner.Token tt = la.getEndToken().getNext();
            if (tt != null && tt.isChar(',')) 
                tt = tt.getNext();
            com.pullenti.ner.core.TerminToken tok = com.pullenti.ner.date.internal.DateItemToken.DAYSOFWEEK.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                la.setEndToken(tok.getEndToken());
                drDay.setDayOfWeek((int)tok.termin.tag);
            }
        }
        return res;
    }

    private static DateReferent _findYear_(com.pullenti.ner.Referent r) {
        DateReferent dr = (DateReferent)com.pullenti.n2j.Utils.cast(r, DateReferent.class);
        if (dr != null) {
            for(; dr != null; dr = dr.getHigher()) {
                if (dr.getHigher() == null && dr.getYear() > 0) 
                    return dr;
            }
            return null;
        }
        DateRangeReferent drr = (DateRangeReferent)com.pullenti.n2j.Utils.cast(r, DateRangeReferent.class);
        if (drr != null) {
            if ((((dr = _findYear_(drr.getDateFrom())))) != null) 
                return dr;
            if ((((dr = _findYear_(drr.getDateTo())))) != null) 
                return dr;
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken tryAttachTime(com.pullenti.ner.Token t, boolean afterDate) {
        if (t == null) 
            return null;
        if (t.isValue("ГОРОД", null) && t.getNext() != null) 
            t = t.getNext();
        while(t != null && ((t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isAdverb() || t.isComma()))) {
            if (t.getMorph().getLanguage().isRu()) {
                if (!t.isValue("ПО", null) && !t.isValue("НА", null)) 
                    t = t.getNext();
                else 
                    break;
            }
            else 
                t = t.getNext();
        }
        if (t == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> dts = com.pullenti.ner.date.internal.DateItemToken.tryAttachList(t, 10);
        return tryAttachTimeLi(dts, afterDate);
    }

    private com.pullenti.ner.Token _corrTime(com.pullenti.ner.Token t0, DateReferent time) {
        com.pullenti.ner.Token t1 = null;
        for(com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                break;
            String term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (com.pullenti.n2j.Utils.stringsEq(term, "МСК")) {
                t1 = t;
                continue;
            }
            if ((t.isCharOf("(") && t.getNext() != null && t.getNext().isValue("МСК", null)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                t1 = (t = t.getNext().getNext());
                continue;
            }
            if ((com.pullenti.n2j.Utils.stringsEq(term, "PM") || com.pullenti.n2j.Utils.stringsEq(term, "РМ") || t.isValue("ВЕЧЕР", "ВЕЧІР")) || t.isValue("ДЕНЬ", null)) {
                if (time.getHour() < 12) 
                    time.setHour(time.getHour() + 12);
                t1 = t;
                continue;
            }
            if ((com.pullenti.n2j.Utils.stringsEq(term, "AM") || com.pullenti.n2j.Utils.stringsEq(term, "АМ") || com.pullenti.n2j.Utils.stringsEq(term, "Ч")) || t.isValue("ЧАС", null)) {
                t1 = t;
                continue;
            }
            if (t.isChar('+')) {
                java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> ddd = com.pullenti.ner.date.internal.DateItemToken.tryAttachList(t.getNext(), 20);
                if ((ddd != null && ddd.size() == 3 && ddd.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) && ddd.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && ddd.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) {
                    t1 = ddd.get(2).getEndToken();
                    continue;
                }
            }
            if (t.isCharOf(",.")) 
                continue;
            break;
        }
        return t1;
    }

    private com.pullenti.ner.ReferentToken tryAttachTimeLi(java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> dts, boolean afterDate) {
        if (dts == null || (dts.size() < 1)) 
            return null;
        com.pullenti.ner.Token t0 = dts.get(0).getBeginToken();
        com.pullenti.ner.Token t1 = null;
        DateReferent time = null;
        if (dts.size() == 1) {
            if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HOUR && afterDate) {
                time = DateReferent._new697(dts.get(0).intValue, 0);
                t1 = dts.get(0).getEndToken();
            }
            else 
                return null;
        }
        else if (dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.HOUR && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MINUTE) {
            time = DateReferent._new697(dts.get(0).intValue, dts.get(1).intValue);
            t1 = dts.get(1).getEndToken();
            if (dts.size() > 2 && dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.SECOND) {
                t1 = dts.get(2).getEndToken();
                time.setSecond(dts.get(2).intValue);
            }
        }
        else if ((((dts.size() > 2 && dts.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER && dts.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) && ((com.pullenti.n2j.Utils.stringsEq(dts.get(1).stringValue, ":") || com.pullenti.n2j.Utils.stringsEq(dts.get(1).stringValue, ".") || com.pullenti.n2j.Utils.stringsEq(dts.get(1).stringValue, "-"))) && dts.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) && (dts.get(0).intValue < 24) && (dts.get(2).intValue < 60)) && dts.get(2).getLengthChar() == 2 && afterDate) {
            time = DateReferent._new697(dts.get(0).intValue, dts.get(2).intValue);
            t1 = dts.get(2).getEndToken();
            if ((dts.size() > 4 && com.pullenti.n2j.Utils.stringsEq(dts.get(3).stringValue, dts.get(1).stringValue) && dts.get(4).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) && (dts.get(4).intValue < 60)) {
                time.setSecond(dts.get(4).intValue);
                t1 = dts.get(4).getEndToken();
            }
        }
        if (time == null) 
            return null;
        com.pullenti.ner.Token tt = _corrTime(t1.getNext(), time);
        if (tt != null) 
            t1 = tt;
        int cou = 0;
        for(tt = t0.getPrevious(); tt != null && (cou < 1000); tt = tt.getPrevious(),cou++) {
            if (tt.getReferent() instanceof DateReferent) {
                DateReferent dr = (DateReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), DateReferent.class);
                if (dr.findSlot(DateReferent.ATTR_DAY, null, true) == null && dr.getHigher() != null) 
                    dr = dr.getHigher();
                if (dr.findSlot(DateReferent.ATTR_DAY, null, true) != null) {
                    time.setHigher(dr);
                    break;
                }
            }
        }
        if (t1.getNext() != null) {
            if (t1.getNext().isValue("ЧАС", null)) 
                t1 = t1.getNext();
        }
        return new com.pullenti.ner.ReferentToken(time, t0, t1, null);
    }

    private boolean applyRuleFormal(java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> its, boolean high, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> year, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> mon, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> day) {
        year.value = null;
        mon.value = null;
        day.value = null;
        int i;
        int j;
        for(i = 0; i < (its.size() - 4); i++) {
            if (its.get(i).getBeginToken().getPrevious() != null && its.get(i).getBeginToken().getPrevious().isChar(')') && (its.get(i).getWhitespacesBeforeCount() < 2)) 
                return false;
            if (!its.get(i).canBeDay() && !its.get(i).canBeYear() && !its.get(i).canByMonth()) 
                continue;
            if (!its.get(i).isWhitespaceBefore()) {
                if (its.get(i).getBeginToken().getPrevious() != null && ((its.get(i).getBeginToken().getPrevious().isCharOf("(;,") || its.get(i).getBeginToken().getPrevious().getMorph()._getClass().isPreposition() || its.get(i).getBeginToken().getPrevious().isTableControlChar()))) {
                }
                else 
                    continue;
            }
            for(j = i; j < (i + 4); j++) {
                if (its.get(j).isWhitespaceAfter()) {
                    if (high && !its.get(j).isNewlineAfter()) 
                        continue;
                    if (i == 0 && its.size() == 5 && ((j == 1 || j == 3))) {
                        if (its.get(j).getWhitespacesAfterCount() < 2) 
                            continue;
                    }
                    break;
                }
            }
            if (j < (i + 4)) 
                continue;
            if (its.get(i + 1).typ != com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM || its.get(i + 3).typ != com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM || com.pullenti.n2j.Utils.stringsNe(its.get(i + 1).stringValue, its.get(i + 3).stringValue)) 
                continue;
            j = i + 5;
            if ((j < its.size()) && !its.get(j).isWhitespaceBefore()) {
                if (its.get(j).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && its.get(j).isWhitespaceAfter()) {
                }
                else 
                    continue;
            }
            mon.value = (its.get(i + 2).canByMonth() ? its.get(i + 2) : null);
            if (!its.get(i).canBeDay()) {
                if (!its.get(i).canBeYear()) 
                    continue;
                year.value = its.get(i);
                if (mon.value != null && its.get(i + 4).canBeDay()) 
                    day.value = its.get(i + 4);
                else if (its.get(i + 2).canBeDay() && its.get(i + 4).canByMonth()) {
                    day.value = its.get(i + 2);
                    mon.value = its.get(i + 4);
                }
                else 
                    continue;
            }
            else if (!its.get(i).canBeYear()) {
                if (!its.get(i + 4).canBeYear()) {
                    if (!high) 
                        continue;
                }
                year.value = its.get(i + 4);
                if (mon.value != null && its.get(i).canBeDay()) 
                    day.value = its.get(i);
                else if (its.get(i).canByMonth() && its.get(i + 2).canBeDay()) {
                    mon.value = its.get(i);
                    day.value = its.get(i + 2);
                }
                else 
                    continue;
            }
            else 
                continue;
            if ((mon.value.intValue < 10) && !mon.value.isZeroHeaded()) {
                if (year.value.intValue < 1980) 
                    continue;
            }
            char delim = its.get(i + 1).stringValue.charAt(0);
            if ((delim != '/' && delim != '\\' && delim != '.') && delim != '-') 
                continue;
            if (delim == '.' || delim == '-') {
                if (year.value == its.get(i) && (year.value.intValue < 1900)) 
                    continue;
            }
            if ((i + 5) < its.size()) 
                for(int indRemoveRange = i + 5 + its.size() - i - 5 - 1, indMinIndex = i + 5; indRemoveRange >= indMinIndex; indRemoveRange--) its.remove(indRemoveRange);
            if (i > 0) 
                for(int indRemoveRange = 0 + i - 1, indMinIndex = 0; indRemoveRange >= indMinIndex; indRemoveRange--) its.remove(indRemoveRange);
            return true;
        }
        if (its.size() >= 5 && its.get(0).isWhitespaceBefore() && its.get(4).isWhitespaceAfter()) {
            if (its.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && its.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) {
                if (its.get(0).getLengthChar() == 2 && its.get(2).getLengthChar() == 2 && ((its.get(4).getLengthChar() == 2 || its.get(4).getLengthChar() == 4))) {
                    if (its.get(0).canBeDay() && its.get(2).canByMonth() && its.get(4).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) {
                        if ((!its.get(0).isWhitespaceAfter() && !its.get(1).isWhitespaceAfter() && !its.get(2).isWhitespaceAfter()) && !its.get(3).isWhitespaceAfter()) {
                            int iyear = 0;
                            int y = its.get(4).intValue;
                            if (y > 80 && (y < 100)) 
                                iyear = 1900 + y;
                            else if (y <= (java.time.LocalDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.of(0, 0)).getYear() - 2000)) 
                                iyear = y + 2000;
                            else 
                                return false;
                            its.get(4).setYear(iyear);
                            year.value = its.get(4);
                            mon.value = its.get(2);
                            day.value = its.get(0);
                            return true;
                        }
                    }
                }
            }
        }
        if (high && its.get(0).canBeYear() && its.size() == 1) {
            year.value = its.get(0);
            return true;
        }
        if (its.get(0).getBeginToken().getPrevious() != null && its.get(0).getBeginToken().getPrevious().isValue("ОТ", null) && its.size() == 4) {
            if (its.get(0).canBeDay() && its.get(3).canBeYear()) {
                if (its.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && its.get(2).canByMonth()) {
                    year.value = its.get(3);
                    mon.value = its.get(2);
                    day.value = its.get(0);
                    return true;
                }
                if (its.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM && its.get(1).canByMonth()) {
                    year.value = its.get(3);
                    mon.value = its.get(1);
                    day.value = its.get(0);
                    return true;
                }
            }
        }
        if ((its.size() == 3 && its.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER && its.get(0).canBeDay()) && its.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR && its.get(1).canByMonth()) {
            if (com.pullenti.ner.core.BracketHelper.isBracket(its.get(0).getBeginToken(), false) && com.pullenti.ner.core.BracketHelper.isBracket(its.get(0).getEndToken(), false)) {
                year.value = its.get(2);
                mon.value = its.get(1);
                day.value = its.get(0);
                return true;
            }
        }
        return false;
    }

    private boolean applyRuleWithMonth(java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> its, boolean high, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> year, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> mon, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> day, com.pullenti.n2j.Outargwrapper<Boolean> yearIsDiff) {
        year.value = null;
        mon.value = null;
        day.value = null;
        yearIsDiff.value = false;
        int i;
        if (its.size() == 2) {
            if (its.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH && its.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                year.value = its.get(1);
                mon.value = its.get(0);
                return true;
            }
            if (its.get(0).canBeDay() && its.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                mon.value = its.get(1);
                day.value = its.get(0);
                return true;
            }
        }
        for(i = 0; i < its.size(); i++) {
            if (its.get(i).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) 
                break;
        }
        if (i >= its.size()) 
            return false;
        com.pullenti.morph.MorphLang lang = its.get(i).lang;
        year.value = null;
        day.value = null;
        mon.value = its.get(i);
        int i0 = i;
        int i1 = i;
        int yearVal = 0;
        if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, (com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.RU, com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.IT, com.pullenti.morph.MorphLang.BY)), com.pullenti.morph.MorphLang.UA)))), com.pullenti.morph.MorphLang.UNKNOWN)) {
            if (((i + 1) < its.size()) && its.get(i + 1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                year.value = its.get(i + 1);
                i1 = i + 1;
                if (i > 0 && its.get(i - 1).canBeDay()) {
                    day.value = its.get(i - 1);
                    i0 = i - 1;
                }
            }
            else if (i > 0 && its.get(i - 1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                year.value = its.get(i - 1);
                i0 = i - 1;
                if (((i + 1) < its.size()) && its.get(i + 1).canBeDay()) {
                    day.value = its.get(i + 1);
                    i1 = i + 1;
                }
            }
            else if (((i + 1) < its.size()) && its.get(i + 1).canBeYear()) {
                year.value = its.get(i + 1);
                i1 = i + 1;
                if (i > 0 && its.get(i - 1).canBeDay()) {
                    day.value = its.get(i - 1);
                    i0 = i - 1;
                }
            }
            else if (i > 1 && its.get(i - 2).canBeYear() && its.get(i - 1).canBeDay()) {
                year.value = its.get(i - 2);
                day.value = its.get(i - 1);
                i0 = i - 2;
            }
            else if (i > 0 && its.get(i - 1).canBeYear()) {
                year.value = its.get(i - 1);
                i0 = i - 1;
                if (((i + 1) < its.size()) && its.get(i + 1).canBeDay()) {
                    day.value = its.get(i + 1);
                    i1 = i + 1;
                }
            }
            if (year.value == null && i == 1 && its.get(i - 1).canBeDay()) {
                for(int j = i + 1; j < its.size(); j++) {
                    if (its.get(j).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) {
                        if ((++j) >= its.size()) 
                            break;
                    }
                    if (its.get(j).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
                        year.value = its.get(j);
                        day.value = its.get(i - 1);
                        i0 = i - 1;
                        i1 = i;
                        yearIsDiff.value = true;
                        break;
                    }
                    if (!its.get(j).canBeDay()) 
                        break;
                    if ((++j) >= its.size()) 
                        break;
                    if (its.get(j).typ != com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) 
                        break;
                }
            }
        }
        else if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.EN)) {
            if (i == 1 && its.get(0).canBeDay()) {
                i1 = 2;
                day.value = its.get(0);
                i0 = 0;
                if ((i1 < its.size()) && its.get(i1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) 
                    i1++;
                if ((i1 < its.size()) && its.get(i1).canBeYear()) 
                    year.value = its.get(i1);
                if (year.value == null) {
                    i1 = 1;
                    yearVal = findYear(its.get(0).getBeginToken());
                }
            }
            else if (i == 0) {
                if (its.size() > 1 && its.get(1).canBeYear() && !its.get(1).canBeDay()) {
                    i1 = 2;
                    year.value = its.get(1);
                }
                else if (its.size() > 1 && its.get(1).canBeDay()) {
                    day.value = its.get(1);
                    i1 = 2;
                    if ((i1 < its.size()) && its.get(i1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) 
                        i1++;
                    if ((i1 < its.size()) && its.get(i1).canBeYear()) 
                        year.value = its.get(i1);
                    if (year.value == null) {
                        i1 = 1;
                        yearVal = findYear(its.get(0).getBeginToken());
                    }
                }
            }
        }
        if (year.value == null && yearVal == 0 && its.size() == 3) {
            if (its.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR && its.get(1).canBeDay() && its.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                i1 = 2;
                year.value = its.get(0);
                day.value = its.get(1);
            }
        }
        if (year.value != null || yearVal > 0) 
            return true;
        if (day.value != null && its.size() == 2) 
            return true;
        return false;
    }

    private int findYear(com.pullenti.ner.Token t) {
        int year = 0;
        int prevdist = 0;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            if (tt.isNewlineBefore()) 
                prevdist += 10;
            prevdist++;
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                if ((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.ReferentToken.class))).referent instanceof DateReferent) {
                    year = (((DateReferent)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.ReferentToken.class))).referent, DateReferent.class))).getYear();
                    break;
                }
            }
        }
        int dist = 0;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineAfter()) 
                dist += 10;
            dist++;
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                if ((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.ReferentToken.class))).referent instanceof DateReferent) {
                    if (year > 0 && (prevdist < dist)) 
                        return year;
                    else 
                        return (((DateReferent)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.ReferentToken.class))).referent, DateReferent.class))).getYear();
                }
            }
        }
        return year;
    }

    private boolean applyRuleYearOnly(java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> its, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> year, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> mon, com.pullenti.n2j.Outargwrapper<com.pullenti.ner.date.internal.DateItemToken> day) {
        year.value = null;
        mon.value = null;
        day.value = null;
        int i;
        boolean doubt = false;
        for(i = 0; i < its.size(); i++) {
            if (its.get(i).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) 
                break;
            else if (its.get(i).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.NUMBER) 
                doubt = true;
            else if (its.get(i).typ != com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) 
                return false;
        }
        if (i >= its.size()) {
            if (((its.size() == 1 && its.get(0).canBeYear() && its.get(0).intValue > 1900) && its.get(0).canBeYear() && (its.get(0).intValue < 2100)) && its.get(0).getBeginToken().getPrevious() != null) {
                if (((its.get(0).getBeginToken().getPrevious().isValue("В", null) || its.get(0).getBeginToken().getPrevious().isValue("У", null) || its.get(0).getBeginToken().getPrevious().isValue("З", null)) || its.get(0).getBeginToken().getPrevious().isValue("IN", null) || its.get(0).getBeginToken().getPrevious().isValue("SINCE", null))) {
                    if (its.get(0).getLengthChar() == 4 || its.get(0).getBeginToken().getMorph()._getClass().isAdjective()) {
                        year.value = its.get(0);
                        return true;
                    }
                }
            }
            return false;
        }
        if ((i + 1) == its.size()) {
            if (its.get(i).intValue > 1900 || its.get(i).newAge != 0) {
                year.value = its.get(i);
                return true;
            }
            if (doubt) 
                return false;
            if (its.get(i).intValue > 10 && (its.get(i).intValue < 100)) {
                if (its.get(i).getBeginToken().getPrevious() != null) {
                    if (its.get(i).getBeginToken().getPrevious().isValue("В", null) || its.get(i).getBeginToken().getPrevious().isValue("IN", null) || its.get(i).getBeginToken().getPrevious().isValue("У", null)) {
                        year.value = its.get(i);
                        return true;
                    }
                }
                if (its.get(i).getBeginToken().isValue("В", null) || its.get(i).getBeginToken().isValue("У", null) || its.get(i).getBeginToken().isValue("IN", null)) {
                    year.value = its.get(i);
                    return true;
                }
            }
            if (its.get(i).intValue >= 100) {
                year.value = its.get(i);
                return true;
            }
            return false;
        }
        if (its.size() == 1 && its.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR && its.get(0).getYear() <= 0) {
            year.value = its.get(0);
            return true;
        }
        if (((its.size() > 2 && its.get(0).canBeYear() && its.get(1).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.DELIM) && its.get(1).getBeginToken().isHiphen() && its.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) && (its.get(0).getYear0() < its.get(2).getYear0())) {
            year.value = its.get(0);
            return true;
        }
        if (its.get(0).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) {
            if ((its.get(0).getBeginToken().getPrevious() != null && its.get(0).getBeginToken().getPrevious().isHiphen() && (its.get(0).getBeginToken().getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken)) && (its.get(0).getBeginToken().getPrevious().getPrevious().getReferent() instanceof DateReferent)) {
                year.value = its.get(0);
                return true;
            }
        }
        return false;
    }

    private DateRangeReferent applyDateRange(com.pullenti.ner.core.AnalyzerData ad, java.util.ArrayList<com.pullenti.ner.date.internal.DateItemToken> its, com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphLang> lang) {
        lang.value = new com.pullenti.morph.MorphLang(null);
        if (its == null || (its.size() < 3)) 
            return null;
        if ((its.get(0).canBeYear() && com.pullenti.n2j.Utils.stringsEq(its.get(1).stringValue, "-") && its.get(2).typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) && (its.get(0).getYear() < its.get(2).getYear())) {
            DateRangeReferent res = new DateRangeReferent();
            res.setDateFrom((DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(DateReferent._new694(its.get(0).getYear())), DateReferent.class));
            com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(res.getDateFrom(), its.get(0).getBeginToken(), its.get(0).getEndToken(), null);
            res.setDateTo((DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(DateReferent._new694(its.get(2).getYear())), DateReferent.class));
            com.pullenti.ner.ReferentToken rt2 = new com.pullenti.ner.ReferentToken(res.getDateTo(), its.get(2).getBeginToken(), its.get(2).getEndToken(), null);
            lang.value = its.get(2).lang;
            return res;
        }
        return null;
    }

    private void applyDateRange0(com.pullenti.ner.core.AnalysisKit kit, com.pullenti.ner.core.AnalyzerData ad) {
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                continue;
            int yearVal1 = 0;
            int yearVal2 = 0;
            DateReferent date1 = null;
            DateReferent date2 = null;
            com.pullenti.morph.MorphLang lang = new com.pullenti.morph.MorphLang(null);
            com.pullenti.ner.Token t0 = t.getNext();
            String str = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (com.pullenti.n2j.Utils.stringsEq(str, "ON") && (t0 instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.core.TerminToken tok = com.pullenti.ner.date.internal.DateItemToken.DAYSOFWEEK.tryParse(t0, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) {
                    DateReferent dow = DateReferent._new750((int)tok.termin.tag);
                    com.pullenti.ner.ReferentToken rtd = new com.pullenti.ner.ReferentToken(ad.registerReferent(dow), t, tok.getEndToken(), null);
                    kit.embedToken(rtd);
                    t = rtd;
                    continue;
                }
            }
            if (com.pullenti.n2j.Utils.stringsEq(str, "С") || com.pullenti.n2j.Utils.stringsEq(str, "C")) 
                lang = com.pullenti.morph.MorphLang.RU;
            else if (com.pullenti.n2j.Utils.stringsEq(str, "З")) 
                lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.n2j.Utils.stringsEq(str, "BETWEEN")) 
                lang = com.pullenti.morph.MorphLang.EN;
            else if (com.pullenti.n2j.Utils.stringsEq(str, "IN")) {
                lang = com.pullenti.morph.MorphLang.EN;
                if ((t0 != null && t0.isValue("THE", null) && t0.getNext() != null) && t0.getNext().isValue("PERIOD", null)) 
                    t0 = t0.getNext().getNext();
            }
            else if (com.pullenti.n2j.Utils.stringsEq(str, "ПО") || com.pullenti.n2j.Utils.stringsEq(str, "ДО")) {
                if ((t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof DateReferent)) {
                    DateRangeReferent dr = DateRangeReferent._new696((DateReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), DateReferent.class));
                    com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(ad.registerReferent(dr), t, t.getNext(), null);
                    kit.embedToken(rt0);
                    t = rt0;
                    continue;
                }
            }
            else 
                continue;
            if (t0 == null) 
                continue;
            if (t0 instanceof com.pullenti.ner.ReferentToken) 
                date1 = (DateReferent)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.ReferentToken.class))).referent, DateReferent.class);
            if (date1 == null) {
                if (t0 instanceof com.pullenti.ner.NumberToken) {
                    int v = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.NumberToken.class))).value;
                    if ((v < 1000) || v >= 2100) 
                        continue;
                    yearVal1 = v;
                }
                else 
                    continue;
            }
            else 
                yearVal1 = date1.getYear();
            com.pullenti.ner.Token t1 = t0.getNext();
            if (t1 == null) 
                continue;
            if (t1.isValue("ПО", "ДО") || t1.isValue("ДО", null)) 
                lang = t1.getMorph().getLanguage();
            else if (t1.isValue("AND", null)) 
                lang = com.pullenti.morph.MorphLang.EN;
            else if (t1.isHiphen() && com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.EN)) {
            }
            else if (lang.isUa() && t1.isValue("І", null)) {
            }
            else 
                continue;
            t1 = t1.getNext();
            if (t1 == null) 
                continue;
            if (t1 instanceof com.pullenti.ner.ReferentToken) 
                date2 = (DateReferent)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.ReferentToken.class))).referent, DateReferent.class);
            if (date2 == null) {
                if (t1 instanceof com.pullenti.ner.NumberToken) {
                    com.pullenti.ner.core.NumberExToken nt1 = com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(t1);
                    if (nt1 != null) 
                        continue;
                    int v = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).value;
                    if (v > 0 && (v < yearVal1)) {
                        int yy = yearVal1 % 100;
                        if (yy < v) 
                            v += (((yearVal1 / 100)) * 100);
                    }
                    if ((v < 1000) || v >= 2100) 
                        continue;
                    yearVal2 = v;
                }
                else 
                    continue;
            }
            else 
                yearVal2 = date2.getYear();
            if (yearVal1 > yearVal2 && yearVal2 > 0) 
                continue;
            if (yearVal1 == yearVal2) {
                if (date1 == null || date2 == null) 
                    continue;
                if (DateReferent.compare(date1, date2) >= 0) 
                    continue;
            }
            if (date1 == null) {
                date1 = (DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(DateReferent._new694(yearVal1)), DateReferent.class);
                com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(date1, t0, t0, null);
                kit.embedToken(rt0);
                if (t0 == t) 
                    t = rt0;
            }
            if (date2 == null) {
                date2 = (DateReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(DateReferent._new694(yearVal2)), DateReferent.class);
                com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(date2, t1, t1, null);
                kit.embedToken(rt1);
                t1 = rt1;
            }
            t = new com.pullenti.ner.ReferentToken(ad.registerReferent(DateRangeReferent._new691(date1, date2)), t, t1, null);
            kit.embedToken((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
        }
    }

    public static void initialize() throws Exception {
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.date.internal.DateItemToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch(Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new DateAnalyzer());
    }
    public DateAnalyzer() {
        super();
    }
    public static DateAnalyzer _globalInstance;
    static {
        _globalInstance = new DateAnalyzer(); 
    }
}
