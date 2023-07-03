package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.enums.CardinalDirection;

import org.junit.jupiter.api.Test;

public class BlockDTOTest {

    /**
     * Tests the constructor
     *
     * @result The constructor should return the correct values like blockImages and
     * @see de.uol.swp.common.game.dto.BlockDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-12
     */
    @Test
    public void testConstructorTest() {
        int[][] inputImageInfo = {{1, 0}, {2, 1}, {3, 2}};
        BlockDTO blockDTO = new BlockDTO(inputImageInfo);

        int[] expectedBlockImages = {1, 2, 3};
        CardinalDirection[] expectedBlockImagesDirection = {
            CardinalDirection.North, CardinalDirection.East, CardinalDirection.South
        };

        assertArrayEquals(expectedBlockImages, blockDTO.getBlockImages());
        assertArrayEquals(expectedBlockImagesDirection, blockDTO.getBlockImagesDirection());
    }

    /**
     * Tests the getBlockImages method
     *
     * @author WKempel
     * @result The method should return the correct blockImages
     * @see de.uol.swp.common.game.dto.BlockDTO
     * @since 2023-06-12
     */
    @Test
    public void testGetBlockImagesTest() {
        int[][] inputImageInfo = {{1, 0}, {2, 1}, {3, 2}};
        BlockDTO blockDTO = new BlockDTO(inputImageInfo);

        int[] expectedBlockImages = {1, 2, 3};
        assertArrayEquals(expectedBlockImages, blockDTO.getBlockImages());
    }

    /**
     * Tests the getBlockImagesDirection method
     *
     * @author WKempel
     * @result The method should return the correct blockImagesDirection
     * @see de.uol.swp.common.game.dto.BlockDTO
     * @since 2023-06-12
     */
    @Test
    public void testGetBlockImagesDirectionTest() {
        int[][] inputImageInfo = {{1, 0}, {2, 1}, {3, 2}};
        BlockDTO blockDTO = new BlockDTO(inputImageInfo);

        CardinalDirection[] expectedBlockImagesDirection = {
            CardinalDirection.North, CardinalDirection.East, CardinalDirection.South
        };
        assertArrayEquals(expectedBlockImagesDirection, blockDTO.getBlockImagesDirection());
    }
}
