package org.ak.billing.commands;

public interface Command {
    void execute();

    void undo();
}
