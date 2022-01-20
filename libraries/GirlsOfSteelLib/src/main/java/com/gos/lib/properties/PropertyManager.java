package com.gos.lib.properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import edu.wpi.first.wpilibj.Preferences;

/**
 * This class contains basic configurable properties. There is an interface
 * where you can ask for a data type, as well as basic classes that can grab the
 * constants from the network table for faster prototyping.
 *
 * @author PJ
 *
 */
public final class PropertyManager {

    private static final Set<String> REGISTERED_KEYS = new HashSet<>();

    private PropertyManager() {

    }


    public static void purgeExtraKeys() {
        Collection<String> keys = Preferences.getKeys();
        for (String key : keys) {
            if (!REGISTERED_KEYS.contains(key) && !".type".equals(key)) {
                Preferences.remove(key);
            }
        }
    }

    public interface IProperty<TypeT> {
        TypeT getValue();

        String getName();
    }

    public static class ConstantProperty<TypeT> implements IProperty<TypeT> {
        private final TypeT m_value;
        private final String m_name;

        public ConstantProperty(String key, TypeT value) {
            m_value = value;
            m_name = key;

            Preferences.remove(key);
        }

        @Override
        public TypeT getValue() {
            return m_value;
        }

        @Override
        public String getName() {
            return m_name;
        }
    }

    public static class BaseProperty<TypeT> implements IProperty<TypeT> {
        private final String m_key;
        private final TypeT m_default;
        private final BiConsumer<String, TypeT> m_setter;
        private final BiFunction<String, TypeT, TypeT> m_getter;

        public BaseProperty(String key, TypeT defaultValue, BiConsumer<String, TypeT> setter,
                BiFunction<String, TypeT, TypeT> getter) {
            m_key = key;
            m_default = defaultValue;
            m_setter = setter;
            m_getter = getter;

            REGISTERED_KEYS.add(key);

            getValue();
        }

        @Override
        public final TypeT getValue() {
            if (Preferences.containsKey(m_key)) {
                return m_getter.apply(m_key, m_default);
            }

            m_setter.accept(m_key, m_default);
            return m_default;
        }

        @Override
        public String getName() {
            return m_key;
        }
    }

    public static class IntProperty extends BaseProperty<Integer> {
        public IntProperty(String key, int defaultValue) {
            super(key, defaultValue, Preferences.getInstance()::putInt, Preferences.getInstance()::getInt);
        }
    }

    public static class DoubleProperty extends BaseProperty<Double> {
        public DoubleProperty(String key, double defaultValue) {
            super(key, defaultValue, Preferences.getInstance()::putDouble, Preferences.getInstance()::getDouble);
        }
    }

    public static class StringProperty extends BaseProperty<String> {
        public StringProperty(String key, String defaultValue) {
            super(key, defaultValue, Preferences.getInstance()::putString, Preferences.getInstance()::getString);
        }
    }

    public static class BooleanProperty extends BaseProperty<Boolean> {
        public BooleanProperty(String key, boolean defaultValue) {
            super(key, defaultValue, Preferences.getInstance()::putBoolean, Preferences.getInstance()::getBoolean);
        }
    }
}
