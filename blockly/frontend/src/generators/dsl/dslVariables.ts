import * as Blockly from "blockly";
import {Order} from "../dungeon_dsl.ts";

export function set_number_expression(
  block: Blockly.Block,
  generator: Blockly.Generator
): string {
  const variable_name = String(block.getField("VAR").getVariable().name);
  const variable_value = generator.valueToCode(block, "VALUE", Order.NONE) || "0";
  const result = variable_name + " = " + variable_value + "\n";
  console.log(result);
  return result
}

export function set_number(
  block: Blockly.Block,
  generator: Blockly.Generator
): string {
  const variable_name = String(block.getField("VAR").getVariable().name);
  const variable_value = generator.valueToCode(block, "VALUE", Order.NONE) || "0";
  const result = variable_name + " = " + variable_value + "\n";
  console.log(result);
  return result
}


export function var_number(block: Blockly.Block, _generator: Blockly.Generator) {
  const code = String(block.getFieldValue("VAR_NUMBER") ?? "0");
  return [code, Order.NONE] as any;
}

export function get_variable(block: Blockly.Block, _generator: Blockly.Generator) {
  const variable_name = String(block.getField("VAR").getVariable().name);
  return [variable_name, Order.NONE] as any;
}

export function expression(
  block: Blockly.Block,
  generator: Blockly.Generator
): string {
  const operator = String(block.getFieldValue("OPERATOR"));
  const input_a = generator.valueToCode(block, "INPUT_A", Order.NONE) || "0";
  const input_b = generator.valueToCode(block, "INPUT_B", Order.NONE) || "0";

  const result = input_a + operator + input_b + "\n";
  console.log(result);
  return [result, Order.MULTIPLICATIVE] as any;
}
