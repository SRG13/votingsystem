package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestUtil {

    public static final int NOT_FOUND_ID = 10;

    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromResultActions(ResultActions resultActions, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(resultActions.andReturn()), clazz);
    }

    public static <T> List<T> readListFromResultActions(ResultActions resultActions, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(resultActions.andReturn()), clazz);
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }
}
