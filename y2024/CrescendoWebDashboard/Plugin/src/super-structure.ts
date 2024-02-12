import { html, LitElement } from "lit";
import { customElement, property } from "lit/decorators.js";
import { MAX_WIDTH, MAX_HEIGHT, Renderer } from "./super-structure/renderer";
import { SuperStructureData } from "./super-structure/datatypes";



export const superStructureElementConfig = {
  dashboard: {
    displayName: "SuperStructure",
  },
  properties: {
    pivotMotorAngle: { type: "Number", primary: true },
    goalAngle: { type: "Number", reflect: true },
    shooterMotorPercentage: { type: "Number", reflect: true },
    pivotMotorPercentage: { type: "Number", reflect: true },
    hasGamePiece: { type: "Boolean", reflect: true },
    intakeMotorPercentage: { type: "Number", reflect: true },
  },
  demos: [
    {
      html: `
      <super-structure source-key="/SuperStructure" source-provider="NetworkTables" style="width: 460px; height: 315px;">
      </super-structure>
    `,
    },
  ],
};

@customElement("super-structure")
export class SuperStructure extends LitElement {
  @property({ type: Number }) pivotMotorAngle = 0.0;
  @property({ type: Number }) goalAngle = 0.0;
  @property({ type: Number }) shooterMotorPercentage = 0.0;
  @property({ type: Number }) pivotMotorPercentage = 0.0;
  @property({ type: Boolean }) hasGamePiece = false;
  @property({ type: Number }) intakeMotorPercentage = 0.0;

  renderer = new Renderer();

  firstUpdated() {
    const canvas = this.shadowRoot?.querySelector("canvas") as HTMLCanvasElement;
    const ctx = canvas.getContext("2d") as CanvasRenderingContext2D;


    const updateObjectsAndDrawings = () => {
      this.renderRobot(canvas, ctx);
      window.requestAnimationFrame(updateObjectsAndDrawings);
    };

    const resizeObserver = new ResizeObserver(() => this.resized());
    resizeObserver.observe(this);

    window.requestAnimationFrame(updateObjectsAndDrawings);
  }

  private renderRobot(canvas: HTMLCanvasElement, ctx: CanvasRenderingContext2D): void {
    ctx.save();

    ctx.clearRect(0, 0, canvas.width, canvas.height);


    const { width, height } = this.getBoundingClientRect();
    const scale = Math.min(width / MAX_WIDTH, height / MAX_HEIGHT);

    ctx.beginPath();
    ctx.scale(scale, scale);

    const superStructureInfo: SuperStructureData = {
      pivotMotorAngle: this.pivotMotorAngle,
      goalAngle: this.goalAngle,
      shooterMotorPercentage: this.shooterMotorPercentage,
      pivotMotorPercentage: this.pivotMotorPercentage,
      hasGamePiece: this.hasGamePiece,
      intakeMotorPercentage: this.intakeMotorPercentage,
    };

    this.renderer.render(ctx, superStructureInfo);

    ctx.restore();
  }

  private resized(): void {
    this.requestUpdate();
  }

  render() {
    const rect = this.getBoundingClientRect();
    const { width, height } = rect;
    return html`
      <canvas
        width="${width}"
        height="${height}"
        style="border:1px solid #000000;"
      />
    `;
  }
}
