import * as Blockly from "blockly";
import { Order } from "../dungeon_dsl.ts";

export function repeat(block: Blockly.Block, generator: Blockly.Generator): string {
  const times = generator.valueToCode(block, "TIMES", Order.NONE) || "0";
  const body  = generator.statementToCode(block, "DO");
  const safeBody = body && body.trim().length ? body : "\n";
  return `repeat ${times}:\n${safeBody}end\n`;
}


export function repeat_number(block: Blockly.Block, _generator: Blockly.Generator) {
  const code = String(block.getFieldValue("REPEAT_NUMBER") ?? "0");
  return [code, Order.NONE] as any;
}
