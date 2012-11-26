package com.thomsonreuters.grc.fsp.ums.domain;

import com.thomsonreuters.grc.fsp.common.base.type.core.HtmlFieldType;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * Secondary Field Component Type Wrapper, For Secondary Field Component Type its define properties
 * within a
 * Context of secondary field such as Mandatory, Label
 *
 * @since 28/08/2012
 */
@MappedSuperclass
public class FieldType extends AbstractIdentityObject {

    /**
     * Flag to indicate if the component is required within the Field Context
     */
    @Column(columnDefinition = "BIT", length = 1)
    private Boolean required;

    /**
     * Component Label
     */
    @Column(nullable = false)
    private String label;

    /**
     * Html Field Type
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HtmlFieldType htmlFieldType;

    /**
     * Regular expression that any value for this {@link FieldType} must conform to
     */
    private String regExp;

    /**
     * Default Constructor passing builder object as a parameter
     */
    protected FieldType(FieldTypeBuilder builder) {
        super(builder);
        this.required = builder.required;
        this.label = builder.label;
        this.htmlFieldType = builder.htmlFieldType;
        this.regExp = builder.regExp;
    }

    /**
     * Getter for field {@code required}.
     *
     * @return The current value of field {@code required}.
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Setter for field {@code required}.
     *
     * @param required The new value to set for field {@code required}.
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * Getter for field {@code label}.
     *
     * @return The current value of field {@code label}.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter for field {@code label}.
     *
     * @param label The new value to set for field {@code label}.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for field {@code htmlFieldType}.
     *
     * @return The current value of field {@code htmlFieldType}.
     */
    public HtmlFieldType getHtmlFieldType() {
        return htmlFieldType;
    }

    /**
     * Setter for field {@code htmlFieldType}.
     *
     * @param htmlFieldType The new value to set for field {@code htmlFieldType}.
     */
    public void setHtmlFieldType(HtmlFieldType htmlFieldType) {
        this.htmlFieldType = htmlFieldType;
    }

    /**
     * Getter for field {@code regExp}.
     *
     * @return The current value of field {@code regExp}.
     */
    public String getRegExp() {
        return regExp;
    }

    /**
     * Setter for field {@code regExp}.
     *
     * @param regExp The new value to set for field {@code regExp}.
     */
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Builder for {@link FieldTypeBuilder}
     */
    public static class FieldTypeBuilder<T extends AbstractIdentityObjectBuilder> extends AbstractIdentityObjectBuilder {
        private Boolean required;
        private String label;
        private HtmlFieldType htmlFieldType;
        private String regExp;

        public FieldTypeBuilder withRequired(Boolean required) {
            this.required = required;
            return this;
        }

        @Override
        protected AbstractIdentityObjectBuilder self() {
            return null;

        }

        public FieldTypeBuilder withLabel(String label) {
            this.label = label;
            return this;
        }

        public FieldTypeBuilder withHtmlFieldType(HtmlFieldType htmlFieldType) {
            this.htmlFieldType = htmlFieldType;
            return this;
        }

        public FieldTypeBuilder withRegExp(String regExp) {
            this.regExp = regExp;
            return this;
        }

        public FieldType build() {
            return new FieldType(this);
        }


    }
}
