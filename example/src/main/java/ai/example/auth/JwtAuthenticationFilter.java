//package ai.example.auth;
//
//import ai.example.auth.service.CustomUserDetailsService;
//import ai.example.jwt.JwtUtil;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String token = jwtUtil.extractTokenFromRequest(request);
//
//        try {
//            if (token != null && jwtUtil.validateToken(token) != null) {
//                // 1. JWT에서 username 추출
//                String username = jwtUtil.extractUsername(token);
//
//                // 2. UserDetailsService를 사용해 UserDetails 조회
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//
//                // 3. 인증(Authentication) 객체를 생성
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // 4. SecurityContext에 인증 객체 저장
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (ExpiredJwtException ex) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("JWT Expired");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//}