package ru.yandex.practicum.catsgram.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.catsgram.dal.mappers.PostRowMapper;
import ru.yandex.practicum.catsgram.model.Post;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository extends BaseRepository<Post> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM posts";
    private static final String FIND_BY_POST_ID_QUERY = "SELECT * FROM posts WHERE id = ?";
    private static final String FIND_BY_AUTHOR_ID_QUERY = "SELECT * FROM posts WHERE author_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO posts(author_id, description, post_date)" +
            "VALUES ( ?, ?, ?) returning  id";
    private static final String UPDATE_QUERY = "UPDATE posts SET  description = ?, post_date = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM posts WHERE id = ?";


    public PostRepository(JdbcTemplate jdbc, PostRowMapper mapper) {
        super(jdbc, mapper);
    }

    public List<Post> findAll() {
        return findMany(FIND_ALL_QUERY);
    }


    public Optional<Post> findById(long postId) {
        return findOne(FIND_BY_POST_ID_QUERY, postId);
    }

    public List<Post> findByAuthorId(long authorId) {
        return findMany(FIND_BY_AUTHOR_ID_QUERY, authorId);
    }

    public Post save(Post post) {
        long postId = insert(
                INSERT_QUERY,
                post.getAuthorId(),
                post.getDescription(),
                Timestamp.from(post.getPostDate())
        );
        post.setId(postId);
        return post;
    }

    public Post update(Post post) {
        update(
                UPDATE_QUERY,
                post.getDescription(),
                Timestamp.from(post.getPostDate()),
                post.getId()
        );
        return post;
    }

    public void delete(long postId) {
        update(DELETE_QUERY, postId);
    }


}
