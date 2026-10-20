
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Post;
import model.User;

public class MySQLPostDAO implements PostDAO {

    @Override
    public boolean save(Post post) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO posts "
                + "(id, content, post_date, user_id) "
                + "VALUES (DEFAULT, ?, CURDATE(), ?)";

        db.prepareStatement(sqlInsert);
        db.setString(1, post.getContent());
        db.setInt(2, post.getUser().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Post post) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE posts "
                + "SET content = ?, user_id = ? "
                + "WHERE id = ?";

        db.prepareStatement(sqlUpdate);
        db.setString(1, post.getContent());
        db.setInt(2, post.getUser().getId());
        db.setInt(3, post.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Post post) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM posts WHERE id = ?";

        db.prepareStatement(sqlDelete);
        db.setInt(1, post.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Post> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<Post> posts = new ArrayList<>();

        String sqlQuery =
                "SELECT p.id, p.content, p.post_date, p.user_id, "
                + "u.nome AS user_name, "
                + "u.sexo AS user_gender, "
                + "u.email AS user_email "
                + "FROM posts p "
                + "INNER JOIN users u ON u.id = p.user_id "
                + "ORDER BY p.id DESC";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {

            Post post = new Post(db.getInt("id"));

            post.setContent(db.getString("content"));
            post.setPostDate(db.getDate("post_date"));

            User user = new User(db.getInt("user_id"));
            user.setName(db.getString("user_name"));
            user.setGender(db.getString("user_gender"));
            user.setEmail(db.getString("user_email"));

            post.setUser(user);

            posts.add(post);
        }

        return posts;
    }

    @Override
    public Post findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT p.id, p.content, p.post_date, "
                + "p.user_id, "
                + "u.nome AS user_name, "
                + "u.sexo AS user_gender, "
                + "u.email AS user_email "
                + "FROM posts p "
                + "INNER JOIN users u ON u.id = p.user_id "
                + "WHERE p.id = ?";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Post post = null;

        if (db.next()) {

            post = new Post(db.getInt("id"));

            post.setContent(db.getString("content"));
            post.setPostDate(db.getDate("post_date"));

            User user = new User(db.getInt("user_id"));
            user.setName(db.getString("user_name"));
            user.setGender(db.getString("user_gender"));
            user.setEmail(db.getString("user_email"));

            post.setUser(user);
        }

        return post;
    }
}
