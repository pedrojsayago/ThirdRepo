package com.thomsonreuters.grc.platform.showcase.jta.service;

import com.thomsonreuters.grc.platform.showcase.jta.domain.dto.SampleOutboundResponse;

public interface DownstreamService {

    public void processFurtherMessage(SampleOutboundResponse response);

}
