package com.thomsonreuters.grc.fsp.ums.service.ui.util;

import com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.service.ui.util.Mapper;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Test class for group mapping
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 29/08/12
 */
public class TestGroupMapping {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private MapperFacade mapperFacade = new Mapper();

    @Test
    public void canLoadMapper() {
        assertNotNull("Cannot load Mapper", mapperFacade);
    }

    @Test
    public void canMapGroupDTO() {
        Group group = new Group();
        group.setId("1");
        group.setName("group name");

        GroupResponse groupDTO = mapperFacade.map(group, GroupResponse.class);

        assertNotNull(groupDTO.getId());
        assertNotNull(groupDTO.getName());
        assertFalse(groupDTO.getHasChildren());

        group.setLeaf(false);

        groupDTO = mapperFacade.map(group, GroupResponse.class);
        assertTrue(groupDTO.getHasChildren());
    }

    @Test
    public void canMapChildren() {
        Group group = new Group();

        List<Group> children = new ArrayList<>();
        final String ID = "1";
        final String NAME = "child group";

        Group childGroup = new Group();
        childGroup.setName(NAME);
        childGroup.setId(ID);
        children.add(childGroup);
        group.setChildren(children);

        GroupResponse groupDTO = mapperFacade.map(group, GroupResponse.class);
        org.springframework.util.Assert.notEmpty(groupDTO.getChildren());

        for (GroupResponse mappedGroup: groupDTO.getChildren()) {
            logger.debug("{}", ToStringBuilder.reflectionToString(mappedGroup));

            assertSame(ID,mappedGroup.getId());
            assertSame(NAME, mappedGroup.getName());
        }

    }
}
