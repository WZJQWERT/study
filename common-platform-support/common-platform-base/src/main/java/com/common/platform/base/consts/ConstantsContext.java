package com.common.platform.base.consts;

import com.common.platform.base.enums.CommonStatus;
import com.common.platform.base.utils.CoreUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.common.platform.base.consts.ConfigConstant.SYSTEM_CONSTANT_PREFIX;
import static com.common.platform.base.utils.CoreUtil.getTempPath;

@Slf4j
public class ConstantsContext {

    private static final String TIPS_END = "，若想忽略此提示，请在开发管理->系统配置->参数配置，设置相关参数！";

    /**
     * 所有常量
     */
    private static Map<String,Object> CONSTNTS_HOLDER = new ConcurrentHashMap<>();

    /**
     * 添加常量
     */
    public static void putConstant(String key, Object value){
        if(CoreUtil.isOneEmpty(key,value)){
            return;
        }
        CONSTNTS_HOLDER.put(key,value);
    }

    /**
     * 删除常量
     */
    public static void deleteConstant(String key){
        if(CoreUtil.isOneEmpty(key)){
            return;
        }
        if(!key.startsWith(SYSTEM_CONSTANT_PREFIX)){
            CONSTNTS_HOLDER.remove(key);
        }
    }

    /**
     * 获取系统常量
     */
    public static Map<String,Object> getConstntsMap(){
        return CONSTNTS_HOLDER;
    }

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOpen(){
        String commonPlatformKaptchaOpen = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "KAPTCHA_OPEN");
        if(CommonStatus.ENABLE.getCode().equalsIgnoreCase(commonPlatformKaptchaOpen)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取管理系统名称
     */
    public static String getSystemName(){
        String systemName = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "SYSTEM_NAME");
        if(CoreUtil.isEmpty(systemName)){
            log.error("管理系统名称不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "SYSTEM_NAME" +"!使用默认名称：通用快速后台"+TIPS_END);
            return "通用快速后台";
        }else{
            return systemName;
        }
    }

    /**
     * 获取管理系统默认密码
     */
    public static String getDefaultPassword(){
        String defaultPassword = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "DEFAULT_PASSWORD");
        if(CoreUtil.isEmpty(defaultPassword)){
            log.error("管理系统默认密码不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "DEFAULT_PASSWORD" +"!使用默认密码：111111"+TIPS_END);
            return "111111";
        }else{
            return defaultPassword;
        }
    }

    /**
     * 获取管理系统OAUTH2用户前缀
     */
    public static String getOAuth2UserPrefix(){
        String oauth2Prefix = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "OAUTH2_PREFIX");
        if(CoreUtil.isEmpty(oauth2Prefix)){
            log.error("管理系统OAUTH2用户前缀不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "OAUTH2_PREFIX" +"!使用默认前缀：oauth2"+TIPS_END);
            return "oauth2";
        }else{
            return oauth2Prefix;
        }
    }

    /**
     * 获取管理系统顶部导航条是否开启
     */
    public static Boolean getDefaultAdvert(){
        String defaultAdvert = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "DEFAULT_ADVERT");
        if(CoreUtil.isEmpty(defaultAdvert)){
            log.error("管理系顶部导航条是否开启不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "DEFAULT_ADVERT" +"!使用默认：true"+TIPS_END);
            return true;
        }else{
            if(CommonStatus.ENABLE.getCode().equalsIgnoreCase(defaultAdvert)){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 获取管理系统发布的版本号
     */
    public static String getReleaseVersion(){
        String releaseVersion = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "RELEASE_VERSION");
        if(CoreUtil.isEmpty(releaseVersion)){
            log.error("管理系统发布的版本号不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "RELEASE_VERSION" +"!使用默认：随机数"+TIPS_END);
            return CoreUtil.getRandomString(8);
        }else{
            return releaseVersion;
        }
    }

    /**
     * 获取文件上传路径
     */
    public static String getFileUploadPath(){
        String fileUploadPath = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "FILE_UPLOAD_PATH");
        if(CoreUtil.isEmpty(fileUploadPath)){
            log.error("管理系统发布的文件上传路径不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "FILE_UPLOAD_PATH" +"!使用默认：系统临时路径"+TIPS_END);
            return getTempPath();
        }else{
            if(fileUploadPath.endsWith(File.separator)){
                fileUploadPath = fileUploadPath + File.separator;
            }
            File file = new File(fileUploadPath);
            if(!file.exists()){
                boolean mkdirs = file.mkdirs();
                if(mkdirs){
                    return fileUploadPath;
                }else{
                    log.error("管理系统发布的文件上传路径不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "FILE_UPLOAD_PATH" +"!使用默认：系统临时路径"+TIPS_END);
                    return getTempPath();
                }
            }else{
                return fileUploadPath;
            }
        }
    }

    /**
     * 获取系统密钥
     */
    public static String getJwtSecret(){
        String jwtSecret = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "JWT_SECRET");
        if(CoreUtil.isEmpty(jwtSecret)){
            String randomSecret = CoreUtil.getRandomString(32);
            CONSTNTS_HOLDER.put(SYSTEM_CONSTANT_PREFIX + "JWT_SECRET",randomSecret);
            log.error("管理系统发布的系统密钥不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "JWT_SECRET" +"!使用默认："+randomSecret+""+TIPS_END);
            return randomSecret;
        }else{
            return jwtSecret;
        }
    }

    /**
     * 获取系统密钥的过期时间
     */
    public static Long getJwtSecretExpireSec(){
        Long defaultSecs = 86400L;
        String jwtSecretSec = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "JWT_SECRET_EXPIRE");
        if(CoreUtil.isEmpty(jwtSecretSec)){
            log.error("管理系统发布的系统密钥的过期时间不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "JWT_SECRET_EXPIRE" +"!使用默认："+defaultSecs+""+TIPS_END);
            CONSTNTS_HOLDER.put(SYSTEM_CONSTANT_PREFIX + "JWT_SECRET_EXPIRE",String.valueOf(defaultSecs));
            return defaultSecs;
        }else{
           try{
               return Long.parseLong(jwtSecretSec);
           }catch (Exception e){
               log.error("管理系统发布的系统密钥的过期时间不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "JWT_SECRET_EXPIRE" +"!使用默认："+defaultSecs+""+TIPS_END);
               CONSTNTS_HOLDER.put(SYSTEM_CONSTANT_PREFIX + "JWT_SECRET_EXPIRE",String.valueOf(defaultSecs));
               return defaultSecs;
           }
        }
    }

    /**
     * 获取Token的Header标识
     */
    public static String getTokenHeaderName(){
        String tokenHeader = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "TOKEN_HEADER_NAME");
        if(CoreUtil.isEmpty(tokenHeader)){
            String defaultName = "Authorization";
            CONSTNTS_HOLDER.put(SYSTEM_CONSTANT_PREFIX + "TOKEN_HEADER_NAME",defaultName);
            log.error("管理系统Token的Header标识不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "TOKEN_HEADER_NAME" +"!使用默认："+defaultName+""+TIPS_END);
            return defaultName;
        }else{
            return tokenHeader;
        }
    }

    /**
     * 获取租户是否开启的开关
     */
    public static Boolean getTenantOpen(){
        String tenantOpen = (String) CONSTNTS_HOLDER.get(SYSTEM_CONSTANT_PREFIX + "TENANT_OPEN");
        if(CoreUtil.isEmpty(tenantOpen)){
            log.error("管理系统租户是否开启的开关不存在！常量名称是："+SYSTEM_CONSTANT_PREFIX + "TENANT_OPEN" +"!使用默认：false"+TIPS_END);
            return false;
        }else{
            return CommonStatus.ENABLE.getCode().equalsIgnoreCase(tenantOpen);
        }
    }

}
