import * as Blockly from "blockly";
import { Order } from "../dungeon_dsl.ts";

export function move(
  _block: Blockly.Block,
  _generator: Blockly.Generator
): string {
  return "gehen()\n";
}

export function rotate(
  block: Blockly.Block,
  generator: Blockly.Generator
): string {
  const ALLOWED_DIRECTIONS = ["links", "rechts"];

  const dir = generator.valueToCode(block, "DIRECTION", Order.ATOMIC).trim();

  if (dir === "") return "";

  if (!ALLOWED_DIRECTIONS.includes(dir)) {
    block.setWarningText("Ungültige Richtung. Erlaubt sind: links, rechts");
    block.getInput("DIRECTION")?.connection?.disconnect();
    return "";
  }

  block.setWarningText(null);

  return `drehen(${dir})\n`;
}
