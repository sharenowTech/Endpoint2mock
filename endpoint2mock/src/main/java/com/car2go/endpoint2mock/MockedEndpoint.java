/*
 * Copyright (c) 2017 Daimler AG / car2go Group GmbH
 *
 * All rights reserved
 */

package com.car2go.endpoint2mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Put this annotation on top of the Retrofit methods (the ones which are annoteted with GET, POST,
 * DELETE or PUT annotation) and Endpoint2mock will add it to the list of mocked endpoints.
 */
@Target(ElementType.METHOD)
public @interface MockedEndpoint {
}
