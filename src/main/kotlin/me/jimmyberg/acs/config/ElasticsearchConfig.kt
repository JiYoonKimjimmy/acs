package me.jimmyberg.acs.config

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import java.nio.file.Paths
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@Configuration
class ElasticsearchConfig(
    @Value("\${spring.elasticsearch.host}") val host: String,
    @Value("\${spring.elasticsearch.username}") val username: String,
    @Value("\${spring.elasticsearch.password}") val password: String
) {

    @Bean
    @Primary
    fun restHighLevelClient(): RestHighLevelClient {
        val path = Paths.get("/Users/jim/Desktop/00_kjy/01_dev/workspace/demo-acs/elastic/http_ca.crt")
        // `X.509` 형식 인증서 처리 객체 생성
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificate = certificateFactory.generateCertificate(path.toFile().inputStream())

        // `JKS` 형식 Key & 인증서 저장소 객체 생성
        val keyStore = KeyStore.getInstance("JKS")
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)

        // `TrustManagerFactory`: SSL 통신 서버의 신뢰 여부 확인 역할 수행
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        // `TLS` 암호화 프로토콜 사용 SSL Context 객체 생
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        return RestClients.create(
            ClientConfiguration
                .builder()
                .connectedTo(host)
                .usingSsl(sslContext)
                .withBasicAuth(username, password)
                .build()
        ).rest()
    }

}