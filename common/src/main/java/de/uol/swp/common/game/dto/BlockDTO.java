package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.Serializable;

/**
 * Object to transfer the information of a Block
 *
 * <p>This object is used to communicate the current state of game between the server and clients.
 * It contains information about the imagesIds and direction of the images of a block
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-16
 */
public class BlockDTO implements Serializable {

    private final int[] blockImages;
    private final CardinalDirection[] blockImagesDirection;
    /**
     * Constructor
     *
     * @param inputImageInfo Array that stores the information of the images to display a block of the map
     *
     * @author Jann Erik Bruns
     * @since 2023-05-16
     */
    public BlockDTO(int[][] inputImageInfo) {
        blockImages = new int[inputImageInfo.length];
        blockImagesDirection = new CardinalDirection[inputImageInfo.length];
        for (int i = 0; i < inputImageInfo.length; i++) {
            blockImages[i] = inputImageInfo[i][0];
            blockImagesDirection[i] = CardinalDirection.values()[(inputImageInfo[i][1] + 4) % 4];
        }
    }
    /**
     * Gets the images of the block
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-16
     */
    public int[] getBlockImages() {
        return blockImages;
    }
    /**
     * Gets the direction of the block's images
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-16
     */
    public CardinalDirection[] getBlockImagesDirection() {
        return blockImagesDirection;
    }
}
