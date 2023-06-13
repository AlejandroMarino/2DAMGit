package asymmetric.impl;

import asymmetric.Certificado;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

@Log4j2
public class CertificadoImpl implements Certificado {

    public CertificadoImpl() {
        //BouncyCastle - CA - certifica la clave pública
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @Override
    public Either<String, X509Certificate> certificatePublicKey(byte[] publicKey, PrivateKey serverPK) {
        Either<String, X509Certificate> certificate;
        try {
            X500Name owner = new X500Name("CN=Marino");
            X500Name issuer = new X500Name("CN=Server");
            X509v3CertificateBuilder certGenerator = new X509v3CertificateBuilder(
                    issuer,
                    BigInteger.valueOf(1),
                    new Date(),
                    new Date(System.currentTimeMillis() + 1000000),
                    owner,
                    SubjectPublicKeyInfo.getInstance(
                            ASN1Sequence.getInstance(publicKey))
            );

            ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(serverPK);
            X509CertificateHolder certHolder = certGenerator.build(signer);
            certificate = Either.right(new JcaX509CertificateConverter().getCertificate(certHolder));
        } catch (CertificateException | OperatorCreationException e) {
            log.error(e.getMessage(), e);
            certificate = Either.left("Error certificating public key");
        }
        return certificate;
    }

    @Override
    public Either<String, X509Certificate> getCertificateFromBase64(String certificate) {
        Either<String, X509Certificate> result;
        try {
            byte[] decodedCertificate = Base64.getUrlDecoder().decode(certificate);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate finalCertificate = (X509Certificate) cf
                    .generateCertificate(new ByteArrayInputStream(decodedCertificate));
            result = Either.right(finalCertificate);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            result = Either.left("Error");
        }
        return result;
    }

    @Override
    public Either<String, String> convertCertificateToBase64(X509Certificate certificate){
        Either<String, String> result;
        try {
            String cert = Base64.getUrlEncoder().encodeToString(certificate.getEncoded());
            result = Either.right(cert);
        } catch (CertificateEncodingException e) {
            result = Either.left("Error encoding certificate");
        }
        return result;
    }

    @Override
    public Either<String, PublicKey> getPublicKeyFromCertificateEncoded(String certificate) {
        return getCertificateFromBase64(certificate).map(X509Certificate::getPublicKey)
                .mapLeft(error -> {
                    log.error(error);
                    return error;
                });
    }

}
