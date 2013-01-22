package com.thomsonreuters.grc.fsp.ums.domain;

/**
 * Enumeration of {@code AuditEvent} Action Type, this defines the type of operations in the
 * system that can result in an audit events being raised.
 */
public enum AuditEventActionType {
    /**
     * raised against a Case when it is first created
     */
    NEW_CASE(ChangeCaseLifecycleStatePayload.class),

    /**
     * raised against a Case when it is assigned to a user
     */
    ASSIGN_CASE(ChangeCaseLifecycleStatePayload.class),

    /**
     * raised against a Match when it is first created
     */
    NEW_MATCH(ChangeCaseLifecycleStatePayload.class),

    /**
     * raised when a note is added to an object in the system
     */
    NEW_NOTE(ChangeCaseLifecycleStatePayload.class),

    /**
     * raised when the Group that owns a Case is changed
     */
    CHANGE_CASE_OWNER(ChangeCaseLifecycleStatePayload.class),

    /**
     * raised when the Case Lifecycle state has changed
     */
    CHANGE_CASE_LIFECYCLE_STATE(ChangeCaseLifecycleStatePayload.class);

    private Class<? extends GenericPayload> payloadClass;

    AuditEventActionType(Class<? extends GenericPayload> payload) {
        this.payloadClass = (Class<? extends ChangeCaseLifecycleStatePayload>) payload;
    }
}