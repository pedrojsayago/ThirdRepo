package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import com.thomsonreuters.grc.fsp.common.base.type.core.DataSet;
import com.thomsonreuters.grc.fsp.common.base.type.core.DataType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Watchlist Screen Settings
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 23/08/12
 */
@Audited
@Entity
public class WatchlistScreeningSetting extends AbstractScreeningSetting {

    /**
     * Screening Setting ID string
     */
    private static final String SCREENING_SETTING_ID = "screening_setting_id";

    /**
     * Source Id String
     */
    private static final String SOURCE_ID = "source_id";

    /**
     * Set of Provider Source Type Identifier, to screen full source ids under the provider types
     */
    @CollectionTable(name = "p_screening_setting_provider_type",
            joinColumns = {@JoinColumn(name = SCREENING_SETTING_ID)},
            uniqueConstraints = {@UniqueConstraint(
                    columnNames = {SCREENING_SETTING_ID, "provider_source_type_id"})})
    @Column(name = "provider_source_type_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> sourceTypeIds = new HashSet<>();

    /**
     * Source Ids to exclude while screening
     */
    @CollectionTable(name = "p_screening_setting_source_exclusion_list",
            joinColumns = {@JoinColumn(name = SCREENING_SETTING_ID)},
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {SCREENING_SETTING_ID, SOURCE_ID})})
    @Column(name = "source_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> excludedSourceIds = new HashSet<>();

    /**
     * Source Ids to exclude while screening
     * Temporary initialising with b_c_1,b_f_1,b_v_1
     * <p/>
     * todo Temporary Source IDs to be removed when namemamtcher implement exclusion and provider
     * source type
     */
    @CollectionTable(name = "p_screening_setting_source",
            joinColumns = {@JoinColumn(name = SCREENING_SETTING_ID)},
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {SCREENING_SETTING_ID, SOURCE_ID})})
    @Column(name = "source_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> sourceIds = new HashSet<>();

    /**
     * Enables/Disables Low Quality AKAs in WC data
     */
    @Column(columnDefinition = "BIT", length = 1, nullable = false)
    private Boolean lowQualityAkaEnabled = false;

    /**
     * Data set GRC or WorldCheck
     */
    @Enumerated(EnumType.STRING)
    private DataSet dataSet = DataSet.GRC;

    /**
     * For WorldCheck DataSet, What type of Data, Premium , PremiumPLUS or Standard
     */
    @Enumerated(EnumType.STRING)
    private DataType dataType = DataType.STANDARD;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private WatchlistScreeningSetting(Builder builder) {
        super(builder);
        this.sourceTypeIds = builder.sourceTypeIds;
        this.excludedSourceIds = builder.excludedSourceIds;
        this.sourceIds = builder.sourceIds;
        this.lowQualityAkaEnabled = builder.lowQualityAkaEnabled;
        this.dataSet = builder.dataSet;
        this.dataType = builder.dataType;
    }

    /**
     * Enables/Disables Low Quality AKAs in WC data
     *
     * @return the lowQualityAkaEnabled
     */
    public Boolean getLowQualityAkaEnabled() {
        return lowQualityAkaEnabled;
    }

    /**
     * Enables/Disables Low Quality AKAs in WC data
     *
     * @param lowQualityAkaEnabled the lowQualityAkaEnabled to set
     */
    public void setLowQualityAkaEnabled(Boolean lowQualityAkaEnabled) {
        this.lowQualityAkaEnabled = lowQualityAkaEnabled;
    }

    /**
     * Get Provider Source Types Ids
     * such as
     * t_trwc_1 for World-Check Watchlist,
     * t_trwc_2 for World-Check Scantions
     *
     * @return set of Provide Source Type Ids
     */
    public Set<String> getSourceTypeIds() {
        return sourceTypeIds;
    }

    /**
     * Set provider source type ids
     *
     * @param sourceTypeIds provider source type ids
     */
    public void setSourceTypeIds(Set<String> sourceTypeIds) {
        this.sourceTypeIds = sourceTypeIds;
    }

    /**
     * Get exclusion list of Source Ids to be excluded from the Screening
     *
     * @return Set of Source Ids
     */
    public Set<String> getExcludedSourceIds() {
        return excludedSourceIds;
    }

    /**
     * Setter for field {@code sourceIds}.
     *
     * @param sourceIds The new value to set for field {@code sourceIds}.
     */
    public void setSourceIds(Set<String> sourceIds) {
        this.sourceIds = sourceIds;
    }

    /**
     * Get Set of Source Ids
     *
     * @return Set of Source Ids
     */
    public Set<String> getSourceIds() {
        return sourceIds;
    }

    /**
     * Setter for field {@code excludedSourceIds}.
     *
     * @param excludedSourceIds The new value to set for field {@code excludedSourceIds}.
     */
    public void setExcludedSourceIds(Set<String> excludedSourceIds) {
        this.excludedSourceIds = excludedSourceIds;
    }

    /**
     * Get Data Set, whether its GRC / WorldCheck
     *
     * @return DateSet
     */
    public DataSet getDataSet() {
        return dataSet;
    }

    /**
     * Set Data set
     *
     * @param dataSet DataSet such GRC / WorldCheck
     */
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Get Data Type , such as Standard / Premium or Premium +
     *
     * @return DateType
     */
    public DataType getDataType() {
        return dataType;
    }


    /**
     * Set Data Type , such as Standard / Premium or Premium +
     *
     * @param dataType DataType to be set
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }


    /**
     * Produce the same hashcode for this object even after different execution
     * For Enum's not using Enum.hashCode, rather using enum's name hashcode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        /**
         * Enums.hashCode produce different hash in different executions
         * Using Enum's name (string) to get same Hash-Code for all the executions
         */
        return Objects.hash(super.hashCode(), sourceTypeIds, excludedSourceIds, sourceIds,
                lowQualityAkaEnabled, dataSet.name(), dataType.name());
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
        final WatchlistScreeningSetting other = (WatchlistScreeningSetting) obj;
        return super.equals(obj) && Objects.equals(this.sourceTypeIds, other.sourceTypeIds) && Objects
                .equals(this.excludedSourceIds, other.excludedSourceIds) && Objects
                .equals(this.sourceIds, other.sourceIds) && Objects
                .equals(this.lowQualityAkaEnabled, other.lowQualityAkaEnabled) && Objects
                .equals(this.dataSet, other.dataSet) && Objects.equals(this.dataType, other.dataType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Builder for {@link WatchlistScreeningSetting}
     */
    public static class Builder extends AbstractScreeningSettingBuilder<Builder> {

        private Set<String> sourceTypeIds = new HashSet<>();
        private Set<String> excludedSourceIds = new HashSet<>();
        private Set<String> sourceIds = new HashSet<>();
        private Boolean lowQualityAkaEnabled = false;
        private DataSet dataSet = DataSet.GRC;
        private DataType dataType = DataType.STANDARD;


        public Builder withSourceTypeIds(Set<String> sourceTypeIds) {
            this.sourceTypeIds = sourceTypeIds;
            return this;
        }

        public Builder withExcludedSourceIds(Set<String> excludedSourceIds) {
            this.excludedSourceIds = excludedSourceIds;
            return this;
        }

        public Builder withSourceIds(Set<String> sourceIds) {
            this.sourceIds = sourceIds;
            return this;
        }

        public Builder withLowQualityAkaEnabled(Boolean lowQualityAkaEnabled) {
            this.lowQualityAkaEnabled = lowQualityAkaEnabled;
            return this;
        }

        public Builder withDataSet(DataSet dataSet) {
            this.dataSet = dataSet;
            return this;
        }

        public Builder withDataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public WatchlistScreeningSetting build() {
            return new WatchlistScreeningSetting(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
