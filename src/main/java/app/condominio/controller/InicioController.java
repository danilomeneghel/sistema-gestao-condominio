package app.condominio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class InicioController {

    @ModelAttribute("ativo")
    public String[] ativo() {
        return new String[]{"inicio", ""};
    }

    @GetMapping({"/", "", "/home", "/inicio"})
    public ModelAndView inicio() {
        return new ModelAndView("fragmentos/layoutSite", "conteudo", "inicio");
    }

    @GetMapping("/login")
    public ModelAndView preLogin() {
        return new ModelAndView("fragmentos/layoutSite", "conteudo", "inicio");
    }

    @GetMapping("/entrar")
    public String posLogin() {
        return "redirect:/sindico";
    }

    /*@GetMapping("/autenticado")
    public String posLogin(Authentication authentication) {
        String retorno = "redirect:/inicio?erro";
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("SINDICO"))) {
            retorno = "redirect:/sindico";
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("CONDOMINO"))) {
            retorno = "redirect:/condomino";
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            retorno = "redirect:/admin";
        }
        return retorno;
    }*/

    @PostMapping("/sair")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?sair";
    }

}
