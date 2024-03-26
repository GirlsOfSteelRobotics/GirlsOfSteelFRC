#include <FastLED.h>
#include <XInput.h>

 #define NUM_LEDS 16
 #define LED_PIN  6

 #define HAS_NOTE_BITMASK       1 << 3
 #define SEES_APRILTAGS_BITMASK 1 << 4

 CRGB leds[NUM_LEDS];

void setup() {
  delay(2000);

  XInput.begin();

  FastLED.addLeds<WS2812B, LED_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setBrightness(50);
}

void loop() {
  // clear all pixel data
  FastLED.clear();

  uint32_t data = XInput.getRumble();
  // ledTest();
  // bitTest(data);
  writeRobotPatterns(data);

  // Finally, show them
  FastLED.show();
}

void writeRobotPatterns(uint32_t data) {
  bool has_note = checkBit(data, HAS_NOTE_BITMASK);
  bool sees_apriltags = checkBit(data, SEES_APRILTAGS_BITMASK);

  if (has_note) {
    for (int i = 0; i < 10; ++i) {
      leds[i] = CRGB::Orange;
    }
  }
  
  if (sees_apriltags) {
    for (int i = 10; i < NUM_LEDS; ++i) {
      leds[i] = CRGB::SkyBlue;
    }
  }
}


void bitTest(uint32_t data) {
  for (int i = 0; i < NUM_LEDS; ++i) {
    uint32_t mask = 1 << i;
    if (checkBit(data, mask)) {
      leds[i] = CRGB::Red;
      // strip.setPixelColor(i, strip.Color(255, 0, 0));
    }
  }
}

void ledTest() {
  leds[0] = CRGB::Red;
  leds[1] = CRGB::Green;
  leds[2] = CRGB::Blue;
}

bool checkBit(uint32_t data, uint32_t bitmask) {
  return (data & bitmask) == bitmask;
}