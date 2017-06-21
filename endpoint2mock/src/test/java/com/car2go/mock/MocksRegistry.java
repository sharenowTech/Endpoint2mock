package com.car2go.mock;

import java.util.HashSet;
import java.util.Set;

/**
 * Example of generated mocks registry. Used only for tests.
 */
@SuppressWarnings("unchecked")
public class MocksRegistry {

    private static final Set registry = new HashSet();

    public static void setRegistry(Set<String> registry) {
        MocksRegistry.registry.clear();
        MocksRegistry.registry.addAll(registry);
    }

    public static Set getMockedEndpoints() {
        return registry;
    }

}
