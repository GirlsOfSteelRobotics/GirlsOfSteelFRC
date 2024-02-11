
import { Shape } from "./shape";

export class Rectangle extends Shape {
  x: number;
  y: number;
  width: number;
  height: number;
  rotation: number;
  rotationPoint?: [number, number];

  constructor(x: number, y: number, width: number, height: number, fillColor?: string) {
    super(fillColor);
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.rotation = 0.0;
  }

  setRotation(degrees: number) {
    this.rotation = (-degrees - 90) * Math.PI / 180.0;
  }

  rotateAround(degrees: number, x: number, y: number) {
    this.rotationPoint = [x, y];
    this.setRotation(degrees);
  }

  rotateAroundShape(degrees: number, shape: Rectangle) {
    this.rotationPoint = [shape.x, shape.y];
    this.setRotation(degrees);
  }

  rotateAroundShapeEnd(degrees: number, shape: Rectangle) {
    this.rotationPoint = [shape.x + shape.width, shape.y + shape.height];
    this.setRotation(degrees);
  }

  rotateAroundOrigin(degrees: number) {
    this.rotateAround(degrees, this.x, this.y);
  }

  rotateAroundEnd(degrees: number) {
    this.rotateAround(degrees, this.x + this.width, this.y + this.height);
  }

  render(ctx: CanvasRenderingContext2D) {
    ctx.save();
    ctx.beginPath();
    ctx.fillStyle = this.fillColor!;
    ctx.strokeStyle = this.strokeColor!;

    if (this.rotationPoint !== undefined) {
      ctx.translate(this.rotationPoint[0], this.rotationPoint[1]);
      ctx.rotate(this.rotation);
      ctx.translate(-this.rotationPoint[0], -this.rotationPoint[1]);
    } else {
      const rotX = this.x + this.width / 2;
      const rotY = this.y + this.height / 2;
      ctx.translate(rotX, rotY);
      ctx.rotate(this.rotation);
      ctx.translate(-rotX, -rotY);
    }


    ctx.fillRect(this.x, this.y, this.width, this.height);
    if (this.strokeColor !== undefined) {
      ctx.lineWidth = this.strokeWidth!;
      ctx.strokeRect(this.x, this.y, this.width, this.height);
    }
    ctx.restore();
  }
}

