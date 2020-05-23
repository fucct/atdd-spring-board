package spring.board.demo.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class MemberDocumentation {
    public static RestDocumentationResultHandler createArticle() {
        return document("articles/create",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("The title of article"),
                fieldWithPath("userName").type(JsonFieldType.STRING).description("The author name "),
                fieldWithPath("content").type(JsonFieldType.STRING).description("The content of article")
            ),
            responseHeaders(
                headerWithName("Location").description("The location of article that just created")
            )
        );
    }
}
