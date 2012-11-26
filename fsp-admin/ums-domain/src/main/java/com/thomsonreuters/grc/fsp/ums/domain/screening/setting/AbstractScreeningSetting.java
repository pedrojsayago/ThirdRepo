package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;


import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Abstract class for Screening Settings
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "p_screening_setting")
public abstract class AbstractScreeningSetting extends AbstractIdentityObject {

    /**
     * Default Score threshold
     */
    private static final Double DEFAULT_SCORE_THRESHOLD = 70.0;

    /**
     * The minimum score threshold - matches with scores lower than this value are discarded.
     */
    private Double scoreThreshold = DEFAULT_SCORE_THRESHOLD;

    /**
     * Object Hash to be used to load object
     */
    private Integer hash;

    /**
     * Default Constructor passing builder object as a parameter
     */
    protected AbstractScreeningSetting(AbstractScreeningSettingBuilder<? extends
            AbstractScreeningSettingBuilder<?>> builder) {
        super(builder);
        this.hash = builder.hash;
        this.scoreThreshold = builder.scoreThreshold;
    }

    /**
     * Get Score Threshold
     *
     * @return Double score threshold
     */
    public Double getScoreThreshold() {
        return scoreThreshold;
    }

    /**
     * Get Score Threshold
     *
     * @param score
     */
    public void setScoreThreshold(Double score) {
        this.scoreThreshold = score;
    }


    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(scoreThreshold);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractScreeningSetting other = (AbstractScreeningSetting) obj;
        return Objects.equals(this.scoreThreshold, other.scoreThreshold);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Builder for {@link AbstractScreeningSetting}
     */
    public static abstract class AbstractScreeningSettingBuilder<T extends AbstractScreeningSettingBuilder<T>>
            extends AbstractIdentityObjectBuilder<T> {

        private Double scoreThreshold = 70.0;
        private Integer hash;

        public T withScoreThreshold(Double scoreThreshold) {
            this.scoreThreshold = scoreThreshold;
            return self();
        }

        public T withHash(Integer hash) {
            this.hash = hash;
            return self();
        }
    }
}
