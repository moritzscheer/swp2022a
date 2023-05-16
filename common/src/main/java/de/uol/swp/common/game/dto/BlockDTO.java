package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.Serializable;


/**
 * Object to transfer the information of a Block
 *
 * This object is used to communicate the current state of game between the server and
 * clients. It contains information about the imagesIds and direction of the images of a block
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-16
 */
public class BlockDTO implements Serializable {

    private final int[] blockImages;
    private final CardinalDirection[] blockImagesDirection;

    public BlockDTO(int[] blockImages, CardinalDirection[] blockImagesDirection){

        this.blockImages = blockImages;
        this.blockImagesDirection = blockImagesDirection;
    }

    public int[] getBlockImages() {
        return blockImages;
    }

    public CardinalDirection[] getBlockImagesDirection() {
        return blockImagesDirection;
    }
}
