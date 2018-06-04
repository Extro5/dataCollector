/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех семантических анализаторов
 */
public abstract class Analyzer {

    /**
     * Запустить анализ
     * @param kit контейнер с данными
     */
    public void process(com.pullenti.ner.core.AnalysisKit kit) throws NumberFormatException {
    }

    /**
     * Уникальное наименование анализатора
     */
    public String getName() {
        return null;
    }


    /**
     * Заголовок анализатора
     */
    public String getCaption() {
        return null;
    }


    /**
     * Описание анализатора
     */
    public String getDescription() {
        return null;
    }


    @Override
    public String toString() {
        return getCaption() + " (" + getName() + ")";
    }

    public Analyzer clone() {
        return null;
    }

    /**
     * Список поддерживаемых типов объектов (сущностей), которые выделяет анализатор
     */
    public java.util.Collection<ReferentClass> getTypeSystem() {
        return new java.util.ArrayList< >();
    }


    /**
     * Список изображений объектов
     */
    public java.util.HashMap<String, byte[]> getImages() {
        return null;
    }


    /**
     * Признак специфического анализатора (предназначенного для конкретной предметной области). 
     *  Специфические анализаторы по умолчанию не добавляются в процессор (Processor)
     */
    public boolean isSpecific() {
        return false;
    }


    /**
     * Создать объект указанного типа
     * @param type 
     * @return 
     */
    public Referent createReferent(String type) {
        return null;
    }

    private static java.util.ArrayList<String> emptyList = new java.util.ArrayList< >();

    /**
     * Список имён типов объектов из других картриджей, которые желательно предварительно выделить (для управления приоритетом применения правил)
     */
    public Iterable<String> getUsedExternObjectTypes() {
        return emptyList;
    }


    /**
     * Сколько примерно времени работает анализатор по сравнению с другими (в условных единицах)
     */
    public int getProgressWeight() {
        return 0;
    }


    public java.util.ArrayList<com.pullenti.n2j.ProgressEventHandler> progress = new java.util.ArrayList<com.pullenti.n2j.ProgressEventHandler>();

    public java.util.ArrayList<com.pullenti.n2j.CancelEventHandler> cancel = new java.util.ArrayList<com.pullenti.n2j.CancelEventHandler>();

    protected boolean onProgress(int pos, int max, com.pullenti.ner.core.AnalysisKit kit) {
        boolean ret = true;
        if (progress.size() > 0) {
            if (pos >= 0 && pos <= max && max > 0) {
                long percent = (long)pos;
                percent *= ((long)100);
                percent /= ((long)max);
                if (percent != lastPercent) {
                    com.pullenti.n2j.ProgressEventArgs arg = new com.pullenti.n2j.ProgressEventArgs((int)percent, null);
                    for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, arg);
                    if (cancel.size() > 0) {
                        com.pullenti.n2j.CancelEventArgs cea = new com.pullenti.n2j.CancelEventArgs();
                        for(int iiid = 0; iiid < cancel.size(); iiid++) cancel.get(iiid).call(kit, cea);
                        ret = !cea.cancel;
                    }
                }
                lastPercent = percent;
            }
        }
        return ret;
    }

    private long lastPercent;

    protected boolean onMessage(Object message) {
        if (progress.size() > 0) 
            for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, new com.pullenti.n2j.ProgressEventArgs(-1, message));
        return true;
    }

    private boolean _persistreferentsregim;

    /**
     * Включить режим накопления выделяемых сущностей при обработке разных SourceOfText 
     *  (то есть локальные сущности будут накапливаться)
     */
    public boolean getPersistReferentsRegim() {
        return _persistreferentsregim;
    }

    /**
     * Включить режим накопления выделяемых сущностей при обработке разных SourceOfText 
     *  (то есть локальные сущности будут накапливаться)
     */
    public boolean setPersistReferentsRegim(boolean value) {
        _persistreferentsregim = value;
        return _persistreferentsregim;
    }


    private boolean _ignorethisanalyzer;

    /**
     * При установке в true будет игнорироваться при обработке (для отладки)
     */
    public boolean getIgnoreThisAnalyzer() {
        return _ignorethisanalyzer;
    }

    /**
     * При установке в true будет игнорироваться при обработке (для отладки)
     */
    public boolean setIgnoreThisAnalyzer(boolean value) {
        _ignorethisanalyzer = value;
        return _ignorethisanalyzer;
    }


    public com.pullenti.ner.core.AnalyzerData persistAnalizerData;

    /**
     * Используется внутренним образом
     * @return 
     */
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerData();
    }

    /**
     * Попытаться выделить сущность в указанном диапазоне (используется внутренним образом). 
     *  Кстати, выделенная сущность не сохраняется в локальной онтологии.
     * @param begin начало диапазона
     * @param end конец диапазона (если null, то до конца)
     * @return результат
     */
    public ReferentToken processReferent(Token begin, Token end) {
        return null;
    }

    /**
     * Это используется внутренним образом для обработки внешних онтологий
     * @param begin 
     * @return 
     */
    public ReferentToken processOntologyItem(Token begin) {
        return null;
    }
    public Analyzer() {
    }
}
