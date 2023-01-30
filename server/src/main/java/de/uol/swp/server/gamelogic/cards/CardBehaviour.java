package de.uol.swp.server.gamelogic.cards;

import io.netty.handler.codec.http2.Http2FrameLogger;

/**
 *
 *
 * @author
 * @see
 * @since
 */
abstract class CardBehaviour{

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public int move(){
        //TODO
        return 0;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public Direction turn(){
        //TODO
        return null;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public boolean uTurn(){
        //TODO
        return false;
    }
}
