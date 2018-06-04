/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core.internal;

/**
 * Токен - строка таблицы из текста
 */
public class TableRowToken extends com.pullenti.ner.MetaToken {

    public TableRowToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    /**
     * Ячейки строки таблицы
     */
    public java.util.ArrayList<TableCellToken> cells = new java.util.ArrayList< >();

    public boolean eor = false;

    public boolean lastRow = false;

    @Override
    public String toString() {
        return "ROW (" + cells.size() + " cells) : " + getSourceText();
    }
    public TableRowToken() {
        super();
    }
}
