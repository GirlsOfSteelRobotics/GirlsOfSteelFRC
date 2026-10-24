import { Shape } from "./shape";
export declare function getClampedColor(inSpeed: number, min: number, max: number): string;
export declare function getMotorColor(speed: number): string;
export declare function getMotorColorWithDefault(speed: number, defaultColor: string | undefined): string;
export declare function setShapesMotorColor(shape: Shape, motorSpeed: number): void;
export declare function setShapesMotorColorStroke(shape: Shape, motorSpeed: number): void;
export declare function setShapesBooleanColor(shape: Shape, state: boolean, trueColor: string, falseColor: string): void;
