package command;

public class Ping implements Command{
    @Override
    public void execute(String args) {
        System.out.println("pong");
    }

    @Override
    public String description() {
        return "No comments\n";
    }
}
