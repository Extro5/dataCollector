/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Семантический процессор
 */
public class Processor implements AutoCloseable {

    public Processor() {
        if(_globalInstance == null) return;
        _ProgressChangedEventHandler_OnProgressHandler = new ProgressChangedEventHandler_OnProgressHandler(this);
        _CancelEventHandler_OnCancel = new CancelEventHandler_OnCancel(this);
    }

    /**
     * Добавить анализатор, если его ещё нет
     * @param a экземпляр анализатора
     */
    public void addAnalyzer(Analyzer a) {
        if (a == null || a.getName() == null || m_AnalyzersHash.containsKey(a.getName())) 
            return;
        m_AnalyzersHash.put(a.getName(), a);
        m_Analyzers.add(a);
        a.progress.add(_ProgressChangedEventHandler_OnProgressHandler);
        a.cancel.add(_CancelEventHandler_OnCancel);
    }

    /**
     * Удалить анализатор
     * @param a 
     */
    public void delAnalyzer(Analyzer a) {
        if (!m_AnalyzersHash.containsKey(a.getName())) 
            return;
        m_AnalyzersHash.remove(a.getName());
        m_Analyzers.remove(a);
        a.progress.remove(_ProgressChangedEventHandler_OnProgressHandler);
        a.cancel.remove(_CancelEventHandler_OnCancel);
    }

    @Override
    public void close() {
        for(Analyzer w : getAnalyzers()) {
            w.progress.remove(_ProgressChangedEventHandler_OnProgressHandler);
            w.cancel.remove(_CancelEventHandler_OnCancel);
        }
    }

    /**
     * Последовательность обработки данных (анализаторы)
     */
    public java.util.Collection<Analyzer> getAnalyzers() {
        return m_Analyzers;
    }


    private java.util.ArrayList<Analyzer> m_Analyzers = new java.util.ArrayList< >();

    private java.util.HashMap<String, Analyzer> m_AnalyzersHash = new java.util.HashMap< >();

    /**
     * Найти анализатор по его имени
     * @param name 
     * @return 
     */
    public Analyzer findAnalyzer(String name) {
        Analyzer a;
        com.pullenti.n2j.Outargwrapper<Analyzer> inoutarg2749 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2750 = com.pullenti.n2j.Utils.tryGetValue(m_AnalyzersHash, (String)com.pullenti.n2j.Utils.notnull(name, ""), inoutarg2749);
        a = inoutarg2749.value;
        if (!inoutres2750) 
            return null;
        else 
            return a;
    }

    /**
     * Обработать текст
     * @param text входной контейнер текста
     * @param extOntology внешняя онтология (null - не используется)
     * @param lang язык (если не задан, то будет определён автоматически)
     * @return аналитический контейнер с результатом
     */
    public AnalysisResult process(SourceOfAnalysis text, ExtOntology extOntology, com.pullenti.morph.MorphLang lang) {
        return _process(text, false, false, extOntology, lang);
    }

    /**
     * Доделать результат, который был сделан другим процессором
     * @param ar то, что было сделано другим процессором
     */
    public void processNext(AnalysisResult ar) {
        if (ar == null) 
            return;
        com.pullenti.ner.core.AnalysisKit kit = com.pullenti.ner.core.AnalysisKit._new2751(this, ar.ontology);
        kit.initFrom(ar);
        _process(kit, ar, false);
        _createRes(kit, ar, ar.ontology, false);
        ar.firstToken = kit.firstToken;
    }

