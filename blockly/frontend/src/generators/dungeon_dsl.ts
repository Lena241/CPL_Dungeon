import * as Blockly from "blockly";

import * as start from "./dsl/start.ts";
import * as character from "./dsl/character.ts";
import * as directions from "./dsl/directions.ts";

class DungeonDSLGenerator extends Blockly.Generator {
  public variables: Map<string, number | string>;

  constructor() {
    super("DungeonDSL");

    this.variables = new Map();
  }

  public scrub_(
    block: Blockly.Block,
    code: string,
    thisOnly?: boolean
  ): string {
    const nextBlock =
      block.nextConnection?.targetBlock() || null;

    if (block.type === "start") {
      return code + dungeonDslGenerator.blockToCode(nextBlock);
    }

    if (nextBlock && !thisOnly) {
      return code + "\n" + dungeonDslGenerator.blockToCode(nextBlock);
    }

    return code;
  }
}

export const dungeonDslGenerator = new DungeonDSLGenerator();

Object.assign(
  dungeonDslGenerator.forBlock,
  start,
  character,
  directions
);

export const Order = {
  ATOMIC: 0,
  MEMBER: 1,
  FUNCTION_CALL: 1,
  INCREMENT: 2,
  DECREMENT: 2,
  UNARY_PLUS: 3,
  UNARY_MINUS: 3,
  UNARY_LOGICAL_NOT: 3,
  UNARY_BITWISE_NOT: 3,
  UNARY_PRE_INCREMENT: 3,
  UNARY_PRE_DECREMENT: 3,
  CAST: 4,
  NEW: 4,
  MULTIPLICATIVE: 5,
  ADDITIVE: 6,
  STRING_CONCAT: 6,
  SHIFT: 7,
  RELATIONAL: 8,
  EQUALITY: 9,
  BITWISE_AND: 10,
  BITWISE_XOR: 11,
  BITWISE_OR: 12,
  LOGICAL_AND: 13,
  LOGICAL_OR: 14,
  TERNARY: 15,
  ASSIGNMENT: 16,
  LAMBDA: 17,
  NONE: 99,
} as const;
