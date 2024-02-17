

export class Shape {
  defaultFillColor?: string;
  fillColor?: string;
  defaultStrokeColor?: string;
  strokeColor?: string;
  strokeWidth?: number;

  constructor(fillColor?: string) {
    this.defaultFillColor = fillColor;
    this.fillColor = fillColor;
  }
}