    public AnalysisResult _process(SourceOfAnalysis text, boolean noEntitiesRegine, boolean noLog, ExtOntology extOntology, com.pullenti.morph.MorphLang lang) {
        m_Breaked = false;
        prepareProgress();
        com.pullenti.n2j.Stopwatch sw0 = com.pullenti.n2j.Utils.startNewStopwatch();
        manageReferentLinks();
        if (!noLog) 
            onProgressHandler(this, new com.pullenti.n2j.ProgressEventArgs(0, "Морфологический анализ"));
        com.pullenti.ner.core.AnalysisKit kit = com.pullenti.ner.core.AnalysisKit._new2752(text, false, lang, _ProgressChangedEventHandler_OnProgressHandler, extOntology, this);
        AnalysisResult ar = new AnalysisResult();
        sw0.stop();
        String msg;
        onProgressHandler(this, new com.pullenti.n2j.ProgressEventArgs(100, "Морфологический анализ завершён"));
        int k = 0;
        for(Token t = kit.firstToken; t != null; t = t.getNext()) {
            k++;
        }
        if (!noLog) {
            msg = "Из " + text.getText().length() + " символов текста выделено " + k + " термов за " + sw0.getElapsedMilliseconds() + " ms";
            if (!kit.baseLanguage.isUndefined()) 
                msg += ", базовый язык " + kit.baseLanguage.toString();
            onMessage(msg);
            ar.getLog().add(msg);
            if (text.crlfCorrectedCount > 0) 
                ar.getLog().add(((Integer)text.crlfCorrectedCount).toString() + " переходов на новую строку заменены на пробел");
            if (kit.firstToken == null) 
                ar.getLog().add("Пустой текст");
        }
        sw0.start();
        if (kit.firstToken != null) 
            _process(kit, ar, noLog);
        if (!noEntitiesRegine) 
            _createRes(kit, ar, extOntology, noLog);
        sw0.stop();
        if (!noLog) {
            if (sw0.getElapsed().getSeconds() > ((long)5)) {
                float f = (float)text.getText().length();
                f /= ((float)sw0.getElapsedMilliseconds());
                msg = "Обработка " + text.getText().length() + " знаков выполнена за " + outSecs(sw0.getElapsedMilliseconds()) + " (" + f + " Kb/sec)";
            }
            else 
                msg = "Обработка " + text.getText().length() + " знаков выполнена за " + outSecs(sw0.getElapsedMilliseconds());
            onMessage(msg);
            ar.getLog().add(msg);
        }
        if (timeoutSeconds > 0) {
            if (((java.time.Duration.between(kit.startDate, java.time.LocalDateTime.now()))).getSeconds() > timeoutSeconds) 
                ar.isTimeoutBreaked = true;
        }
        ar.getSofas().add(text);
        if (!noEntitiesRegine) 
            ar.getEntities().addAll(kit.getEntities());
        ar.firstToken = kit.firstToken;
        ar.ontology = extOntology;
        ar.baseLanguage = kit.baseLanguage;
        return ar;
    }

    private void _process(com.pullenti.ner.core.AnalysisKit kit, AnalysisResult ar, boolean noLog) {
        String msg;
        com.pullenti.n2j.Stopwatch sw = com.pullenti.n2j.Utils.startNewStopwatch();
        boolean stopByTimeout = false;
        java.util.ArrayList<Analyzer> anals = new java.util.ArrayList(m_Analyzers);
        for(int ii = 0; ii < anals.size(); ii++) {
            Analyzer c = anals.get(ii);
            if (c.getIgnoreThisAnalyzer()) 
                continue;
            if (m_Breaked) {
                if (!noLog) {
                    msg = "Процесс прерван пользователем";
                    onMessage(msg);
                    ar.getLog().add(msg);
                }
                break;
            }
            if (timeoutSeconds > 0 && !stopByTimeout) {
                if (((java.time.Duration.between(kit.startDate, java.time.LocalDateTime.now()))).getSeconds() > timeoutSeconds) {
                    m_Breaked = true;
                    if (!noLog) {
                        msg = "Процесс прерван по таймауту";
                        onMessage(msg);
                        ar.getLog().add(msg);
                    }
                    stopByTimeout = true;
                }
            }
            if (stopByTimeout) {
                if (com.pullenti.n2j.Utils.stringsEq(c.getName(), "INSTRUMENT")) {
                }
                else 
                    continue;
            }
            if (!noLog) 
                onProgressHandler(c, new com.pullenti.n2j.ProgressEventArgs(0, "Работа \"" + c.getCaption() + "\""));
            try {
                sw.reset();
                sw.start();
                c.process(kit);
                sw.stop();
                com.pullenti.ner.core.AnalyzerData dat = kit.getAnalyzerData(c);
                if (!noLog) {
                    msg = "Анализатор \"" + c.getCaption() + "\" выделил " + (dat == null ? 0 : dat.getReferents().size()) + " объект(ов) за " + outSecs(sw.getElapsedMilliseconds());
                    onMessage(msg);
                    ar.getLog().add(msg);
                }
            } catch(Exception ex) {
                if (!noLog) {
                    ex = new Exception("Ошибка в анализаторе \"" + c.getCaption() + "\" (" + ex.getMessage() + ")", ex);
                    onMessage(ex);
                    ar.addException(ex);
                }
            }
        }
        if (!noLog) 
            onProgressHandler(null, new com.pullenti.n2j.ProgressEventArgs(0, "Пересчёт отношений обобщения"));
        try {
            sw.reset();
            sw.start();
            com.pullenti.ner.internal.GeneralRelationHelper.refreshGenerals(this, kit);
            sw.stop();
            if (!noLog) {
                msg = "Отношение обобщение пересчитано за " + outSecs(sw.getElapsedMilliseconds());
                onMessage(msg);
                ar.getLog().add(msg);
            }
        } catch(Exception ex) {
            if (!noLog) {
                ex = new Exception("Ошибка пересчёта отношения обобщения", ex);
                onMessage(ex);
                ar.addException(ex);
            }
        }
    }

