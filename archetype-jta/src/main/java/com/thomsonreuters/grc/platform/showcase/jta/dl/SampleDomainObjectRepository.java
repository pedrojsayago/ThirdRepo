package com.thomsonreuters.grc.platform.showcase.jta.dl;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thomsonreuters.grc.platform.showcase.jta.domain.SampleDomainObject;

public interface SampleDomainObjectRepository extends JpaRepository<SampleDomainObject, String> {

}
