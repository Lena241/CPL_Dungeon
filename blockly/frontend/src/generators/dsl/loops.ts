import * as Blockly from "blockly";
import { Order } from "../dungeon_dsl.ts";

export function repeat(block: Blockly.Block, generator: Blockly.Generator): string {
  const times = generator.valueToCode(block, "TIMES", Order.NONE) || "0";
  const repeat_var = block.id.replace(/[^a-zA-Z_]/g, "");

  let body = generator.statementToCode(block, "DO");
  body = body && body.trim().length ? body : "";
  body = body.replace(/[ \t\r\n]+$/, "") + "\n";

  return `for ${repeat_var} in range(${times}):\n${body}end\n`;
}

export function repeat_number(block: Blockly.Block, _generator: Blockly.Generator) {
  const code = String(block.getFieldValue("REPEAT_NUMBER") ?? "0");
  return [code, Order.NONE] as any;
}
