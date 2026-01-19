import * as Blockly from "blockly";
import { Order } from "../dungeon_dsl.ts";

export function logic_boolean(block: Blockly.Block) {
  const code = block.getFieldValue("BOOL") === "TRUE" ? "true" : "false";
  return [code, Order.NONE];
}

export function logic_operator(block: Blockly.Block, generator: Blockly.Generator) {
  const a = generator.valueToCode(block, "CONDITION_A", Order.NONE) || "false";
  const b = generator.valueToCode(block, "CONDITION_B", Order.NONE) || "false";
  const op = block.getFieldValue("LOGIC_OPERATOR");
  return [`(${a} ${op} ${b})`, Order.NONE];
}

export function not_condition(block: Blockly.Block, generator: Blockly.Generator) {
  const a = generator.valueToCode(block, "INPUT_A", Order.NONE) || "false";
  return [`not ${a}`, Order.NONE];
}

export function logic_active_direction(block: Blockly.Block, generator: Blockly.Generator) {
  const dir = generator.valueToCode(block, "DIRECTION", Order.NONE) || "hier";
  return [`active(${dir})`, Order.NONE];
}

export function controls_if(block: Blockly.Block, generator: Blockly.Generator) {
  let code = "";

  // IF0
  const cond0 = (generator.valueToCode(block, "IF0", Order.NONE) || "false").trim();
  const do0 = generator.statementToCode(block, "DO0");
  code += `if ${cond0}:\n${do0}`;

  // ELSE IFs
  let n = 1;
  while (block.getInput("IF" + n)) {
    const condN = (generator.valueToCode(block, "IF" + n, Order.NONE) || "false").trim();
    const doN = generator.statementToCode(block, "DO" + n);

    console.log("[DSL] IF0 condition:", cond0);
    console.log("[DSL] DO0 body:\n", do0);

    code += `else if ${condN}:\n${doN}`;
    n++;
  }

  // ELSE
  if (block.getInput("ELSE")) {
    const elseBranch = generator.statementToCode(block, "ELSE");
    code += `else:\n${elseBranch}`;
  }

  code += `end\n`;
  return code;
}

export const controls_ifelse = controls_if;
