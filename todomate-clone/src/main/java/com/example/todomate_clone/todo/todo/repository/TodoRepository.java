package com.example.todomate_clone.todo.todo.repository;

import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.user.domain.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    public List<Todo> findAllByUserAndDateAndIsArchivedFalse(User user, LocalDate date);
    public List<Todo> findAllByReminderTimeLessThanEqualAndReminderSentFalse(LocalTime now);

    @Query("""
    select t from Todo t
    left join TodoVisibility tv on t.id = tv.todo.id
    left join Category c on t.category.id = c.id
    where t.user.id = :toUserId
    and t.date = :date
    and (
        c.visibility = 'PUBLIC'
        or (c.visibility = 'FOLLOWERS' and exists (
        select 1 from Friend f
        where f.fromUser.id = :toUserId
        and f.toUser.id = :fromUserId
        )
        )
        or (c.visibility = 'PARTIAL' and tv.user.id = :fromUserId)
        or (c.visibility = 'PRIVATE' and t.user.id = :fromUserId)
    )
""")
    List<Todo> findVisibleTodos(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId, @Param("date") LocalDate date);
}

