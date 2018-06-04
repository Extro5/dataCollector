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
public class MetaPeace extends com.pullenti.ner.ReferentClass {

    public MetaPeace() {
        super();
        kindFi = addFeature(com.pullenti.ner.literature.TextPeaceReferent.ATTR_KIND, "Класс", 1, 1);
        kindFi.addValue(com.pullenti.ner.literature.TextPeaceKind.TEXT.toString(), "Текст", null, null);
        kindFi.addValue(com.pullenti.ner.literature.TextPeaceKind.TITLE.toString(), "Заголовок", null, null);
        kindFi.addValue(com.pullenti.ner.literature.TextPeaceKind.HEAD.toString(), "Титул", null, null);
        kindFi.addValue(com.pullenti.ner.literature.TextPeaceKind.TAIL.toString(), "Хвост", null, null);
        typeFi = addFeature(com.pullenti.ner.literature.TextPeaceReferent.ATTR_TYPE, "Тип", 0, 1);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.BOOK.toString(), "Книга", null, null);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.VOLUME.toString(), "Том", null, null);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.PART.toString(), "Часть", null, null);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.CHAPTER.toString(), "Глава", null, null);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.INTRO.toString(), "Введение", null, null);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.CONCLUSION.toString(), "Заключение", null, null);
        typeFi.addValue(com.pullenti.ner.literature.TextPeaceType.REMARKS.toString(), "Примечания", null, null);
        addFeature(com.pullenti.ner.literature.TextPeaceReferent.ATTR_NAME, "Наименование", 0, 1);
        addFeature(com.pullenti.ner.literature.TextPeaceReferent.ATTR_NUMBER, "Атрибут", 0, 1);
    }

    public com.pullenti.ner.Feature kindFi;

    public com.pullenti.ner.Feature typeFi;

    @Override
    public String getName() {
        return com.pullenti.ner.literature.TextPeaceReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Фрагмент текста";
    }


    public static String IMAGEID = "textpeace";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.literature.TextPeaceReferent ch = (com.pullenti.ner.literature.TextPeaceReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.literature.TextPeaceReferent.class);
        if (ch != null) {
        }
        return IMAGEID;
    }

    public static MetaPeace GLOBALMETA = new MetaPeace();
}
