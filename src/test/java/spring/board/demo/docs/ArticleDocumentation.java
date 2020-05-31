package spring.board.demo.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static spring.board.demo.docs.ApiDocumentUtils.*;
import static spring.board.demo.docs.DocumentFormatGenerator.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class ArticleDocumentation {
    public static RestDocumentationResultHandler createArticle() {
        return document("articles/create",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING)
                    .description("The title of article")
                    .attributes(getNotBlankFormat()),
                fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("The content of article")
                    .attributes(getNotBlankFormat())
            ),
            responseHeaders(
                headerWithName("Location").description("The location of article that just created")
            ),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The no of article"),
                fieldWithPath("title").type(JsonFieldType.STRING)
                    .description("The title of article"),
                fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("The content of article"),
                fieldWithPath("userName").type(JsonFieldType.STRING)
                    .description("The userName of article")
            )
        );
    }

    public static RestDocumentationResultHandler getAllArticles() {
        return document("articles/get-all",
            getDocumentRequest(),
            getDocumentResponse()
        );
    }

    public static RestDocumentationResultHandler getArticle() {
        return document("articles/get",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The no of article"),
                fieldWithPath("title").type(JsonFieldType.STRING)
                    .description("The title of article"),
                fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("The content of article"),
                fieldWithPath("userName").type(JsonFieldType.STRING)
                    .description("The userName of article")
            )
        );
    }

    public static RestDocumentationResultHandler update() {
        return document("articles/update",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING)
                    .description("The title of article"),
                fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("The content of article")
            )
        );
    }

    public static RestDocumentationResultHandler delete() {
        return document("articles/delete",
            getDocumentRequest(),
            getDocumentResponse()
        );
    }
}
