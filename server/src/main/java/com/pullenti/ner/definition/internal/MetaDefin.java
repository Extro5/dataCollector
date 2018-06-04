/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.definition.internal;

public class MetaDefin extends com.pullenti.ner.ReferentClass {

    public MetaDefin() {
        super();
        addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_TERMIN, "Термин", 1, 0);
        addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_TERMIN_ADD, "Дополнение термина", 0, 0);
        addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_VALUE, "Значение", 1, 0);
        addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_MISC, "Мелочь", 0, 0);
        addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_DECREE, "Ссылка на НПА", 0, 0);
        com.pullenti.ner.Feature fi = addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_KIND, "Тип", 1, 1);
        fi.addValue(com.pullenti.ner.definition.DefinitionKind.ASSERTATION.toString(), "Утверждение", null, null);
        fi.addValue(com.pullenti.ner.definition.DefinitionKind.DEFINITION.toString(), "Определение", null, null);
        fi.addValue(com.pullenti.ner.definition.DefinitionKind.NEGATION.toString(), "Отрицание", null, null);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.definition.DefinitionReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Тезис";
    }


    public static String IMAGEDEFID = "defin";

    public static String IMAGEASSID = "assert";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.definition.DefinitionReferent) {
            com.pullenti.ner.definition.DefinitionKind ki = (((com.pullenti.ner.definition.DefinitionReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.definition.DefinitionReferent.class))).getKind();
            if (ki == com.pullenti.ner.definition.DefinitionKind.DEFINITION) 
                return IMAGEDEFID;
        }
        return IMAGEASSID;
    }

    public static MetaDefin globalMeta = new MetaDefin();
}
