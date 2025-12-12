/*
 * Name: ADVITA BATHOLE
 * Email: ABATHOLE@UCSD.EDU
 * PID: A19237518
 * Sources used: CSE 11 NOTES
 *
 * This file contains an ImageEditor class that implements a simple
 * image editor allowing users to rotate, downsample, and patch images.
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the main class for the image editor.
 * It supports loading, saving, rotating, downsampling, and patching images.
 */
public class ImageEditor {

    /* Constants */
    private static final String PNG_FORMAT = "png";
    private static final String NON_RGB_WARNING =
            "Warning: we do not support the image you provided.\n"
                    + "Please change another image and try again.";
    private static final String RGB_TEMPLATE = "(%3d, %3d, %3d) ";

    private static final int BLUE_BYTE_SHIFT = 0;
    private static final int GREEN_BYTE_SHIFT = 8;
    private static final int RED_BYTE_SHIFT = 16;
    private static final int ALPHA_BYTE_SHIFT = 24;

    private static final int BLUE_BYTE_MASK = 0xff << BLUE_BYTE_SHIFT;
    private static final int GREEN_BYTE_MASK = 0xff << GREEN_BYTE_SHIFT;
    private static final int RED_BYTE_MASK = 0xff << RED_BYTE_SHIFT;
    private static final int ALPHA_BYTE_MASK = ~(0xff << ALPHA_BYTE_SHIFT);

    // New constants for rotation
    private static final int RIGHT_ANGLE = 90;
    private static final int FULL_ROTATION = 360;

    /* Static variable */
    static int[][] image;

