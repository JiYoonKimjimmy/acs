package me.jimmyberg.acs.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@Configuration
class ElasticClientConfig(
    private val resourceLoader: ResourceLoader,
    @Value("\${spring.elasticsearch.host}") private val host: String,
    @Value("\${spring.elasticsearch.username}") private val username: String,
    @Value("\${spring.elasticsearch.password}") private val password: String
) : ElasticsearchConfiguration() {

    override fun clientConfiguration(): ClientConfiguration {
        val certification = getCertification()
        val keyStore = getKeyStore(certification)
        val trustManager = getTrustManager(keyStore)
        val sslContext = getSSLContext(trustManager)
        return ClientConfiguration
            .builder()
            .connectedTo(host)
            .usingSsl(sslContext)
            .build()
    }

    private fun getCertification(): Certificate {
        val resource = resourceLoader.getResource("classpath:http_ca.crt")
        val certificate = CertificateFactory.getInstance("X.509").generateCertificate(resource.inputStream)
        return certificate
    }

    private fun getKeyStore(certificate: Certificate): KeyStore {
        val keyStore = KeyStore.getInstance("JKS")
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)
        return keyStore
    }

    private fun getTrustManager(keyStore: KeyStore): TrustManagerFactory {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        return trustManagerFactory
    }

    private fun getSSLContext(trustManagerFactory: TrustManagerFactory): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)
        return sslContext
    }

    private fun getClientConfiguration(sslContext: SSLContext): ClientConfiguration {
        return ClientConfiguration
            .builder()
            .connectedTo(host)
            .usingSsl(sslContext)
            .withBasicAuth(username, password)
            .build()
    }

}