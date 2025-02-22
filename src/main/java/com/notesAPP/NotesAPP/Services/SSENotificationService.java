package com.notesAPP.NotesAPP.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Service
public class SSENotificationService {

    // List to hold all connected SSE clients
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // Stream method for clients to connect
    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    // Send a notice to all connected clients
    public void sendNotice(String notice) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(notice);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
