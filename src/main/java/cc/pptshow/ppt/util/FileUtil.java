package cc.pptshow.ppt.util;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.exception.PPTShowCreateException;
import cc.pptshow.ppt.exception.PPTShowGenerateException;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {

    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        if (Strings.isNullOrEmpty(uuid)) {
            return System.currentTimeMillis() + Constant.EMPTY;
        }
        return uuid.replace(Constant.LINE, Constant.EMPTY);
    }

    public static String tmpdir() {
        return System.getProperty("java.io.tmpdir") + Constant.PATH + Constant.BACKSLASH;
    }

    public static void traditionalCopyFromStream(InputStream sourceStream, String destPath) {
        File dest = new File(destPath);
        FileOutputStream fos = null;
        try {
            if (!dest.exists()) {
                boolean newFile = dest.createNewFile();
                if (!newFile) {
                    throw new PPTShowCreateException("抱歉，在系统缓存目录创建缓存文件失败，请检查是否进行了权限设定！");
                }
            }
            fos = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len;
            while ((len = sourceStream.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        } catch (Throwable t) {
            throw new PPTShowCreateException(t);
        } finally {
            try {
                if (Objects.nonNull(sourceStream)) {
                    sourceStream.close();
                }
            } catch (Throwable ignored) {
            }
            try {
                if (Objects.nonNull(fos)) {
                    fos.close();
                }
            } catch (Throwable ignored) {
            }
        }
    }

    public static void traditionalCopy(String sourcePath, String destPath) {
        File source = new File(sourcePath);
        File dest = new File(destPath);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            if (!dest.exists()) {
                boolean newFile = dest.createNewFile();
                if (!newFile) {
                    throw new PPTShowCreateException("抱歉，在系统缓存目录创建缓存文件失败，请检查是否进行了权限设定！");
                }
            }
            fis = new FileInputStream(source);
            fos = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        } catch (Throwable t) {
            throw new PPTShowCreateException(t);
        } finally {
            try {
                if (Objects.nonNull(fis)) {
                    fis.close();
                }
            } catch (Throwable ignored) {
            }
            try {
                if (Objects.nonNull(fos)) {
                    fos.close();
                }
            } catch (Throwable ignored) {
            }
        }
    }

    public static void write(String file, String text) {
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            fos = new FileOutputStream(file);
            channel = fos.getChannel();
            byte[] array = text.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(array);
            channel.write(buffer);
        } catch (Throwable t) {
            throw new PPTShowGenerateException(t);
        } finally {
            close(channel);
            close(fos);
        }
    }

    public static void close(Closeable close) {
        try {
            if (Objects.nonNull(close)) {
                close.close();
            }
        } catch (Throwable ignored) {}
    }

    public static String getMediaImgName(int mediaIdBegin, String img) {
        return "image" + mediaIdBegin + Constant.DOT + Files.getFileExtension(img);
    }

    public static void convertSvg2Png(File svg, File png) {
        try {
            InputStream in = new FileInputStream(svg);
            OutputStream out = new FileOutputStream(png);
            out = new BufferedOutputStream(out);

            Transcoder transcoder = new PNGTranscoder();
            try {
                TranscoderInput input = new TranscoderInput(in);
                try {
                    TranscoderOutput output = new TranscoderOutput(out);
                    transcoder.transcode(input, output);
                } finally {
                    close(out);
                }
            } finally {
                close(in);
            }
        } catch (Throwable t) {
            throw new PPTShowGenerateException(t);
        }
    }

    public static long getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            return -1;
        }
        return file.length();
    }

}
