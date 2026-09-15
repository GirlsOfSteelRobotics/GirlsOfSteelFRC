package com.gos.lib.properties;

import java.util.function.DoubleConsumer;

/**
 * A wrapper around PropertyManager.DoubleProperty, for things that shouldn't
 * be updated frequently for performance issues, like PIDF gains that go over the
 * CAN bus.
 *
 * You should call updateIfChanged every loop, but the actual setter will only get
 * called if the value changes
 */
public class HeavyDoubleProperty extends BaseHeavyProperty<Double> {

    /**
     * Constructor.
     * @param setter The consumer to call when the value is changed
     * @param property The underlying property
     */
    public HeavyDoubleProperty(DoubleConsumer setter, GosDoubleProperty property) {
        super(setter::accept, property);
    }

    /**
     * Helper function for making the creation of a heavy property less verbose.
     * @param propertyName The name of the property
     * @param isConstant If the property should be constant
     * @param defaultValue The default value of the property
     * @param setter The consumer to call when the value has changed
     * @return The constructed heavy property
     */
    public static HeavyDoubleProperty create(String propertyName, boolean isConstant, double defaultValue, DoubleConsumer setter) {
        GosDoubleProperty prop = new GosDoubleProperty(isConstant, propertyName, defaultValue);
        return new HeavyDoubleProperty(setter, prop);
    }

}
