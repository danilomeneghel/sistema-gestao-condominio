package condominio.controller;

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
    public String inicio() {
        return "redirect:/sindico";
    }

    @GetMapping("/login")
    public ModelAndView preLogin() {
        return new ModelAndView("fragmentos/layoutSite", "conteudo", "inicio");
    }

    @PostMapping("/sair")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?sair";
    }

}
