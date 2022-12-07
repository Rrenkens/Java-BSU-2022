package by.dudkoandrei.quizer.constants;

import by.dudkoandrei.quizer.tasks.Task.Generator;
import by.dudkoandrei.quizer.tasks.math_tasks.ExpressionTask;
import by.dudkoandrei.quizer.tasks.math_tasks.MathTask.Operation;
import java.util.EnumSet;

public class ExpressionTaskGeneratorConstants {

  public static final Generator generator = new ExpressionTask.Generator(
      0,
      25,
      4,
      EnumSet.allOf(Operation.class));
  public static final Generator integerGenerator = new ExpressionTask.Generator(
      0,
      25,
      EnumSet.allOf(Operation.class));
}
