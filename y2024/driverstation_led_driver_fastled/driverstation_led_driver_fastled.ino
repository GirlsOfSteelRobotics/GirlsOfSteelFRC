#include <FastLED.h>
#include <XInput.h>

#define NUM_LEDS 58
#define LED_PIN 6

#define IS_CONNECTED_BITMASK   1 << 0
#define HAS_NOTE_BITMASK       1 << 3
#define SEES_APRILTAGS_BITMASK 1 << 4
#define IN_SHOOTING_RANGE      1 << 5

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
  // bitTest(data);
  writeRobotPatterns(data);

  // Finally, show them
  FastLED.show();
}

void writeRobotPatterns(uint32_t data) {
  bool has_note = checkBit(data, HAS_NOTE_BITMASK);
  bool sees_apriltags = checkBit(data, SEES_APRILTAGS_BITMASK);
  bool in_shooting_range = checkBit(data, IN_SHOOTING_RANGE);
  bool is_connected = checkBit(data, IS_CONNECTED_BITMASK);

  int note_lights_start = 0;
  int note_lights_end = NUM_LEDS - 1;

  int in_shooting_range_start = 0;
  int in_shooting_range_end = NUM_LEDS - 1;

  int sees_april_tag_start = in_shooting_range_end;
  int sees_april_tag_end = sees_april_tag_start + 1;

  if (!is_connected) {
    rainbowWave(50, 10);
    return;
  }

  if (has_note) {
    for (int i = note_lights_start; i < note_lights_end; ++i) {
      leds[i] = CRGB::Orange;
    }
  }
  
  if (in_shooting_range) {
    for (int i = in_shooting_range_start; i < in_shooting_range_end; ++i) {
      leds[i] = CRGB::Green;
    }
  }

  if (sees_apriltags) {
    for (int i = sees_april_tag_start; i < sees_april_tag_end; ++i) {
      leds[i] = CRGB::SkyBlue;
    }
  }
}


void bitTest(uint32_t data) {
  int numLedBits = min(32, NUM_LEDS);
  for (int i = 0; i < numLedBits; ++i) {
    uint32_t mask = 1 << i;
    if (checkBit(data, mask)) {
      leds[i] = CRGB::Red;
    }
  }
  
  for (int i = numLedBits; i < NUM_LEDS; ++i) {
    leds[i] = CRGB::Green;
  }
}

void ledTest() {
  leds[0] = CRGB::Red;
  leds[1] = CRGB::Green;
  leds[2] = CRGB::Blue;
}

void rainbowWave(uint8_t thisSpeed, uint8_t deltaHue) {
  uint8_t thisHue = beat8(thisSpeed, 255); 
  fill_rainbow(leds, NUM_LEDS, thisHue, deltaHue);

}

bool checkBit(uint32_t data, uint32_t bitmask) {
  return (data & bitmask) == bitmask;
}