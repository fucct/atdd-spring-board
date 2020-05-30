package spring.board.demo.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static spring.board.demo.docs.ApiDocumentUtils.*;
import static spring.board.demo.docs.DocumentFormatGenerator.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserDocumentation {

    public static RestDocumentationResultHandler create() {
        return document("users/create",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("userId").type(JsonFieldType.STRING).description("The user's Id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name"),
                fieldWithPath("password").type(JsonFieldType.STRING)
                    .description("The user's password")
            ),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The user's no")
            )
        );
    }

    public static RestDocumentationResultHandler get() {
        return document("users/get",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The user's no"),
                fieldWithPath("userId").type(JsonFieldType.STRING).description("The user's id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name"),
                fieldWithPath("articles").type(JsonFieldType.ARRAY)
                    .description("The user's articles")
                    .optional()
            )
        );
    }

    public static RestDocumentationResultHandler update() {
        return document("users/update",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("The user's id"),
                fieldWithPath("oldPassword").type(JsonFieldType.STRING)
                    .description("The user's previous password")
                    .attributes(getPasswordFormat()),
                fieldWithPath("newPassword").type(JsonFieldType.STRING)
                    .description("The user's new password")
                    .attributes(getPasswordFormat())
            )
        );
    }

    public static RestDocumentationResultHandler delete() {
        return document("users/delete",
            getDocumentRequest(),
            getDocumentResponse()
        );
    }
}
