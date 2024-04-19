package com.example.demo.Answer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswer is a Querydsl query type for Answer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswer extends EntityPathBase<Answer> {

    private static final long serialVersionUID = -2097538380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswer answer = new QAnswer("answer");

    public final com.example.demo.User.QSiteUser author;

    public final ListPath<com.example.demo.Comment.Comment, com.example.demo.Comment.QComment> commentList = this.<com.example.demo.Comment.Comment, com.example.demo.Comment.QComment>createList("commentList", com.example.demo.Comment.Comment.class, com.example.demo.Comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final com.example.demo.Question.QQuestion question;

    public final SetPath<com.example.demo.User.SiteUser, com.example.demo.User.QSiteUser> voter = this.<com.example.demo.User.SiteUser, com.example.demo.User.QSiteUser>createSet("voter", com.example.demo.User.SiteUser.class, com.example.demo.User.QSiteUser.class, PathInits.DIRECT2);

    public QAnswer(String variable) {
        this(Answer.class, forVariable(variable), INITS);
    }

    public QAnswer(Path<? extends Answer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswer(PathMetadata metadata, PathInits inits) {
        this(Answer.class, metadata, inits);
    }

    public QAnswer(Class<? extends Answer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.example.demo.User.QSiteUser(forProperty("author")) : null;
        this.question = inits.isInitialized("question") ? new com.example.demo.Question.QQuestion(forProperty("question"), inits.get("question")) : null;
    }

}

