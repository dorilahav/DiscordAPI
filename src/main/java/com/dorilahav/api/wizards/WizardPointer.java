package com.dorilahav.api.wizards;

import java.beans.ConstructorProperties;
import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@FunctionalInterface
public interface WizardPointer<T> {

    T get();

    public class ClassPointer<T> implements WizardPointer<T> {

        final Class<T>
                clazz;

        final Object[]
                args;

        @JsonCreator
        @ConstructorProperties(value = {"clazz", "args"})
        public ClassPointer(Class<T> clazz, Object... args) {
            this.clazz = clazz;
            this.args = args;
        }

        @Override
        @SneakyThrows
        public T get() {
            return ConstructorUtils.invokeConstructor(clazz, args);
        }

    }

    public class FieldPointer<T> implements WizardPointer<T> {

        final Field
                field;

        @ConstructorProperties({"field"})
        public FieldPointer(Field field) {
            this.field = field;
        }

        @SuppressWarnings("unchecked")
        @Override
        @SneakyThrows
        public T get() {
            return (T) field.get(null);
        }

    }

    public class ObjectPointer<T> implements WizardPointer<T> {

        final T
                t;

        @ConstructorProperties({"t"})
        public ObjectPointer(T t) {
            this.t = t;
        }

        @Override
        public T get() {
            return t;
        }

    }

}
