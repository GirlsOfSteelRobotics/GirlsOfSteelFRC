package frc.robot.lib;

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
public class PropertyManager {

	private static final Set<String> REGISTERED_KEYS = new HashSet<>();

	public static void purgeExtraKeys()
	{
		Collection<String> keys = Preferences.getInstance().getKeys();
		for(String key : keys)
		{
			if (!REGISTERED_KEYS.contains(key) && !".type".equals(key))
			{
				Preferences.getInstance().remove(key);
			}
		}
	}

	public static interface IProperty<TypeT> {
		TypeT getValue();

		String getName();
	}

	public static class ConstantProperty<TypeT> implements IProperty<TypeT> {
		private final TypeT mValue;
		private final String mName;

		public ConstantProperty(String aKey, TypeT aValue) {
			mValue = aValue;
			mName = aKey;

			Preferences.getInstance().remove(aKey);
		}

		@Override
		public TypeT getValue() {
			return mValue;
		}

		@Override
		public String getName() {
			return mName;
		}
	}

	public static class BaseProperty<TypeT> implements IProperty<TypeT> {
		private final String mKey;
		private final TypeT mDefault;
		private final BiConsumer<String, TypeT> mSetter;
		private final BiFunction<String, TypeT, TypeT> mGetter;

		public BaseProperty(String aKey, TypeT aDefault, BiConsumer<String, TypeT> aSetter,
				BiFunction<String, TypeT, TypeT> aGetter) {
			mKey = aKey;
			mDefault = aDefault;
			mSetter = aSetter;
			mGetter = aGetter;

			REGISTERED_KEYS.add(aKey);

			getValue();
		}

		@Override
		public TypeT getValue() {
			if (Preferences.getInstance().containsKey(mKey)) {
				return mGetter.apply(mKey, mDefault);
			}

			mSetter.accept(mKey, mDefault);
			return mDefault;
		}

		@Override
		public String getName() {
			return mKey;
		}
	}

	public static class IntProperty extends BaseProperty<Integer> {
		public IntProperty(String aKey, int aDefault) {
			super(aKey, aDefault, Preferences.getInstance()::putInt, Preferences.getInstance()::getInt);
		}
	}

	public static class DoubleProperty extends BaseProperty<Double> {
		public DoubleProperty(String aKey, double aDefault) {
			super(aKey, aDefault, Preferences.getInstance()::putDouble, Preferences.getInstance()::getDouble);
		}
	}

	public static class StringProperty extends BaseProperty<String> {
		public StringProperty(String aKey, String aDefault) {
			super(aKey, aDefault, Preferences.getInstance()::putString, Preferences.getInstance()::getString);
		}
	}

	public static class BooleanProperty extends BaseProperty<Boolean> {
		public BooleanProperty(String aKey, boolean aDefault) {
			super(aKey, aDefault, Preferences.getInstance()::putBoolean, Preferences.getInstance()::getBoolean);
		}
	}
}
