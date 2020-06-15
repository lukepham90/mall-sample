package com.uuhnaut69.search.config.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
public class RestClientElasticConfig {

  @Value("${elasticsearch.host}")
  private String elasticHost;

  @Value("${elasticsearch.port}")
  private int elasticPort;

  @Value("${username}")
  private String username;

  @Value("${password}")
  private String password;

  /** Config RestHighLevelClient with https connection and auth */
  @Bean
  public RestHighLevelClient client() {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
        AuthScope.ANY, new UsernamePasswordCredentials(username, password));
    RestClientBuilder builder =
        RestClient.builder(new HttpHost(elasticHost, elasticPort, "http"))
            .setHttpClientConfigCallback(
                httpClientBuilder ->
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

    return new RestHighLevelClient(builder);
  }
}
