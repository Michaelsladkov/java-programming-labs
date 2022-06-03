package command;

import util.database.DatabaseWorks;
import util.network.Answer;

public class Login implements Command, LoginNeededCommand, PasswordNeededCommand {
    private final DatabaseWorks databaseWorks;
    private String login;
    private String password;

    Login(DatabaseWorks dbWorker) {
        databaseWorks = dbWorker;
    }

    @Override
    public void putLogin(String login) {
        this.login = login;
    }

    @Override
    public void putPassword(String pass) {
        password = pass;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        answer.add(databaseWorks.checkUser(login, password));
        return answer;
    }
}
