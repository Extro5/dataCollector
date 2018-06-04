/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.named.internal;

public class MetaNamedEntity extends com.pullenti.ner.ReferentClass {

    public MetaNamedEntity() {
        super();
        addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_KIND, "Класс", 1, 1);
        addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_TYPE, "Тип", 0, 0);
        addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_NAME, "Наименование", 0, 0);
        addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_REF, "Ссылка", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.named.NamedEntityReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Именованная сущность";
    }


    public static String IMAGEID = "monument";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.named.NamedEntityReferent) 
            return (((com.pullenti.ner.named.NamedEntityReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.named.NamedEntityReferent.class))).getKind().toString();
        return IMAGEID;
    }

    public static MetaNamedEntity GLOBALMETA = new MetaNamedEntity();
}
