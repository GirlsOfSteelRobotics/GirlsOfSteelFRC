/* eslint camelcase: "off" */
import { Circle } from "../utils/circle";
import { getMotorColor } from "../utils/color_utils";
import { Rectangle } from "../utils/rectangle";

import { SuperStructureData } from "./datatypes";


export const MAX_WIDTH = 15; // TODO figure out real value
export const MAX_HEIGHT = 15; // TODO figure out real value


const CHASSIS_HEIGHT = 2;
const CHASSIS_WIDTH = 8;
const PIVOT_MOTOR_RADIUS = 1;
const ARM_RECT_HEIGHT = 6;
const ARM_RECT_WIDTH = 1;
const SHOOTER_RECT_HEIGHT = 1;
const SHOOTER_RECT_WIDTH = 6;
const SHOOTER_MOTOR_RADIUS = 0.5;
const INTAKE_MOTOR_RADIUS = 0.5;
const PIVOT_ANGLE_GOAL_HEIGHT = ARM_RECT_HEIGHT;
const PIVOT_ANGLE_GOAL_WIDTH = ARM_RECT_WIDTH;


const CHASSIS_X = 3;
const CHASSIS_Y = MAX_HEIGHT - CHASSIS_HEIGHT - 4;
const PIVOT_MOTOR_X = CHASSIS_X + 0.5;
const PIVOT_MOTOR_Y = CHASSIS_Y;
const ARM_RECT_X = CHASSIS_X;
const ARM_RECT_Y = CHASSIS_Y - ARM_RECT_HEIGHT;
const SHOOTER_RECT_X = ARM_RECT_X - 1;
const SHOOTER_RECT_Y = CHASSIS_Y - ARM_RECT_HEIGHT - SHOOTER_RECT_HEIGHT;
const SHOOTER_MOTOR_X = SHOOTER_RECT_X - SHOOTER_MOTOR_RADIUS / 2.0;
const SHOOTER_MOTOR_Y = SHOOTER_RECT_Y + (SHOOTER_RECT_HEIGHT / 2);
const INTAKE_MOTOR_X = SHOOTER_RECT_X + SHOOTER_RECT_WIDTH + INTAKE_MOTOR_RADIUS / 2.0;
const INTAKE_MOTOR_Y = SHOOTER_RECT_Y + (SHOOTER_RECT_HEIGHT / 2);
const PIVOT_ANGLE_GOAL_X = ARM_RECT_X;
const PIVOT_ANGLE_GOAL_Y = ARM_RECT_Y;


export class Renderer {

  m_chassis: Rectangle;
  m_pivotMotor: Circle;
  m_armRect: Rectangle;
  m_shooterRect: Rectangle;
  m_shooterMotor: Circle;
  m_intakeMotor: Circle;
  m_pivotAngleGoal: Rectangle;

  constructor() {

    this.m_chassis = new Rectangle(CHASSIS_X, CHASSIS_Y, CHASSIS_WIDTH, CHASSIS_HEIGHT, "grey");
    this.m_pivotMotor = new Circle(PIVOT_MOTOR_X, PIVOT_MOTOR_Y, PIVOT_MOTOR_RADIUS, "darkred");
    this.m_armRect = new Rectangle(ARM_RECT_X, ARM_RECT_Y, ARM_RECT_WIDTH, ARM_RECT_HEIGHT, "lightseagreen");
    this.m_shooterRect = new Rectangle(SHOOTER_RECT_X, SHOOTER_RECT_Y, SHOOTER_RECT_WIDTH, SHOOTER_RECT_HEIGHT, "transparent");
    this.m_shooterMotor = new Circle(SHOOTER_MOTOR_X, SHOOTER_MOTOR_Y, SHOOTER_MOTOR_RADIUS, "plum");
    this.m_intakeMotor = new Circle(INTAKE_MOTOR_X, INTAKE_MOTOR_Y, INTAKE_MOTOR_RADIUS, "plum");
    this.m_pivotAngleGoal = new Rectangle(PIVOT_ANGLE_GOAL_X, PIVOT_ANGLE_GOAL_Y, PIVOT_ANGLE_GOAL_WIDTH, PIVOT_ANGLE_GOAL_HEIGHT, "transparent");
  }


  private updateSuperStructure(superStructureData: SuperStructureData) {
    this.m_pivotMotor.fillColor = getMotorColor(superStructureData.pivotMotorPercentage);
    this.m_shooterMotor.fillColor = getMotorColor(superStructureData.shooterMotorPercentage);
    this.m_intakeMotor.fillColor = getMotorColor(superStructureData.intakeMotorPercentage);
    this.m_shooterRect.strokeColor = "black"
    this.m_shooterRect.strokeWidth = 0.1
    this.m_pivotAngleGoal.strokeColor = "black"
    this.m_pivotAngleGoal.strokeWidth = 0.1

    if (superStructureData.hasGamePiece) {
        this.m_shooterRect.fillColor = "orange"
    } else {
        this.m_shooterRect.fillColor = "transparent"
    }

    this.m_armRect.rotateAroundEnd(180 + superStructureData.pivotMotorAngle)
    this.m_shooterRect.rotateAroundShapeEnd(180 + superStructureData.pivotMotorAngle, this.m_armRect)
    this.m_shooterMotor.rotateAroundShapeEnd(180 + superStructureData.pivotMotorAngle, this.m_armRect)
    this.m_intakeMotor.rotateAroundShapeEnd(180 + superStructureData.pivotMotorAngle, this.m_armRect)

    this.m_pivotAngleGoal.rotateAroundEnd(180 + superStructureData.goalAngle)
  }



  render(ctx: CanvasRenderingContext2D, superStructureData: SuperStructureData) {

    this.updateSuperStructure(superStructureData);

    this.m_chassis.render(ctx);
    this.m_pivotMotor.render(ctx);
    this.m_armRect.render(ctx);
    this.m_shooterRect.render(ctx);
    this.m_shooterMotor.render(ctx);
    this.m_intakeMotor.render(ctx);
    this.m_pivotAngleGoal.render(ctx);
  }

}