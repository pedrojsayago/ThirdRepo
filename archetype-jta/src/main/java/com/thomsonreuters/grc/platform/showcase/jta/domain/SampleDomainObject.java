package com.thomsonreuters.grc.platform.showcase.jta.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SampleDomainObject {

    @Id
    private String id;

    private String sampleField;

    public SampleDomainObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleField() {
        return sampleField;
    }

    public void setSampleField(String sampleField) {
        this.sampleField = sampleField;
    }

}
