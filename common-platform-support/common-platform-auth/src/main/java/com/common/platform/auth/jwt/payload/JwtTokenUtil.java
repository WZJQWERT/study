package com.common.platform.auth.jwt.payload;

import com.common.platform.base.consts.ConstantsContext;
import com.common.platform.base.utils.CoreUtil;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

/**
 *
 * <p>JWT工具</p>
 * <pre>
 *     JWT的Claim里一般包含的数据：
 *     1.iss -- token的发行者
 *     2.sub -- 该JWT所面向的用户
 *     3.aud -- 接收该JWT的一方
 *     4.exp -- token的失效时间
 *     5.nbf -- 在此时间段之前，不会别处理
 *     6.iat -- JWT的发布时间
 *     7.jti -- JWT唯一标识，防止重复使用
 * </pre>
 */

public class JwtTokenUtil {

    /**
     * 生成token，根据userId和默认的过期时间
     */
    public static String generateToken(JwtPayLoad jwtPayLoad){
        Long expiredSeconds = getExpireSeconds();
        final Date expirationDate = new Date(System.currentTimeMillis() + expiredSeconds*1000);
        return generateToekn(String.valueOf(jwtPayLoad.getUserId()),expirationDate,jwtPayLoad.toMap());
    }

    /**
     * 具体生成
     */
    public static String generateToekn(String userId, Date expirationDate, Map<String,Object> claims){
        final Date createDate = new Date();
        String secret = getJwtSecret();
        if(claims==null){
            return Jwts.builder()
                    .setSubject(userId)
                    .setIssuedAt(createDate)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512,secret)
                    .compact();
        }else{
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userId)
                    .setIssuedAt(createDate)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512,secret)
                    .compact();
        }
    }

    /**
     * 获取JWT的PayLoad部分
     */
    public static JwtPayLoad getJwtPayLoad(String token){
        Claims claims = getClaimFromToken(token);
        return JwtPayLoad.toBean(claims);
    }

    /**
     *解析token是否正确
     */
    public static  Boolean checkToken(String token){
        try{
            String jwtSecret = getJwtSecret();
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    /**
     * 验证token是否过期
     */
    public static Boolean isTokenExpired(String token){
        try{
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }
    }

    public static Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token).getExpiration();
    }

    public static Claims getClaimFromToken(String token){
        if(CoreUtil.isEmpty(token)){
            throw new IllegalArgumentException("token参数为空");
        }
        String jwtSecret= getJwtSecret();
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    private static String getJwtSecret(){
        return ConstantsContext.getJwtSecret();
    }

    private static Long getExpireSeconds(){
        return ConstantsContext.getJwtSecretExpireSec();
    }
}
