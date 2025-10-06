package com.fiap.stock.test;

import com.fiap.stock.controller.EstoqueController;
import com.fiap.stock.model.Material;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.EstoqueService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EstoqueServiceTest {

    private static Connection conn;
    private static EstoqueService service;
    private static EstoqueController controller;

    @BeforeAll
    public static void setup() throws Exception {
        // Banco em memória H2
        conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        Statement stmt = conn.createStatement();

        // Criar tabelas mínimas
        stmt.execute("CREATE TABLE MATERIAL (ID BIGINT AUTO_INCREMENT PRIMARY KEY, NOME VARCHAR(100), QUANTIDADE DOUBLE, UNIDADE VARCHAR(10), PONTO_REPOSICAO DOUBLE)");
        stmt.execute("CREATE TABLE USUARIO (ID INT AUTO_INCREMENT PRIMARY KEY, NOME VARCHAR(100), LOGIN VARCHAR(50), SENHA VARCHAR(50), PERFIL VARCHAR(20))");
        stmt.execute("CREATE TABLE MOVIMENTACAO (ID INT AUTO_INCREMENT PRIMARY KEY, MATERIAL_ID INT, USUARIO_ID INT, TIPO VARCHAR(10), QUANTIDADE INT, DATA_MOVIMENTACAO TIMESTAMP)");

        // Inserir usuário para teste
        stmt.execute("INSERT INTO USUARIO(NOME, LOGIN, SENHA, PERFIL) VALUES('Test User','user','123','ADMIN')");

        service = new EstoqueService(conn);
        controller = new EstoqueController(service);
    }

    @Test
    @Order(1)
    public void testCriarMaterial() throws Exception {
        Material m = controller.adicionarMaterial("Álcool 70%", 100.0, "ml", 10.0);
        List<Material> list = controller.listarMateriais();
        assertEquals(1, list.size());
        assertEquals("Álcool 70%", list.get(0).getNome());
    }

    @Test
    @Order(2)
    public void testRegistrarEntrada() throws Exception {
        Material m = controller.listarMateriais().get(0);
        Usuario u = service.autenticar("user", "123");
        controller.registrarEntrada(m, 50, u);
        Material updated = controller.listarMateriais().get(0);
        assertEquals(150.0, updated.getQuantidade());
    }

    @Test
    @Order(3)
    public void testRegistrarSaida() throws Exception {
        Material m = controller.listarMateriais().get(0);
        Usuario u = service.autenticar("user", "123");
        controller.registrarSaida(m, 30, u);
        Material updated = controller.listarMateriais().get(0);
        assertEquals(120.0, updated.getQuantidade());
    }

    @Test
    @Order(4)
    public void testAutenticarUsuario() {
        Usuario u = controller.autenticar("user", "123");
        assertNotNull(u);
        assertEquals("Test User", u.getNome());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        conn.close();
    }
}
