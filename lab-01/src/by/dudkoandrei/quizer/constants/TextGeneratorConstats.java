package by.dudkoandrei.quizer.constants;

import by.dudkoandrei.quizer.task_generators.PoolTaskGenerator;
import by.dudkoandrei.quizer.tasks.Task.Generator;
import by.dudkoandrei.quizer.tasks.TextTask;

public class TextGeneratorConstats {

  public static final Generator generator = new PoolTaskGenerator(
      false,
      new TextTask("Fill in the blank: \"_____. I'm your father.\"\n"
          + "a) Luke\n"
          + "b) Boy\n"
          + "c) Skywalker\n"
          + "d) No", "d"),
      new TextTask("Mace Windu light-saber color?\n"
          + "a) Red\n"
          + "b) Purple\n"
          + "c) Green\n"
          + "d) Blue", "b"),
      new TextTask("What species of alien is Yoda?\n"
          + "a) Ortolan\n"
          + "b) Human\n"
          + "c) Unknown\n"
          + "d) Gungan", "c"));
}
