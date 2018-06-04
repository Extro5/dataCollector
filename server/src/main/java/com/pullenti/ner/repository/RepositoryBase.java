/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.repository;

/**
 * Репозиторий сущностей (базовый класс)
 */
public class RepositoryBase implements AutoCloseable {

    /**
     * Экземпляр процессора, используемого при десериализации 
     *  (по умолчанию, создаётся с полным набором доступных анализаторов)
     */
    public com.pullenti.ner.Processor getProcessor() {
        if (m_Processor == null) 
            m_Processor = com.pullenti.ner.ProcessorService.createProcessor();
        return m_Processor;
    }

    /**
     * Экземпляр процессора, используемого при десериализации 
     *  (по умолчанию, создаётся с полным набором доступных анализаторов)
     */
    public com.pullenti.ner.Processor setProcessor(com.pullenti.ner.Processor value) {
        m_Processor = value;
        return value;
    }


    private com.pullenti.ner.Processor m_Processor;

    private boolean _savetextsamples;

    /**
     * Сохранять ли примеры фрагментов текстов
     */
    public boolean getSaveTextSamples() {
        return _savetextsamples;
    }

    /**
     * Сохранять ли примеры фрагментов текстов
     */
    public boolean setSaveTextSamples(boolean value) {
        _savetextsamples = value;
        return _savetextsamples;
    }


    /**
     * Инициализировать извлечение всех элементов
     */
    protected void resetFetchItems() {
    }

    /**
     * Извлечь очередной элемент
     * @return 
     */
    protected RepositoryItem fetchNextItem() {
        return null;
    }

    /**
     * Получить элемент по его идентификатору
     * @param id 
     * @return 
     */
    protected RepositoryItem getItem(int id) {
        return null;
    }

    /**
     * Сохранить изменения. Если у элемента нулевой идентификатор, то это новый 
     *  элемент, и новое значение нужно записать в поле Id.
     * @param item 
     */
    protected void saveItem(RepositoryItem item) {
    }

    /**
     * Объединить сущности. Необходимо сохранить baseItem, удалить mergedItems, 
     *  а также предпринять усилия по обеспечению целостности информации, если 
     *  кто-либо извне ссылается на удаляемые элементы.
     * @param baseItem 
     * @param mergedItems 
     */
    protected void mergeItems(RepositoryItem baseItem, java.util.ArrayList<RepositoryItem> mergedItems) {
    }

    /**
     * Добавить в элемент дополнительную информацию (которая поступает из RepositoryInputItem.AdditionalData)
     * @param item 
     * @param additionalData 
     */
    protected void addAdditionalData(RepositoryItem item, Object additionalData) {
    }

    /**
     * Создать экземпляр элемента (по умолчанию создаётся RepositoryItem)
     * @return 
     */
    protected RepositoryItem createItem() {
        return new RepositoryItem();
    }

