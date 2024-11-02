import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciamento de operações CRUD de clientes.
 */
public class ClienteRepository {
    private List<Cliente> clientes = new ArrayList<>();

    /**
     * Adiciona um novo cliente ao repositório.
     *
     * @param cliente Cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return Lista de clientes.
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente a ser buscado.
     * @return Cliente encontrado, ou Optional vazio se não encontrado.
     */
    public Optional<Cliente> buscarClientePorId(int id) {
        return clientes.stream().filter(cliente -> cliente.getId() == id).findFirst();
    }

    /**
     * Atualiza as informações de um cliente.
     *
     * @param id       ID do cliente a ser atualizado.
     * @param nome     Novo nome do cliente.
     * @param email    Novo e-mail do cliente.
     * @param telefone Novo telefone do cliente.
     * @return true se o cliente foi atualizado, false se o cliente não foi encontrado.
     */
    public boolean atualizarCliente(int id, String nome, String email, String telefone) {
        Optional<Cliente> clienteOpt = buscarClientePorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            return true;
        }
        return false;
    }

    /**
     * Remove um cliente do repositório.
     *
     * @param id ID do cliente a ser removido.
     * @return true se o cliente foi removido, false se o cliente não foi encontrado.
     */
    public boolean removerCliente(int id) {
        return clientes.removeIf(cliente -> cliente.getId() == id);
    }
}
