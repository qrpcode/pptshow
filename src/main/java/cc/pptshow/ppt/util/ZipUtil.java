package cc.pptshow.ppt.util;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.exception.PPTShowCreateException;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static cc.pptshow.ppt.util.FileUtil.close;

public class ZipUtil {

    /**
     * zip文件解压
     * @param inputFile  待解压文件夹/文件
     * @param destDirPath  解压路径
     */
    public static void zipUncompress(String inputFile, String destDirPath) {
        ZipFile zipFile = null;
        InputStream is = null;
        FileOutputStream fos = null;
        File srcFile = new File(inputFile);
        try {
            if (!srcFile.exists()) {
                throw new Exception(srcFile.getPath() + "所指文件不存在");
            }
            zipFile = new ZipFile(srcFile);
            //开始解压
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    srcFile.mkdirs();
                } else {
                    File targetFile = new File(destDirPath + Constant.SLASH + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    is = zipFile.getInputStream(entry);
                    fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                }
            }
        } catch (Throwable t) {
            throw new PPTShowCreateException(t);
        } finally {
            close(fos);
            close(is);
            close(zipFile);
        }
    }

    public static void zipMultiFile(String filepath ,String zippath) {
        ZipOutputStream zipOut = null;
        try {
            // 要被压缩的文件夹
            File file = new File(filepath);
            File zipFile = new File(zippath);
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if(file.isDirectory()){
                File[] files = file.listFiles();
                if(files != null){
                    for(File fileSec : files){
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(zipOut != null) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files == null){
                return;
            }
            for(File fileSec:files){
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        }else{
            byte[] buf = new byte[1024];
            InputStream input = null;
            try {
                input = new FileInputStream(file);
                zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
                int len;
                while ((len = input.read(buf)) != -1) {
                    zipOut.write(buf, 0, len);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(input != null){
                    input.close();
                }
            }
        }
    }


}
