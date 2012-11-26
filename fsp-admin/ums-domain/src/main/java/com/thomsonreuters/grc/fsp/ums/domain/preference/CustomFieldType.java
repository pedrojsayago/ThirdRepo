package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.ums.domain.FieldType;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * Custom Field Type, specialisation of FieldType
 *
 * @since 28/08/2012
 */
@Entity
@Audited
public class CustomFieldType extends FieldType {

    /**
     * Default Constructor passing builder object as a parameter
     */
    private CustomFieldType(FieldTypeBuilder fieldTypeBuilder) {
        super(fieldTypeBuilder);
    }

    /**
     * Builder for {@link CustomFieldType}
     */
    public static class Builder extends FieldTypeBuilder {

        @Override
        public CustomFieldType build() {
            return new CustomFieldType(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
