import { Shape } from "./shape";
export declare class Arc extends Shape {
    centerX: number;
    centerY: number;
    radius: number;
    startAngle: number;
    endAngle: number;
    constructor(centerX: number, centerY: number, radius: number, startAngle: number, endAngle: number, fillColor?: string);
    render(ctx: CanvasRenderingContext2D): void;
}
