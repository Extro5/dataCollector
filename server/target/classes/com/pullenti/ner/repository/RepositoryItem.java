/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.repository;

/**
 * Элемент репозитория сущностей - 
 *  представление сущности для СУБД или какго другого внешнего хранилища 
 *  (обёртка над Referent)
 */
public class RepositoryItem implements Comparable<Object> {

    private int _id;

    /**
     * Уникальный идентификатор внутри репозитория
     */
    public int getId() {
        return _id;
    }

    /**
     * Уникальный идентификатор внутри репозитория
     */
    public int setId(int value) {
        _id = value;
        return _id;
    }


    private String _spelling;

    /**
     * Это ToString() от сущности
     */
    public String getSpelling() {
        return _spelling;
    }

    /**
     * Это ToString() от сущности
     */
    public String setSpelling(String value) {
        _spelling = value;
        return _spelling;
    }


    private String _typ;

    /**
     * Это тип сущности (поле TypeName)
     */
    public String getTyp() {
        return _typ;
    }

    /**
     * Это тип сущности (поле TypeName)
     */
    public String setTyp(String value) {
        _typ = value;
        return _typ;
    }


    private int _generalid;

    /**
     * Идентификатор сущности-обобщения ("общее-частное")
     */
    public int getGeneralId() {
        return _generalid;
    }

    /**
     * Идентификатор сущности-обобщения ("общее-частное")
     */
    public int setGeneralId(int value) {
        _generalid = value;
        return _generalid;
    }


    private int _parentid;

    /**
     * Идентификатор сущности-контейнера ("часть-целое")
     */
    public int getParentId() {
        return _parentid;
    }

    /**
     * Идентификатор сущности-контейнера ("часть-целое")
     */
    public int setParentId(int value) {
        _parentid = value;
        return _parentid;
    }


    private String _imageid;

    /**
     * Идентификатор иконки (саму иконку можно получить через  
     *  ProcessorService.GetImageById(imageId)
     */
    public String getImageId() {
        return _imageid;
    }

    /**
     * Идентификатор иконки (саму иконку можно получить через  
     *  ProcessorService.GetImageById(imageId)
     */
    public String setImageId(String value) {
        _imageid = value;
        return _imageid;
    }


    /**
     * Это строка, представляющая сериализацию сущности
     */
    public String data;

    /**
     * Это строка, в которой сериализуются примеры встречаемости сущности в текстах 
     *  (для десериализации используйте класс RepositoryItemSample
     */
    public String samples;

    /**
     * Используется произвольным образом
     */
    public Object tag;

    /**
     * Ссылка на репозиторий
     */
    public RepositoryBase repository;

    /**
     * Экземпляр сущности
     */
    public com.pullenti.ner.Referent referent;

    /**
     * Признак изменения
     */
    public boolean isChanged;

    public void updateChanges() throws javax.xml.stream.XMLStreamException {
        if (referent == null) 
            return;
        String dat = referent.serialize();
        if (com.pullenti.n2j.Utils.stringsNe(dat, data)) {
            data = dat;
            isChanged = true;
        }
        String str = referent.toString();
        if (com.pullenti.n2j.Utils.stringsNe(str, getSpelling())) {
            setSpelling(str);
            isChanged = true;
        }
        String img = referent.getImageId();
        if (com.pullenti.n2j.Utils.stringsNe(img, getImageId())) {
            setImageId(img);
            isChanged = true;
        }
        int par = 0;
        if (referent.getParentReferent() != null) 
            par = referent.getParentReferent().repositoryItemId;
        if (par != getParentId()) {
            setParentId(par);
            isChanged = true;
        }
        int gen = 0;
        if (referent.getGeneralReferent() != null) 
            gen = referent.getGeneralReferent().repositoryItemId;
        if (gen != getGeneralId()) {
            setGeneralId(gen);
            isChanged = true;
        }
    }

    public void mergeSamples(java.util.ArrayList<RepositoryItemSample> _samples) throws javax.xml.stream.XMLStreamException {
        if (_samples == null || _samples.size() == 0) 
            return;
        java.util.ArrayList<RepositoryItemSample> thisSams = RepositoryItemSample.deserialize(samples);
        if (thisSams == null) 
            thisSams = new java.util.ArrayList< >();
        if (thisSams.size() > 100) 
            return;
        java.util.HashMap<String, Boolean> hash = new java.util.HashMap< >();
        for(RepositoryItemSample s : thisSams) {
            if (!hash.containsKey(s.bodyPeace)) 
                hash.put(s.bodyPeace, true);
        }
        boolean isCh = false;
        for(RepositoryItemSample s : _samples) {
            if (!hash.containsKey(s.bodyPeace)) {
                thisSams.add(s);
                hash.put(s.bodyPeace, false);
                isCh = true;
            }
        }
        if (isCh) {
            java.util.Collections.sort(thisSams);
            String str = RepositoryItemSample.serialize(thisSams);
            if (str.length() < 32000) {
                isChanged = true;
                samples = str;
            }
        }
    }

    public void mergeSamplesRi(RepositoryItem it) throws javax.xml.stream.XMLStreamException {
        if (it == null) 
            return;
        mergeSamples(RepositoryItemSample.deserialize(it.samples));
    }

    @Override
    public int compareTo(Object obj) {
        RepositoryItem ri = (RepositoryItem)com.pullenti.n2j.Utils.cast(obj, RepositoryItem.class);
        if (ri == null) 
            return 0;
        int i = com.pullenti.n2j.Utils.stringsCompare(getTyp(), ri.getTyp(), false);
        if (i != 0) 
            return i;
        return com.pullenti.n2j.Utils.stringsCompare(getSpelling(), ri.getSpelling(), false);
    }

    @Override
    public String toString() {
        if (getId() == 0) 
            return (String)com.pullenti.n2j.Utils.notnull(getSpelling(), "?");
        else 
            return ((Integer)getId()).toString() + ": " + ((String)com.pullenti.n2j.Utils.notnull(getSpelling(), "?"));
    }
    public RepositoryItem() {
    }
}
