package com.jdev.book_service.command.aggregate;

import com.jdev.book_service.command.command.CreateBookCommand;
import com.jdev.book_service.command.command.DeleteBookCommand;
import com.jdev.book_service.command.command.UpdateBookCommand;
import com.jdev.book_service.command.event.BookCreateEvent;
import com.jdev.book_service.command.event.BookDeleteEvent;
import com.jdev.book_service.command.event.BookUpdateEvent;
import lombok.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class BookAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreateEvent bookCreatedEvent = new BookCreateEvent();
        BeanUtils.copyProperties(command,bookCreatedEvent);

        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand command){
        BookUpdateEvent bookUpdatedEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(command,bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand command){
        BookDeleteEvent bookDeletedEvent = new BookDeleteEvent();
        BeanUtils.copyProperties(command,bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }


    @EventSourcingHandler
    public void on(BookCreateEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event){
        this.id = event.getId();
    }
}
