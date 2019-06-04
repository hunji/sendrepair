package com.youhualife.modules.sys.form;

import lombok.Data;

/**
 * 密码表单
 *
 * @author hunji
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

}
