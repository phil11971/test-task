package com.haulmont.testtask.view.subwindow.abstraction;

public interface CompositeConvertation<T> extends Convertation<T> {
    T convertFormToObject(Long firstId, Long secondId);
}
