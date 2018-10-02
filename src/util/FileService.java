package util;

import client.Client;
import server.Connexion;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class FileService {

    public static File compile(Connexion connexion, File sourceFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());
        return getFile(connexion, sourceFile.getName().replace("java", "class"));
    }

    public static File getFile(Connexion connexion, String s_file) {
        File file = new File("serverFiles/clientFiles/" + s_file);
        if (file.exists() && !file.isDirectory()) {
            return file;
        }
        System.out.println("Le fichier n'existe pas.");
        return null;
    }

    public static File getFile(Client client, String s_file) {
        File file = new File("src/clientFiles/" + s_file);
        if (file.exists() && !file.isDirectory()) {
            return file;
        }
        System.out.println("Le fichier n'existe pas.");
        return null;
    }

    public static Object getObject(String _class) {
        StringBuilder _file = new StringBuilder();
        _file.append(System.getProperty("user.dir"))
                .append(File.separator)
                .append("serverFiles") //@TODO change this
                .append(File.separator);
        File file = new File(_file.toString());
        Object result = null;
        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = URLClassLoader.newInstance(urls);
            String _package = "clientFiles.";
            Class<?> cls = Class.forName(_package + _class, true, cl);
            result = cls.getConstructor().newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
