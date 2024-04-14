package com.projeto.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.projeto.repository.JDBC.PessoaRepositoryJDBC;

@Controller
public class PessoaController {
    private final PessoaRepositoryJDBC pessoaRepository;

    public PessoaController(PessoaRepositoryJDBC pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }



    @PostMapping("/login")
    public String login(@RequestParam String cpf, Model model) {
        // Verifica se o CPF existe no banco de dados
        if (pessoaRepository.verificarCpf(cpf)) {
            // Se existir, redireciona para a página de criação de senha
            model.addAttribute("cpf", cpf);
            return "redirect:/criarSenha.html";
        } else {
            // Se não existir, exibe uma mensagem de erro
            model.addAttribute("error", "CPF não cadastrado");
            return "login";
        }
    }

    @GetMapping("/criarSenha")
    public String showCreatePasswordPage(@RequestParam String cpf, Model model) {
        model.addAttribute("cpf", cpf);
        return "criarSenha"; // Retornar a página de criação de senha
    }
}
