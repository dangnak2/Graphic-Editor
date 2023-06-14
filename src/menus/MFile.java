package menus;

import java.io.*;

public class MFile {
    private File file;

    public MFile() {
        this.file = null;
    }

    public void store(Object object, File file){
        this.file = file;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // file에 저장된 데이터를 Java Object로 load한다.
    public Object load(File file){
        this.file = file;
        try {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
