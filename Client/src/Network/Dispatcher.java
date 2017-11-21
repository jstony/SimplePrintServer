package Network;

import Commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Logger;

public class Dispatcher {

    private Logger logger;
    private NetworkManager networkManager;

    public Dispatcher(NetworkManager networkManager, Logger logger){
        this.networkManager = networkManager;
        this.logger = logger;
    }

    public boolean send(Command command, int mode, String fileName, String filePath) {

        if(networkManager.getStatus() == 0){
            return false;
        }

        Path path = Paths.get(filePath);

        String commandStr;

        switch (mode){
            case 0:
                commandStr = command.getPrintCommand();
                break;
            case 1:
            case 2:
                commandStr = command.getOpenCommand();
                break;
            default:
                return false;
        }

        try {
            byte[] data = Files.readAllBytes(path);
            networkManager.send(serialize(new Carrier(commandStr, mode, fileName, new String(data))));
            return true;
        }
        catch (Exception e){
            logger.info("can't send Carrier to server, " + e.toString());
        }

        return false;
    }

    private String serialize( Serializable serializable ) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream( byteArrayOutputStream );
        objectOutputStream.writeObject( serializable );
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
}
