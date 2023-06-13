package cc.pptshow.ppt.util;

import com.google.common.base.Strings;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cc.pptshow.ppt.constant.Constant.PPTX;
import static cc.pptshow.ppt.constant.Constant.SEPARATOR;
import static cc.pptshow.ppt.util.FileUtil.tmpdir;
import static java.lang.Character.UnicodeBlock.*;

/**
 * PPT工具类，注意一些方法【仅支持】Windows!
 */
public class PPTUtil {

    private static final String vbs_1 = "Option Explicit\n" +
            " \n" +
            "PPT2ANY \"";
    private static final String vbs_2 = "\",\"";
    private static final String vbs_3 = "\",\"";
    private static final String vbs_4 = "\"\n" +
            " \n" +
            "Sub PPT2ANY( inFile, outFile, outFormat)\n" +
            "    Dim objFSO, objPPT, objPresentation, pptFormat\n" +
            " \n" +
            "    Const ppSaveAsAddIn                             =8\n" +
            "    Const ppSaveAsBMP                               =19\n" +
            "    Const ppSaveAsDefault                           =11\n" +
            "    Const ppSaveAsEMF                               =23\n" +
            "    Const ppSaveAsExternalConverter                 =64000\n" +
            "    Const ppSaveAsGIF                               =16\n" +
            "    Const ppSaveAsJPG                               =17\n" +
            "    Const ppSaveAsMetaFile                          =15\n" +
            "    Const ppSaveAsMP4                               =39\n" +
            "    Const ppSaveAsOpenDocumentPresentation          =35\n" +
            "    Const ppSaveAsOpenXMLAddin                      =30\n" +
            "    Const ppSaveAsOpenXMLPicturePresentation        =36\n" +
            "    Const ppSaveAsOpenXMLPresentation               =24\n" +
            "    Const ppSaveAsOpenXMLPresentationMacroEnabled   =25\n" +
            "    Const ppSaveAsOpenXMLShow                       =28\n" +
            "    Const ppSaveAsOpenXMLShowMacroEnabled           =29\n" +
            "    Const ppSaveAsOpenXMLTemplate                   =26\n" +
            "    Const ppSaveAsOpenXMLTemplateMacroEnabled       =27\n" +
            "    Const ppSaveAsOpenXMLTheme                      =31\n" +
            "    Const ppSaveAsPDF                               =32\n" +
            "    Const ppSaveAsPNG                               =18\n" +
            "    Const ppSaveAsPresentation                      =1\n" +
            "    Const ppSaveAsRTF                               =6\n" +
            "    Const ppSaveAsShow                              =7\n" +
            "    Const ppSaveAsStrictOpenXMLPresentation         =38\n" +
            "    Const ppSaveAsTemplate                          =5\n" +
            "    Const ppSaveAsTIF                               =21\n" +
            "    Const ppSaveAsWMV                               =37\n" +
            "    Const ppSaveAsXMLPresentation                   =34\n" +
            "    Const ppSaveAsXPS                               =33\n" +
            " \n" +
            "    ' Create a File System object\n" +
            "    Set objFSO = CreateObject( \"Scripting.FileSystemObject\" )\n" +
            " \n" +
            "    ' Create a PowerPoint object\n" +
            "    Set objPPT = CreateObject( \"PowerPoint.Application\" )\n" +
            " \n" +
            "    With objPPT\n" +
            "        ' True: make PowerPoint visible; False: invisible\n" +
            "        .Visible = True\n" +
            "  \n" +
            "        ' Check if the PowerPoint document exists\n" +
            "        If not( objFSO.FileExists( inFile ) ) Then\n" +
            "            WScript.Echo \"FILE OPEN ERROR: The file does not exist\" & vbCrLf\n" +
            "            ' Close PowerPoint\n" +
            "            .Quit\n" +
            "            Exit Sub\n" +
            "        End If\n" +
            "  \n" +
            "        ' Open the PowerPoint document\n" +
            "        .Presentations.Open inFile\n" +
            "  \n" +
            "        ' Make the opened file the active document\n" +
            "        Set objPresentation = .ActivePresentation\n" +
            "  \n" +
            "        If StrComp(Ucase( outFormat ),\"PDF\") = 0 then\n" +
            "            pptFormat = ppSaveAsPDF \n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"XPS\") = 0 then\n" +
            "            pptFormat = ppSaveAsXPS\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"BMP\") = 0 then\n" +
            "            pptFormat= ppSaveAsBMP\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"PNG\") = 0 then\n" +
            "            pptFormat= ppSaveAsPNG\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"JPG\") = 0 then\n" +
            "            pptFormat= ppSaveAsJPG\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"GIF\") = 0 then\n" +
            "            pptFormat= ppSaveAsGIF\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"XML\") = 0 then\n" +
            "            pptFormat= ppSaveAsOpenXMLPresentation\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"RTF\") = 0 then\n" +
            "            pptFormat= ppSaveAsRTF\n" +
            "        ElseIf StrComp(Ucase( outFormat ),\"MP4\") = 0 then\n" +
            "            pptFormat= ppSaveAsMP4\n" +
            "        Else\n" +
            "            WScript.Echo \"FILE FORTMART ERROR: Unknown file format\" & vbCrLf\n" +
            "            ' Close PowerPoint\n" +
            "            .Quit\n" +
            "            Exit Sub\n" +
            "        End If\n" +
            " \n" +
            "        ' Save in PDF/XPS format\n" +
            "        objPresentation.SaveAs outFile, pptFormat\n" +
            "        ' save as MP4 need long time, do not close too early\n" +
            "\t\tDim fs, ts\n" +
            "\t\tset fs = CreateObject(\"Scripting.FileSystemObject\")\n" +
            "        If StrComp(Ucase( outFormat ),\"MP4\") = 0 then\n" +
            "\t\t\tWhile fs.FileExists(outFile & \".\" & outFormat & \".wait\")\n" +
            "\t\t\t  wscript.sleep 1000\n" +
            "\t\t\tWend\n" +
            "        End If\n" +
            "        ' Close the active document\n" +
            "        objPresentation.Close\n" +
            "  \n" +
            "        ' Close PowerPoint\n" +
            "        .Quit\n" +
            "    End With\n" +
            "End Sub\n";

