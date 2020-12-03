package com.common.platform.sys.modular.system.controller;

import com.common.platform.base.consts.ConstantsContext;
import com.common.platform.sys.util.FileUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Autowired
    private Producer producer;

    /**
     * 生成验证码
     */
    @RequestMapping("")
    public void index(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");

        response.setContentType("image/jpeg");

        String capText = producer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY,capText);

        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            ImageIO.write(bi,"jpg",out);
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            try{
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }finally {
            try {
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回图片
     */
    @RequestMapping("/{pictureId}")
    public void renderPicture(@PathVariable("pictureId") String pictureId,HttpServletResponse response){
        String path = ConstantsContext.getFileUploadPath() + pictureId;
        try{
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            try {
                response.sendRedirect("/static/img/girl.gif");
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
