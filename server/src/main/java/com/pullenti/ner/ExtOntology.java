/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Внешняя онтология
 */
public class ExtOntology {

    /**
     * Добавить элемент
     * @param extId произвольный объект
     * @param typeName имя типа сущности
     * @param _definition текстовое определение. Определение может содержать несколько  
     *  отдельных фрагментов, которые разделяются точкой с запятой. 
     *  Например, Министерство Обороны России; Минобороны
     * @return если null, то не получилось...
     */
    public ExtOntologyItem add(Object extId, String typeName, String _definition) {
        if (typeName == null || _definition == null) 
            return null;
        Referent r = _createReferent(typeName, _definition);
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new2728(extId, r, typeName);
        items.add(res);
        return res;
    }

    /**
     * Добавить готовую сущность
     * @param extId произвольный объект
     * @param referent готовая сущность (например, сфомированная явно)
     * @return 
     */
    public ExtOntologyItem addReferent(Object extId, Referent referent) {
        if (referent == null) 
            return null;
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new2728(extId, referent, referent.getTypeName());
        items.add(res);
        return res;
    }

    private Referent _createReferent(String typeName, String _definition) {
        Analyzer analyzer = null;
        com.pullenti.n2j.Outargwrapper<Analyzer> inoutarg2730 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2731 = com.pullenti.n2j.Utils.tryGetValue(m_AnalByType, typeName, inoutarg2730);
        analyzer = inoutarg2730.value;
        if (!inoutres2731) 
            return null;
        AnalysisResult ar = m_Processor._process(new SourceOfAnalysis(_definition), true, true, null, new com.pullenti.morph.MorphLang(null));
        if (ar == null || ar.firstToken == null) 
            return null;
        Referent r0 = ar.firstToken.getReferent();
        Token t = null;
        if (r0 != null) {
            if (com.pullenti.n2j.Utils.stringsNe(r0.getTypeName(), typeName)) 
                r0 = null;
        }
        if (r0 != null) 
            t = ar.firstToken;
        else {
            ReferentToken rt = analyzer.processOntologyItem(ar.firstToken);
            if (rt == null) 
                return null;
            r0 = rt.referent;
            t = rt.getEndToken();
        }
        for(t = t.getNext(); t != null; t = t.getNext()) {
            if (t.isChar(';') && t.getNext() != null) {
                Referent r1 = t.getNext().getReferent();
                if (r1 == null) {
                    ReferentToken rt = analyzer.processOntologyItem(t.getNext());
                    if (rt == null) 
                        continue;
                    t = rt.getEndToken();
                    r1 = rt.referent;
                }
                if (com.pullenti.n2j.Utils.stringsEq(r1.getTypeName(), typeName)) 
                    r0.mergeSlots(r1, true);
            }
        }
        if (r0 != null) 
            r0 = analyzer.persistAnalizerData.registerReferent(r0);
        return r0;
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param _definition новое определение
     * @return 
     */
    public boolean refresh(ExtOntologyItem item, String _definition) {
        if (item == null) 
            return false;
        Referent r = _createReferent(item.typeName, _definition);
        return refresh(item, r);
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param newReferent 
     * @return 
     */
    public boolean refresh(ExtOntologyItem item, Referent newReferent) {
        if (item == null) 
            return false;
        Analyzer analyzer = null;
        com.pullenti.n2j.Outargwrapper<Analyzer> inoutarg2732 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2733 = com.pullenti.n2j.Utils.tryGetValue(m_AnalByType, item.typeName, inoutarg2732);
        analyzer = inoutarg2732.value;
        if (!inoutres2733) 
            return false;
        if (analyzer.persistAnalizerData == null) 
            return true;
        if (item.referent != null) 
            analyzer.persistAnalizerData.removeReferent(item.referent);
        Referent oldReferent = item.referent;
        newReferent = analyzer.persistAnalizerData.registerReferent(newReferent);
        item.referent = newReferent;
        m_Hash = null;
        if (oldReferent != null && newReferent != null) {
            for(Analyzer a : m_Processor.getAnalyzers()) {
                if (a.persistAnalizerData != null) {
                    for(Referent rr : a.persistAnalizerData.getReferents()) {
                        for(Slot s : newReferent.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                newReferent.uploadSlot(s, rr);
                        }
                        for(Slot s : rr.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                rr.uploadSlot(s, newReferent);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Список элементов внешней онтологии
     */
    public java.util.ArrayList<ExtOntologyItem> items = new java.util.ArrayList< >();

    public ExtOntology(String specNames) {
        m_Processor = ProcessorService.createSpecificProcessor(specNames);
        m_AnalByType = new java.util.HashMap< >();
        for(Analyzer a : m_Processor.getAnalyzers()) {
            a.setPersistReferentsRegim(true);
            if (com.pullenti.n2j.Utils.stringsEq(a.getName(), "DENOMINATION")) 
                a.setIgnoreThisAnalyzer(true);
            else 
                for(ReferentClass t : a.getTypeSystem()) {
                    if (!m_AnalByType.containsKey(t.getName())) 
                        m_AnalByType.put(t.getName(), a);
                }
        }
    }

    private Processor m_Processor;

    private java.util.HashMap<String, Analyzer> m_AnalByType;

    /**
     * Используется внутренним образом
     * @param typeName 
     * @return 
     */
    public com.pullenti.ner.core.AnalyzerData _getAnalyzerData(String typeName) {
        Analyzer a;
        com.pullenti.n2j.Outargwrapper<Analyzer> inoutarg2734 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2735 = com.pullenti.n2j.Utils.tryGetValue(m_AnalByType, typeName, inoutarg2734);
        a = inoutarg2734.value;
        if (!inoutres2735) 
            return null;
        return a.persistAnalizerData;
    }

    private java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection> m_Hash = null;

    private void _initHash() {
        m_Hash = new java.util.HashMap< >();
        for(ExtOntologyItem it : items) {
            if (it.referent != null) {
                com.pullenti.ner.core.IntOntologyCollection ont;
                com.pullenti.n2j.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> inoutarg2737 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres2738 = com.pullenti.n2j.Utils.tryGetValue(m_Hash, it.referent.getTypeName(), inoutarg2737);
                ont = inoutarg2737.value;
                if (!inoutres2738) 
                    m_Hash.put(it.referent.getTypeName(), (ont = com.pullenti.ner.core.IntOntologyCollection._new2736(true)));
                if (it.referent.getTag() == null) 
                    it.referent.setTag(it);
                else if (it.referent.getTag() instanceof ExtOntologyItem) {
                    java.util.ArrayList<ExtOntologyItem> li = new java.util.ArrayList< >();
                    li.add((ExtOntologyItem)com.pullenti.n2j.Utils.cast(it.referent.getTag(), ExtOntologyItem.class));
                    li.add(it);
                    it.referent.setTag(li);
                }
                else if (it.referent.getTag() instanceof java.util.ArrayList) 
                    (((java.util.ArrayList<ExtOntologyItem>)com.pullenti.n2j.Utils.cast(it.referent.getTag(), java.util.ArrayList.class))).add(it);
                it.referent.intOntologyItem = null;
                ont.addReferent(it.referent);
            }
        }
    }

    public java.util.ArrayList<ExtOntologyItem> attachReferent(Referent r) {
        if (m_Hash == null) 
            _initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> inoutarg2739 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2740 = com.pullenti.n2j.Utils.tryGetValue(m_Hash, r.getTypeName(), inoutarg2739);
        onto = inoutarg2739.value;
        if (!inoutres2740) 
            return null;
        java.util.ArrayList<Referent> li = onto.tryAttachByReferent(r, null, false);
        if (li == null || li.size() == 0) 
            return null;
        java.util.ArrayList<ExtOntologyItem> res = null;
        for(Referent rr : li) {
            if (rr.getTag() instanceof ExtOntologyItem) {
                if (res == null) 
                    res = new java.util.ArrayList< >();
                if (!res.contains((ExtOntologyItem)com.pullenti.n2j.Utils.cast(rr.getTag(), ExtOntologyItem.class))) 
                    res.add((ExtOntologyItem)com.pullenti.n2j.Utils.cast(rr.getTag(), ExtOntologyItem.class));
            }
            else if (rr.getTag() instanceof java.util.ArrayList) {
                for(ExtOntologyItem eoi : (java.util.ArrayList<ExtOntologyItem>)com.pullenti.n2j.Utils.cast(rr.getTag(), java.util.ArrayList.class)) {
                    if (res == null) 
                        res = new java.util.ArrayList< >();
                    if (!res.contains(eoi)) 
                        res.add(eoi);
                }
            }
        }
        return res;
    }

    /**
     * Используется внутренним образом
     * @param typeName 
     * @param t 
     * @return 
     */
    public java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> attachToken(String typeName, Token t) {
        if (m_Hash == null) 
            _initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> inoutarg2741 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2742 = com.pullenti.n2j.Utils.tryGetValue(m_Hash, typeName, inoutarg2741);
        onto = inoutarg2741.value;
        if (!inoutres2742) 
            return null;
        return onto.tryAttach(t, null, false);
    }
    public ExtOntology() {
        this(null);
    }
}
