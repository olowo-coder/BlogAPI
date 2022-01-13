package com.example.blog.services.serviceImpl;

import com.example.blog.model.FavoritePost;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.repository.FavoritePostRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FavoritePostRepository favoritePostRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, FavoritePostRepository favoritePostRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.favoritePostRepository = favoritePostRepository;
    }

    public List<PostMessage> getAllPost(){
        return postRepository.findAll();
    }

    public Page<PostMessage> pageGetAllPosts(String byWhat, int next){
        Pageable firstPageElements = PageRequest.of(0, next, Sort.by(byWhat).ascending());
        return postRepository.findAll(firstPageElements);
    }



    public boolean addNewPost(PostMessage postMessage){
        Optional<Users> user = userRepository.findById(postMessage.getUsers().getUserId());
        if(user.isPresent()) {
            postRepository.save(postMessage);
            return true;
        }
        else {
            return false;
        }
    }

    public PostMessage getPostById(Long postId){
        return postRepository.findById(postId).get();
    }

    @Transactional
    public void updatePost(PostMessage postMessage){
        PostMessage postMessage1 = postRepository.findById(postMessage.getPostId()).orElseThrow(() -> new IllegalStateException("does not exist"));
        postMessage1.setPostBody(postMessage.getPostBody());
    }

    @Transactional
    public void deletePost(Long postId) {
        boolean exist = postRepository.existsById(postId);
        if(!exist){
            throw  new IllegalStateException("Post with id -> " + postId + " does not exist");
        }
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostMessage> getAllPostById(Long userId) {
        return postRepository.findAllByUsersUserId(userId);
    }

    @Override
    public PostMessage getPostByPostBody(String postBody) {
        return postRepository.getPostMessageByPostBody(postBody);
    }

    @Override
    public void favoritePost(Long postById) {
        FavoritePost favoritePost = new FavoritePost();
        favoritePost.setPostMessage(getPostById(postById));
        favoritePostRepository.save(favoritePost);
    }

    @Override
    public List<FavoritePost> getAllFavoritePost() {
        return favoritePostRepository.findAll();
    }

    @Override
    public List<FavoritePost> getFavoritePostByUser(Long userId) {
        return favoritePostRepository.getAllByPostMessageUsersUserId(userId);
    }

    @Override
    public List<PostMessage> allPostContains(String post) {
        return postRepository.getAllByPostBodyContains(post);
    }

}
