/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Результат анализа
 */
public class AnalysisResult {

    /**
     * Входные анализируемые тексты
     */
    public java.util.ArrayList<SourceOfAnalysis> getSofas() {
        return m_Sofas;
    }


    private java.util.ArrayList<SourceOfAnalysis> m_Sofas = new java.util.ArrayList< >();

    /**
     * Выделенные сущности
     */
    public java.util.ArrayList<Referent> getEntities() {
        return m_Entities;
    }


    private java.util.ArrayList<Referent> m_Entities = new java.util.ArrayList< >();

    /**
     * Ссылка на первый токен текста, который был проанализирован последним
     */
    public Token firstToken;

    /**
     * Используемая внешняя онтология
     */
    public ExtOntology ontology;

    /**
     * Базовый язык
     */
    public com.pullenti.morph.MorphLang baseLanguage;

    /**
     * Это некоторые информационные сообщения
     */
    public java.util.ArrayList<String> getLog() {
        return m_Log;
    }


    private java.util.ArrayList<String> m_Log = new java.util.ArrayList< >();

    /**
     * Возникшие исключения (одинаковые исключаются)
     */
    public java.util.ArrayList<Exception> exceptions = new java.util.ArrayList< >();

    public void addException(Exception ex) {
        String str = ex.toString();
        for(Exception e : exceptions) {
            if (com.pullenti.n2j.Utils.stringsEq(e.toString(), str)) 
                return;
        }
        exceptions.add(ex);
    }

    /**
     * Процесс был прерван по таймауту (если был задан)
     */
    public boolean isTimeoutBreaked = false;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        int len = 0;
        for(SourceOfAnalysis s : getSofas()) {
            len += s.getText().length();
        }
        res.append("Общая длина ").append(len).append(" знаков");
        if (getSofas().size() > 1) 
            res.append(" в ").append(getSofas().size()).append(" текстах");
        if (baseLanguage != null) 
            res.append(", базовый язык ").append(baseLanguage.toString());
        res.append(", найдено ").append(getEntities().size()).append(" сущностей");
        if (isTimeoutBreaked) 
            res.append(", прервано по таймауту");
        return res.toString();
    }
    public AnalysisResult() {
    }
}
