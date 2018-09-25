import upload.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileService {


    public static File compile(File sourceFile) {
        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());
        return getFile(sourceFile.getName().replace("java","class"));
    }

    public static File getFile(String s_file) {
        File file = new File("src/upload/" + s_file);
        if (file.exists() && !file.isDirectory()) {
            return file;
        }
        System.out.println("Le fichier n'existe pas.");
        return null;
    }

    public static int sizeof(Object obj) throws IOException {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();

        return byteOutputStream.toByteArray().length;
    }


//    public static void main(String[] args) {
//        File file = getFile("Test.java");
//        File compiledFile = compile(file);
//
//    }

}
