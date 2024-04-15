package com.projeto.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            }
            // Se a senha estiver definida, redireciona para a página de login com senha
            model.addAttribute("cpf", cpf);
            return "loginComSenha.html";
        }
        // Se o CPF não existir, exibe uma mensagem de erro
        model.addAttribute("error", "CPF não cadastrado");
        return "login";
    }

    @GetMapping("/criarSenha")
    public String showCreatePasswordPage(@RequestParam String cpf, Model model) {
        model.addAttribute("cpf", cpf);
        return "criarSenha"; // Retornar a página de criação de senha
    }

    @PostMapping("/salvarSenha")
    public String savePassword(@RequestParam String cpf, @RequestParam String senha, Model model) {
        // Verifica se o CPF existe no banco de dados
        if (pessoaRepository.verificarCpf(cpf)) {
            // Cria uma nova instância de Pessoa com os dados fornecidos
            Pessoa pessoa = new Pessoa(0, "", null, cpf, "", senha);

            // Salva a senha no banco de dados
            pessoaRepository.save(pessoa);

            // Redireciona para a página de login com a senha recém-criada
            model.addAttribute("cpf", cpf);
            return "loginComSenha.html";
        } else {
            // Se o CPF não existir, exibe uma mensagem de erro
            model.addAttribute("error", "CPF não cadastrado1");
            return "login";
        }
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        // Adicione aqui lógica adicional, se necessário
        return "home"; // Retorna o nome da página HTML para exibir
    }

}
