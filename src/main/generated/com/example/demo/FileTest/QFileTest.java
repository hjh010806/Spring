package com.example.demo.FileTest;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileTest is a Querydsl query type for FileTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileTest extends EntityPathBase<FileTest> {

    private static final long serialVersionUID = 667746740L;

    public static final QFileTest fileTest = new QFileTest("fileTest");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath key = createString("key");

    public final StringPath url = createString("url");

    public QFileTest(String variable) {
        super(FileTest.class, forVariable(variable));
    }

    public QFileTest(Path<? extends FileTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileTest(PathMetadata metadata) {
        super(FileTest.class, metadata);
    }

}