    private void _createRes(com.pullenti.ner.core.AnalysisKit kit, AnalysisResult ar, ExtOntology extOntology, boolean noLog) {
        com.pullenti.n2j.Stopwatch sw = com.pullenti.n2j.Utils.startNewStopwatch();
        int ontoAttached = 0;
        for(int k = 0; k < 2; k++) {
            for(Analyzer c : getAnalyzers()) {
                if (k == 0) {
                    if (!c.isSpecific()) 
                        continue;
                }
                else if (c.isSpecific()) 
                    continue;
                com.pullenti.ner.core.AnalyzerData dat = kit.getAnalyzerData(c);
                if (dat != null && dat.getReferents().size() > 0) {
                    if (extOntology != null) {
                        for(Referent r : dat.getReferents()) {
                            if (r.ontologyItems == null) {
                                if ((((r.ontologyItems = extOntology.attachReferent(r)))) != null) 
                                    ontoAttached++;
                            }
                        }
                    }
                    ar.getEntities().addAll(dat.getReferents());
                }
            }
        }
        sw.stop();
        if (extOntology != null && !noLog) {
            String msg = "Привязано " + ontoAttached + " объектов к внешней отнологии (" + extOntology.items.size() + " элементов) за " + outSecs(sw.getElapsedMilliseconds());
            onMessage(msg);
            ar.getLog().add(msg);
        }
    }

    private static String outSecs(long ms) {
        if (ms < ((long)4000)) 
            return ((Long)ms).toString() + "ms";
        ms /= ((long)1000);
        if (ms < ((long)120)) 
            return ((Long)ms).toString() + "sec";
        return (((Long)(ms / ((long)60))).toString()) + "min " + (ms % ((long)60)) + "sec";
    }

    /**
     * Событие обработки строки состояния процесса. 
     *  Там-же в событии ProgressChangedEventArg в UserState выводятся информационные сообщения. 
     *  Внимание, если ProgressPercentage &lt; 0, то учитывать только информационное сообщение в UserState.
     */
    public java.util.ArrayList<com.pullenti.n2j.ProgressEventHandler> progress = new java.util.ArrayList<com.pullenti.n2j.ProgressEventHandler>();

    private java.util.HashMap<Object, com.pullenti.ner.core.internal.ProgressPeace> m_ProgressPeaces = new java.util.HashMap< >();

    private Object m_ProgressPeacesLock = new Object();

    /**
     * Прервать процесс анализа
     */
    public void _break() {
        m_Breaked = true;
    }

    private boolean m_Breaked = false;

    /**
     * Максимальное время обработки, прервёт при превышении. 
     *  По умолчанию (0) - неограничено.
     */
    public int timeoutSeconds = 0;

    private static final int morphCoef = 10;

    private void prepareProgress() {
        synchronized (m_ProgressPeacesLock) {
            lastPercent = -1;
            int co = morphCoef;
            int total = co;
            for(Analyzer wf : getAnalyzers()) {
                total += (wf.getProgressWeight() > 0 ? wf.getProgressWeight() : 1);
            }
            m_ProgressPeaces.clear();
            float max = (float)(co * 100);
            max /= ((float)total);
            m_ProgressPeaces.put(this, com.pullenti.ner.core.internal.ProgressPeace._new2753((float)0, max));
            for(Analyzer wf : getAnalyzers()) {
                float min = max;
                co += (wf.getProgressWeight() > 0 ? wf.getProgressWeight() : 1);
                max = (float)(co * 100);
                max /= ((float)total);
                if (!m_ProgressPeaces.containsKey(wf)) 
                    m_ProgressPeaces.put(wf, com.pullenti.ner.core.internal.ProgressPeace._new2753(min, max));
            }
        }
    }

