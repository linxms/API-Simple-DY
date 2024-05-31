package org.example.apisimple_dy.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class User {
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private Integer role;

}
