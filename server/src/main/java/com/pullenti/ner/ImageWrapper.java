/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Приходится работать через обёртку, так как некоторые реализации .NET не содержат System.Drawing 
 *  (например, для Андроида)
 */
public class ImageWrapper {

    /**
     * Уникальный идентификатор
     */
    public String id;

    /**
     * Байтовый поток иконки
     */
    public byte[] content;

    /**
     * А здесь Bitmap вы уж сами формируйте, если нужно
     */
    public Object image;

    public static ImageWrapper _new2764(String _arg1, byte[] _arg2) {
        ImageWrapper res = new ImageWrapper();
        res.id = _arg1;
        res.content = _arg2;
        return res;
    }
    public ImageWrapper() {
    }
}
