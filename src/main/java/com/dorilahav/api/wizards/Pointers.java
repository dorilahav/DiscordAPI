package com.dorilahav.api.wizards;

import java.lang.reflect.Field;

import lombok.SneakyThrows;

public class Pointers {

    public static <T> WizardPointer<T> classPointer(Class<T> clazz, Object... args) {
        return new WizardPointer.ClassPointer<>(clazz, args);
    }

    public static <T> WizardPointer<T> fieldPointer(Field field) {
        return new WizardPointer.FieldPointer<>(field);
    }

    @SneakyThrows
    public static <T> WizardPointer<T> fieldPointer(Class<?> clazz, String fieldName) {
        return new WizardPointer.FieldPointer<>(clazz.getDeclaredField(fieldName));
    }

    public static <T> WizardPointer<T> objectPointer(T t) {
        return new WizardPointer.ObjectPointer<>(t);
    }

}
