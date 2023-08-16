package wantedpreonboarding.boardservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.jwt.BearerAuthInterceptor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final BearerAuthInterceptor bearerAuthInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(bearerAuthInterceptor)
			.addPathPatterns(
				"/api/articles/form"
			);
	}
}
