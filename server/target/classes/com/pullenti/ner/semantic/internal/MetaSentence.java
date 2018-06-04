/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class MetaSentence extends com.pullenti.ner.ReferentClass {

    public MetaSentence() {
        super();
        hideInGraph = true;
        com.pullenti.ner.Feature f = addFeature(com.pullenti.ner.semantic.SentenceReferent.ATTR_KIND, "Класс", 1, 1);
        f.addValue(com.pullenti.ner.semantic.SentenceKind.ANNOTATION.toString(), "Автореферат", null, null);
        addFeature(com.pullenti.ner.semantic.SentenceReferent.ATTR_REF, "Ссылка", 0, 0);
        addFeature(com.pullenti.ner.semantic.SentenceReferent.ATTR_CONTENT, "Содержимое", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.semantic.SentenceReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Предложение";
    }


    public static String IMAGEID = "sent";

    public static String ANNOIMAGEID = "anno";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.semantic.SentenceReferent sy = (com.pullenti.ner.semantic.SentenceReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.semantic.SentenceReferent.class);
        if (sy != null) {
            if (sy.getKind() == com.pullenti.ner.semantic.SentenceKind.ANNOTATION) 
                return ANNOIMAGEID;
        }
        return IMAGEID;
    }

    public static MetaSentence globalMeta = new MetaSentence();
}
