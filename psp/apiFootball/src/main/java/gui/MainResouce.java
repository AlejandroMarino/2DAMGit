package gui;

import lombok.SneakyThrows;

import java.util.Properties;

public class MainResouce {

    public static void main(String[] args) {


        MainResouce m = new MainResouce();
        m.loadPro();
    }



    @SneakyThrows
    public void loadPro()
    {
       String s = this.getClass().getClassLoader().getResource("config.properties").toString();
        Properties pro = new Properties();
        pro.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        pro.stringPropertyNames().stream().forEach(System.out::println);
        pro.entrySet().stream().map(objectObjectEntry -> objectObjectEntry.getKey() + " "
                + objectObjectEntry.getValue()).forEach(System.out::println);

        System.out.println(s);
    }
}