    private void onProgressHandler(Object sender, com.pullenti.n2j.ProgressEventArgs e) {
        if (progress.size() == 0) 
            return;
        if (e.getProgressPercentage() >= 0) {
            com.pullenti.ner.core.internal.ProgressPeace pi;
            synchronized (m_ProgressPeacesLock) {
                com.pullenti.n2j.Outargwrapper<com.pullenti.ner.core.internal.ProgressPeace> inoutarg2755 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres2756 = com.pullenti.n2j.Utils.tryGetValue(m_ProgressPeaces, (Object)com.pullenti.n2j.Utils.notnull(sender, this), inoutarg2755);
                pi = inoutarg2755.value;
                if (inoutres2756) {
                    float p = (((float)e.getProgressPercentage()) * ((pi.max - pi.min))) / ((float)100);
                    p += pi.min;
                    int pers = (int)p;
                    if (pers == lastPercent && e.getUserState() == null && !m_Breaked) 
                        return;
                    e = new com.pullenti.n2j.ProgressEventArgs((int)p, e.getUserState());
                    lastPercent = pers;
                }
            }
        }
        for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, e);
    }

    private void onCancel(Object sender, com.pullenti.n2j.CancelEventArgs e) {
        if (timeoutSeconds > 0) {
            if (sender instanceof com.pullenti.ner.core.AnalysisKit) {
                if (((java.time.Duration.between((((com.pullenti.ner.core.AnalysisKit)com.pullenti.n2j.Utils.cast(sender, com.pullenti.ner.core.AnalysisKit.class))).startDate, java.time.LocalDateTime.now()))).getSeconds() > timeoutSeconds) 
                    m_Breaked = true;
            }
        }
        e.cancel = m_Breaked;
    }

    private void onMessage(Object message) {
        if (progress.size() > 0) 
            for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, new com.pullenti.n2j.ProgressEventArgs(-1, message));
    }

    private int lastPercent;

    private java.util.HashMap<String, Referent> m_Links = null;

    private java.util.HashMap<String, Referent> m_Links2 = null;

    private java.util.ArrayList<ProxyReferent> m_Refs = null;

    public void manageReferentLinks() {
        if (m_Refs != null) {
            for(ProxyReferent pr : m_Refs) {
                Referent r;
                com.pullenti.n2j.Outargwrapper<Referent> inoutarg2759 = new com.pullenti.n2j.Outargwrapper< >();
                Boolean inoutres2760 = com.pullenti.n2j.Utils.tryGetValue(m_Links2, pr.identity, inoutarg2759);
                r = inoutarg2759.value;
                if (pr.identity != null && m_Links2 != null && inoutres2760) 
                    pr.ownerReferent.uploadSlot(pr.ownerSlot, r);
                else {
                    com.pullenti.n2j.Outargwrapper<Referent> inoutarg2757 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2758 = com.pullenti.n2j.Utils.tryGetValue(m_Links, pr.value, inoutarg2757);
                    r = inoutarg2757.value;
                    if (m_Links != null && inoutres2758) 
                        pr.ownerReferent.uploadSlot(pr.ownerSlot, r);
                    else {
                    }
                }
            }
        }
        m_Links = (m_Links2 = null);
        m_Refs = null;
    }

    /**
     * Десериализация сущности
     * @param data результат сериализации, см. Referent.Serialize()
     * @param ontologyElement если не null, то элемент будет добавляться к внутренней онтологии, 
     *  и при привязке к нему у сущности будет устанавливаться соответствующее свойство (Referent.OntologyElement)
     * @return 
     */
    public Referent deserializeReferent(String data, String identity, boolean createLinks1) {
        try {
            com.pullenti.n2j.XmlDocumentWrapper xml = new com.pullenti.n2j.XmlDocumentWrapper();
            xml.doc = xml.db.parse(new org.xml.sax.InputSource(new java.io.StringReader(data)));
            return deserializeReferentFromXml(xml.doc.getDocumentElement(), identity, createLinks1);
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Десериализация сущности из узла XML
     * @param xml 
     * @param identity 
     * @return 
     */
    public Referent deserializeReferentFromXml(org.w3c.dom.Node xml, String identity, boolean createLinks1) {
        try {
            Referent res = null;
            for(Analyzer a : getAnalyzers()) {
                if ((((res = a.createReferent(xml.getNodeName())))) != null) 
                    break;
            }
            if (res == null) 
                return null;
            for(org.w3c.dom.Node x : (new com.pullenti.n2j.XmlNodeListWrapper(xml.getChildNodes())).arr) {
                if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(x), "#text")) 
                    continue;
                String nam = x.getNodeName();
                if (nam.startsWith("ATCOM_")) 
                    nam = "@" + nam.substring(6);
                org.w3c.dom.Node att = com.pullenti.n2j.Utils.getXmlAttrByName(x, "ref");
                Slot slot = null;
                if (att != null && com.pullenti.n2j.Utils.stringsEq(att.getNodeValue(), "true")) {
                    ProxyReferent pr = ProxyReferent._new2761(x.getTextContent(), res);
                    slot = (pr.ownerSlot = res.addSlot(nam, pr, false, 0));
                    if ((((att = com.pullenti.n2j.Utils.getXmlAttrByName(x, "id")))) != null) 
                        pr.identity = att.getNodeValue();
                    if (m_Refs == null) 
                        m_Refs = new java.util.ArrayList< >();
                    m_Refs.add(pr);
                }
                else 
                    slot = res.addSlot(nam, x.getTextContent(), false, 0);
                if ((((att = com.pullenti.n2j.Utils.getXmlAttrByName(x, "count")))) != null) {
                    int cou;
                    com.pullenti.n2j.Outargwrapper<Integer> inoutarg2762 = new com.pullenti.n2j.Outargwrapper< >();
                    boolean inoutres2763 = com.pullenti.n2j.Utils.parseInteger(att.getNodeValue(), inoutarg2762);
                    cou = (inoutarg2762.value != null ? inoutarg2762.value : 0);
                    if (inoutres2763) 
                        slot.setCount(cou);
                }
            }
            if (m_Links == null) 
                m_Links = new java.util.HashMap< >();
            if (m_Links2 == null) 
                m_Links2 = new java.util.HashMap< >();
            if (createLinks1) {
                String key = res.toString();
                if (!m_Links.containsKey(key)) 
                    m_Links.put(key, res);
            }
            if (!com.pullenti.n2j.Utils.isNullOrEmpty(identity)) {
                res.setTag(identity);
                if (!m_Links2.containsKey(identity)) 
                    m_Links2.put(identity, res);
            }
            return res;
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Используется произвольным образом
     */
    public Object tag;

    /**
     * Используется произвольным образом
     */
    public java.util.HashMap<String, Object> miscData = new java.util.HashMap< >();

    public static class ProgressChangedEventHandler_OnProgressHandler implements com.pullenti.n2j.ProgressEventHandler {
    
        private com.pullenti.ner.Processor m_Source;
    
        private ProgressChangedEventHandler_OnProgressHandler(com.pullenti.ner.Processor src) {
            super();
            m_Source = src;
        }
    
        @Override
        public void call(Object sender, com.pullenti.n2j.ProgressEventArgs e) {
            m_Source.onProgressHandler(sender, e);
        }
        public ProgressChangedEventHandler_OnProgressHandler() {
        }
    }


    private ProgressChangedEventHandler_OnProgressHandler _ProgressChangedEventHandler_OnProgressHandler;

    public static class CancelEventHandler_OnCancel implements com.pullenti.n2j.CancelEventHandler {
    
        private com.pullenti.ner.Processor m_Source;
    
        private CancelEventHandler_OnCancel(com.pullenti.ner.Processor src) {
            super();
            m_Source = src;
        }
    
        @Override
        public void call(Object sender, com.pullenti.n2j.CancelEventArgs e) {
            m_Source.onCancel(sender, e);
        }
        public CancelEventHandler_OnCancel() {
        }
    }


    private CancelEventHandler_OnCancel _CancelEventHandler_OnCancel;
    public static Processor _globalInstance;
    static {
        _globalInstance = new Processor(); 
    }
}
