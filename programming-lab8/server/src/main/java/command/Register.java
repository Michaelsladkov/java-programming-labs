package command;

import util.database.DatabaseWorks;
import util.network.Answer;

public class Register implements Command, PasswordNeededCommand {
    private final DatabaseWorks databaseWorks;
    private String password;

    Register(DatabaseWorks dbWorker) {
        databaseWorks = dbWorker;
    }

    @Override
    public void putPassword(String pass) {
        password = pass;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Boolean a = databaseWorks.insertUser(args, password);
        System.out.println(a);
        answer.add(a);
        return answer;
    }
}
