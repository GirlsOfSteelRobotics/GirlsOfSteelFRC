#include <FastLED.h>
#include <XInput.h>

#define NUM_LEDS 50
#define LED_PIN 6

#define IS_CONNECTED_BITMASK   1 << 0

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
  bool is_connected = checkBit(data, IS_CONNECTED_BITMASK);

  if (!is_connected) {
    rainbowWave(50, 10);
    return;
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