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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.projeto.model.Admin;
import com.projeto.model.Aluno;
import com.projeto.model.Pessoa;
import com.projeto.model.Presenca;
import com.projeto.model.Professor;
import com.projeto.model.Turma;
import com.projeto.model.TurmaAlunos;
import com.projeto.repository.JDBC.AdminRepositoryJDBC;
import com.projeto.repository.JDBC.AlunoRepositoryJDBC;
import com.projeto.repository.JDBC.PessoaRepositoryJDBC;
import com.projeto.repository.JDBC.PresencaRepositoryJDBC;
import com.projeto.repository.JDBC.ProfessorRepositoryJDBC;
import com.projeto.repository.JDBC.TurmaAlunosRepositoryJDBC;
import com.projeto.repository.JDBC.TurmaRepositoryJDBC;
import com.projeto.Util.ValidaSenha;
import jakarta.servlet.http.HttpSession;

@Controller
public class PessoaController {
    private final PessoaRepositoryJDBC pessoaRepository;
    private final AdminRepositoryJDBC adminRepository;
    private final AlunoRepositoryJDBC alunoRepository;
    private final ProfessorRepositoryJDBC professorRepository;
    private final PresencaRepositoryJDBC presencaRepository;
    private final TurmaRepositoryJDBC turmaRepository;
    private final TurmaAlunosRepositoryJDBC turmaAlunosRepository;

    public PessoaController(PessoaRepositoryJDBC pessoaRepository,
            AdminRepositoryJDBC adminRepository,
            AlunoRepositoryJDBC alunoRepository,
            PresencaRepositoryJDBC presencaRepository,
            ProfessorRepositoryJDBC professorRepository,
            TurmaRepositoryJDBC turmaRepository,
            TurmaAlunosRepositoryJDBC turmaAlunosRepository) {
        this.pessoaRepository = pessoaRepository;
        this.adminRepository = adminRepository;
        this.alunoRepository = alunoRepository;
        this.presencaRepository = presencaRepository;
        this.professorRepository = professorRepository;
        this.turmaRepository = turmaRepository;
        this.turmaAlunosRepository = turmaAlunosRepository;

    }

    @GetMapping("/")
    public String primeiraPagina(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String cpf, HttpSession session, Model model) {
        if (pessoaRepository.verificarCpf(cpf)) {
            session.setAttribute("cpf", cpf);
            String senha = pessoaRepository.buscarSenhaPorCpf(cpf);
            if (senha != null) {
                model.addAttribute("cpf", cpf);
                return "loginComSenha.html";
            } else {
                model.addAttribute("cpf", cpf);
                return "criarSenha.html";
            }
        } else {
            model.addAttribute("erro", "CPF não cadastrado");
            return "login";
        }
    }

    @GetMapping("/criarSenha")
    public String showCreatePasswordPage(@RequestParam String cpf, Model model) {
        model.addAttribute("cpf", cpf);
        return "criarSenha";
    }

