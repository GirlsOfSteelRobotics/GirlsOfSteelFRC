package com.gos.lib.properties;

import java.util.function.IntConsumer;

/**
 * A wrapper around PropertyManager.DoubleProperty, for things that shouldn't
 * be updated frequently for performance issues, like PIDF gains that go over the
 * CAN bus.
 *
 * You should call updateIfChanged every loop, but the actual setter will only get
 * called if the value changes
 */
public class HeavyIntegerProperty extends BaseHeavyProperty<Integer> {

    /**
     * Constructor.
     * @param setter The consumer to call when the value is changed
     * @param property The underlying property
     */
    public HeavyIntegerProperty(IntConsumer setter, GosIntProperty property) {
        super(setter::accept, property);
    }

}
