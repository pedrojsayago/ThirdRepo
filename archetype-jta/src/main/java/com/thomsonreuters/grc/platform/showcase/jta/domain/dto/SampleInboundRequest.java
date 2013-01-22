package com.thomsonreuters.grc.platform.showcase.jta.domain.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SampleInboundRequest {

    private String requestField;

    public SampleInboundRequest() {
    }

    public SampleInboundRequest(String requestField) {
        this.requestField = requestField;
    }

    @XmlElement
    public String getRequestField() {
        return requestField;
    }

    public void setRequestField(String requestField) {
        this.requestField = requestField;
    }

}
