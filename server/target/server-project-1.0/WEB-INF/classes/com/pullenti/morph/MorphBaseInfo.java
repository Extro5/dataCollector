/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Базовая часть морфологической информации
 */
public class MorphBaseInfo {

    public MorphBaseInfo() {
    }

    public MorphBaseInfo(MorphBaseInfo bi) {
        if (bi != null) 
            bi.copyTo(this);
    }

    /**
     * Часть речи
     */
    public MorphClass _getClass() {
        return m_Cla;
    }

    /**
     * Часть речи
     */
    public MorphClass _setClass(MorphClass value) {
        m_Cla = value;
        return value;
    }


    private MorphClass m_Cla = new MorphClass(null);

    private MorphGender _gender = MorphGender.UNDEFINED;

    /**
     * Род
     */
    public MorphGender getGender() {
        return _gender;
    }

    /**
     * Род
     */
    public MorphGender setGender(MorphGender value) {
        _gender = value;
        return _gender;
    }


    private MorphNumber _number = MorphNumber.UNDEFINED;

    /**
     * Число
     */
    public MorphNumber getNumber() {
        return _number;
    }

    /**
     * Число
     */
    public MorphNumber setNumber(MorphNumber value) {
        _number = value;
        return _number;
    }


    /**
     * Падеж
     */
    public MorphCase getCase() {
        return m_Cas;
    }

    /**
     * Падеж
     */
    public MorphCase setCase(MorphCase value) {
        m_Cas = value;
        return value;
    }


    private MorphCase m_Cas = new MorphCase(null);

    /**
     * Язык
     */
    public MorphLang getLanguage() {
        return m_Lang;
    }

    /**
     * Язык
     */
    public MorphLang setLanguage(MorphLang value) {
        m_Lang = value;
        return value;
    }


