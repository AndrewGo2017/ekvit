package ru.sber.ekvit.job.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static ru.sber.ekvit.job.register.BaseRegister.SPECIAL_SYMBOL;


public class CommonUtil {

    public static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1).toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        try {
            int index = name.lastIndexOf(".");
            return name.substring(0, name.lastIndexOf("."));
        } catch (Exception e) {
            return "!";
        }
    }

    public static void checkDirectory(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static File renameUploadedFile(File file, String contract) throws IOException {
        String path = file.getParent();
        String fName = file.getName();
        checkDirectory(Paths.get(path).toFile());
        File f = new File(Paths.get(path, SPECIAL_SYMBOL + contract + SPECIAL_SYMBOL + fName).toUri());
        File newFile = getNewFile(f);
        return newFile;
    }

    public static File getNewFile(File f) {
        String path = f.getParent();
        String fName = f.getName();
        int i = 0;
        while (f.exists()) {
            i++;
            String str = "!" + i;
            f = new File(
                    Paths.get(
                            path, getFileNameWithoutExtension(new File(fName)) +
                                    str + "." +
                                    getFileExtension(new File(fName))).toUri());
        }
        return f;
    }

    public static void deleteFile(File file) {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {

        }
    }

    public static String getContractIdFromFileName(String fileName){
        return fileName.substring(SPECIAL_SYMBOL.length(), fileName.indexOf(SPECIAL_SYMBOL, SPECIAL_SYMBOL.length()));
    }

    public static List<File> unZip(File zipFile) throws IOException{
        List<File> filesFromZip = new ArrayList<>();
        byte[] buffer = new byte[2048];
        Path outDir = Paths.get(zipFile.getParent());

        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = CommonUtil.getNewFile(outDir.resolve(entry.getName()).toFile());

                try (FileOutputStream fos = new FileOutputStream(newFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                }

                filesFromZip.add(newFile);
            }
        }
        CommonUtil.deleteFile(zipFile);
        return filesFromZip;
    }
}
