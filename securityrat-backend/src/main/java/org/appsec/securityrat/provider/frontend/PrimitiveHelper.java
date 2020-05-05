package org.appsec.securityrat.provider.frontend;

final class PrimitiveHelper {
    public static Object parsePrimitive(String value, Class<?> targetType) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        
        if (targetType == String.class) {
            return value;
        }
        
        // Unsigned integers

        if (targetType == Character.class || targetType == char.class) {
            return value.charAt(0);
        }
        
        // Signed integers
        
        if (targetType == Byte.class || targetType == byte.class) {
            return Byte.parseByte(value);
        }
        
        if (targetType == Short.class || targetType == short.class) {
            return Short.parseShort(value);
        }
        
        if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        }
        
        if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value);
        }
        
        // Floating point numbers
        
        if (targetType == Float.class || targetType == float.class) {
            return Float.parseFloat(value);
        }
        
        if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(value);
        }
        
        // Boolean
        
        if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value);
        }
        
        // Non-primitives
        
        throw new IllegalArgumentException(
                "Class not primitive: " + targetType.getName());
    }
}
