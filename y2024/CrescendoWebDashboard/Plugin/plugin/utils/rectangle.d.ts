import { Shape } from "./shape";
export declare class Rectangle extends Shape {
    x: number;
    y: number;
    width: number;
    height: number;
    rotation: number;
    rotationPoint?: [number, number];
    constructor(x: number, y: number, width: number, height: number, fillColor?: string);
    setRotation(degrees: number): void;
    rotateAround(degrees: number, x: number, y: number): void;
    rotateAroundShape(degrees: number, shape: Rectangle): void;
    rotateAroundShapeEnd(degrees: number, shape: Rectangle): void;
    rotateAroundOrigin(degrees: number): void;
    rotateAroundEnd(degrees: number): void;
    render(ctx: CanvasRenderingContext2D): void;
}
