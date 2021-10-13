package botStructure;

import initCollections.clientMap;

public class Utils {

    public static String getNameFromMap(String username) {
        return clientMap.clientsMap.get(username);
    }
}
