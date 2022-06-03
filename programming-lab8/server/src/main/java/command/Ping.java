package command;

import util.network.Answer;

public class Ping implements Command {

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        answer.add("pong");
        return answer;
    }

    @Override
    public String description() {
        return "No comments\n";
    }
}
