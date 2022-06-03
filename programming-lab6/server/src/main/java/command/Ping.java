package command;

import util.AnswerSender;

public class Ping implements Command {
    private final AnswerSender sender;

    Ping(AnswerSender answerSender) {
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        sender.prepareAnswer("pong");
    }

    @Override
    public String description() {
        return "No comments\n";
    }
}
