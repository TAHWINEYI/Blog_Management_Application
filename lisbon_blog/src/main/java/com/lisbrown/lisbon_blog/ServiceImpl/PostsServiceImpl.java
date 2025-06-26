package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.Exceptions.ResourcesNotFoundException;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import com.lisbrown.lisbon_blog.Repositories.PostsRepository;
import com.lisbrown.lisbon_blog.Services.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;

    public PostsServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public Page<PostsDTO> fetchPostByKeyword(Pageable pageable, String keyword) {
        List<PostsDTO> fetchedPost = postsRepository.findByKeywordIgnoreCase(pageable,keyword)
                .stream().map(postsDTO -> new PostsDTO(
                postsDTO.getTittle(),postsDTO.getContent(), postsDTO.getCategory(),
                postsDTO.getUser(),postsDTO.getCreatedOn(), postsDTO.getImage(),postsDTO.getComments()
        )).toList();
        return new PageImpl<>(fetchedPost,pageable, fetchedPost.size());
    }

    @Override
    public Page<PostsDTO> findAllPosts(Pageable pageable) {

        Page<Posts> posts = postsRepository.findAll(pageable);
        List<PostsDTO> fetchedPostsDTO = posts.stream()
                .map(post-> new PostsDTO(post.getPostId(),
                        post.getTittle(),
                        post.getContent(),
                        post.getCategory(),
                        post.getUser(),
                        post.getCreatedOn(),
                        post.getImage(),
                        post.getComments())).collect(Collectors.toList());
        return new PageImpl<>(fetchedPostsDTO,pageable,fetchedPostsDTO.size());
    }

    @Override
    public Posts addNewPosts(PostsDTO post) {
        Posts newPost = new Posts();
        newPost.setPostId(post.post_id());
        newPost.setUser(post.user_id());
        newPost.setCategory(post.category_id());
        newPost.setContent(post.content());
        newPost.setTittle(post.tittle());
        newPost.setComments(post.comments());
        newPost.setImage(post.image());
        newPost.setCreatedOn(post.created_date());
        postsRepository.save(newPost);
        return newPost;
    }

    @Override
    public String deletePosts(Long post_id) {
        Optional<Posts> deletePost = postsRepository.findById(post_id);
        if(deletePost.isPresent()){
            postsRepository.deleteById(post_id);
            return "post with post_id:" + post_id + "has been deleted successfully";
        }return "post with post id" + post_id + "was not found";
    }

    @Override
    public Optional<PostsDTO> findPostById(Long post_id) throws ResourcesNotFoundException {
        return Optional.ofNullable(postsRepository.findById(post_id).map(
                u -> new PostsDTO(
                        u.getPostId(), u.getTittle(), u.getContent(), u.getCategory(),
                        u.getUser(), u.getCreatedOn(), u.getImage(), u.getComments())
        ).orElseThrow(() -> new ResourcesNotFoundException("post with post id:" + post_id + "was not found")));
    }

}
