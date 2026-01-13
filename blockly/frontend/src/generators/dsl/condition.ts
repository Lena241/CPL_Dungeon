import * as Blockly from "blockly";
import {Order} from "../dungeon_dsl.ts";

export function controls_if(block: Blockly.Block, generator: Blockly.Generator) {
  let n = 0;
  let code = "";

  // First IF
  const cond0 = (generator.valueToCode(block, "IF0", Order.NONE) || "falsch").trim();
  let do0 = generator.statementToCode(block, "DO0");
  code += `if ${cond0}:\n${do0}`;

  // ELSE IFs
  n = 1;
  while (block.getInput("IF" + n)) {
    const condN = (generator.valueToCode(block, "IF" + n, Order.NONE) || "falsch").trim();
    const doN = generator.statementToCode(block, "DO" + n);
    code += `else if ${condN}:\n${doN}`;
    n++;
  }

  // ELSE (optional)
  if (block.getInput("ELSE")) {
    const elseBranch = generator.statementToCode(block, "ELSE");
    code += `else:\n${elseBranch}`;
  }

  // Close once
  code += "end\n";
  return code;
}

export const controls_ifelse = controls_if;

export function logic_active_direction(
  block: Blockly.Block,
  generator: Blockly.Generator
) {
  const dir = generator.valueToCode(block, "DIRECTION", Order.NONE);
  return ["activ(" + dir + ")", Order.NONE];
}
