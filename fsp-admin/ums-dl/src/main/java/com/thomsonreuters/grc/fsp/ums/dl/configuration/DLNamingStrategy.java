/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.configuration;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Custom Hibernate {@code NamingStrategy} to give all table names a given prefix
 */
public class DLNamingStrategy extends ImprovedNamingStrategy {

    private static final String PREFIX = "p_";

    /**
     * {@inheritDoc}
     */
    @Override
    public String classToTableName(final String className) {
        return PREFIX + super.classToTableName(className);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String collectionTableName(final String ownerEntity, final String ownerEntityTable,
                                      final String associatedEntity, final String associatedEntityTable,
                                      final String propertyName) {
        return PREFIX + super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity,
                associatedEntityTable, propertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String logicalCollectionTableName(final String tableName, final String ownerEntityTable,
                                             final String associatedEntityTable, final String propertyName) {
        return PREFIX + super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable,
                propertyName);
    }
}
