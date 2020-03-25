package com.haulmont.testtask.view.subwindow.abstraction;

public interface SimpleConvertation<T> extends Convertation<T> {
    T convertFormToObject(Long id);
}
