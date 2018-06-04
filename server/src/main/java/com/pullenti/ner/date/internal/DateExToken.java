/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.date.internal;

/**
 * Используется для нахождения в тексте абсолютных и относительных дат и диапазонов, 
 *  например, "в прошлом году", "за первый квартал этого года", "два дня назад и т.п."
 */
public class DateExToken extends com.pullenti.ner.MetaToken {

    public DateExToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    /**
     * Признак того, что это диапазон
     */
    public boolean isDiap = false;

    /**
     * Выделенные элементы (для диапазона начало периода)
     */
    public java.util.ArrayList<DateExItemToken> itemsFrom = new java.util.ArrayList< >();

    /**
     * Для диапазона конец периода
     */
    public java.util.ArrayList<DateExItemToken> itemsTo = new java.util.ArrayList< >();

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for(DateExItemToken it : itemsFrom) {
            tmp.append((isDiap ? "(fr)" : "")).append(it.toString()).append("; ");
        }
        for(DateExItemToken it : itemsTo) {
            tmp.append("(to)").append(it.toString()).append("; ");
        }
        return tmp.toString();
    }

    public boolean getDatesOld(java.time.LocalDateTime now, com.pullenti.n2j.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.n2j.Outargwrapper<java.time.LocalDateTime> to, boolean canBeFuture) {
        Integer yFrom = null;
        Integer yTo = null;
        boolean yFromDef = false;
        boolean yToDef = false;
        Integer qFrom = null;
        Integer qTo = null;
        Integer mFrom = null;
        Integer mTo = null;
        Integer dFrom = null;
        Integer dTo = null;
        Integer hFrom = null;
        Integer hTo = null;
        Integer minFrom = null;
        Integer minTo = null;
        for(int k = 0; k < 2; k++) {
            java.util.ArrayList<DateExItemToken> its = (k == 0 ? itemsFrom : itemsTo);
            int i = 0;
            int v = 0;
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.YEAR) {
                if (!its.get(i).isValueRelate) 
                    v = its.get(i).value;
                else 
                    v = now.getYear() + its.get(i).value;
                i++;
                if (k == 0) 
                    yFrom = v;
                else 
                    yTo = v;
                if (k == 0) 
                    yFromDef = true;
                else 
                    yToDef = true;
            }
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.QUARTAL) {
                if (!its.get(i).isValueRelate) 
                    v = its.get(i).value;
                else 
                    v = 1 + ((((now.getMonthValue() - 1)) / 4)) + its.get(i).value;
                i = its.size();
                if (k == 0) 
                    qFrom = v;
                else 
                    qTo = v;
            }
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.MONTH) {
                if (!its.get(i).isValueRelate) 
                    v = its.get(i).value;
                else 
                    v = now.getMonthValue() + its.get(i).value;
                i++;
                if (k == 0) 
                    mFrom = v;
                else 
                    mTo = v;
            }
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.DAY) {
                if (!its.get(i).isValueRelate) 
                    v = its.get(i).value;
                else 
                    v = now.getDayOfMonth() + its.get(i).value;
                i++;
                if (k == 0) 
                    dFrom = v;
                else 
                    dTo = v;
            }
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.DAYOFWEEK) {
                v = its.get(i).value;
                if (its.get(i).value < 0) {
                    v = -v;
                    for(java.time.LocalDateTime ddd = now; ; ddd = ddd.plusDays((long)-1)) {
                        if ((ddd.getDayOfWeek()) == java.time.DayOfWeek.MONDAY) {
                            ddd = ddd.plusDays((long)-7);
                            if (v > 1) 
                                ddd = ddd.plusDays((long)(v - 1));
                            if (k == 0) {
                                yFrom = ddd.getYear();
                                mFrom = ddd.getMonthValue();
                                dFrom = ddd.getDayOfMonth();
                            }
                            else {
                                yTo = ddd.getYear();
                                mTo = ddd.getMonthValue();
                                dTo = ddd.getDayOfMonth();
                            }
                            break;
                        }
                    }
                }
                else {
                    int dow = now.getDayOfWeek().getValue();
                    if (dow == 0) 
                        dow = 7;
                    java.time.LocalDateTime ddd = now;
                    if (v > dow) 
                        ddd = ddd.plusDays((long)(v - dow));
                    else {
                        if (canBeFuture) 
                            ddd = ddd.plusDays((long)7);
                        if (dow > v) 
                            ddd = ddd.plusDays((long)(v - dow));
                    }
                    if (k == 0) {
                        yFrom = ddd.getYear();
                        mFrom = ddd.getMonthValue();
                        dFrom = ddd.getDayOfMonth();
                    }
                    else {
                        yTo = ddd.getYear();
                        mTo = ddd.getMonthValue();
                        dTo = ddd.getDayOfMonth();
                    }
                }
                i++;
            }
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.HOUR) {
                if (!its.get(i).isValueRelate) 
                    v = its.get(i).value;
                else 
                    v = now.getHour() + its.get(i).value;
                i++;
                if (k == 0) 
                    hFrom = v;
                else 
                    hTo = v;
            }
            if ((i < its.size()) && its.get(i).typ == DateExItemTokenType.MINUTE) {
                if (!its.get(i).isValueRelate && its.size() > 1) 
                    v = its.get(i).value;
                else 
                    v = now.getMinute() + its.get(i).value;
                i++;
                if (k == 0) 
                    minFrom = v;
                else 
                    minTo = v;
            }
        }
        if (yFrom == null) {
            if (yTo == null) 
                yFrom = (yTo = now.getYear());
            else 
                yFrom = yTo;
        }
        else if (yTo == null) 
            yTo = yFrom;
        if (qFrom == null && dFrom == null) 
            qFrom = qTo;
        else if (qTo == null && dTo == null) 
            qTo = qFrom;
        if (qFrom != null) 
            mFrom = 1 + (((qFrom - 1)) * 4);
        if (qTo != null) 
            mTo = (qTo * 4) - 1;
        if (mFrom == null && (((dFrom != null || mFrom != null || hFrom != null) || minFrom != 0 || itemsFrom.size() == 0))) {
            if (mTo != null) 
                mFrom = mTo;
            else 
                mFrom = (mTo = now.getMonthValue());
        }
        if (mTo == null && (((dTo != null || mTo != null || hTo != null) || minTo != null || itemsTo.size() == 0))) {
            if (mFrom != null) 
                mTo = mFrom;
            else 
                mFrom = (mTo = now.getMonthValue());
        }
        if (mFrom != null) {
            while(mFrom > 12) {
                yFrom++;
                mFrom -= 12;
            }
            while(mFrom <= 0) {
                yFrom--;
                mFrom += 12;
            }
        }
        else 
            mFrom = 1;
        if (mTo != null) {
            while(mTo > 12) {
                yTo++;
                mTo -= 12;
            }
            while(mTo <= 0) {
                yTo--;
                mTo += 12;
            }
        }
        else 
            mTo = 12;
        if (dFrom == null && ((hFrom != null || minFrom != null || itemsFrom.size() == 0))) {
            if (dTo != null) 
                dFrom = dTo;
            else 
                dFrom = (dTo = now.getDayOfMonth());
        }
        if (dTo == null && ((hTo != null || minTo != null || itemsTo.size() == 0))) {
            if (dFrom != null) 
                dTo = dFrom;
            else 
                dFrom = (dTo = now.getDayOfMonth());
        }
        if (dFrom != null) {
            while(dFrom > com.pullenti.n2j.Utils.daysInMonth(yFrom, mFrom)) {
                dFrom -= com.pullenti.n2j.Utils.daysInMonth(yFrom, mFrom);
                mFrom++;
                if (mFrom > 12) {
                    mFrom = 1;
                    yFrom++;
                }
            }
            while(dFrom <= 0) {
                mFrom--;
                if (mFrom <= 0) {
                    mFrom = 12;
                    yFrom--;
                }
                dFrom += com.pullenti.n2j.Utils.daysInMonth(yFrom, mFrom);
            }
        }
        if (dTo != null) {
            while(dTo > com.pullenti.n2j.Utils.daysInMonth(yTo, mTo)) {
                dTo -= com.pullenti.n2j.Utils.daysInMonth(yTo, mTo);
                mTo++;
                if (mTo > 12) {
                    mTo = 1;
                    yTo++;
                }
            }
            while(dTo <= 0) {
                mTo--;
                if (mTo <= 0) {
                    mTo = 12;
                    yTo--;
                }
                dTo += com.pullenti.n2j.Utils.daysInMonth(yTo, mTo);
            }
        }
        else if (dFrom != null && mFrom == mTo && yFrom == yTo) 
            dTo = dFrom;
        else 
            dTo = com.pullenti.n2j.Utils.daysInMonth(yTo, mTo);
        if (dFrom == null) 
            dFrom = 1;
        try {
            from.value = java.time.LocalDateTime.of(yFrom, mFrom, dFrom, 0, 0);
            to.value = java.time.LocalDateTime.of(yTo, mTo, dTo, 0, 0);
        } catch(Exception ex) {
            from.value = java.time.LocalDateTime.MIN;
            to.value = java.time.LocalDateTime.MIN;
            return false;
        }
        if ((!yFromDef && itemsFrom.size() > 0 && java.time.LocalDateTime.of(from.value.toLocalDate(), java.time.LocalTime.of(0, 0)).compareTo(java.time.LocalDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.of(0, 0))) > 0) && !canBeFuture) {
            if (!yToDef && to.value.getYear() == from.value.getYear()) 
                to.value = to.value.plusYears((long)-1);
            from.value = from.value.plusYears((long)-1);
        }
        if (hFrom == null && hTo != null) 
            hFrom = hTo;
        else if (hTo == null && hFrom != null) 
            hTo = hFrom;
        if (minFrom == null && minTo != null) 
            minFrom = minTo;
        else if (minTo == null && minFrom != null) 
            minTo = minFrom;
        if (hFrom != null || minFrom != null) {
            if (hFrom == null) 
                hFrom = now.getHour();
            if (minFrom == null) 
                minFrom = 0;
            while(minFrom >= 60) {
                hFrom++;
                minFrom -= 60;
            }
            while(minFrom < 0) {
                hFrom--;
                minFrom += 60;
            }
            while(hFrom >= 24) {
                from.value = from.value.plusDays((long)1);
                hFrom -= 24;
            }
            while(hFrom < 0) {
                from.value = from.value.plusDays((long)-1);
                hFrom += 24;
            }
            from.value = from.value.plusHours((long)hFrom).plusMinutes((long)minFrom);
        }
        if (hTo != null || minTo != null) {
            if (hTo == null) 
                hTo = now.getHour();
            if (minTo == null) 
                minTo = 0;
            while(minTo >= 60) {
                hTo++;
                minTo -= 60;
            }
            while(minTo < 0) {
                hTo--;
                minTo += 60;
            }
            while(hTo >= 24) {
                to.value = to.value.plusDays((long)1);
                hTo -= 24;
            }
            while(hTo < 0) {
                to.value = to.value.plusDays((long)-1);
                hTo += 24;
            }
            to.value = to.value.plusHours((long)hTo).plusMinutes((long)minTo);
        }
        return true;
    }

    /**
     * Получить дату-время (одну)
     * @param now текущая дата (для относительных вычислений)
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - испрользуется 
     *  при неоднозначных случаях
     * @return дата-время или null
     */
    public java.time.LocalDateTime getDate(java.time.LocalDateTime now, int tense) {
        DateValues dvl = tryCreate_DateValues((itemsFrom.size() > 0 ? itemsFrom : itemsTo), now, tense);
        try {
            java.time.LocalDateTime dt = dvl.generateDate(now, false);
            dt = _correctHours(dt, (itemsFrom.size() > 0 ? itemsFrom : itemsTo), now);
            return dt;
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Получить диапазон (если не диапазон, то from = to)
     * @param now текущая дата-время
     * @param from начало диапазона
     * @param to конец диапазона
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - испрользуется 
     *  при неоднозначных случаях 
     *  Например, 7 сентября, а сейчас лето, то какой это год? При true - этот, при false - предыдущий
     * @return признак корректности
     */
    public boolean getDates(java.time.LocalDateTime now, com.pullenti.n2j.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.n2j.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        from.value = java.time.LocalDateTime.MIN;
        to.value = java.time.LocalDateTime.MIN;
        boolean hasHours = false;
        for(DateExItemToken it : itemsFrom) {
            if (it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE) 
                hasHours = true;
        }
        for(DateExItemToken it : itemsTo) {
            if (it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE) 
                hasHours = true;
        }
        java.util.ArrayList<DateExItemToken> li = new java.util.ArrayList< >();
        if (hasHours) {
            for(DateExItemToken it : itemsFrom) {
                if (it.typ != DateExItemTokenType.HOUR && it.typ != DateExItemTokenType.MINUTE) 
                    li.add(it);
            }
            for(DateExItemToken it : itemsTo) {
                if (it.typ != DateExItemTokenType.HOUR && it.typ != DateExItemTokenType.MINUTE) {
                    boolean exi = false;
                    for(DateExItemToken itt : li) {
                        if (itt.typ == it.typ) {
                            exi = true;
                            break;
                        }
                    }
                    if (!exi) 
                        li.add(it);
                }
            }
            java.util.Collections.sort(li);
            DateValues dvl = tryCreate_DateValues(li, now, tense);
            if (dvl == null) 
                return false;
            try {
                from.value = dvl.generateDate(now, false);
            } catch(Exception ex) {
                return false;
            }
            to.value = from.value;
            from.value = _correctHours(from.value, itemsFrom, now);
            to.value = _correctHours(to.value, itemsTo, now);
            return true;
        }
        if (itemsTo.size() == 0) {
            DateValues dvl = tryCreate_DateValues(itemsFrom, now, tense);
            try {
                from.value = dvl.generateDate(now, false);
            } catch(Exception ex) {
                return false;
            }
            try {
                to.value = dvl.generateDate(now, true);
            } catch(Exception ex) {
                to.value = from.value;
            }
            return true;
        }
        li.clear();
        for(DateExItemToken it : itemsFrom) {
            li.add(it);
        }
        for(DateExItemToken it : itemsTo) {
            boolean exi = false;
            for(DateExItemToken itt : li) {
                if (itt.typ == it.typ) {
                    exi = true;
                    break;
                }
            }
            if (!exi) 
                li.add(it);
        }
        java.util.Collections.sort(li);
        DateValues dvl1 = tryCreate_DateValues(li, now, tense);
        li.clear();
        for(DateExItemToken it : itemsTo) {
            li.add(it);
        }
        for(DateExItemToken it : itemsFrom) {
            boolean exi = false;
            for(DateExItemToken itt : li) {
                if (itt.typ == it.typ) {
                    exi = true;
                    break;
                }
            }
            if (!exi) 
                li.add(it);
        }
        java.util.Collections.sort(li);
        DateValues dvl2 = tryCreate_DateValues(li, now, tense);
        try {
            from.value = dvl1.generateDate(now, false);
        } catch(Exception ex) {
            return false;
        }
        try {
            to.value = dvl2.generateDate(now, true);
        } catch(Exception ex) {
            return false;
        }
        return true;
    }

    private java.time.LocalDateTime _correctHours(java.time.LocalDateTime dt, java.util.ArrayList<DateExItemToken> li, java.time.LocalDateTime now) {
        boolean hasHour = false;
        for(DateExItemToken it : li) {
            if (it.typ == DateExItemTokenType.HOUR) {
                hasHour = true;
                if (it.isValueRelate) 
                    dt = dt.plusHours((long)it.value);
                else if (it.value > 0 && (it.value < 24)) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), it.value, 0, 0);
            }
            else if (it.typ == DateExItemTokenType.MINUTE) {
                if (!hasHour) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), now.getHour(), 0, 0);
                if (it.isValueRelate) {
                    dt = dt.plusMinutes((long)it.value);
                    if (!hasHour) 
                        dt = dt.plusMinutes((long)now.getHour());
                }
                else if (it.value > 0 && (it.value < 60)) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), it.value, 0);
            }
        }
        return dt;
    }

    public static class DateValues {
    
        public int day1;
    
        public int day2;
    
        public int month1;
    
        public int month2;
    
        public int year;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            if (year > 0) 
                tmp.append("Year:").append(year);
            if (month1 > 0) {
                tmp.append(" Month:").append(month1);
                if (month2 > month1) 
                    tmp.append("..").append(month2);
            }
            if (day1 > 0) {
                tmp.append(" Day:").append(day1);
                if (day2 > day1) 
                    tmp.append("..").append(day2);
            }
            return tmp.toString().trim();
        }
    
        public java.time.LocalDateTime generateDate(java.time.LocalDateTime today, boolean endOfDiap) {
            int _year = year;
            if (_year == 0) 
                _year = today.getYear();
            int mon = month1;
            if (mon == 0) 
                mon = (endOfDiap ? 12 : 1);
            else if (endOfDiap && month2 > 0) 
                mon = month2;
            int day = day1;
            if (day == 0) 
                day = (endOfDiap ? 31 : 1);
            else if (day2 > 0) 
                day = day2;
            if (day > com.pullenti.n2j.Utils.daysInMonth(_year, mon)) 
                day = com.pullenti.n2j.Utils.daysInMonth(_year, mon);
            return java.time.LocalDateTime.of(_year, mon, day, 0, 0);
        }
    
        public static DateValues _new629(int _arg1, int _arg2, int _arg3) {
            DateValues res = new DateValues();
            res.year = _arg1;
            res.month1 = _arg2;
            res.day1 = _arg3;
            return res;
        }
        public DateValues() {
        }
    }


    /**
     * Выделить в тексте дату с указанной позиции
     * @param t 
     * @return 
     */
    public static DateExToken tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        DateExToken res = null;
        boolean toRegime = false;
        boolean fromRegime = false;
        com.pullenti.ner.Token t0 = null;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            com.pullenti.ner.date.DateRangeReferent drr = (com.pullenti.ner.date.DateRangeReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateRangeReferent.class);
            if (drr != null) {
                res = _new630(t, tt, true);
                com.pullenti.ner.date.DateReferent fr = drr.getDateFrom();
                if (fr != null) 
                    _addItems(fr, res.itemsFrom, tt);
                com.pullenti.ner.date.DateReferent to = drr.getDateTo();
                _addItems(to, res.itemsTo, tt);
                return res;
            }
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateReferent.class);
            if (dr != null) {
                if (res == null) 
                    res = new DateExToken(t, tt);
                if (toRegime) {
                    if (res.itemsTo.size() > 0) 
                        break;
                    _addItems(dr, res.itemsTo, tt);
                }
                else {
                    if (res.itemsFrom.size() > 0) 
                        break;
                    _addItems(dr, res.itemsFrom, tt);
                }
                continue;
            }
            if (tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ПО", null) || tt.isValue("ДО", null)) {
                    toRegime = true;
                    if (t0 == null) 
                        t0 = tt;
                }
                else if (tt.isValue("С", null) || tt.isValue("ОТ", null)) {
                    fromRegime = true;
                    if (t0 == null) 
                        t0 = tt;
                }
                continue;
            }
            DateExItemToken it = tryParse_DateExItemToken(tt, null);
            if (it == null) 
                break;
            if (it.getEndToken() == tt && ((it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE))) {
                if (tt.getPrevious() == null || !tt.getPrevious().getMorph()._getClass().isPreposition()) 
                    break;
            }
            if (res == null) 
                res = new DateExToken(t, tt);
            if (toRegime) 
                res.itemsTo.add(it);
            else 
                res.itemsFrom.add(it);
            tt = it.getEndToken();
            res.setEndToken(tt);
        }
        if (res != null) {
            if (t0 != null && res.getBeginToken().getPrevious() == t0) 
                res.setBeginToken(t0);
            res.isDiap = fromRegime || toRegime;
            java.util.Collections.sort(res.itemsFrom);
            java.util.Collections.sort(res.itemsTo);
        }
        return res;
    }

    private static void _addItems(com.pullenti.ner.date.DateReferent fr, java.util.ArrayList<DateExItemToken> res, com.pullenti.ner.Token tt) {
        if (fr.getYear() > 0) 
            res.add(DateExItemToken._new631(tt, tt, DateExItemTokenType.YEAR, fr.getYear()));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new632(tt, tt, DateExItemTokenType.YEAR, 0, true));
        if (fr.getMonth() > 0) 
            res.add(DateExItemToken._new631(tt, tt, DateExItemTokenType.MONTH, fr.getMonth()));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new632(tt, tt, DateExItemTokenType.MONTH, 0, true));
        if (fr.getDay() > 0) 
            res.add(DateExItemToken._new631(tt, tt, DateExItemTokenType.DAY, fr.getDay()));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new632(tt, tt, DateExItemTokenType.DAY, 0, true));
    }

    public static class DateExItemToken extends com.pullenti.ner.MetaToken implements Comparable<DateExItemToken> {
    
        public DateExItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
            super(begin, end, null);
        }
    
        public com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR;
    
        /**
         * Начало и конец диапазона, при совпадении значение точное
         */
        public int value;
    
        /**
         * Признак относительности значения (относительно текущей даты)
         */
        public boolean isValueRelate;
    
        /**
         * Признак того, что значение примерное (в начале года)
         */
        public boolean isValueNotstrict;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append(typ.toString()).append(" ");
            if (isValueNotstrict) 
                tmp.append("~");
            if (isValueRelate) 
                tmp.append((value < 0 ? "" : "+")).append(value);
            else 
                tmp.append(value);
            return tmp.toString();
        }
    
        @Override
        public int compareTo(DateExItemToken other) {
            if ((typ.value()) < (other.typ.value())) 
                return -1;
            if ((typ.value()) > (other.typ.value())) 
                return 1;
            return 0;
        }
    
        public static DateExItemToken _new631(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            return res;
        }
        public static DateExItemToken _new632(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4, boolean _arg5) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            res.isValueRelate = _arg5;
            return res;
        }
        public DateExItemToken() {
            super();
        }
    }


    public static DateValues tryCreate_DateValues(java.util.ArrayList<DateExItemToken> list, java.time.LocalDateTime today, int tense) {
        if (list == null || list.size() == 0) 
            return DateValues._new629(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        DateValues res = new DateValues();
        DateExItemToken it;
        int i = 0;
        boolean hasRel = false;
        if ((i < list.size()) && list.get(i).typ == DateExItemTokenType.YEAR) {
            it = list.get(i);
            if (!it.isValueRelate) 
                res.year = it.value;
            else {
                res.year = today.getYear() + it.value;
                hasRel = true;
            }
            i++;
        }
        if ((i < list.size()) && list.get(i).typ == DateExItemTokenType.QUARTAL) {
            it = list.get(i);
            int v = 0;
            if (!it.isValueRelate) {
                if (res.year == 0) {
                    int v0 = 1 + ((((today.getMonthValue() - 1)) / 4));
                    if (it.value > v0 && (tense < 0)) 
                        res.year = today.getYear() - 1;
                    else if ((it.value < v0) && tense > 0) 
                        res.year = today.getYear() + 1;
                    else 
                        res.year = today.getYear();
                }
                v = it.value;
            }
            else {
                if (res.year == 0) 
                    res.year = today.getYear();
                v = 1 + ((((today.getMonthValue() - 1)) / 4)) + it.value;
            }
            while(v > 4) {
                v -= 4;
                res.year++;
            }
            while(v <= 0) {
                v += 4;
                res.year--;
            }
            res.month1 = (((v - 1)) * 4) + 1;
            res.month2 = res.month1 + 3;
            return res;
        }
        if ((i < list.size()) && list.get(i).typ == DateExItemTokenType.MONTH) {
            it = list.get(i);
            if (!it.isValueRelate) {
                if (res.year == 0) {
                    if (it.value > today.getMonthValue() && (tense < 0)) 
                        res.year = today.getYear() - 1;
                    else if ((it.value < today.getMonthValue()) && tense > 0) 
                        res.year = today.getYear() + 1;
                    else 
                        res.year = today.getYear();
                }
                res.month1 = it.value;
            }
            else {
                hasRel = true;
                if (res.year == 0) 
                    res.year = today.getYear();
                int v = today.getMonthValue() + it.value;
                while(v > 12) {
                    v -= 12;
                    res.year++;
                }
                while(v <= 0) {
                    v += 12;
                    res.year--;
                }
                res.month1 = v;
            }
            i++;
        }
        if ((i < list.size()) && list.get(i).typ == DateExItemTokenType.DAY) {
            it = list.get(i);
            if (!it.isValueRelate) {
                res.day1 = it.value;
                if (res.month1 == 0) {
                    if (res.year == 0) 
                        res.year = today.getYear();
                    if (it.value > today.getDayOfMonth() && (tense < 0)) {
                        res.month1 = today.getMonthValue() - 1;
                        if (res.month1 <= 0) {
                            res.month1 = 12;
                            res.year--;
                        }
                    }
                    else if ((it.value < today.getDayOfMonth()) && tense > 0) {
                        res.month1 = today.getMonthValue() + 1;
                        if (res.month1 > 12) {
                            res.month1 = 1;
                            res.year++;
                        }
                    }
                    else 
                        res.month1 = today.getMonthValue();
                }
            }
            else {
                hasRel = true;
                if (res.year == 0) 
                    res.year = today.getYear();
                if (res.month1 == 0) 
                    res.month1 = today.getMonthValue();
                int v = today.getDayOfMonth() + it.value;
                while(v > com.pullenti.n2j.Utils.daysInMonth(res.year, res.month1)) {
                    v -= com.pullenti.n2j.Utils.daysInMonth(res.year, res.month1);
                    res.month1++;
                    if (res.month1 > 12) {
                        res.month1 = 1;
                        res.year++;
                    }
                }
                while(v <= 0) {
                    res.month1--;
                    if (res.month1 <= 0) {
                        res.month1 = 12;
                        res.year--;
                    }
                    v += com.pullenti.n2j.Utils.daysInMonth(res.year, res.month1);
                }
                res.day1 = v;
            }
            i++;
        }
        if ((i < list.size()) && list.get(i).typ == DateExItemTokenType.DAYOFWEEK) 
            it = list.get(i);
        return res;
    }

    public static DateExItemToken tryParse_DateExItemToken(com.pullenti.ner.Token t, java.util.ArrayList<DateExItemToken> prev) {
        if (t == null) 
            return null;
        if (t.isValue("ЗАВТРА", null)) 
            return DateExItemToken._new632(t, t, DateExItemTokenType.DAY, 1, true);
        if (t.isValue("ПОСЛЕЗАВТРА", null)) 
            return DateExItemToken._new632(t, t, DateExItemTokenType.DAY, 2, true);
        if (t.isValue("ВЧЕРА", null)) 
            return DateExItemToken._new632(t, t, DateExItemTokenType.DAY, -1, true);
        if (t.isValue("ПОЗАВЧЕРА", null)) 
            return DateExItemToken._new632(t, t, DateExItemTokenType.DAY, -2, true);
        if (t.isValue("ПОЛЧАСА", null)) 
            return DateExItemToken._new632(t, t, DateExItemTokenType.MINUTE, 30, true);
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0);
        if (npt == null) {
            if (t instanceof com.pullenti.ner.NumberToken) {
                DateExItemToken res0 = tryParse_DateExItemToken(t.getNext(), prev);
                if (res0 != null && res0.value == 1) {
                    res0.setBeginToken(t);
                    res0.value = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value;
                    if (t.getPrevious() != null && t.getPrevious().isValue("ЧЕРЕЗ", null)) 
                        res0.isValueRelate = true;
                    return res0;
                }
            }
            return null;
        }
        DateExItemTokenType ty = DateExItemTokenType.HOUR;
        int val = 0;
        if (npt.noun.isValue("ГОД", null)) 
            ty = DateExItemTokenType.YEAR;
        else if (npt.noun.isValue("КВАРТАЛ", null)) 
            ty = DateExItemTokenType.QUARTAL;
        else if (npt.noun.isValue("МЕСЯЦ", null)) 
            ty = DateExItemTokenType.MONTH;
        else if (npt.noun.isValue("ДЕНЬ", null)) 
            ty = DateExItemTokenType.DAY;
        else if (npt.noun.isValue("ЧАС", null)) 
            ty = DateExItemTokenType.HOUR;
        else if (npt.noun.isValue("МИНУТА", null)) 
            ty = DateExItemTokenType.MINUTE;
        else if (npt.noun.isValue("ПОНЕДЕЛЬНИК", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 1;
        }
        else if (npt.noun.isValue("ВТОРНИК", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 2;
        }
        else if (npt.noun.isValue("СРЕДА", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 3;
        }
        else if (npt.noun.isValue("ЧЕТВЕРГ", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 4;
        }
        else if (npt.noun.isValue("ПЯТНИЦА", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 5;
        }
        else if (npt.noun.isValue("СУББОТА", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 6;
        }
        else if (npt.noun.isValue("ВОСКРЕСЕНЬЕ", null) || npt.noun.isValue("ВОСКРЕСЕНИЕ", null)) {
            ty = DateExItemTokenType.DAYOFWEEK;
            val = 7;
        }
        else 
            return null;
        DateExItemToken res = DateExItemToken._new631(t, npt.getEndToken(), ty, val);
        boolean heg = false;
        for(com.pullenti.ner.MetaToken a : npt.adjectives) {
            if (a.isValue("СЛЕДУЮЩИЙ", null) || a.isValue("БУДУЩИЙ", null)) 
                res.isValueRelate = true;
            else if (a.isValue("ПРЕДЫДУЩИЙ", null) || a.isValue("ПРОШЛЫЙ", null)) {
                res.isValueRelate = true;
                heg = true;
            }
            else if (a.getBeginToken() == a.getEndToken() && (a.getBeginToken() instanceof com.pullenti.ner.NumberToken)) {
                if (res.typ != DateExItemTokenType.DAYOFWEEK) 
                    res.value = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(a.getBeginToken(), com.pullenti.ner.NumberToken.class))).value;
            }
            else if (a.isValue("ЭТОТ", null) || a.isValue("ТЕКУЩИЙ", null)) {
            }
            else if (a.isValue("БЛИЖАЙШИЙ", null) && res.typ == DateExItemTokenType.DAYOFWEEK) {
            }
            else 
                return null;
        }
        if (res.value == 0) 
            res.value = 1;
        if (heg) 
            res.value = -res.value;
        return res;
    }

    public static class DateExItemTokenType implements Comparable<DateExItemTokenType> {
    
        public static final DateExItemTokenType YEAR; // 0
    
        public static final DateExItemTokenType QUARTAL; // 1
    
        public static final DateExItemTokenType MONTH; // 2
    
        public static final DateExItemTokenType DAY; // 4
    
        /**
         * День недели
         */
        public static final DateExItemTokenType DAYOFWEEK; // 5
    
        public static final DateExItemTokenType HOUR; // 6
    
        public static final DateExItemTokenType MINUTE; // 7
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private DateExItemTokenType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(DateExItemTokenType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, DateExItemTokenType> mapIntToEnum; 
        private static java.util.HashMap<String, DateExItemTokenType> mapStringToEnum; 
        public static DateExItemTokenType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            DateExItemTokenType item = new DateExItemTokenType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static DateExItemTokenType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            YEAR = new DateExItemTokenType(0, "YEAR");
            mapIntToEnum.put(YEAR.value(), YEAR);
            mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
            QUARTAL = new DateExItemTokenType(1, "QUARTAL");
            mapIntToEnum.put(QUARTAL.value(), QUARTAL);
            mapStringToEnum.put(QUARTAL.m_str.toUpperCase(), QUARTAL);
            MONTH = new DateExItemTokenType(2, "MONTH");
            mapIntToEnum.put(MONTH.value(), MONTH);
            mapStringToEnum.put(MONTH.m_str.toUpperCase(), MONTH);
            DAY = new DateExItemTokenType(4, "DAY");
            mapIntToEnum.put(DAY.value(), DAY);
            mapStringToEnum.put(DAY.m_str.toUpperCase(), DAY);
            DAYOFWEEK = new DateExItemTokenType(5, "DAYOFWEEK");
            mapIntToEnum.put(DAYOFWEEK.value(), DAYOFWEEK);
            mapStringToEnum.put(DAYOFWEEK.m_str.toUpperCase(), DAYOFWEEK);
            HOUR = new DateExItemTokenType(6, "HOUR");
            mapIntToEnum.put(HOUR.value(), HOUR);
            mapStringToEnum.put(HOUR.m_str.toUpperCase(), HOUR);
            MINUTE = new DateExItemTokenType(7, "MINUTE");
            mapIntToEnum.put(MINUTE.value(), MINUTE);
            mapStringToEnum.put(MINUTE.m_str.toUpperCase(), MINUTE);
        }
    }


    public static DateExToken _new630(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        DateExToken res = new DateExToken(_arg1, _arg2);
        res.isDiap = _arg3;
        return res;
    }
    public DateExToken() {
        super();
    }
    public static DateExToken _globalInstance;
    static {
        _globalInstance = new DateExToken(); 
    }
}
