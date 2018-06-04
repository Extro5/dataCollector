/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharItemVar {

    public CharItemVar(CharItem it, CharItemToken cit) {
        item = it;
        if (cit != null) {
            addOccur(cit);
            age = cit.age;
            if (cit.isUniWithNext) 
                setNeedNext(true);
            if (cit.isUniWithPrev) 
                setNeedPrev(true);
            if (cit.typ == CharItemType.ATTR) {
                setAttr(true);
                if (cit.subtyp == CharItemAttrSubtype.EMOTION) 
                    setEmoAttr(true);
                else if (cit.subtyp == CharItemAttrSubtype.MISTER) 
                    setEmptyAttr(true);
                else if (cit.subtyp == CharItemAttrSubtype.PERSONAFTER) {
                    setBePersonAfter(true);
                    if (com.pullenti.n2j.Utils.stringsEq(cit.values.get(0), "ДРУГ") || com.pullenti.n2j.Utils.stringsEq(cit.values.get(0), "ПРИЯТЕЛЬ") || com.pullenti.n2j.Utils.stringsEq(cit.values.get(0), "БРАТ")) 
                        setEmptyAttr(true);
                }
                if (cit.charTyp == com.pullenti.ner.literature.CharacterType.MAN) 
                    setMan(true);
                else if (cit.charTyp == com.pullenti.ner.literature.CharacterType.ANIMAL) 
                    setAnimal(true);
                else if (cit.charTyp == com.pullenti.ner.literature.CharacterType.MYTHIC) 
                    setMythical(true);
            }
            if (cit.canBeFirstName) 
                setBeFirstName(true);
            if (cit.canBeLastName) 
                setBeLastName(true);
            if (cit.canBeMiddleName) 
                setBeMiddleName(true);
            if (cit.isAfterByName) 
                setAfterByName(true);
            if (cit.subtyp == CharItemAttrSubtype.NOUNINDICT) 
                setNounInDict(true);
            if (cit.anaforRef != null) 
                anaforRef = cit.anaforRef;
            if (cit.persProp != null) 
                persProp = cit.persProp;
        }
    }

    public CharItem item;

    public OccuresContainer occures = new OccuresContainer();

    public void addOccur(CharItemToken it) {
        occures.addOccur(it);
        m_Gender1 = com.pullenti.morph.MorphGender.UNDEFINED;
    }

    public void addOccures(CharItemVar civ) {
        if (CharItemVar.isEquals(civ, this)) 
            return;
        for(CharItemToken o : civ.occures.getOccurs()) {
            occures.addOccur(o);
        }
        m_Gender1 = com.pullenti.morph.MorphGender.UNDEFINED;
    }

    public com.pullenti.morph.MorphGender getGender() {
        if (m_Gender != com.pullenti.morph.MorphGender.UNDEFINED) 
            return m_Gender;
        if (m_Gender1 != com.pullenti.morph.MorphGender.UNDEFINED) 
            return m_Gender1;
        int male = 0;
        int female = 0;
        int neutr = 0;
        for(CharItemToken o : occures.getOccurs()) {
            com.pullenti.morph.MorphGender g = o.gender;
            if (g == com.pullenti.morph.MorphGender.UNDEFINED) {
                for(com.pullenti.morph.MorphBaseInfo it : o.getMorph().getItems()) {
                    if ((it instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).isInDictionary() && it.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                        g = com.pullenti.morph.MorphGender.of((g.value()) | (it.getGender().value()));
                }
            }
            if (g == com.pullenti.morph.MorphGender.UNDEFINED) 
                g = o.getMorph().getGender();
            if ((((g.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                male++;
            if ((((g.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                female++;
            if ((((g.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                neutr++;
        }
        if (male > ((female * 2)) && male > ((neutr * 2))) 
            m_Gender1 = com.pullenti.morph.MorphGender.MASCULINE;
        else if (female > ((male * 2)) && female > ((neutr * 2))) 
            m_Gender1 = com.pullenti.morph.MorphGender.FEMINIE;
        else if (neutr > ((male * 2)) && neutr > ((female * 2))) 
            m_Gender1 = com.pullenti.morph.MorphGender.NEUTER;
        return m_Gender1;
    }

    public com.pullenti.morph.MorphGender setGender(com.pullenti.morph.MorphGender value) {
        m_Gender = value;
        return value;
    }


    public CharacterAge age = CharacterAge.UNDEFINED;

    public com.pullenti.ner.person.PersonPropertyReferent persProp;

    public com.pullenti.ner.Token anaforRef;

    /**
     * Это тот персонах, на который ссылается атрибут!!!
     */
    public CharacterEx getRefCharacter() {
        if (m_RefCharacter != null && m_RefCharacter.real != null) 
            return m_RefCharacter.real;
        return m_RefCharacter;
    }

    /**
     * Это тот персонах, на который ссылается атрибут!!!
     */
    public CharacterEx setRefCharacter(CharacterEx value) {
        if (item.values.contains("НАЧАЛЬНИК")) {
        }
        m_RefCharacter = value;
        return value;
    }


    private CharacterEx m_RefCharacter;

    private com.pullenti.morph.MorphGender m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;

    private com.pullenti.morph.MorphGender m_Gender1 = com.pullenti.morph.MorphGender.UNDEFINED;

    public short m_Value;

    private boolean getValue(int i) {
        return ((((((int)m_Value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            m_Value |= ((short)((1 << i)));
        else 
            m_Value &= ((short)(~((1 << i))));
    }

    /**
     * Это атрибут
     */
    public boolean isAttr() {
        return getValue(0);
    }

    /**
     * Это атрибут
     */
    public boolean setAttr(boolean value) {
        setValue(0, value);
        return value;
    }


    /**
     * Значит обязательно далее должен быть другой элемент (например, де ...)
     */
    public boolean isNeedNext() {
        return getValue(1);
    }

    /**
     * Значит обязательно далее должен быть другой элемент (например, де ...)
     */
    public boolean setNeedNext(boolean value) {
        setValue(1, value);
        return value;
    }


    /**
     * Обязательно перед должен быть элемент
     */
    public boolean isNeedPrev() {
        return getValue(2);
    }

    /**
     * Обязательно перед должен быть элемент
     */
    public boolean setNeedPrev(boolean value) {
        setValue(2, value);
        return value;
    }


    /**
     * Позитивная окраска атрибуда (сволочь, подлец, молодец)
     */
    public boolean isEmoAttr() {
        return getValue(3);
    }

    /**
     * Позитивная окраска атрибуда (сволочь, подлец, молодец)
     */
    public boolean setEmoAttr(boolean value) {
        setValue(3, value);
        return value;
    }


    /**
     * Это явно животное
     */
    public boolean isAnimal() {
        return getValue(4);
    }

    /**
     * Это явно животное
     */
    public boolean setAnimal(boolean value) {
        setValue(4, value);
        return value;
    }


    /**
     * За словом может быть персона в родительном падеже (слуга Хозяина, отец Ивана ...)
     */
    public boolean isCanBePersonAfter() {
        return getValue(5);
    }

    /**
     * За словом может быть персона в родительном падеже (слуга Хозяина, отец Ивана ...)
     */
    public boolean setBePersonAfter(boolean value) {
        setValue(5, value);
        return value;
    }


    /**
     * Пустой атрибут (типа: господин, мистер)
     */
    public boolean isEmptyAttr() {
        return getValue(6);
    }

    /**
     * Пустой атрибут (типа: господин, мистер)
     */
    public boolean setEmptyAttr(boolean value) {
        setValue(6, value);
        return value;
    }


    /**
     * Это явно человек
     */
    public boolean isMan() {
        return getValue(7);
    }

    /**
     * Это явно человек
     */
    public boolean setMan(boolean value) {
        setValue(7, value);
        return value;
    }


    /**
     * Мифологическое существо
     */
    public boolean isMythical() {
        return getValue(8);
    }

    /**
     * Мифологическое существо
     */
    public boolean setMythical(boolean value) {
        setValue(8, value);
        return value;
    }


    public boolean isCanBeFirstName() {
        return getValue(9);
    }

    public boolean setBeFirstName(boolean value) {
        setValue(9, value);
        return value;
    }


    public boolean isCanBeLastName() {
        return getValue(10);
    }

    public boolean setBeLastName(boolean value) {
        setValue(10, value);
        return value;
    }


    public boolean isCanBeMiddleName() {
        return getValue(11);
    }

    public boolean setBeMiddleName(boolean value) {
        setValue(11, value);
        return value;
    }


    public boolean isNounInDict() {
        return getValue(12);
    }

    public boolean setNounInDict(boolean value) {
        setValue(12, value);
        return value;
    }


    public boolean isAfterByName() {
        return getValue(13);
    }

    public boolean setAfterByName(boolean value) {
        setValue(13, value);
        return value;
    }


    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(item.toString());
        if (age != CharacterAge.UNDEFINED) 
            tmp.append(" Age:").append(age.toString());
        if (m_Value != ((short)0)) {
            tmp.append(" - ");
            if (isAttr()) 
                tmp.append("атриб.");
            if (isAnimal()) 
                tmp.append("животн.");
            if (isMan()) 
                tmp.append("челов.");
            if (isMythical()) 
                tmp.append("мифич.");
            if (isEmoAttr()) 
                tmp.append("эмоц.");
            if (isEmptyAttr()) 
                tmp.append("пуст.");
            if (isNeedNext()) 
                tmp.append("должен_продолж.");
            if (isNeedPrev()) 
                tmp.append("должен_предшест.");
            if (isCanBePersonAfter()) 
                tmp.append("персона_за_родит.");
            if (isCanBeFirstName()) 
                tmp.append("имя.");
            if (isCanBeLastName()) 
                tmp.append("фамил.");
            if (isCanBeMiddleName()) 
                tmp.append("отчеств.");
            if (isNounInDict()) 
                tmp.append("в_словар.");
            if (isAfterByName()) 
                tmp.append("после_имени.");
        }
        if (persProp != null) 
            tmp.append(" Prop:'").append(persProp.toString()).append("'");
        if (anaforRef != null) 
            tmp.append(" Anafor:").append(anaforRef.toString());
        if (getRefCharacter() != null) 
            tmp.append(" CharRef: ").append(getRefCharacter().toString(true, new com.pullenti.morph.MorphLang(null), 0));
        return tmp.toString();
    }

    public static boolean isEquals(CharItemVar v1, CharItemVar v2) {
        if (v1 == null || v2 == null) 
            return v1 == v2;
        if (v1.item != v2.item) {
            CharItem i1 = (CharItem)com.pullenti.n2j.Utils.notnull(v1.item.fullVariant, v1.item);
            CharItem i2 = (CharItem)com.pullenti.n2j.Utils.notnull(v2.item.fullVariant, v2.item);
            if (i1 != i2) 
                return false;
        }
        if (v1.getRefCharacter() != null || v2.getRefCharacter() != null) {
            if (v1.getRefCharacter() != v2.getRefCharacter()) 
                return false;
        }
        if (v1.persProp != null && v2.persProp != null) {
            if (!v1.persProp.canBeEquals(v2.persProp, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                return false;
        }
        if ((v1.anaforRef instanceof com.pullenti.ner.TextToken) || (v2.anaforRef instanceof com.pullenti.ner.TextToken)) {
            if (!((v1.anaforRef instanceof com.pullenti.ner.TextToken)) || !((v2.anaforRef instanceof com.pullenti.ner.TextToken))) 
                return false;
            if (com.pullenti.n2j.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(v1.anaforRef, com.pullenti.ner.TextToken.class))).term, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(v2.anaforRef, com.pullenti.ner.TextToken.class))).term)) 
                return false;
        }
        return true;
    }

    public int getGenderCoef(int begin, int end, com.pullenti.morph.MorphGender gen) {
        CharItemToken o1 = occures.findOccure(begin, end);
        if (o1 == null) 
            return -1;
        String t1 = o1.getTermValue();
        if (t1 == null) 
            return -1;
        int nom1 = item.occures.canBeGender(t1, gen);
        return nom1;
    }

    public int getNominativeCoef(int begin, int end, com.pullenti.morph.MorphGender gen) {
        CharItemToken o1 = occures.findOccure(begin, end);
        if (o1 == null) 
            return -1;
        String t1 = o1.getTermValue();
        if (t1 == null) 
            return -1;
        int nom1 = item.occures.canBeNominative(t1, gen);
        return nom1;
    }

    public int getGenetiveCoef(int begin, int end, com.pullenti.morph.MorphGender gen) {
        CharItemToken o1 = occures.findOccure(begin, end);
        if (o1 == null) 
            return -1;
        String t1 = o1.getTermValue();
        if (t1 == null) 
            return -1;
        int gen1 = item.occures.canBeGenetive(t1, gen);
        return gen1;
    }
    public CharItemVar() {
    }
}
