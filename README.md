# pptshow
Language：English（[中文](https://gitee.com/qiruipeng/pptshow)）

### Reference document address
Language：[中文文档](https://github.com/qrpcode/pptshow/wiki/%E4%B8%AD%E6%96%87%E6%96%87%E6%A1%A3)  [English Documents](https://github.com/qrpcode/pptshow/wiki/English-Documents)   
You can also go to the [official website](https://pptshow.cc) to view the Chinese documentation, the content is consistent and more comfortable to read  

### Introduction

Java to generate PPT documents toolkit, support the new features of the 2010 version of PPTX 

### Feel it first
For example, the following PPT is made entirely with this Jar package  
Including this video is also generated automatically through the Jar package  

https://user-images.githubusercontent.com/53297393/245331343-43c1cd94-e3b3-41d2-9efa-93b4a7cad0a7.mp4

### Function Support
##### PPT basic editing functions
1. PPT multiple pages
2. PPT background music
3. PPT switching animation, automatic timing switching
4. PPT text, picture, shape and other elements insertion support
5. entrance animation of PPT elements

##### PPT extended features
1. PPT to generate pictures (fonts will not be messed up, only available in Windows environment)
2. PPT generate MP4 video (fonts will not be messed up, only available in Windows environment)

##### PPT reading
1. Read information according to the specified PPT page file

#### 10 seconds to get started
You can use maven to add jar package references, or directly download jar packages and manually import

##### Maven coordinates [recommended]
```xml
<dependency>
  <groupId>cc.pptshow</groupId>
  <artifactId>pptshow</artifactId>
  <version>1.3</version>
</dependency>
```
##### Download and import manually [not recommended]
Download address:https://s01.oss.sonatype.org/service/local/repositories/releases/content/cc/pptshow/pptshow/1.3/pptshow-1.3.jar  

After successful import, create a new Main class, copy the following code and paste it in:
 ```java
import cc.pptshow.ppt.domain.*;
import cc.pptshow.ppt.element.impl.*;
import cc.pptshow.ppt.show.PPTShow;
import cc.pptshow.ppt.show.PPTShowSide;

public class Main {

    public static void main(String[] args) {
        //Create a new PPT object
        PPTShow pptShow = PPTShow.build();
        //Create a new PPT page
        PPTShowSide side = PPTShowSide.build();
        
        //Create an in-line text object with the text set to Hello World
        PPTInnerText pptInnerText = PPTInnerText.build("Hello World");
        //Create an in-line text style object so that the text color is red
        PPTInnerTextCss pptInnerTextCss = PPTInnerTextCss.build().setColor("FF00000");
        //Binding in-line text and style objects
        pptInnerText.setCss(pptInnerTextCss);
        
        //Create a line text object from in-line text and create a text object from a line text object
        PPTText pptText = PPTText.build(PPTInnerLine.build(pptInnerText));
        //Adding text objects to a PPT page
        side.add(pptText);
        //Add PPT page inside PPT
        pptShow.add(side);
        
        //Output to file
        pptShow.toFile("C:/Users/xxx/Desktop/test.pptx");
    }

}

 ```


### Jar package description

The common PPT generation process does not rely on system or third-party components for any function  
Linux or Windows systems can be used normally  
Video and image generation for Windows is achieved by calling Windows Office through vbs, only these two functions depend on Windows system

### Licensing Protocol
Apache-2.0 License Agreement  
i.e. for commercial use, modification, distribution, proprietary use, private use  
However, registration as a trademark is not permitted, and no responsibility or guarantee of availability is given for the program.
