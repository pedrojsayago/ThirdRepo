package com.thomsonreuters.grc.platform.showcase.jta.domain.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SampleOutboundResponse {

    private String responseField;

    public SampleOutboundResponse() {
    }

    @XmlElement
    public String getResponseField() {
        return responseField;
    }

    public void setResponseField(String responseField) {
        this.responseField = responseField;
    }

}