    public static final String _MP4 = ".mp4";
    public static final String _MP4_WAIT = ".mp4.wait";
    public static final String _VBS = ".vbs";
    public static final String MP4 = "MP4";
    public static final String PNG = "PNG";
    public static final String _PNG = ".PNG";
    public static final String PPT_NAME = "幻灯片";

    public static final int LONG_IMG_X = 1280;
    public static final int LONG_IMG_Y = 720;
    public static final int LONG_IMG_HALF_Y = LONG_IMG_Y / 2;
    public static final int LONG_IMG_HALF_X = LONG_IMG_X / 2;

    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    public static synchronized void PPT2MP4(String pptPath, String mp4Path) {
        try {
            if (!isWindows()) {
                throw new RuntimeException("抱歉，此功能依赖于系统OFFICE组件，必须使用Windows系统");
            }
            if (mp4Path.toLowerCase().endsWith(_MP4)) {
                mp4Path = mp4Path.substring(0, mp4Path.length() - _MP4.length());
            }
            String vbs = vbs_1 + pptPath + vbs_2 + mp4Path + vbs_3 + MP4 + vbs_4;
            String name = System.currentTimeMillis() + round(1, 9999999) + _VBS;
            String path = tmpdir() + SEPARATOR + name;
            FileUtil.write(path, vbs);
            //如果存在则删除
            String mp4AllPath = mp4Path + _MP4;
            File mp4File = new File(mp4AllPath);
            if (mp4File.exists()) {
                mp4File.delete();
            }
            //由于权限原因，不能让vbs直接检测生成的MP4文件文件大小
            //直接检测有可能会出现文件路径找不到
            String waitPath = mp4Path + _MP4_WAIT;
            System.out.println(waitPath);
            //输出什么无所谓，这个文件是一个标识文件
            FileUtil.write(waitPath, "1");
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            pool.execute(() -> {
                while (FileUtil.getFileSize(mp4AllPath) <= 0) {
                    try {
                        Thread.sleep(10);
                    } catch (Throwable ignored) {
                    }
                }
                new File(waitPath).delete();
                countDownLatch.countDown();
            });
            String[] cmd = new String[]{"wscript", path};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor(1, TimeUnit.SECONDS);
            new File(path).delete();
            countDownLatch.await();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void PPT2PNG(String pptPath, String jpgPath) {
        try {
            if (!isWindows()) {
                throw new RuntimeException("抱歉，此功能依赖于系统OFFICE组件，必须使用Windows系统");
            }
            String catchPath = null;
            if (checkStringContainChinese(pptPath)) {
                catchPath = tmpdir() + "png" + UUID.randomUUID().toString().replace("-", "") + PPTX;
                FileUtil.traditionalCopy(pptPath, catchPath);
            }
            String vbs;
            if (Strings.isNullOrEmpty(catchPath)) {
                vbs = vbs_1 + pptPath + vbs_2 + jpgPath + vbs_3 + PNG + vbs_4;
            } else {
                vbs = vbs_1 + catchPath + vbs_2 + jpgPath + vbs_3 + PNG + vbs_4;
            }
            String name = System.currentTimeMillis() + round(1, 9999999) + _VBS;
            String path = tmpdir() + SEPARATOR + name;
            FileUtil.write(path, vbs);
            String[] cmd = new String[]{"wscript", path};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            new File(path).delete();
            if (Strings.isNullOrEmpty(catchPath)) {
                new File(catchPath).delete();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void png2LongImg(String pngPath, String longImgPath, String waterImgPath) {
        try {
            File file = new File(pngPath);
            List<File> pngs = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter(f -> f.isFile() && f.getPath().endsWith(_PNG))
                    .sorted((x, y) -> {
                        String xName = x.getName()
                                .replace(_PNG, "").replace(PPT_NAME, "");
                        int xNum = Integer.parseInt(xName);
                        String yName = y.getName()
                                .replace(_PNG, "").replace(PPT_NAME, "");
                        int yNum = Integer.parseInt(yName);
                        return Integer.compare(xNum, yNum);
                    })
                    .collect(Collectors.toList());
            int height = pngs.size() % 2 == 0
                    ? (LONG_IMG_HALF_Y * (pngs.size() / 2) + LONG_IMG_Y)
                    : (LONG_IMG_HALF_Y * ((pngs.size() - 1) / 2) + LONG_IMG_Y);
            BufferedImage thumbImage = new BufferedImage(LONG_IMG_X, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = thumbImage.createGraphics();
            Image src = javax.imageio.ImageIO.read(pngs.get(0));
            graphics.drawImage(src.getScaledInstance(LONG_IMG_X, LONG_IMG_Y, Image.SCALE_SMOOTH), 0, 0, null);
            boolean newHeight = true;
            int longY = LONG_IMG_HALF_Y;
            int longX = 0;
            for (int i = 0; i < pngs.size(); i++) {
                if (i == 0 && pngs.size() % 2 == 1) {
                    continue;
                }
                if (newHeight) {
                    longY += LONG_IMG_HALF_Y;
                    newHeight = false;
                    longX = 0;
                } else {
                    longX = LONG_IMG_HALF_X;
                    newHeight = true;
                }
                Image longSrc = javax.imageio.ImageIO.read(pngs.get(i));
                graphics.drawImage(longSrc.getScaledInstance(LONG_IMG_HALF_X, LONG_IMG_HALF_Y, Image.SCALE_SMOOTH)
                        , longX, longY, null);
            }

            //画上水印
            File water = new File(waterImgPath);
            Image longSrc = javax.imageio.ImageIO.read(water);
            graphics.drawImage(longSrc.getScaledInstance(200, 54, Image.SCALE_SMOOTH)
                    , round(0, LONG_IMG_X - 200), round(0, LONG_IMG_Y - 54), null);

            for (int i = 0; i < 5; i++) {
                graphics.drawImage(longSrc.getScaledInstance(200, 54, Image.SCALE_SMOOTH)
                        , round(0, LONG_IMG_X - 200), round(LONG_IMG_Y, height), null);
            }

            ImageIO.write(thumbImage, "png", new File(longImgPath));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @SneakyThrows
    public static void mp4Compression(String bigMp4Path, String smallMp4Path) {
        if (!isWindows()) {
            throw new RuntimeException("抱歉，此功能是windows定制化代码，不支持其他环境！");
        }
        Runtime runtime = Runtime.getRuntime();
        String cutCmd = "ffmpeg -i " + bigMp4Path + " -r 15 -b:v 400k -s 1280x720 " + smallMp4Path + " -loglevel quiet";
        runtime.exec(cutCmd);
    }

    private static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toLowerCase().contains("windows");
    }

    private static int round(int min, int max) {
        final double d = Math.random();
        return ((int) Math.floor(d * (max - min + 1))) + min;
    }

    private static boolean checkStringContainChinese(String checkStr){
        if(!Strings.isNullOrEmpty(checkStr)){
            char[] checkChars = checkStr.toCharArray();
            for(int i = 0; i < checkChars.length; i++){
                char checkChar = checkChars[i];
                if(checkCharContainChinese(checkChar)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkCharContainChinese(char checkChar){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);
        return CJK_UNIFIED_IDEOGRAPHS == ub || CJK_COMPATIBILITY_IDEOGRAPHS == ub || CJK_COMPATIBILITY_FORMS == ub ||
                CJK_RADICALS_SUPPLEMENT == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub
                || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub;
    }

    public static void main(String[] args) {
        mp4Compression("C:/Users/qrp19/Desktop/20220224103341.mp4",
                "C:/Users/qrp19/Desktop/small.mp4");
    }
}
