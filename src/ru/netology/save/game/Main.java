package ru.netology.save.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static final String ROOT_DIR = "Games";

    public static void main(String[] args) {
        openZip("savegames//save.zip", "savegames//");
        System.out.println(openProgress("savegames//save1.dat"));
    }

    private static void openZip(String zipFile, String savePath) {
        try (ZipInputStream zipInStream = new ZipInputStream(new FileInputStream(ROOT_DIR + "//" + zipFile))) {
            ZipEntry entry;
            String name;
            while ((entry = zipInStream.getNextEntry()) != null) {
                name = entry.getName();
                if (!new File(ROOT_DIR + "//" + savePath + name).exists()) {
                    try (FileOutputStream fileOutStream = new FileOutputStream(ROOT_DIR + "//" + savePath + name)) {
                        for (int c = zipInStream.read(); c != -1; c = zipInStream.read()) {
                            fileOutStream.write(c);
                        }
                        fileOutStream.flush();
                        zipInStream.closeEntry();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static GameProgress openProgress(String saveFile) {
        try (FileInputStream fileInStream = new FileInputStream(ROOT_DIR + "//" + saveFile);
             ObjectInputStream objInStream = new ObjectInputStream(fileInStream)) {
            return (GameProgress) objInStream.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
