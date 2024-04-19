package com.example.demo.Question;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -549015372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final ListPath<com.example.demo.Answer.Answer, com.example.demo.Answer.QAnswer> answerList = this.<com.example.demo.Answer.Answer, com.example.demo.Answer.QAnswer>createList("answerList", com.example.demo.Answer.Answer.class, com.example.demo.Answer.QAnswer.class, PathInits.DIRECT2);

    public final com.example.demo.User.QSiteUser author;

    public final com.example.demo.Category.QCategory category;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath subject = createString("subject");

    public final SetPath<com.example.demo.User.SiteUser, com.example.demo.User.QSiteUser> voter = this.<com.example.demo.User.SiteUser, com.example.demo.User.QSiteUser>createSet("voter", com.example.demo.User.SiteUser.class, com.example.demo.User.QSiteUser.class, PathInits.DIRECT2);

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.example.demo.User.QSiteUser(forProperty("author")) : null;
        this.category = inits.isInitialized("category") ? new com.example.demo.Category.QCategory(forProperty("category")) : null;
    }

}

