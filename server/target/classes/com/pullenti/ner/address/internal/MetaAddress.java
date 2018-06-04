/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.address.internal;

public class MetaAddress extends com.pullenti.ner.ReferentClass {

    public MetaAddress() {
        super();
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_STREET, "Улица", 0, 2);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_HOUSE, "Дом", 0, 1);
        houseTypeFeature = addFeature(com.pullenti.ner.address.AddressReferent.ATTR_HOUSETYPE, "Тип дома", 0, 1);
        houseTypeFeature.addValue(com.pullenti.ner.address.AddressHouseType.ESTATE.toString(), "Владение", null, null);
        houseTypeFeature.addValue(com.pullenti.ner.address.AddressHouseType.HOUSE.toString(), "Дом", null, null);
        houseTypeFeature.addValue(com.pullenti.ner.address.AddressHouseType.HOUSEESTATE.toString(), "Домовладение", null, null);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BUILDING, "Строение", 0, 1);
        buildingTypeFeature = addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BUILDINGTYPE, "Тип строения", 0, 1);
        buildingTypeFeature.addValue(com.pullenti.ner.address.AddressBuildingType.BUILDING.toString(), "Строение", null, null);
        buildingTypeFeature.addValue(com.pullenti.ner.address.AddressBuildingType.CONSTRUCTION.toString(), "Сооружение", null, null);
        buildingTypeFeature.addValue(com.pullenti.ner.address.AddressBuildingType.LITER.toString(), "Литера", null, null);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_CORPUS, "Корпус", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_PORCH, "Подъезд", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FLOOR, "Этаж", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FLAT, "Квартира", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_CORPUSORFLAT, "Корпус или квартира", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_OFFICE, "Офис", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_PLOT, "Участок", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BLOCK, "Блок", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BOX, "Бокс", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_KILOMETER, "Километр", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_GEO, "Город\\Регион\\Страна", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_ZIP, "Индекс", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_POSTOFFICEBOX, "Абоненский ящик", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_CSP, "ГСП", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_METRO, "Метро", 0, 1);
        com.pullenti.ner.Feature detail = addFeature(com.pullenti.ner.address.AddressReferent.ATTR_DETAIL, "Дополнительный указатель", 0, 1);
        detailFeature = detail;
        detail.addValue(com.pullenti.ner.address.AddressDetailType.CROSS.toString(), "На пересечении", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NEAR.toString(), "Вблизи", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.HOSTEL.toString(), "Общежитие", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NORTH.toString(), "Севернее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.SOUTH.toString(), "Южнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.EAST.toString(), "Восточнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.WEST.toString(), "Западнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NORTHEAST.toString(), "Северо-восточнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NORTHWEST.toString(), "Северо-западнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.SOUTHEAST.toString(), "Юго-восточнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.SOUTHWEST.toString(), "Юго-западнее", null, null);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_MISC, "Разное", 0, 0);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_DETAILPARAM, "Параметр детализации", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FIAS, "Объект ФИАС", 0, 1);
        addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BTI, "Объект БТИ", 0, 1);
    }

    public com.pullenti.ner.Feature detailFeature;

    public com.pullenti.ner.Feature houseTypeFeature;

    public com.pullenti.ner.Feature buildingTypeFeature;

    @Override
    public String getName() {
        return com.pullenti.ner.address.AddressReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Адрес";
    }


    public static String ADDRESSIMAGEID = "address";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return ADDRESSIMAGEID;
    }

    public static MetaAddress globalMeta = new MetaAddress();
}
