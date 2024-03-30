# GOS Arduino "Joystick" LED driver
This project contains the Arduino driver that can act as a joystick, read inputs from an FRC robot, and drive a 5v ws2812 light strip.

The arduino gets treated as a generic HID controller that the driver station can recognize. Data is communicated from the rio / simulator to the arduino by setting a rumble power. The arduino can then use this 16bit signal to draw custom LED patterns.


## Required Arduino Board Url's

```
https://raw.githubusercontent.com/dmadison/ArduinoXInput_Boards/master/package_dmadison_xinput_index.json
https://raw.githubusercontent.com/sparkfun/Arduino_Boards/main/IDE_Board_Manager/package_sparkfun_index.json
```

## Arduino IDE setup
The board we use is the `SparkFun Pro Micro w/ XInput`. Be sure to select the correct processor speed of `ATMega 32U4 (5V 16Mhz)