package study.jdk8.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    /** 编号 */
    private Integer id;
    /** 姓名 */
    private String name;
    /** 年龄 */
    private Integer age;
    /** 工资 */
    private Double salary;

}
