/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.domain;

import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;
import org.meanbean.test.HashCodeMethodTester;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests getters/setters, equals() & hashCode() implementations on all domain classes
 */
public class DomainJavaBeanTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainJavaBeanTest.class);

    private static final Set<Class<? extends AbstractIdentityObject>> domainClasses = new HashSet<>();

    @BeforeClass
    public static void getDomainClasses() {

        Reflections reflections = new Reflections("com.thomsonreuters.grc.fsp.ums.domain");

        domainClasses.addAll(reflections.getSubTypesOf(AbstractIdentityObject.class));
    }

    @Test
    public void testGettersAndSetters() {

        BeanTester beanTester = new BeanTester();
        beanTester.setIterations(10);

        for (Class<? extends AbstractIdentityObject> clazz : domainClasses) {
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

        for (Class<? extends AbstractIdentityObject> clazz : domainClasses) {
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

        for (Class<? extends AbstractIdentityObject> clazz : domainClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                LOGGER.debug("Testing hashCode for Class {}", clazz.getSimpleName());
                hashCodeMethodTester.testHashCodeMethod(clazz);
            }
        }
    }
}
