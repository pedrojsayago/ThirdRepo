/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Tests for {@link GroupPreferenceRepository}
 */
@Transactional
public class GroupPreferenceRepositoryTest extends BaseDataTest {

    @Autowired
    private GroupPreferenceRepository groupPreferenceRepository;


    @Test
    public void test() {

    }
}
