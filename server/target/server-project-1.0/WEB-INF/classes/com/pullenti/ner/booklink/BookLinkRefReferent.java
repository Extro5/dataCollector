/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.booklink;

/**
 * Ссылка на внешний литературный источник (статью, книгу и пр.)
 */
public class BookLinkRefReferent extends com.pullenti.ner.Referent {

    public static final String OBJ_TYPENAME = "BOOKLINKREF";

    public static final String ATTR_BOOK = "BOOK";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_PAGES = "PAGES";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_MISC = "MISC";

    public BookLinkRefReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.booklink.internal.MetaBookLinkRef.globalMeta);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getNumber() != null) 
            res.append("[").append(getNumber()).append("] ");
        if (getPages() != null) 
            res.append((lang.isEn() ? "pages" : "стр.")).append(" ").append(getPages()).append("; ");
        com.pullenti.ner.Referent _book = getBook();
        if (_book == null) 
            res.append("?");
        else 
            res.append(_book.toString(shortVariant, lang, lev));
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_BOOK), com.pullenti.ner.Referent.class);
    }


    /**
     * Тип ссылки
     */
    public BookLinkRefType getTyp() {
        String val = getStringValue(ATTR_TYPE);
        if (val == null) 
            return BookLinkRefType.UNDEFINED;
        try {
            return BookLinkRefType.of(val);
        } catch(Exception ex397) {
        }
        return BookLinkRefType.UNDEFINED;
    }

    /**
     * Тип ссылки
     */
    public BookLinkRefType setTyp(BookLinkRefType value) {
        addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    /**
     * Собственно ссылка вовне на источник - BookLinkReferent или DecreeReferent
     */
    public com.pullenti.ner.Referent getBook() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_BOOK), com.pullenti.ner.Referent.class);
    }

    /**
     * Собственно ссылка вовне на источник - BookLinkReferent или DecreeReferent
     */
    public com.pullenti.ner.Referent setBook(com.pullenti.ner.Referent value) {
        addSlot(ATTR_BOOK, value, true, 0);
        return value;
    }


    /**
     * Порядковый номер в списке
     */
    public String getNumber() {
        return getStringValue(ATTR_NUMBER);
    }

    /**
     * Порядковый номер в списке
     */
    public String setNumber(String value) {
        String num = value;
        if (num != null && num.indexOf('-') > 0) 
            num = num.replace(" - ", "-");
        addSlot(ATTR_NUMBER, num, true, 0);
        return value;
    }


    /**
     * Ссылка на страницу или диапазон страниц
     */
    public String getPages() {
        return getStringValue(ATTR_PAGES);
    }

    /**
     * Ссылка на страницу или диапазон страниц
     */
    public String setPages(String value) {
        addSlot(ATTR_PAGES, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        BookLinkRefReferent r = (BookLinkRefReferent)com.pullenti.n2j.Utils.cast(obj, BookLinkRefReferent.class);
        if (r == null) 
            return false;
        if (getBook() != r.getBook()) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(getNumber(), r.getNumber())) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(getPages(), r.getPages())) 
            return false;
        if (((getTyp() == BookLinkRefType.INLINE)) != ((r.getTyp() == BookLinkRefType.INLINE))) 
            return false;
        return true;
    }

    /**
     * Возвращает разницу номеров r2 - r1, иначе null, если номеров нет
     * @param r1 
     * @param r2 
     * @return 
     */
    public static Integer getNumberDiff(com.pullenti.ner.Referent r1, com.pullenti.ner.Referent r2) {
        String num1 = r1.getStringValue(ATTR_NUMBER);
        String num2 = r2.getStringValue(ATTR_NUMBER);
        if (num1 == null || num2 == null) 
            return null;
        int n1;
        int n2;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg398 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres399 = com.pullenti.n2j.Utils.parseInteger(num1, inoutarg398);
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg400 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres401 = com.pullenti.n2j.Utils.parseInteger(num2, inoutarg400);
        n1 = (inoutarg398.value != null ? inoutarg398.value : 0);
        n2 = (inoutarg400.value != null ? inoutarg400.value : 0);
        if (!inoutres399 || !inoutres401) 
            return null;
        return n2 - n1;
    }

    public static BookLinkRefReferent _new389(com.pullenti.ner.Referent _arg1) {
        BookLinkRefReferent res = new BookLinkRefReferent();
        res.setBook(_arg1);
        return res;
    }
}
