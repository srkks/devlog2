package com.devlog.config.auth;

import com.devlog.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console을 사용하기 위해 해당 옵션 비활성화
                .and()
                .authorizeRequests()//url별 권한 관리 설정하는 옵션의 시작점. 이게 선언돼야만 antMatchers 옵션 사용 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() //설정된 값 외 나머지 url, authenticated로 나머지url들은 모두 인증된 사용자(로그인)에게만 허용하게 함
                .and()
                .logout() //로그아웃 기능에 대한 여러 설정의 진입점.
                .logoutSuccessUrl("/") //로그아웃시 이동할 url
                .and()
                .oauth2Login()// oauth2 로그인 기능에 대한 여러 설정의 진입점.
                .userInfoEndpoint()// oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속조치를 진행할 UserService인터페이스의 구현체 등록
    }
}
