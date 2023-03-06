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

    public HeavyDoubleProperty(DoubleConsumer setter, GosDoubleProperty property) {
        super(setter::accept, property);
    }

}
