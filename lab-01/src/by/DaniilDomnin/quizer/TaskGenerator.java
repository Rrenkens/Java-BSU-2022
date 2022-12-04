package by.DaniilDomnin.quizer;

import exceptions.TaskGeneratorException;

public interface TaskGenerator {
    Task generate() throws TaskGeneratorException;
}