import { LitElement } from "lit";
import { Renderer } from "./super-structure/renderer";
export declare const superStructureElementConfig: {
    dashboard: {
        displayName: string;
    };
    properties: {
        pivotMotorAngle: {
            type: string;
            primary: boolean;
        };
        goalAngle: {
            type: string;
            reflect: boolean;
        };
        shooterMotorPercentage: {
            type: string;
            reflect: boolean;
        };
        pivotMotorPercentage: {
            type: string;
            reflect: boolean;
        };
        hasGamePiece: {
            type: string;
            reflect: boolean;
        };
        intakeMotorPercentage: {
            type: string;
            reflect: boolean;
        };
    };
    demos: {
        html: string;
    }[];
};
export declare class SuperStructure extends LitElement {
    pivotMotorAngle: number;
    goalAngle: number;
    shooterMotorPercentage: number;
    pivotMotorPercentage: number;
    hasGamePiece: boolean;
    intakeMotorPercentage: number;
    renderer: Renderer;
    firstUpdated(): void;
    private renderRobot;
    private resized;
    render(): import("lit-html").TemplateResult<1>;
}
