package cn.enjoyedu.nettybasic.embedded;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Guanpin
 * @create 2020-09-15-22:36
 * @Description
 **/
public class lomb {



        public static Map<String, Object> parseMapForFilterByOptional(Map<String, Object> map) {

        return Optional.ofNullable(map).map(
                (v) -> {
                    Map params = v.entrySet().stream()
                            .filter((e) -> checkValueByOptional(e.getValue()))
                            .collect(Collectors.toMap(
                                    (e) -> (String) e.getKey(),
                                    (e) -> e.getValue()
                            ));

                    return params;
                }
        ).orElse(null);
    }

    private static boolean checkValueByOptional(Object object) {
        return (Boolean) Optional.ofNullable(object)
                .filter((e) -> e instanceof String && e.equals("") ? false : true)
                .orElse(false);
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>(16);

        params.put("a", "");
        params.put("b", null);
        params.put("c", "c");


        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");

        //Optional<List<String>> strings = Optional.ofNullable(params).map((k) -> list.stream().filter(page -> !k.equals(page)).collect(Collectors.toList()));
        params.forEach((k, v) -> {
            List<String> collect = list.stream().filter(page -> !k.equals(page)).collect(Collectors.toList());
            collect.forEach(System.out::println);
        });


       // params = parseMapForFilterByOptional(params);

       // System.out.println(params);
       // System.out.println(parseMapForFilterByOptional(null));
    }




     static class Student {

        private String stuId;

        private String name;

        private String age;

        private String sex;

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
