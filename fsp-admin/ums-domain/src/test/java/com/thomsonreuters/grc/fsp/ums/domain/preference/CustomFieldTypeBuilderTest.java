package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.core.HtmlFieldType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class CustomFieldTypeBuilderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFieldTypeBuilderTest.class);

    @Test
    public void canCreateCustomFieldTypeBuilder() {

        CustomFieldType customFieldType = (CustomFieldType) new CustomFieldType.Builder()
                .withLabel("newLabel")
                .withRegExp("regExp")
                .withHtmlFieldType(HtmlFieldType.DATE)
                .withRequired(true)
                .build();

        LOGGER.debug("this is the Custom Field Type {}", customFieldType);
    }
}
