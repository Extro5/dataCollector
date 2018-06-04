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
public class MetaCharacter extends com.pullenti.ner.ReferentClass {

    public MetaCharacter() {
        super();
        com.pullenti.ner.Feature ff = addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_TYPE, "Тип", 0, 0);
        ff.addValue(com.pullenti.ner.literature.CharacterType.MAN.toString(), "Человек", null, null);
        ff.addValue(com.pullenti.ner.literature.CharacterType.ANIMAL.toString(), "Животное", null, null);
        ff.addValue(com.pullenti.ner.literature.CharacterType.MYTHIC.toString(), "Мифический", null, null);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, "Собственное имя", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME1, "Косвенное имя", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_FIRSTNAME, "Имя", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_LASTNAME, "Фамилия", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_MIDDLENAME, "Отчество", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, "Атрибут", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_EMO, "Эмоция", 0, 0);
        com.pullenti.ner.Feature f = addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_GENDER, "Пол", 0, 1);
        f.addValue(com.pullenti.ner.literature.CharacterGender.MASCULINE.toString().toLowerCase(), "Мужской", null, null);
        f.addValue(com.pullenti.ner.literature.CharacterGender.FEMINIE.toString().toLowerCase(), "Женский", null, null);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_ROLE, "Роль", 0, 0);
        addFeature(com.pullenti.ner.literature.CharacterReferent.ATTR_MISC, "Разное", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.literature.CharacterReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Литературный персонаж";
    }


    public static String IMAGEID = "character";

    public static String IMAGEMANID = "man";

    public static String IMAGEWOMANID = "woman";

    public static String IMAGEMANUNDEFID = "man_undef";

    public static String IMAGEANIMALID = "animal";

    public static String IMAGEMYTHICALID = "mythical";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.literature.CharacterReferent ch = (com.pullenti.ner.literature.CharacterReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.literature.CharacterReferent.class);
        if (ch != null) {
            com.pullenti.ner.literature.CharacterType ty = ch.getTyp();
            if (ty == com.pullenti.ner.literature.CharacterType.MAN) {
                if (ch.getGender() == com.pullenti.ner.literature.CharacterGender.MASCULINE) 
                    return IMAGEMANID;
                if (ch.getGender() == com.pullenti.ner.literature.CharacterGender.FEMINIE) 
                    return IMAGEWOMANID;
                return IMAGEMANUNDEFID;
            }
            if (ty == com.pullenti.ner.literature.CharacterType.ANIMAL) 
                return IMAGEANIMALID;
            if (ty == com.pullenti.ner.literature.CharacterType.MYTHIC) 
                return IMAGEMYTHICALID;
        }
        return IMAGEID;
    }

    public static MetaCharacter GLOBALMETA = new MetaCharacter();
}
