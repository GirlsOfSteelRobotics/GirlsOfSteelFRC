package com.gos.lib.properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

    private static boolean purgeConstantPreferenceKeys;

    private static final Set<String> REGISTERED_KEYS = new HashSet<>();
    private static final Set<BaseProperty<?>> DYNAMIC_PROPERTIES = new LinkedHashSet<>();

    private PropertyManager() {

    }

    /**
     * Sets the ability to have the key names associated with constant property removed when the constant property is
     * created. There is no checking to see if the constant is the same as the value saved on the robot
     * @param purgeConstantKeys True to auto-purge values
     */
    public static void setPurgeConstantPreferenceKeys(boolean purgeConstantKeys) {
        purgeConstantPreferenceKeys = purgeConstantKeys;
    }


    /**
     * Purges all the keys not associated with a dynamic property. Useful if you have an old config
     * file from a previous year, or have removed a property all together
     */
    public static void purgeExtraKeys() {
        Collection<String> keys = Preferences.getKeys();
        for (String key : keys) {
            if (!REGISTERED_KEYS.contains(key) && !".type".equals(key)) {
                Preferences.remove(key);
            }
        }
    }

    public static void printDynamicProperties() {
        for (BaseProperty<?> prop : DYNAMIC_PROPERTIES) {
            System.out.println(prop.m_constructionStackTrace);
        }
    }

    public interface IProperty<TypeT> {
        TypeT getValue();

        String getName();
    }

    private static class ConstantProperty<TypeT> implements IProperty<TypeT> {
        private final TypeT m_value;
        private final String m_name;

        private ConstantProperty(String key, TypeT value) {
            m_value = value;
            m_name = key;

            if (purgeConstantPreferenceKeys) {
                System.out.print("Removing constant key for '" + key + "'");
                Preferences.remove(key);
            }
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

    private static class BaseProperty<TypeT> implements IProperty<TypeT> {
        private final String m_key;
        private final TypeT m_default;
        private final BiConsumer<String, TypeT> m_setter;
        private final BiFunction<String, TypeT, TypeT> m_getter;
        private final String m_constructionStackTrace;

        private BaseProperty(String key, TypeT defaultValue, BiConsumer<String, TypeT> setter,
                BiFunction<String, TypeT, TypeT> getter) {
            m_key = key;
            m_default = defaultValue;
            m_setter = setter;
            m_getter = getter;

            REGISTERED_KEYS.add(key);

            getValue();

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder(200);
            sb.append("Creating non-constant property '").append(m_key).append("'. Default=")
                .append(m_default).append(" vs Saved=").append(getValue())
                .append(" -- at:");

            // Skip the first couple entries, because they are all the same.
            // getStackTrace() -> BaseProperty() -> <type>Property() -> create<type>Property()
            for (int i = 4; i < stackTrace.length; ++i) {
                sb.append("\n  ").append(stackTrace[i]);
            }
            m_constructionStackTrace = sb.toString();

            DYNAMIC_PROPERTIES.add(this);
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

    private static class IntProperty extends BaseProperty<Integer> {
        private IntProperty(String key, int defaultValue) {
            super(key, defaultValue, Preferences::setInt, Preferences::getInt);
        }
    }

    public static IProperty<Integer> createIntProperty(boolean isConstant, String key, int defaultValue) {
        if (isConstant) {
            return new ConstantProperty<>(key, defaultValue);
        }
        return new IntProperty(key, defaultValue);
    }

    private static class DoubleProperty extends BaseProperty<Double> {
        private DoubleProperty(String key, double defaultValue) {
            super(key, defaultValue, Preferences::setDouble, Preferences::getDouble);
        }
    }

    public static IProperty<Double> createDoubleProperty(boolean isConstant, String key, double defaultValue) {
        if (isConstant) {
            return new ConstantProperty<>(key, defaultValue);
        }
        return new DoubleProperty(key, defaultValue);
    }

    private static class StringProperty extends BaseProperty<String> {
        private StringProperty(String key, String defaultValue) {
            super(key, defaultValue, Preferences::setString, Preferences::getString);
        }
    }

    public static IProperty<String> createStringProperty(boolean isConstant, String key, String defaultValue) {
        if (isConstant) {
            return new ConstantProperty<>(key, defaultValue);
        }
        return new StringProperty(key, defaultValue);
    }

    private static class BooleanProperty extends BaseProperty<Boolean> {
        private BooleanProperty(String key, boolean defaultValue) {
            super(key, defaultValue, Preferences::setBoolean, Preferences::getBoolean);
        }
    }

    public static IProperty<Boolean> createBooleanProperty(boolean isConstant, String key, boolean defaultValue) {
        if (isConstant) {
            return new ConstantProperty<>(key, defaultValue);
        }
        return new BooleanProperty(key, defaultValue);
    }
}