    /**
     * Open an image from disk and return a 2D array of its pixels.
     *
     * @param pathname the path and name to the file
     * @return 2D array storing the RGB value of each pixel
     * @throws IOException when file cannot be found or read
     */
    public static int[][] open(String pathname) throws IOException {
        BufferedImage data = ImageIO.read(new File(pathname));
        if (data.getType() != BufferedImage.TYPE_3BYTE_BGR
                && data.getType() != BufferedImage.TYPE_4BYTE_ABGR) {
            System.err.println(NON_RGB_WARNING);
        }

        int[][] array = new int[data.getHeight()][data.getWidth()];
        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                array[row][column] = data.getRGB(column, row) & ALPHA_BYTE_MASK;
            }
        }
        return array;
    }

    /**
     * Load an image from disk into the 'image' 2D array.
     *
     * @param pathname path and name to the file
     * @throws IOException when file cannot be found or read
     */
    public static void load(String pathname) throws IOException {
        image = open(pathname);
    }

    /**
     * Save the 2D image array to a PNG file on the disk.
     *
     * @param pathname path and name for the file
     * @throws IOException when file cannot be written
     */
    public static void save(String pathname) throws IOException {
        BufferedImage data = new BufferedImage(
                image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                data.setRGB(column, row, image[row][column]);
            }
        }
        ImageIO.write(data, PNG_FORMAT, new File(pathname));
    }

    /**
     * Unpack red byte from a packed RGB int.
     *
     * @param rgb RGB packed int
     * @return red value (0–255)
     */
    private static int unpackRedByte(int rgb) {
        return (rgb & RED_BYTE_MASK) >> RED_BYTE_SHIFT;
    }

    /**
     * Unpack green byte from a packed RGB int.
     *
     * @param rgb RGB packed int
     * @return green value (0–255)
     */
    private static int unpackGreenByte(int rgb) {
        return (rgb & GREEN_BYTE_MASK) >> GREEN_BYTE_SHIFT;
    }

    /**
     * Unpack blue byte from a packed RGB int.
     *
     * @param rgb RGB packed int
     * @return blue value (0–255)
     */
    private static int unpackBlueByte(int rgb) {
        return (rgb & BLUE_BYTE_MASK) >> BLUE_BYTE_SHIFT;
    }

    /**
     * Pack RGB bytes back into an int.
     *
     * @param red   red component (0–255)
     * @param green green component (0–255)
     * @param blue  blue component (0–255)
     * @return packed int representing a pixel
     */
    private static int packInt(int red, int green, int blue) {
        return (red << RED_BYTE_SHIFT)
                + (green << GREEN_BYTE_SHIFT)
                + (blue << BLUE_BYTE_SHIFT);
    }

    /**
     * Print the current image 2D array in (red, green, blue) format.
     */
    public static void printImage() {
        for (int[] row : image) {
            for (int pixel : row) {
                System.out.printf(
                        RGB_TEMPLATE,
                        unpackRedByte(pixel),
                        unpackGreenByte(pixel),
                        unpackBlueByte(pixel));
            }
            System.out.println();
        }
    }

    /**
     * Rotates the current image clockwise by the specified degree.
     * Only supports multiples of 90 degrees.
     *
     * @param degree degree of rotation, must be a multiple of 90
     */
    public static void rotate(int degree) {
        if (degree < 0 || degree % RIGHT_ANGLE != 0) {
            return;
        }

        int times = (degree % FULL_ROTATION) / RIGHT_ANGLE;

        for (int t = 0; t < times; t++) {
            int oldHeight = image.length;
            int oldWidth = image[0].length;
            int[][] rotated = new int[oldWidth][oldHeight];

            for (int i = 0; i < oldHeight; i++) {
                for (int j = 0; j < oldWidth; j++) {
                    rotated[j][oldHeight - 1 - i] = image[i][j];
                }
            }
            image = rotated;
        }
    }

    /**
     * Averages pixel blocks to reduce image size.
     *
     * @param heightScale number of vertical pixels to combine
     * @param widthScale  number of horizontal pixels to combine
     */
    public static void downSample(int heightScale, int widthScale) {
        int oldHeight = image.length;
        int oldWidth = image[0].length;

        if (heightScale < 1 || widthScale < 1) {
            return;
        }
        if (oldHeight % heightScale != 0 || oldWidth % widthScale != 0) {
            return;
        }

        int newHeight = oldHeight / heightScale;
        int newWidth = oldWidth / widthScale;
        int[][] downsampled = new int[newHeight][newWidth];

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                int redTotal = 0;
                int greenTotal = 0;
                int blueTotal = 0;

                for (int r = i * heightScale; r < (i + 1) * heightScale; r++) {
                    for (int c = j * widthScale; c < (j + 1) * widthScale; c++) {
                        int pixel = image[r][c];
                        redTotal += unpackRedByte(pixel);
                        greenTotal += unpackGreenByte(pixel);
                        blueTotal += unpackBlueByte(pixel);
                    }
                }

                int numPixels = heightScale * widthScale;
                int redAvg = redTotal / numPixels;
                int greenAvg = greenTotal / numPixels;
                int blueAvg = blueTotal / numPixels;

                downsampled[i][j] = packInt(redAvg, greenAvg, blueAvg);
            }
        }
        image = downsampled;
    }

    /**
     * Patches a smaller image onto the current image.
     * Ignores pixels matching a specified transparent color.
     *
     * @param startRow         starting row index
     * @param startColumn      starting column index
     * @param patchImage       2D array of the patch image
     * @param transparentRed   red value of transparent color
     * @param transparentGreen green value of transparent color
     * @param transparentBlue  blue value of transparent color
     * @return number of pixels patched
     */
    public static int patch(
            int startRow,
            int startColumn,
            int[][] patchImage,
            int transparentRed,
            int transparentGreen,
            int transparentBlue) {

        if (startRow < 0 || startColumn < 0) {
            return 0;
        }
        if (startRow + patchImage.length > image.length) {
            return 0;
        }
        if (startColumn + patchImage[0].length > image[0].length) {
            return 0;
        }

        int patchedCount = 0;

        for (int i = 0; i < patchImage.length; i++) {
            for (int j = 0; j < patchImage[i].length; j++) {
                int pixel = patchImage[i][j];
                int red = unpackRedByte(pixel);
                int green = unpackGreenByte(pixel);
                int blue = unpackBlueByte(pixel);

                if (!(red == transparentRed
                        && green == transparentGreen
                        && blue == transparentBlue)) {
                    image[startRow + i][startColumn + j] = pixel;
                    patchedCount++;
                }
            }
        }
        return patchedCount;
    }
}
