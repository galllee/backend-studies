package com.example.todomate_clone.auth.service.store;

import com.example.todomate_clone.auth.service.tempdata.SignupTempData;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SignupMemoryStore {
    private final Map<String, SignupTempData> store = new ConcurrentHashMap<>();

    public void save(String token, SignupTempData data) {
        store.put(token, data);
    }

    public SignupTempData get(String token) {
        return store.get(token);
    }

    public void remove(String token) {
        store.remove(token);
    }
}
