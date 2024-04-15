package com.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.projeto.model.Pessoa;
import com.projeto.Util.ValidaSenha;
import com.projeto.repository.JDBC.PessoaRepositoryJDBC;
import com.projeto.repository.PessoaRepository;

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
            // Verifica se a senha está definida para o CPF
            String senha = pessoaRepository.buscarSenhaPorCpf(cpf);
            if (senha == null) {
                // Se não houver senha, redireciona para a página de criação de senha
                model.addAttribute("cpf", cpf);
                return "criarSenha.html";
                if (ValidaSenha.validaSenha(senha)){
                    pessoaRepository.save(pessoa);
                    return "home.html";
                }
            } else {
                // Se a senha estiver definida, redireciona para a página de login com senha
                model.addAttribute("cpf", cpf);
                return "loginComSenha.html";
            }
        } else {
            // Se o CPF não existir, exibe uma mensagem de erro
            model.addAttribute("error", "CPF não cadastrado");
            return "login";
        }
    }

    @GetMapping("/criarSenha")
    public String showCreatePasswordPage(@RequestParam String cpf, Model model) {
        model.addAttribute("cpf", cpf);
        return "criarSenhja"; // Retornar a página de criação de senha
    } 
}
