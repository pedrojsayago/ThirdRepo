package com.thomsonreuters.grc.fsp.ums.client.ui;

import com.thomsonreuters.grc.fsp.ums.client.ui.dto.ClientCreationResponse;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.IdentifierRequest;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse;

import java.util.List;

/**
 * Group UI Service Definition
 */
public interface GroupUIService {

    /**
     * Method retrieves the group which the authenticated user can screen on.
     *
     * The returned group will include the immediate children for the specified group identifier.
     * When no group identifier is specified, all the groups which the authenticated user has
     * access to is returned as children of a 'Zero' / dummy parent group.
     *
     *
     * @param identifierRequest - {@link IdentifierRequest}
     * @return - {@link com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse}
     */
    GroupResponse getScreeningGroup(IdentifierRequest identifierRequest);

    /**
     * Retrieves all available Clients.
     *
     * @return - {@link List} of {@link ClientResponse}
     */
    List<ClientResponse> getClients();

    /**
     * Create new client based on provided details.
     *
     * @param clientRequest - the details of the new client
     *
     * @return - the {@link ClientCreationResponse} of the client creation operation including
     * ids of the new objects
     */
    ClientCreationResponse createClient(ClientRequest clientRequest);
}