    /**
     * Инициализация репозитория (необходимо вызывать перед первым использованием)
     */
    public void initialize() {
        if (m_Initialized) 
            return;
        m_Initialized = true;
        m_Items.clear();
        m_RefsHash.clear();
        m_Refs.clear();
        com.pullenti.n2j.Stopwatch sw = new com.pullenti.n2j.Stopwatch();
        sw.start();
        java.util.ArrayList<com.pullenti.ner.Referent> all = new java.util.ArrayList< >();
        resetFetchItems();
        while(true) {
            RepositoryItem item = fetchNextItem();
            if (item == null) 
                break;
            if (item.getId() == 0) 
                continue;
            if (item.data == null) {
                _sendMessage("Item Id=" + item.getId() + " has not data", true);
                continue;
            }
            if (item.getId() == 18) {
            }
            com.pullenti.ner.Referent r = getProcessor().deserializeReferent(item.data, ((Integer)item.getId()).toString(), true);
            if (r == null) {
                _sendMessage("Error by loading item Id=" + item.getId() + ": " + item.data, true);
                continue;
            }
            r.repositoryItemId = item.getId();
            all.add(r);
        }
        getProcessor().manageReferentLinks();
        for(com.pullenti.ner.Referent r : all) {
            _addReferent(r);
        }
        for(com.pullenti.ner.Referent r : m_Refs.values()) {
            for(com.pullenti.ner.Slot s : r.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.ProxyReferent) {
                    com.pullenti.ner.ProxyReferent pr = (com.pullenti.ner.ProxyReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.ProxyReferent.class);
                    _sendMessage("Error link Id=" + r.repositoryItemId + " (" + r.toString() + ") -> Identity=" + ((String)com.pullenti.n2j.Utils.notnull(pr.identity, "null")) + ", Value=" + ((String)com.pullenti.n2j.Utils.notnull(pr.value, "null")), true);
                    s.setValue(pr.toString());
                }
                else if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "ORGANIZATION") && com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "HIGHER") && !((s.getValue() instanceof com.pullenti.ner.Referent))) 
                    _sendMessage("Error referent value Id=" + r.repositoryItemId + " (" + r.toString() + "): " + s.getValue() + " is string!", true);
            }
        }
        sw.stop();
        _sendMessage("Load " + m_Refs.size() + " entities by " + ((int)sw.getElapsedMilliseconds()) + "ms", false);
    }

    private boolean m_Initialized;

    private void progressChangedEventHandler(Object sender, com.pullenti.n2j.ProgressEventArgs e) {
        if (e.getUserState() instanceof String) 
            _sendMessage((String)com.pullenti.n2j.Utils.cast(e.getUserState(), String.class), true);
    }

    /**
     * Общее число элементов в репозитории
     */
    public int getItemsCount() {
        return m_Refs.size();
    }


    /**
     * Вызывать в конце работы
     */
    public void deinitialize() {
        if (!m_Initialized) 
            return;
        m_Initialized = false;
    }

    @Override
    public void close() {
        deinitialize();
    }

    public static class AllItemsEnum implements java.util.Iterator<com.pullenti.ner.repository.RepositoryItem> {
    
        public AllItemsEnum(com.pullenti.ner.repository.RepositoryBase rep) {
            m_Rep = rep;
            rep.resetFetchItems();
        }
    
        public void dispose() {
        }
    
        private com.pullenti.ner.repository.RepositoryBase m_Rep;
    
        private com.pullenti.ner.repository.RepositoryItem m_Cur;
    
        @Override
        public com.pullenti.ner.repository.RepositoryItem next() {
            return m_Cur;
        }
    
    
        @Override
        public boolean hasNext() {
            m_Cur = m_Rep.fetchNextItem();
            if (m_Cur != null) 
                m_Cur.referent = m_Rep._findReferentById(m_Cur.getId());
            return m_Cur != null;
        }
    
        public void reset() {
            m_Rep.resetFetchItems();
        }
        public AllItemsEnum() {
        }
    }


    public static class AllItemsEnum0 implements Iterable<com.pullenti.ner.repository.RepositoryItem> {
    
        public AllItemsEnum0(com.pullenti.ner.repository.RepositoryBase rep) {
            m_Rep = rep;
        }
    
        private com.pullenti.ner.repository.RepositoryBase m_Rep;
    
        @Override
        public java.util.Iterator<com.pullenti.ner.repository.RepositoryItem> iterator() {
            return new com.pullenti.ner.repository.RepositoryBase.AllItemsEnum(m_Rep);
        }
        public AllItemsEnum0() {
        }
    }


    /**
     * Перечисление всех элеменов
     */
    public Iterable<RepositoryItem> getAllItems() {
        return new AllItemsEnum0(this);
    }


    /**
     * Получить список всех элементов, на которые ссылается указанный
     * @param itemId 
     * @return 
     */
    public java.util.ArrayList<RepositoryItem> getOutgoingItems(int itemId) throws javax.xml.stream.XMLStreamException {
        com.pullenti.ner.Referent r;
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Referent> inoutarg2437 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2438 = com.pullenti.n2j.Utils.tryGetValue(m_Refs, itemId, inoutarg2437);
        r = inoutarg2437.value;
        if (!inoutres2438) 
            return null;
        java.util.ArrayList<RepositoryItem> res = null;
        for(com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.Referent) {
                com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                if (rr.repositoryItemId == 0) 
                    continue;
                RepositoryItem ri = _getItem(rr, null, null);
                if (ri != null) {
                    if (res == null) 
                        res = new java.util.ArrayList< >();
                    if (!res.contains(ri)) 
                        res.add(ri);
                }
            }
        }
        return res;
    }

    /**
     * Получить список всех элементов, которые ссылаются на указанный элемент
     * @param itemId 
     * @return 
     */
    public java.util.ArrayList<RepositoryItem> getIncomingItems(int itemId) throws javax.xml.stream.XMLStreamException {
        com.pullenti.ner.Referent r;
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Referent> inoutarg2439 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2440 = com.pullenti.n2j.Utils.tryGetValue(m_Refs, itemId, inoutarg2439);
        r = inoutarg2439.value;
        if (!inoutres2440) 
            return null;
        java.util.ArrayList<RepositoryItem> res = null;
        for(com.pullenti.ner.Referent rr : m_Refs.values()) {
            for(com.pullenti.ner.Slot s : rr.getSlots()) {
                if (s.getValue() == r) {
                    RepositoryItem ri = _getItem(rr, null, null);
                    if (ri != null) {
                        if (res == null) 
                            res = new java.util.ArrayList< >();
                        if (!res.contains(ri)) 
                            res.add(ri);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Сохранить сущности
     * @param input список обёрток над сущностями
     */
    public void saveReferents(java.util.Collection<RepositoryInputItem> input) throws javax.xml.stream.XMLStreamException {
        if (!m_Initialized) 
            initialize();
        if (input == null) 
            return;
        com.pullenti.n2j.Stopwatch sw = new com.pullenti.n2j.Stopwatch();
        sw.start();
        java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList< >();
        for(RepositoryInputItem ir : input) {
            ir.referent.setTag(ir);
            ir.referent.repositoryReferent = null;
            ir.tmp = false;
            refs.add(ir.referent);
        }
        for(com.pullenti.ner.Referent r : refs) {
            for(com.pullenti.ner.Slot s : r.getSlots()) {
                if ((s.getValue() instanceof com.pullenti.ner.Referent) && !refs.contains((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))) {
                    if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).getTypeName(), "ORGANIZATION")) {
                    }
                    s.setValue(s.getValue().toString());
                }
            }
        }
        java.util.ArrayList<com.pullenti.ner.Referent> refs4load = new java.util.ArrayList< >();
        for(com.pullenti.ner.Referent r : refs) {
            if (!(((RepositoryInputItem)com.pullenti.n2j.Utils.cast(r.getTag(), RepositoryInputItem.class))).tmp) {
                refs4load.add(r);
                (((RepositoryInputItem)com.pullenti.n2j.Utils.cast(r.getTag(), RepositoryInputItem.class))).tmp = true;
                _addLinks(refs4load, r);
            }
        }
        for(com.pullenti.ner.Referent r : refs4load) {
            (((RepositoryInputItem)com.pullenti.n2j.Utils.cast(r.getTag(), RepositoryInputItem.class))).tmp = false;
            r.repositoryItemId = 0;
            r.repositoryReferent = null;
            r.setGeneralReferent(null);
        }
        java.util.ArrayList<RepositoryItem> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Referent r : refs4load) {
            RepositoryInputItem rii = (RepositoryInputItem)com.pullenti.n2j.Utils.cast(r.getTag(), RepositoryInputItem.class);
            if (rii == null) 
                continue;
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "DECREE")) {
            }
            for(com.pullenti.ner.Slot s : r.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                    if (rr.repositoryReferent != null) 
                        s.setValue(rr.repositoryReferent);
                    else {
                    }
                }
            }
            java.util.ArrayList<RepositoryItemSample> samples = null;
            if (getSaveTextSamples()) {
                if (rii.samples != null) 
                    samples = RepositoryItemSample.deserialize(rii.samples);
                else {
                    samples = new java.util.ArrayList< >();
                    for(com.pullenti.ner.TextAnnotation o : r.getOccurrence()) {
                        samples.add(new RepositoryItemSample(o.sofa.getText(), o.beginChar, o.endChar, o.essentialForOccurence));
                    }
                }
            }
            boolean hasGenerals = false;
            com.pullenti.n2j.Outargwrapper<Boolean> inoutarg2441 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.ner.Referent exRef = _findExistReferent(r, inoutarg2441);
            hasGenerals = (inoutarg2441.value != null ? inoutarg2441.value : false);
            RepositoryItem ri;
            if (exRef != null) {
                ri = _getItem(exRef, null, null);
                if (ri == null) 
                    continue;
                r.repositoryReferent = exRef;
                r.repositoryItemId = ri.getId();
                exRef.mergeSlots(r, true);
                ri.updateChanges();
                if (getSaveTextSamples()) 
                    ri.mergeSamples(samples);
                if (rii.additionalData != null) 
                    addAdditionalData(ri, rii.additionalData);
                saveItem(ri);
                ri.isChanged = false;
                _addReferent(exRef);
                res.add(ri);
                rii.item = ri;
                continue;
            }
            ri = _getItem(r, samples, rii.additionalData);
            if (ri == null) 
                continue;
            if (hasGenerals) 
                _updateGenerals(ri);
            _addReferent(ri.referent);
            r.repositoryReferent = ri.referent;
            r.repositoryItemId = ri.getId();
            res.add(ri);
            rii.item = ri;
        }
        for(int i = 0; i < 10; i++) {
            boolean isCh = false;
            for(RepositoryItem ri : res) {
                boolean ok = false;
                for(com.pullenti.ner.Slot s : ri.referent.getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.Referent) {
                        com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                        if (rr.repositoryReferent != null) {
                            s.setValue(rr.repositoryReferent);
                            ok = true;
                        }
                    }
                }
                if (!ok) 
                    continue;
                ri.updateChanges();
                if (ri.isChanged) {
                    saveItem(ri);
                    ri.isChanged = false;
                    isCh = true;
                }
            }
            if (!isCh) 
                break;
        }
        sw.stop();
        _sendMessage("Manage " + input.size() + " input entities -> " + res.size() + " items by " + ((int)sw.getElapsedMilliseconds()) + "ms", false);
    }

    /**
     * Найти для сущности существующие в хранилище элементы
     * @param referent 
     * @param includeGenerals при true будет включать в список сущности с учётом отношения обобщения
     * @return список (null - если нет аналогов)
     */
    public java.util.ArrayList<RepositoryItem> findItems(com.pullenti.ner.Referent referent, boolean includeGenerals) throws javax.xml.stream.XMLStreamException {
        if (referent == null) 
            return null;
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> inoutarg2444 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2445 = com.pullenti.n2j.Utils.tryGetValue(m_RefsHash, referent.getTypeName(), inoutarg2444);
        di = inoutarg2444.value;
        if (!inoutres2445) 
            return null;
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList< >();
        java.util.ArrayList<String> strs = referent.getCompareStrings();
        if (strs == null) 
            return null;
        for(String s : strs) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2442 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2443 = com.pullenti.n2j.Utils.tryGetValue(di, s, inoutarg2442);
            li = inoutarg2442.value;
            if (!inoutres2443) 
                continue;
            for(com.pullenti.ner.Referent rr : li) {
                if (rr.repositoryItemId <= 0) 
                    continue;
                if (res.contains(rr)) 
                    continue;
                if (!includeGenerals) {
                    if (rr.canBeGeneralFor(referent) || referent.canBeGeneralFor(rr)) 
                        continue;
                }
                if (!rr.canBeEquals(referent, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) 
                    continue;
                res.add(rr);
            }
        }
        if (res.size() == 0) 
            return null;
        java.util.ArrayList<RepositoryItem> res1 = new java.util.ArrayList< >();
        for(com.pullenti.ner.Referent r : res) {
            RepositoryItem rr = _getItem(r, null, null);
            if (rr != null) 
                res1.add(rr);
        }
        return (res1.size() == 0 ? null : res1);
    }

    private java.util.HashMap<Integer, RepositoryItem> m_Items = new java.util.HashMap< >();

    private java.util.HashMap<String, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> m_RefsHash = new java.util.HashMap< >();

    private java.util.HashMap<Integer, com.pullenti.ner.Referent> m_Refs = new java.util.HashMap< >();

    private RepositoryItem _getItem(com.pullenti.ner.Referent r, java.util.ArrayList<RepositoryItemSample> samples, Object addData) throws javax.xml.stream.XMLStreamException {
        int id = r.repositoryItemId;
        RepositoryItem res;
        if (id > 0) {
            com.pullenti.n2j.Outargwrapper<RepositoryItem> inoutarg2446 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2447 = com.pullenti.n2j.Utils.tryGetValue(m_Items, id, inoutarg2446);
            res = inoutarg2446.value;
            if (inoutres2447) 
                return res;
            res = getItem(id);
            if (res == null) {
                _sendMessage("Can't find item Id=" + id + " for " + r.toString(), true);
                return null;
            }
            m_Items.put(id, res);
            res.referent = r;
            return res;
        }
        res = createItem();
        res.repository = this;
        res.setTyp(r.getTypeName());
        res.setSpelling(r.toString());
        res.data = r.serialize();
        res.setImageId(r.getImageId());
        res.mergeSamples(samples);
        if (r.getParentReferent() != null) 
            res.setParentId(r.getParentReferent().repositoryItemId);
        if (r.getGeneralReferent() != null) 
            res.setGeneralId(r.getGeneralReferent().repositoryItemId);
        if (addData != null) 
            addAdditionalData(res, addData);
        saveItem(res);
        res.isChanged = false;
        if ((((id = res.getId()))) == 0) {
            _sendMessage("Save new item error (Id=0) for " + r.toString(), true);
            return null;
        }
        if (m_Items.containsKey(id)) {
            _sendMessage("New item Id=" + id + " exists", true);
            return null;
        }
        m_Items.put(id, res);
        com.pullenti.ner.Referent clon = com.pullenti.ner.ProcessorService.createReferent(r.getTypeName());
        for(com.pullenti.ner.Slot s : r.getSlots()) {
            clon.addSlot(s.getTypeName(), s.getValue(), false, s.getCount());
        }
        res.referent = clon;
        clon.repositoryItemId = id;
        _sendMessage("New item Id=" + id + ": " + clon.toString(), false);
        return res;
    }

    private void _addLinks(java.util.ArrayList<com.pullenti.ner.Referent> refs, com.pullenti.ner.Referent r) {
        for(com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.Referent) {
                com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                RepositoryInputItem ir = (RepositoryInputItem)com.pullenti.n2j.Utils.cast(rr.getTag(), RepositoryInputItem.class);
                if (ir == null || ir.tmp) 
                    continue;
                int ind = refs.indexOf(r);
                if (ind < 0) 
                    continue;
                refs.add(ind, rr);
                ir.tmp = true;
                _addLinks(refs, rr);
            }
        }
    }

    private void _addReferent(com.pullenti.ner.Referent r) {
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di = null;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> inoutarg2452 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2453 = com.pullenti.n2j.Utils.tryGetValue(m_RefsHash, r.getTypeName(), inoutarg2452);
        di = inoutarg2452.value;
        if (!inoutres2453) 
            m_RefsHash.put(r.getTypeName(), (di = new java.util.HashMap< >()));
        for(String s : r.getCompareStrings()) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2448 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2449 = com.pullenti.n2j.Utils.tryGetValue(di, s, inoutarg2448);
            li = inoutarg2448.value;
            if (!inoutres2449) 
                di.put(s, (li = new java.util.ArrayList< >()));
            if (!li.contains(r)) 
                li.add(r);
        }
        if (r.repositoryItemId > 0) {
            com.pullenti.ner.Referent rr;
            com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Referent> inoutarg2450 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2451 = com.pullenti.n2j.Utils.tryGetValue(m_Refs, r.repositoryItemId, inoutarg2450);
            rr = inoutarg2450.value;
            if (!inoutres2451) 
                m_Refs.put(r.repositoryItemId, r);
            else if (r != rr) 
                _sendMessage("Id=" + r.repositoryItemId + " has 2 entities: (" + r.toString() + ") and (" + rr.toString() + ")", true);
        }
    }

    private void _RemoveReferent(com.pullenti.ner.Referent r) {
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di = null;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> inoutarg2456 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2457 = com.pullenti.n2j.Utils.tryGetValue(m_RefsHash, r.getTypeName(), inoutarg2456);
        di = inoutarg2456.value;
        if (!inoutres2457) 
            return;
        for(String s : r.getCompareStrings()) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2454 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2455 = com.pullenti.n2j.Utils.tryGetValue(di, s, inoutarg2454);
            li = inoutarg2454.value;
            if (inoutres2455) {
                if (li.contains(r)) 
                    li.remove(r);
            }
        }
        if (r.repositoryItemId > 0) {
            if (m_Refs.containsKey(r.repositoryItemId)) 
                m_Refs.remove(r.repositoryItemId);
        }
    }

    private com.pullenti.ner.Referent _findReferentById(int id) {
        com.pullenti.ner.Referent r;
        com.pullenti.n2j.Outargwrapper<com.pullenti.ner.Referent> inoutarg2458 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2459 = com.pullenti.n2j.Utils.tryGetValue(m_Refs, id, inoutarg2458);
        r = inoutarg2458.value;
        if (inoutres2459) 
            return r;
        return null;
    }

    private com.pullenti.ner.Referent _findExistReferent(com.pullenti.ner.Referent r, com.pullenti.n2j.Outargwrapper<Boolean> hasGenerals) throws javax.xml.stream.XMLStreamException {
        hasGenerals.value = false;
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> inoutarg2462 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2463 = com.pullenti.n2j.Utils.tryGetValue(m_RefsHash, r.getTypeName(), inoutarg2462);
        di = inoutarg2462.value;
        if (!inoutres2463) 
            return null;
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList< >();
        java.util.ArrayList<String> strs = r.getCompareStrings();
        if (strs == null) 
            return null;
        for(String s : strs) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2460 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2461 = com.pullenti.n2j.Utils.tryGetValue(di, s, inoutarg2460);
            li = inoutarg2460.value;
            if (!inoutres2461) 
                continue;
            if (li.size() > 100) {
            }
            for(com.pullenti.ner.Referent rr : li) {
                if (rr.repositoryItemId <= 0) 
                    continue;
                if (res.contains(rr)) 
                    continue;
                if (rr.canBeGeneralFor(r) || r.canBeGeneralFor(rr)) {
                    hasGenerals.value = true;
                    continue;
                }
                if (!rr.canBeEquals(r, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) 
                    continue;
                res.add(rr);
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.size() > 1) 
            _mergeEntities(res, 0, true);
        return res.get(0);
    }

    private void _updateGenerals(RepositoryItem ri) throws javax.xml.stream.XMLStreamException {
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di;
        com.pullenti.n2j.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> inoutarg2466 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2467 = com.pullenti.n2j.Utils.tryGetValue(m_RefsHash, ri.referent.getTypeName(), inoutarg2466);
        di = inoutarg2466.value;
        if (!inoutres2467) 
            return;
        java.util.ArrayList<com.pullenti.ner.Referent> genPar = null;
        java.util.ArrayList<com.pullenti.ner.Referent> genCh = null;
        java.util.ArrayList<String> strs = ri.referent.getCompareStrings();
        if (strs == null) 
            return;
        for(String s : strs) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> inoutarg2464 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2465 = com.pullenti.n2j.Utils.tryGetValue(di, s, inoutarg2464);
            li = inoutarg2464.value;
            if (!inoutres2465) 
                continue;
            for(com.pullenti.ner.Referent rr : li) {
                if (rr.repositoryItemId <= 0) 
                    continue;
                if (rr.canBeGeneralFor(ri.referent) && !ri.referent.canBeGeneralFor(rr)) {
                    if (genPar == null) 
                        genPar = new java.util.ArrayList< >();
                    if (!genPar.contains(rr)) 
                        genPar.add(rr);
                }
                if (ri.referent.canBeGeneralFor(rr) && !rr.canBeGeneralFor(ri.referent)) {
                    if (genCh == null) 
                        genCh = new java.util.ArrayList< >();
                    if (!genCh.contains(rr)) 
                        genCh.add(rr);
                }
            }
        }
        if (genPar != null) {
            for(int i = 0; i < genPar.size(); i++) {
                if (genPar.get(i).getGeneralReferent() != null && genPar.contains(genPar.get(i).getGeneralReferent())) {
                    genPar.remove(genPar.get(i).getGeneralReferent());
                    i--;
                }
            }
            if (genPar.size() == 1) {
                ri.referent.setGeneralReferent(genPar.get(0));
                ri.updateChanges();
                ri.setGeneralId(genPar.get(0).repositoryItemId);
                saveItem(ri);
                ri.isChanged = false;
            }
        }
        if (genCh != null) {
            for(com.pullenti.ner.Referent rr : genCh) {
                boolean ch = false;
                if (rr.getGeneralReferent() == null) {
                    rr.setGeneralReferent(ri.referent);
                    ch = true;
                }
                else if (ri.referent.getGeneralReferent() == rr.getGeneralReferent()) {
                    rr.setGeneralReferent(ri.referent);
                    ch = true;
                }
                if (ch) {
                    RepositoryItem rri = _getItem(rr, null, null);
                    if (rri == null) 
                        continue;
                    rri.updateChanges();
                    rri.setGeneralId(ri.getId());
                    saveItem(rri);
                    rri.isChanged = false;
                }
            }
        }
    }

    /**
     * Объединение сущностей - тонкая вещь
     * @param res 
     */
    private void _mergeEntities(java.util.ArrayList<com.pullenti.ner.Referent> res, int level, boolean remove) throws javax.xml.stream.XMLStreamException {
        if (res == null || res.size() == 0) 
            return;
        RepositoryItem baseItem = _getItem(res.get(0), null, null);
        if (baseItem == null) 
            return;
        _sendMessage("Merge equal " + res.size() + " entities:", false);
        java.util.ArrayList<RepositoryItem> delItems = new java.util.ArrayList< >();
        for(int i = 0; i < res.size(); i++) {
            if (res.get(i).repositoryItemId == 0) 
                continue;
            _sendMessage("  Id=" + res.get(i).repositoryItemId + ": " + res.get(i).toString(), false);
            if (i > 0) {
                RepositoryItem it = _getItem(res.get(i), null, null);
                if (it == null) 
                    continue;
                baseItem.referent.mergeSlots(res.get(i), true);
                baseItem.mergeSamplesRi(it);
                delItems.add(it);
                if (remove) 
                    _RemoveReferent(res.get(i));
                res.get(i).repositoryItemId = 0;
                res.get(i).repositoryReferent = null;
            }
        }
        baseItem.updateChanges();
        saveItem(baseItem);
        baseItem.isChanged = false;
        _addReferent(baseItem.referent);
        mergeItems(baseItem, delItems);
        java.util.ArrayList<RepositoryItem> addRefs = new java.util.ArrayList< >();
        for(com.pullenti.ner.Referent rr : m_Refs.values()) {
            boolean ch = false;
            for(com.pullenti.ner.Slot s : rr.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    if (res.indexOf((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)) > 0) {
                        ch = true;
                        s.setValue(res.get(0));
                    }
                }
            }
            if (!ch) 
                continue;
            RepositoryItem ex = _getItem(rr, null, null);
            if (ex != null) {
                if (!addRefs.contains(ex) && !delItems.contains(ex)) 
                    addRefs.add(ex);
            }
        }
        for(RepositoryItem it : delItems) {
            if (m_Items.containsKey(it.getId())) 
                m_Items.remove(it.getId());
        }
        for(RepositoryItem e : addRefs) {
            e.updateChanges();
            saveItem(e);
            e.isChanged = false;
            _addReferent(e.referent);
        }
    }

    /**
     * Это для сообщений
     */
    public java.util.ArrayList<com.pullenti.n2j.EventHandler<Object>> messageOccured = new java.util.ArrayList<com.pullenti.n2j.EventHandler<Object>>();

    private void _sendMessage(String msg, boolean err) {
        if (err) {
        }
        if (messageOccured.size() > 0) 
            for(int iiid = 0; iiid < messageOccured.size(); iiid++) messageOccured.get(iiid).call(this, RepositoryMessageArgs._new2468(msg, err));
    }
    public RepositoryBase() {
    }
    public static RepositoryBase _globalInstance;
    static {
        _globalInstance = new RepositoryBase(); 
    }
}
