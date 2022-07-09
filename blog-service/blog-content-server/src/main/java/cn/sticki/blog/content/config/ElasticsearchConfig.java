package cn.sticki.blog.content.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 10:22
 */
@Configuration
public class ElasticsearchConfig {

	@Resource
	ElasticsearchProperties properties;

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));
		RestClientBuilder restClientBuilder = RestClient.builder(HttpHost.create(properties.getUris().get(0)))
				.setHttpClientConfigCallback(httpAsyncClientBuilder
						-> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
		return new RestHighLevelClient(restClientBuilder);
	}

}
