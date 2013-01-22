package com.thomsonreuters.grc.platform.showcase.jta.service;

import com.thomsonreuters.grc.platform.showcase.jta.domain.dto.SampleInboundRequest;

public interface MessageProcessingService {

    public void processRequest(SampleInboundRequest request);
}
