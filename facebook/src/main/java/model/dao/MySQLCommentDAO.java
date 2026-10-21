
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Comment;
import model.Post;

public class MySQLCommentDAO implements CommentDAO {

    @Override
    public boolean save(Comment comment) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO comments "
                + "(id, content, post_id) "
                + "VALUES (DEFAULT, ?, ?)";

        db.prepareStatement(sqlInsert);
        db.setString(1, comment.getContent());
        db.setInt(2, comment.getPost().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Comment comment) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE comments "
                + "SET content = ?, post_id = ? "
                + "WHERE id = ?";

        db.prepareStatement(sqlUpdate);
        db.setString(1, comment.getContent());
        db.setInt(2, comment.getPost().getId());
        db.setInt(3, comment.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Comment comment) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM comments WHERE id = ?";

        db.prepareStatement(sqlDelete);
        db.setInt(1, comment.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Comment> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<Comment> comments = new ArrayList<>();

        String sqlQuery =
                "SELECT c.id, c.content, c.post_id, "
                + "p.content AS post_content "
                + "FROM comments c "
                + "INNER JOIN posts p ON p.id = c.post_id "
                + "ORDER BY c.id DESC";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {

            Comment comment = new Comment(db.getInt("id"));
            comment.setContent(db.getString("content"));

            Post post = new Post(db.getInt("post_id"));
            post.setContent(db.getString("post_content"));

            comment.setPost(post);

            comments.add(comment);
        }

        return comments;
    }

    @Override
    public Comment findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql =
                "SELECT c.id, c.content, c.post_id, "
                + "p.content AS post_content "
                + "FROM comments c "
                + "INNER JOIN posts p ON p.id = c.post_id "
                + "WHERE c.id = ?";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Comment comment = null;

        if (db.next()) {

            comment = new Comment(db.getInt("id"));
            comment.setContent(db.getString("content"));

            Post post = new Post(db.getInt("post_id"));
            post.setContent(db.getString("post_content"));

            comment.setPost(post);
        }

        return comment;
    }
}
