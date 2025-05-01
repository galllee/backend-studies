package com.example.todomate_clone.todo.todo.service;

import com.example.todomate_clone.friend.domain.TodoLike;
import com.example.todomate_clone.friend.repository.TodoLikeRepository;
import com.example.todomate_clone.notification.event.todoCompleted.TodoCompletedEvent;
import com.example.todomate_clone.notification.event.todoLike.TodoLikeEvent;
import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.todo.todo.dto.request.TodoOrderItem;
import com.example.todomate_clone.todo.category.dto.request.UpdateTodoOrdersRequest;
import com.example.todomate_clone.todo.category.repository.CategoryRepository;
import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.todo.todo.dto.response.TodoGroupByCategoryResponse;
import com.example.todomate_clone.todo.todo.repository.TodoRepository;
import com.example.todomate_clone.todo.todo.dto.request.*;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TodoLikeRepository todoLikeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void createTodo(Long userId, Long categoryId, CreateTodoRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));
        todoRepository.save(
                Todo.builder()
                        .category(category)
                        .user(user)
                        .title(request.getTitle())
                        .date(request.getDate())
                        .build()
        );
    }

    @Transactional
    public void editTodo(Long todoId, String title) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.editTodo(title);
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todoRepository.delete(todo);
    }

    @Transactional
    public void updateTodoMemo(Long todoId, UpdateTodoMemoRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateTodoMemo(request.getMemo(), request.isMemoPrivate());
    }

    @Transactional
    public void updateTodoReminderTime(Long todoId, UpdateTodoReminderTime request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateTodoReminderTime(request.getReminderTime());
    }

    @Transactional
    public void pauseTodoTimer(Long todoId, PauseTodoTimerRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateElapsedTime(request.getElapsedTime());
    }

    @Transactional
    public void completeTodoTimer(Long todoId, CompleteTodoTimerRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateElapsedTime(request.getElapsedTime());
        todo.markAsCompleted();
    }

    @Transactional
    public void resetTodoTimer(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.resetElapsedTime();
    }

    @Transactional
    public void scheduleTodoForToday(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateDate(LocalDate.now());
    }

    @Transactional
    public void scheduleTodoForTomorrow(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateDate(LocalDate.now().plusDays(1));
    }

    @Transactional
    public void updateTodoSchedule(Long todoId, UpdateTodoScheduleRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateDate(request.getNewDate());
    }

    @Transactional
    public void archiveTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.archive();
    }

    @Transactional
    public void repeatTodoToday(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todoRepository.save(
                todo.repeatForDate(LocalDate.now())
        );
    }

    @Transactional
    public void repeatTodo(Long todoId, LocalDate date) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todoRepository.save(
                todo.repeatForDate(date)
        );
    }

    public List<TodoGroupByCategoryResponse> getTodosByDateGroupedByCategory(Long fromUserId, Long toUserId, LocalDate date) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Map<Category, List<Todo>> todosByCategory = todoRepository.findVisibleTodos(fromUserId, toUserId, date)
                .stream().collect(Collectors.groupingBy(Todo::getCategory));

        return todosByCategory.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<Category, List<Todo>> entry) -> {
                    Category category = categoryRepository.findById(entry.getKey().getId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
                    Long orderNum = category.getOrderNum();
                    return orderNum == null ? Integer.MAX_VALUE : orderNum;
                }).thenComparing(entry -> {
                    Category category = categoryRepository.findById(entry.getKey().getId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
                    return category.getCreatedAt();
                }))
                .flatMap(
                entry -> {
                    Category category = categoryRepository.findById(entry.getKey().getId())
                                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

                    List<Todo> todos = entry.getValue();

                    Comparator<Todo> nullFirstComparator = (t1, t2) -> {
                        boolean isT1Null = t1.getOrderNum() == null;
                        boolean isT2Null = t2.getOrderNum() == null;

                        if (isT1Null && !isT2Null) {
                            return -1;
                        } else if (!isT1Null && isT2Null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    };

                    Comparator<Todo> detailComparator = (t1, t2) -> {
                        if (t1.getOrderNum() == null && t2.getOrderNum() == null) {
                            return t2.getCreatedAt().compareTo(t1.getCreatedAt());
                        } else {
                            return t1.getOrderNum().compareTo(t2.getOrderNum());
                        }
                    };

                    List<Todo> sortedTodo = todos.stream()
                            .sorted(nullFirstComparator.thenComparing(detailComparator))
                            .toList();

                    return sortedTodo.stream().map(todo -> TodoGroupByCategoryResponse.builder()
                            .categoryName(category.getName())
                            .title(todo.getTitle())
                            .elapsedTime(todo.getElapsedTime())
                            .memo(todo.getMemo())
                            .status(todo.getStatus())
                            .build()
                    );
                }).toList();
    }

    @Transactional
    public void updateTodoOrders(Long userId, UpdateTodoOrdersRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<Todo> todos = todoRepository.findAllByUserAndDateAndIsArchivedFalse(user, request.getDate());

        for(Todo todo : todos) {
            TodoOrderItem matched = request.getOrderItems()
                            .stream().filter(
                                    orderItem -> todo.getId().equals(orderItem.getTodoId())
                    ).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 todo가 존재하지 않습니다."));

            todo.updateOrderNum(matched.getOrderNum());

            Category category = categoryRepository.findById(matched.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));
            // for문 전에 카테고리 한번에 조회해놓는 것도 좋을듯

            todo.updateCategory(category);
        }
    }

    @Transactional
    public void likeTodo(Long userId, Long todoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두가 존재하지 않습니다."));

        todoLikeRepository.save(
                new TodoLike(user, todo)
        );

        eventPublisher.publishEvent(new TodoLikeEvent(user, todo));
    }

    @Transactional
    public void unlikeTodo(Long todoLikeId) {
        todoLikeRepository.deleteById(todoLikeId);
    }

    @Transactional
    public void completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두가 존재하지 않습니다."));

        todo.markAsCompleted();

        eventPublisher.publishEvent(new TodoCompletedEvent(todo.getUser(), todo));
    }
}
