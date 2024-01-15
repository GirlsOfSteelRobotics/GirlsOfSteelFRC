
import { Shape } from "./shape";

export function getClampedColor(inSpeed: number, min: number, max: number) : string {
  let speed = inSpeed;

  if (Number.isNaN(speed)) {
    speed = 0;
  }
  if (speed > max) {
    speed = max;
  } else if (speed < min) {
    speed = min;
  }

  const spread = max - min;

  const percent = (speed - min) / spread;
  const hue = percent * 120; // Sweep lower third of the color wheel for

  return `hsl(${ hue },100%,50%)`;
}
  
  
export function getMotorColor(speed: number) : string {
  return getClampedColor(speed, -1, 1);
}

export function getMotorColorWithDefault(speed: number, defaultColor: string | undefined) : string {
  if (defaultColor !== undefined && (isNaN(speed) || Math.abs(speed) < .005)) {
    return defaultColor;
  }
  return getMotorColor(speed);
}

export function setShapesMotorColor(shape: Shape, motorSpeed: number) {
  const color = getMotorColorWithDefault(motorSpeed, shape.defaultFillColor);
  shape.fillColor = color;
}

export function setShapesMotorColorStroke(shape: Shape, motorSpeed: number) {
  const color = getMotorColorWithDefault(motorSpeed, shape.defaultStrokeColor);
  shape.strokeColor = color;
}

export function setShapesBooleanColor(shape : Shape, state: boolean, trueColor: string, falseColor: string) {
  if (state) {
    shape.fillColor = trueColor;
  } else {
    shape.fillColor = falseColor;
  }
}

  
  