
package model;

public class Comment {

    private int id;
    private String content;

    // Atributo que representa o post relacionado
    private Post post;


    public Comment() {
        this(0);
    }

    public Comment(int id) {
        this.id = id;
        setContent("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    public Post getPost() {
        return post;
    }

    
    public void setPost(Post post) {
        this.post = post;
    }
}
