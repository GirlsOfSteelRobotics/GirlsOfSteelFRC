import numpy as np
import cv2


def erode_image(image, iterations):
    if iterations == 0:
        return image

    kernel = np.ones((iterations, iterations), np.uint8)
    image = cv2.erode(image, kernel)

    return image


def dilate_image(image, iterations):
    if iterations == 0:
        return image

    kernel = np.ones((iterations, iterations), np.uint8)
    image = cv2.dilate(image, kernel)

    return image
