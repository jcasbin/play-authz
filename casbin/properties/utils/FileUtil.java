package casbin.properties.utils;

import casbin.exceptions.File.FileIsEmptyException;

import java.io.*;

public class FileUtil {

    public static File file(String path) {
        return new File(path);

    }

    public static boolean isFileExists(String path) throws FileNotFoundException {
        File file = file(path);
        if (!(file.exists() && file.isFile())) throw new FileNotFoundException();
        return true;
    }

    public static boolean isFileEmpty(String path) throws FileIsEmptyException, FileNotFoundException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            if (isFileExists(path) && (br.readLine().length() == 0L)) {
                throw new FileIsEmptyException();
            }
        } catch (IOException e) {
            throw new FileIsEmptyException();
        }
        return true;
    }

}

