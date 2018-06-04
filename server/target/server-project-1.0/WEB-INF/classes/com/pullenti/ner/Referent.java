/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех сущностей
 */
public class Referent {

    public Referent(String typ) {
        if(_globalInstance == null) return;
        m_ObjectType = typ;
    }

    private String m_ObjectType;

    /**
     * Имя типа (= InstanceOf.Name)
     */
    public String getTypeName() {
        return m_ObjectType;
    }


    @Override
    public String toString() {
        return toString(false, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    /**
     * Специализированное строковое представление сущности
     * @param shortVariant Сокращённый вариант
     * @param lang Язык
     * @return 
     */
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return getTypeName();
    }

    /**
     * По этой строке можно осуществлять сортировку среди объектов одного типа
     * @return 
     */
    public String toSortString() {
        return toString(false, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    private ReferentClass _instanceof;

    /**
     * Ссылка на описание из модели данных
     */
    public ReferentClass getInstanceOf() {
        return _instanceof;
    }

    /**
     * Ссылка на описание из модели данных
     */
    public ReferentClass setInstanceOf(ReferentClass value) {
        _instanceof = value;
        return _instanceof;
    }


    /**
     * Привязка к элементам внешней онтологии, если таковые были заданы 
     *  (в принципе, может соответствовать несколько элементов онтологии)
     */
    public java.util.ArrayList<ExtOntologyItem> ontologyItems;

    /**
     * Значения атрибутов
     */
    public java.util.ArrayList<Slot> getSlots() {
        return m_Slots;
    }


    private java.util.ArrayList<Slot> m_Slots = new java.util.ArrayList< >();

    /**
     * Добавить значение атрибута
     * @param attrName имя
     * @param attrValue значение
     * @param clearOldValue если true и слот существует, то значение перезапишется
     * @return 
     */
    public Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        if (clearOldValue) {
            for(int i = getSlots().size() - 1; i >= 0; i--) {
                if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(i).getTypeName(), attrName)) 
                    getSlots().remove(i);
            }
        }
        if (attrValue == null) 
            return null;
        for(Slot r : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), attrName)) {
                if (compareValues(r.getValue(), attrValue, true)) {
                    r.setCount(r.getCount() + statCount);
                    return r;
                }
            }
        }
        Slot res = new Slot();
        res.setOwner(this);
        res.setValue(attrValue);
        res.setTypeName(attrName);
        res.setCount(statCount);
        getSlots().add(res);
        return res;
    }

    public void uploadSlot(Slot slot, Object newVal) {
        if (slot != null) 
            slot.setValue(newVal);
    }

    /**
     * Найти слот
     * @param attrName 
     * @param val 
     * @param useCanBeEqualsForReferents 
     * @return 
     */
    public Slot findSlot(String attrName, Object val, boolean useCanBeEqualsForReferents) {
        if (attrName == null) {
            if (val == null) 
                return null;
            for(Slot r : getSlots()) {
                if (compareValues(val, r.getValue(), useCanBeEqualsForReferents)) 
                    return r;
            }
            return null;
        }
        for(Slot r : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), attrName)) {
                if (val == null) 
                    return r;
                if (compareValues(val, r.getValue(), useCanBeEqualsForReferents)) 
                    return r;
            }
        }
        return null;
    }

    private boolean compareValues(Object val1, Object val2, boolean useCanBeEqualsForReferents) {
        if (val1 == null) 
            return val2 == null;
        if (val2 == null) 
            return val1 == null;
        if (val1 == val2) 
            return true;
        if ((val1 instanceof Referent) && (val2 instanceof Referent)) {
            if (useCanBeEqualsForReferents) 
                return (((Referent)com.pullenti.n2j.Utils.cast(val1, Referent.class))).canBeEquals((Referent)com.pullenti.n2j.Utils.cast(val2, Referent.class), EqualType.DIFFERENTTEXTS);
            else 
                return false;
        }
        if (val1 instanceof String) {
            if (!((val2 instanceof String))) 
                return false;
            String s1 = val1.toString();
            String s2 = val2.toString();
            int i = com.pullenti.n2j.Utils.stringsCompare(s1, s2, true);
            return i == 0;
        }
        return val1 == val2;
    }

    /**
     * Получить значение (если их несколько, то вернёт первое)
     * @param attrName 
     * @return 
     */
    public Object getValue(String attrName) {
        for(Slot v : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(v.getTypeName(), attrName)) 
                return v.getValue();
        }
        return null;
    }

    /**
     * Получить строковое значение (если их несколько, то вернёт первое)
     * @param attrName 
     * @return 
     */
    public String getStringValue(String attrName) {
        for(Slot v : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(v.getTypeName(), attrName)) 
                return (v.getValue() == null ? null : v.getValue().toString());
        }
        return null;
    }

    /**
     * Получить все строовые значения заданного атрибута
     * @param attrName 
     * @return 
     */
    public java.util.ArrayList<String> getStringValues(String attrName) {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(Slot v : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(v.getTypeName(), attrName) && v.getValue() != null) {
                if (v.getValue() instanceof String) 
                    res.add((String)com.pullenti.n2j.Utils.cast(v.getValue(), String.class));
                else 
                    res.add(v.toString());
            }
        }
        return res;
    }

    /**
     * Получить числовое значение (если их несколько, то вернёт первое)
     * @param attrName 
     * @param defValue 
     * @return 
     */
    public int getIntValue(String attrName, int defValue) {
        String str = getStringValue(attrName);
        if (com.pullenti.n2j.Utils.isNullOrEmpty(str)) 
            return defValue;
        int res;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg2768 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres2769 = com.pullenti.n2j.Utils.parseInteger(str, inoutarg2768);
        res = (inoutarg2768.value != null ? inoutarg2768.value : 0);
        if (!inoutres2769) 
            return defValue;
        return res;
    }

    private java.util.ArrayList<TextAnnotation> m_Occurrence;

    /**
     * Привязка элемента к текстам (аннотации)
     */
    public java.util.ArrayList<TextAnnotation> getOccurrence() {
        if (m_Occurrence == null) 
            m_Occurrence = new java.util.ArrayList< >();
        return m_Occurrence;
    }


    public TextAnnotation findNearOccurence(Token t) {
        int min = -1;
        TextAnnotation res = null;
        for(TextAnnotation oc : getOccurrence()) {
            if (oc.sofa == t.kit.getSofa()) {
                int len = oc.beginChar - t.beginChar;
                if (len < 0) 
                    len = -len;
                if ((min < 0) || (len < min)) {
                    min = len;
                    res = oc;
                }
            }
        }
        return res;
    }

    public void addOccurenceOfRefTok(ReferentToken rt) {
        addOccurence(TextAnnotation._new690(rt.kit.getSofa(), rt.beginChar, rt.endChar, rt.referent));
    }

    /**
     * Добавить аннотацию
     * @param anno 
     */
    public void addOccurence(TextAnnotation anno) {
        for(TextAnnotation l : getOccurrence()) {
            com.pullenti.ner.core.internal.TextsCompareType typ = l.compareWith(anno);
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE) 
                continue;
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.EQUIVALENT || typ == com.pullenti.ner.core.internal.TextsCompareType.CONTAINS) 
                return;
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.IN || typ == com.pullenti.ner.core.internal.TextsCompareType.INTERSECT) {
                l.merge(anno);
                return;
            }
        }
        if (anno.getOccurenceOf() != this && anno.getOccurenceOf() != null) 
            anno = TextAnnotation._new2771(anno.beginChar, anno.endChar, anno.sofa);
        if (m_Occurrence == null) 
            m_Occurrence = new java.util.ArrayList< >();
        anno.setOccurenceOf(this);
        if (m_Occurrence.size() == 0) {
            anno.essentialForOccurence = true;
            m_Occurrence.add(anno);
            return;
        }
        if (anno.beginChar < m_Occurrence.get(0).beginChar) {
            m_Occurrence.add(0, anno);
            return;
        }
        if (anno.beginChar >= m_Occurrence.get(m_Occurrence.size() - 1).beginChar) {
            m_Occurrence.add(anno);
            return;
        }
        for(int i = 0; i < (m_Occurrence.size() - 1); i++) {
            if (anno.beginChar >= m_Occurrence.get(i).beginChar && anno.beginChar <= m_Occurrence.get(i + 1).beginChar) {
                m_Occurrence.add(i + 1, anno);
                return;
            }
        }
        m_Occurrence.add(anno);
    }

    /**
     * Проверка, что ссылки на элемент имеются на заданном участке текста
     * @param beginChar 
     * @param endChar 
     * @return 
     */
    public boolean checkOccurence(int beginChar, int endChar) {
        for(TextAnnotation loc : getOccurrence()) {
            com.pullenti.ner.core.internal.TextsCompareType cmp = loc.compare(beginChar, endChar);
            if (cmp != com.pullenti.ner.core.internal.TextsCompareType.EARLY && cmp != com.pullenti.ner.core.internal.TextsCompareType.LATER && cmp != com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE) 
                return true;
        }
        return false;
    }

    private Object _tag;

    /**
     * Используется произвольным образом
     */
    public Object getTag() {
        return _tag;
    }

    /**
     * Используется произвольным образом
     */
    public Object setTag(Object value) {
        _tag = value;
        return _tag;
    }


    public Referent clone() {
        Referent res = ProcessorService.createReferent(getTypeName());
        if (res == null) 
            res = new Referent(getTypeName());
        res.getOccurrence().addAll(getOccurrence());
        res.ontologyItems = ontologyItems;
        for(Slot r : getSlots()) {
            Slot rr = Slot._new2772(r.getTypeName(), r.getValue(), r.getCount());
            rr.setOwner(res);
            res.getSlots().add(rr);
        }
        return res;
    }

    /**
     * Проверка возможной тождественности объектов
     * @param obj другой объект
     * @param typ тип сравнения
     * @return результат
     */
    public boolean canBeEquals(Referent obj, EqualType typ) {
        if (obj == null || com.pullenti.n2j.Utils.stringsNe(obj.getTypeName(), getTypeName())) 
            return false;
        for(Slot r : getSlots()) {
            if (r.getValue() != null && obj.findSlot(r.getTypeName(), r.getValue(), true) == null) 
                return false;
        }
        for(Slot r : obj.getSlots()) {
            if (r.getValue() != null && findSlot(r.getTypeName(), r.getValue(), true) == null) 
                return false;
        }
        return true;
    }

    /**
     * Объединение значений атрибутов со значениями атрибутов другого объекта
     * @param obj Другой объект, считающийся эквивалентным
     */
    public void mergeSlots(Referent obj, boolean mergeStatistic) {
        if (obj == null) 
            return;
        for(Slot r : obj.getSlots()) {
            Slot s = findSlot(r.getTypeName(), r.getValue(), true);
            if (s == null && r.getValue() != null) 
                s = addSlot(r.getTypeName(), r.getValue(), false, 0);
            if (s != null && mergeStatistic) 
                s.setCount(s.getCount() + r.getCount());
        }
        _mergeExtReferents(obj);
    }

    /**
     * Ссылка на родительский объект (для разных типов объектов здесь может быть свои объекты, 
     *  например, для организаций - вышестоящая организация, для пункта закона - сам закон и т.д.)
     */
    public Referent getParentReferent() {
        return null;
    }


    /**
     * Получить идентификатор иконки (саму иконку можно получить через функцию 
     *  GetImageById(imageId) статического класса ProcessorService
     * @return 
     */
    public String getImageId() {
        if (getInstanceOf() == null) 
            return null;
        return getInstanceOf().getImageId(this);
    }

    public static final String ATTR_GENERAL = "GENERAL";

    /**
     * Проверка, может ли текущий объект быть обобщением для другого объекта
     * @param obj 
     * @return 
     */
    public boolean canBeGeneralFor(Referent obj) {
        return false;
    }

    /**
     * Ссылка на объект-обобщение
     */
    public Referent getGeneralReferent() {
        Referent res = (Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_GENERAL), Referent.class);
        if (res == null || res == this) 
            return null;
        return res;
    }

    /**
     * Ссылка на объект-обобщение
     */
    public Referent setGeneralReferent(Referent value) {
        if (value == getGeneralReferent()) 
            return value;
        if (value == this) 
            return value;
        addSlot(ATTR_GENERAL, value, true, 0);
        return value;
    }


    /**
     * Создать элемент отнологии
     * @return 
     */
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        return null;
    }

    /**
     * Используется внутренним образом (напрямую не устанавливать!)
     */
    public com.pullenti.ner.core.IntOntologyItem intOntologyItem;

    /**
     * Используется внутренним образом
     * @return 
     */
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        res.add(toString());
        String s = toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
        if (com.pullenti.n2j.Utils.stringsNe(s, res.get(0))) 
            res.add(s);
        return res;
    }

    /**
     * Сериализация в строку XML  
     *  (последующая десериализация делается через Processor.DeserializeReferent)
     * @return 
     */
    public String serialize() throws javax.xml.stream.XMLStreamException {
        StringBuilder res = new StringBuilder();
        try (com.pullenti.n2j.XmlWriterWrapper xml = new com.pullenti.n2j.XmlWriterWrapper(res)) {
            serialize(xml, null);
        }
        int i = res.toString().indexOf('>');
        if (i > 10 && res.charAt(1) == '?') 
            res.delete(0, 0+(i + 1));
        for(i = 0; i < res.length(); i++) {
            char ch = res.charAt(i);
            int cod = (int)ch;
            if ((cod < 0x80) && cod >= 0x20) 
                continue;
            if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) 
                continue;
            res.delete(i, (i)+1);
            res.insert(i, "&#x" + String.format("%04X", cod) + ";");
        }
        return res.toString();
    }

    /**
     * Прямая сериализация в XML
     * @param xml 
     */
    public void serialize(com.pullenti.n2j.XmlWriterWrapper xml, java.util.HashMap<String, String> attrs) throws javax.xml.stream.XMLStreamException {
        xml.wr.writeStartElement(getTypeName());
        if (attrs != null) {
            for(java.util.Map.Entry<String, String> kp : attrs.entrySet()) {
                xml.wr.writeAttribute(kp.getKey(), com.pullenti.ner.core.MiscHelper._corrXmlText(kp.getValue()));
            }
        }
        else if (getTag() != null) 
            xml.wr.writeAttribute("id", com.pullenti.ner.core.MiscHelper._corrXmlText(getTag().toString()));
        java.util.ArrayList<String> refs = null;
        for(Slot s : getSlots()) {
            if (s.getValue() != null) {
                String nam = s.getTypeName();
                if (nam.charAt(0) == '@') 
                    nam = "ATCOM_" + nam.substring(1);
                if (!((s.getValue() instanceof Referent)) && !((s.getValue() instanceof ProxyReferent))) {
                    xml.wr.writeStartElement(nam);
                    if (s.getCount() > 0) 
                        xml.wr.writeAttribute("count", ((Integer)s.getCount()).toString());
                    try {
                        xml.wr.writeCharacters(String.valueOf(com.pullenti.ner.core.MiscHelper._corrXmlText(s.getValue().toString())));
                    } catch(Exception ex2773) {
                    }
                    xml.wr.writeEndElement();
                }
                else {
                    String str = s.getTypeName() + s.getValue();
                    if (refs == null) 
                        refs = new java.util.ArrayList< >();
                    if (refs.contains(str)) 
                        continue;
                    refs.add(str);
                    xml.wr.writeStartElement(nam);
                    xml.wr.writeAttribute("ref", "true");
                    if (s.getCount() > 0) 
                        xml.wr.writeAttribute("count", ((Integer)s.getCount()).toString());
                    String id = null;
                    if (s.getValue() instanceof ProxyReferent) 
                        id = (((ProxyReferent)com.pullenti.n2j.Utils.cast(s.getValue(), ProxyReferent.class))).identity;
                    else if (s.getValue() instanceof Referent) {
                        Referent rr = (Referent)com.pullenti.n2j.Utils.cast(s.getValue(), Referent.class);
                        if (rr.repositoryItemId != 0) 
                            id = ((Integer)rr.repositoryItemId).toString();
                        else if (rr.getTag() != null) 
                            id = rr.getTag().toString();
                    }
                    if (!com.pullenti.n2j.Utils.isNullOrEmpty(id)) 
                        xml.wr.writeAttribute("id", id);
                    else {
                    }
                    xml.wr.writeCharacters(String.valueOf(com.pullenti.ner.core.MiscHelper._corrXmlText(s.getValue().toString())));
                    xml.wr.writeEndElement();
                }
            }
        }
        xml.wr.writeEndElement();
    }

    /**
     * Используется внутренним образом (при сохранении сущностей в репозитории)
     */
    public int repositoryItemId;

    public Referent repositoryReferent;

    public java.util.ArrayList<ReferentToken> m_ExtReferents;

    public void addExtReferent(ReferentToken rt) {
        if (rt == null) 
            return;
        if (m_ExtReferents == null) 
            m_ExtReferents = new java.util.ArrayList< >();
        if (!m_ExtReferents.contains(rt)) 
            m_ExtReferents.add(rt);
        if (m_ExtReferents.size() > 100) {
        }
    }

    protected void _mergeExtReferents(Referent obj) {
        if (obj.m_ExtReferents != null) {
            for(ReferentToken rt : obj.m_ExtReferents) {
                addExtReferent(rt);
            }
        }
    }

    public void serialize(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, getTypeName());
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Slots.size());
        for(Slot s : m_Slots) {
            com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, s.getTypeName());
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, s.getCount());
            if ((s.getValue() instanceof Referent) && ((((Referent)com.pullenti.n2j.Utils.cast(s.getValue(), Referent.class))).getTag() instanceof Integer)) 
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, -((int)(((Referent)com.pullenti.n2j.Utils.cast(s.getValue(), Referent.class))).getTag()));
            else if (s.getValue() instanceof String) 
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
            else if (s.getValue() == null) 
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
            else 
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, s.getValue().toString());
        }
        if (m_Occurrence == null) 
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
        else {
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Occurrence.size());
            for(TextAnnotation o : m_Occurrence) {
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, o.beginChar);
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, o.endChar);
                int attr = 0;
                if (o.essentialForOccurence) 
                    attr = 1;
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, attr);
            }
        }
    }

    public void deserialize(com.pullenti.n2j.Stream stream, java.util.ArrayList<Referent> all, SourceOfAnalysis sofa) throws java.io.IOException {
        String typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for(int i = 0; i < cou; i++) {
            typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            int c = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            int id = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            Object val = null;
            if (id < 0) 
                val = all.get((-id) - 1);
            else if (id > 0) {
                stream.setPosition(stream.getPosition() - ((long)4));
                val = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            }
            addSlot(typ, val, false, c);
        }
        cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Occurrence = new java.util.ArrayList< >();
        for(int i = 0; i < cou; i++) {
            TextAnnotation a = TextAnnotation._new2774(sofa, this);
            m_Occurrence.add(a);
            a.beginChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            a.endChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            int attr = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            if (((attr & 1)) != 0) 
                a.essentialForOccurence = true;
        }
    }

    /**
     * Типы сравнение объектов
     */
    public static class EqualType implements Comparable<EqualType> {
    
        /**
         * Объекты в рамках одного текста
         */
        public static final EqualType WITHINONETEXT; // 0
    
        /**
         * Объекты из разных текстов
         */
        public static final EqualType DIFFERENTTEXTS; // 1
    
        /**
         * Проверка для потенциального объединения объектов
         */
        public static final EqualType FORMERGING; // 2
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private EqualType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(EqualType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, EqualType> mapIntToEnum; 
        private static java.util.HashMap<String, EqualType> mapStringToEnum; 
        public static EqualType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            EqualType item = new EqualType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static EqualType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            WITHINONETEXT = new EqualType(0, "WITHINONETEXT");
            mapIntToEnum.put(WITHINONETEXT.value(), WITHINONETEXT);
            mapStringToEnum.put(WITHINONETEXT.m_str.toUpperCase(), WITHINONETEXT);
            DIFFERENTTEXTS = new EqualType(1, "DIFFERENTTEXTS");
            mapIntToEnum.put(DIFFERENTTEXTS.value(), DIFFERENTTEXTS);
            mapStringToEnum.put(DIFFERENTTEXTS.m_str.toUpperCase(), DIFFERENTTEXTS);
            FORMERGING = new EqualType(2, "FORMERGING");
            mapIntToEnum.put(FORMERGING.value(), FORMERGING);
            mapStringToEnum.put(FORMERGING.m_str.toUpperCase(), FORMERGING);
        }
    }

    public Referent() {
    }
    public static Referent _globalInstance;
    static {
        _globalInstance = new Referent(); 
    }
}
