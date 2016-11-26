package hu.elte.inetsense.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PropertyUtil {
	public static final List<String> TRUE_VALUES = Collections.unmodifiableList(
		Arrays.asList("true", "TRUE", "True", "YES", "Yes", "yes", "y", "Y")
    );

	public static String getProperty(String key) {
		return getProperty(key, null);
	}
	
	public static Integer getIntProperty(String key) {
		return Integer.parseInt(getProperty(key));
	}
	
	public static String getProperty(String key, String defaultValue) {
		return System.getProperty(key, defaultValue);
	}
	
	public static Integer getIntProperty(String key, int defaultValue) {
		String stringValue = getProperty(key);
		int result = defaultValue;
		if(stringValue != null) {
			result = Integer.parseInt(stringValue);
		}
		return result;
	}
	
	public static boolean getBooleanProperty(String key) {
		String value = getProperty(key);
		return value != null && isTrueValue(value);
	}

	private static boolean isTrueValue(String value) {
		return TRUE_VALUES.contains(value);
	}
}
