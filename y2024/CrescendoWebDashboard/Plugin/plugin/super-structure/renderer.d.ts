import { Circle } from "../utils/circle";
import { Rectangle } from "../utils/rectangle";
import { SuperStructureData } from "./datatypes";
export declare const MAX_WIDTH = 15;
export declare const MAX_HEIGHT = 15;
export declare class Renderer {
    m_chassis: Rectangle;
    m_pivotMotor: Circle;
    m_armRect: Rectangle;
    m_shooterRect: Rectangle;
    m_shooterMotor: Circle;
    m_intakeMotor: Circle;
    m_pivotAngleGoal: Rectangle;
    constructor();
    private updateSuperStructure;
    render(ctx: CanvasRenderingContext2D, superStructureData: SuperStructureData): void;
}
