import * as Blockly from "blockly";
import {dungeonDslGenerator} from "../dungeon_dsl.ts";

export function start(block: Blockly.Block): string {
  const nextBlock = block.getNextBlock();
  return <string>dungeonDslGenerator.blockToCode(nextBlock);
}
