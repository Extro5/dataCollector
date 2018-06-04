/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class MetaPredicate extends com.pullenti.ner.ReferentClass {

    public MetaPredicate() {
        super();
        addFeature(com.pullenti.ner.semantic.PredicateReferent.ATTR_BASE, "Корень", 1, 0);
        addFeature(com.pullenti.ner.semantic.PredicateReferent.ATTR_PROP, "Свойство", 0, 0);
        addFeature(com.pullenti.ner.semantic.PredicateReferent.ATTR_ACTANT, "Актант", 0, 0).setShowAsParent(true);
        com.pullenti.ner.Feature f = addFeature(com.pullenti.ner.semantic.PredicateReferent.ATTR_ATTR, "Атрибут", 0, 0);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.NOT.toString(), "Не", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.IMPERFECTIVE.toString(), "Несовершенный", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.PERFECTIVE.toString(), "Совершенный", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.INFINITIVE.toString(), "Инфинитив", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.IMPERATIVE.toString(), "Повелительный", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.PAST.toString(), "Прошедшее", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.PRESENT.toString(), "Настоящее", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.FUTURE.toString(), "Будущее", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.VERB.toString(), "Глагол", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.PARTICIPLE.toString(), "Причастие", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.TRANSREGRESSIVE.toString(), "Деепричастие", null, null);
        f.addValue(com.pullenti.ner.semantic.PredicateAttr.REFLEXIVE.toString(), "Возвратный", null, null);
        ATTRFEATURE = f;
    }

    @Override
    public String getName() {
        return com.pullenti.ner.semantic.PredicateReferent.OBJ_TYPENAME;
    }


    public static com.pullenti.ner.Feature ATTRFEATURE;

    @Override
    public String getCaption() {
        return "Предикат";
    }


    public static String IMAGEID = "predicate";

    public static String IMAGEPARTID = "participle";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.semantic.PredicateReferent pr = (com.pullenti.ner.semantic.PredicateReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.semantic.PredicateReferent.class);
        if (pr != null) {
            if (pr.isAttr(com.pullenti.ner.semantic.PredicateAttr.PARTICIPLE)) 
                return IMAGEPARTID;
        }
        return IMAGEID;
    }

    public static MetaPredicate globalMeta = new MetaPredicate();
}
