package save;

import setup.MainSetup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MainSave {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Densone\\IdeaProjects\\DZ_files\\src\\setup\\Games\\savegames";

        GameProgress ex1 = new GameProgress(100, 1, 100, 20.5);
        GameProgress ex2 = new GameProgress(80, 2, 70, 15.5);
        GameProgress ex3 = new GameProgress(60, 3, 40, 10.5);

        saveGame(path + "\\ex0.dat", ex1);
        saveGame(path + "\\ex1.dat", ex2);
        saveGame(path + "\\ex2.dat", ex3);

        List<String> gameProgressPath = new ArrayList<>();
        gameProgressPath.add("C:\\Users\\Densone\\IdeaProjects\\DZ_files\\src\\setup\\Games\\savegames\\ex0.dat");
        gameProgressPath.add("C:\\Users\\Densone\\IdeaProjects\\DZ_files\\src\\setup\\Games\\savegames\\ex1.dat");
        gameProgressPath.add("C:\\Users\\Densone\\IdeaProjects\\DZ_files\\src\\setup\\Games\\savegames\\ex2.dat");

        zipFiles(path, gameProgressPath);
        deleteFiles(path);
    }

    public static void saveGame(String path, GameProgress gameProgress) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gameProgress);
        oos.flush();
        oos.close();
        fos.close();
    }

    public static void zipFiles(String path, List<String> gameProgressPath) throws IOException {

        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + "\\output.zip"));

        for (int i = 0; i < gameProgressPath.size(); i++) {
            String filename = gameProgressPath.get(i);
            FileInputStream fis = new FileInputStream(filename);
            ZipEntry entry = new ZipEntry("ex" + i + ".dat");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
            fis.close();
        }
        zout.close();
    }

    public static void deleteFiles(String path) {

        String toDel = ".dat";
        final FilenameFilter filter = new ExtensionFilter(toDel);
        final File dir = new File(path);
        String[] filenames = dir.list(filter);
        if (filenames != null) {
            for (String filename : filenames) {
                String fullFilename = path + File.separator + filename;
                File file = new File(fullFilename);
                file.delete();
            }
        } else {
            StringBuilder logForDel = MainSetup.log;
            File tempTxt = new File("C:\\Users\\Densone\\IdeaProjects\\DZ_files\\src\\setup\\Games\\temp\\temp.txt");
            MainSetup.log.append("Удалять нечего").append("\r\n");
            try {
                FileWriter toTemp = new FileWriter(tempTxt, false);
                toTemp.write(String.valueOf(logForDel));
                toTemp.flush();
                toTemp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ExtensionFilter implements FilenameFilter {
        private final String toDel;

        public ExtensionFilter(String ext) {

            toDel = ext;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(toDel);
        }
    }

}
