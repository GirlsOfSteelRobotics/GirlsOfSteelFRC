package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.Preferences;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * This class contains basic configurable properties. There is an interface
 * where you can ask for a data type, as well as basic classes that can grab the
 * constants from the network table for faster prototyping.
 *
 * @author PJ
 *
 */
public final class PropertyManager {

    private static final Alert PROPERTY_MISMATCH_ALERT = new Alert("Properties Mismatch", AlertType.kWarning);
    private static final Alert HAS_DYNAMIC_PROPERTIES = new Alert("Using Dynamic GOS Properties", AlertType.kInfo);
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
        printDynamicProperties(true);
    }

    public static void printDynamicProperties(boolean printOnlyMismatches) {
        // Print all the properties that do not match their default
        for (BaseProperty<?> prop : DYNAMIC_PROPERTIES) {
            if (!prop.m_default.equals(prop.getValue())) {
                PROPERTY_MISMATCH_ALERT.set(true);
                System.err.println(prop.m_constructionStackTrace);
            }
        }

        // If we want to print all dynamic properties, only print out the ones that didn't get logged in stderr
        if (!printOnlyMismatches) {
            for (BaseProperty<?> prop : DYNAMIC_PROPERTIES) {
                if (prop.m_default.equals(prop.getValue())) {
                    System.out.println(prop.m_constructionStackTrace);
                }
            }
        }
    }

    /* default */ interface IProperty<TypeT> {
        TypeT getValue();

        String getName();

        void resetValue();
    }

    /* default */ static class ConstantProperty<TypeT> implements IProperty<TypeT> {
        private final TypeT m_value;
        private final String m_name;

        /* default */ ConstantProperty(String key, TypeT value) {
            m_value = value;
            m_name = key;

            if (purgeConstantPreferenceKeys && Preferences.containsKey(key)) {
                System.out.println("Removing constant key for '" + key + "'");
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

        @Override
        public void resetValue() {
            // Do nothing as it's a constant value
        }
    }

    /* default */ static class BaseProperty<TypeT> implements IProperty<TypeT> {
        private final String m_key;
        private final TypeT m_default;
        private final BiConsumer<String, TypeT> m_setter;
        private final BiFunction<String, TypeT, TypeT> m_getter;
        private final String m_constructionStackTrace;

        /* default */ BaseProperty(String key, TypeT defaultValue, BiConsumer<String, TypeT> setter,
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
            // getStackTrace() -> BaseProperty() -> Gos<type>Property()
            //
            // Also cut off the end because it is always the same
            // startRobot() -> runRobot() -> startCompetition() -> robotIni()
            for (int i = 3; i < stackTrace.length - 4; ++i) {
                sb.append("\n  ").append(stackTrace[i]);
            }
            m_constructionStackTrace = sb.toString();

            DYNAMIC_PROPERTIES.add(this);
            HAS_DYNAMIC_PROPERTIES.set(true);
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

        @Override
        public void resetValue() {
            m_setter.accept(m_key, m_default);
        }
    }
}
