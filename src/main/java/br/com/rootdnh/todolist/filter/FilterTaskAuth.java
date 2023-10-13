package br.com.rootdnh.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rootdnh.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var servletPath = request.getServletPath();
    if (servletPath.equals("/tasks/")) {

      var autorization = request.getHeader("Authorization");
      var authEncoded = autorization.substring("Basic".length()).trim();
      byte[] authDecode = Base64.getDecoder().decode(authEncoded);
      var authString = new String(authDecode);

      String[] credential = authString.split(":");
      String username = credential[0];
      String password = credential[1];

      var user = this.userRepository.findByName(username);
      if (user == null) {
        response.sendError(401);
      } else {
        var passVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (passVerify.verified) {
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

}
