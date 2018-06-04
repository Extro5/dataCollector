/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument;

/**
 * Классы частей НПА
 */
public class InstrumentKind implements Comparable<InstrumentKind> {

    /**
     * Неизвестно
     */
    public static final InstrumentKind UNDEFINED; // 0

    /**
     * Корневой документ
     */
    public static final InstrumentKind DOCUMENT; // 1

    /**
     * Внутренний документ (например, который утверждается)
     */
    public static final InstrumentKind INTERNALDOCUMENT; // 2

    /**
     * Заголовочная часть
     */
    public static final InstrumentKind HEAD; // 3

    /**
     * Элемент с основным содержимым
     */
    public static final InstrumentKind CONTENT; // 4

    /**
     * Хвостовая часть
     */
    public static final InstrumentKind TAIL; // 5

    /**
     * Приложение
     */
    public static final InstrumentKind APPENDIX; // 6

    /**
     * Часть документа (деление самого верхнего уровня)
     */
    public static final InstrumentKind DOCPART; // 7

    /**
     * Раздел
     */
    public static final InstrumentKind SECTION; // 8

    /**
     * Подраздел
     */
    public static final InstrumentKind SUBSECTION; // 9

    /**
     * Глава
     */
    public static final InstrumentKind CHAPTER; // 10

    /**
     * Параграф
     */
    public static final InstrumentKind PARAGRAPH; // 11

    /**
     * Подпараграф
     */
    public static final InstrumentKind SUBPARAGRAPH; // 12

    /**
     * Статья
     */
    public static final InstrumentKind CLAUSE; // 13

    /**
     * Часть статьи
     */
    public static final InstrumentKind CLAUSEPART; // 14

    /**
     * Пункт
     */
    public static final InstrumentKind ITEM; // 15

    /**
     * Подпункт
     */
    public static final InstrumentKind SUBITEM; // 16

    /**
     * Абзац
     */
    public static final InstrumentKind INDENTION; // 17

    /**
     * Элемент списка
     */
    public static final InstrumentKind LISTITEM; // 18

    /**
     * Заголовок списка (первый абзац перед перечислением)
     */
    public static final InstrumentKind LISTHEAD; // 19

    /**
     * Преамбула
     */
    public static final InstrumentKind PREAMBLE; // 20

    /**
     * Оглавление
     */
    public static final InstrumentKind INDEX; // 21

    /**
     * Примечание
     */
    public static final InstrumentKind NOTICE; // 22

    /**
     * Номер
     */
    public static final InstrumentKind NUMBER; // 23

    /**
     * Номер дела (для судебных документов)
     */
    public static final InstrumentKind CASENUMBER; // 24

    /**
     * Дополнительная информация (для судебных документов)
     */
    public static final InstrumentKind CASEINFO; // 25

    /**
     * Наименование
     */
    public static final InstrumentKind NAME; // 26

    /**
     * Тип
     */
    public static final InstrumentKind TYP; // 27

    /**
     * Подписант
     */
    public static final InstrumentKind SIGNER; // 28

    /**
     * Организация
     */
    public static final InstrumentKind ORGANIZATION; // 29

    /**
     * Место
     */
    public static final InstrumentKind PLACE; // 30

    /**
     * Дата-время
     */
    public static final InstrumentKind DATE; // 31

    /**
     * Контактные данные
     */
    public static final InstrumentKind CONTACT; // 32

    /**
     * Инициатор
     */
    public static final InstrumentKind INITIATOR; // 33

    /**
     * Директива
     */
    public static final InstrumentKind DIRECTIVE; // 34

    /**
     * Редакции
     */
    public static final InstrumentKind EDITIONS; // 35

    /**
     * Одобрен, утвержден
     */
    public static final InstrumentKind APPROVED; // 36

    /**
     * Ссылка на документ
     */
    public static final InstrumentKind DOCREFERENCE; // 37

