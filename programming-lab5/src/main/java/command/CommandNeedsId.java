package command;

public abstract class CommandNeedsId implements Command {
    protected Integer getId(String args){
        if(args.length()==0){
            System.out.println("id is required");
            return null;
        }
        int id;
        try{
            id=Integer.parseInt(args);
        } catch (NumberFormatException e){
            System.out.println("Your input is not an id");
            return null;
        }
        return id;
    }
}
