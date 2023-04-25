package de.uol.swp.common.user.request;

import de.uol.swp.common.message.AbstractRequestMessage;

public class SubmitCardsRequest extends AbstractRequestMessage {
    private final int[] cardIDs;

    /**
     * @author OZimmermann
     * @see
     * @since 2023-04-25
     */
    public SubmitCardsRequest(int[] cardIDs) {
        this.cardIDs = cardIDs;
    }

    /**
     * @author OZimmermann
     * @see
     * @since 2023-04-25
     */
    public int[] getCardIDs() {
        return cardIDs;
    }

    /**
     * @author OZimmermann
     * @see de.uol.swp.common.message.AbstractRequestMessage#authorizationNeeded()
     * @since 2023-04-25
     */
    @Override
    public boolean authorizationNeeded() {
        return false;
    }
}
