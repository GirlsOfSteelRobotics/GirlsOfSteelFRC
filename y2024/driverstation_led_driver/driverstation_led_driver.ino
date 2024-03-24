#include <XInput.h>

#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
#include <avr/power.h>
#endif
#define PIN 6
#define NUMPIXELS 16
#define DELAYVAL 50

Adafruit_NeoPixel strip(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

void setup() {
  XInput.begin();

  strip.begin();
}

void loop() {
  strip.clear();

  uint32_t data = XInput.getRumbleLeft();
    populateLights(data);
    strip.show();
    delay(DELAYVAL);
}

void populateLights(uint32_t data) {
  for (int i = 0; i < NUMPIXELS; ++i) {
    uint32_t mask = 1 << i;
    if (checkBit(data, mask)) {
      strip.setPixelColor(i, strip.Color(255, 0, 0));
    }
  }
}


bool checkBit(uint32_t data, uint32_t bitmask) {
  return (data & bitmask) == bitmask;
}

#define BITMASK_HAS_NOTE 1 << 0
#define BITMASK_ARM_AT_ANGLE 1 << 1