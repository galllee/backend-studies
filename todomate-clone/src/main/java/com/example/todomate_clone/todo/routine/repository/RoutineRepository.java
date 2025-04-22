package com.example.todomate_clone.todo.routine.repository;

import com.example.todomate_clone.todo.routine.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
}
