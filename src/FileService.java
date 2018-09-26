//import upload.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class FileService {


    public static File compile(File sourceFile) {
        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());
        return getFile(sourceFile.getName().replace("java", "class"));
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

    /**
     * Right now, we supposed we know the number of parameters, their types and the type return by the method
     * @param _class
     * @param _method
     * @return
     */
    public static int callMethod(String _class, String _method) {
        Object test = getObject(_class);
        Method method = null;
        try {
            method = test.getClass().getMethod(_method, int.class, int.class);
            int result = (int) method.invoke(test, 1, 1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static Object getObject(String _class) {
        StringBuilder _file = new StringBuilder();
        _file.append(System.getProperty("user.dir"))
                .append(File.separator)
                .append("test")
                .append(File.separator);
        File file = new File(_file.toString());
        Object result = null;
        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = URLClassLoader.newInstance(urls);
            String _package = "upload.";
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

//    public Method getMethod(String _method, Class classe) {
//        Method[] methods = classe.getDeclaredMethods();
//        for (Method method : methods) {
//            if (method.getName().equals(method)) {
//                return method;
//            }
//            continue;
//        }
//        return null;
//    }

//    public static Class[] getParameters(Method method) {
//        Class test = (Class) ((ParameterizedType)method.getParameters().getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        test a = 1;
//        return null;
//    }


    public void loadClass(String classe) {

    }

//    public static void main(String[] args) {
//        StringBuilder sb = new StringBuilder(64);
//        sb.append("package testcompile;\n");
//        sb.append("public class HelloWorld implements inlinecompiler.InlineCompiler.DoStuff {\n");
//        sb.append("    public void doStuff() {\n");
//        sb.append("        System.out.println(\"Hello world\");\n");
//        sb.append("    }\n");
//        sb.append("}\n");
//
//        File helloWorldJava = new File("testcompile/HelloWorld.java");
//        if (helloWorldJava.getParentFile().exists() || helloWorldJava.getParentFile().mkdirs()) {
//
//            try {
//                Writer writer = null;
//                try {
//                    writer = new FileWriter(helloWorldJava);
//                    writer.write(sb.toString());
//                    writer.flush();
//                } finally {
//                    try {
//                        writer.close();
//                    } catch (Exception e) {
//                    }
//                }
//
//                /** Compilation Requirements *********************************************************************************************/
//                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
//
//                // This sets up the class path that the compiler will use.
//                // I've added the .jar file that contains the DoStuff interface within in it...
//                List<String> optionList = new ArrayList<String>();
//                optionList.add("-classpath");
//                optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");
//
//                Iterable<? extends JavaFileObject> compilationUnit
//                        = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
//                JavaCompiler.CompilationTask task = compiler.getTask(
//                        null,
//                        fileManager,
//                        diagnostics,
//                        optionList,
//                        null,
//                        compilationUnit);
//                /********************************************************************************************* Compilation Requirements **/
//                if (task.call()) {
//                    /** Load and execute *************************************************************************************************/
//                    System.out.println("Yipe");
//                    // Create a new custom class loader, pointing to the directory that contains the compiled
//                    // classes, this should point to the top of the package structure!
//                    URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
//                    // Load the class from the classloader by name....
//                    Class<?> loadedClass = classLoader.loadClass("testcompile.HelloWorld");
//                    // Create a new instance...
//                    Object obj = loadedClass.newInstance();
//                    // Santity check
//                    if (obj instanceof DoStuff) {
//                        // Cast to the DoStuff interface
//                        DoStuff stuffToDo = (DoStuff)obj;
//                        // Run it baby
//                        stuffToDo.doStuff();
//                    }
//                    /************************************************************************************************* Load and execute **/
//                } else {
//                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
//                        System.out.format("Error on line %d in %s%n",
//                                diagnostic.getLineNumber(),
//                                diagnostic.getSource().toUri());
//                    }
//                }
//                fileManager.close();
//            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exp) {
//                exp.printStackTrace();
//            }
//        }
//    }
//
//    public static interface DoStuff {
//
//        public void doStuff();
//    }


//    public static void main(String[] args) {
//        File file = getFile("Test.java");
//        File compiledFile = compile(file);
//
//    }

}
