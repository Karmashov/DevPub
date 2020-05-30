package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "captcha_codes")
public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    //@TODO Дата?
    private Date time;

    @NotNull
    @Column(columnDefinition = "tinytext")
    private String code;

    @NotNull
    @Column(columnDefinition = "tinytext")
    private String secretCode;
}