    /**
     * Ключевое слово (типа Приложение и т.п.)
     */
    public static final InstrumentKind KEYWORD; // 38

    /**
     * Комментарий
     */
    public static final InstrumentKind COMMENT; // 39

    /**
     * Цитата
     */
    public static final InstrumentKind CITATION; // 40

    /**
     * Вопрос
     */
    public static final InstrumentKind QUESTION; // 41

    /**
     * Ответ
     */
    public static final InstrumentKind ANSWER; // 42

    /**
     * Таблица
     */
    public static final InstrumentKind TABLE; // 43

    /**
     * Строка таблицы
     */
    public static final InstrumentKind TABLEROW; // 44

    /**
     * Ячейка таблицы
     */
    public static final InstrumentKind TABLECELL; // 45

    /**
     * Для внутреннего использования
     */
    public static final InstrumentKind IGNORED; // 46

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private InstrumentKind(int val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Integer)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(InstrumentKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, InstrumentKind> mapIntToEnum; 
    private static java.util.HashMap<String, InstrumentKind> mapStringToEnum; 
    public static InstrumentKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        InstrumentKind item = new InstrumentKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static InstrumentKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new InstrumentKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        DOCUMENT = new InstrumentKind(1, "DOCUMENT");
        mapIntToEnum.put(DOCUMENT.value(), DOCUMENT);
        mapStringToEnum.put(DOCUMENT.m_str.toUpperCase(), DOCUMENT);
        INTERNALDOCUMENT = new InstrumentKind(2, "INTERNALDOCUMENT");
        mapIntToEnum.put(INTERNALDOCUMENT.value(), INTERNALDOCUMENT);
        mapStringToEnum.put(INTERNALDOCUMENT.m_str.toUpperCase(), INTERNALDOCUMENT);
        HEAD = new InstrumentKind(3, "HEAD");
        mapIntToEnum.put(HEAD.value(), HEAD);
        mapStringToEnum.put(HEAD.m_str.toUpperCase(), HEAD);
        CONTENT = new InstrumentKind(4, "CONTENT");
        mapIntToEnum.put(CONTENT.value(), CONTENT);
        mapStringToEnum.put(CONTENT.m_str.toUpperCase(), CONTENT);
        TAIL = new InstrumentKind(5, "TAIL");
        mapIntToEnum.put(TAIL.value(), TAIL);
        mapStringToEnum.put(TAIL.m_str.toUpperCase(), TAIL);
        APPENDIX = new InstrumentKind(6, "APPENDIX");
        mapIntToEnum.put(APPENDIX.value(), APPENDIX);
        mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
        DOCPART = new InstrumentKind(7, "DOCPART");
        mapIntToEnum.put(DOCPART.value(), DOCPART);
        mapStringToEnum.put(DOCPART.m_str.toUpperCase(), DOCPART);
        SECTION = new InstrumentKind(8, "SECTION");
        mapIntToEnum.put(SECTION.value(), SECTION);
        mapStringToEnum.put(SECTION.m_str.toUpperCase(), SECTION);
        SUBSECTION = new InstrumentKind(9, "SUBSECTION");
        mapIntToEnum.put(SUBSECTION.value(), SUBSECTION);
        mapStringToEnum.put(SUBSECTION.m_str.toUpperCase(), SUBSECTION);
        CHAPTER = new InstrumentKind(10, "CHAPTER");
        mapIntToEnum.put(CHAPTER.value(), CHAPTER);
        mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
        PARAGRAPH = new InstrumentKind(11, "PARAGRAPH");
        mapIntToEnum.put(PARAGRAPH.value(), PARAGRAPH);
        mapStringToEnum.put(PARAGRAPH.m_str.toUpperCase(), PARAGRAPH);
        SUBPARAGRAPH = new InstrumentKind(12, "SUBPARAGRAPH");
        mapIntToEnum.put(SUBPARAGRAPH.value(), SUBPARAGRAPH);
        mapStringToEnum.put(SUBPARAGRAPH.m_str.toUpperCase(), SUBPARAGRAPH);
        CLAUSE = new InstrumentKind(13, "CLAUSE");
        mapIntToEnum.put(CLAUSE.value(), CLAUSE);
        mapStringToEnum.put(CLAUSE.m_str.toUpperCase(), CLAUSE);
        CLAUSEPART = new InstrumentKind(14, "CLAUSEPART");
        mapIntToEnum.put(CLAUSEPART.value(), CLAUSEPART);
        mapStringToEnum.put(CLAUSEPART.m_str.toUpperCase(), CLAUSEPART);
        ITEM = new InstrumentKind(15, "ITEM");
        mapIntToEnum.put(ITEM.value(), ITEM);
        mapStringToEnum.put(ITEM.m_str.toUpperCase(), ITEM);
        SUBITEM = new InstrumentKind(16, "SUBITEM");
        mapIntToEnum.put(SUBITEM.value(), SUBITEM);
        mapStringToEnum.put(SUBITEM.m_str.toUpperCase(), SUBITEM);
        INDENTION = new InstrumentKind(17, "INDENTION");
        mapIntToEnum.put(INDENTION.value(), INDENTION);
        mapStringToEnum.put(INDENTION.m_str.toUpperCase(), INDENTION);
        LISTITEM = new InstrumentKind(18, "LISTITEM");
        mapIntToEnum.put(LISTITEM.value(), LISTITEM);
        mapStringToEnum.put(LISTITEM.m_str.toUpperCase(), LISTITEM);
        LISTHEAD = new InstrumentKind(19, "LISTHEAD");
        mapIntToEnum.put(LISTHEAD.value(), LISTHEAD);
        mapStringToEnum.put(LISTHEAD.m_str.toUpperCase(), LISTHEAD);
        PREAMBLE = new InstrumentKind(20, "PREAMBLE");
        mapIntToEnum.put(PREAMBLE.value(), PREAMBLE);
        mapStringToEnum.put(PREAMBLE.m_str.toUpperCase(), PREAMBLE);
        INDEX = new InstrumentKind(21, "INDEX");
        mapIntToEnum.put(INDEX.value(), INDEX);
        mapStringToEnum.put(INDEX.m_str.toUpperCase(), INDEX);
        NOTICE = new InstrumentKind(22, "NOTICE");
        mapIntToEnum.put(NOTICE.value(), NOTICE);
        mapStringToEnum.put(NOTICE.m_str.toUpperCase(), NOTICE);
        NUMBER = new InstrumentKind(23, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        CASENUMBER = new InstrumentKind(24, "CASENUMBER");
        mapIntToEnum.put(CASENUMBER.value(), CASENUMBER);
        mapStringToEnum.put(CASENUMBER.m_str.toUpperCase(), CASENUMBER);
        CASEINFO = new InstrumentKind(25, "CASEINFO");
        mapIntToEnum.put(CASEINFO.value(), CASEINFO);
        mapStringToEnum.put(CASEINFO.m_str.toUpperCase(), CASEINFO);
        NAME = new InstrumentKind(26, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        TYP = new InstrumentKind(27, "TYP");
        mapIntToEnum.put(TYP.value(), TYP);
        mapStringToEnum.put(TYP.m_str.toUpperCase(), TYP);
        SIGNER = new InstrumentKind(28, "SIGNER");
        mapIntToEnum.put(SIGNER.value(), SIGNER);
        mapStringToEnum.put(SIGNER.m_str.toUpperCase(), SIGNER);
        ORGANIZATION = new InstrumentKind(29, "ORGANIZATION");
        mapIntToEnum.put(ORGANIZATION.value(), ORGANIZATION);
        mapStringToEnum.put(ORGANIZATION.m_str.toUpperCase(), ORGANIZATION);
        PLACE = new InstrumentKind(30, "PLACE");
        mapIntToEnum.put(PLACE.value(), PLACE);
        mapStringToEnum.put(PLACE.m_str.toUpperCase(), PLACE);
        DATE = new InstrumentKind(31, "DATE");
        mapIntToEnum.put(DATE.value(), DATE);
        mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
        CONTACT = new InstrumentKind(32, "CONTACT");
        mapIntToEnum.put(CONTACT.value(), CONTACT);
        mapStringToEnum.put(CONTACT.m_str.toUpperCase(), CONTACT);
        INITIATOR = new InstrumentKind(33, "INITIATOR");
        mapIntToEnum.put(INITIATOR.value(), INITIATOR);
        mapStringToEnum.put(INITIATOR.m_str.toUpperCase(), INITIATOR);
        DIRECTIVE = new InstrumentKind(34, "DIRECTIVE");
        mapIntToEnum.put(DIRECTIVE.value(), DIRECTIVE);
        mapStringToEnum.put(DIRECTIVE.m_str.toUpperCase(), DIRECTIVE);
        EDITIONS = new InstrumentKind(35, "EDITIONS");
        mapIntToEnum.put(EDITIONS.value(), EDITIONS);
        mapStringToEnum.put(EDITIONS.m_str.toUpperCase(), EDITIONS);
        APPROVED = new InstrumentKind(36, "APPROVED");
        mapIntToEnum.put(APPROVED.value(), APPROVED);
        mapStringToEnum.put(APPROVED.m_str.toUpperCase(), APPROVED);
        DOCREFERENCE = new InstrumentKind(37, "DOCREFERENCE");
        mapIntToEnum.put(DOCREFERENCE.value(), DOCREFERENCE);
        mapStringToEnum.put(DOCREFERENCE.m_str.toUpperCase(), DOCREFERENCE);
        KEYWORD = new InstrumentKind(38, "KEYWORD");
        mapIntToEnum.put(KEYWORD.value(), KEYWORD);
        mapStringToEnum.put(KEYWORD.m_str.toUpperCase(), KEYWORD);
        COMMENT = new InstrumentKind(39, "COMMENT");
        mapIntToEnum.put(COMMENT.value(), COMMENT);
        mapStringToEnum.put(COMMENT.m_str.toUpperCase(), COMMENT);
        CITATION = new InstrumentKind(40, "CITATION");
        mapIntToEnum.put(CITATION.value(), CITATION);
        mapStringToEnum.put(CITATION.m_str.toUpperCase(), CITATION);
        QUESTION = new InstrumentKind(41, "QUESTION");
        mapIntToEnum.put(QUESTION.value(), QUESTION);
        mapStringToEnum.put(QUESTION.m_str.toUpperCase(), QUESTION);
        ANSWER = new InstrumentKind(42, "ANSWER");
        mapIntToEnum.put(ANSWER.value(), ANSWER);
        mapStringToEnum.put(ANSWER.m_str.toUpperCase(), ANSWER);
        TABLE = new InstrumentKind(43, "TABLE");
        mapIntToEnum.put(TABLE.value(), TABLE);
        mapStringToEnum.put(TABLE.m_str.toUpperCase(), TABLE);
        TABLEROW = new InstrumentKind(44, "TABLEROW");
        mapIntToEnum.put(TABLEROW.value(), TABLEROW);
        mapStringToEnum.put(TABLEROW.m_str.toUpperCase(), TABLEROW);
        TABLECELL = new InstrumentKind(45, "TABLECELL");
        mapIntToEnum.put(TABLECELL.value(), TABLECELL);
        mapStringToEnum.put(TABLECELL.m_str.toUpperCase(), TABLECELL);
        IGNORED = new InstrumentKind(46, "IGNORED");
        mapIntToEnum.put(IGNORED.value(), IGNORED);
        mapStringToEnum.put(IGNORED.m_str.toUpperCase(), IGNORED);
    }
}
