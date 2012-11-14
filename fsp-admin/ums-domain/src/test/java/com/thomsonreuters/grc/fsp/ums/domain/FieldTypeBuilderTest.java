package com.thomsonreuters.grc.fsp.ums.domain;

import com.thomsonreuters.grc.fsp.common.base.type.core.HtmlFieldType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class FieldTypeBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldTypeBuilderTest.class);

    @Test
    public void canCreateFieldTypeBuilder() {

        FieldType.FieldTypeBuilder fieldTypeBuilder = new FieldType.FieldTypeBuilder();
        FieldType fieldType = fieldTypeBuilder
                .withLabel("fieldTypeLabel")
                .withRequired(true)
                .withRegExp("regularExp")
                .withHtmlFieldType(HtmlFieldType.DATE)
                .build();

        LOGGER.debug("this is the Field Type {}", fieldType);
    }
}
