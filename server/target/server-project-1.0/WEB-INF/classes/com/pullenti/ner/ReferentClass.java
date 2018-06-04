/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Описатель некоторого класса сущностей
 */
public class ReferentClass {

    /**
     * Строковый идентификатор
     */
    public String getName() {
        return "?";
    }


    /**
     * Заголовок (зависит от текущего языка)
     */
    public String getCaption() {
        return null;
    }


    @Override
    public String toString() {
        return (String)com.pullenti.n2j.Utils.notnull(getCaption(), getName());
    }

    /**
     * Атрибуты класса
     */
    public java.util.ArrayList<Feature> getFeatures() {
        return m_Features;
    }


    private java.util.ArrayList<Feature> m_Features = new java.util.ArrayList< >();

    /**
     * Добавить фичу
     * @param attrName 
     * @param attrCaption 
     * @param lowBound 
     * @param upBound 
     * @return 
     */
    public Feature addFeature(String attrName, String attrCaption, int lowBound, int upBound) {
        Feature res = Feature._new2775(attrName, attrCaption, lowBound, upBound);
        m_Features.add(res);
        if (!m_Attrs.containsKey(attrName)) 
            m_Attrs.put(attrName, res);
        else 
            m_Attrs.put(attrName, res);
        return res;
    }

    /**
     * Найти атрибут по его системному имени
     * @param _name 
     * @return 
     */
    public Feature findFeature(String _name) {
        Feature res;
        com.pullenti.n2j.Outargwrapper<Feature> inoutarg2776 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2777 = com.pullenti.n2j.Utils.tryGetValue(m_Attrs, _name, inoutarg2776);
        res = inoutarg2776.value;
        if (!inoutres2777) 
            return null;
        else 
            return res;
    }

    private java.util.HashMap<String, Feature> m_Attrs = new java.util.HashMap< >();

    /**
     * Вычислить картинку
     * @param obj если null, то общая картинка для типа
     * @return идентификатор картинки, саму картинку можно будет получить через ProcessorService.GetImageById
     */
    public String getImageId(Referent obj) {
        return null;
    }

    /**
     * Не выводить на графе объектов
     */
    public boolean hideInGraph = false;
    public ReferentClass() {
    }
}
