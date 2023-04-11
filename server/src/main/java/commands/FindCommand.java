package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;

public class FindCommand extends Command {
    private final DragonCollection collection;

    FindCommand(DragonCollection collection) {
        super("find", "find id : returns dragon from collection by its id");
        this.collection = collection;
    }


    @Override
    public String execute(Request request) {
        Dragon dragon = collection.find((int) request.getArg());
        if (dragon != null) return dragon.toString();
        else return "No such element in collection";
    }
}
