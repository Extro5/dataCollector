/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument.internal;

public class MetaInstrumentBlock extends com.pullenti.ner.ReferentClass {

    public MetaInstrumentBlock() {
        super();
        kindFeature = addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_KIND, "Класс", 0, 1);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.UNDEFINED.toString(), "Неизвестный фрагмент", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DOCUMENT.toString(), "Документ", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT.toString(), "Внутренний документ", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.APPENDIX.toString(), "Приложение", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CONTENT.toString(), "Содержимое", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.HEAD.toString(), "Заголовочная часть", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TAIL.toString(), "Хвостовая часть", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.NAME.toString(), "Название", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.NUMBER.toString(), "Номер", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CASENUMBER.toString(), "Номер дела", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CASEINFO.toString(), "Информация дела", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.EDITIONS.toString(), "Редакции", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.APPROVED.toString(), "Одобрен", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION.toString(), "Организация", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DOCPART.toString(), "Часть документа", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.PLACE.toString(), "Место", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SIGNER.toString(), "Подписант", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SUBITEM.toString(), "Подпункт", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INDENTION.toString(), "Абзац", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CHAPTER.toString(), "Глава", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.PARAGRAPH.toString(), "Параграф", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SUBPARAGRAPH.toString(), "Подпараграф", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.LISTHEAD.toString(), "Заголовок списка", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.LISTITEM.toString(), "Элемент списка", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.NOTICE.toString(), "Примечание", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TYP.toString(), "Тип", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SECTION.toString(), "Раздел", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SUBSECTION.toString(), "Подраздел", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CLAUSE.toString(), "Статья", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART.toString(), "Часть", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DATE.toString(), "Дата", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE.toString(), "Директива", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INDEX.toString(), "Оглавление", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE.toString(), "Ссылка на документ", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INITIATOR.toString(), "Инициатор", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.PREAMBLE.toString(), "Преамбула", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.ITEM.toString(), "Пункт", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.KEYWORD.toString(), "Ключевое слово", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.COMMENT.toString(), "Комментарий", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.QUESTION.toString(), "Вопрос", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CITATION.toString(), "Цитата", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CONTACT.toString(), "Контакт", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TABLE.toString(), "Таблица", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TABLEROW.toString(), "Строка таблицы", null, null);
        kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TABLECELL.toString(), "Ячейка таблицы", null, null);
        com.pullenti.ner.Feature fi2 = addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_KIND, "Класс (доп.)", 0, 1);
        for(int i = 0; i < kindFeature.innerValues.size(); i++) {
            fi2.addValue(kindFeature.innerValues.get(i), kindFeature.outerValues.get(i), null, null);
        }
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_CHILD, "Внутренний элемент", 0, 0).setShowAsParent(true);
        addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_NAME, "Наименование", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NUMBER, "Номер", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_MINNUMBER, "Минимальный номер", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_SUBNUMBER, "Подномер", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_SUB2NUMBER, "Подномер второй", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_SUB3NUMBER, "Подномер третий", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_VALUE, "Значение", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, "Ссылка на объект", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_EXPIRED, "Утратил силу", 0, 1);
    }

    public com.pullenti.ner.Feature kindFeature;

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentBlockReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Блок документа";
    }


    public static String DOCIMAGEID = "decree";

    public static String PARTIMAGEID = "part";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return PARTIMAGEID;
    }

    public static MetaInstrumentBlock GLOBALMETA = new MetaInstrumentBlock();
}
