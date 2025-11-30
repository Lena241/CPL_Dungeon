package coderunner;

import core.Game;
import core.utils.logging.DungeonLogger;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import systems.BlocklyCommandExecuteSystem;

public class DslCodeRunner {

  private static final DungeonLogger LOGGER = DungeonLogger.getLogger(DslCodeRunner.class);
  private static DslCodeRunner instance;

  private final AtomicBoolean codeRunning = new AtomicBoolean(false);
  private ExecutorService executor;
  private Future<?> currentExecution;

  private DslCodeRunner() {}

  public static DslCodeRunner instance() {
    if (instance == null) {
      instance = new DslCodeRunner();
    }
    return instance;
  }

  public void executeDslCode(String code) {
    executeDslCode(code, 0);
  }

  public void executeDslCode(String code, int sleepAfterEachLineMillis) {
    stopCode();
    codeRunning.set(true);

    VerySimpleDslRunner runner = new VerySimpleDslRunner();

    executor = Executors.newSingleThreadExecutor();
    currentExecution =
      executor.submit(
        () -> {
          try {
            runner.execute(code, sleepAfterEachLineMillis);
          } catch (RuntimeException e) {
            LOGGER.warn("DSL execution error: " + e.getMessage(), e);
            throw e;
          } finally {
            codeRunning.set(false);
          }
        });
  }

  public boolean isCodeRunning() {
    BlocklyCommandExecuteSystem bce =
      (BlocklyCommandExecuteSystem) Game.systems().get(BlocklyCommandExecuteSystem.class);
    return (codeRunning.get() && currentExecution != null && !currentExecution.isDone())
      || !bce.isEmpty();
  }

  public void stopCode() {
    if (currentExecution != null && !currentExecution.isDone()) {
      currentExecution.cancel(true);
    }
    if (executor != null) {
      executor.shutdownNow();
      executor = null;
    }
    codeRunning.set(false);
  }
}
