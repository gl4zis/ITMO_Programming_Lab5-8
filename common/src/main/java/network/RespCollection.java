package network;

import dragons.DragonCollection;

public class RespCollection extends Response {
    public final DragonCollection collection;

    public RespCollection(DragonCollection collection) {
        super(null);
        this.collection = collection;
    }
}
