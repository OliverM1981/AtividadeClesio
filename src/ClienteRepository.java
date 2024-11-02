import java.util.ArrayList;
import java.util.Optional;

/**
 * Classe de repositório que gerencia o armazenamento e manipulação de clientes.
 */
public class ClienteRepository {
    private ArrayList<Cliente> clientes;  // Armazena os clientes cadastrados
    private int idCounter = 1;  // Contador para geração automática de IDs

    /**
     * Construtor da classe ClienteRepository.
     */
    public ClienteRepository() {
        this.clientes = new ArrayList<>();
    }

    /**
     * Adiciona um novo cliente ao repositório com ID gerado automaticamente.
     *
     * @param cliente Cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente) {
        cliente.setId(idCounter++); // Atribui um novo ID e incrementa o contador
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso! ID: " + cliente.getId());
    }

    /**
     * Lista todos os clientes cadastrados no repositório.
     */
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    /**
     * Atualiza as informações de um cliente com base no ID.
     *
     * @param id ID do cliente a ser atualizado.
     * @param novoCliente Cliente com os dados atualizados.
     * @return true se o cliente foi atualizado, false caso contrário.
     */
    public boolean atualizarCliente(int id, Cliente novoCliente) {
        Optional<Cliente> clienteOptional = buscarCliente(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNome(novoCliente.getNome());
            cliente.setEmail(novoCliente.getEmail());
            cliente.setTelefone(novoCliente.getTelefone());
            System.out.println("Cliente com ID " + id + " atualizado com sucesso.");
            return true;
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return false;
        }
    }

    /**
     * Remove um cliente do repositório com base no ID.
     *
     * @param id ID do cliente a ser removido.
     * @return true se o cliente foi removido, false caso contrário.
     */
    public boolean removerCliente(int id) {
        Optional<Cliente> clienteOptional = buscarCliente(id);
        if (clienteOptional.isPresent()) {
            clientes.remove(clienteOptional.get());
            System.out.println("Cliente com ID " + id + " removido com sucesso.");
            return true;
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return false;
        }
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente a ser buscado.
     * @return Optional contendo o cliente, se encontrado.
     */
    public Optional<Cliente> buscarCliente(int id) {
        return clientes.stream().filter(cliente -> cliente.getId() == id).findFirst();
    }
}
