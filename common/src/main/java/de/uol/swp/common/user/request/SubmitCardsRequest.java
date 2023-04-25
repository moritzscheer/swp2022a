package de.uol.swp.common.user.request;

import de.uol.swp.common.message.AbstractRequestMessage;

public class SubmitCardsRequest extends AbstractRequestMessage {
    private static final long serialVersionUID = -5910486927806761643L;
    private final int[] cardIDs;

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    public SubmitCardsRequest(int[] cardIDs) {
        this.cardIDs = cardIDs;
    }

    /**
     * @author Ole Zimmermann
     * @since 2023-04-25
     */
    public int[] getCardIDs() {
        return cardIDs;
    }

    @Override
    public boolean authorizationNeeded() {
        return true;
    }
}
