package com.common.platform.sys.modular.system.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.auth.pojo.LoginUser;
import com.common.platform.base.config.sys.DefaultAvatar;
import com.common.platform.base.consts.ConstantsContext;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.CoreExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.modular.system.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.entity.User;
import com.common.platform.sys.modular.system.mapper.FileInfoMapper;
import com.common.platform.sys.modular.system.model.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

/**
 * <p>
 * 文件信息表 服务类
 * </p>
 */
@Service
@Slf4j
public class FileInfoService extends ServiceImpl<FileInfoMapper,FileInfo> {

    @Autowired
    private UserService userService;
    /**
     * 更换头像
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String fileId){
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        if(loginUser==null){
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }
        User user = this.userService.getById(loginUser.getId());
        user.setAvatar(fileId);
        this.userService.updateById(user);
    }

    /**
     * 预览当前用户头像
     */
    public byte[] previewAvatar(){
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        if(loginUser==null){
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }
        User user = this.userService.getById(loginUser.getId());
        String avatar = user.getAvatar();
        if(CoreUtil.isEmpty(avatar)){
            return Base64.decode(DefaultAvatar.BASE_64_AVATAR);
        }else{
            FileInfo fileInfo = this.getById(avatar);
            if(fileInfo==null){
                return Base64.decode(DefaultAvatar.BASE_64_AVATAR);
            }else{
                try{
                    String filePath = fileInfo.getFilePath();
                    return IoUtil.readBytes(new FileInputStream(filePath));
                }catch (FileNotFoundException e){
                    log.error("头像未找到",e);
                    return Base64.decode(DefaultAvatar.BASE_64_AVATAR);
                }
            }
        }
    }

    /**
     * 上传文件 （默认文件路径）
     */
    public UploadResult uploadFile(MultipartFile file){
        String fileSavePath = ConstantsContext.getFileUploadPath();
        return this.uploadFile(file,fileSavePath);
    }

    /**
     * 上传文件 （指定路径）
     */
    public UploadResult uploadFile(MultipartFile file,String fileSavePath){
        UploadResult uploadResult = new UploadResult();
        String fileId = IdWorker.getIdStr();
        uploadResult.setFileId(fileId);

        String fileSuffix = CoreUtil.getFileSuffix(file.getOriginalFilename());
        uploadResult.setFileSuffix(fileSuffix);

        String originalFilename = file.getOriginalFilename();
        uploadResult.setOriginalFilename(originalFilename);

        String finalName = fileId+"."+fileSuffix;
        uploadResult.setFinalName(finalName);
        uploadResult.setFileSavePath(fileSavePath + finalName);

        try{
            File newFile = new File(uploadResult.getFileSavePath());
            file.transferTo(newFile);

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(fileId);
            fileInfo.setFileName(originalFilename);
            fileInfo.setFileSuffix(fileSuffix);
            fileInfo.setFilePath(uploadResult.getFileSavePath());
            fileInfo.setFileName(finalName);

            long kb = new BigDecimal(file.getSize()).divide(BigDecimal.valueOf(1024)).setScale(0,BigDecimal.ROUND_HALF_UP).longValue();
            fileInfo.setFileSizeKb(kb);
            this.save(fileInfo);
        }catch (Exception e){
            log.error("上传文件失败",e);
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return uploadResult;
    }
}
