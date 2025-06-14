package com.jdev.book_service.command.controller;

import com.jdev.book_service.command.command.CreateBookCommand;
import com.jdev.book_service.command.command.DeleteBookCommand;
import com.jdev.book_service.command.command.UpdateBookCommand;
import com.jdev.book_service.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model){
        CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(),model.getName(),model.getAuthor(),true);
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{bookId}")
    public String updateBook(@RequestBody BookRequestModel model, @PathVariable String bookId){

        UpdateBookCommand command = new UpdateBookCommand(bookId, model.getName(), model.getAuthor(), model.getIsReady());
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") String bookId){
        DeleteBookCommand command = new DeleteBookCommand(bookId);
        return commandGateway.sendAndWait(command);
    }
}