    private MorphLang m_Lang = new MorphLang(null);

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (!_getClass().isUndefined()) 
            res.append(_getClass().toString()).append(" ");
        if (getNumber() != MorphNumber.UNDEFINED) {
            if (getNumber() == MorphNumber.SINGULAR) 
                res.append("ед.ч. ");
            else if (getNumber() == MorphNumber.PLURAL) 
                res.append("мн.ч. ");
            else 
                res.append("ед.мн.ч. ");
        }
        if (getGender() != MorphGender.UNDEFINED) {
            if (getGender() == MorphGender.MASCULINE) 
                res.append("муж.р. ");
            else if (getGender() == MorphGender.NEUTER) 
                res.append("ср.р. ");
            else if (getGender() == MorphGender.FEMINIE) 
                res.append("жен.р. ");
            else if ((getGender().value()) == (((MorphGender.MASCULINE.value()) | (MorphGender.NEUTER.value())))) 
                res.append("муж.ср.р. ");
            else if ((getGender().value()) == (((MorphGender.FEMINIE.value()) | (MorphGender.NEUTER.value())))) 
                res.append("жен.ср.р. ");
            else if ((getGender().value()) == 7) 
                res.append("муж.жен.ср.р. ");
            else if ((getGender().value()) == (((MorphGender.FEMINIE.value()) | (MorphGender.MASCULINE.value())))) 
                res.append("муж.жен.р. ");
        }
        if (!getCase().isUndefined()) 
            res.append(getCase().toString()).append(" ");
        if (!getLanguage().isUndefined() && MorphLang.ooNoteq(getLanguage(), MorphLang.RU)) 
            res.append(getLanguage().toString()).append(" ");
        return com.pullenti.n2j.Utils.trimEnd(res.toString());
    }

    public void copyTo(MorphBaseInfo dst) {
        dst._setClass(new MorphClass(_getClass()));
        dst.setGender(getGender());
        dst.setNumber(getNumber());
        dst.setCase(new MorphCase(getCase()));
        dst.setLanguage(new MorphLang(getLanguage()));
    }

    public boolean containsAttr(String attrValue, MorphClass cla) {
        MorphWordForm wf = (MorphWordForm)com.pullenti.n2j.Utils.cast(this, MorphWordForm.class);
        if (wf == null) 
            return false;
        if (wf.misc != null && wf.misc.getAttrs() != null) 
            return wf.misc.getAttrs().contains(attrValue);
        return false;
    }

    public Object clone() {
        MorphBaseInfo res = new MorphBaseInfo();
        copyTo(res);
        return res;
    }

    public boolean checkAccord(MorphBaseInfo v, boolean ignoreGender) {
        if (MorphLang.ooNoteq(v.getLanguage(), getLanguage())) {
            if (MorphLang.ooEq(v.getLanguage(), MorphLang.UNKNOWN) && MorphLang.ooEq(getLanguage(), MorphLang.UNKNOWN)) 
                return false;
        }
        if ((((v.getNumber().value()) & (getNumber().value()))) == (MorphNumber.UNDEFINED.value())) {
            if (v.getNumber() != MorphNumber.UNDEFINED && getNumber() != MorphNumber.UNDEFINED) {
                if (v.getNumber() == MorphNumber.SINGULAR && v.getCase().isGenitive()) {
                    if (getNumber() == MorphNumber.PLURAL && getCase().isGenitive()) {
                        if ((((v.getGender().value()) & (MorphGender.MASCULINE.value()))) == (MorphGender.MASCULINE.value())) 
                            return true;
                    }
                }
                return false;
            }
        }
        if (!ignoreGender) {
            if ((((v.getGender().value()) & (getGender().value()))) == (MorphGender.UNDEFINED.value())) {
                if (v.getGender() != MorphGender.UNDEFINED && getGender() != MorphGender.UNDEFINED) 
                    return false;
            }
        }
        if (((MorphCase.ooBitand(v.getCase(), getCase()))).isUndefined()) {
            if (!v.getCase().isUndefined() && !getCase().isUndefined()) 
                return false;
        }
        return true;
    }

    public static MorphBaseInfo _new210(MorphClass _arg1, MorphNumber _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setNumber(_arg2);
        return res;
    }
    public static MorphBaseInfo _new211(MorphClass _arg1, MorphGender _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        return res;
    }
    public static MorphBaseInfo _new238(MorphCase _arg1, MorphClass _arg2, MorphNumber _arg3, MorphGender _arg4) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res._setClass(_arg2);
        res.setNumber(_arg3);
        res.setGender(_arg4);
        return res;
    }
    public static MorphBaseInfo _new486(MorphClass _arg1, MorphGender _arg2, MorphNumber _arg3, MorphLang _arg4) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        res.setNumber(_arg3);
        res.setLanguage(_arg4);
        return res;
    }
    public static MorphBaseInfo _new487(MorphGender _arg1, MorphCase _arg2, MorphNumber _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        res.setCase(_arg2);
        res.setNumber(_arg3);
        return res;
    }
    public static MorphBaseInfo _new488(MorphCase _arg1, MorphLang _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setLanguage(_arg2);
        return res;
    }
    public static MorphBaseInfo _new566(MorphClass _arg1) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        return res;
    }
    public static MorphBaseInfo _new1443(MorphClass _arg1, MorphGender _arg2, MorphNumber _arg3, MorphCase _arg4, MorphLang _arg5) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        res.setNumber(_arg3);
        res.setCase(_arg4);
        res.setLanguage(_arg5);
        return res;
    }
    public static MorphBaseInfo _new1509(MorphCase _arg1, MorphClass _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res._setClass(_arg2);
        return res;
    }
    public static MorphBaseInfo _new1658(MorphCase _arg1, MorphGender _arg2, MorphNumber _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setGender(_arg2);
        res.setNumber(_arg3);
        return res;
    }
    public static MorphBaseInfo _new2176(MorphClass _arg1, MorphGender _arg2, MorphLang _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        res.setLanguage(_arg3);
        return res;
    }
    public static MorphBaseInfo _new2220(MorphCase _arg1) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        return res;
    }
    public static MorphBaseInfo _new2278(MorphCase _arg1, MorphGender _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setGender(_arg2);
        return res;
    }
    public static MorphBaseInfo _new2292(MorphGender _arg1, MorphCase _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        res.setCase(_arg2);
        return res;
    }
    public static MorphBaseInfo _new2314(MorphGender _arg1) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        return res;
    }
    public static MorphBaseInfo _new2360(MorphCase _arg1, MorphGender _arg2, MorphClass _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setGender(_arg2);
        res._setClass(_arg3);
        return res;
    }
    public static MorphBaseInfo _new2395(MorphNumber _arg1, MorphLang _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setNumber(_arg1);
        res.setLanguage(_arg2);
        return res;
    }
    public static MorphBaseInfo _new2398(MorphGender _arg1, MorphNumber _arg2, MorphLang _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        res.setNumber(_arg2);
        res.setLanguage(_arg3);
        return res;
    }
    public static MorphBaseInfo _new2400(MorphNumber _arg1, MorphGender _arg2, MorphLang _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setNumber(_arg1);
        res.setGender(_arg2);
        res.setLanguage(_arg3);
        return res;
    }
    public static MorphBaseInfo _new2496(MorphNumber _arg1, MorphGender _arg2, MorphCase _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setNumber(_arg1);
        res.setGender(_arg2);
        res.setCase(_arg3);
        return res;
    }
}
