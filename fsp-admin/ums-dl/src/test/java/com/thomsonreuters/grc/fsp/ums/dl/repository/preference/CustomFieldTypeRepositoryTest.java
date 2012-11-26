/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;


import com.thomsonreuters.grc.fsp.common.base.type.core.HtmlFieldType;
import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.preference.CustomFieldType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Test State Label Repository to test CRUD
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 31 August 2012
 */
@Transactional
public class CustomFieldTypeRepositoryTest extends BaseDataTest {


    /**
     * Secondary Field Type Repo
     */
    @Autowired
    private CustomFieldTypeRepository customFieldTypeRepository;


    /**
     * Can Load State Label
     */
    @Test
    public void canLoadCustomFieldTypeByLabel() {

        List<CustomFieldType> secondaryFieldTypes =
                customFieldTypeRepository.findByLabel("Deal Id");
        Assert.assertNotNull(secondaryFieldTypes);

        CustomFieldType customFieldType = secondaryFieldTypes.get(0);
        Assert.assertNotNull(customFieldType);
    }

    /**
     * Can save State Label
     */
    @Test
    public void canSaveCustomField() {
        List<CustomFieldType> customFieldTypes = new ArrayList<>();

        CustomFieldType customFieldType = (CustomFieldType) new CustomFieldType.Builder()
                .withRequired(Boolean.FALSE)
                .withLabel("Deal Id")
                .withHtmlFieldType(HtmlFieldType.TEXT)
                .withRegExp("")
                .withGeneratedId()
                .build();
        customFieldTypes.add(customFieldType);

        customFieldType = (CustomFieldType) new CustomFieldType.Builder()
                .withRequired(Boolean.FALSE)
                .withLabel("Deal Id 2")
                .withHtmlFieldType(HtmlFieldType.TEXT)
                .withRegExp("")
                .withGeneratedId()
                .build();
        customFieldTypes.add(customFieldType);

        customFieldType = (CustomFieldType) new CustomFieldType.Builder()
                .withRequired(Boolean.TRUE)
                .withLabel("Custom Field 1")
                .withHtmlFieldType(HtmlFieldType.TEXT)
                .withRegExp("")
                .withGeneratedId()
                .build();
        customFieldTypes.add(customFieldType);

        customFieldType = (CustomFieldType) new CustomFieldType.Builder()
                .withRequired(Boolean.FALSE)
                .withLabel("Custom Field 2")
                .withHtmlFieldType(HtmlFieldType.TEXT)
                .withRegExp("")
                .withGeneratedId()
                .build();
        customFieldTypes.add(customFieldType);

        customFieldTypeRepository.save(customFieldTypes);
    }
}
