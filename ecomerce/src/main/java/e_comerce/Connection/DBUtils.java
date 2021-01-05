package e_comerce.Connection;


import e_comerce.beans.AdminAccount;
import e_comerce.beans.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


    public class DBUtils {
        public static AdminAccount findUser(Connection connection, String userName) throws SQLException {

            String sql = "Select a.User_name, a.Password from Admin_Account a "//
                    + " where a.User_name = ? ";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userName);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String password = rs.getString("Password");
                AdminAccount user = new AdminAccount();
                user.setUsername(userName);
                user.setPassword(password);
                return user;
            }
            return null;
        }
        public static AdminAccount findUser(Connection connection, String username, String password) throws SQLException {

            String sql = "Select a.User_name, a.Password from Admin_Account a "//
                    + " where a.User_name = ? and a.Password = ? ";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                AdminAccount user = new AdminAccount();
                user.setUsername(username);
                user.setPassword(password);
                return user;
            }
            return null;
        }

        public static List<Product> queryProduct(Connection conn) throws SQLException {
            String sql = "Select id, name, productunit, quantity, price  from Product  ";

            PreparedStatement pstm = conn.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();
            List<Product> list = new ArrayList<Product>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("Name");
                float price = rs.getFloat("Price");
                String productUnit = rs.getString("productunit");
                int quantity = rs.getInt("quantity");
                Product product = new Product();
                product.setProductId(id);
                product.setProductName(name);
                product.setPrice(price);
                product.setProductUnit(productUnit);
                product.setQuantity(quantity);
                list.add(product);
            }
            return list;
        }
        public static Product findProduct(Connection conn, String productId) throws SQLException {
            String sql = "Select a.id, a.name, a.productunit, a.quantity, a.price from Product a where a.id=?";

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, productId);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String productUnit = rs.getString("productunit");
                int quanity = rs.getInt("quanity");
                float price = rs.getFloat("price");
                Product product = new Product(productId,name,productUnit, quanity,price);
                return product;
            }
            return null;
        }



        public static void updateProduct(Connection conn, Product product) throws SQLException {
            String sql = "Update Product set Name =?, Price=?, Productunit=?, Quantity=?  where Id=? ";

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setFloat(2, product.getPrice());
            pstm.setString(1, product.getProductName());
            pstm.setString(3, product.getProductUnit());
            pstm.setInt(4, product.getQuantity());
            pstm.setString(5, product.getProductId());
            pstm.executeUpdate();
        }

        public static void insertProduct(Connection conn, Product product) throws SQLException {
            String sql = "Insert into Product(id, Name,Productunit,Price,Quantity) values (?,?,?,?,?)";

            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, product.getProductId());
            pstm.setFloat(4, product.getPrice());
            pstm.setString(2, product.getProductName());
            pstm.setString(3, product.getProductUnit());
            pstm.setInt(5, product.getQuantity());
            pstm.executeUpdate();
        }

        public static void deleteProduct(Connection conn, String code) throws SQLException {
            String sql = "Delete From Product where productid= ?";

            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, code);

            pstm.executeUpdate();
        }

    }

