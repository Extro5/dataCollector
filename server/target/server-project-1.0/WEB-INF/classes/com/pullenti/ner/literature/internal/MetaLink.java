/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

/**
 * Метаинформация о литературном персонаже
 */
public class MetaLink extends com.pullenti.ner.ReferentClass {

    public MetaLink() {
        super();
        typeFi = addFeature(com.pullenti.ner.literature.LinkReferent.ATTR_TYPE, "Тип", 0, 1);
        addFeature(com.pullenti.ner.literature.LinkReferent.ATTR_NAME, "Наименование", 0, 1);
        addFeature(com.pullenti.ner.literature.LinkReferent.ATTR_SHORTNAME, "Краткое наименование", 0, 1);
        addFeature(com.pullenti.ner.literature.LinkReferent.ATTR_CHAR, "Персонаж", 1, 0);
    }

    public com.pullenti.ner.Feature typeFi;

    @Override
    public String getName() {
        return com.pullenti.ner.literature.LinkReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Связь персонажей";
    }


    public static String IMAGEID = "link";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.literature.LinkReferent ch = (com.pullenti.ner.literature.LinkReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.literature.LinkReferent.class);
        if (ch != null) {
        }
        return IMAGEID;
    }

    public static MetaLink GLOBALMETA = new MetaLink();
}
