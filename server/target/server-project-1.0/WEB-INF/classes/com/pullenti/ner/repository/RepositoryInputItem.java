/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.repository;

/**
 * Это обёртка для входной сущности
 */
public class RepositoryInputItem {

    /**
     * Это должно быть установлено на входе
     */
    public com.pullenti.ner.Referent referent;

    /**
     * Здесь могут быть ранее подготовленные примеры вхождений 
     *  (если нет, то будут вычисляться из Referent.Occurence)
     */
    public String samples;

    /**
     * Некоторые дополнительные данные
     */
    public Object additionalData;

    /**
     * Используется произвольным образом
     */
    public Object tag;

    /**
     * Это будет установлено после сохранения
     */
    public RepositoryItem item;

    /**
     * Используется внутренним образом
     */
    public boolean tmp;

    @Override
    public String toString() {
        return (referent == null ? "?" : referent.toString());
    }
    public RepositoryInputItem() {
    }
}
