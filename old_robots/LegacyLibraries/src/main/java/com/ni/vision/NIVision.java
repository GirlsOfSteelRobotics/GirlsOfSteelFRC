package com.ni.vision;

@SuppressWarnings("PMD")
public class NIVision {


    public enum ColorMode {
        HSL,
    }

    public static class Image {

    }

    public enum ImageType {
        IMAGE_HSL, IMAGE_U8;
    }

    public static class Range {
        public Range(int i, int i1) {
            throw new UnsupportedOperationException();
        }
    }

    public static void imaqColorThreshold(Image outImage, Image inImage, int i, ColorMode colorMode, Range hue, Range sat, Range lum) {
        throw new UnsupportedOperationException();
    }

    public static class Rect {
        public Rect(int i, int i1, int i2, int i3) {
            throw new UnsupportedOperationException();
        }
    }

    public static Image imaqCreateImage(ImageType imageU8, int i) {
        throw new UnsupportedOperationException();
    }
}
