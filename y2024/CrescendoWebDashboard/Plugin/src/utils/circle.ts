

import { Shape } from "./shape";
import { Rectangle } from "./rectangle";

export class Circle extends Shape {
  centerX: number;
  centerY: number;
  radius: number;
  rotation: number;
  rotationPoint?: [number, number];

  constructor(x: number, y: number, radius: number, fillColor?: string) {
    super(fillColor);
    this.centerX = x;
    this.centerY = y;
    this.radius = radius;
    this.fillColor = fillColor;
    this.rotation = 0.0;
  }

  rotateAroundShapeEnd(degrees: number, shape: Rectangle) {
    this.rotationPoint = [shape.x + shape.width, shape.y + shape.height];
    this.rotation = (-degrees - 90) * Math.PI / 180.0;
  }

  render(ctx: CanvasRenderingContext2D) {
    ctx.save();

    ctx.beginPath();

    if (this.rotationPoint !== undefined) {
      ctx.translate(this.rotationPoint[0], this.rotationPoint[1]);
      ctx.rotate(this.rotation);
      ctx.translate(-this.rotationPoint[0], -this.rotationPoint[1]);
    }

    ctx.arc(this.centerX, this.centerY, this.radius, 0, 2 * Math.PI, false);
    ctx.fillStyle = this.fillColor!;
    ctx.fill();

    ctx.restore();
  }
}