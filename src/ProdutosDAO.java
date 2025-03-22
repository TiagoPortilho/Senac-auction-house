/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProdutosDAO {
    
    conectaDAO connect;
    Connection conn;
    Statement stm;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void connect (){
        try {
            connect = new conectaDAO();
            conn = connect.getConnection();
            stm = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public void desconnect() {
        try {
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void cadastrarProduto (ProdutosDTO p){
        connect();
    
        try {
            stm.execute("INSERT INTO produtos(nome, valor, status) VALUES('" 
                + p.getNome() + "', " 
                + p.getValor() + ", '" 
                + p.getStatus() + "')");
            JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível inserir os dados! Por favor, verifique os valores digitados! " 
                + ex.getMessage());
        } finally {
            desconnect();
        }
        
    }
    
   public ArrayList<ProdutosDTO> listarProdutos(){
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    try {
        connect();
        
        
        ResultSet rs = stm.executeQuery("SELECT * FROM produtos");
        
        
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("ID"));
            produto.setNome(rs.getString("NOME"));
            produto.setValor(rs.getInt("VALOR"));
            produto.setStatus(rs.getString("STATUS"));
            listagem.add(produto);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex.getMessage());
    } finally {
        
        desconnect();
    }
    
    return listagem;
}
    
    public void venderProduto(int id) {
        conectaDAO dbConnection = new conectaDAO();
        Connection conexao = dbConnection.getConnection();

        try {
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    
    public ArrayList<ProdutosDTO> listarVendidos(){
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    try {
        connect();
        
        
        ResultSet rs = stm.executeQuery("SELECT * FROM produtos WHERE status = 'Vendido'");
        
        
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("ID"));
            produto.setNome(rs.getString("NOME"));
            produto.setValor(rs.getInt("VALOR"));
            produto.setStatus(rs.getString("STATUS"));
            listagem.add(produto);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex.getMessage());
    } finally {
        
        desconnect();
    }
    
    return listagem;
}
    
        
}

