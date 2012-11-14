/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.client.ui;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;
import org.meanbean.test.HashCodeMethodTester;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests getters/setters, equals() & hashCode() implementations on all DTO classes
 */
public class DTOJavaBeanTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DTOJavaBeanTest.class);

    private static final Set<Class<?>> dtoClasses = new HashSet<>();

    @BeforeClass
    public static void getDomainClasses() {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forPackage("com.thomsonreuters.grc.fsp.ums.client.ui.dto")));

        dtoClasses.addAll(reflections.getSubTypesOf(Object.class));
    }

    @Test
    public void testGettersAndSetters() {

        BeanTester beanTester = new BeanTester();
        beanTester.setIterations(10);

        for (Class<?> clazz : dtoClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                LOGGER.debug("Testing getters/setters for Class {}", clazz.getSimpleName());
                beanTester.testBean(clazz);
            }
        }
    }

    @Test
    @Ignore
    public void testEquals() {
        EqualsMethodTester equalsMethodTester = new EqualsMethodTester();

        for (Class<?> clazz : dtoClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                LOGGER.debug("Testing equals for Class {}", clazz.getSimpleName());
                equalsMethodTester.testEqualsMethod(clazz);
            }
        }
    }

    @Test
    @Ignore
    public void testHashCode() {
        HashCodeMethodTester hashCodeMethodTester = new HashCodeMethodTester();

        for (Class<?> clazz : dtoClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                LOGGER.debug("Testing hashCode for Class {}", clazz.getSimpleName());
                hashCodeMethodTester.testHashCodeMethod(clazz);
            }
        }
    }
}
