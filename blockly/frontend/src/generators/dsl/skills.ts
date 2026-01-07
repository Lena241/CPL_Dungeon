import * as Blockly from "blockly";
import {Order} from "../dungeon_dsl.ts";

export function use(
  block: Blockly.Block,
  generator: Blockly.Generator
) {
  const dir = generator.valueToCode(block, "DIRECTION", Order.NONE);
  return "benutzen(" + dir + ")\n";
}

export function push(_block: Blockly.Block, _generator: Blockly.Generator) {
  return "schieben()\n";
}

export function pull(_block: Blockly.Block, _generator: Blockly.Generator) {
  return "ziehen()\n";
}
