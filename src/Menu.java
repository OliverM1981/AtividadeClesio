import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Classe de Menu que gerencia a interação do usuário com o sistema CRUD de clientes.
 */
public class Menu {
    private ClienteRepository clienteRepository;
    private Scanner scanner;

    /**
     * Construtor da classe Menu.
     *
     * @param clienteRepository Repositório de clientes.
     */
    public Menu(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Exibe o menu principal e processa as opções selecionadas pelo usuário.
     */
    public void exibirMenu() {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Remover Cliente");
            System.out.println("5. Buscar Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> listarClientes();
                    case 3 -> atualizarCliente();
                    case 4 -> removerCliente();
                    case 5 -> buscarCliente();
                    case 0 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
                scanner.nextLine(); // Limpar o buffer
            }
        }
    }

    /**
     * Cadastra um novo cliente com ID gerado automaticamente.
     */
    private void cadastrarCliente() {
        System.out.println("ID gerado automaticamente para o novo cliente.");

        System.out.print("Nome (deve incluir sobrenome): ");
        String nome = scanner.nextLine();
        while (!isNomeValido(nome)) {
            System.out.println("Erro: Nome inválido. Certifique-se de incluir o sobrenome.");
            System.out.print("Nome: ");
            nome = scanner.nextLine();
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        while (!isEmailValido(email)) {
            System.out.println("Erro: E-mail inválido. Exemplo de formato válido: exemplo@dominio.com");
            System.out.print("Email: ");
            email = scanner.nextLine();
        }

        System.out.print("Telefone (somente números com DDD): ");
        String telefone = scanner.nextLine();
        while (!isTelefoneValido(telefone)) {
            System.out.println("Erro: Telefone inválido. O formato deve incluir DDD e ser composto apenas por números.");
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
        }

        Cliente cliente = new Cliente(0, nome, email, telefone); // ID será atribuído no repositório
        clienteRepository.adicionarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso. ID: " + cliente.getId());
    }

    /**
     * Atualiza os dados de um cliente existente, permitindo a escolha dos campos.
     */
    private void atualizarCliente() {
        System.out.print("Informe o ID do cliente a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Cliente> clienteOptional = clienteRepository.buscarCliente(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            boolean atualizar = true;
            while (atualizar) {
                System.out.println("Selecione o campo para atualizar:");
                System.out.println("1. Nome");
                System.out.println("2. Email");
                System.out.println("3. Telefone");
                System.out.println("0. Concluir atualizações");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine();
                        cliente.setNome(novoNome);
                    }
                    case 2 -> {
                        System.out.print("Novo email: ");
                        String novoEmail = scanner.nextLine();
                        cliente.setEmail(novoEmail);
                    }
                    case 3 -> {
                        System.out.print("Novo telefone: ");
                        String novoTelefone = scanner.nextLine();
                        cliente.setTelefone(novoTelefone);
                    }
                    case 0 -> atualizar = false;
                    default -> System.out.println("Opção inválida.");
                }
            }
            clienteRepository.atualizarCliente(cliente.getId(), cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    /**
     * Lista todos os clientes cadastrados.
     */
    private void listarClientes() {
        clienteRepository.listarClientes();
    }

    /**
     * Remove um cliente cadastrado com base no ID.
     */
    private void removerCliente() {
        System.out.print("Informe o ID do cliente a ser removido: ");
        int id = scanner.nextInt();
        if (!clienteRepository.removerCliente(id)) {
            System.out.println("Cliente não encontrado.");
        }
    }

    /**
     * Busca um cliente pelo ID e exibe suas informações.
     */
    private void buscarCliente() {
        System.out.print("Informe o ID do cliente a ser buscado: ");
        int id = scanner.nextInt();
        Optional<Cliente> clienteOptional = clienteRepository.buscarCliente(id);
        if (clienteOptional.isPresent()) {
            System.out.println("Cliente encontrado: " + clienteOptional.get());
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    /**
     * Valida se o nome possui pelo menos duas palavras.
     *
     * @param nome Nome a ser validado.
     * @return true se o nome é válido, false caso contrário.
     */
    private boolean isNomeValido(String nome) {
        String[] partes = nome.trim().split(" ");
        return partes.length >= 2;
    }

    /**
     * Valida se o e-mail está em um formato aceitável.
     *
     * @param email E-mail a ser validado.
     * @return true se o e-mail é válido, false caso contrário.
     */
    private boolean isEmailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    /**
     * Valida se o telefone contém somente números e possui formato válido.
     *
     * @param telefone Telefone a ser validado.
     * @return true se o telefone é válido, false caso contrário.
     */
    private boolean isTelefoneValido(String telefone) {
        return telefone.matches("\\d{2}\\d{9}"); // Exemplo: DDD + 9 dígitos
    }
}
