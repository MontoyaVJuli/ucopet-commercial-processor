package com.uco.ucopetcommercialprocessor.util;

public interface MessageSender<T> {
    void execute(T message, String idMessage);
}