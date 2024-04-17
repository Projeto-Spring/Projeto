package com.projeto.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projeto.model.Admin;
import com.projeto.model.Aluno;
import com.projeto.model.Pessoa;
import com.projeto.model.Professor;
import com.projeto.model.Turma;
import com.projeto.repository.JDBC.PessoaRepositoryJDBC;

import jakarta.servlet.http.HttpSession;

@Controller
public class PessoaController {
    private final PessoaRepositoryJDBC pessoaRepository;

    public PessoaController(PessoaRepositoryJDBC pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping("/")
    public String primeiraPagina(Model model) {
        return "login";
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
            // Obtém o tipo de usuário associado ao CPF
            String tipoUsuario = pessoaRepository.buscarTipoUsuarioPorCpf(cpf);

            // Redireciona com base no tipo de usuário
            switch (tipoUsuario) {
                case "Aluno":
                    return "redirect:/homeAluno"; // Redireciona para a página do aluno
                case "Professor":
                    return "redirect:/homeProfessor"; // Redireciona para a página do professor
                case "Admin":
                    return "redirect:/homeAdmin"; // Redireciona para a página do administrador
                default:
                    // Se o tipo de usuário não for reconhecido, redireciona para uma página de erro
                    return "redirect:/pagina_de_erro";
            }
        } else {
            return "redirect:/pagina_de_erro";
        }
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/homeAluno")
    public String homeAluno(Model model) {
        return "homeAluno";
    }

    @GetMapping("/homeProfessor")
    public String homeProfessor(Model model) {
        return "homeProfessor";
    }

    @GetMapping("/homeAdmin")
    public String homeAdmin(Model model) {
        return "homeAdmin";
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

    // GETMAPPING DA TELA DE CADASTRAR UM ADMIN
    @GetMapping("/adminCadastrarAdmin")
    public String adminCadastrarAdmin(Model model) {
        return "adminCadastrarAdmin";
    }

    // CODIGO QUE PUXA O METODO DO FORM DE CADASTRO DE ADMIN
    @PostMapping("/validarCadastroAdmin")
    public String validarCadastroAdmin(@RequestParam String nome,
            @RequestParam Date data_nascimento,
            @RequestParam String cpf,
            @RequestParam String tipoUsuario,
            HttpSession session, Model model) {
        // Acessa o CPF do usuário logado na sessão, se necessário
        @SuppressWarnings("unused")
        String cpfLogado = (String) session.getAttribute("cpf");

        // Verifica se todos os parâmetros necessários estão presentes
        if (nome != null && data_nascimento != null && cpf != null && tipoUsuario != null) {
            // Verifica se o CPF já existe no banco de dados
            if (!pessoaRepository.verificarCpf(cpf)) {
                // O CPF não existe, então podemos inserir o novo usuário na base de dados
                // Crie uma instância de Pessoa com os dados do formulário
                Pessoa pessoa = new Pessoa(0, nome, data_nascimento, cpf, tipoUsuario, null);
                Admin admin = new Admin(0, 0, nome, data_nascimento, cpf, tipoUsuario, null);
                // Insere a nova pessoa na base de dados
                pessoaRepository.save(admin);
                pessoaRepository.save(pessoa);

                // Redireciona para a página de sucesso após o cadastro
                return "redirect:/homeAdmin";
            } else {
                // Se o CPF já existir, exibe uma mensagem de erro
                model.addAttribute("error", "CPF já cadastrado");
                return "login";
            }
        } else {
            // Se algum parâmetro estiver ausente, exibe uma mensagem de erro
            model.addAttribute("error", "Todos os campos são obrigatórios");
            return "login";
        }
    }

    // GETMAPPING DA TELA DE CADASTRAR UM ALUNO
    @GetMapping("/adminCadastrarAluno")
    public String adminCadastrarAluno(Model model) {
        return "adminCadastrarAluno";
    }

    @PostMapping("/validarCadastroAluno")
    public String validarCadastroAluno(@RequestParam String nome,
                                        @RequestParam Date data_nascimento,
                                        @RequestParam String cpf,
                                        @RequestParam String tipoUsuario,
                                        @RequestParam int idTurma,
                                        HttpSession session, Model model) {
        // Acessa o CPF do usuário logado na sessão, se necessário
        String cpfLogado = (String) session.getAttribute("cpf");
    
        // Verifica se todos os parâmetros necessários estão presentes
        if (nome != null && data_nascimento != null && cpf != null && tipoUsuario != null) {
            // Verifica se o CPF já existe no banco de dados
            if (!pessoaRepository.verificarCpf(cpf)) {
                // O CPF não existe, então podemos inserir o novo usuário na base de dados
                // Crie uma instância de Pessoa com os dados do formulário
                Pessoa pessoa = new Pessoa(0, nome, data_nascimento, cpf, tipoUsuario, null);
                Aluno aluno = new Aluno(0, 0, nome, data_nascimento, cpf, tipoUsuario, null, idTurma); // Atualizado para incluir o idTurma
                // Insere a nova pessoa na base de dados
                pessoaRepository.save(aluno);
                pessoaRepository.save(pessoa);
    
                // Redireciona para a página de sucesso após o cadastro
                return "redirect:/homeAdmin";
            } else {
                // Se o CPF já existir, exibe uma mensagem de erro
                model.addAttribute("error", "CPF já cadastrado");
                return "login";
            }
        } else {
            // Se algum parâmetro estiver ausente, exibe uma mensagem de erro
            model.addAttribute("error", "Todos os campos são obrigatórios");
            return "login";
        }
    }
    
    @ResponseBody
    @GetMapping("/buscarIdTurma")
    public Map<String, Integer> buscarIdTurma(@RequestParam String serie) {
        int idTurma = pessoaRepository.findIdTurmaBySerie(serie);
        Map<String, Integer> response = new HashMap<>();
        response.put("idTurma", idTurma);
        return response;
    }
    

    // GETMAPPING DA TELA DE CADASTRAR UM PROFESSOR
    @GetMapping("/adminCadastrarProfessor")
    public String adminCadastrarProfessor(Model model) {
        return "adminCadastrarProfessor";
    }

    // CODIGO QUE PUXA O METODO DO FORM DE CADASTRO DE ALUNO
    @PostMapping("/validarCadastroProfessor")
    public String validarCadastroProfessor(@RequestParam String nome,
            @RequestParam Date data_nascimento,
            @RequestParam String cpf,
            @RequestParam String tipoUsuario,
            @RequestParam String disciplina,
            HttpSession session, Model model) {
        // Acessa o CPF do usuário logado na sessão, se necessário
        @SuppressWarnings("unused")
        String cpfLogado = (String) session.getAttribute("cpf");

        // Verifica se todos os parâmetros necessários estão presentes
        if (nome != null && data_nascimento != null && cpf != null && tipoUsuario != null) {
            // Verifica se o CPF já existe no banco de dados
            if (!pessoaRepository.verificarCpf(cpf)) {
                // O CPF não existe, então podemos inserir o novo usuário na base de dados
                // Crie uma instância de Pessoa com os dados do formulário
                Pessoa pessoa = new Pessoa(0, nome, data_nascimento, cpf, tipoUsuario, null);
                Professor professor = new Professor(0, 0, nome, data_nascimento, cpf, tipoUsuario, null, disciplina);
                // Insere a nova pessoa na base de dados
                pessoaRepository.save(professor);
                pessoaRepository.save(pessoa);

                // Redireciona para a página de sucesso após o cadastro
                return "redirect:/homeAdmin";
            } else {
                // Se o CPF já existir, exibe uma mensagem de erro
                model.addAttribute("error", "CPF já cadastrado");
                return "login";
            }
        } else {
            // Se algum parâmetro estiver ausente, exibe uma mensagem de erro
            model.addAttribute("error", "Todos os campos são obrigatórios");
            return "login";
        }
    }

    @GetMapping("/adminCadastrarTurma")
    public String adminCadastrarTurma(Model model) {
        return "adminCadastrarTurma";
    }

    @PostMapping("/validarCadastroTurma")
    public String validarCadastroTurma(@RequestParam String serie,
            @RequestParam String cpf,
            HttpSession session, Model model) {
        // Verifica se o CPF do professor existe no banco de dados
        if (pessoaRepository.verificarCpfProfessor(cpf)) {
            // Obtém o ID do professor com base no CPF
            int idProfessor = pessoaRepository.obterIdProfessorPorCpf(cpf);

            // Obtém a disciplina do professor com base no CPF
            String disciplina = pessoaRepository.obterDisciplinaPorCpf(cpf);

            // Define o CPF, o ID do professor e a disciplina na sessão
            session.setAttribute("cpf", cpf);
            session.setAttribute("idProfessor", idProfessor);
            session.setAttribute("disciplina", disciplina);

            // Cria uma nova turma associada ao professor
            Turma turma = new Turma(0, serie, idProfessor, disciplina);

            // Salva a turma no banco de dados
            pessoaRepository.save(turma);

            // Redireciona para a página de administrador
            return "redirect:/homeAdmin";
        } else {
            // Se o CPF do professor não for encontrado, exibe uma mensagem de erro
            model.addAttribute("error", "CPF do professor não encontrado");
            return "login";
        }
    }

    @GetMapping("/exibirTurmasProfessor")
    public String mostrarTurmasDoProfessor(Model model, HttpSession session) {
        String cpfLogado = (String) session.getAttribute("cpf");

        // Verifica se o CPF recuperado da sessão é nulo
        if (cpfLogado == null) {
            // Adiciona uma mensagem de erro ao modelo
            model.addAttribute("erro", "CPF do professor não encontrado na sessão.");
            return "error"; // Por exemplo, redireciona para uma página de erro
        }

        // Adiciona o CPF do professor ao modelo para alerta
        model.addAttribute("cpfLogado", cpfLogado);

        // Imprime o CPF para verificar se está sendo recuperado corretamente
        System.out.println("CPF do professor logado: " + cpfLogado);

        // Recupera as turmas do professor
        List<Turma> turmas = pessoaRepository.findTurmasByProfessorCpf(cpfLogado);

        // Adiciona as turmas ao modelo
        model.addAttribute("turmas", turmas);

        return "exibirTurmasProfessor";
    }

    @GetMapping("/associarAlunoTurma")
    public String associarAlunoTurma(@RequestParam int idTurma, @RequestParam int idAluno, Model model) {
        // Verifica se a turma e o aluno existem no banco de dados
        if (pessoaRepository.verificarExistenciaTurma(idTurma) && pessoaRepository.verificarExistenciaAluno(idAluno)) {
            // Associar o aluno à turma
            pessoaRepository.associarAlunoTurma(idTurma, idAluno);
            // Redirecionar para alguma página de sucesso
            return "redirect:/exibirAlunoTurma";
        } else {
            // Caso a turma ou o aluno não existam, exibir uma mensagem de erro
            model.addAttribute("error", "Turma ou aluno não encontrados");
            return "home";
        }
    }

    @GetMapping("/buscarTurmas")
    public ResponseEntity<List<Turma>> buscarTurmas() {
        try {
            List<Turma> turmas = pessoaRepository.findAllTurmas(); // Supondo que você tenha um método no seu repositório para buscar todas as turmas
            return ResponseEntity.ok().body(turmas);
        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
