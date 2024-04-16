package com.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projeto.repository.JDBC.PessoaRepositoryJDBC;

import jakarta.servlet.http.HttpSession;

@Controller
public class PessoaController {
    private final PessoaRepositoryJDBC pessoaRepository;

    public PessoaController(PessoaRepositoryJDBC pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @PostMapping("/login")
    public String login(@RequestParam String cpf, HttpSession session, Model model) {
        // Verifica se o CPF existe no banco de dados
        if (pessoaRepository.verificarCpf(cpf)) {
            // Armazena o CPF na sessão do usuário
            session.setAttribute("cpf", cpf);
            // Verifica se a senha está definida para o CPF
            String senha = pessoaRepository.buscarSenhaPorCpf(cpf);
            if (senha != null) {
                // Se a senha estiver definida, redireciona para a página de login com senha
                model.addAttribute("cpf", cpf);
                return "loginComSenha.html";
            } else {
                // Se não houver senha, redireciona para a página de criação de senha
                model.addAttribute("cpf", cpf);
                return "criarSenha.html";
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
        return "criarSenha";
    }

    @PostMapping("/atualizarSenha")
    public String atualizarSenha(@RequestParam("novaSenha") String novaSenha, HttpSession session) {
        String cpf = (String) session.getAttribute("cpf");
        if (cpf != null) {
            pessoaRepository.atualizarSenhaPorCpf(cpf, novaSenha);
            return "redirect:/home";
        } else {
            return "redirect:/pagina_de_erro";
        }
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
    }

    @PostMapping("/validarSenha")
    public String validarSenha(@RequestParam("senha") String senha, HttpSession session, Model model) {
        // Obtém o CPF da sessão
        String cpf = (String) session.getAttribute("cpf");
    
        // Verifica se o CPF está presente na sessão
        if (cpf == null) {
            // Se o CPF não estiver na sessão, redireciona para a página de login
            return "login";
        }
    
        // Verifica se a senha é válida para o CPF fornecido
        if (pessoaRepository.validarSenha(cpf, senha)) {
            // Obtém o tipo de usuário associado ao CPF
            String tipoUsuario = pessoaRepository.buscarTipoUsuarioPorCpf(cpf);
    
            // Redireciona com base no tipo de usuário
            switch (tipoUsuario) {
                case "Aluno":
                    return "homeAluno"; // Redireciona para a página do aluno
                case "Professor":
                    return "homeProfessor"; // Redireciona para a página do professor
                case "Admin":
                    return "homeAdmin"; // Redireciona para a página do administrador
                default:
                    // Se o tipo de usuário não for reconhecido, redireciona para uma página de erro
                    return "pagina_de_erro";
            }
        } else {
            // Se a senha for incorreta, exibe uma mensagem de erro na página de login
            model.addAttribute("error", "Senha incorreta");
            model.addAttribute("cpf", cpf); // Adiciona o CPF novamente ao modelo
            return "login";
        }
    }    
    

}
