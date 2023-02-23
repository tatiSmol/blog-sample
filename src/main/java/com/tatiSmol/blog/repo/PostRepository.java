package com.tatiSmol.blog.repo;

import com.tatiSmol.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
