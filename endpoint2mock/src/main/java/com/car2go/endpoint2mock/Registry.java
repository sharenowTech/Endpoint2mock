package com.car2go.endpoint2mock;

import java.lang.reflect.Method;

/**
 * Checks whether URL should be mocked or not.
 */
class Registry {

    private final Class<?> generatedMocksRegistryClass;
    private final Method isMockedMethod;

    public Registry() {
        generatedMocksRegistryClass = loadMocksRegistryClass();
        isMockedMethod = loadIsMockedMethod(generatedMocksRegistryClass);
    }

    private static Method loadIsMockedMethod(Class<?> registryClass) {
        if (registryClass == null) {
            return null;
        }

        try {
            return registryClass.getDeclaredMethod("isMocked", registryClass);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Class<?> loadMocksRegistryClass() {
        try {
            return Class.forName("com.car2go.mock.MocksRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * @return {@code true} if given URL is in registry. {@code false} if it is not.
     */
    public boolean isInRegistry(String url) {
        if (isMockedMethod == null) {
            return false;
        }

        try {
            return (boolean) isMockedMethod.invoke(generatedMocksRegistryClass, url);
        } catch (Exception e) {
            return false;
        }
    }

}
