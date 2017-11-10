package Commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Logger;

public class CommandsManager {

    private Logger logger;

    private String commandsSavePath = "";
    private final String FILE_NAME = ".SPSCommands";

    List<Command> commandList = new Vector<>();

    public CommandsManager(String path, Logger logger){
        this.logger = logger;
        this.commandsSavePath = path;

        if(commandsSavePath.isEmpty()){
            commandsSavePath = FILE_NAME;
        }
        else {
            commandsSavePath += "/" + FILE_NAME;
        }
    }

    public void readCommands(){
        try {
            FileInputStream fileInputStream = new FileInputStream(commandsSavePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            commandList = (Vector<Command>) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (Exception e){
            logger.info("Error while reading commandsList, " + e.toString());
        }
    }

    public void writeCommands(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(commandsSavePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(commandList);
            objectOutputStream.close();
        }
        catch (Exception e){
            logger.info("Error while saving commandsList, " + e.toString());
        }
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public boolean add(Command command) {
        return commandList.add(command);
    }

    public int size(){
        return commandList.size();
    }

    public Command get(int i) {
        return commandList.get(i);
    }

    public Command set(int i, Command command) {
        return commandList.set(i, command);
    }

    public Command remove(int i) {
        return commandList.remove(i);
    }

}