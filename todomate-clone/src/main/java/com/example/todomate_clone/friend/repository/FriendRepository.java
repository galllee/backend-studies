package com.example.todomate_clone.friend.repository;

import com.example.todomate_clone.friend.domain.Friend;
import com.example.todomate_clone.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    public Optional<Friend> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    @Query("select f.fromUser from Friend f where f.toUser.id = :toUserId")
    public List<User> findAllByToUserId(@Param("toUserId") Long toUserId);
}
