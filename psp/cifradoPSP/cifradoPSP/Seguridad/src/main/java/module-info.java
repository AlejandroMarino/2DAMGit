module Seguridad {
    requires org.bouncycastle.pkix;
    requires org.bouncycastle.provider;
    requires io.vavr;
    requires lombok;
    requires Domain;
    requires org.apache.logging.log4j;
    requires com.google.common;

    exports asymmetric;
    exports symmetric;
    exports asymmetric.impl;
    exports symmetric.impl;
}