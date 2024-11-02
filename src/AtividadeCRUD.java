/**
 * Classe principal que inicia a aplicação de CRUD de clientes.
 */
public class AtividadeCRUD {
    /**
     * Método principal que inicializa o repositório de clientes e exibe o menu.
    
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        ClienteRepository clienteRepository = new ClienteRepository();
        Menu menu = new Menu(clienteRepository);
        menu.exibirMenu();
    }
}