    @PostMapping("/atualizarSenha")
    public String atualizarSenha(@RequestParam("novaSenha") String novaSenha, HttpSession session, Model model) {
        String cpf = (String) session.getAttribute("cpf");
        if (cpf != null) {
            if (ValidaSenha.validaSenha(novaSenha)) {
                pessoaRepository.atualizarSenhaPorCpf(cpf, novaSenha);
                String tipoUsuario = pessoaRepository.buscarTipoUsuarioPorCpf(cpf);
                switch (tipoUsuario) {
                    case "Aluno":
                        return "redirect:/homeAluno";
                    case "Professor":
                        return "redirect:/homeProfessor";
                    case "Admin":
                        return "redirect:/homeAdmin";
                    default:
                        return "redirect:/pagina_de_erro";
                }
            } else {
                model.addAttribute("erro",
                        "A senha deve conter pelo menos 8 caracteres, incluindo números, letras maiúsculas, letras minúsculas e caracteres especiais.");
                return "login";
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
        String cpf = (String) session.getAttribute("cpf");
        if (cpf == null) {
            return "login";
        }
        if (pessoaRepository.validarSenha(cpf, senha)) {
            String tipoUsuario = pessoaRepository.buscarTipoUsuarioPorCpf(cpf);
            switch (tipoUsuario) {
                case "Aluno":
                    return "homeAluno";
                case "Professor":
                    return "homeProfessor";
                case "Admin":
                    return "homeAdmin";
                default:
                    return "pagina_de_erro";
            }
        } else {
            model.addAttribute("erro", "Senha incorreta");
            model.addAttribute("cpf", cpf);
            return "login";
        }
    }

    @GetMapping("/adminCadastrarAdmin")
    public String adminCadastrarAdmin(Model model) {
        return "adminCadastrarAdmin";
    }

    @PostMapping("/validarCadastroAdmin")
    public String validarCadastroAdmin(@RequestParam String nome,
            @RequestParam Date data_nascimento,
            @RequestParam String cpf,
            @RequestParam String tipoUsuario,
            HttpSession session, Model model) {
        if (nome != null && data_nascimento != null && cpf != null && tipoUsuario != null) {
            if (!pessoaRepository.verificarCpf(cpf)) {
                Pessoa pessoa = new Pessoa(0, nome, data_nascimento, cpf, tipoUsuario, null);
                Admin admin = new Admin(0, 0, nome, data_nascimento, cpf, tipoUsuario, null);
                adminRepository.save(admin);
                pessoaRepository.save(pessoa);
                return "redirect:/homeAdmin";
            } else {
                model.addAttribute("erro", "CPF já cadastrado");
                return "paginaDeErro";
            }
        } else {
            model.addAttribute("erro", "Todos os campos são obrigatórios");
            return "paginaDeErro";
        }
    }

    @GetMapping("/adminCadastrarAluno")
    public String adminCadastrarAluno(Model model) {
        return "adminCadastrarAluno";
    }

    @PostMapping("/validarCadastroAluno")
    public String validarCadastroAluno(@RequestParam String nome,
            @RequestParam Date data_nascimento,
            @RequestParam String cpf,
            @RequestParam String tipoUsuario,
            @RequestParam List<Integer> idTurmas,
            HttpSession session, Model model) {
        if (nome != null && data_nascimento != null && cpf != null && tipoUsuario != null) {
            if (!pessoaRepository.verificarCpf(cpf)) {
                Pessoa pessoa = new Pessoa(0, nome, data_nascimento, cpf, tipoUsuario, null);
                Aluno aluno = new Aluno(0, 0, nome, data_nascimento, cpf, tipoUsuario, null);
                alunoRepository.save(aluno);
                pessoaRepository.save(pessoa);
                Integer idAluno = alunoRepository.obterIdAlunoPorCpf(cpf);
                for (Integer idTurma : idTurmas) {
                    TurmaAlunos turmaAlunos = new TurmaAlunos(idTurma, idAluno);
                    turmaAlunosRepository.save(turmaAlunos);
                }
                return "redirect:/homeAdmin";
            } else {
                model.addAttribute("erro", "CPF já cadastrado");
                return "paginaDeErro";
            }
        } else {
            model.addAttribute("erro", "Todos os campos são obrigatórios");
            return "paginaDeErro";
        }
    }

    @ResponseBody
    @GetMapping("/buscarIdTurma")
    public Map<String, Integer> buscarIdTurma(@RequestParam String serie) {
        int idTurma = turmaRepository.findIdTurmaBySerie(serie);
        Map<String, Integer> response = new HashMap<>();
        response.put("idTurma", idTurma);
        return response;
    }

    @GetMapping("/adminCadastrarProfessor")
    public String adminCadastrarProfessor(Model model) {
        return "adminCadastrarProfessor";
    }

    @PostMapping("/validarCadastroProfessor")
    public String validarCadastroProfessor(@RequestParam String nome,
            @RequestParam Date data_nascimento,
            @RequestParam String cpf,
            @RequestParam String tipoUsuario,
            HttpSession session, Model model) {
        if (nome != null && data_nascimento != null && cpf != null && tipoUsuario != null) {
            if (!pessoaRepository.verificarCpf(cpf)) {
                Pessoa pessoa = new Pessoa(0, nome, data_nascimento, cpf, tipoUsuario, null);
                Professor professor = new Professor(0, 0, nome, data_nascimento, cpf, tipoUsuario, null);
                professorRepository.save(professor);
                pessoaRepository.save(pessoa);
                return "redirect:/homeAdmin";
            } else {
                model.addAttribute("erro", "CPF já cadastrado");
                return "paginaDeErro";
            }
        } else {
            model.addAttribute("erro", "Todos os campos são obrigatórios");
            return "paginaDeErro";
        }
    }

    @GetMapping("/adminCadastrarTurma")
    public String adminCadastrarTurma(Model model) {
        return "adminCadastrarTurma";
    }

    @PostMapping("/validarCadastroTurma")
    public String validarCadastroTurma(@RequestParam String serie,
            @RequestParam String cpf,
            @RequestParam String disciplina,
            HttpSession session, Model model) {
        if (professorRepository.verificarCpfProfessor(cpf)) {
            int idProfessor = professorRepository.obterIdProfessorPorCpf(cpf);
            session.setAttribute("cpf", cpf);
            session.setAttribute("idProfessor", idProfessor);
            Turma turma = new Turma(0, serie, idProfessor, disciplina);
            turmaRepository.save(turma);
            return "redirect:/homeAdmin";
        } else {
            model.addAttribute("erro", "CPF do professor não encontrado");
            return "paginaDeErro";
        }
    }

    @GetMapping("/exibirTurmasProfessor")
    public String mostrarTurmasDoProfessor(Model model, HttpSession session) {
        String cpfLogado = (String) session.getAttribute("cpf");
        if (cpfLogado == null) {
            model.addAttribute("erro", "CPF logado não foi encontrado");
            return "paginaDeErro";
        }
        model.addAttribute("cpfLogado", cpfLogado);
        List<Turma> turmas = turmaRepository.findTurmasByProfessorCpf(cpfLogado);
        model.addAttribute("turmas", turmas);
        return "exibirTurmasProfessor";
    }

    @GetMapping("/buscarTurmas")
    public ResponseEntity<List<Turma>> buscarTurmas() {
        try {
            List<Turma> turmas = turmaRepository.findAllTurmas();
            return ResponseEntity.ok().body(turmas);
        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/exibirTurmasAluno")
    public String mostrarTurmasDoAluno(Model model, HttpSession session) {
        String cpfLogado = (String) session.getAttribute("cpf");
        List<Turma> turmas = turmaRepository.findTurmasByAlunoCpf(cpfLogado);
        model.addAttribute("turmas", turmas);
        return "exibirTurmasAluno";
    }

    @GetMapping("/inserirIdTurma")
    public String inserirIdTurma(Model model) {
        return "inserirIdTurma";
    }

    @PostMapping("/inserirIdTurma")
    public String inserirIdTurma(@RequestParam int idTurma, Model model) {
        if (turmaRepository.existeTurma(idTurma)) {
            List<Aluno> alunos = alunoRepository.procurarAlunosPorIdTurma(idTurma);
            model.addAttribute("idTurma", idTurma);
            model.addAttribute("alunos", alunos);
            return "confirmarPresenca";
        } else {
            model.addAttribute("erro", "A turma com o ID especificado não foi encontrada.");
            return "paginaDeErro";
        }
    }

    @PostMapping("/confirmarPresenca")
    public String confirmarPresenca(@RequestParam int idTurma,
            @RequestParam Date dataPresenca,
            @RequestParam List<String> situacao,
            Model model) {
        List<Aluno> alunos = alunoRepository.procurarAlunosPorIdTurma(idTurma);
        if (!alunos.isEmpty() && idTurma > 0 && dataPresenca != null && situacao != null && !situacao.isEmpty()) {
            if (presencaRepository.existeChamadaParaData(idTurma, dataPresenca)) {
                model.addAttribute("erro", "Já existe uma chamada para essa turma na mesma data.");
                return "paginaDeErro";
            } else {
                for (Aluno aluno : alunos) {
                    Presenca presenca = new Presenca(0, aluno.getIdAluno(), dataPresenca,
                            situacao.get(alunos.indexOf(aluno)), idTurma);
                    presencaRepository.save(presenca);
                }
                return "redirect:/homeProfessor";
            }
        } else {
            model.addAttribute("erro", "Todos os campos são obrigatórios");
            return "paginaDeErro";
        }
    }

    @GetMapping("/exibirPresencaAluno")
    public String exibirPresencasDoAluno(Model model, HttpSession session) {
        String cpfLogado = (String) session.getAttribute("cpf");
        Integer idAluno = alunoRepository.obterIdAlunoPorCpf(cpfLogado);
        model.addAttribute("idAluno", idAluno);
        List<Presenca> presencas = presencaRepository.buscarPresencasDoAlunoAtual(idAluno);
        model.addAttribute("presencas", presencas);
        return "exibirPresencaAluno";
    }
}
