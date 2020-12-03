package com.common.platform.sys.util;

import com.common.platform.base.exception.CoreExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static byte[] toByteArray(String filename){
        File file = new File(filename);
        if(!file.exists()){
            logger.error("文件未找到"+filename);
            throw new ServiceException(CoreExceptionEnum.FILE_NOT_FOUND);
        }
        FileChannel channel = null;
        FileInputStream fileInputStream = null;

        try{
            fileInputStream = new FileInputStream(file);
            channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer))>0){

            }
            return byteBuffer.array();
        }catch (IOException e){
            throw new ServiceException(CoreExceptionEnum.FILE_READING_ERROR);
        }finally {
            try{
                channel.close();
            }catch (IOException e){
                throw new ServiceException(CoreExceptionEnum.FILE_READING_ERROR);
            }
            try{
                fileInputStream.close();
            }catch (IOException e){
                throw new ServiceException(CoreExceptionEnum.FILE_READING_ERROR);
            }
        }
    }
}
