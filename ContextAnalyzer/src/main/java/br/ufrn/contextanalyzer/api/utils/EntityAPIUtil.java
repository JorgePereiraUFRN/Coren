package br.ufrn.contextanalyzer.api.utils;

public class EntityAPIUtil {
	
	public final static Class<?> toClass(String typeName) {
		if (typeName.equalsIgnoreCase("int")) {
			return Integer.class;
		}
		else if (typeName.equalsIgnoreCase("float")) {
			return Float.class;
		}
		else if (typeName.equalsIgnoreCase("double")) {
			return Double.class;
		}
		else if (typeName.equalsIgnoreCase("byte")) {
			return Byte.class;
		}
		else if (typeName.equalsIgnoreCase("short")) {
			return Short.class;
		}
		else if (typeName.equalsIgnoreCase("long")) {
			return Long.class;
		}
		else if (typeName.equalsIgnoreCase("boolean")) {
			return Boolean.class;
		}
		else if (typeName.equalsIgnoreCase("string")) {
			return String.class;
		}
		else { // use reflection to get class
			try {
				return Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}		
		}
	}
	
	public static Comparable<?> toValue(Class<?> type, String value) {
		Comparable<?> result = null;
		try {
			result = (Comparable<?>) type.getMethod("valueOf", String.class).invoke(null, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
