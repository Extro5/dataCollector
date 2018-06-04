/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class MetaActant extends com.pullenti.ner.ReferentClass {

    public MetaActant() {
        super();
        com.pullenti.ner.Feature f = addFeature(com.pullenti.ner.semantic.ActantReferent.ATTR_ROLE, "Роль", 0, 1);
        f.addValue(com.pullenti.ner.semantic.ActantRole.AGENT.toString(), "Агенс", null, null);
        f.addValue(com.pullenti.ner.semantic.ActantRole.PATIENT.toString(), "Пациенс", null, null);
        f.addValue(com.pullenti.ner.semantic.ActantRole.SENTACTANT.toString(), "Сентактант", null, null);
        f.addValue(com.pullenti.ner.semantic.ActantRole.OBJECT.toString(), "Объект", null, null);
        ROLEFEATURE = f;
        addFeature(com.pullenti.ner.semantic.ActantReferent.ATTR_REF, "Ссылка", 1, 0);
        addFeature(com.pullenti.ner.semantic.ActantReferent.ATTR_VALUE, "Значение", 0, 1);
        addFeature(com.pullenti.ner.semantic.ActantReferent.ATTR_PROP, "Свойство", 0, 0);
        addFeature(com.pullenti.ner.semantic.ActantReferent.ATTR_PREPOSITION, "Предлог", 0, 1);
        addFeature(com.pullenti.ner.semantic.ActantReferent.ATTR_COUNT, "Количество", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.semantic.ActantReferent.OBJ_TYPENAME;
    }


    public static com.pullenti.ner.Feature ROLEFEATURE;

    @Override
    public String getCaption() {
        return "Актант предиката";
    }


    public static String IMAGEID = "actant";

    public static String IMAGEAGENTID = "actant_agent";

    public static String IMAGEPATIENTID = "actant_patient";

    public static String IMAGESENTACTANTID = "actant_sent";

    public static String IMAGETIMEID = "actant_time";

    public static String IMAGEOBJID = "actant_obj";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.semantic.ActantReferent sy = (com.pullenti.ner.semantic.ActantReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.semantic.ActantReferent.class);
        if (sy != null) {
            com.pullenti.ner.semantic.ActantRole r = sy.getRole();
            if (r == com.pullenti.ner.semantic.ActantRole.AGENT) 
                return IMAGEAGENTID;
            if (r == com.pullenti.ner.semantic.ActantRole.PATIENT) 
                return IMAGEPATIENTID;
            if (r == com.pullenti.ner.semantic.ActantRole.SENTACTANT) 
                return IMAGESENTACTANTID;
            if (r == com.pullenti.ner.semantic.ActantRole.OBJECT) 
                return IMAGEOBJID;
        }
        return IMAGEID;
    }

    public static MetaActant globalMeta = new MetaActant();
}
