package systems;

import coderunner.BlocklyCommands;
import core.Game;

public class DungeonHeroActions implements HeroActions {

  @Override
  public void move() {
    Game.system(BlocklyCommandExecuteSystem.class,
      s -> s.add(BlocklyCommands.Commands.HERO_MOVE));
  }

  @Override
  public void turnLeft() {
    Game.system(BlocklyCommandExecuteSystem.class,
      s -> s.add(BlocklyCommands.Commands.HERO_TURN_LEFT));
  }

  @Override
  public void turnRight() {
    Game.system(BlocklyCommandExecuteSystem.class,
      s -> s.add(BlocklyCommands.Commands.HERO_TURN_RIGHT));
  }
}
