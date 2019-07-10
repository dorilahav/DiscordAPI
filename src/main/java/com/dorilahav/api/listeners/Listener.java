package com.dorilahav.api.listeners;

import java.lang.reflect.ParameterizedType;

import lombok.Getter;
import net.dv8tion.jda.core.events.Event;

@SuppressWarnings("unchecked")
public abstract class Listener<T> {

    @Getter
    private Class<T>
            eventType;

    {
        try {
            eventType =
                    (Class<T>) Class.forName(((ParameterizedType) getClass().getGenericSuperclass()).
                            getActualTypeArguments()[0].getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void run(Event event) {
        run((T) event);
    }

    public abstract void run(T event);

}
