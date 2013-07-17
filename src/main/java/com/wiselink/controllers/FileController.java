/**
 * FileController.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-11 下午8:53:47
 */
package com.wiselink.controllers;

import java.io.File;
import java.io.IOException;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.wiselink.base.ApiStatus;
import com.wiselink.base.Config;
import com.wiselink.controllers.annotations.LoginRequired;
import com.wiselink.dao.AvatarDAO;
import com.wiselink.utils.ImageUtils;

/**
 * @author leo
 */
@LoginRequired
@Path("")
public class FileController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    private static final String DEFAULT_URL = "avatar/u.1.jpg";
    @Autowired
    private AvatarDAO avatarDao;

    @Post("avatar/up")
    public String uploadAvatar(@Param("userId") String userId, @Param("file") MultipartFile file) {
        LOGGER.info("got upload avatar request: user:{}, file:{}", userId, file.getOriginalFilename());
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException ex) {
            LOGGER.error("get bytes from file exception!", ex);
            return failResult(ApiStatus.INVALID_PARAMETER);
        }
        String url = Config.getInstance().avatarUrl(userId);
        String path = Config.getInstance().path(url);
        LOGGER.info("save:{} to :{}", url, path);
        try {
            avatarDao.add(url, bytes);
            ImageUtils.create(bytes, path);
        } catch (Exception ex) {
            LOGGER.error("save avatar got exception!", ex);
            return failResult(ApiStatus.DATA_INSERT_FAILED);
        }
        return successResult(url);
    }

    @Get("avatar/u/{userId:[0-9]+}")
    public String getAvatar2(@Param("userId") String userId) {
        String url = Config.getInstance().avatarUrl(userId);
        String path = Config.getInstance().path(url);
        if (new File(path).exists()) {
            return "../" + url;
        } else {
            return "../" + DEFAULT_URL;
        }
    }
}
