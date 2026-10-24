import { Shape } from "./shape";
import { Rectangle } from "./rectangle";
export declare class Circle extends Shape {
    centerX: number;
    centerY: number;
    radius: number;
    rotation: number;
    rotationPoint?: [number, number];
    constructor(x: number, y: number, radius: number, fillColor?: string);
    rotateAroundShapeEnd(degrees: number, shape: Rectangle): void;
    render(ctx: CanvasRenderingContext2D): void;
}
