package de.uol.swp.common.game;

import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BlockDTOTest {

        @Test
        void constructorTest() {
            int[][] inputImageInfo = {{1, 0}, {2, 1}, {3, 2}};
            BlockDTO blockDTO = new BlockDTO(inputImageInfo);

            int[] expectedBlockImages = {1, 2, 3};
            CardinalDirection[] expectedBlockImagesDirection = {
                    CardinalDirection.North, CardinalDirection.East, CardinalDirection.South
            };

            assertArrayEquals(expectedBlockImages, blockDTO.getBlockImages());
            assertArrayEquals(expectedBlockImagesDirection, blockDTO.getBlockImagesDirection());
        }

        @Test
        void getBlockImagesTest() {
            int[][] inputImageInfo = {{1, 0}, {2, 1}, {3, 2}};
            BlockDTO blockDTO = new BlockDTO(inputImageInfo);

            int[] expectedBlockImages = {1, 2, 3};
            assertArrayEquals(expectedBlockImages, blockDTO.getBlockImages());
        }

        @Test
        void getBlockImagesDirectionTest() {
            int[][] inputImageInfo = {{1, 0}, {2, 1}, {3, 2}};
            BlockDTO blockDTO = new BlockDTO(inputImageInfo);

            CardinalDirection[] expectedBlockImagesDirection = {
                    CardinalDirection.North, CardinalDirection.East, CardinalDirection.South
            };
            assertArrayEquals(expectedBlockImagesDirection, blockDTO.getBlockImagesDirection());
        }
    }
