/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class MetaObject extends com.pullenti.ner.ReferentClass {

    public MetaObject() {
        super();
        com.pullenti.ner.Feature f = addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_KIND, "Класс", 1, 1);
        f.addValue(com.pullenti.ner.semantic.SemanticKind.OBJECT.toString(), "Объект", null, null);
        f.addValue(com.pullenti.ner.semantic.SemanticKind.PRONOUN.toString(), "Местоимение", null, null);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_BASE, "Значение", 0, 0);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_PROP, "Свойство", 0, 0);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_NAME, "Собственное имя", 0, 0);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_ALIAS, "Псевдоним (сокращение)", 0, 0);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_REF, "Ссылка", 0, 0);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_MISCREF, "Мелочь", 0, 0);
        addFeature(com.pullenti.ner.semantic.ObjectReferent.ATTR_ENTITY, "Сущность-заместитель", 0, 0);
        addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающий объект", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.semantic.ObjectReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Семантический объект";
    }


    public static String OBJIMAGEID = "semobj";

    public static String ACTIMAGEID = "semact";

    public static String OBJNAMEIMAGEID = "semobjname";

    public static String OBJPRONOUNIMAGEID = "semobjref";

    public static String NUMIMAGEID = "semnum";

    public static String TIMEIMAGEID = "semtime";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.semantic.ObjectReferent sy = (com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.semantic.ObjectReferent.class);
        if (sy != null) {
            if (sy.getKind() == com.pullenti.ner.semantic.SemanticKind.PRONOUN) 
                return OBJPRONOUNIMAGEID;
            if (sy.getName() != null || sy.getProxy() != null) 
                return OBJNAMEIMAGEID;
        }
        return OBJIMAGEID;
    }

    public static MetaObject globalMeta = new MetaObject();
}
