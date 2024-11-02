import java.util.Scanner;

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
        }
    }

    /**
     * Cadastra um novo cliente solicitando as informações ao usuário.
     */
    private void cadastrarCliente() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Cliente cliente = new Cliente(id, nome, email, telefone);
        clienteRepository.adicionarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso.");
    }

    /**
     * Lista todos os clientes cadastrados.
     */
    private void listarClientes() {
        clienteRepository.listarClientes().forEach(System.out::println);
    }

    /**
     * Atualiza as informações de um cliente existente.
     */
    private void atualizarCliente() {
        System.out.print("ID do Cliente a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo Email: ");
        String email = scanner.nextLine();
        System.out.print("Novo Telefone: ");
        String telefone = scanner.nextLine();

        boolean atualizado = clienteRepository.atualizarCliente(id, nome, email, telefone);
        System.out.println(atualizado ? "Cliente atualizado." : "Cliente não encontrado.");
    }

    /**
     * Remove um cliente do repositório com base no ID.
     */
    private void removerCliente() {
        System.out.print("ID do Cliente a remover: ");
        int id = scanner.nextInt();
        boolean removido = clienteRepository.removerCliente(id);
        System.out.println(removido ? "Cliente removido." : "Cliente não encontrado.");
    }

    /**
     * Busca um cliente pelo ID e exibe suas informações.
     */
    private void buscarCliente() {
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        clienteRepository.buscarClientePorId(id)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Cliente não encontrado.")
                );
    }
}
